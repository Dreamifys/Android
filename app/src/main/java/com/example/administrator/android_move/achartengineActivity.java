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

    int type = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_achartengine );
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        type = bundle.getInt("name");
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
        Sensor orientationSensor = sensorManager.getDefaultSensor(type);
        sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    class MySensorEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            //得到方向的值
            if(event.sensor.getType()== type)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                ((MyApplication)getApplication()).setX((int)x/6);
                ((MyApplication)getApplication()).setY((int)y/6);
                ((MyApplication)getApplication()).setZ((int)z/6);
            }

        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }

}
