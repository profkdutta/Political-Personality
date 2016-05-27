package com.service.twitter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.bean.TwitterBean;
import com.service.data.ExcelService;

public class RetrieveTwitterDataService {

public HashMap<Integer, TwitterBean> retrieveTwitterData(){
		
		HashMap<Integer, TwitterBean> result = null;
		ExcelService service = null;
		List data = null;
		
		try {
			result = new HashMap<>();
			service = new ExcelService();
			
			//Retrieve Data -- from excel only -- to be deleted
			//To be changed with database
			data = service.retreiveExcelData("src/Data/Twitter/", "twitterData.xlsx");
			
			final Iterator iterator = data.iterator();
			List tempRow = null;
			TwitterBean bean = null;
			
			while(iterator.hasNext()){
				tempRow =  (List) iterator.next();
				
				bean = new TwitterBean();
				bean.setId((int)Double.parseDouble(((String) tempRow.get(0))));
				bean.setName((String)tempRow.get(1));
				bean.setContext((String)tempRow.get(2));
				
				result.put(bean.getId(), bean);
			}
			
		} catch (Exception exception) {
			// TODO: handle exception
			exception.printStackTrace();;
		}
		return result;
	}
}
