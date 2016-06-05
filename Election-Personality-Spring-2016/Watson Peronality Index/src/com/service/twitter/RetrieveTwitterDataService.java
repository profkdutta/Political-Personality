package com.service.twitter;

import java.io.File;
import java.util.HashMap;


import org.apache.commons.io.FileUtils;

import com.bean.TwitterBean;


public class RetrieveTwitterDataService {

public HashMap<Integer, TwitterBean> retrieveTwitterData(){
		
		HashMap<Integer, TwitterBean> result = null;
				
		try { 
			result = new HashMap<>();
			File folder = new File("D://Workstation/workspace/Twitter Scrapper/Twitter Clean Text/Republican");
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      File file = listOfFiles[i];
			      if (file.isFile()&& file.getName().endsWith(".txt")) {
			    	  String content = FileUtils.readFileToString(file);
			    	  String name = file.getName().replace(".txt", "");
			    	  TwitterBean bean = null;
			    		bean = new TwitterBean();
						bean.setId(i);
						bean.setName(name);
						bean.setContext(content);
						//System.out.println(name);
						result.put(bean.getId(), bean);
			      }
			    }
				
		} catch (Exception exception) {
			// TODO: handle exception
			exception.printStackTrace();
		}
		return result;
	}
}
