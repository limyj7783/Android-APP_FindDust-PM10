package com.example.finedustpm10.finedustpm10;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    LinearLayout linearLayout;
    TextView textViewLocation, textViewDateTime, textViewKHAICondition, textViewAirPollutionInformation;
    TextView textViewPM10Condition, textViewPM25Condition;
    ImageView imageViewKHAICondition, imageViewPM10Condition, imageViewPM25Condition;
    Button buttonAdditionInformation;
    GoogleApiClient googleApi;
    String address="";

    static AirKoreaDataValue airKoreaDataValue = null;

    final String PERMISSION_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    final int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutSetting();

        buttonAdditionInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, MoreInformationActivity.class );
                IntentPutExtra(intent);
                startActivity( intent );
            }
        });

        buildGoogleApiClient();
        getCurrentDateTime();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog().build());
    }

    protected void onStart() {
        super.onStart();
        googleApi.connect();

        if(airKoreaDataValue !=null) {
            KHAIInformation();
            PM10Information();
            PM25Information();
        }
}

    protected void onStop() {
        googleApi.disconnect();
        super.onStop();
    }

    synchronized void buildGoogleApiClient(){
        googleApi =  new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(LocationServices.API)
                .build();
    }

    public void onConnected(Bundle bundle){
        if(getCurrentLocation() && airKoreaDataValue==null) {
            airKoreaDataValue = new AirKoreaDataValue();
            //데이터값 저장
            TMcoord(); //현재 위치로 TM좌표값 얻기
            MeasureAddress(); //TM좌표로 '측정소명' 얻기
            AirPollution(); //'측정소명'으로 대기 오염 정보 얻기
            //데이터 출력
            KHAIInformation();
            PM10Information();
            PM25Information();
        }else if(airKoreaDataValue!=null){

        }else{
//            Toast.makeText(this, "네트워크, GPS 사용설정을 확인해주세요",Toast.LENGTH_LONG).show();
        }
    }


    public void onConnectionSuspended(int cause){
        googleApi.connect();
    }

    public void onConnectionFailed(ConnectionResult result){
        Toast.makeText(this, "네트워크, GPS 사용설정을 확인해주세요",Toast.LENGTH_LONG).show();
    }

    public  boolean getCurrentLocation() {
        Geocoder geoCoder = new Geocoder(this);
        double latitude;//위도
        double longitude;//경도
        List<Address> list=null;
        Location currentLocation;
        String location="";

        if(checkPermission()){
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApi);
            latitude = currentLocation.getLatitude();
            longitude = currentLocation.getLongitude();

            try {
                list = geoCoder.getFromLocation(latitude, longitude, 1);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(list!=null) {
                if(list.get(0).getAdminArea()!=null){//서울특별시, 경기도
                    address += list.get(0).getAdminArea()+" ";
                }
                if(list.get(0).getLocality()!=null){//금천구, 성남시
                    address += list.get(0).getLocality()+" ";
                    location += list.get(0).getLocality()+" ";
                }
                if(list.get(0).getSubLocality()!=null){//null, 수정구
                    address += list.get(0).getSubLocality()+" ";
                    location += list.get(0).getSubLocality()+" ";
                }
                if(list.get(0).getThoroughfare()!=null){//시흥4동, 복정동
                    String tempStr="";
                    String tempAddr=list.get(0).getThoroughfare();
                    location += list.get(0).getThoroughfare();

                    for(int i=0; i<tempAddr.length(); i++){
                        char temp = tempAddr.charAt(i);

                        if((int)temp<48 || (int)temp>57){
                            tempStr+=temp;
                        }
                    }
                    address+=tempStr;
                }
                textViewLocation.setText(location);
            }
            return true;
        }
        return false;
    }

    public boolean checkPermission(){
        return (ActivityCompat.checkSelfPermission(MainActivity.this, PERMISSION_LOCATION)==PERMISSION_GRANTED);
    }

    public void getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        String dateTime;
        int month = calendar.get(Calendar.MONTH)+1;
        int hour = calendar.get(Calendar.HOUR);

        String amPm;
        if(calendar.get(Calendar.AM_PM)==1){
            amPm = "PM";
            if(hour==0){
                hour+=12;
            }
        }else{
            amPm = "AM";
        }

        dateTime = calendar.get(Calendar.YEAR)+":"+month+":"+calendar.get(Calendar.DATE)+"     "+
        hour+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND) +" "+
        amPm;

        textViewDateTime.setText(dateTime);
    }

    public void onLocationChanged(Location location){

    }

    public void KHAIInformation(){
        String khaiCondition = getResources().getString(R.string.air_pollution) +airKoreaDataValue.getKhaiValue()+getResources().getString(R.string.khai_unit);
        textViewAirPollutionInformation.setText(khaiCondition);

        switch (airKoreaDataValue.getKhaiGrade()){
            case "1":
                textViewKHAICondition.setText(R.string.good);
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_good));
                imageViewKHAICondition.setBackground(ContextCompat.getDrawable(this, R.drawable.good));
                break;
            case "2":
                textViewKHAICondition.setText(R.string.normal);
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_normal));
                imageViewKHAICondition.setBackground(ContextCompat.getDrawable(this, R.drawable.normal));
                break;
            case "3":
                textViewKHAICondition.setText(R.string.bad);
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_bad));
                imageViewKHAICondition.setBackground(ContextCompat.getDrawable(this, R.drawable.bad));
                break;
            case "4":
                textViewKHAICondition.setText(R.string.very_bad);
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_very_bad));
                imageViewKHAICondition.setBackground(ContextCompat.getDrawable(this, R.drawable.verybad));
                break;
        }
    }

    public void PM10Information(){
        String pm10Condition=airKoreaDataValue.getPm10Value()+getResources().getString(R.string.pm_unit);
        textViewPM10Condition.setText(pm10Condition);

        switch (airKoreaDataValue.getPm10Grade()){
            case "1":
                imageViewPM10Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.good));
                break;
            case "2":
                imageViewPM10Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.normal));
                break;
            case "3":
                imageViewPM10Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.bad));
                break;
            case "4":
                imageViewPM10Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.verybad));
                break;
            default:
                imageViewPM10Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.cry));
                textViewPM10Condition.setText(R.string.cry);
                break;
        }
    }

    public void PM25Information(){
        String pm25Condition=airKoreaDataValue.getPm25Value()+getResources().getString(R.string.pm_unit);
        textViewPM25Condition.setText(pm25Condition);

        switch (airKoreaDataValue.getPm25Grade()){
            case "1":
                imageViewPM25Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.good));
                break;
            case "2":
                imageViewPM25Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.normal));
                break;
            case "3":
                imageViewPM25Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.bad));
                break;
            case "4":
                imageViewPM25Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.verybad));
                break;
            default:
                imageViewPM25Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.cry));
                textViewPM25Condition.setText(R.string.cry);
                break;
        }
    }

    public void TMcoord(){
        TMCoodinate tmCoodinate = new TMCoodinate();
        tmCoodinate.SetLocation(address);
        tmCoodinate.CreateXML();
        tmCoodinate.xmlPaser.SetElement("tmX");
        airKoreaDataValue.setTmX(tmCoodinate.xmlPaser.Parsing());
        tmCoodinate.xmlPaser.SetElement("tmY");
        airKoreaDataValue.setTmY(tmCoodinate.xmlPaser.Parsing());
    }

    public void MeasureAddress(){
        MeasureLocation measureLocation = new MeasureLocation();
        measureLocation.setTmX(airKoreaDataValue.getTmX());
        measureLocation.setTmY(airKoreaDataValue.getTmY());
        measureLocation.CreateXML();
        measureLocation.xmlPaser.SetElement("stationName");
        airKoreaDataValue.setStationName(measureLocation.xmlPaser.Parsing());
    }

    public void AirPollution(){
        AirPollutionMeasure airPollutionMeasure = new AirPollutionMeasure();
        airPollutionMeasure.SetLocation(airKoreaDataValue.getStationName());
        airPollutionMeasure.CreateXML();

        airPollutionMeasure.xmlPaser.SetElement("dataTime");
        airKoreaDataValue.setDataTime(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("so2Value");
        airKoreaDataValue.setSo2Value(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("coValue");
        airKoreaDataValue.setCoValue(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("o3Value");
        airKoreaDataValue.setO3Value(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("no2Value");
        airKoreaDataValue.setNo2Value(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("pm10Value");
        airKoreaDataValue.setPm10Value(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("pm25Value");
        airKoreaDataValue.setPm25Value(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("khaiValue");
        airKoreaDataValue.setKhaiValue(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("khaiGrade");
        airKoreaDataValue.setKhaiGrade(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("so2Grade");
        airKoreaDataValue.setSo2Grade(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("coGrade");
        airKoreaDataValue.setCoGrade(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("o3Grade");
        airKoreaDataValue.setO3Grade(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("no2Grade");
        airKoreaDataValue.setNo2Grade(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("pm10Grade");
        airKoreaDataValue.setPm10Grade(airPollutionMeasure.xmlPaser.Parsing());
        airPollutionMeasure.xmlPaser.SetElement("pm25Grade");
        airKoreaDataValue.setPm25Grade(airPollutionMeasure.xmlPaser.Parsing());
    }

    public void IntentPutExtra(Intent intent){
        intent.putExtra("so2Value", airKoreaDataValue.getSo2Value());
        intent.putExtra("so2Grade", airKoreaDataValue.getSo2Grade());
        intent.putExtra("o3Value", airKoreaDataValue.getO3Value());
        intent.putExtra("o3Grade", airKoreaDataValue.getO3Grade());
        intent.putExtra("no2Value", airKoreaDataValue.getNo2Value());
        intent.putExtra("no2Grade", airKoreaDataValue.getNo2Grade());
        intent.putExtra("coValue", airKoreaDataValue.getCoValue());
        intent.putExtra("coGrade", airKoreaDataValue.getCoGrade());
        intent.putExtra("dataTime", airKoreaDataValue.getDataTime());
        intent.putExtra("stationName", airKoreaDataValue.getStationName());
        intent.putExtra("khaiGrade", airKoreaDataValue.getKhaiGrade());
        intent.putExtra("pm10Value", airKoreaDataValue.getPm10Value());
        intent.putExtra("pm10Grade", airKoreaDataValue.getPm10Grade());
        intent.putExtra("pm25Value", airKoreaDataValue.getPm25Value());
        intent.putExtra("pm25Grade", airKoreaDataValue.getPm25Grade());
    }

    public void LayoutSetting(){
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        textViewLocation = (TextView) findViewById(R.id.TextViewLocaion);
        textViewDateTime = (TextView) findViewById(R.id.TextViewDateTime);
        textViewKHAICondition = (TextView) findViewById(R.id.TextViewKHAICondition);
        textViewPM10Condition = (TextView)findViewById(R.id.TextViewPM10Condition);
        textViewPM25Condition = (TextView)findViewById(R.id.TextViewPM25Condition);
        textViewAirPollutionInformation = (TextView) findViewById(R.id.TextVIewAirPollutionInformation);
        imageViewKHAICondition = (ImageView) findViewById(R.id.ImageViewKHAICondition);
        imageViewPM10Condition = (ImageView)findViewById(R.id.ImageViewPM10Condition);
        imageViewPM25Condition = (ImageView)findViewById(R.id.ImageViewPM25Condition);
        buttonAdditionInformation = (Button) findViewById(R.id.ButtonAdditionInformation);
    }
}
