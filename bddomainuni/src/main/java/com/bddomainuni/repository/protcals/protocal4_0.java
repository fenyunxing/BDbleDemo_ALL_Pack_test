package com.bddomainuni.repository.protcals;


import com.bddomainuni.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;

/**
 * 北斗4.0协议类
 *
 * 修改于 2017/10/27. 修改了TXSQ发送比特位长度没有乘8问题
 */
public class protocal4_0 {

    //DWSQ相关参数
    public static final int MEASURE_HIGH_YES = 1;//有高程
    public static final int MEASURE_HIGH_NO = 2;//无测高
    public static final int MEASURE_HIGH_1 = 3;//测高1
    public static final int MEASURE_HIGH_2 = 4;//测高2

    //CKSC串口输出参数
    public static final byte BAUDRATE_19200 = 0x00;//19.2 kbps
    public static final byte BAUDRATE_1200 = 0x01;//1.2 kbps
    public static final byte BAUDRATE_2400 = 0x02;//2.4 kbps
    public static final byte BAUDRATE_4800 = 0x03;//4.8 kbps
    public static final byte BAUDRATE_9600 = 0x04;//9.6 kbps
    public static final byte BAUDRATE_38400 = 0x05;//38.4 kbps
    public static final byte BAUDRATE_57600 = 0x06;//57.6 kbps
    public static final byte BAUDRATE_115200 = 0x07;//115.2 kbps

    //TXSQ相关参数
    public static final int MSG_TYPE_CHINESE = 0;//汉字
    public static final int MSG_TYPE_CODE = 1;//代码
    public static final int MSG_TYPE_MIX = 2;//混发

