package com.bddomainuni.repository.tools;

import com.bddomainuni.models.entity.protocal2_1.RMCMsg;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 常用工具方法类
 *
 * Modified by admin on 2017/05/13
 * Created by admin on 2016/11/22.
 */
public class BDMethod {
    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param paramArrayOfByte
     *         字节数组
     *
     * @return 十六进制字符串
     */
    public static final String castBytesToHexString(byte[] paramArrayOfByte) {

        String str = null;
        StringBuffer result = new StringBuffer();
        for (int j = 0; j < paramArrayOfByte.length; j++) {
            str = Integer.toHexString(
                    (paramArrayOfByte[j] & 0x000000FF) | 0xFFFFFF00).substring(
                    6);
            result.append(str);
        }
        return result.toString().toUpperCase();
    }

    /**
     * 将单字节转换为十六进制字符
     *
     * @param paramArrayOfByte
     *         单字节
     *
     * @return 十六进制字符
     */
    public static final String castByteToHexString(byte paramArrayOfByte) {

        String str = null;
        StringBuffer result = new StringBuffer();
        str = Integer.toHexString((paramArrayOfByte & 0x000000FF) | 0xFFFFFF00)
                .substring(6);
        result.append(str);
        return result.toString().toUpperCase();
    }

    /**
     * 将十六进制字符串转化为字节数组
     *
     * @param hexString
     *         十六进制字符串
     *
     * @return 字节数组
     */
    public static final byte[] castHexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    /**
     * 16进制字符转为byte值 如：E --> 15
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将单字符转换为十六进制单字节
     *
     * @param paramChar
     *         单字符
     *
     * @return 十六进制单字节
     */
    public static final byte castCharToHexByte(char paramChar) {
        byte b = -1;
        if ((paramChar >= '0') && (paramChar <= '9')) {
            b = (byte) paramChar;
            return b;
        }
        do {
            if ((paramChar >= 'A') && (paramChar <= 'F'))
                return (byte) (10 + (paramChar - 'A'));
        } while ((paramChar < 'a') || (paramChar > 'f'));
        return (byte) (10 + (paramChar - 'a'));
    }

    /**
     * 按字节异或
     *
     * @param parambyte
     *         字节数组
     * @param length
     *         字节数组长度
     *
     * @return 按字节异或结果（单字节）
     */
    public static final byte CheckByte(byte[] parambyte, int length) {
        if (length > 0) {
            byte bytetemp;
            bytetemp = parambyte[0];
            for (int i = 1; i < length; i++) {
                bytetemp = (byte) (bytetemp ^ parambyte[i]);
            }

            return bytetemp;
        }
        return (Byte) null;
    }

