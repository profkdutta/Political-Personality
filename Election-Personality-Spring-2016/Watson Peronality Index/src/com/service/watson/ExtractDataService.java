package com.service.watson;


import java.util.Map;

import com.bean.BigFiveBean;
import com.global.GlobalValues;

public class ExtractDataService {

	public BigFiveBean extractBigFiveData(String name,Map<String, Integer> watsonData){
		BigFiveBean bigFiveBean = null;
		
		try{
			//Code for extraction of big five values
			bigFiveBean = new BigFiveBean();
			bigFiveBean.setId(name);
			bigFiveBean.setConscientiousnessBean(watsonData.get(GlobalValues.CONSCIENTIOUSNESS));
			bigFiveBean.setConscientiousnessSampleError(watsonData.get(GlobalValues.CONSCIENTIOUSNESS_SE));
			bigFiveBean.setAgreeablenessBean(watsonData.get(GlobalValues.AGREEABLENESSBEAN));
			bigFiveBean.setEmotionalRangeBean(watsonData.get(GlobalValues.EMOTIONALRANGEBEAN));
			bigFiveBean.setIntroversionBean(watsonData.get(GlobalValues.INTROVERSIONBEAN));
			bigFiveBean.setOpennessBean(watsonData.get(GlobalValues.OPENNESSBEAN));
			bigFiveBean.setAgreeblenessSampleError(watsonData.get(GlobalValues.AGREEABLENESSBEAN_SE));
			bigFiveBean.setEmotionalRangeSampleError(watsonData.get(GlobalValues.EMOTIONALRANGEBEAN_SE));
			bigFiveBean.setIntroversionSampleError(watsonData.get(GlobalValues.INTROVERSIONBEAN_SE));
			bigFiveBean.setOpennessSampleError(watsonData.get(GlobalValues.OPENNESSBEAN_SE));
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return bigFiveBean;
	}
	
	
	
	
}
