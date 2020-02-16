package com.bddomain40.models.entity.protocal4_0;

import com.bddomain40.repository.tools.BDMethod;

/**
 * 版本信息类
 *
 * Created by admin on 2017/5/24.
 */

public class BBXXMsg {
    private boolean vaild = false;
    private String ic;
    private String bbxxHexStr;
    private byte[] bbxxByte;

    public BBXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.bbxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.bbxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});

        }
    }

    public boolean isVaild() {
        return vaild;
    }

    public String getBbxxHexStr() {
        return bbxxHexStr;
    }

    public byte[] getBbxxByte() {
        return bbxxByte;
    }

    /*public String getIc() {
        return ic;
    }*/
}
