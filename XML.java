package com.example.finedustpm10.finedustpm10;

import android.content.Intent;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public abstract class XML {
	protected String location="";
	private String xml="";	
	private String addr="";
	protected String apiKey="MxNgCYUFO9eUOZLFOjDh%2FDYtOtc2LtS%2FWHfp5RCrn37cOe3zincTZOa3THGB9hIkJRBLjEqz9konYY82FBStig%3D%3D";
	protected XMLPaser xmlPaser = new XMLPaser();

	public void CreateXML(){
		try{			
			location = URLEncoder.encode(location, "UTF-8");
			 
			addr = SetURLAddress();
			 
			URL url = new URL(addr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            
            BufferedReader br;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            
            while(true){
            	String str = br.readLine();
            	if(str==null)
            		break;
            	sb.append(str);
            }
            
            br.close();
            conn.disconnect();
            xml = sb.toString();
            xmlPaser.SetXML(xml);
		}catch(Exception e){

		}

	}
	
	public void SetLocation(String str){
		location = str;
	}
	public String GetLocation(){
		return location;
	}
	public String GetXML(){
		return xml;
	}

	public abstract String SetURLAddress();
	
}
