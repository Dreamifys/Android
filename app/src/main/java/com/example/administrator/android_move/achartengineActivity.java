package com.example.administrator.android_move;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
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

    }
    @Override
    protected void onResume()
    {
        //获取方向传感器
        Sensor orientationSensor = sensorManager.getDefaultSensor(type);
        sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
                y = z = 0.00;
            }
            ((MyApplication)getApplication()).setX((int)x);
            ((MyApplication)getApplication()).setY((int)y);
            ((MyApplication)getApplication()).setZ((int)z);

            t1.setText("x-Orie: " + String.format("%.2f", x).toString());
            t2.setText("y-Orie: " + String.format("%.2f", y).toString());
            t3.setText("z-Orie: " + String.format("%.2f", z).toString());

        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }

}
