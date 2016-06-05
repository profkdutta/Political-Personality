package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.bean.BigFiveBean;
import com.bean.TwitterBean;
import com.service.twitter.RetrieveTwitterDataService;
import com.service.watson.ExtractDataService;
import com.service.watson.WatsonService;

public class MainController {

	
	
	
	public static void main(String[] args) {
		
		Map<Integer, TwitterBean> twitterDataMap = null;
		Map<String,Integer> watsonDataMap = null;
		BigFiveBean bigFiveBean = null;
		
		List<BigFiveBean> watsonDataList = null;
		
		WebDriver driver = null;
		
		//To be deleted once web app is developed
		RetrieveTwitterDataService twitterService = null;
		WatsonService watsonService = null;
		ExtractDataService extractService = null;
		
		try{
		
			//Get Excel Data
			twitterService = new RetrieveTwitterDataService();
			twitterDataMap = twitterService.retrieveTwitterData();
			
						
			//Send Data to Watson and get Json
			
			driver = new FirefoxDriver();
			for (Integer tempId : twitterDataMap.keySet()) {
					watsonService = new WatsonService();
					watsonDataList = new ArrayList<BigFiveBean>();
					extractService = new ExtractDataService();
					//Watson Code Goes here
					String name=twitterDataMap.get(tempId).getName();
					watsonDataMap = watsonService.getWatsonData(driver,twitterDataMap.get(tempId));
					bigFiveBean = extractService.extractBigFiveData(name,watsonDataMap);
					
					watsonDataList.add(bigFiveBean);
					watsonService.saveWatsonData(watsonDataList);
					System.out.println("Data fetched for.. "+name);
					Thread.sleep(new Random().nextInt(60000 - 45000) + 45000);  // generates random number between 45s and 60s
				
			}
			driver.close();
			
						
			System.out.println("All Files Saved to disk...");
			
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		finally{
			driver.quit();
		}
		
	}
}
