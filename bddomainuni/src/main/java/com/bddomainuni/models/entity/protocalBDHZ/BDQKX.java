package com.bddomainuni.models.entity.protocalBDHZ;

import com.bddomainuni.repository.tools.BDMethod;

public class BDQKX {
    private BDQKX bdqkx;

    private String okStatus;
    private String data;
    private boolean Ifvaild = false;

    public final static String STATE_OK_SUCCESS = "0"; // 成功进入/退出OK
    public final static String STATE_OK_NO_CENTERIC = "1"; // 无上报中心号码
    public final static String STATE_OK_OPENED = "2"; // 系统已工作在OK模式
    public final static String STATE_OK_BOX_NO_IC = "3"; // 未读取到北斗卡，OK模式无法工作



    public BDQKX() {
        bdqkx = this;
    }

    public BDQKX(byte[] parambytes) {
        data = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(data);
        if (Ifvaild) {
            String[] items = data.split(",");
            if (items.length > 2) {
                setOkStatus(items[1]);
            } else {
                setOkStatus("3");
            }
        }
    }

    public String getOkStatus() {
        return okStatus;
    }

    private void setOkStatus(String okStatus) {
        this.okStatus = okStatus;
    }

    public boolean getVaild(){
        return Ifvaild;
    }
}
