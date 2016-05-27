package com.service.watson;


import java.util.Map;

import com.bean.BigFiveBean;
import com.bean.UClassifyBean;
import com.global.GlobalValues;

public class ExtractDataService {

	public BigFiveBean extractBigFiveData(Integer id,Map<String, Integer> watsonData){
		BigFiveBean bigFiveBean = null;
		
		try{
			//Code for extraction of big five values
			bigFiveBean = new BigFiveBean();
			bigFiveBean.setConscientiousnessBean(watsonData.get(GlobalValues.CONSCIENTIOUSNESS));
			bigFiveBean.setId(id);
			bigFiveBean.setAgreeablenessBean(watsonData.get(GlobalValues.AGREEABLENESSBEAN));
			bigFiveBean.setEmotionalRangeBean(watsonData.get(GlobalValues.EMOTIONALRANGEBEAN));
			bigFiveBean.setIntroversionBean(watsonData.get(GlobalValues.INTROVERSIONBEAN));
			bigFiveBean.setOpennessBean(watsonData.get(GlobalValues.OPENNESSBEAN));
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return bigFiveBean;
	}
	
	
	public UClassifyBean extractUClassifyData(Integer id,Map<String, Integer> uClassifyDataMap){
		
		UClassifyBean uClassifyBean = null;
		
		try{
			//Code to extract UClassify Values
			uClassifyBean = new UClassifyBean();
			uClassifyBean.setId(id);
			uClassifyBean.setExtraversion(uClassifyDataMap.get(GlobalValues.EXTRAVERSION));
			uClassifyBean.setFeeling(uClassifyDataMap.get(GlobalValues.FEELING));
			uClassifyBean.setIntroversion(uClassifyDataMap.get(GlobalValues.INTROVERSION));
			uClassifyBean.setIntution(uClassifyDataMap.get(GlobalValues.INTUITION));
			uClassifyBean.setJudging(uClassifyDataMap.get(GlobalValues.JUDGING));
			uClassifyBean.setPerceiving(uClassifyDataMap.get(GlobalValues.PERCEIVING));
			uClassifyBean.setSensing(uClassifyDataMap.get(GlobalValues.SENSING));
			uClassifyBean.setThinking(uClassifyDataMap.get(GlobalValues.THINKING));
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return uClassifyBean;
		
	}
	
}
