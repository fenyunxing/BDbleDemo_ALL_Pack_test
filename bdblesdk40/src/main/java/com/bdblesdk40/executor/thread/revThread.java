package com.bdblesdk40.executor.thread;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bdblesdk40.executor.handler.BDBLEHandler;
import com.bdblesdk40.executor.handler.BLEManager;
import com.bddomain40.repository.tools.BDMethod;

import java.io.InputStream;


/**
 * 蓝牙接收组装线程类(适用于4.0协议)
 * 负责将放在接收缓存的数据组装成完整命令，并传递给后续解析方法
 *
 * Created by admin on 2017/05/24.
 */
public class revThread extends Thread {

    //private byte[] data;
    //private byte[] dataTEMP = new byte[300];
    //private int begin = 0;
    private boolean stop = false;
    final Intent intent = new Intent(BDBLEHandler.ACTION_DATA_AVAILABLE);
    private MessageTempList mRevMessage = new MessageTempList();
    private Context mContext;
    private int length = 0;

    /**
     * 将接收信息存入接收缓存列表
     *
     * @param parambytes
     *         接收字节信息
     */
    public void setRevTempMsg(byte[] parambytes) {
        mRevMessage.setByteList(parambytes);
    }

    private int frameBufferDataEndIndex = 0;//数据帧缓存有效数据结尾索引
    private int headerFieldEndOffset = 7;//数据帧头部偏移量
    public int dataLen = -1;//协议头长度字段表示值
    private int maxDataFrameLength = 250;
    private int maxBufferLength = 1024;
    private byte[] frameBuffer = new byte[maxBufferLength];//二级缓存
    private InputStream mInputStream;
    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            int tempBufferDataSize = -1;
            if ((mRevMessage.getByteList() != null && mRevMessage.getSize() > 0) || frameBufferDataEndIndex > 0){
                if(mRevMessage.getByteList() != null){
                    tempBufferDataSize = mRevMessage.getByteList().length;
                    byte[] tempBuffer = new byte[tempBufferDataSize];
                    tempBuffer = mRevMessage.getByteList();
                    System.arraycopy(tempBuffer, 0, frameBuffer, frameBufferDataEndIndex, tempBufferDataSize);
                    frameBufferDataEndIndex += tempBufferDataSize;
                    mRevMessage.delByteList();
                }
                while (frameBufferDataEndIndex > 0){

                    if(frameBufferDataEndIndex >= maxDataFrameLength){
                        Log.v("FDBDTestLog", "超过数据最长长度，抛弃");
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
                    //判断是否二级有效数据索引是否大于协议头部长度
                    if (frameBufferDataEndIndex >= headerFieldEndOffset) {
                        dataLen = frameBuffer[5] << 8 | frameBuffer[6];
                        //判断去除长度字段数值是否协议最大长度
                        if (dataLen > maxBufferLength) {
                            frameBufferDataEndIndex -= 1;
                            System.arraycopy(frameBuffer, 1, frameBuffer, 0, frameBufferDataEndIndex);
                        }
                        //判断二级缓存有效数据索引是否大于该条协议数据长度，且该条协议数据长度大于0
                        if (frameBufferDataEndIndex >= dataLen && dataLen > 0) {
                            //Log.v("FDBDTestLog", "dataLen ====> " + dataLen);
                            byte[] data = new byte[dataLen];
                            System.arraycopy(frameBuffer, 0, data, 0, dataLen);
                            //Log.v("FDBDTestLog", "========= data ========= " + new String(data));
                            intent.putExtra(BDBLEHandler.EXTRA_DATA, new String(data));
                            if (BLEManager.getInstance().bdbleHandler != null) {
                                BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                BLEManager.getInstance().bdbleHandler.onMessageReceivered(data);
                            }
                            frameBufferDataEndIndex -= dataLen;
                            System.arraycopy(frameBuffer, dataLen, frameBuffer, 0,
                                    frameBufferDataEndIndex);

                        }else {
                            break;
                        }

                    }else {
                        break;
                    }

                }
            }else{
                try {
                    Thread.currentThread().sleep(50L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
                                if (begin == 7) {
                                    length = getLength(dataTEMP[5], dataTEMP[6]);
                                    byte[] changeByte = new byte[7];
                                    System.arraycopy(dataTEMP, 0, changeByte, 0, 7);
                                    dataTEMP = new byte[length];
                                    System.arraycopy(changeByte, 0, dataTEMP, 0, 7);
                                    //Log.v("FDBDTestLog","length ==> "+length);
                                } else if (begin > 7) {
                                    if (begin > (length - 1)) {
                                        intent.putExtra(BDBLEHandler.EXTRA_DATA, new String(dataTEMP));
                                        if (BLEManager.getInstance().bdbleHandler != null) {
                                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(dataTEMP);
                                        }
                                        //Log.v("FDBDTestLog","接收到的4.0信息 == >"+BDMethod.castBytesToHexString(dataTEMP));
                                        begin = 0;
                                        dataTEMP = new byte[300];
                                    }
                                }

                                if (begin >= 299) {
                                    dataTEMP = new byte[300];
                                    String attentionString = "error : Already received more than 300 bytes , but did not search to '*'!";
                                    dataTEMP = attentionString.getBytes();
                                    intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                            new String(dataTEMP));

                                    if (BLEManager.getInstance().bdbleHandler != null) {
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
        } catch (InterruptedException e) {
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

    /**
     * 获取长度值
     *
     * 根据4.0协议长度信息，将byte[]形式转为int形式
     *
     * @param high 高位字节
     * @param low 低位字节
     */
    private int getLength(byte high, byte low) {
        byte[] lengthByte = new byte[2];
        lengthByte[0] = low;
        lengthByte[1] = high;
        return BDMethod.castBytesToInt(lengthByte);
    }
}

