package com.bdblesdkuni.executor.thread;

import android.content.Context;
import android.content.Intent;

import com.bdblesdkuni.executor.handler.BDBLEHandler;
import com.bdblesdkuni.executor.handler.BLEManager;
import com.bddomainuni.repository.tools.BDMethod;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;


/**
 * 蓝牙接收组装线程类(适用于4.0协议)
 * 负责将放在接收缓存的数据组装成完整命令，并传递给后续解析方法
 *
 * Created by admin on 2017/05/24.
 */
public class revThread extends Thread {

    private byte[] data;
    private byte[] dataTEMP40 = new byte[300];
    private byte[] dataTEMP21 = new byte[300];
    private int begin = 0;
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
    private int headerFieldEndOffset = 12;//数据帧头部偏移量
    public int dataLen = -1;//协议头长度字段表示值
    private int maxDataFrameLength = 250;//最大数据帧长
    private int maxBufferLength = 1024;//最大缓存长度
    private byte[] frameBuffer = new byte[maxBufferLength];//二级缓存
    private InputStream mInputStream;
    private int typeFrame = -1;
    private static final int TYPE_FRAME_40 = 0;//4.0协议类型标志
    private static final int TYPE_FRAME_21 = 1;//2.0协议类型标志

    @Override
    public void run() {

        super.run();
        while (!isInterrupted()) {
            int tempBufferDataSize = -1;
            if ((mRevMessage.getByteList() != null && mRevMessage.getSize() > 0) || frameBufferDataEndIndex > 0) {
                if (mRevMessage.getByteList() != null) {
                    tempBufferDataSize = mRevMessage.getByteList().length;//取出蓝牙20字节缓存空间的实际信息长度
                    byte[] tempBuffer = new byte[tempBufferDataSize];
                    tempBuffer = mRevMessage.getByteList();
                    if (frameBufferDataEndIndex + tempBufferDataSize > maxBufferLength) {
                        //二级缓存溢出，暴力抛弃
                       // Log.v("FDBDTestLog","二级缓存溢出，暴力抛弃");
                        frameBufferDataEndIndex = 0;
                    } else {
                        System.arraycopy(tempBuffer, 0, frameBuffer, frameBufferDataEndIndex, tempBufferDataSize);
                        frameBufferDataEndIndex += tempBufferDataSize;
                    }
                    mRevMessage.delByteList();
                }

                while (frameBufferDataEndIndex > 0) {
                   // Log.v("FDBDTestLog", "frameBuffer ==>" + new String(frameBuffer));
                    //Log.v("FDBDTestLog", "0D0Aposition ==>" + get0D0Aposition(frameBuffer, frameBufferDataEndIndex));
                    //查找"$"去除"$"前的多余间隔，保证缓存第一位为"$"
                    if (frameBuffer[0] != 0x24) {
                      //  Log.v("FDBDTestLog", "1) 查找$");
                        for (int i = 0; i < frameBufferDataEndIndex; i++) {
                            if (frameBuffer[i] == 0x24) {
                                System.arraycopy(frameBuffer, i, frameBuffer, 0,
                                        frameBufferDataEndIndex - i);
                                frameBufferDataEndIndex -= i;
                                break;
                            }
                        }
                    }

                    if (frameBufferDataEndIndex > 7
                            && get40Length(frameBuffer) <= frameBufferDataEndIndex
                            && get40Length(frameBuffer) > 0) {  //fixme 18011601 应该大于4.0协议的最小长度7？
                        //fixme 18011602 是否应该判断当前二级缓存有效数据索引是否大于或等于提取出来的数据长度？&& frameBufferDataEndIndex >= get40Length(frameBuffer)
                        //4.0协议处理过程
                        //Log.v("FDBDTestLog", "2) 4.0协议处理过程");
                        dataLen = get40Length(frameBuffer);
                        byte[] data = new byte[dataLen];
                        System.arraycopy(frameBuffer, 0, data, 0, dataLen);
                        try {
                            intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                    new String(data,"gbk"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (BLEManager.getInstance().bdbleHandler != null) {
                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(data, BDBLEHandler.TYPE_PROTOCAL_40);
                        }
                        frameBufferDataEndIndex -= dataLen;
                        System.arraycopy(frameBuffer, dataLen, frameBuffer, 0,
                                frameBufferDataEndIndex);

                    } else if (frameBufferDataEndIndex > headerFieldEndOffset
                            && get0D0Aposition(frameBuffer, frameBufferDataEndIndex) > 0
                            && get0D0Aposition(frameBuffer, frameBufferDataEndIndex) < maxDataFrameLength) {
                        //fixme 18011603 当连续收到两条4.0信息时会不会重复进入此判断？
                        //NMEA0183协议处理过程
                        //Log.v("FDBDTestLog", "3) 2.1协议处理过程");
                        dataLen = get0D0Aposition(frameBuffer, frameBufferDataEndIndex);
                        //Log.v("FDBDTestLog","长度 ==> " + dataLen);
                        byte[] data = new byte[dataLen - 2];
                        System.arraycopy(frameBuffer, 0, data, 0, (dataLen - 2));
                        try {
                            intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                    new String(data,"gbk"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (BLEManager.getInstance().bdbleHandler != null) {
                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(data, BDBLEHandler.TYPE_PROTOCAL_21);
                        }
                        frameBufferDataEndIndex -= dataLen;
                        System.arraycopy(frameBuffer, dataLen, frameBuffer, 0,
                                frameBufferDataEndIndex);

                    } else if (frameBufferDataEndIndex >= maxBufferLength) {
                        //当接收二级缓存溢出时，暴力抛弃
                        //Log.v("FDBDTestLog", "4) 二级缓存溢出");
                        frameBufferDataEndIndex = 0;
                    } else if (frameBufferDataEndIndex > 7
                            && get40Length(frameBuffer) > maxDataFrameLength
                            && getMiddleDollar(frameBuffer, frameBufferDataEndIndex) > 1) {
                        //Log.v("FDBDTestLog", "5) 不满足");
                        //1) 当也不满足NMEA0183协议和4.0协议时，则考虑是否是4.0协议长度字段错误导致识别出错,去掉首部$，舍弃该帧
                        //2) 当接收缓存数据大于最大协议数据长度，去掉首部$，舍弃该帧
                        frameBufferDataEndIndex -= 1;
                        System.arraycopy(frameBuffer, 1, frameBuffer, 0, frameBufferDataEndIndex);
                    } else {
                        //Log.v("FDBDTestLog", "6) 不够");
                        //当接收协议数据还不完整时
                        break;
                    }

                }



                /*while (frameBufferDataEndIndex > 0) {
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

                    if (frameBufferDataEndIndex >= 3) {
                        //Log.v("FDBDTestLog", "typeFrame ==> " + typeFrame);
                        if (frameBuffer[1] == 0x42) {
                            if (frameBuffer[2] == 0x44) {
                                typeFrame = TYPE_FRAME_21;
                            }
                        } else if (frameBuffer[1] == 0x47) {
                            if (frameBuffer[2] == 0x50) {
                                typeFrame = TYPE_FRAME_21;
                            } else if (frameBuffer[2] == 0x4E) {
                                typeFrame = TYPE_FRAME_21;
                            } else if (frameBuffer[2] == 0x4C) {
                                typeFrame = TYPE_FRAME_21;
                            } else if (frameBuffer[2] == 0x41) {
                                typeFrame = TYPE_FRAME_21;
                            }
                        } else if (frameBuffer[1] == 0x43) {
                            if (frameBuffer[2] == 0x43) {
                                typeFrame = TYPE_FRAME_21;
                            }
                        } else {
                            typeFrame = TYPE_FRAME_40;
                            //Log.v("FDBDTestLog", "typeFrame ==> " + typeFrame);
                        }
                    }

                    if(typeFrame == TYPE_FRAME_21){
                        if (frameBufferDataEndIndex >= headerFieldEndOffset) {
                            for (int i = (headerFieldEndOffset - 3); i < frameBufferDataEndIndex; i++) {
                                if (frameBuffer[i] == 0x0A) {
                                    if (frameBuffer[i - 1] == 0x0D) {
                                        //Log.v("FDBD TestLog", "dataLen ====> " + dataLen);
                                        dataLen = i - 1;
                                        byte[] data = new byte[dataLen];
                                        System.arraycopy(frameBuffer, 0, data, 0, dataLen);
                                        //Log.v("FDBDTestLog", "========= data ========= " + new String(data));
                                        intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                                new String(data));
                                        if(BLEManager.getInstance().bdbleHandler !=null){
                                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(data,BDBLEHandler.TYPE_PROTOCAL_21);
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
                    }else if(typeFrame == TYPE_FRAME_40){
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
                                intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                        new String(data));
                                if(BLEManager.getInstance().bdbleHandler !=null){
                                    BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                    BLEManager.getInstance().bdbleHandler.onMessageReceivered(data,BDBLEHandler.TYPE_PROTOCAL_40);
                                }
                                frameBufferDataEndIndex -= dataLen;
                                System.arraycopy(frameBuffer, dataLen, frameBuffer, 0,
                                        frameBufferDataEndIndex);
                            } else {
                                break;
                            }

                        } else {
                            break;
                        }
                    }else {
                        break;
                    }

                }*/
            } else {
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
                                dataTEMP40[begin] = data[i];
                                dataTEMP21[begin++] = data[i];
                            } else if (begin > 0) {
                                dataTEMP40[begin] = data[i];
                                dataTEMP21[begin++] = data[i];
                                if (begin == 7) {
                                    length = getLength(dataTEMP40[5], dataTEMP40[6]);
                                    byte[] changeByte = new byte[7];
                                    System.arraycopy(dataTEMP40, 0, changeByte, 0, 7);
                                    dataTEMP40 = new byte[length];
                                    System.arraycopy(changeByte, 0, dataTEMP40, 0, 7);
                                    //Log.v("FDBDTestLog","length ==> "+length);
                                } else if (begin > 7) {

                                    if (dataTEMP21[begin - 5] == '*' && dataTEMP21[begin - 2] == ((byte)0x0D) && dataTEMP21[begin - 1] == ((byte)0x0A)) {

                                        intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                                new String(dataTEMP21));
                                        if(BLEManager.getInstance().bdbleHandler !=null){
                                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(dataTEMP21,BDBLEHandler.TYPE_PROTOCAL_21);
                                        }

                                        begin = 0;
                                        dataTEMP40 = new byte[300];
                                        dataTEMP21 = new byte[300];
                                    }

                                    if (begin > (length - 1)) {
                                        intent.putExtra(BDBLEHandler.EXTRA_DATA, new String(dataTEMP40));
                                        if (BLEManager.getInstance().bdbleHandler != null) {
                                            BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                            BLEManager.getInstance().bdbleHandler.onMessageReceivered(dataTEMP40,BDBLEHandler.TYPE_PROTOCAL_40);
                                        }
                                        //Log.v("FDBDTestLog","接收到的4.0信息 == >"+BDMethod.castBytesToHexString(dataTEMP40));
                                        begin = 0;
                                        dataTEMP40 = new byte[300];
                                        dataTEMP21 = new byte[300];
                                    }
                                }

                                if (begin >= 299) {
                                    dataTEMP40 = new byte[300];
                                    dataTEMP21 = new byte[300];
                                    String attentionString = "error : Already received more than 300 bytes , but did not search to '*'!";
                                    dataTEMP40 = attentionString.getBytes();
                                    intent.putExtra(BDBLEHandler.EXTRA_DATA,
                                            new String(dataTEMP40));

                                    if (BLEManager.getInstance().bdbleHandler != null) {
                                        BLEManager.getInstance().bdbleHandler.mContext.getApplicationContext().sendBroadcast(intent);
                                    }
                                    begin = 0;
                                    dataTEMP40 = new byte[300];
                                    dataTEMP21 = new byte[300];
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
     * @param high
     *         高位字节
     * @param low
     *         低位字节
     */
    private int getLength(byte high, byte low) {
        byte[] lengthByte = new byte[2];
        lengthByte[0] = low;
        lengthByte[1] = high;
        return BDMethod.castBytesToInt(lengthByte);
    }

    private int get40Length(byte[] param) {
        return (int) (param[5] << 8 | param[6]);
    }

    private int get0D0Aposition(byte[] param, int length) {
        int dataLen = -1;
        for (int i = (headerFieldEndOffset - 3); i < length; i++) {
            if (param[i] == 0x0A) {
                if (param[i - 1] == 0x0D) {
                    dataLen = i + 1;
                    return dataLen;
                }
            }
            if (i == length - 1) {
                dataLen = -1;
                return dataLen;
            }
        }
        return dataLen;
    }

    private int getMiddleDollar(byte[] param, int length) {
        int dollarPosition = -1;
        for (int i = 1; i < length; i++) {
            if (param[i] == 0x24) {
                dollarPosition = i + 1;
                return dollarPosition;
            }
            if (i == length - 1) {
                dollarPosition = -1;
                return dollarPosition;
            }
        }
        return dollarPosition;
    }
}

