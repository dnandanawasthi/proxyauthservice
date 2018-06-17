package com.authservice.common;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class CustomHttpClient {

    private static final String EMPTY_STRING = "";

    public static String client(final String url) {

        try {

            // create HTTP Client
            HttpClient httpClient = HttpClientBuilder.create().build();

            String finalUrl = "http://localhost:8082/"+url;
            System.out.println("CustomHttpClient:client----> "+finalUrl);
            // Create new getRequest with below mentioned URL
            HttpGet getRequest = new HttpGet(finalUrl);

            // Add additional header to getRequest which accepts application/xml data
            getRequest.addHeader("accept", "application/json");

            // Execute your request and catch response
            HttpResponse response = httpClient.execute(getRequest);

            // Check for HTTP response code: 200 = success
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            String result = EntityUtils.toString(response.getEntity());
            System.out.println("response is -------------------> "+ result);
            // Get-Capture Complete application/xml body response

            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY_STRING;
    }

}




