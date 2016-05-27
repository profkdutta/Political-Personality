package com.service.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.bean.BigFiveBean;
import com.bean.UClassifyBean;
import com.global.GlobalValues;

public class ExcelService {
	
	
	@SuppressWarnings("rawtypes")
	public List<ArrayList> retreiveExcelData(final String path,final String filename) throws Exception{
		
		List sheetData = null;
		FileInputStream fis = null;
		Workbook workBook = null;
		Sheet sheet = null;
		
		final String fullPath = path.concat(filename);
		try {
			sheetData = new ArrayList<>();
			
			//to read the excel file
			fis = new FileInputStream(fullPath);
			
			//create workbook from file system
			workBook = WorkbookFactory.create(fis);
			
			//read first sheet 
			sheet = workBook.getSheetAt(0);
			
			final Iterator<?> rows = sheet.rowIterator();
			Iterator cells = null;
			List data = null;
			
			Row singleRow = null;
			Cell singleCell;
			
			int counter = 0;
			
			while(rows.hasNext()){
				
				singleRow = (Row) rows.next(); 
				if(counter > 0){
					cells = singleRow.cellIterator();
					
					data = new ArrayList<Cell>();
					int rowId = 0;
					while(cells.hasNext()){
						singleCell = (Cell) cells.next();
						if(rowId == 0){
							data.add(Double.toString(singleCell.getNumericCellValue()));
							rowId++;
						}
						else{
							data.add(singleCell.getStringCellValue());
						}
					}
					sheetData.add(data);
				}
				counter++;
			}
				
		} catch (Exception exception) {
			// TODO: handle exception
			exception.printStackTrace();
		}
		
		return sheetData;
	}
	
	
	
	public boolean saveWatsonDataInCSV(final List<BigFiveBean> watsonDataList,final String URL,final String fileName){
		
		boolean success = false;
		File csvTwitterFile = null;
		FileOutputStream oFile = null;
		final String fullPath = URL.concat(fileName);
		StringBuffer tempBuffer = null;
		
		try{
			
			csvTwitterFile = new File(fullPath);
			if(!csvTwitterFile.exists()) {
				csvTwitterFile.createNewFile();
			} 
			oFile = new FileOutputStream(csvTwitterFile, true); 
			
			tempBuffer = new StringBuffer();
			
			tempBuffer.append(GlobalValues.WATSON_FILE_HEADER);
			tempBuffer.append(GlobalValues.NEW_LINE_SEPARATOR);

			oFile.write(tempBuffer.toString().getBytes(), 0, tempBuffer.length());
			
			
			for (BigFiveBean bigFiveBean : watsonDataList) {
				
				tempBuffer = new StringBuffer();
				
				tempBuffer.append(bigFiveBean.getId());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getConscientiousnessBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getOpennessBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getAgreeablenessBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getEmotionalRangeBean());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(bigFiveBean.getIntroversionBean());
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
	
	public boolean saveUClassifyDataInCSV(final List<UClassifyBean> uClassifyDataList,final String URL,final String fileName){
		
		boolean success = false;
		File csvTwitterFile = null;
		FileOutputStream oFile = null;
		final String fullPath = URL.concat(fileName);
		StringBuffer tempBuffer = null;
		
		try{
			
			csvTwitterFile = new File(fullPath);
			if(!csvTwitterFile.exists()) {
				csvTwitterFile.createNewFile();
			} 
			oFile = new FileOutputStream(csvTwitterFile, true); 
			
			tempBuffer = new StringBuffer();
			
			tempBuffer.append(GlobalValues.UCLASSIFY_FILE_HEADER);
			tempBuffer.append(GlobalValues.NEW_LINE_SEPARATOR);

			oFile.write(tempBuffer.toString().getBytes(), 0, tempBuffer.length());
			
			
			for (UClassifyBean uClassifyBean : uClassifyDataList) {
				
				tempBuffer = new StringBuffer();
				
				tempBuffer.append(uClassifyBean.getId());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getFeeling());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getThinking());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getExtraversion());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getIntroversion());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getJudging());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getPerceiving());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getSensing());
				tempBuffer.append(GlobalValues.COMMA_DELIMITER);
				tempBuffer.append(uClassifyBean.getIntution());
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