    public static byte[] gen_gljc(int frequence) {
        byte[] gljc = new byte[12];
        byte[] header = new byte[]{0x24, 0x47, 0x4C, 0x4A, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, (byte) frequence};
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, gljc, 0, header.length);
        System.arraycopy(crc, 0, gljc, 11, 1);
        return gljc;
    }

    public static byte[] gen_gljc(int ic, int frequence) {
        byte[] gljc = new byte[12];
        byte[] icByte = BDMethod.castIntToBytesPos(ic);
        byte[] header = new byte[]{0x24, 0x47, 0x4C, 0x4A, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, (byte) frequence};
        System.arraycopy(icByte, 1, header, 7, 3);
        System.arraycopy(header, 0, gljc, 0, header.length);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(crc, 0, gljc, 11, 1);
        return gljc;
    }

    public static byte[] gen_icjc(int frameNum) {
        byte[] icjc = new byte[12];
        byte[] header = new byte[]{0x24, 0x49, 0x43, 0x4A, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, (byte) frameNum};
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, icjc, 0, header.length);
        System.arraycopy(crc, 0, icjc, 11, 1);
        return icjc;
    }

    public static byte[] gen_icjc(int ic, int frameNum) {
        byte[] icjc = new byte[12];
        byte[] icByte = BDMethod.castIntToBytesPos(ic);
        byte[] header = new byte[]{0x24, 0x49, 0x43, 0x4A, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, (byte) frameNum};
        System.arraycopy(icByte, 1, header, 7, 3);
        System.arraycopy(header, 0, icjc, 0, header.length);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(crc, 0, icjc, 11, 1);
        return icjc;
    }

    public static byte[] gen_cksc(byte boudRate) {
        byte[] cksc = new byte[12];
        byte[] header = new byte[]{0x24, 0x43, 0x4B, 0x53, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, boudRate};
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, cksc, 0, header.length);
        System.arraycopy(crc, 0, cksc, 11, 1);
        return cksc;
    }

    public static byte[] gen_cksc(int ic, int boudRate) {
        byte[] cksc = new byte[12];
        byte[] icByte = BDMethod.castIntToBytesPos(ic);
        byte[] header = new byte[]{0x24, 0x43, 0x4B, 0x53, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, (byte) boudRate};
        System.arraycopy(icByte, 1, header, 7, 3);
        System.arraycopy(header, 0, cksc, 0, header.length);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(crc, 0, cksc, 11, 1);
        return cksc;
    }

    public static byte[] gen_xtzj(int freq) {
        byte[] xtzj = new byte[13];
        byte[] header = new byte[]{0x24, 0x58, 0x54, 0x5A, 0x4A, 0x00, 0x0D, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] freqByte = BDMethod.castIntToBytesPos(freq);
        System.arraycopy(freqByte, 2, header, 10, 2);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, xtzj, 0, header.length);
        System.arraycopy(crc, 0, xtzj, 12, 1);
        return xtzj;
    }

    public static byte[] gen_xtzj(int ic, int freq) {
        byte[] xtzj = new byte[13];
        byte[] icByte = BDMethod.castIntToBytesPos(ic);
        byte[] header = new byte[]{0x24, 0x49, 0x43, 0x4A, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] freqByte = BDMethod.castIntToBytesPos(freq);
        System.arraycopy(freqByte, 2, header, 10, 2);
        System.arraycopy(icByte, 1, header, 7, 3);
        System.arraycopy(header, 0, xtzj, 0, header.length);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(crc, 0, xtzj, 12, 1);
        return xtzj;
    }

    public static byte[] gen_sjsc(int freq) {
        byte[] sjsc = new byte[13];
        byte[] header = new byte[]{0x24, 0x53, 0x4A, 0x53, 0x43, 0x00, 0x0D, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] freqByte = BDMethod.castIntToBytesPos(freq);
        System.arraycopy(freqByte, 2, header, 10, 2);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, sjsc, 0, header.length);
        System.arraycopy(crc, 0, sjsc, 12, 1);
        return sjsc;
    }

    public static byte[] gen_sjsc(int ic, int freq) {
        byte[] sjsc = new byte[13];
        byte[] icByte = BDMethod.castIntToBytesPos(ic);
        byte[] header = new byte[]{0x24, 0x53, 0x4A, 0x53, 0x43, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] freqByte = BDMethod.castIntToBytesPos(freq);
        System.arraycopy(freqByte, 2, header, 10, 2);
        System.arraycopy(icByte, 1, header, 7, 3);
        System.arraycopy(header, 0, sjsc, 0, header.length);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(crc, 0, sjsc, 12, 1);
        return sjsc;
    }

    public static byte[] gen_bbdq() {
        byte[] bbdq = new byte[11];
        byte[] header = new byte[]{0x24, 0x42, 0x42, 0x44, 0x51, 0x00, 0x0B, 0x00, 0x00, 0x00,};
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, bbdq, 0, header.length);
        System.arraycopy(crc, 0, bbdq, 10, 1);
        return bbdq;
    }

    public static byte[] gen_bbdq(int ic) {
        byte[] bbdq = new byte[11];
        byte[] icByte = BDMethod.castIntToBytesPos(ic);
        byte[] header = new byte[]{0x24, 0x42, 0x42, 0x44, 0x51, 0x00, 0x0B, 0x00, 0x00, 0x00};
        System.arraycopy(icByte, 1, header, 7, 3);
        System.arraycopy(header, 0, bbdq, 0, header.length);
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(crc, 0, bbdq, 10, 1);
        return bbdq;
    }

    public static byte[] gen_dwsq() {
        byte[] dwsq = new byte[22];
        byte[] header = new byte[]{0x24, 0x44, 0x57, 0x53, 0x51, 0x00, 0x16, 0x00, 0x00, 0x00,
                0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] crc = new byte[]{BDMethod.CheckByte(header, header.length)};
        System.arraycopy(header, 0, dwsq, 0, header.length);
        System.arraycopy(crc, 0, dwsq, 21, 1);
        return dwsq;
    }


    public static byte[] gen_txsq(int revIc, int msgType, String msg) {
        byte[] header = new byte[]{0x24, 0x54, 0x58, 0x53, 0x51, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] mixFlag = new byte[]{(byte) 0xA4};
        byte[] Type = new byte[1];
        byte[] msgContent;
        byte[] txsq;
        int msgLength = 0;
        int sumLength = 0;
        try {
            msgContent = msg.getBytes("gbk");
            msgLength = msgContent.length;
            if (msgType == 0) {
                Type[0] = 0x44;
            } else if (msgType == 1) {
                Type[0] = 0x46;
            } else if (msgType == 2) {
                Type[0] = 0x46;
                msgLength++;
            } else {
                Type[0] = 0x44;
            }
            sumLength = msgLength + 18;
            txsq = new byte[sumLength];
            System.arraycopy(BDMethod.castIntToBytesPos(sumLength), 2, header, 5, 2);//填入总长度
            System.arraycopy(Type, 0, header, 10, 1);//填入信息类别
            System.arraycopy(BDMethod.castIntToBytesPos(revIc), 1, header, 11, 3);//填入收方卡号
            System.arraycopy(BDMethod.castIntToBytesPos(msgLength * 8), 2, header, 14, 2);//填入电文长度
            System.arraycopy(header, 0, txsq, 0, header.length);//填入header
            if (msgType != 2) {
                System.arraycopy(msgContent, 0, txsq, 17, msgLength);//填入电文
            } else {
                System.arraycopy(mixFlag, 0, txsq, 17, 1);//填入电文
                System.arraycopy(msgContent, 0, txsq, 18, msgLength - 1);//填入电文
            }

            byte[] checkByte = new byte[sumLength - 1];
            System.arraycopy(txsq, 0, checkByte, 0, sumLength - 1);//填入电文
            byte[] crc = new byte[]{BDMethod.CheckByte(checkByte, checkByte.length)};
            System.arraycopy(crc, 0, txsq, sumLength - 1, 1);
            return txsq;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[20];
    }

    /**
     * 检测是否为包含$GLZK头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_GLZK(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'G' && buf[2] == 'L' && buf[3] == 'Z' && buf[4] == 'K') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$DWXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_DWXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'D' && buf[2] == 'W' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$TXXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_TXXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'T' && buf[2] == 'X' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$ICXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_ICXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'I' && buf[2] == 'C' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$ZJXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_ZJXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'Z' && buf[2] == 'J' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$SJXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_SJXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'S' && buf[2] == 'J' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BBXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_BBXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'B' && buf[2] == 'B' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$FKXX头部
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_FKXX(byte[] buf) {
        boolean flag = false;
        if (buf.length > 5) {
            if (buf[1] == 'F' && buf[2] == 'K' && buf[3] == 'X' && buf[4] == 'X') {
                flag = true;
            }
        }
        return flag;
    }
}
