package com.bddomainuni.models.entity.protocal2_1;

import android.util.Log;
import com.bddomainuni.models.CheckImpl;
import com.bddomainuni.repository.tools.BDMethod;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 2.1协议RMC信息类
 *
 * Created by admin on 2016/10/20.
 * Modified by admin on 2017/5/13
 */
public class RMCMsg implements CheckImpl, Serializable {
    private String RMCstr;// 定位信息字符串
    // 时间
    private String Date;
    private String Hour;// 定位时刻（时）
    private String Min;// 定位时刻(分)
    private String Sec;// 定位时刻(秒)
    private String sSec;// 定位时刻(飞秒)
    private String UTCTime;
    private String BJTime;// 北京时间字符串形式，如12时30分15秒
    private String timeStampHex;//时间戳毫秒(默认转化为十六进制字符串,4字节)
    private long BjTimeStamp = 0;//北京时间戳
    private boolean DWstaus = false;// 定位状态
    // 经纬度
    private String LatiH;// 纬度（度）
    private String LatiM;// 纬度（分）
    private String LatiS;// 纬度（秒）
    private double Latitude = 0;// 纬度
    private String LatiDeg;// 纬度（度分秒形式）
    private String LatiOrin;// 纬度方向
    private String LongiH;// 经度（度）
    private String LongiM;// 经度（分）
    private String LongiS;// 经度（秒）
    private double Longitude = 0;// 经度
    private float height = 0;// 海拔
    private String LongiDeg;// 经度（度分秒形式）
    private String LongOrin;// 经度方向
    // 速度与方向
    private String speed;
    private String direction;
    // 模式指示
    private String modeInd;
    private boolean Ifvaild = false;

    public RMCMsg() {
        this.timeStampHex = BDMethod.castLongToHexStringByNum((new Date().getTime())/1000,8);
    }

