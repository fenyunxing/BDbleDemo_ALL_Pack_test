package com.bddomainuni.models.entity.protocal2_1;

import android.content.Context;

import com.bddomainuni.models.CheckImpl;
import com.bddomainuni.repository.tools.BDMethod;


/**
 * 2.1协议定位信息类
 *
 * Created by admin on 2016/10/20.
 */
public class DWRMsg implements CheckImpl {
    //基本信息
    private String DWType;//定位信息类型
    private String IDNum;//用户地址
    //时间
    private String Hour;//定位时刻（时）
    private String Min;//定位时刻(分)
    private String Sec;//定位时刻(秒)
    private String sSec;//定位时刻(飞秒)
    private String UTCTime;
    private String BJTime;//北京时间字符串形式，如12时30分15秒
    //经纬度
    private String LatiH;//纬度（度）
    private String LatiM;//纬度（分）
    private String LatiS;//纬度（秒）
    private double Latitude;//纬度
    private String LatiDeg;//纬度（度分秒形式）
    private String LatiOrin;//纬度方向
    private String LongiH;//经度（度）
    private String LongiM;//经度（分）
    private String LongiS;//经度（秒）
    private double Longitude;//经度
    private String LongiDeg;//经度（度分秒形式）
    private String LongOrin;//经度方向
    //高程差
    private String GeoHig;//大地高
    private String GeoHigUnit;//大地高单位
    private String HigAno;//高程异常
    private String HigAnoUnit;//高程异常单位
    //指示
    private String PrecisionInd;//精度指示
    private String EmergencyDW;//紧急定位指示
    private String MulValInd;//多值解指示
    private String HigTypeInd;//高程类型指示

    private String dwrStr;//定位信息字符串
    private boolean Ifvaild = false;

    private Context DwContext;

    public DWRMsg() {
    }

    /**
     * 构造函数
     * 直接解析定位信息
     *
     * @param parambytes
     *         定位信息字节串
     */
    public DWRMsg(byte[] parambytes) {
        //DwContext = mcontext;
        dwrStr = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(dwrStr);
        if (Ifvaild) {
            String[] items = dwrStr.split(",");
            //Log.v("BDDWRMessage", ""+items.length+" "+items[3].length()+" "+items[4].length()+" "+items[6].length());
            if (items.length >= 15 && items[3].length() >= 9 && items[4].length() >= 7 && items[6].length() >= 8)//避免两个,,直接的内容为空时，指针溢出
            {
                //Log.v("BDDWRMessage", "非整数");
                this.setDWType(items[1]);
                this.setIDNum(items[2]);
                this.SetTime(items[3].substring(0, 2), items[3].substring(2, 4), items[3].substring(4, 6), items[3].substring(7, 9));
                this.setLatitude(items[4].substring(0, 2), items[4].substring(2, 4), items[4].substring(5), items[5]);
                this.setLongitude(items[6].substring(0, 3), items[6].substring(3, 5), items[6].substring(6), items[7]);
                this.setGeoHig(items[8], items[9]);
                this.setHigAno(items[10], items[11]);
                this.setPrecisionInd(items[12]);
                this.setEmergencyDW(items[13]);
                this.setMulValInd(items[14]);
                this.setHigTypeInd(items[15]);
            } else {
                //Log.v("BDDWRMessage", "整数");
                //当时间、经度或纬度中其中有一个以上为整数时
            }
        } else {
            initData();
        }


    }

    /**
     * 初始化数据
     */
    private void initData() {
        this.setDWType("1");
        this.setIDNum("XXXXXXX");
        this.SetTime("00", "00", "00", "00");
        this.setLatitude("00", "00", "00", "X");
        this.setLongitude("000", "00", "00", "X");
        this.setGeoHig("XXX", "X");
        this.setHigAno("XXX", "X");
        this.setPrecisionInd("0");
        this.setEmergencyDW("V");
        this.setMulValInd("V");
        this.setHigTypeInd("L");
    }

