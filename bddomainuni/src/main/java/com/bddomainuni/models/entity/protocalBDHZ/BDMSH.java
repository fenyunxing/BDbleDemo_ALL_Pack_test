package com.bddomainuni.models.entity.protocalBDHZ;

import com.bddomainuni.repository.tools.BDMethod;

public class BDMSH {
    private BDMSH bdmsh;

    private String workMode;
    private String data;
    private boolean Ifvaild = false;

    public BDMSH() {
        bdmsh = this;
    }

    public BDMSH(byte[] parambytes) {
        data = new String(parambytes);
        Ifvaild = BDMethod.CheckCKS(data);
        if (Ifvaild) {
            String[] items = data.split(",");
            if (items.length > 2) {
                setWorkMode(items[1]);
            } else {
                setWorkMode("1");
            }
        }
    }

    public String getWorkMode() {
        return workMode;
    }

    private void setWorkMode(String workMode) {
        this.workMode = workMode;
    }


    public boolean getVaild(){
        return Ifvaild;
    }
}
