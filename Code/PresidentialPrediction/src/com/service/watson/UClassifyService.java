package com.service.watson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.bean.TwitterBean;
import com.bean.UClassifyBean;
import com.service.data.ExcelService;

public class UClassifyService {
	
	
	public HashMap<String,Integer> getUclassify(final TwitterBean twitterBean){
		HashMap<String, Integer> res=new LinkedHashMap<String,Integer>();
			try{
				String encoded = DatatypeConverter.printBase64Binary(twitterBean.getContext().getBytes());
				//String encoded = Base64.encodeBase64(twitterBean.getContext().getBytes()).toString();
				//String encoded= java.util.Base64.getEncoder().encodeToString(twitterBean.getContext().getBytes());
				URL url = new URL("http://api.uclassify.com");    
			    URLConnection uconn = url.openConnection();
				uconn.setRequestProperty("Content-Type", "text/xml");
				uconn.setDoInput(true);
				uconn.setDoOutput(true);
				HttpURLConnection conn = (HttpURLConnection) uconn;
				conn.connect();	
				DataOutputStream wr = new DataOutputStream (uconn.getOutputStream());
				wr.writeBytes(xmlGen(encoded));
				wr.close();
			    InputStream is = uconn.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder();
			    String line;
			    while((line = rd.readLine()) != null) {
			      response.append(line);
			    }
			    conn.disconnect();
			    res=xmlRead(response.toString());
			    return res;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}
	
	private String xmlGen(String text){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><uclassify xmlns=\"http://api.uclassify.com/1/RequestSchema\" version=\"1.01\"><texts><textBase64 id=\"text_1\">"+text+"</textBase64></texts><readCalls readApiKey=\"d39gXR52YW2h\"><classify classifierName=\"myers briggs judging function\" id=\"call_1\" textId=\"text_1\" username=\"prfekt\"/><classify classifierName=\"myers briggs attitude\" id=\"call_2\" textId=\"text_1\" username=\"prfekt\"/><classify classifierName=\"myers briggs lifestyle\" id=\"call_3\" textId=\"text_1\" username=\"prfekt\"/><classify classifierName=\"myers briggs perceiving function\" id=\"call_4\" textId=\"text_1\" username=\"prfekt\"/></readCalls></uclassify>";
	}
	
	private HashMap<String,Integer> xmlRead(String xml){
		try{
			HashMap<String, Integer> uclass=new LinkedHashMap<String,Integer>();
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			Document doc = db.parse(is);
			NodeList list=doc.getElementsByTagName("classify");
			for(int i=0;i<list.getLength();i++){
				Node c = list.item(i);
				Element cele = (Element)c;
				NodeList classification = cele.getElementsByTagName("classification");
				Element e1 = (Element)classification.item(0);
				NodeList classes=e1.getElementsByTagName("class");
				Element class1=(Element)classes.item(0);
				Element class2=(Element)classes.item(1);
				uclass.put(class1.getAttribute("className"),(int)Math.round(Double.parseDouble(class1.getAttribute("p"))*100));
				uclass.put(class2.getAttribute("className"),(int)Math.round(Double.parseDouble(class2.getAttribute("p"))*100));
			}
			return uclass;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean saveUClassifyData(final List<UClassifyBean> uClassifyDataList){
		
		boolean flag =false;
		ExcelService service = null;
				
		try {
			
			service = new ExcelService();
			flag = service.saveUClassifyDataInCSV(uClassifyDataList,"src/Data/UClassify/", "uClasifyData.csv");
			
			} catch (Exception exception) {
			exception.printStackTrace();
			flag = false;

		}
		
		return flag;
		
	}

}
