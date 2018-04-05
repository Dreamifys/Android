package com.example.administrator.android_move;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class achartengineActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private MySensorEventListener sensorEventListener;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    int type = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_achartengine );
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        type = bundle.getInt("name");
        t1 = this.findViewById(R.id.textView1);
        t2 = this.findViewById(R.id.textView2);
        t3 = this.findViewById(R.id.textView3);
        sensorEventListener = new MySensorEventListener();
        //获取感应器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ((MyApplication)getApplication()).setFlag(true);


    }
    @Override
    protected void onResume()
    {
        //获取方向传感器
        Sensor orientationSensor = sensorManager.getDefaultSensor(type);
        sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        ((MyApplication)getApplication()).setFlag(true);

        super.onResume();
    }
    class MySensorEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {

            double x = event.values[SensorManager.DATA_X];
            double y = event.values[SensorManager.DATA_Y];
            double z = event.values[SensorManager.DATA_Z];
            //得到方向的值
            if(event.sensor.getType()==5)
            {
                //光传感器控制屏幕亮度
                y = z = 0;
                float light = (float)x;
                if(light>255f)light=255f;
                if(light<0.0f)light=0.0f;
                Window window = getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.screenBrightness = light / 255.0f;
                window.setAttributes(lp);
            }
            setValue((int)x,(int)y,(int)z);

            t1.setText("x-Orie: " + String.format("%.2f", x).toString());
            t2.setText("y-Orie: " + String.format("%.2f", y).toString());
            t3.setText("z-Orie: " + String.format("%.2f", z).toString());

            System.out.println( ((MyApplication)getApplication()).getFlag() );
        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }
    public void setValue(int x,int y,int z){
        ((MyApplication)getApplication()).setX(x);
        ((MyApplication)getApplication()).setY(y);
        ((MyApplication)getApplication()).setZ(z);
    }
    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(sensorEventListener);
        ((MyApplication)getApplication()).setFlag(false);
        setValue(0,0,0);
        super.onPause();
    }
    @Override
    protected void onStop()
    {
        sensorManager.unregisterListener(sensorEventListener);
        ((MyApplication)getApplication()).setFlag(false);
        setValue(0,0,0);

        super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        sensorManager.unregisterListener(sensorEventListener);
        ((MyApplication)getApplication()).setFlag(false);
        setValue(0,0,0);

        super.onDestroy();
    }
}
