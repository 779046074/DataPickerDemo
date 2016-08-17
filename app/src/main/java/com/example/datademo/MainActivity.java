package com.example.datademo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.utils.MeasureUtil;
import cn.aigestudio.datepicker.views.DatePicker;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn;
    private Handler handler = new Handler();
    private float angle = 0.0f;
    private SignInBo signInBo = new SignInBo();
    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        today = year + "-" + month + "-" + day;

        ArrayList<String> needSignInDays = new ArrayList<>();

        needSignInDays.add("2016-8-1");
        needSignInDays.add("2016-8-2");
        needSignInDays.add("2016-8-3");
        needSignInDays.add("2016-8-4");
        needSignInDays.add("2016-8-5");
        needSignInDays.add("2016-8-6");
        needSignInDays.add("2016-8-7");
        needSignInDays.add("2016-8-8");
        needSignInDays.add("2016-8-9");
        needSignInDays.add("2016-8-10");
        needSignInDays.add("2016-8-11");
        needSignInDays.add("2016-8-17");
        needSignInDays.add("2016-8-18");
        needSignInDays.add("2016-8-19");
        signInBo.setNeedSignInDays(needSignInDays);

        ArrayList<String> hasSignedInDays = new ArrayList<>();
        hasSignedInDays.add("2016-8-1");
        hasSignedInDays.add("2016-8-2");
        hasSignedInDays.add("2016-8-3");
        hasSignedInDays.add("2016-8-4");
        hasSignedInDays.add("2016-8-5");
        hasSignedInDays.add("2016-8-6");
        hasSignedInDays.add("2016-8-7");
        hasSignedInDays.add("2016-8-8");
        hasSignedInDays.add("2016-8-9");
        hasSignedInDays.add("2016-8-17");
        hasSignedInDays.add("2016-8-18");
        hasSignedInDays.add("2016-8-19");
        signInBo.setHasSignInDays(hasSignedInDays);

        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.show();
                final DatePicker picker = new DatePicker(MainActivity.this);
                picker.setDate(year, month);
                picker.setMode(DPMode.SINGLE);
                picker.setHolidayDisplay(false);
                picker.setFestivalDisplay(false);
                picker.setDeferredDisplay(false);

                DPCManager.getInstance().setDecorBG(signInBo.getNeedSignInDays());

                picker.setDPDecor(new DPDecor() {
                    @Override
                    public void drawDecorBG(Canvas canvas, Rect rect, Paint paint, String data) {
                        paint.setColor(ContextCompat.getColor(MainActivity.this, R.color.bg_gray));
                        paint.setStyle(Paint.Style.FILL);
                        paint.setStrokeWidth(6f);
                        canvas.drawRect(rect, paint);

                        for (String hasSignedInDay : signInBo.getHasSignInDays()) {
                            if (data.equals(hasSignedInDay)){
                                paint.setColor(ContextCompat.getColor(MainActivity.this, R.color.bg_cyne));
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawCircle(rect.centerX(), rect.centerY(), MeasureUtil.dp2px(MainActivity.this, 16), paint);

                            }
                        }

                        //恢复画笔
                        paint.setStrokeWidth(1f);
                        paint.setStyle(Paint.Style.FILL);

                    }
                });

                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        if (signInBo.getNeedSignInDays().contains(date)){
                            if (signInBo.getHasSignInDays().contains(date)){
                                Toast.makeText(MainActivity.this, date + "已签到", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, date + "未签到", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //TODO 不处理

                        }
                    }
                });


                alertDialog.setContentView(picker);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
