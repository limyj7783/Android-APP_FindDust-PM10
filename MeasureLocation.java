package com.example.finedustpm10.finedustpm10;//TM��ǥ�� ������ �ִ� ������ ���
import java.net.URLEncoder;

public class MeasureLocation extends XML{
	private String tmX;
	private String tmY;
	
	String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?tmX=";
	String parameter1="&tmY=";
	String parameter2="&pageNo=1&numOfRows=1&ServiceKey=";

	public String getTmX() {
		return tmX;
	}

	public void setTmX(String tmX) {
		try{
			this.tmX = URLEncoder.encode(tmX, "UTF-8");
		}catch(Exception e){
			
		}
	}

	public String getTmY() {
		return tmY;
	}

	public void setTmY(String tmY) {
		try{
			this.tmY = URLEncoder.encode(tmY, "UTF-8");
		}catch(Exception e){
			
		}
	}
	
	public String SetURLAddress(){
		return addr+tmX+parameter1+tmY+parameter2+apiKey;
	}
}
