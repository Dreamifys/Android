package com.example.administrator.android_move;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.os.Handler;
import android.os.Message;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    public Thread t;
    public  static int XPoint = 60;
    public  static int YPoint = 760;
    public  static int XScale = 8;  //刻度长度
    public  static int YScale = 33;
    public  static int XLength = 380;
    public  static int YLength = 660;

    private int MaxDataSize = XLength / XScale;

    public  static List<Integer> data = new ArrayList<Integer>();
    public  static List<Integer> datay = new ArrayList<Integer>();
    public  static List<Integer> dataz = new ArrayList<Integer>();



    private String[] YLabel = new String[YLength / YScale];

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == 0x1234){
                MyView.this.invalidate();
            }
        };
    };
    public MyView(Context context) {
        super(context);
    }



    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for(int i=0; i<YLabel.length; i++){
            YLabel[i] = (i - 9) + " ";
        }
        data.clear();
        datay.clear();
        dataz.clear();
        t = new Thread(new Runnable() {

            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(data.size() >= MaxDataSize){
                        data.remove(0);
                    }

                    data.add(((MyApplication)getContext().getApplicationContext()).getX());
                    if(datay.size() >= MaxDataSize){
                        datay.remove(0);
                    }

                    datay.add(((MyApplication)getContext().getApplicationContext()).getY());
                    if(dataz.size() >= MaxDataSize){
                        dataz.remove(0);
                    }

                    dataz.add(((MyApplication)getContext().getApplicationContext()).getZ());
                    handler.sendEmptyMessage(0x1234);
                }
            }
        });
        t.start();
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); //去锯齿
        paint.setColor(Color.BLUE);

        //画Y轴
        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);

        //Y轴箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint-YLength + 6, paint);  //箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint-YLength + 6 ,paint);

        //添加刻度和文字
        for(int i=0; i * YScale < YLength; i++) {
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i * YScale, paint);  //刻度

            canvas.drawText(YLabel[i], XPoint - 50, YPoint - i * YScale, paint);//文字
        }

        //画X轴
        canvas.drawLine(XPoint, YPoint-9* YScale, XPoint + XLength, YPoint-9* YScale, paint);
        if(data.size() > 1){
            for(int i=1; i<data.size(); i++){
                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - data.get(i-1) * YScale-9* YScale,
                        XPoint + i * XScale, YPoint - data.get(i) * YScale-9* YScale, paint);
            }
        }
        paint.setColor(Color.RED);
        if(datay.size() > 1){
            for(int i=1; i<datay.size(); i++){
                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - datay.get(i-1) * YScale-9* YScale,
                        XPoint + i * XScale, YPoint - datay.get(i) * YScale-9* YScale, paint);
            }
        }
        paint.setColor(Color.YELLOW);
        if(dataz.size() > 1){
            for(int i=1; i<dataz.size(); i++){
                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - dataz.get(i-1) * YScale-9* YScale,
                        XPoint + i * XScale, YPoint - dataz.get(i) * YScale-9* YScale, paint);
            }
        }
    }
}
