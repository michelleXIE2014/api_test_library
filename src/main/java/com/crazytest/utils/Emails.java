package com.prodigy.utils;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Emails {
	private static final Logger LOGGER = LogManager.getLogger(Emails.class.getName());
	
	public static String now() {
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime()).toString().replace(" ", "").replace(":", "");
	}
	

	public static String generateUniqueEmail(String baseEmail){
	    String userName = baseEmail.substring(0, baseEmail.indexOf("@"));
	    String domain = baseEmail.substring(baseEmail.indexOf("@"),baseEmail.length());
	    String uniqueEmail = userName + now() + domain;
	    LOGGER.info(uniqueEmail);
	    return uniqueEmail;
	}
}
