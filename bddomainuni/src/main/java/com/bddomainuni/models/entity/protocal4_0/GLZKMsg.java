package com.bddomainuni.models.entity.protocal4_0;


import com.bddomainuni.repository.tools.BDMethod;

/**
 * 功率检测信息类
 *
 * Created by admin on 2017/5/24.
 */

public class GLZKMsg {
    private boolean vaild = false;
    private String ic;
    private String pw1;
    private String pw2;
    private String pw3;
    private String pw4;
    private String pw5;
    private String pw6;
    private String glzkHexStr;
    private byte[] glzkByte;

    public GLZKMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            glzkHexStr = BDMethod.castBytesToHexString(parambytes);
            glzkByte = parambytes;
            ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});
            pw1 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[10]});
            pw2 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[11]});
            pw3 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[12]});
            pw4 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[13]});
            pw5 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[14]});
            pw6 = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[15]});
            //Log.v("FDBDTestLog","ic原 ==> "+BDMethod.castBytesToHexString(new byte[]{(byte)0,parambytes[7],parambytes[8],parambytes[9]}));
            //Log.v("FDBDTestLog","ic ==> "+ic);
            //Log.v("FDBDTestLog","功率 ==> "+pw1+"-"+pw2+"-"+pw3+"-"+pw4+"-"+pw5+"-"+pw6);
        }
    }

    public boolean isVaild() {
        return vaild;
    }

    public String getIc() {
        return ic;
    }

    public String getPw1() {
        return pw1;
    }

    public String getPw2() {
        return pw2;
    }

    public String getPw3() {
        return pw3;
    }

    public String getPw4() {
        return pw4;
    }

    public String getPw5() {
        return pw5;
    }

    public String getPw6() {
        return pw6;
    }


    public String getGlzkHexStr() {
        return glzkHexStr;
    }

    public byte[] getGlzkByte() {
        return glzkByte;
    }
}
