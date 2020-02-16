package com.bddomainuni.models.entity.protocalBDHZ;

import com.bddomainuni.repository.tools.BDMethod;

public class BDQZX {
    private BDQZX bdqzx;

    private String zzmStatus;
    private String data;
    private boolean Ifvaild = false;

    public final static String STATE_ZZM_SUCCESS = "0"; // 成功进入/退出极限追踪
    public final static String STATE_ZZM_NO_CENTERIC = "1"; // 无上报中心号码
    public final static String STATE_ZZM_OPENED = "2"; // 系统已工作在极限追踪模式
    public final static String STATE_ZZM_BOX_NO_IC = "3"; // 未读取到北斗卡，极限追踪模式无法工作



    public BDQZX() {
        bdqzx = this;
    }

    public BDQZX(byte[] parambytes) {
        data = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(data);
        if (Ifvaild) {
            String[] items = data.split(",");
            if (items.length > 2) {
                setZzmStatus(items[1]);
            } else {
                setZzmStatus("3");
            }
        }
    }

    public String getZzmStatus() {
        return zzmStatus;
    }

    private void setZzmStatus(String zzmStatus) {
        this.zzmStatus = zzmStatus;
    }

    public boolean getVaild(){
        return Ifvaild;
    }
}
