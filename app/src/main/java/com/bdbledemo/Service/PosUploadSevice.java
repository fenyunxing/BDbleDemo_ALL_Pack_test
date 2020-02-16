package com.bdbledemo.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bdbledemo.Listener.PosUpdateListener;
import com.bdbledemo.MainApp;
import com.bdbledemo.Manager.PosUpdateManger;
import com.bdbledemo.Thread.PosUploadThread;
import com.bdblesdkuni.executor.handler.BLEManager;

import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_pos_update;

/**
 * Created by lenovo on 2018/4/27.
 */

public class PosUploadSevice extends Service implements PosUpdateListener {

    private PosUploadThread posUploadThread = new PosUploadThread();
    private PosUpdateManger posUpdateManger;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("FDBDTestLog", "位置上报服务启动");
        posUpdateManger = MainApp.getInstance().getPosUpdateManger();
        posUpdateManger.setPosUpdateListener(this);
        posUploadThread.setSamplingTime(3);
        posUploadThread.setSampleNum(2);
        posUploadThread.requestStart();
        posUploadThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return flags;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("FDBDTestLog", "位置上报服务销毁");
        posUploadThread.requestStop();
        posUpdateManger.removePosUpdateListener(this);
    }

    @Override
    public void onPositionMsgAssembled(String posMsg) {
        BLEManager.getInstance().send(
                gen_msg_pos_update("1234567", "15000000001",5,posMsg)
        );
        Log.v("FDBDTestLog", "位置组装:" + posMsg);
    }
}
