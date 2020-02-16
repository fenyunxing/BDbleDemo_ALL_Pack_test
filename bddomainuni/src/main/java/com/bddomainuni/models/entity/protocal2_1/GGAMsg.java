package com.bddomainuni.models.entity.protocal2_1;


import com.bddomainuni.models.CheckImpl;
import com.bddomainuni.repository.tools.BDMethod;

import org.apache.commons.lang.StringUtils;

/**
 * 2.1协议二代模块定位GGA信息
 *
 * Created by admin on 2016/10/20.
 * Modified by admin on 2018/6/6
 */
public class GGAMsg implements CheckImpl {
    //时间
    private String thh;
    private String tmm;
    private String tss;
    private String tsss;
    private String utctime;
    //private String bjtime;//北京时间字符串形式，如12时30分15秒
    //纬度
    private String lath;
    private String latm;
    private String latmm;
    private String latdir;
    private double Latitude;// N 119.0145668
    private String LatiDeg;// N 14°18′1〃
    //经度
    private String lngh;//longitude
    private String lngm;
    private String lngmm;
    private String lngdir;
    private double Longitude;
    private String LongiDeg;// E 14°18′1〃
    //定位状态
    private boolean status;//语句标示为GN  状态指示：0定位不可用 1定位有效
    //卫星数
    private String sateliteNums = "0";//
    //HDOP
    private String hdop;
    //天线
    private String antennaheight = "-";
    private String antennaheightunit = "米";//
    //高程差
    private String heighterror = "-";
    private String heighterrorunit = "米";
    //差分数据龄期
    private String agedifdata;
    //差分站台ID
    private String iddifstation;

    private String gpsdata;//定位信息字符串
    private boolean Ifvaild = false;

    public GGAMsg() {
    }

    /**
     * 构造函数
     * 直接解析GGA字节串
     *
     * @param parambytes
     *         GGA字节串
     */
    public GGAMsg(byte[] parambytes) {
        gpsdata = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(gpsdata);
        if (Ifvaild) {
            String[] items = gpsdata.split(",");
            if (items.length >= 14 && items[1].length() >= 9 && items[2].length() >= 7 && items[4].length() >= 8)//避免两个,,直接的内容为空时，指针溢出
            {
                this.setTime(items[1].substring(0, 2), items[1].substring(2, 4), items[1].substring(4, 6), items[1].substring(7, 9));
                this.setLat(items[2].substring(0, 2), items[2].substring(2, 4), items[2].substring(5, 7), items[3]);
                this.setLng(items[4].substring(0, 3), items[4].substring(3, 5), items[4].substring(6, 8), items[5]);
                this.setStatus(items[6]);
                this.setSatelitesNum(items[7]);
                this.setHDOP(items[8]);
                this.setAntanna(items[9], items[10]);
                this.setHeighterror(items[11], items[12]);
                this.setAgeDifData(items[13]);
                this.setIDDifStation(items[14]);
            } else {
                initGga();
            }
        } else {
            initGga();
        }


    }

    /**
     * 设置时分秒信息
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
    public void setTime(String h, String m, String s, String ss) {
        this.thh = h;
        this.tmm = m;
        this.tss = s;
        this.tsss = ss;
        this.utctime = h + "时" + m + "分" + s + "秒";
        //this.bjtime = "北京时间：" + (Integer.parseInt(h) + 8) + "时" + m + "分" + s + "秒";
    }

    /**
     * 获取UTC时间
     *
     * @return 如：15时16分10秒
     */
    public String getUtcTime() {
        return this.utctime;
    }

    /*public String GetBeiJingTime() {
        return this.bjtime;
    }*/

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
    public void setLat(String mlath, String mlatm, String mlatmm, String mlatdir) {
        this.lath = mlath;
        this.latm = mlatm;
        this.latmm = StringUtils.leftPad("" + BDMethod.castStringToInt(mlatmm) * 60, 2);
        this.latdir = mlatdir;
        this.Latitude = Integer.parseInt(mlath) + Double.parseDouble(mlatm) / 60 + Double.parseDouble("0." + mlatmm) / 60;// 119.12345648
        this.LatiDeg = mlatdir + lath + "°" + latm + "′" + latmm + "″"; // N
        // 114°18′1〃
    }

    /**
     * 获取纬度（度形式）
     *
     * @return 如：26.12453°
     */
    public double getLatitude() {
        return this.Latitude;
    }

