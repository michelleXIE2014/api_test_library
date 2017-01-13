package com.craytest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.prodigy.api.AccessWebSiteTestCase;

public class AccessingWebSiteTest extends AccessWebSiteTestCase {

	@Test
	public void accessingWebSiteTest() throws Exception {
		AccessWebSite();
	}
	
	@AfterClass
	public void tearDown(){
		super.closeClient();
	}
	
}



