package com.bddomain40.models.entity.protocal4_0;

import com.bddomain40.repository.tools.BDMethod;

/**
 * IC信息类
 *
 * Created by admin on 2017/5/24.
 */

public class ICXXMsg {
    private boolean vaild = false;
    private String ic;
    private String frameNum;//帧号
    private String broadcastId;//通播ID
    private String userCharacter;//用户特征
    private String serviceFreq;//服务频度
    private String level;//通信等级
    private String encryptFlag;//加密标志
    private String subUserNum;//下属用户总数
    private String icxxHexStr;
    private byte[] icxxByte;

    public ICXXMsg(byte[] parambytes) {
        vaild = BDMethod.CheckCKS_40(parambytes);
        if (vaild) {
            this.icxxHexStr = BDMethod.castBytesToHexString(parambytes);
            this.icxxByte = parambytes;
            this.ic = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[7], parambytes[8], parambytes[9]});
            this.frameNum = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[10]});
            this.broadcastId = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, parambytes[11], parambytes[12], parambytes[13]});
            this.userCharacter = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, parambytes[15], parambytes[16]});
            this.serviceFreq = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, parambytes[15], parambytes[16]});
            this.level = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[17]});
            this.encryptFlag = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, (byte) 0, parambytes[18]});
            this.subUserNum = "" + BDMethod.castBytesToIntByPos(new byte[]{(byte) 0, (byte) 0, parambytes[19], parambytes[20]});
        }
    }

    public boolean isVaild() {
        return vaild;
    }

    public String getIc() {
        return ic;
    }

    public String getFrameNum() {
        return frameNum;
    }

    public String getBroadcastId() {
        return broadcastId;
    }

    public String getUserCharacter() {
        return userCharacter;
    }

    public String getServiceFreq() {
        return serviceFreq;
    }

    public String getLevel() {
        return level;
    }

    public String getEncryptFlag() {
        if (encryptFlag.equals("0")) {
            return "保密用户";
        } else {
            return "非密用户";
        }
    }

    public String getSubUserNum() {
        return subUserNum;
    }

    public String getIcxxHexStr() {
        return icxxHexStr;
    }

    public byte[] getIcxxByte() {
        return icxxByte;
    }
}
