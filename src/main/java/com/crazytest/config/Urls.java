package com.com.crazytest.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Urls {
	private static final Logger LOGGER = LogManager.getLogger(Urls.class.getName());
	private static final String WEBSITE_URL_DEV = "https://dev.yourcompany.org/";
	private static final String WEBSITE_URL_STAGING = "https://www.yourcompany.org/";
	private static final String WEBSITE_URL_PROD = "https://www.yourcompany.com/";
	private static final String SONAR_REPORT_URL = "http://localhost:9000/overview/debt?id=com.prodigy%3Aprodigy-automation-tests";

	public static String getWebsiteUrl() {
		String env = System.getProperty("env");
		LOGGER.info("current environment is {}", env);
		String WEBSITE_URL = WEBSITE_URL_DEV;
		switch (env.toLowerCase()) {
		case "staging":
			WEBSITE_URL = WEBSITE_URL_STAGING;
			break;
		case "prod":
			WEBSITE_URL = WEBSITE_URL_PROD;
			break;
		default:
			LOGGER.info("not support yet");
		}
		LOGGER.info("WEBSITE Url is {}", WEBSITE_URL);
		return WEBSITE_URL;
	}

	public static String getSonarReportUrl() {
		return SONAR_REPORT_URL;
	}
}
