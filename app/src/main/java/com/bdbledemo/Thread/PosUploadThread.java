package com.bdbledemo.Thread;

import com.bdbledemo.MainApp;
import com.bdbledemo.Manager.PosUpdateManger;
import com.bddomainuni.models.entity.protocal2_1.GGAMsg;
import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bdplatformsdk.models.MultiLocReportMsg;

/**
 * Created by lenovo on 2018/4/27.
 */

public class PosUploadThread extends Thread {

    private static boolean stopRequested = false;
    private int sampleTime = 60000;
    private int sampleNum = 2;
    private int nowSampleTime = 1;
    private RMCMsg rmcMsg = new RMCMsg("$GNRMC,133806.000,A,2603.2273,N,11911.8064,E,0.18,180.15,020317,,,A*7C".getBytes());
    private GGAMsg ggaMsg = new GGAMsg("$GNGGA,024810.000,2603.2396,N,11911.7843,E,1,06,3.0,27.2,M,0.0,M,,*47".getBytes());
    private RMCMsg rmcMsgDev1 = new RMCMsg("$GNRMC,133816.000,A,2603.2263,N,11911.8054,E,0.25,57.2,020317,,,A*7C".getBytes());
    private GGAMsg ggaMsgDev2 = new GGAMsg("$GNGGA,024810.000,2603.2396,N,11911.7843,E,1,06,3.0,10,M,0.0,M,,*5F".getBytes());
    private MultiLocReportMsg multiLocReportMsg = new MultiLocReportMsg();
    private PosUpdateManger posUpdateManger = MainApp.getInstance().getPosUpdateManger();
    private String positionStr = "";

    public static synchronized void requestStop() {
        stopRequested = true;
    }

    public static synchronized void requestStart() {
        stopRequested = false;
    }

    private static synchronized boolean isStop() {
        return stopRequested;
    }

    @Override
    public void run() {
        try {
            while (!isStop()) {
                Thread.currentThread().sleep(sampleTime);
                if (isStop()) {
                    return;
                }
                if (nowSampleTime < 2) {
                    if (rmcMsg.getDWstaus()) {
                        nowSampleTime++;
                        positionStr = multiLocReportMsg.getBaseLocHexStr(rmcMsg, ggaMsg);
                        //Log.v("FDBDTestLog", "基准位置" + multiLocReportMsg.getBaseLocHexStr(rmcMsg, ggaMsg));
                    }
                } else {
                    nowSampleTime++;
                    //Log.v("FDBDTestLog", "偏移位置" + multiLocReportMsg.getDevLocHexStr(rmcMsgDev1, ggaMsgDev2));
                    positionStr = positionStr + multiLocReportMsg.getDevLocHexStr(rmcMsgDev1, ggaMsgDev2);
                    if (nowSampleTime > sampleNum) {
                        //Log.v("FDBDTestLog", "sampleNum * sampleTime = " + sampleNum * sampleTime);
                        if (sampleNum * sampleTime < 5000) {
                            Thread.currentThread().sleep(5000 - sampleNum * sampleTime);
                        }
                        posUpdateManger.callbackPosMsg(positionStr);
                        nowSampleTime = 1;
                        positionStr = "";
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSamplingTime(int sampleTime) {
        this.sampleTime = sampleTime * 1000;
    }

    public void setSampleNum(int sampleNum) {
        if (sampleNum < 0) {
            this.sampleNum = 0;
        }
        this.sampleNum = sampleNum;
    }
}
