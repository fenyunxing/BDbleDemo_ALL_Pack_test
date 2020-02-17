package com.bdbledemo.LitePal;

import org.litepal.crud.LitePalSupport;

public class ProtocalTable extends LitePalSupport {
    private String protocalName;
    private String protocalContent;

    public String getProtocalName() {
        return protocalName;
    }

    public void setProtocalName(String protocalName) {
        this.protocalName = protocalName;
    }

    public String getProtocalContent() {
        return protocalContent;
    }

    public void setProtocalContent(String protocalContent) {
        this.protocalContent = protocalContent;
    }
}
