package com.bddomain40.models.entity.protocal4_0;

import com.bddomain40.repository.tools.BDMethod;

/**
 * 反馈信息类
 *
 * Created by admin on 2017/5/24.
 */

public class FKXXMsg {
    private boolean vaild = false;
    private String ic;
    private String fkxxHexStr;
    private byte[] fkxxByte;
    private int fkFlag;
    private String fkContent;
    private byte[] b;
    public FKXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.fkxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.fkxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});
            this.fkFlag = BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[10]});
            switch (fkFlag) {
                case 0:
                     b = new byte[]{parambytes[11], parambytes[12], parambytes[13], parambytes[14]};
                    fkContent = "成功 指令:" + new String(b);
                    break;
                case 1:
                    b = new byte[]{parambytes[11], parambytes[12], parambytes[13], parambytes[14]};
                    fkContent = "失败 指令:" + new String(b);
                    break;
                case 2:
                    fkContent = "信号未锁定";
                    break;
                case 3:
                    fkContent = "电量不足，发射抑制";
                    break;
                case 4:
                    b = new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[11]};
                    fkContent = "发射频度未到 等待时间:"+ BDMethod.castBytesToIntByPos(b);
                    break;
                case 5:
                    fkContent = "加解密错误";
                    break;
                case 6:
                    b = new byte[]{parambytes[11], parambytes[12], parambytes[13], parambytes[14]};
                    fkContent = "CRC错误  指令:" + new String(b);
                    break;
                case 7:
                    fkContent = "系统抑制";
                    break;

            }
        }
    }

    public boolean isVaild() {
        return vaild;
    }

    /*public String getIc() {
        return ic;
    }*/

    public String getFkxxHexStr() {
        return fkxxHexStr;
    }

    public byte[] getFkxxByte() {
        return fkxxByte;
    }

    public int getFkFlag() {
        return fkFlag;
    }

    public String getFkContent() {
        return fkContent;
    }
}
