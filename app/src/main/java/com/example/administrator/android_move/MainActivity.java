package com.example.administrator.android_move;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idescout.sql.SqlScoutServer;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tt1;
    private TextView tt2;
    private MyDatabaseHelper dbHelper;
    private TextView accelerometerView;
    private TextView orientationView;
    private SensorManager sensorManager;
    private MySensorEventListener sensorEventListener;
    private LineChartView lineChart;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    //获取当前时间
    Date dates = new Date(System.currentTimeMillis());
    //time1.setText("Date获取当前日期时间"+simpleDateFormat.format(dates));
    private int todayf;
    String[] date = {"0点","2点","4点","6点","8点","10点","12点","14点","16点","18点","20点","22点"};//X轴的标注
    int[] score= {0,0,0,0,0,0,0,0,0,0,0,0};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SqlScoutServer.create(this, getPackageName());
        getSupportActionBar().hide();
        //getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
//db manager
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);
        dbHelper.getWritableDatabase();
//sensor manager
        sensorEventListener = new MySensorEventListener();
        //获取感应器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//kongjian
        TextView t1 = (TextView) findViewById(R.id.textview1);
        TextView t2 = (TextView) findViewById(R.id.textview2);
        TextView t3 = (TextView) findViewById(R.id.textview3);
        TextView t4 = (TextView) findViewById(R.id.textview4);
        TextView t5 = (TextView) findViewById(R.id.textview5);
        TextView t6 = (TextView) findViewById(R.id.textview6);
        TextView t7 = (TextView) findViewById(R.id.textview7);
        TextView t8 = (TextView) findViewById(R.id.textview8);
        tt1 = (TextView) findViewById(R.id.textView);
        tt2 = (TextView) findViewById(R.id.textView7);
        ImageView i6 = (ImageView) findViewById(R.id.imageView6);
        tt1.setOnClickListener(this);
        tt2.setOnClickListener(this);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        i6.setOnClickListener(this);
//hellochart
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        readFromDb();
    }
    @Override
    protected void onResume()
    {
        mPointValues.clear();
        readFromDb();
        //获取方向传感器
        Sensor orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //单次有效计步
        Sensor  mStepCount = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //系统计步累加值
        Sensor  mStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(sensorEventListener, mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);

        sensorManager.registerListener(sensorEventListener, mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }
    public void onClick(View view)
    {
        int sensor = 8;//18 19 计步
        switch (view.getId()){
            case R.id.textview1://磁场
                sensor = 2;
                break;
            case R.id.textview2://加速度计
                sensor = 1;
                break;
            case R.id.textview3://陀螺仪
                sensor = 4;
                break;
            case R.id.textview4://光
                sensor = 5;
                break;
            case R.id.textview5://重力
                sensor = 9;
                break;
            case R.id.textview6://方向
                sensor = 3;
                break;
            case R.id.textview7://线性加速度
                sensor = 10;
                break;
            case R.id.textview8://旋转矢量
                sensor = 11;
                break;
            case R.id.imageView6://地图定位
                sensor = 0;
                break;

            default:
                break;
        }
        if(sensor!=0){

            Intent achart = new Intent(this,achartengineActivity.class);
            Bundle bundle=new Bundle();
            //传递name参数为tinyphp
            bundle.putInt("name", sensor);
            achart.putExtras(bundle);
            startActivity(achart);
        }else{

            Intent achart = new Intent(this,Main2Activity.class);
            startActivity(achart);
        }

    }

    class MySensorEventListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            //得到方向的值
            if(event.sensor.getType()==Sensor.TYPE_ORIENTATION)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
            }
            //得到加速度的值
            else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
            }

            if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

                //event.values[0]为计步历史累加值event.values[0]
                tt1.setText(""+event.values[0]);

            }

            if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

                if (event.values[0] == 1.0) {
                    todayf++;
                    tt2.setText(""+todayf);
                    Date d = new Date();
                    int hours = d.getHours();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    int s = hours/2;
                    Cursor cursor = db.rawQuery("select * from foot where type="+s, null);
                    while (cursor.moveToNext()) {
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        int wri = cursor.getInt(cursor.getColumnIndex("wri"));
                        int num = cursor.getInt(cursor.getColumnIndex("num"));
                        String bei = cursor.getString(cursor.getColumnIndex("bei"));

                        ContentValues v1 = new ContentValues();
                        v1.put("num",num+1);
                        //仔细update中提示的参数（String table,ContentValues,String whereClause,String[] whereArgs）
                        //第三滴四行指定具体更新那几行。注意第三个参数中的？是一个占位符，通过第四个参数为第三个参数中占位符指定相应的内容。
                        db.update("foot",v1,"type=?",new String[]{type});
                        mPointValues.clear();
                        readFromDb();
                        return;
                    }
                    cursor.close();

                    ContentValues values = new ContentValues();
                    values.put("type",s);
                    values.put("wri",hours);
                    values.put("num",1);
                    values.put("bei","normal");
                    db.insert("foot",null,values);
                    values.clear();
                    mPointValues.clear();
                    readFromDb();
                }

            }

        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }

    //暂停传感器的捕获
    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    /**
     * 设置数据恢复readFromDb
     */
    private void readFromDb(){
        todayf = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //指明去查询Book表。
        Cursor cursor = db.query("foot",null,null,null,null,null,null);
        //调用moveToFirst()将数据指针移动到第一行的位置。
        if (cursor.moveToFirst()){
            do {
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                int tnum = cursor.getInt(cursor.getColumnIndex("num"));
                score[type] = tnum;
                todayf+=tnum;
            }while(cursor.moveToNext());
        }
        cursor.close();
        tt2.setText(""+todayf);
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
    }


    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add( new PointValue( i, score[i] ) );
        }
    }
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor( Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape( ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(true);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType( ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility( View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }

    }
