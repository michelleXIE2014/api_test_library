package com.crazytest.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.prodigy.utils.Emails;

@SuppressWarnings("deprecation")
public class TestCase {
	private static final Logger LOGGER = LogManager.getLogger(TestCase.class.getName());

	private String hostUrl = Urls.getWebsiteUrl();
	private DefaultHttpClient httpClient = new DefaultHttpClient();
	private HttpGet getRequest;
	private HttpPost postRequest;
	private HttpDelete deleteRequest;
	private HttpPut putRequest;
	private String userID;
	private String authToken;
	private String classID;
	private String classCode;
	private String studentID;
	private String studentUsername;
	private String studentPassword;
	
	public String register(String name, String email,String userPassword) throws Exception {
		String uniqeEmail = Emails.generateUniqueEmail(email);
		String endpoint = String.format(yourapiurl);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", uniqeEmail));
		params.add(new BasicNameValuePair("password", userPassword));
		
		createHttpClient();
		postWithoutToken(endpoint, params);
		HttpResponse httpResponse = getResponseOfPostRequest();
		String httpResponseStringJsonString = EntityUtils.toString(httpResponse.getEntity());
		LOGGER.info("Http response registration ={}", httpResponseStringJsonString);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(httpResponseStringJsonString);
		JSONObject jsonObject = (JSONObject) obj;
		Object userID = jsonObject.get("user_id");
		LOGGER.info("userID={}", userID);
        
		return uniqeEmail;
	}

	protected void login(String userName, String userPass) throws Exception {
		String endpoint = "/your-api/v2/login/";
		String params = String.format("?username=%s&password=%s", userName, userPass);

		createHttpClient();

		get(endpoint, params);
		HttpResponse httpResponse = getResponseOfGetRequest();
		String httpResponseStringJsonString = EntityUtils.toString(httpResponse.getEntity());
		LOGGER.info("Http response={}", httpResponseStringJsonString);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(httpResponseStringJsonString);
		JSONObject jsonObject = (JSONObject) obj;
		Object userID = jsonObject.get("objectID");
		Object authToken = jsonObject.get("authToken");
		JSONArray classes = (JSONArray) jsonObject.get("classes");
        JSONObject class1 = (JSONObject) classes.get(0);
		LOGGER.info("userID={}", userID.toString());
		LOGGER.info("authToken={}", authToken.toString());

		LOGGER.info("classID={}", class1.get("id"));
		this.userID = userID.toString();
		this.authToken = authToken.toString();
		this.classID = class1.get("id").toString();

	}
	
	
	
	protected String getUserID() {
		return this.userID;

	}

	protected String getAuthToken() {
		return this.authToken;

	}

	protected String getClassID() {
		return this.classID;

	}
	
	protected String getClassCode() {
		return this.classCode;

	}
	
	protected String getStudentID(){
		return this.studentID;
	}

	protected String getHost() {
		return Urls.getWebsiteUrl();
	}

	protected DefaultHttpClient createHttpClient() {
		return this.httpClient;
	}

	protected void get(String endpoint) {
		HttpGet getRequest = new HttpGet(hostUrl + endpoint);
		getRequest.setHeader("Content-Type", "text/html");
		getRequest.setHeader("User-Agent", "Safari");
		this.getRequest = getRequest;
		LOGGER.info("request: {}", getRequest.getRequestLine());

	}
	
	protected void get(String endpoint, String params) {
		HttpGet getRequest = new HttpGet(hostUrl + endpoint + params);
		getRequest.setHeader("Content-Type", "text/html");
		getRequest.setHeader("User-Agent", "Safari");
		this.getRequest = getRequest;
		LOGGER.info("request: {}", getRequest.getRequestLine());

	}
	
	protected void getWithCredential(String endpoint, String params) throws Exception {
		String endpointString = hostUrl + endpoint + "?authToken=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID + params;
		HttpGet getRequest = new HttpGet(endpointString);
		getRequest.setHeader("Content-Type", "text/html");
		getRequest.setHeader("User-Agent", "Safari");
		this.getRequest = getRequest;
		LOGGER.info("request: {}", getRequest.getRequestLine());
	}

