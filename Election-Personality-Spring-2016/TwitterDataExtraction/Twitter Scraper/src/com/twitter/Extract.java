package com.twitter;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


import java.util.Scanner;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import twitter4j.User;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;

public class Extract {
	static String username;
	public static void intializeHandles() throws IOException,LangDetectException
	{
		
		Scanner s = new Scanner(new File("D://TwitterAcctRepublican.txt"));
		MainApp.twitterHandleList = new ArrayList<String>();
		while (s.hasNext()){
			MainApp.twitterHandleList.add(s.next());
		}
		s.close();
		
		//System.out.println(MainApp.twitterHandleList);	
	}
	

	public static void extractTwitterFeed(String conkey, String consec,String tokkey, String toksec) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(conkey)
          .setOAuthConsumerSecret(consec)
          .setOAuthAccessToken(tokkey)
          .setOAuthAccessTokenSecret(toksec);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter1 = tf.getInstance();
		MainApp.userTweets = new ArrayList<Status>();
		ArrayList<String> temp=new ArrayList<String>();
		MainApp.thandleNames=new ArrayList<String>();
		User user;
		try{
			Iterator<String> twitterHandleIterator = MainApp.twitterHandleList.iterator();
		    while(twitterHandleIterator.hasNext()){
		    	
				 MainApp.twitterHandle= twitterHandleIterator.next();
				 
				 user=twitter1.showUser(MainApp.twitterHandle);
				 MainApp.thandleNames.add(user.getName());
				 temp.add(MainApp.twitterHandle);
				 username=user.getName();
				 int limit=172;
				 int page = 1;
		       	 System.out.println("Extracting Data for.."+ username);
		       	 
		    	 while (page<20){
		    		MainApp.userTweets.addAll(twitter1.getUserTimeline(MainApp.twitterHandle,new Paging(page++,200)));
		    	 }
		    	 storeInExcel(MainApp.userTweets); 
		    	 MainApp.userTweets.clear();
		    	try{
		    		 Map<String, RateLimitStatus> rateLimitStatus = twitter1.getRateLimitStatus();
			    	 RateLimitStatus rateLimit = rateLimitStatus.get("/application/rate_limit_status");
			    	 if(limit>=rateLimit.getRemaining())
			    	 {
			    		 System.out.println("Limit reached sleeping for 15 Min...");
			    		 Thread.sleep(900*1000);
			    		 System.out.println("Resuming...");
			    	 }
			    	 
		    	}
		    	 catch(TwitterException te)
		    	{te.printStackTrace();
	            System.out.println("Failed to get rate limit status: " + te.getMessage());
	           
	            }
			 }
		    }catch(TwitterException t){
		    	temp.remove(temp.size()-1);
		    	if(temp.size()>0){
		    		System.out.println("Twitter Exception Caused - API Limit Exceeded\nTweets collected for "+temp );
			    	MainApp.twitterHandleList.clear();
			    	MainApp.twitterHandleList.addAll(temp);	
		    	}else{
		    		System.out.println("Twitter Exception Caused - API Limit Exceeded\nNo Tweets Collected");
		    		System.exit(0);
		    	}
		    	
		    }catch(Exception e){
		    	System.out.println(e.getMessage());
		    }
	}	  
	
			
	public static void storeInExcel(List<Status> inputList) throws Exception{
		XSSFWorkbook rawDataFile = new XSSFWorkbook();
		XSSFSheet rawSheet = rawDataFile.createSheet("RawData");
		int rownum = 0;	
		PrintWriter writer = new PrintWriter("Twitter Clean Text/"+username+".txt", "UTF-8");
        String cleanPost;
		String prevCleanPost="Test";
		for(Status post:inputList){
			if (post.isRetweetedByMe()==false &&  post.isRetweet()==false){				
				Row row = rawSheet.createRow(rownum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(post.getCreatedAt().toString());
				cell = row.createCell(1);
				cell.setCellValue(post.getText().toString());	
				cleanPost = clean(post.getText().toString()).trim();
				if (cleanPost.equals("")){
					cell = row.createCell(2);
					cell.setCellValue("");
										
				}
				else{
					cell = row.createCell(2);
					cell.setCellValue(cleanPost);
					int probability = validateData(cleanPost);
					if (probability>=90){
					if(!cleanPost.equals(prevCleanPost))
					{
						cell = row.getCell(2);
						writer.write(cell.getStringCellValue());
						writer.append(" ");
						prevCleanPost=cleanPost;
					}
					}
				}	
			}
		} 
		System.out.println(inputList.size() + " tweets extracted.");
		FileOutputStream out = new FileOutputStream(new File("Twitter Raw Data/"+username+" RawData.xlsx"));
		rawDataFile.write(out);
		out.close();
		rawDataFile.close();
		writer.close();
		System.out.println("Files written successfully on disk.");
	}
	
	

	public static String clean(String inputText){
		int wordCount = 0;
		StringTokenizer str = new StringTokenizer(inputText," ");
		StringBuilder sb = new StringBuilder();
		while(str.hasMoreTokens()){
			
			String s = str.nextToken().trim();
				
				if (s.contains("http") || s.contains("@")||s.contains("#")){
					continue;
				}					
				else{
					if(s.length()>=3){
						s=s.replaceAll("[^a-zA-Z]", "");
						if(s.length()>=3)
						{
						sb.append(s.trim());
						}
				}
					sb.append(" ");
					wordCount++;		
			}
		}
		if(wordCount>=3)
			{
				return sb.toString();
			}
			else
				return "";
	 }
	
	public static String detect(String text) throws LangDetectException {
    	Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.detect();
    }
    
    public static ArrayList<Language> detectLangs(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.getProbabilities();
    }
    
    public static int validateData(String text) throws Exception{
    	if(detect(text).equals("en")){
        	return getProbability(detectLangs(text));
        }
        else 
        	return 0;
        }
       
	public static int getProbability(ArrayList<Language> inputProbability) {
		Integer tempInt = new Integer(0);
    	String tempString ="";
    	ArrayList<Integer> probabilities = new ArrayList<Integer>();
    	
    	Iterator<Language> it = inputProbability.iterator();
    	while(it.hasNext()){
    		 tempString= it.next().toString();
    		if(tempString.contains("en"))
    			tempInt = Integer.parseInt(tempString.substring(6,8));
    			probabilities.add(tempInt);
    	}
    	Collections.sort(probabilities);
    	int maxProbability =probabilities.get(probabilities.size()-1).intValue();
    	return maxProbability;
	}
    
}



