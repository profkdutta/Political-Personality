package com.twitter;

import java.util.List;

import com.cybozu.labs.langdetect.DetectorFactory;

import twitter4j.Status;

public class MainApp {
	
	
	
	public static String conkey="iemoR9Zr5Qb4uuOgwdxDaYgWa";
	public static String consec="z2uEJeihKAoNy4SxkDNZZQ4QbqcDq3AnGRfIAXO6eytrGWAgXK";
	public static String tokkey="160104416-lyUTQSbuuN19UXNBR96CFu1H9TtgCBeMQyOQh6GD";
	public static String toksec="rdzMy6tLCiwEbXW9vrO16tTd6MCXjMMeCF2NQdWw1TqzY";
	public static List<String> twitterHandleList;
	public static List<String> thandleNames;
	public static List<Status> userTweets;
	public static String twitterHandle;
	
	public static void main(String[] args) throws Exception {
		DetectorFactory.loadProfile(System.getProperty("user.dir")+"/profiles/");
		Extract.intializeHandles();
		Extract.extractTwitterFeed(conkey,consec,tokkey,toksec);
		
	}

}
