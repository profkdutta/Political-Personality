package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.bean.BigFiveBean;
import com.bean.TwitterBean;
import com.bean.UClassifyBean;
import com.service.twitter.RetrieveTwitterDataService;
import com.service.watson.ExtractDataService;
import com.service.watson.UClassifyService;
import com.service.watson.WatsonService;

public class MainController {

	
	
	
	public static void main(String[] args) {
		
		Map<Integer, TwitterBean> twitterDataMap = null;
		Map<String, Integer> watsonDataMap = null;
		BigFiveBean bigFiveBean = null;
		UClassifyBean uClassifyBean = null;
		List<BigFiveBean> watsonDataList = null;
		List<UClassifyBean> uClassifyDataList = null;
		WebDriver driver = null;
		UClassifyService uClassifyService = null; 
		Map<String, Integer> uClassifyDataMap = null;
		//To be deleted once web app is developed
		RetrieveTwitterDataService twitterService = null;
		WatsonService watsonService = null;
		ExtractDataService extractService = null;
		
		try{
		
			//Get Excel Data
			twitterService = new RetrieveTwitterDataService();
			twitterDataMap = twitterService.retrieveTwitterData();
			
			uClassifyService = new UClassifyService();
			uClassifyDataList = new ArrayList<UClassifyBean>();
			
			//Send Data to Watson and get Json
			watsonService = new WatsonService();
			watsonDataList = new ArrayList<BigFiveBean>();
			extractService = new ExtractDataService();
			driver = new FirefoxDriver();
			for (Integer tempId : twitterDataMap.keySet()) {
				if(tempId <= 3){   // Code to be removed
					
					//Watson Code Goes here
					watsonDataMap = watsonService.getWatsonData(driver,twitterDataMap.get(tempId));
					bigFiveBean = extractService.extractBigFiveData(tempId,watsonDataMap);
					System.out.println(tempId);
					watsonDataList.add(bigFiveBean);
					
					// UClassify code Goes Here
					uClassifyDataMap = uClassifyService.getUclassify(twitterDataMap.get(tempId));
					uClassifyBean = extractService.extractUClassifyData(tempId,uClassifyDataMap);
					System.out.println(tempId);
					uClassifyDataList.add(uClassifyBean);
					
					
					Thread.sleep(new Random().nextInt(60000 - 45000) + 45000);  // generates random number between 45s and 60s
				}
			}
			driver.close();
			
			//Save the data in Excel
			watsonService.saveWatsonData(watsonDataList);
			uClassifyService.saveUClassifyData(uClassifyDataList);
			
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		finally{
			driver.quit();
		}
		
	}
}
