package com.crazytest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.prodigy.config.TestCase;

public class GetLoginTest extends TestCase {
	private static final Logger LOGGER = LogManager.getLogger(GetLoginTest.class.getName());


	@Test
	public void loginAsTeacherWithCorrectCredential() throws Exception {
		String email = login(username, password);
		LOGGER.info("unique email = {}", email);
		LOGGER.info("getUserID()={}", getUserID());
		LOGGER.info("getAuthToken()={}", getAuthToken());
		LOGGER.info("getClassID()={}", getClassID());
		LOGGER.info("getClassCode()={}", getClassCode());
		LOGGER.info("getStudentID()={}", getStudentID());
		LOGGER.info("getStudentUsername()={}", getStudentUsername());
		LOGGER.info("getStudentpassword()={}", getStudentPassword());
		verifyResponseOfGetRequest(200, "");
	}
	
	@AfterClass
	public void tearDown(){
		super.closeClient();
	}
	
}
