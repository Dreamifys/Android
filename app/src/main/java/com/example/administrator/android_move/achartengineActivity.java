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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_achartengine );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        sensorEventListener = new MySensorEventListener();
        //获取感应器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyApplication)getApplication()).setX(5);
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
    }
    @Override
    protected void onResume()
    {
        //获取方向传感器
        Sensor orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //获取加速度传感器
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    class MySensorEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            //得到方向的值
            if(event.sensor.getType()== Sensor.TYPE_ORIENTATION)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                System.out.print(x);
                ((MyApplication)getApplication()).setX((int)x/6);
            }
            //得到加速度的值
            else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                //accelerometerView.setText("Accelerometer: " + (int)x + ", " + (int)y + ", " + (int)z);
            }

        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }

}
