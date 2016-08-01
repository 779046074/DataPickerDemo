package com.example.datademo;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
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
        needSignInDays.add("2016-7-28");
        needSignInDays.add("2016-7-29");
        needSignInDays.add("2016-7-30");
        needSignInDays.add("2016-7-31");
        needSignInDays.add("2016-8-1");
        needSignInDays.add("2016-8-2");
        needSignInDays.add("2016-8-3");
        needSignInDays.add("2016-8-4");
        signInBo.setNeedSignInDays(needSignInDays);

        ArrayList<String> hasSignedInDays = new ArrayList<>();
        hasSignedInDays.add("2016-7-28");
        hasSignedInDays.add("2016-7-29");
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
                picker.setTodayDisplay(false);
                picker.setHolidayDisplay(false);
                picker.setFestivalDisplay(false);
                picker.setDeferredDisplay(false);

                DPCManager.getInstance().setDecorBG(signInBo.getNeedSignInDays());

                picker.setDPDecor(new DPDecor() {
                    @Override
                    public void drawDecorBG(Canvas canvas, Rect rect, Paint paint, String data) {
                        paint.setColor(Color.rgb(216, 209, 207));
                        paint.setStyle(Paint.Style.FILL);
                        paint.setStrokeWidth(6f);
                        canvas.drawRect(rect, paint);

                        for (String hasSignedInDay : signInBo.getHasSignInDays()) {
                            if (data.equals(hasSignedInDay)){
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2f - 5, paint);

                            }
                        }

                        if (data.equals(today)){
                            paint.setColor(Color.RED);
                            paint.setStyle(Paint.Style.STROKE);
                            RectF rectF = new RectF(rect.left+5, rect.top+5, rect.right-5, rect.bottom-5);
                            canvas.drawArc(rectF, 0, angle, false,paint);
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

                //签到动画
                if (!signInBo.getHasSignInDays().contains(today)){

                    ValueAnimator animator = ValueAnimator.ofFloat(0 ,360);
                    animator.setDuration(1000);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            angle = (float) animation.getAnimatedValue();
                            if (angle >= 360){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, today + "签到成功", Toast.LENGTH_LONG).show();
                                        btnSignIn.setText("已签到");
                                    }
                                });
                                signInBo.getHasSignInDays().add(today);
                            }
                            picker.postInvalidate();
                        }
                    });
                    animator.start();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
