package com.bddomainuni.models.entity.protocalBDHZ;

import com.bddomainuni.repository.tools.BDMethod;

public class BDQDX {
    private BDQDX bdqdx;

    private String sosStatus;
    private String data;
    private boolean Ifvaild = false;

    public final static String STATE_SOS_SUCCESS = "0"; // 成功进入/退出SOS
    public final static String STATE_SOS_NO_CENTERIC = "1"; // 无上报中心号码
    public final static String STATE_SOS_OPENED = "2"; // 系统已工作在SOS模式
    public final static String STATE_SOS_BOX_NO_IC = "3"; // 未读取到北斗卡，SOS模式无法工作


    public BDQDX() {
        bdqdx = this;
    }

    public BDQDX(byte[] parambytes) {
        data = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(data);
        if (Ifvaild) {
            String[] items = data.split(",");
            if (items.length > 2) {
                setSosStatus(items[1]);
            } else {
                setSosStatus("3");
            }
        }
    }

    public String getSosStatus() {
        return sosStatus;
    }

    private void setSosStatus(String sosStatus) {
        this.sosStatus = sosStatus;
    }

    public boolean getVaild(){
        return Ifvaild;
    }
}