    /**
     * 检测命令是否有效
     *
     * @return 有效标志位
     */
    public boolean getVaild() {
        return Ifvaild;
    }

    /**
     * 获取定位类型
     */
    public String getDWType() {
        return DWType;
    }

    /**
     * 设置定位类型
     *
     * @param paramStr
     *         定位类型字符串
     */
    public void setDWType(String paramStr) {
        switch (paramStr) {
            case "1":
                //DWType = DwContext.getResources().getString(R.string.dwtype_this_device);
                DWType = "本设备位置信息";
                break;
            case "2":
                //DWType = DwContext.getResources().getString(R.string.dwtype_sub_device);
                DWType = "下属用户位置信息";
                break;
            case "3":
                //DWType = DwContext.getResources().getString(R.string.dwtype_report_device);
                DWType = "位置报告信息";
                break;
            default:
                break;
        }
    }

    /**
     * 获取定位信息接收IC卡号
     *
     * @return IC卡号字符串
     */
    public String getIDNum() {
        return IDNum;
    }

    /**
     * 设置定位信息接收IC卡号
     *
     * @param paramStr
     *         定位信息接收IC卡号字符串
     */
    public void setIDNum(String paramStr) {
        IDNum = paramStr;
    }


    /**
     * 获取定位精度指示
     *
     * @return 定位精度指示字符串
     */
    public String getPrecisionInd() {
        return PrecisionInd;
    }

    /**
     * 设置定位精度
     *
     * @param mprecisionInd
     *         定位精度字符串（“0”或“1”）
     */
    public void setPrecisionInd(String mprecisionInd) {
        switch (mprecisionInd) {
            case "0":
                //PrecisionInd = DwContext.getResources().getString(R.string.dwpre_first);
                PrecisionInd = "一档 20m";
                break;
            case "1":
                //PrecisionInd = DwContext.getResources().getString(R.string.dwpre_second);
                PrecisionInd = "二档 100m";
                break;
            default:
                break;
        }
    }

    /**
     * 获取是否紧急定位指示
     *
     * @return 定位类型字符串
     */
    public String getEmergencyDW() {
        return EmergencyDW;
    }

    /**
     * 设置是否紧急定位指示
     *
     * @param memergencyDW
     *         定位类型字符串（“A”或“V”）
     */
    public void setEmergencyDW(String memergencyDW) {
        switch (memergencyDW) {
            case "A":
                //EmergencyDW = DwContext.getResources().getString(R.string.dw_emergency);
                EmergencyDW = "紧急定位";
                break;
            case "V":
                //EmergencyDW = DwContext.getResources().getString(R.string.dw_unemergency);
                EmergencyDW = "非紧急定位";
                break;
            default:
                break;
        }
    }

    /**
     * 获取多解值指示
     *
     * @return 多解值指示字符串
     */
    public String getMulValInd() {
        return MulValInd;
    }

    /**
     * 设置多解值指示
     *
     * @param mmulValInd
     *         多解值指示字符串（“A”或“V”）
     */
    public void setMulValInd(String mmulValInd) {
        switch (mmulValInd) {
            case "A":
                //MulValInd = DwContext.getResources().getString(R.string.dw_multi_result);
                MulValInd = "多值解";
                break;
            case "V":
                //MulValInd = DwContext.getResources().getString(R.string.dw_unmulti_result);
                MulValInd = "非多值解";
                break;
            default:
                break;
        }
    }

    /**
     * 获取高程类型指示
     *
     * @return 返回高程类型指示字符串
     */
    public String getHigTypeInd() {
        return HigTypeInd;
    }

