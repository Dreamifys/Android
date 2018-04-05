package com.example.administrator.android_move;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MyView2 extends View {

    public int weight = 640;
    public MyView2(Context context) {
        super( context );
        init();
    }

    public MyView2(Context context, AttributeSet attrs) {
        super( context, attrs );
        init();
    }

    public MyView2(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        init();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //把整张画布绘制成白色
        int r,cx,cy,x0,x1,y0,y1;
        r = 120;
        cx = weight/2;
        cy = 182;
        x0 = weight/2-r;
        x1 = weight/2+r;
        y0 = cy-r;
        y1 = cy+r;
        canvas.drawColor(Color.BLUE);
        Paint paint = new Paint();
        //去锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        //设置渐变器后绘制
        //为Paint设置渐变器
        Shader mShasder = new LinearGradient(x0, y0, x1, y1, new int[]{Color.RED,Color.GREEN,Color.CYAN,Color.YELLOW}, null, Shader.TileMode.REPEAT);
        paint.setShader(mShasder);
        //设置阴影
        paint.setShadowLayer(45, 10, 10, Color.GRAY);
        //绘制圆形
        canvas.drawCircle(cx, cy, r, paint);

    }
    public void init(){
        DisplayMetrics dm =getResources().getDisplayMetrics();
        weight = dm.widthPixels;
    }
}
