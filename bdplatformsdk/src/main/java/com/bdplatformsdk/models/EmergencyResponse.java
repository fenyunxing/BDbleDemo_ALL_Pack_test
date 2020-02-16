package com.bdplatformsdk.models;

import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * Created by lenovo on 2018/4/16.
 */

public class EmergencyResponse {
    private int emergencyState = 0;//紧急响应阶段
    private int emergencyType = 0;//紧急求救类型
    private String emergencyMsg = "";
    private String emergnStr = "";

    public EmergencyResponse(TXRMsg txr) {
        int len = txr.getMsgHexStr().length();
        if (len > 18) {
            emergnStr = txr.getMsgHexStr().substring(18);
            setEmergencyState(emergnStr.substring(0,2));
            setEmergencyMsg(emergnStr.substring(2));
        }
    }

    public int getEmergencyState() {
        return emergencyState;
    }

    private void setEmergencyState(String state) {

        byte[] stateByte = BDMethod.castHexStringToBytes(state);
        this.emergencyState = stateByte[0] >> 5;
        this.emergencyType = stateByte[0] & 0x1F;
    }

    public int getEmergencyType() {
        return emergencyType;
    }


    public String getEmergencyMsg() {
        return emergencyMsg;
    }

    private void setEmergencyMsg(String emergencyMsg) {
        this.emergencyMsg = BDMethod.castHexStringToHanziString(emergencyMsg);
    }
}