    /**
     * 设置高程类型指示
     *
     * @param mhigTypeInd
     *         高程指示字符串（“H”或“L”）
     */
    public void setHigTypeInd(String mhigTypeInd) {
        switch (mhigTypeInd.substring(0, 1)) {
            case "H":
                //HigTypeInd = DwContext.getResources().getString(R.string.dw_hightype_high);
                HigTypeInd = "高空";
                break;
            case "L":
                //HigTypeInd = DwContext.getResources().getString(R.string.dw_hightype_normol);
                HigTypeInd = "普通";
                break;
            default:
                break;
        }
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
    public void setLatitude(String mlath, String mlatm, String mlatmm, String mlatdir) {
        this.LatiH = mlath;
        this.LatiM = mlatm;
        this.LatiS = ("" + BDMethod.castStringToInt(mlatmm) * 60).substring(0, 2);
        this.LatiOrin = mlatdir;
        this.Latitude = Integer.parseInt(mlath) + Double.parseDouble(mlatm) / 60 + Double.parseDouble("0." + mlatmm) / 60;//119.12345648
        this.LatiDeg = mlatdir + " " + LatiH + "°" + LatiM + "′" + LatiS + "''"; // N 114°18′1〃
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
    public void setLongitude(String lngh, String lngm, String lngmm, String lngdir) {
        this.LongiH = lngh;
        this.LongiM = lngm;
        this.LongiS = ("" + BDMethod.castStringToInt(lngmm) * 60).substring(0, 2);
        this.LongOrin = lngdir;
        this.Longitude = Double.parseDouble(lngh) + Double.parseDouble(lngm) / 60 + Double.parseDouble("0." + lngmm) / 60;//26.12345648
        this.LongiDeg = lngdir + " " + LongiH + "°" + LongiM + "′" + LongiS + "''";   // E 23°18′1〃
    }

    /**
     * 获取海拔高度
     *
     * @return 高度
     */
    public String getGeoHig() {
        return GeoHig;
    }

    /**
     * 获取高度单位
     *
     * @return 高度单位
     */
    public String getGeoHigUnit() {
        return GeoHigUnit;
    }

    /**
     * 设置海拔高度
     *
     * @param mgeoHig
     *         高度
     * @param mgeoHigUnit
     *         高度单位
     */
    public void setGeoHig(String mgeoHig, String mgeoHigUnit) {
        GeoHig = mgeoHig + " " + mgeoHigUnit;
    }

    /**
     * 获取异常高程
     *
     * @return 异常高程
     */
    public String getHigAno() {
        return HigAno;
    }

    /**
     * 获取异常高程单位
     *
     * @return 异常高程单位
     */
    public String getHigAnoUnit() {
        return HigAnoUnit;
    }

    /**
     * 设置异常高程
     *
     * @param mhigAno
     *         异常高程
     * @param mhigAnoUnit
     *         异常高程单位
     */
    public void setHigAno(String mhigAno, String mhigAnoUnit) {
        HigAno = mhigAno + " " + mhigAnoUnit;
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
        this.UTCTime = h + ":" + m + ":" + s;
        this.BJTime = (BDMethod.castUTCtimeToBeijingTime("0000-00-0000 " + UTCTime)).substring(11);
    }

    /**
     * 获取北京时间
     */
    public String getBJTime() {
        return BJTime;
    }

    /**
     * 获取UTC时间
     */
    public String getUTCTime() {
        return UTCTime;
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
     * 获取纬度方向
     */
    public String getLatiOrin() {
        return LatiOrin;
    }

    /**
     * 获取经度方向
     */
    public String getLongOrin() {
        return LongOrin;
    }

    /**
     * 获取DWR协议原始信息
     *
     * @return DWR原始信息
     */
    public String getDwrStr() {
        return dwrStr.substring(0, dwrStr.indexOf("*") + 3);
    }

    /**
     * 设置DWR协议原始信息
     *
     * @param dwrStr DWR原始信息
     */
    /*private void setDwrStr(String dwrStr) {
        try {
            this.dwrStr = dwrStr.substring(0,dwrStr.indexOf("*")+2);
        } catch (Exception e) {
            Log.v("FDBDErrorLog","dwrStr设置原始数据出错");
        }
    }*/
}
