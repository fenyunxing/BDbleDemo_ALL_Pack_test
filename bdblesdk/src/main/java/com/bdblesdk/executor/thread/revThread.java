package com.bdblesdk.executor.thread;

import android.content.Context;
import android.content.Intent;

import com.bdblesdk.executor.handler.BDBLEHandler;
import com.bdblesdk.executor.handler.BLEManager;

import java.io.UnsupportedEncodingException;


/**
 * 蓝牙接收组装线程类
 * 负责将放在接收缓存的数据组装成完整命令，并传递给后续解析方法
 *
 * Created by admin on 2016/10/21.
 */
public class revThread extends Thread {

    //private byte[] data;
    //private byte[] dataTEMP = new byte[300];
    //private int begin = 0;
    private boolean stop = false;
    final Intent intent = new Intent(BDBLEHandler.ACTION_DATA_AVAILABLE);
    private MessageTempList mRevMessage = new MessageTempList();
    private Context mContext;


    /**
     * 将接收信息存入接收缓存列表
     *
     * @param parambytes
     *         接收字节信息
     */
    public void setRevTempMsg(byte[] parambytes) {
        mRevMessage.setByteList(parambytes);
    }

    private static int frameBufferDataEndIndex = 0;//数据帧缓存有效数据结尾索引
    private static int minFrameLength = 12;//最短2.1协议长度
    public static int dataLen = -1;//协议头长度字段表示值
    private static int maxDataFrameLength = 250;
    private static int maxBufferLength = 1024;
    private byte[] frameBuffer = new byte[maxBufferLength];//二级缓存

    @Override
    public void run() {
        super.run();
        try{
            while (!isInterrupted()) {
                int tempBufferDataSize = -1;
                if ((mRevMessage.getByteList() != null && mRevMessage.getSize() > 0) || frameBufferDataEndIndex > 0){
                    //Log.v("FDBDTestLog","tempBuffer ==>"+mRevMessage.getByteList().length);
                    if(mRevMessage.getByteList() != null){
                        tempBufferDataSize = mRevMessage.getByteList().length;
                        byte[] tempBuffer = new byte[tempBufferDataSize];
                        tempBuffer = mRevMessage.getByteList();
                        System.arraycopy(tempBuffer, 0, frameBuffer, frameBufferDataEndIndex, tempBufferDataSize);
                        frameBufferDataEndIndex += tempBufferDataSize;
                        mRevMessage.delByteList();
                    }

                    while (frameBufferDataEndIndex > 0) {
                        if (frameBufferDataEndIndex >= maxDataFrameLength) {
                            frameBufferDataEndIndex = 0;
                            break;
                        }
                        //查找"$"去除"$"前的多余间隔，保证缓存第一位为"$"
                        if (frameBuffer[0] != 0x24) {
                            for (int i = 0; i < frameBufferDataEndIndex; i++) {
                                if (frameBuffer[i] == 0x24) {
                                    System.arraycopy(frameBuffer, i, frameBuffer, 0,
                                            frameBufferDataEndIndex - i);
                                    frameBufferDataEndIndex -= i;
                                    break;
                                }
                            }
                        }

                        if (frameBufferDataEndIndex >= minFrameLength) {
                            for (int i = (minFrameLength - 3); i < frameBufferDataEndIndex; i++) {
                                if (frameBuffer[i] == 0x0A) {
                                    if (frameBuffer[i - 1] == 0x0D) {
                                        //Log.v("FDBD TestLog", "dataLen ====> " + dataLen);
                                        dataLen = i - 1;
                                        byte[] data = new byte[dataLen];
                                        System.arraycopy(frameBuffer, 0, data, 0, dataLen);
                                        //Log.v("FDBDTestLog", "========= data ========= " + new String(data));
                                        try {
                                            intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                                    new String(data,"gbk"));
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        if(BLEManager.getInstance().bdbleHandler !=null){
                                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(data);
                                        }
                                        frameBufferDataEndIndex -= dataLen;

                                        System.arraycopy(frameBuffer, dataLen, frameBuffer, 0,
                                                frameBufferDataEndIndex);

                                    }
                                }
                            }
                            break;
                        } else {
                            break;
                        }
                    }
                } else{
                    try {
                        Thread.currentThread().sleep(50L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }catch (Exception e){

        }
        /*try {
            while (!isStop()) {

                if (mRevMessage.getByteList() != null && mRevMessage.getSize() > 0) {
                    data = mRevMessage.getByteList();
                    if (data != null && data.length > 0) {
                        for (int i = 0; i < data.length; i++) {
                            if ((data[i] == '$') && (begin == 0)) {
                                dataTEMP[begin++] = data[i];
                            } else if (begin > 0) {
                                dataTEMP[begin++] = data[i];
                                if (begin >= 3 && dataTEMP[begin - 3] == '*') {

                                    intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                            new String(dataTEMP));
                                    //Log.v("repairtest",new String(dataTEMP) );
                                    if(BLEManager.getInstance().bdbleHandler !=null){
                                        BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                        BLEManager.getInstance().bdbleHandler.onMessageReceivered(dataTEMP);
                                    }

                                    begin = 0;
                                    dataTEMP = new byte[300];
                                }
                                if(begin >= 299){
                                    dataTEMP = new byte[300];
                                    String attentionString = "error : Already received more than 300 bytes , but did not search to '*'!";
                                    dataTEMP = attentionString.getBytes();
                                    intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                            new String(dataTEMP));

                                    if(BLEManager.getInstance().bdbleHandler !=null){
                                        BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                    }
                                    begin = 0;
                                    dataTEMP = new byte[300];
                                }
                            }
                        }
                        mRevMessage.delByteList();
                    } else {
                        Thread.currentThread().sleep(50L);
                    }
                } else {
                    Thread.currentThread().sleep(50L);
                }
            }
        }catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    private boolean isStop() {
        return this.stop;
    }

    /**
     * 停止接收将数据放入接收缓存
     */
    public void stopThread() {
        this.stop = true;
    }

}

