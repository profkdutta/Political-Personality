package com.service.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.bean.BigFiveBean;
import com.global.GlobalValues;

public class ExcelService {
	
	
	
	public boolean saveWatsonDataInCSV(final List<BigFiveBean> watsonDataList,final String URL,final String fileName){
		
		boolean success = false;
		File csvTwitterFile = null;
		FileOutputStream oFile = null;
		final String fullPath = URL.concat(fileName);
		StringBuffer tempBuffer = null;
		
		try{
			
			csvTwitterFile = new File(fullPath);
			tempBuffer = new StringBuffer();
			if(!csvTwitterFile.exists()) {
				csvTwitterFile.createNewFile();
				tempBuffer.append(GlobalValues.WATSON_FILE_HEADER);
				tempBuffer.append(GlobalValues.NEW_LINE_SEPARATOR);
			} 
			oFile = new FileOutputStream(csvTwitterFile, true); 
			oFile.write(tempBuffer.toString().getBytes(), 0, tempBuffer.length());
			
			
			for (BigFiveBean bigFiveBean : watsonDataList) {
				
				tempBuffer = new StringBuffer();
				
				tempBuffer.append(bigFiveBean.getId());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getConscientiousnessBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getAgreeblenessSampleError());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getOpennessBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getOpennessSampleError());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getAgreeablenessBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getAgreeblenessSampleError());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getEmotionalRangeBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getEmotionalRangeSampleError());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getIntroversionBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getIntroversionSampleError());
				tempBuffer.append(GlobalValues.NEW_LINE_SEPARATOR);
				
				oFile.write(tempBuffer.toString().getBytes(), 0, tempBuffer.length());
			}
			
			success = true;
		} 
		catch (IOException e) {
            e.printStackTrace();
        }
		finally{
            try {
            	oFile.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
		return success;
	}
	
}	
