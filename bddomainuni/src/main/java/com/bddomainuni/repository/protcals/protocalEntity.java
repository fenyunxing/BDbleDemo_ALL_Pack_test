package com.bddomainuni.repository.protcals;

import com.bddomainuni.repository.tools.BDMethod;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class protocalEntity implements Serializable{

    public final static String TYPE_QUERY = "0"; //查询
    public final static String TYPE_SET = "1";  //设置

    public final static String MODE_RDRN = "0"; //正常模式
    public final static String MODE_RD = "1"; //省电模式
    public final static String MODE_RN = "2"; //定位模式

    public final static String TYPE_PWD_SET = "1"; //密码设置
    public final static String TYPE_PWD_LOGIN = "2"; //登录

    public final static String STATE_SUCCESS = "1"; //成功
    public final static String STATE_FAILED = "2"; //失败


    /**
     *  OK键设置和查询（CCOKS）
     *
     *  OKS 与 OKX 共用
     */
    public static class CCOKSobj implements Serializable{

        private CCOKSobj ccoksobj;

        private String type = TYPE_QUERY;
        private String centerCrd = "";
        private String msg = "";

        private String data;
        private boolean Ifvaild = false;

        public CCOKSobj() {
            this.ccoksobj = this;
        }

        public CCOKSobj(byte[] parambytes) {
            try {
                data = new String(parambytes, "gb18030");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Ifvaild = BDMethod.CheckCKS(data);
            if (Ifvaild) {
                String[] items = data.split(",");
                if (items.length > 2) {
                    setCenterCrd(items[1]);
                    setMsg(items[2]);
//                    Log.v("FDBDTestLog","items[1] = "+items[1]);
//                    Log.v("FDBDTestLog","items[2] = "+items[2]);
                } else {
                    setCenterCrd("");
                    setMsg("");
                }
            }
        }

        public boolean getVaild(){
            return Ifvaild;
        }

        public String getType() {
            return type;
        }

        public CCOKSobj setType(String type) {
            this.type = type;
            return this.ccoksobj;
        }

        public String getCenterCrd() {
            return centerCrd;
        }

        public CCOKSobj setCenterCrd(String centerCrd) {
            this.centerCrd = centerCrd;
            return this.ccoksobj;
        }

        public String getMsg() {
            return msg;
        }

        public CCOKSobj setMsg(String msg) {
            this.msg = msg;
            return this.ccoksobj;
        }
    }


    /**
     *  极限追踪设置和查询（CCZZM）
     *
     *  ZZM 与 ZDX 共用
     */
    public static class CCZZMobj implements Serializable {
        private CCZZMobj cczzmobj;

        private String type;
        private String centerCrd;
        private String freq;
        private String mode;

        private String data;
        private boolean Ifvaild = false;

        public CCZZMobj() {
            cczzmobj = this;
        }

        public CCZZMobj(byte[] parambytes) {
            data = new String(parambytes);
            Ifvaild = BDMethod.CheckCKS(data);
            if (Ifvaild) {
                String[] items = data.split(",");
                if (items.length > 4) {
                    setCenterCrd(items[1]);
                    setFreq(items[2]);
                    setMode(items[3]);
                } else {
                    setCenterCrd("");
                    setFreq("");
                    setMode(MODE_RDRN);
                }
            }
        }

        public boolean getVaild(){
            return Ifvaild;
        }

        public String getType() {
            return type;
        }

        public CCZZMobj setType(String type) {
            this.type = type;
            return this.cczzmobj;
        }

        public String getCenterCrd() {
            return centerCrd;
        }

        public CCZZMobj setCenterCrd(String centerCrd) {
            this.centerCrd = centerCrd;
            return this.cczzmobj;
        }

        public String getFreq() {
            return freq;
        }

        public CCZZMobj setFreq(String freq) {
            this.freq = freq;
            return this.cczzmobj;
        }

        public String getMode() {
            return mode;
        }

        public CCZZMobj setMode(String mode) {
            this.mode = mode;
            return this.cczzmobj;
        }
    }

    /**
     *  SOS设置和查询（CCSHM）
     *
     *  SHM 与 HMX 共用
     */
    public static class CCSHMobj implements Serializable{
        private CCSHMobj ccshmobj;

        private String type;
        private String centerCrd;
        private String freq;
        private String msg;

        private String data;
        private boolean Ifvaild = false;

        public CCSHMobj() {
            this.ccshmobj = this;
        }

        public CCSHMobj(byte[] parambytes) {
            try {
                data = new String(parambytes, "gb18030");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Ifvaild = BDMethod.CheckCKS(data);
            if (Ifvaild) {
                String[] items = data.split(",");
                if (items.length > 3) {
                    setCenterCrd(items[1]);
                    setFreq(items[2]);
                    setMsg(items[3]);
                } else {
                    setCenterCrd("");
                    setFreq("");
                    setMsg("");
                }
            }
        }

        public boolean getVaild(){
            return Ifvaild;
        }

        public String getType() {
            return type;
        }

        public CCSHMobj setType(String type) {
            this.type = type;
            return ccshmobj;
        }

        public String getCenterCrd() {
            return centerCrd;
        }

        public CCSHMobj setCenterCrd(String centerCrd) {
            this.centerCrd = centerCrd;
            return ccshmobj;
        }

        public String getFreq() {
            return freq;
        }

        public CCSHMobj setFreq(String freq) {
            this.freq = freq;
            return ccshmobj;
        }

        public String getMsg() {
            return msg;
        }

        public CCSHMobj setMsg(String msg) {
            this.msg = msg;
            return ccshmobj;
        }
    }

    public static class BDPWXobj implements Serializable{
        private BDPWXobj bdpwxobj;

        private String type;
        private String status;

        private String data;
        private boolean Ifvaild = false;

        public BDPWXobj() {
            this.bdpwxobj = this;
        }

        public BDPWXobj(byte[] parambytes) {
            data = new String(parambytes);
            Ifvaild = BDMethod.CheckCKS(data);
            if (Ifvaild) {
                String[] items = data.split(",");
                if (items.length > 3) {
                    setType(items[1]);
                    setStatus(items[2]);
                } else {
                    setType("");
                    setStatus("");
                }
            }
        }

        public boolean getVaild(){
            return Ifvaild;
        }

        public String getType() {
            return type;
        }

        public BDPWXobj setType(String type) {
            this.type = type;
            return this.bdpwxobj;
        }

        public String getStatus() {
            return status;
        }

        public BDPWXobj setStatus(String status) {
            this.status = status;
            return this.bdpwxobj;
        }

    }

    public static class BDZDXobj implements Serializable{
        private BDZDXobj bdzdxobj;

        private String bdCrd;
        private String battery;
        private String[] power = new String[10];
        private String freq;
        private String level;
        private String maxBit;

        private String data;
        private boolean Ifvaild = false;

        public BDZDXobj() {
            this.bdzdxobj = this;
        }

        public BDZDXobj(byte[] parambytes) {
            data = new String(parambytes);
            Ifvaild = BDMethod.CheckCKS(data);
            if (Ifvaild) {
                String[] items = data.split(",");
                if (items.length > 14) {
                    setBdCrd(items[1]);
                    setBattery(items[2]);
                    setPower(items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10],items[11],items[12]);
                    setFreq(items[13]);
                    setLevel(items[14]);
                    setMaxBit(items[15]);
                } else {
                    setBdCrd("");
                    setBattery("");
                    setPower("","","","","","","","","","");
                    setFreq("");
                    setLevel("");
                    setMaxBit("");
                }
            }
        }

        public boolean getVaild(){
            return Ifvaild;
        }

        public String getBdCrd() {
            return bdCrd;
        }

        public BDZDXobj setBdCrd(String bdCrd) {
            this.bdCrd = bdCrd;
            return this.bdzdxobj;
        }

        public String getBattery() {
            return battery;
        }

        public BDZDXobj setBattery(String battery) {
            this.battery = battery;
            return this.bdzdxobj;
        }

        public String[] getPower() {
            return power;
        }

        public BDZDXobj setPower(String power1,
                                 String power2,
                                 String power3,
                                 String power4,
                                 String power5,
                                 String power6,
                                 String power7,
                                 String power8,
                                 String power9,
                                 String power10) {
            this.power[0] = power1;
            this.power[1] = power2;
            this.power[2] = power3;
            this.power[3] = power4;
            this.power[4] = power5;
            this.power[5] = power6;
            this.power[6] = power7;
            this.power[7] = power8;
            this.power[8] = power9;
            this.power[9] = power10;

            return this.bdzdxobj;
        }

        public String getFreq() {
            return freq;
        }

        public BDZDXobj setFreq(String freq) {
            this.freq = freq;
            return this.bdzdxobj;
        }

        public String getLevel() {
            return level;
        }

        public BDZDXobj setLevel(String level) {
            this.level = level;
            return this.bdzdxobj;
        }

        public String getMaxBit() {
            return maxBit;
        }

        public BDZDXobj setMaxBit(String maxBit) {
            this.maxBit = maxBit;
            return this.bdzdxobj;
        }
    }

}
