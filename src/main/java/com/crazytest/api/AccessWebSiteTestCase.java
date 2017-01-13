package com.com.crazytest.api;

import com.com.crazytest.config.TestCase;

public class AccessWebSiteTestCase extends TestCase {

	
	public void AccessWebSite() throws Exception {
		String endpoint = String.format("");
		String params = "";
		createHttpClient();
		get(endpoint, params);
		verifyResponseOfGetRequest(200, "");
	}

}
