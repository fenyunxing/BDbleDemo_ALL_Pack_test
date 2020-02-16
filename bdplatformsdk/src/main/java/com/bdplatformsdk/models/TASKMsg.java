package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.repository.tools.BDMethod;

/**
 * (平台协议)平台任务单信息类
 *
 * Created by admin on 2017/2/13.
 */

public class TASKMsg {

    private String TASKstr;
    private String User;
    private String taskNum;
    private String taskMsg;

    public TASKMsg() {
    }

    /**
     * 构造函数
     * 直接解析任务单信息
     *
     * @param paramTXR
     *         短报文
     */
    public TASKMsg(TXRMsg paramTXR) {
        if (paramTXR.getMsgHexStr().length() > 25) {
            TASKstr = paramTXR.getMsgHexStr().substring(6);
            //Log.v("FDBDChatTest","TASKstr ===> "+TASKstr);
            setUser(TASKstr.substring(1, 12));
            setTaskNum(TASKstr.substring(12, 24));
            setTaskMsg(TASKstr.substring(24));
        } else {
            // TODO: 2017/2/9  当得到任务单报文过短时的处理
        }
        //Log.v("FDBDChatTest","User ===> "+User+" Num ===> "+taskNum+" content ===> "+taskMsg);
    }

    /**
     * 获取接收用户
     */
    public String getUser() {
        return User;
    }

    /**
     * 设置接收用户
     */
    private void setUser(String user) {
        User = user;
    }

    /**
     * 获取任务单号
     */
    public String getTaskNum() {
        return taskNum;
    }

    /**
     * 设置任务单号
     */
    private void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * 获取任务内容
     */
    public String getTaskMsg() {
        return taskMsg;
    }

    /**
     * 设置任务内容
     */
    private void setTaskMsg(String taskMsg) {
        this.taskMsg = BDMethod.castHexStringToHanziString(taskMsg);
    }
}