    /**
     * 构造函数
     * 直接解析RMC字节串
     *
     * @param parambytes
     *         RMC字节串
     */
    public RMCMsg(byte[] parambytes) {
        RMCstr = new String(parambytes);
        this.timeStampHex = BDMethod.castLongToHexStringByNum((new Date().getTime())/1000,8);
        String[] items = RMCstr.split(",");
        Ifvaild = BDMethod.CheckCKS(RMCstr);
        if (Ifvaild) {
            if (items.length > 12) {
                if (items[1] == null || items[1].equals("")) {
                    this.SetTime("0", "0", "0", "0");
                } else {
                    if (items[1].length() >= 9) {
                        this.SetTime(items[1].substring(0, 2),
                                items[1].substring(2, 4), items[1].substring(4, 6),
                                items[1].substring(7, 9));
                    }
                }

                if (items[9] == null || items[9].equals("")) {
                    this.SetDate("00", "00", "00");
                } else {
                    if (items[9].length() >= 6) {
                        this.SetDate(items[9].substring(4, 6),
                                items[9].substring(2, 4), items[9].substring(0, 2));
                    }
                }

                this.setUTCandBJTime();

                if (items[2] == null || items[2].equals("")) {
                    this.SetDWstaus("V");
                } else {
                    if (items[2].length() >= 1) {
                        this.SetDWstaus(items[2]);
                    }
                }

                if (items[3] == null || items[3].equals("") || items[4] == null
                        || items[4].equals("")) {
                    LatiDeg = "X000°00′00″";
                } else {
                    if (items[3].length() >= 7) {
                        this.setLatitude(items[3].substring(0, 2),
                                items[3].substring(2, 4), items[3].substring(5),
                                items[4]);
                    }
                }

                if (items[5] == null || items[5].equals("") || items[5] == null
                        || items[6].equals("")) {
                    LongiDeg = "X000°00′00″";
                } else {
                    if (items[5].length() >= 8) {
                        this.setLongitude(items[5].substring(0, 3),
                                items[5].substring(3, 5), items[5].substring(6),
                                items[6]);
                    }
                }

                if (items[7] == null || items[7].equals("")) {
                    this.speed = "00";
                } else {
                    this.speed = items[7];
                }

                if (items[8] == null || items[8].equals("")) {
                    this.direction = "00";
                } else {
                    this.direction = items[8];
                }

                if (items[12] == null || items[12].equals("")) {
                    this.SetModeInd("N");
                } else {
                    if (items[12].length() >= 1) {
                        this.SetModeInd(items[12].substring(0, 1));
                    }
                }
            } else {
                Log.e("FDBDHZerror", "RMCMessage items.length<=12");
            }
        } else {
            initData();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        SetTime("0", "0", "0", "0");
        SetDate("00", "00", "00");
        SetDWstaus("V");
        LatiDeg = "X000°00′00″";
        LongiDeg = "X000°00′00″";
        speed = "00";
        direction = "00";
        this.LatiOrin = "N";
        this.LongOrin = "E";
        SetModeInd("N");
    }

    /**
     * @return 海拔高度 单位米
     */
    public float getHeight() {
        return height;
    }

    /**
     * 设置海拔高度,单位米
     *
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * 获取航向
     *
     * @return 航向字符串
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 获取速度
     *
     * @return 速度字符串
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * 获取定位模式
     *
     * @return 定位模式字符串
     */
    public String getModeInd() {
        return modeInd;
    }

    /**
     * 设置定位模式
     *
     * @param mode
     *         定位模式字符串
     */
    private void SetModeInd(String mode) {
        switch (mode) {
            case "A":
                modeInd = "自主定位";
                break;
            case "D":
                modeInd = "差分";
                break;
            case "E":
                modeInd = "估算";
                break;
            case "N":
                modeInd = "数据无效";
                break;
            default:
                break;
        }
    }

    /**
     * 设置日期
     *
     * @param year
     *         年
     * @param month
     *         月
     * @param day
     *         日
     */
    public void SetDate(String year, String month, String day) {
        if (day == "00") {
            Date = "00" + year + "-" + month + "-" + day;
        } else {
            Date = "20" + year + "-" + month + "-" + day;
        }
    }

    /**
     * 获取日期
     *
     * @return 日期字符串
     */
    public String getDate() {
        return Date;
    }

    /**
     * 获取纬度
     *
     * @return 双精度形式纬度
     */
    public double getLatitude() {
        return Latitude;
    }

    /**
     * 获取角度形式纬度
     *
     * @return 纬度
     */
    public String getLatiDeg() {
        return LatiDeg;
    }

    /**
     * 获取经度
     *
     * @return 双精度形式经度
     */
    public double getLongitude() {
        return Longitude;
    }

    /**
     * 获取角度形式经度
     *
     * @return 经度
     */
    public String getLongiDeg() {
        return LongiDeg;
    }

    /**
     * 设置纬度
     *
     * @param mlath
     *         度
     * @param mlatm
     *         分
     * @param mlatmm
     *         秒
     * @param mlatdir
     *         方向
     */
    public void setLatitude(String mlath, String mlatm, String mlatmm,
                            String mlatdir) {
        this.LatiH = mlath;
        this.LatiM = mlatm;
        this.LatiS = ("" + BDMethod.castStringToInt(mlatmm) * 60).substring(0, 2);
        this.LatiOrin = mlatdir;
        this.Latitude = Integer.parseInt(mlath) + Double.parseDouble(mlatm) / 60 + Double.parseDouble("0." + mlatmm) / 60;// 119.12345648
        this.LatiDeg = mlatdir + LatiH + "°" + LatiM + "′" + LatiS + "″"; // N
        // 114°18′1〃
    }

    /**
     * 设置纬度
     *
     * @param mlat
     *         双精度形式纬度
     */
    public void setLatitude(Double mlat) {
        String mlatStr = String.valueOf(mlat);
        if (mlatStr.length() > 0 && mlatStr.indexOf(".") != -1) {
            if (mlat > 0) {
                this.LatiOrin = "N";
            } else {
                this.LatiOrin = "S";
            }
            this.Latitude = mlat;
            String[] arrayH = mlatStr.split("\\.");
            this.LatiH = arrayH[0];
            String[] arrayM = String.valueOf(Double.parseDouble("0." + arrayH[1]) * 60).split("\\.");
            this.LatiM = String.format("%02d", Integer.parseInt(arrayM[0]));
            String[] arrayS = String.valueOf(Double.parseDouble("0." + arrayM[1]) * 60).split("\\.");
            this.LatiS = String.format("%02d", Integer.parseInt(arrayS[0]));
            this.LatiDeg = LatiOrin + LatiH + "°" + LatiM + "′" + LatiS + "″"; //
        }


    }

    /**
     * 设置经度
     *
     * @param lngh
     *         度
     * @param lngm
     *         分
     * @param lngmm
     *         秒
     * @param lngdir
     *         方向
     */
    public void setLongitude(String lngh, String lngm, String lngmm,
                             String lngdir) {
        this.LongiH = lngh;
        this.LongiM = lngm;
        this.LongiS = ("" + BDMethod.castStringToInt(lngmm) * 60).substring(0, 2);
        this.LongOrin = lngdir;
        this.Longitude = Double.parseDouble(lngh) + Double.parseDouble(lngm) / 60 + Double.parseDouble("0." + lngmm) / 60;// 26.12345648
        this.LongiDeg = lngdir + LongiH + "°" + LongiM + "′" + LongiS + "″"; // E
        // 23°18′1〃
    }

    /**
     * 设置经度
     *
     * @param mlng
     *         双精度形式经度
     */
    public void setLongitude(Double mlng) {
        String mlngStr = String.valueOf(mlng);
        if (mlngStr.length() > 0 && mlngStr.indexOf(".") != -1) {
            if (mlng > 0) {
                this.LongOrin = "E";
            } else {
                this.LongOrin = "W";
            }
            this.Longitude = mlng;
            String[] arrayH = mlngStr.split("\\.");
            this.LongiH = arrayH[0];
            String[] arrayM = String.valueOf(Double.parseDouble("0." + arrayH[1]) * 60).split("\\.");
            this.LongiM = String.format("%02d", Integer.parseInt(arrayM[0]));
            String[] arrayS = String.valueOf(Double.parseDouble("0." + arrayM[1]) * 60).split("\\.");
            this.LongiS = String.format("%02d", Integer.parseInt(arrayS[0]));
            this.LongiDeg = LongOrin + LongiH + "°" + LongiM + "′" + LongiS + "″"; //
        }
    }

    /**
     * 设置定位有效性标志
     *
     * @param stausStr
     *         定位有效性状态
     */
    public void SetDWstaus(String stausStr) {
        switch (stausStr) {
            case "A":
                DWstaus = true;
                break;
            case "V":
                DWstaus = false;
                break;
            default:
                break;
        }
    }

    /**
     * 获取定位有效性状态
     *
     * @return 定位有效性状态
     */
    public boolean getDWstaus() {
        return DWstaus;
    }

    /**
     * 设置时间
     *
     * @param h
     *         时
     * @param m
     *         分
     * @param s
     *         秒
     * @param ss
     *         毫秒
     */
    public void SetTime(String h, String m, String s, String ss) {
        this.Hour = h;
        this.Min = m;
        this.Sec = s;
        this.sSec = ss;
        //this.UTCTime = h + "时" + m + "分" + s + "秒";
        //this.BJTime = (Integer.parseInt(h) + 8) + ":" + m + ":" + s;
    }

    /**
     * 设置时间
     *
     * @param mTime
     *         时间戳
     */
    public void SetTime(long mTime) {
        Date date = new Date(mTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.UTCTime = sdf.format(date);
        //this.UTCTime = h + "时" + m + "分" + s + "秒";
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        this.BJTime = sdf.format(date);
    }

    /**
     * 设置北京时间与UTC时间
     */
    private void setUTCandBJTime() {
        UTCTime = Date + " " + Hour + ":" + Min + ":" + Sec;
        BJTime = BDMethod.castUTCtimeToBeijingTime(UTCTime);
        setBjTimeStamp(BJTime);
    }

    /**
     * 获取北京时间
     *
     * @return 北京时间字符串
     */
    public String getBJTime() {
        return BJTime;
    }

    /**
     * 获取UTC时间
     *
     * @return UTC时间字符串
     */
    public String getUTCTime() {
        return UTCTime;
    }

    /**
     * 检测命令是否有效
     *
     * @return 有效标志位
     */
    @Override
    public boolean getVaild() {
        return Ifvaild;
    }

    /**
     * 获取纬度 度
     */
    public String getLatiH() {
        return LatiH;
    }

    /**
     * 获取纬度 分
     */
    public String getLatiM() {
        return LatiM;
    }

    /**
     * 获取纬度 秒
     */
    public String getLatiS() {
        return LatiS;
    }

    /**
     * 获取纬度方向
     */
    public String getLatiOrin() {
        return LatiOrin;
    }

    /**
     * 获取经度 度
     */
    public String getLongiH() {
        return LongiH;
    }

    /**
     * 获取经度 分
     */
    public String getLongiM() {
        return LongiM;
    }

    /**
     * 获取经度 秒
     */
    public String getLongiS() {
        return LongiS;
    }

    /**
     * 获取经度方向
     */
    public String getLongOrin() {
        return LongOrin;
    }

    /**
     * 设置纬度 度
     */
    private void setLatiH(String latiH) {
        LatiH = latiH;
    }

    /**
     * 设置纬度 分
     */
    private void setLatiM(String latiM) {
        LatiM = latiM;
    }

    /**
     * 设置纬度 秒
     */
    private void setLatiS(String latiS) {
        LatiS = latiS;
    }

    /**
     * 设置经度 度
     */
    private void setLongiH(String longiH) {
        LongiH = longiH;
    }

    /**
     * 设置经度 分
     */
    private void setLongiM(String longiM) {
        LongiM = longiM;
    }

    /**
     * 设置经度 秒
     */
    private void setLongiS(String longiS) {
        LongiS = longiS;
    }

    /**
     * 设置纬度方向
     */
    private void setLatiOrin(String latiOrin) {
        LatiOrin = latiOrin;
    }

    /**
     * 设置经度方向
     */
    public void setLongOrin(String longOrin) {
        LongOrin = longOrin;
    }

    /**
     * 设置速度
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }

    /**
     * 设置航向
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 获取原始RMC协议信息
     *
     * @return RMC 协议信息
     */
    public String getRmcStr() {
        return RMCstr.substring(0, RMCstr.indexOf("*") + 3);
    }

    private void setBjTimeStamp(String bjTime) {
        BjTimeStamp = BDMethod.castDateToTimestamp(bjTime);
    }

    public long getBjTimeStamp() {
        return BjTimeStamp;
    }

    /**
     * 设置原始RMC协议信息
     *
     */
/*    private void setRMCstr(String RMCstr) {
        try {
            this.RMCstr = RMCstr.substring(0,RMCstr.indexOf("*")+2);
        } catch (Exception e) {
            Log.v("FDBDErrorLog","RMCstr设置原始数据出错");
        }
    }*/

    public String gettimeStampHex() {
        return timeStampHex;
    }

    public void settimeStampHex() {
        //this.timeStampHex = timeStamp;
        this.timeStampHex = BDMethod.castLongToHexStringByNum((new Date().getTime())/1000,8);

    }
}
