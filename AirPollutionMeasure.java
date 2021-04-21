package com.example.finedustpm10.finedustpm10;

//�����Һ� �ǽð� �������� ��ȸ
//Update Time
//so2Value, coValue, o3Value, no2Value, pm10Value, pm25Value, khaiValue,
//so2Grade, coGrade, o3Grade, no2Grade, pm10Grade, pm25Grade, khaiGrade
//	->1=Good, 2=Normal, 3=Bad, 4=VeryBad
public class AirPollutionMeasure extends XML{

	String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName=";
	String parameter1="&dataTerm=month&pageNo=1&numOfRows=1&ServiceKey=";
	String parameter2="&ver=1.3";
	
	public String SetURLAddress(){
		return addr+location+parameter1+apiKey+parameter2;
	}
}
