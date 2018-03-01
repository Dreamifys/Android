package com.example.administrator.android_move;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.textview);
        tv.setMovementMethod(new ScrollingMovementMethod());

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //从系统服务中获得传感器管理器

        List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        //从传感器管理器中获得全部的传感器列表

        tv.setText("经检测该设备有" + allSensors.size() + "个传感器，他们分别是：\n\n");
        //显示有多少个传感器

        for(Sensor s : allSensors){//显示每个传感器的具体信息
            String tempString = "\n" + " 设备名称：" +s.getName() + "\n"
                    + " 设备版本：" + s.getVersion() + "\n" + " 供应商："
                    + s.getVendor() + "\n";
            switch (s.getType()) {

                case Sensor.TYPE_ACCELEROMETER:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 加速度传感器accelerometer" + tempString + "\n");
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 温度传感器temperature" + tempString + "\n");
                    break;
                case Sensor.TYPE_GRAVITY:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 重力传感器gravity" + tempString + "\n");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 陀螺仪传感器gyroscope" + tempString + "\n");
                    break;
                case Sensor.TYPE_LIGHT:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 环境光线传感器light" + tempString + "\n");
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 线性加速度传感器linear_accelerometer" + tempString + "\n");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 电磁场传感器magnetic field" + tempString + "\n");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 方向传感器orientation" + tempString + "\n");
                    break;
                case Sensor.TYPE_PRESSURE:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 压力传感器pressure" + tempString + "\n");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 距离传感器proximity" + tempString + "\n");
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 湿度传感器relative_humidity" + tempString + "\n");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 旋转矢量传感器rotation_vector" + tempString + "\n");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    tv.setText(tv.getText().toString() + s.getType()
                            + " 温度传感器temperature" + tempString + "\n");
                    break;
                default:
                    tv.setText(tv.getText().toString() + s.getType() + " 未知传感器"
                            + tempString);
                    break;
            }
        }
    }

}
