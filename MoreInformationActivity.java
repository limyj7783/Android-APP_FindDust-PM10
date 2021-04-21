package com.example.finedustpm10.finedustpm10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreInformationActivity extends Activity{
    LinearLayout linearLayout;
    ImageView imageViewPM10Condition, imageViewPM25Condition, imageViewO3Condition;
    ImageView imageViewNO2Condition, imageViewCOCondition, imageViewSO2Condition;
    TextView textViewPM10Condition, textViewPM25Condition, textViewO3Condtion;
    TextView textViewNO2Condition, textViewCOCondition, textViewSO2Condition;
    TextView textViewMeasureLocation, textViewPM25NullValue, textViewUpdatedTime;

    String pm10Value, pm10Grade;
    String pm25Value, pm25Grade;
    String khaiGrade;
    String so2Value, so2Grade;
    String o3Value, o3Grade;
    String no2Value, no2Grade;
    String coValue, coGrade;
    String stationName, dataTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinformation);
        LayoutSetting();

        Intent intent = getIntent();
        IntentGetExtra(intent);

        Print();
    }

    public void O3Information(){
        String o3Condition = o3Value+getResources().getString(R.string.ppm_unit);
        textViewO3Condtion.setText(o3Condition);

        switch(o3Grade){
            case "1":
                imageViewO3Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.good));
                break;
            case "2":
                imageViewO3Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.normal));
                break;
            case "3":
                imageViewO3Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.bad));
                break;
            case "4":
                imageViewO3Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.verybad));
                break;
            default:
                imageViewO3Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.cry));
                textViewO3Condtion.setText(R.string.cry);
                break;
        }
    }

    public void NO2Informtion(){
        String no2Condition = no2Value+getResources().getString(R.string.ppm_unit);
        textViewNO2Condition.setText(no2Condition);

        switch (no2Grade){
            case "1":
                imageViewNO2Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.good));
                break;
            case "2":
                imageViewNO2Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.normal));
                break;
            case "3":
                imageViewNO2Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.bad));
                break;
            case "4":
                imageViewNO2Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.verybad));
                break;
            default:
                imageViewNO2Condition.setBackground(ContextCompat.getDrawable(this, R.drawable.cry));
                textViewNO2Condition.setText(R.string.cry);
                break;
        }
    }

    public void COInformation(){
        String coCondition = coValue+getResources().getString(R.string.ppm_unit);
        textViewCOCondition.setText(coCondition);

        switch (coGrade){
            case "1":
                imageViewCOCondition.setBackground(ContextCompat.getDrawable(this, R.drawable.good));
                break;
            case "2":
                imageViewCOCondition.setBackground(ContextCompat.getDrawable(this, R.drawable.normal));
                break;
            case "3":
                imageViewCOCondition.setBackground(ContextCompat.getDrawable(this, R.drawable.bad));
                break;
            case "4":
                imageViewCOCondition.setBackground(ContextCompat.getDrawable(this, R.drawable.verybad));
                break;
            default:
                imageViewCOCondition.setBackground(ContextCompat.getDrawable(this, R.drawable.cry));
                textViewCOCondition.setText(R.string.cry);
                break;
        }
    }

    public void SO2Information(){
        String so2Condition = so2Value+getResources().getString(R.string.ppm_unit);
        textViewSO2Condition.setText(so2Condition);

        switch (so2Grade){
            case "1":
                imageViewSO2Condition.setImageResource(R.drawable.good);
                break;
            case "2":
                imageViewSO2Condition.setImageResource(R.drawable.normal);
                break;
            case "3":
                imageViewSO2Condition.setImageResource(R.drawable.bad);
                break;
            case "4":
                imageViewSO2Condition.setImageResource(R.drawable.verybad);
                break;
            default:
                imageViewSO2Condition.setImageResource(R.drawable.verybad);
                textViewSO2Condition.setText(R.string.cry);
                break;
        }
    }

    public void PM10Information(){
        String pm10Condition = pm10Value+getResources().getString(R.string.pm_unit);
        textViewPM10Condition.setText(pm10Condition);

        switch (pm10Grade){
            case "1":
                imageViewPM10Condition.setImageResource(R.drawable.good);
                break;
            case "2":
                imageViewPM10Condition.setImageResource(R.drawable.normal);
                break;
            case "3":
                imageViewPM10Condition.setImageResource(R.drawable.bad);
                break;
            case "4":
                imageViewPM10Condition.setImageResource(R.drawable.verybad);
                break;
            default:
                imageViewPM10Condition.setImageResource(R.drawable.cry);
                textViewPM10Condition.setText(R.string.cry);
                break;
        }
    }

    public void PM25Information(){
        String pm25Condition = pm25Value+getResources().getString(R.string.pm_unit);
        textViewPM25Condition.setText(pm25Condition);

        switch (pm25Grade){
            case "1":
                imageViewPM25Condition.setImageResource(R.drawable.good);
                break;
            case "2":
                imageViewPM25Condition.setImageResource(R.drawable.normal);
                break;
            case "3":
                imageViewPM25Condition.setImageResource(R.drawable.bad);
                break;
            case "4":
                imageViewPM25Condition.setImageResource(R.drawable.verybad);
                break;
            default:
                imageViewPM25Condition.setImageResource(R.drawable.cry);
                textViewPM25Condition.setText(R.string.cry);
                textViewPM25NullValue.setText(R.string.pm25_null_value);
                break;
        }
    }

    public void KHAIInformation(){
        switch (khaiGrade){
            case "1":
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_good));
                break;
            case "2":
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_normal));
                break;
            case "3":
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_bad));
                break;
            case "4":
                linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.layout_very_bad));
                break;
        }
    }

    public void RelatedInformation(){
        String stationCondition = getResources().getString(R.string.measure_location) + stationName;
        textViewMeasureLocation.setText(stationCondition);
        String dataTimeCondition = getResources().getString(R.string.updated_time) + dataTime;
        textViewUpdatedTime.setText(dataTimeCondition);
    }

    public void IntentGetExtra(Intent intent){
        so2Value = intent.getStringExtra("so2Value");
        so2Grade = intent.getStringExtra("so2Grade");
        o3Value = intent.getStringExtra("o3Value");
        o3Grade = intent.getStringExtra("o3Grade");
        no2Value = intent.getStringExtra("no2Value");
        no2Grade = intent.getStringExtra("no2Grade");
        coValue = intent.getStringExtra("coValue");
        coGrade = intent.getStringExtra("coGrade");
        pm10Value = intent.getStringExtra("pm10Value");
        pm10Grade = intent.getStringExtra("pm10Grade");
        pm25Value = intent.getStringExtra("pm25Value");
        pm25Grade = intent.getStringExtra("pm25Grade");
        khaiGrade = intent.getStringExtra("khaiGrade");
        stationName = intent.getStringExtra("stationName");
        dataTime = intent.getStringExtra("dataTime");
        khaiGrade = intent.getStringExtra("khaiGrade");
    }

    public void Print(){
        KHAIInformation();
        O3Information();
        NO2Informtion();
        COInformation();
        SO2Information();
        PM10Information();
        PM25Information();
        RelatedInformation();
    }

    public void LayoutSetting(){
        linearLayout = (LinearLayout)findViewById(R.id.LinearLayout);
        imageViewPM10Condition = (ImageView)findViewById(R.id.ImageViewPM10Condition);
        imageViewPM25Condition = (ImageView)findViewById(R.id.ImageViewPM25Condition);
        imageViewO3Condition = (ImageView)findViewById(R.id.ImageViewO3Condition);
        imageViewNO2Condition = (ImageView)findViewById(R.id.ImageViewNO2Condition);
        imageViewCOCondition = (ImageView)findViewById(R.id.ImageViewCOCondition);
        imageViewSO2Condition = (ImageView)findViewById(R.id.ImageViewSO2Condition);
        textViewPM10Condition = (TextView)findViewById(R.id.TextViewPM10Condition);
        textViewPM25Condition = (TextView)findViewById(R.id.TextViewPM25Condition);
        textViewO3Condtion = (TextView)findViewById(R.id.TextViewO3Condition);
        textViewNO2Condition = (TextView)findViewById(R.id.TextViewNO2Condition);
        textViewCOCondition = (TextView)findViewById(R.id.TextViewcCOCondition);
        textViewSO2Condition = (TextView)findViewById(R.id.TextViewSO2Condition);
        textViewMeasureLocation = (TextView)findViewById(R.id.TextViewMeasureLocation);
        textViewPM25NullValue = (TextView)findViewById(R.id.TextViewPM25NullValue);
        textViewUpdatedTime = (TextView)findViewById(R.id.TextViewUpdatedTime);
    }
}
