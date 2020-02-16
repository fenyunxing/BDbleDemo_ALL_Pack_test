package com.bddomainuni.repository.protcals;


import com.bddomainuni.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;

/**
 * 北斗2.1协议类
 * Created by admin on 2016/11/22.
 *
 * @version: V1.0.0
 */
public class protocal2_1 {

    //传输方式 0汉字  1 代码  2 混发 3平台
    public final static int HANZI = 0;
    public final static int DAIMA = 1;
    public final static int HUNFA = 2;

    //通信类别 0特快  1 普通
    public final static int COMMTYPE_FAST = 0;
    public final static int COMMTYPE_NORMAL = 1;


    /**
     * 组装功率检测申请指令方法（2.1协议）
     *
     * @param a
     *         状态：1为关闭输出功率状态，2为输出功率状态
     * @param frequence
     *         间隔频度，单位：秒
     *
     * @return 返回组装好的功率检测申请命令string
     *
     * @Description: 输出为$CCRMO, BSI, 2, 1*27
     */
    public static String gen_ccrmo(int a, int frequence) {
        String cmd = "";
        if (a == 2) {
            cmd = "CCRMO,BSI," + a + "," + frequence;
        } else if (a == 1) {
            cmd = "CCRMO,BSI," + a + ",";
        }
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 组装IC检测申请指令方法（2.1协议）
     *
     * @param a
     *         未知，填0
     * @param kcnum
     *         卡槽号，默认0
     *
     * @return 返回组装好的IC检测申请命令string
     *
     * @Description: 输出为$CCICA, 0, 0*4B
     */
    public static String gen_ccica(int a, int kcnum) {
        String cmd = "CCICA," + a + "," + kcnum;
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 组装通信申请指令方法（2.1协议）
     *
     * @param ic
     *         接收方ic卡
     * @param txlx
     *         通信类别 0特快  1 普通
     * @param csfs
     *         传输方式 0汉字  1 代码  2 混发 3平台
     * @param str
     *         传输的内容
     *
     * @return 返回单条报文命令
     *
     * @Description: 输出为$CCTXA, 0000000, 1, 2, A4313233C4E3BAC3D5E2CAC7B2E2CAD4*7F
     */
    public static String gen_cctxa(String ic, int txlx, int csfs, String str) {
        String cmd = "CCTXA," + ic + "," + txlx + "," + csfs + ",";
        String strfordata = "";

        if (csfs == HANZI) {//汉字
            cmd += str;
            strfordata = str;
        } else if (csfs == DAIMA) {//代码
            cmd += str;
            strfordata = str;
        } else if (csfs == HUNFA) {//混发
            byte[] bytes = {};
            try {
                bytes = str.getBytes("gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] data = BDMethod.conArrayOfBytes(new byte[]{(byte) 0xA4}, bytes);
            strfordata = BDMethod.castBytesToHexString(data);
            cmd += strfordata;

        }
        //至此，${ cmd }*
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);

        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    public static String gen_cctxa(String ic, int txlx, int csfs, String str, String hexlnltStr) {
        String cmd = "CCTXA," + ic + "," + txlx + "," + csfs + ",";
        String strfordata = "";

        if (csfs == HANZI) {//汉字
            cmd += str;
            strfordata = str;
        } else if (csfs == DAIMA) {//代码
            cmd += str;
            strfordata = str;
        } else if (csfs == HUNFA) {//混发
            byte[] bytes = {};
            try {
                bytes = str.getBytes("gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //byte[] data = BDMethod.conArrayOfBytes(new byte[]{(byte) 0xA4}, bytes);
            strfordata = "A4A5" + hexlnltStr + BDMethod.castBytesToHexString(bytes);
            cmd += strfordata;
        }
        //至此，${ cmd }*
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);

        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 组装定位申请指令方法（2.1协议）
     *
     * @param ic
     *         卡号
     * @param AorV
     *         A:紧急定位   V:普通定位
     * @param cegaofangshi
     *         测高方式
     * @param gaochengzhishi
     *         高程指示
     * @param gaochenzhi
     *         高程值
     * @param tianxiangao
     *         天线高
     * @param qiyazhi
     *         气压值
     * @param wenduzhi
     *         温度值
     * @param shenqingpindu
     *         申请频度 单位：秒
     *
     * @return 返回定位申请命令
     *
     * @Description: 输出为$CCDWA, 2097151, V, 1, L, 0, 0.0, 0.0, 0.0, 0*42
     */
    public static String gen_ccdwa(String ic, String AorV, int cegaofangshi, String gaochengzhishi, String gaochenzhi,
                                   double tianxiangao, double qiyazhi, double wenduzhi, int shenqingpindu) {
        //$CCDWA,0456094,V,1,L,0,0,0,0,0*5F
        String cmd = "CCDWA," + ic + "," + AorV + "," + cegaofangshi + "," + gaochengzhishi + "," + gaochenzhi + "," +
                tianxiangao + "," + qiyazhi + "," + wenduzhi + "," + shenqingpindu;
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 组装位置报告申请指令方法（2.1协议）
     *
     * @param IC
     *         接收报告的用户地址
     * @param atitudeInd
     *         高程指示
     * @param AntennaHigh
     *         天线高
     * @param freq
     *         报告频度
     *
     * @return 返回组装好的位置报告申请指令string
     */
    public static String gen_ccwba(String IC, String atitudeInd, String AntennaHigh, String freq) {
        String cmd = "CCWBA," + IC + "," + atitudeInd + "," + AntennaHigh + "," + freq;
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    /**
     * 组装位置报告1申请（2.1协议）
     *
     * @param ic
     *         本设备卡号
     * @param freq
     *         报告频度
     * @param time
     *         时间
     * @param lat
     *         纬度
     * @param latDir
     *         纬度方向
     * @param lng
     *         经度
     * @param lngDir
     *         经度方向
     * @param high
     *         高程值
     * @param highUnit
     *         高程单位
     */
    public static String gen_ccwaa(String ic, String type, String freq, String time, String lat, String latDir,
                                   String lng, String lngDir, String high, String highUnit) {
        //$CCDWA,0456094,V,1,L,0,0,0,0,0*5F
        String cmd = "CCWAA," + type + "," + freq + "," + ic + "," + time + "," + lat + "," +
                latDir + "," + lng + "," + lngDir + "," + high + "," + highUnit;
        byte[] tmp = cmd.getBytes();
        byte ret = BDMethod.CheckByte(tmp, tmp.length);
        String crc = BDMethod.castByteToHexString(ret);
        String result = "$" + cmd + "*" + crc + "\r\n";
        return result;
    }

    public static String gen_clogps() {
        String result = "$CCRMO,,3,*4F\r\n";
        return result;
    }

    public static String gen_opngps() {
        String result = "$CCRMO,,4,1.5*62\r\n";
        return result;
    }


    /**
     * 检测是否为包含$--WAA头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_WAA(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[3] == 0x57 && buf[4] == 0x41 && buf[5] == 0x41) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDICI头部 （2.1协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isBDHead_BDICI(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x49 && buf[4] == 0x43 && buf[5] == 0x49) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDDWR头部 （2.1协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isBDHead_BDDWR(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x44 && buf[4] == 0x57 && buf[5] == 0x52) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDTXR头部 （2.1协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isBDHead_BDTXR(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x54 && buf[4] == 0x58 && buf[5] == 0x52) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDBSI头部 （2.1协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isBDHead_BDBSI(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x42 && buf[4] == 0x53 && buf[5] == 0x49) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$BDFKI头部 （2.1协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isBDHead_BDFKI(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x46 && buf[4] == 0x4B && buf[5] == 0x49) {
                flag = true;
            }
        }
        return flag;
    }

 /*    *//**
     * 检测是否为包含$BDGGA头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
    public static boolean isHead_BDGGA(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x47 && buf[4] == 0x47 && buf[5] == 0x41)
             {
                 flag = true;
             }
         }
         return flag;
     }

    *//**
     * 检测是否为包含$GPGGA头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
     public static boolean isHead_GPGGA(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[0] == 0x24 && buf[1] == 0x47 && buf[2] == 0x50 && buf[3] == 0x47 && buf[4] == 0x47 && buf[5] == 0x41)
             {
                 flag = true;
             }
         }
         return flag;
     }

     *//**
     * 检测是否为包含$GLGGA头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
     public static boolean isHead_GLGGA(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[0] == 0x24 && buf[1] == 0x47 && buf[2] == 0x4C && buf[3] == 0x47 && buf[4] == 0x47 && buf[5] == 0x41)
             {
                 flag = true;
             }
         }
         return flag;
     }

     *//**
     * 检测是否为包含$GAGGA头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
     public static boolean isHead_GAGGA(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[0] == 0x24 && buf[1] == 0x47 && buf[2] == 0x41 && buf[3] == 0x47 && buf[4] == 0x47 && buf[5] == 0x41)
             {
                 flag = true;
             }
         }
         return flag;
     }

     */

    /**
     * 检测是否为包含$GNGGA头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_GNGGA(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[0] == 0x24 && buf[1] == 0x47 && buf[2] == 0x4E && buf[3] == 0x47 && buf[4] == 0x47 && buf[5] == 0x41) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测是否为包含$GPGSV头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
     public static boolean isHead_GPGSV(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[0] == 0x24 && buf[1] == 0x47 && buf[2] == 0x50 && buf[3] == 0x47 && buf[4] == 0x53 && buf[5] == 0x56)
             {
                 flag = true;
             }
         }
         return flag;
     }

     *//**
     * 检测是否为包含$BDGSV头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
     public static boolean isHead_BDGSV(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[0] == 0x24 && buf[1] == 0x42 && buf[2] == 0x44 && buf[3] == 0x47 && buf[4] == 0x53 && buf[5] == 0x56)
             {
                 flag = true;
             }
         }
         return flag;
     }

     *//**
     * 检测是否为包含$--GSV头部 （二代模块协议）
     * @param buf 接收到的指数据字节串
     * @return 包含true 不包含false
     *//*
     public static boolean isHead_GSV(byte[] buf)
     {
         boolean flag = false;
         if (buf.length > 6)
         {
             if (buf[3] == 0x47 && buf[4] == 0x53 && buf[5] == 0x56)
             {
                 flag = true;
             }
         }
         return flag;
     } */

    /**
     * 检测是否为包含$--RMC头部 （二代模块协议）
     *
     * @param buf
     *         接收到的指数据字节串
     *
     * @return 包含true 不包含false
     */
    public static boolean isHead_RMC(byte[] buf) {
        boolean flag = false;
        if (buf.length > 6) {
            if (buf[3] == 0x52 && buf[4] == 0x4D && buf[5] == 0x43) {
                flag = true;
            }
        }
        return flag;
    }


}
