package com.bdbledemo.LitePal;

import org.litepal.crud.LitePalSupport;

public class MySendTable extends LitePalSupport {
    private String sendcontent;

    public String getSendcontent() {
        return sendcontent;
    }

    public void setSendcontent(String sendcontent) {
        this.sendcontent = sendcontent;
    }
}
