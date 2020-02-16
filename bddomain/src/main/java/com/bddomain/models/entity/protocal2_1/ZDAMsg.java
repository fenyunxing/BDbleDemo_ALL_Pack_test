package com.bddomain.models.entity.protocal2_1;

import com.bddomain.repository.tools.BDMethod;

/**
 * Created by admin on 2017/4/8.
 */

public class ZDAMsg {
    private String mode;
    private String day;
    private String month;
    private String year;
    private String utcTime;
    private String beijingTime;//北京时间
    private int localZone;//本地时区
    private String minDiff;//本时区分钟差
    private String timeCorrection;//定时修正值时刻
    private String correctionValue;//修正值
    private String accuracy;//精度指示
    private String signalLockState;//信号锁定状态

    /**
     * 获取模式指示
     *
     * @return 模式 1：RD定位 2：RN定位
     */
    public String getMode() {
        return mode;
    }

    /**
     * 设置模式指示
     *
     * @param mode
     *         模式 1：RD定位 2：RN定位
     */
    public void setMode(int mode) {
        switch (mode) {
            case 1:
                this.mode = "RD定位";
                break;
            case 2:
                this.mode = "RN定位";
                break;
            default:
                this.mode = "无";
                break;
        }

    }

    /**
     * 获取北京时间
     *
     * @return 北京时间
     */
    public String getBeijingTime() {
        return beijingTime;
    }

    /**
     * 设置北京时间
     *
     * @param beijingTime
     *         北京时间
     */
    public void setBeijingTime(String beijingTime) {
        this.beijingTime = beijingTime;
    }

    /**
     * 获取日
     *
     * @return 日
     */
    public String getDay() {
        return day;
    }

    /**
     * 设置日
     *
     * @param day
     *         日
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * 获取月
     *
     * @return 月
     */
    public String getMonth() {
        return month;
    }

    /**
     * 设置月
     *
     * @param month
     *         月
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 获取年
     *
     * @return 年
     */
    public String getYear() {
        return year;
    }

    /**
     * 设置年
     *
     * @param year
     *         年
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 获取UTC时间
     *
     * @return UTC时间
     */
    public String getUtcTime() {
        return utcTime;
    }

    /**
     * 设置UTC时间
     *
     * @param utcTime
     *         "yyyy-MM-dd HH:mm:ss"格式
     */
    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime.substring(0, 2) + ":" + utcTime.substring(2, 4) + ":" + utcTime.substring(4, 6);
        //Log.v("FDBDTestLog",year+"-"+month+"-"+day+" "+utcTime);
        setBeijingTime(BDMethod.castUTCtimeToBeijingTime(year + "-" + month + "-" + day + " " + getUtcTime()));
    }

    /**
     * 获取时区
     *
     * @return 时区
     */
    public int getLocalZone() {
        return localZone;
    }

    /**
     * 设置时区
     *
     * @param localZone
     *         时区
     */
    public void setLocalZone(String localZone) {
        this.localZone = BDMethod.castStringToInt(localZone);
    }

    /**
     * 获取区分钟差
     *
     * @return 区分钟差
     */
    public String getMinDiff() {
        return minDiff;
    }

    /**
     * 设置区分钟差
     *
     * @param minDiff
     *         区分钟差
     */
    public void setMinDiff(String minDiff) {
        this.minDiff = minDiff;
    }

    /**
     * 获取定时修正值
     *
     * @return 定时修正值
     */
    public String getTimeCorrection() {
        return timeCorrection;
    }

    /**
     * 设置定时修正值
     *
     * @param timeCorrection
     *         定时修正值
     */
    public void setTimeCorrection(String timeCorrection) {
        this.timeCorrection = timeCorrection;
    }

    /**
     * 获取修正值
     *
     * @return 修正值
     */
    public String getCorrectionValue() {
        return correctionValue;
    }

    /**
     * 设置修正值
     *
     * @param correctionValue
     *         修正值
     */
    public void setCorrectionValue(String correctionValue) {
        this.correctionValue = correctionValue;
    }

    /**
     * 获取精度指示
     *
     * @return 精度指示
     */
    public String getAccuracy() {
        return accuracy;
    }

    /**
     * 设置精度指示（0~3）
     *
     * @param accuracy
     *         精度 0：未检测 1：0～10ns 2：10～20ns 3：>20ns
     */
    public void setAccuracy(int accuracy) {
        switch (accuracy) {
            case 0:
                this.accuracy = "未检测";
                break;
            case 1:
                this.accuracy = "0～10ns";
                break;
            case 2:
                this.accuracy = "10～20ns";
                break;
            case 3:
                this.accuracy = ">20ns";
                break;
            default:
                this.accuracy = "未检测";
                break;
        }
    }

    /**
     * 获取卫星信号锁定状态
     */
    public String getSignalLockState() {
        return signalLockState;
    }

    /**
     * 设置卫星信号锁定状态（“N”或“Y”）
     *
     * @param signalLockState
     *         锁定状态 N：失锁  Y：锁定
     */
    public void setSignalLockState(String signalLockState) {
        switch (signalLockState) {
            case "Y":
                this.signalLockState = "锁定";
                break;
            case "N":
                this.signalLockState = "失锁";
                break;
            default:
                this.signalLockState = "失锁";
                break;
        }
    }
}