	protected void post(String endpoint, List<NameValuePair> params) throws Exception {
		String endpointString = hostUrl + endpoint + "?auth-key=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID;

		URL url = new URL(endpointString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		HttpPost postRequest = new HttpPost(uri);
		postRequest.setEntity(new UrlEncodedFormEntity(params));

		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader("User-Agent", "Safari");
		this.postRequest = postRequest;
		LOGGER.info("request: {}", postRequest.getRequestLine());

	}

	protected void postWithoutToken(String endpoint, List<NameValuePair> params) throws Exception {
		String endpointString = hostUrl + endpoint;

		URL url = new URL(endpointString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		HttpPost postRequest = new HttpPost(uri);
		postRequest.setEntity(new UrlEncodedFormEntity(params));

		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader("User-Agent", "Safari");
		this.postRequest = postRequest;
		LOGGER.info("request: {}", postRequest.getRequestLine());

	} 
	protected void postWithCredential(String endpoint, String params)
			throws UnsupportedEncodingException, URISyntaxException, MalformedURLException {
		String endpointString = hostUrl + endpoint + "?auth-key=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID + params;

		URL url = new URL(endpointString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		HttpPost postRequest = new HttpPost(uri);

		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader("User-Agent", "Safari");
		this.postRequest = postRequest;

		LOGGER.info("request: {}", postRequest.getRequestLine());
		LOGGER.info("request: {}", postRequest.getParams().getParameter("licenseeID"));
	}

	protected void postWithCredential(String endpoint, List<NameValuePair> params)
			throws UnsupportedEncodingException, URISyntaxException, MalformedURLException {
		String endpointString = hostUrl + endpoint + "?auth-key=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID;

		URL url = new URL(endpointString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		HttpPost postRequest = new HttpPost(uri);
		postRequest.setEntity(new UrlEncodedFormEntity(params));

		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader("User-Agent", "Safari");
		this.postRequest = postRequest;

		LOGGER.info("request:{}", postRequest.getRequestLine());
		LOGGER.info("request:{}", postRequest.getParams().getParameter("licenseeID"));
	}
	
	protected void postWithCredential(String endpoint, StringEntity params)
			throws UnsupportedEncodingException, URISyntaxException, MalformedURLException {
		String endpointString = hostUrl + endpoint + "?auth-key=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID;

		URL url = new URL(endpointString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		HttpPost postRequest = new HttpPost(uri);
		postRequest.setEntity(params);
		LOGGER.info("post request entity={}", postRequest);
		postRequest.setHeader("Content-Type", "application/json");
		postRequest.setHeader("User-Agent", "Safari");
		this.postRequest = postRequest;

		LOGGER.info("request:{}", postRequest.getRequestLine());
		LOGGER.info("request:{}", postRequest.getParams().getParameter("licenseeID"));
	}

	protected void putWithCredential(String endpoint, List<NameValuePair> params)
			throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
		String endpointString = hostUrl + endpoint + "?auth-key=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID;

		URL url = new URL(endpointString);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef());

		HttpPut putRequest = new HttpPut(uri);
		putRequest.setEntity(new UrlEncodedFormEntity(params));

		putRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		putRequest.setHeader("User-Agent", "Safari");
		this.putRequest = putRequest;

		LOGGER.info("request: {}", putRequest.getRequestLine());
	}

	protected void putWithCredential(String endpoint, String params) {
		String endpointString = hostUrl + endpoint + "?authToken=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID + params;
		HttpPut putRequest = new HttpPut(endpointString);
		putRequest.setHeader("Content-Type", "text/html");
		putRequest.setHeader("User-Agent", "Safari");
		this.putRequest = putRequest;
		LOGGER.info("request: {}", putRequest.getRequestLine());
	}

	protected void deleteWithCredential(String endpoint, String params) {
		String endpointString = hostUrl + endpoint + "?authToken=" + this.authToken + "&token=" + this.authToken
				+ "&userID=" + this.userID + params;
		HttpDelete deleteRequest = new HttpDelete(endpointString);

		deleteRequest.setHeader("Accept", "*/*");
		deleteRequest.setHeader("User-Agent", "Safari");
		this.deleteRequest = deleteRequest;
		LOGGER.info("request: {}", deleteRequest.getRequestLine());
	}

	protected HttpResponse getResponseOfGetRequest() throws ClientProtocolException, IOException {
		return this.httpClient.execute(this.getRequest);
	}

	protected void verifyResponseOfGetRequest(int code, String message) {
		try {
			HttpResponse httpResponse = getResponseOfGetRequest();
			int actualCode = httpResponse.getStatusLine().getStatusCode();
			String actualContent = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.info("actual code = {}  actual content = {}", actualCode, actualContent);
			Assert.assertTrue(actualCode == code, String.format("actual code = %s expected code = %s", actualCode, code));
			Assert.assertTrue(actualContent.contains(message), String.format("actual content = %s expected content = %s", actualContent, message));
		} catch (IOException e) {
			LOGGER.warn(e);
		}

	}

	protected HttpResponse getResponseOfPostRequest() throws ClientProtocolException, IOException {
		return this.httpClient.execute(this.postRequest);
	}

	protected void verifyResponseOfPostRequest(int code, String message) {
		try {
			HttpResponse httpResponse = getResponseOfPostRequest();
			int actualCode = httpResponse.getStatusLine().getStatusCode();
			String actualContent = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.info("actual code = {}  actual content = {}", actualCode, actualContent);
			Assert.assertTrue(actualCode == code, String.format("actual code = %s expected code = %s", actualCode, code));
			Assert.assertTrue(actualContent.contains(message), String.format("actual content = %s expected content = %s", actualContent, message));
		} catch (IOException e) {
			LOGGER.warn(e);
		}

	}

	protected HttpResponse getResponseOfDeleteRequest() throws ClientProtocolException, IOException {
		return this.httpClient.execute(this.deleteRequest);
	}

	protected void verifyResponseOfDeleteRequest(int code, String message) {
		try {
			HttpResponse httpResponse = getResponseOfDeleteRequest();
			int actualCode = httpResponse.getStatusLine().getStatusCode();
			String actualContent = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.info("actual code = {}  actual content = {}", actualCode, actualContent);
			Assert.assertTrue(actualCode == code, String.format("actual code = %s expected code = %s", actualCode, code));
			Assert.assertTrue(actualContent.contains(message), String.format("actual content = %s expected content = %s", actualContent, message));
		} catch (IOException e) {
			LOGGER.warn(e);
		}

	}

	protected HttpResponse getResponseOfPutRequest() throws ClientProtocolException, IOException {
		return this.httpClient.execute(this.putRequest);
	}

	protected void verifyResponseOfPutRequest(int code, String message) {
		try {
			HttpResponse httpResponse = getResponseOfPutRequest();
			int actualCode = httpResponse.getStatusLine().getStatusCode();
			String actualContent = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.info("actual code = {}  actual content = {}", actualCode, actualContent);
			Assert.assertTrue(actualCode == code, String.format("actual code = %s expected code = %s", actualCode, code));
			Assert.assertTrue(actualContent.contains(message), String.format("actual content = %s expected content = %s", actualContent, message));
		} catch (IOException e) {
			LOGGER.warn(e);
		}
	}

	protected void closeClient() {
		this.httpClient.close();
	}

}
