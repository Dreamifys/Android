package com.example.administrator.android_move;

import android.app.Application;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class MyApplication extends Application {
    private int x = 0;
    public int getX() {
         return x;
     }
    public void setX(int score) {
         this.x = score;
     }
    private int y = 0;
    public int getY() {
        return y;
    }
    public void setY(int score) {
        this.y = score;
    }
    private int z = 0;
    public int getZ() {
        return z;
    }
    public void setZ(int score) {
        this.z = score;
    }
}
