package com.httpfaulty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



public class HttpFaulty {

	final String GET = "get";
	final String POST = "post";
	
	HttpGet httpGet;
	HttpPost httpPost; 
	
	HashMap<String,String> reqHeader; 
	String httpMethod,reqUrl;
	
	Handler classHandler;
	HttpParams httpParams ;
	HttpResponse httpResponse ;
	DefaultHttpClient defaultClient ;	
	
	Iterator iterator;
	int retryCount,retryInterval;
	boolean infiniteRetry,isException = false,doRetry = true;
	
	
	public HttpFaulty(String url,String method,HashMap<String,String>  headerHash,Handler handler,int retry,int retryInt){
	
/*
 * parameter Specification:
 * url: http url where you want to make request
 * header : it is hash of string, if you wish to add request header
 * handler: which will excetue code in current activity when exception occurs
 * retry: how many times you want to retry. if you want to make infinite retry then pass negative value.
 * retryInt: time of interval between each try. it is in milliseconds 
 */
		
		
		reqUrl = url;
		reqHeader = headerHash;
		iterator = headerHash != null ? headerHash.entrySet().iterator() : null;
		httpMethod = method;
		classHandler = handler;
		httpParams = new BasicHttpParams();		
		retryCount = retry;
		retryInterval = retryInt;
		infiniteRetry = retryCount < 0 ? true : false;
		
		if(method.equals(GET)){
			
			httpGet = new HttpGet(url);
			
			if(iterator != null){
				
				while (iterator.hasNext()) {
					
			        Map.Entry<String,String> pairs = (Map.Entry)iterator.next();
			        httpGet.setHeader(pairs.getKey(),pairs.getValue());
			        iterator.remove(); // avoids a ConcurrentModificationException
			        
			    }
				
			}
			 
			
		}else if(method.equals(POST)){
			
			httpPost = new HttpPost(url);
			
			if(iterator != null){
				
				 while (iterator.hasNext()) {
					 
				        Map.Entry<String,String> pairs = (Map.Entry)iterator.next();
				        httpPost.setHeader(pairs.getKey(),pairs.getValue());
				        iterator.remove(); // avoids a ConcurrentModificationException
				        
				 }
				
			}
			
		}
		
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000); // Connection
		HttpConnectionParams.setSoTimeout(httpParams, 30000); // Socket

		defaultClient =  new DefaultHttpClient(httpParams);
		
	}
	
	public void execute() {

		HttpResponse httpResponse = null;

		while (doRetry) {
			
			try {
				
				if (isException) {
					Thread.sleep(retryInterval);
				}

				if (retryCount > 0) {

					if (httpMethod.equals(GET)) {

						httpResponse = defaultClient.execute(httpGet);

					} else if (httpMethod.equals(POST)) {

						httpResponse = defaultClient.execute(httpPost);

					}

					HttpEntity httpEntity = httpResponse.getEntity();
					String stringEntity = EntityUtils.toString(httpEntity);

					Log.i("MyFaultResponse", stringEntity);

					Message responseMessage = new Message();
					Bundle msgBundle = new Bundle();
					msgBundle.putBoolean("isException", false);
					msgBundle.putString("response", stringEntity);
					responseMessage.setData(msgBundle);

					isException = false;
					doRetry = false;
					retryCount = 0;
					classHandler.sendMessage(responseMessage);
				}

			} catch (Exception e) {

				Log.i("MyFaultException", e.getMessage());
				isException = true;
				Message responseMessage = new Message();
				Bundle msgBundle = new Bundle();
				msgBundle.putBoolean("isException", true);
				msgBundle.putString("exceptionMsg", e.getMessage());
				responseMessage.setData(msgBundle);
				classHandler.sendMessage(responseMessage);

			}
		}

	}	
	
}