    /**
     * 获取纬度（度分秒形式）
     *
     * @return 如：26°13′31″
     */
    public String getLatiDeg() {
        return this.LatiDeg;
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
    public void setLng(String lngh, String lngm, String lngmm,
                       String lngdir) {
        this.lngh = lngh;
        this.lngm = lngm;
        this.lngmm = StringUtils.leftPad("" + BDMethod.castStringToInt(lngmm) * 60, 2);
        this.lngdir = lngdir;
        this.Longitude = Double.parseDouble(lngh) + Double.parseDouble(lngm) / 60 + Double.parseDouble("0." + lngmm) / 60;// 26.12345648
        this.LongiDeg = lngdir + lngh + "°" + lngm + "′" + lngmm + "″"; // E
        // 23°18′1〃
    }

    /**
     * 获取经度（度形式）
     *
     * @return 如：119.15546°
     */
    public double getLongitude() {
        return this.Longitude;
    }

    /**
     * 获取经度（度分秒形式）
     *
     * @return 如：119°15′42″
     */
    public String getLongiDeg() {
        return this.LongiDeg;
    }

    /**
     * 设置定位状态
     *
     * @param status
     *         定位状态 "0"定位失败   "1"定位成功
     */
    public void setStatus(String status) {
        if (status.equals("0")) {
            this.status = false;
        } else {
            this.status = true;
        }
    }

    /**
     * 获取定位状态
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * 设置卫星数量
     *
     * @param num
     *         卫星数量
     */
    public void setSatelitesNum(String num) {
        this.sateliteNums = num;
    }

    /**
     * 获取卫星数量
     *
     * @return 卫星数量（字符串形式）
     */
    public String getSatelitesNum() {
        return this.sateliteNums;
    }

    /**
     * 设置水平分量精度因子
     *
     * @param hdop
     *         水平分量精度因子
     */
    public void setHDOP(String hdop) {
        this.hdop = hdop;
    }

    /**
     * 获取水平分量精度因子
     *
     * @return 水平分量精度因子
     */
    public String getHDOP() {
        return this.hdop;
    }

    /**
     * 设置天线大地高
     *
     * @param height
     *         高度
     * @param unit
     *         高度单位
     */
    public void setAntanna(String height, String unit) {
        if (!("米".equals(unit))) {
            this.antennaheightunit = unit;
        }
        if (height.equals(null)) {
            this.antennaheight = "-";
        } else {
            this.antennaheight = height;
        }
    }

    /**
     * 获取天线高单位
     *
     * @return 天线高单位
     */
    public String getAntennaheightunit() {
        return antennaheightunit;
    }

    /**
     * 获取天线高
     *
     * @return 天线高
     */
    public String getAntannaHeight() {
        return this.antennaheight;
    }

    /**
     * 设置高程异常
     *
     * @param error
     *         高程异常
     * @param unit
     *         高程异常单位
     */
    public void setHeighterror(String error, String unit) {
        if (!("米".equals(unit))) {
            this.heighterrorunit = unit;
        }
        this.heighterror = error + this.heighterrorunit;

    }

    /**
     * 获取高程异常
     *
     * @return 高程异常值
     */
    public String getHeighterror() {
        return this.heighterror;
    }

    /**
     * 设置差分数据
     *
     * @param age
     *         差分数据
     */
    public void setAgeDifData(String age) {
        this.agedifdata = age;
    }

    /**
     * 获取差分数据
     */
    public String getAgeDifData() {
        return this.agedifdata;
    }

    /**
     * 设置差分站台ID号
     *
     * @param id
     *         差分站台ID号
     */
    public void setIDDifStation(String id) {
        this.iddifstation = id;
    }

    /**
     * 获取差分站台ID号
     *
     * @return 差分站台ID号
     */
    public String getIDDifStation() {
        return this.iddifstation;
    }

    /**
     * 获取报文校验标志
     */
    @Override
    public boolean getVaild() {
        return Ifvaild;
    }

    /**
     * 初始化GGA信息
     */
    private void initGga() {
        //Log.v("FDBDTestLog","GGAMsg 收到（4）");
        this.setTime("00", "00", "00", "00");
        this.setLat("00", "00", "00", "X");
        this.setLng("000", "00", "00", "X");
        this.setStatus("0");
        this.setSatelitesNum("0");
        this.setHDOP("0");
        this.setAntanna("-", "m");
        this.setHeighterror("0", "m");
        this.setAgeDifData("0");
        this.setIDDifStation("0");
    }
}
