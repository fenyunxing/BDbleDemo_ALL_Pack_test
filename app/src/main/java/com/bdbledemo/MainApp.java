package com.bdbledemo;

import android.app.Application;

import com.bdbledemo.Manager.PosUpdateManger;

/**
 * Created by lenovo on 2018/5/9.
 */

public class MainApp extends Application {
    private static MainApp mainApp;
    private PosUpdateManger posUpdateManger;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApp = this;
        posUpdateManger = new PosUpdateManger();
    }

    public static MainApp getInstance() {
        return mainApp;
    }

    public PosUpdateManger getPosUpdateManger() {
        return posUpdateManger;
    }
}
