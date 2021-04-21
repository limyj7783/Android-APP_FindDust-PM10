package com.example.finedustpm10.finedustpm10;//�������� TM ��ǥ�� ���

public class TMCoodinate extends XML{

	String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?umdName=";
	String parameter1="&pageNo=1&numOfRows=10&ServiceKey=";

	public String SetURLAddress(){
		return addr+location+parameter1+apiKey;
	}
}