    /**
     * 校验指令返回校验结果
     *
     * @param paramStr
     *         校验指令
     *
     * @return 校验结果
     */
    public static final boolean CheckCKS(String paramStr) {
        try {
            String checkStr = paramStr.substring(paramStr.indexOf("$") + 1, paramStr.indexOf("*"));
            String CKS = paramStr.substring(paramStr.indexOf("*") + 1, paramStr.indexOf("*") + 3);
            String checkResult = null;
            checkResult = BDMethod.castByteToHexString(CheckByte(checkStr.getBytes("gbk"), checkStr.getBytes("gbk").length));
            if (checkResult.equals(CKS)) {
                /*            Log.v("CKStest","校验结果----->正确");*/
                return true;
            } else {
                /*            Log.v("CKStest","校验结果----->错误");*/
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
/*        Log.v("CKStest","取出的待校验部分----->"+checkStr);
        Log.v("CKStest","取出的校验和----->"+CKS);*/
    }

    /**
     * 校验指令返回校验结果(4.0协议)
     *
     * @param paramByte
     *         校验指令
     *
     * @return 校验结果
     */
    public static final boolean CheckCKS_40(byte[] paramByte) {
        byte[] checkByte = new byte[paramByte.length - 1];
        System.arraycopy(paramByte, 0, checkByte, 0, paramByte.length - 1);
        //Log.v("CKStest","checkByte ==> " + BDMethod.castBytesToHexString(checkByte));
        byte[] CKS = new byte[1];
        System.arraycopy(paramByte, paramByte.length - 1, CKS, 0, 1);
        //Log.v("CKStest","CKS ==> " + BDMethod.castBytesToHexString(CKS));
        byte checkResult = CheckByte(checkByte, checkByte.length);
        //Log.v("CKStest","checkResult ==> " + BDMethod.castByteToHexString(checkResult));
        if (checkResult == CKS[0]) {
            //Log.v("CKStest","校验结果----->正确");
            return true;
        } else {
            //Log.v("CKStest","校验结果----->错误");
            return false;
        }
    }

    /**
     * 将十六进制字符串转化为十进制字符串
     *
     * @param paramString
     *         十六进制字符串
     *
     * @return 返回十进制字符串
     */
    public static final String castHexStringToDcmString(String paramString) {
        return String.valueOf(Long.valueOf(Long.parseLong(paramString, 16)));
    }
    public static final String castDcmStringToHexString(String paramString) {
        return Integer.toHexString(Integer.parseInt(paramString)).toUpperCase();
    }
    public static final String castIntToHexString(int paramInt) {
        return Integer.toHexString(paramInt).toUpperCase();
    }
    public static final String castLongToHexString(long paramLong) {
        return Long.toHexString(paramLong).toUpperCase();
    }
    public static final String castLongToHexStringByNum(long paramLong,int num) {
        return StringUtils.leftPad(castLongToHexString(paramLong), num, "0");
    }
    public static final String castIntToHexStringByNum(int paramInt,int num) {
        return StringUtils.leftPad(castIntToHexString(paramInt), num, "0");
    }

    /**
     * 将字节数组转换为整数
     *
     * @param parambyte
     *         字节数组 //高位在后，低位在前（反序）
     *
     * @return 整数
     */
    public static final int castBytesToInt(byte[] parambyte) {
        int iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < parambyte.length; i++) {
            bLoop = parambyte[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);
        }
        return iOutcome;
    }

    /**
     * 将字节数组转换为整数
     *
     * @param parambyte
     *         字节数组 //高位在前，低位在后（正序）
     *
     * @return 整数
     */
    public static final int castBytesToIntByPos(byte[] parambyte) {

        return (((int) (parambyte[0] & 0x0ff)) << 24) + (((int) (parambyte[1] & 0x0ff)) << 16) +
                (((int) (parambyte[2] & 0x0ff)) << 8) + (int) (parambyte[3] & 0x0ff);
    }

    /**
     * 将整数转为字节数组
     *
     * @param value
     *         整形数组
     *
     * @return 字节数组
     */
    public static byte[] castIntToBytesPos(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将字符串转化为整数
     *
     * @param intstr
     *         字符串
     *
     * @return 整数
     */
    public static int castStringToInt(String intstr) {
        Integer integer;
        integer = Integer.valueOf(intstr);
        return integer.intValue();
    }

    public static double castStringToDouble(String doubleStr) {
        Double doubleOut;
        doubleOut = Double.valueOf(doubleStr);
        return doubleOut.doubleValue();
    }

    /**
     * 将日历对象转化为字符串
     *
     * @param paramCalendar
     *         日历对象
     * @param paramString
     *         日历显示格式（如：yy/MM/dd HH:mm:ss）
     *
     * @return 一定格式的日历字符串
     */
    public static String castCalendarToString(Calendar paramCalendar,
                                              String paramString) {
        if (paramCalendar == null) {
            paramCalendar = Calendar.getInstance();
        }
        if (paramString == null) {
            paramString = "yy/MM/dd HH:mm:ss";
        }
        return new SimpleDateFormat(paramString)
                .format(paramCalendar.getTime());
    }

	/*	*//**
     * 将十六进制字符串转换为汉字字符串
     *
     * @param paramString
     *            十六进制字符串
     * @return gbk编码的中文字符串
     */
    /*
     * public static final String castHexStringToHanziString(byte[] arrayOfByte)
	 * { try { String str = new String(arrayOfByte, "gbk"); return str; } catch
	 * (Exception localException) { } return null; }
	 */

    /**
     * 将十六进制字符串转换为汉字字符串
     *
     * @param paramString
     *         十六进制字符串
     *
     * @return gbk编码的中文字符串
     */
    public static final String castHexStringToHanziString(String paramString) {
        byte[] arrayOfByte = castHexStringToBytes(paramString);
        try {
            String str = new String(arrayOfByte, "gbk");
            return str;
        } catch (Exception localException) {
        }
        return null;
    }

    /**
     * 从一个byte[]数组中截取一部分
     *
     * @param src
     *         字符数组源
     * @param begin
     *         字节开始位置（从0）
     * @param count
     *         截取几个字节
     *
     * @return 返回截取的字节
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++)
            bs[i - begin] = src[i];
        return bs;
    }

    /**
     * 组装byte[]
     *
     * @param c
     *         字节数组
     * @param d
     *         字节数组
     *
     * @return ret 组合字节数组
     */
    public static byte[] conArrayOfBytes(byte[] c, byte[] d) {
        byte[] a = c;
        byte[] b = d;
        byte[] arrayOfByte = new byte[a.length + b.length];
        System.arraycopy(a, 0, arrayOfByte, 0, a.length);
        System.arraycopy(b, 0, arrayOfByte, a.length, b.length);
        return arrayOfByte;
        /*byte[] ret = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i];
        }
        for (int j = 0; j < b.length; j++) {
            ret[a.length + j] = b[j];
        }
        return ret;*/
    }

    /**
     * 将十六进制数字字符串转化为字节数组
     * 如：1234 --> 0x12 0x34
     *
     * @param HexString
     *         十六进制数字字符串
     */
    public static byte[] BytesTwoToOne(String HexString) {
        byte two[] = HexString.getBytes();
        byte tempA;
        byte tempB;
        byte tempC;
        int compresslength = two.length / 2;
        byte one[] = new byte[compresslength];
        int j = 0;
        for (int i = 0; i < compresslength; i++) {
            tempA = (byte) (two[j] & 15);
            tempB = (byte) (two[j + 1] & 15);
            tempC = (byte) ((tempA << 4) + tempB);
            one[i] = tempC;
            j = j + 2;
        }
        return one;
        //return new String(one);
    }
/*
    //以String中的char元素为一个基本单位，16bits，存储4个数字的4bits。以下数组用空间换时间，分别构造出数字0~9的4bit位存储模型
    static final char LOW_LOW_BYTES[] = {0x0000, 0x0001, 0x0002, 0x0003, 0x0004, 0x0005, 0x0006, 0x0007, 0x0008, 0x0009};    //最右边的4位
    static final char LOW_HIGH_BYTES[] = {0x0000, 0x0010, 0x0020, 0x0030, 0x0040, 0x0050, 0x0060, 0x0070, 0x0080, 0x0090};    //倒数第二个右边的4位
    static final char HIGH_LOW_BYTES[] = {0x0000, 0x0100, 0x0200, 0x0300, 0x0400, 0x0500, 0x0600, 0x0700, 0x0800, 0x0900};    //倒数第三个右边的4位
    static final char HIGH_HIGH_BYTES[] = {0x0000, 0x1000, 0x2000, 0x3000, 0x4000, 0x5000, 0x6000, 0x7000, 0x8000, 0x9000};    //最左边的4位
    static final char BYTES[][] = {HIGH_HIGH_BYTES, HIGH_LOW_BYTES, LOW_HIGH_BYTES, LOW_LOW_BYTES};    //为了方便做循环，将上面的数组拼在一起

    public static String numCompress(String num) {
        num = "0"+num;
        int size = num.length();
        size = ( size & 0x03 ) != 0 ? size >> 2 + 1 : size >> 2;    //size&0x03表示对4取模，判断是否整除4。计算最后返回String类型字符长度
        StringBuilder temp = new StringBuilder(size);
        char c = 0x0000;
        for(int i = 0; i < num.length(); ++i) {    //对String从左到右处理，左边的可以认为是字串的高位
            if ((i & 0x03) == 0 && i != 0) {    //每处理完String的4个字符就做一次拼接，但最后一次的此处拼接不了
                temp.append(c);
                c = 0x0000;    //恢复到原始状态，等待下一次拼接
            }
            c |= BYTES[i & 0x03][Integer.valueOf(String.valueOf(num.charAt(i)))];    //将逐数字在各自的位模型用 '或' 操作
        }
        return temp.append(c).toString();    //处理最后一次拼接，然后转化成String并返回
    }
    //输入字符串为 "1234"，计算后返回字符串的内存结构（16进制）为0x1234，也即是 0001 0010 0011 0100，但是这个字符不可见...*/

    /**
     * 将秒转化为日时分秒
     *
     * @param ttime
     *         秒
     */
    public static String timeCalculate(long ttime) {
        long daysuuu, hoursuuu, minutesuuu, secondsuuu;
        String daysT = "", restT = "";
        daysuuu = (Math.round(ttime) / 86400);
        hoursuuu = (Math.round(ttime) / 3600) - (daysuuu * 24);
        minutesuuu = (Math.round(ttime) / 60) - (daysuuu * 1440) - (hoursuuu * 60);
        secondsuuu = Math.round(ttime) % 60;
        if (daysuuu == 1) daysT = String.format("%d day ", daysuuu);
        if (daysuuu > 1) daysT = String.format("%d days ", daysuuu);
        restT = String.format("%02d:%02d:%02d", hoursuuu, minutesuuu, secondsuuu);
        return daysT + restT;
    }

    /**
     * 将日期时间转换为时间戳
     *
     * @param s
     *         满足"yyyy-MM-dd HH:mm:ss"格式的日期时间字符串
     *
     * @return 时间戳
     */
    public static long castDateToTimestamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    /**
     * 将时间戳转换为日期时间字符串
     *
     * @param time
     *         时间戳(毫秒)
     *
     * @return 满足"yyyy-MM-dd HH:mm:ss"格式的日期时间字符串
     */
    public static String castTimestampToDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * UTC时间转换为北京时间
     *
     * @param date
     *         utc时间 格式为 yyyy-MM-dd HH:mm:ss
     */
    public static String castUTCtimeToBeijingTime(String date) {
        return BDMethod.castTimestampToDate(BDMethod.castDateToTimestamp(date) + 3600 * 8 * 1000);
    }


    /**
     * 将十六进制字符串转换为十进制字符串
     * @param a 十六进制字符串
     */
    public static String castHexStrToDcmStr(String a){
        return Integer.parseInt(a, 16)+"";
    }

    /**
     * 将字符串转换为符合GB18030编码的HEX字符串
     *
     * @param data
     *
     * @return
     */
    public static String castStrToHexStrByGB18030(String data){
        byte[] bytes = {};
        try {
            bytes = data.getBytes("gb18030");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return castBytesToHexString(bytes);
    }

    public static RMCMsg parsePosition(String data, RMCMsg rmcMsg){
        double lng = Double.valueOf(BDMethod.castHexStringToDcmString(data.substring(0,8)))/1000000;
        double lat = Double.valueOf(BDMethod.castHexStringToDcmString(data.substring(8,16)))/1000000;

        if(lng > 180d || lat > 90d){
            rmcMsg.SetDWstaus("V");
            return rmcMsg;
        }
        float height = Float.valueOf(BDMethod.castHexStringToDcmString(data.substring(16)));
        rmcMsg.setLongitude(lng);
        rmcMsg.setLatitude(lat);
        rmcMsg.setHeight(height);
        rmcMsg.SetDWstaus("A");
        return rmcMsg;
    }
}
