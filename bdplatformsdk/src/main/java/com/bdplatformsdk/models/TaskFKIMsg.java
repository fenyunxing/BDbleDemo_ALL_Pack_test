package com.bdplatformsdk.models;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;

/**
 * (平台协议)平台任务单上报反馈信息类
 *
 * Created by admin on 2017/2/13.
 */
public class TaskFKIMsg {

    private String TaskFkiStr;
    private String User;
    private String taskNum;
    private String taskState;

    public TaskFKIMsg() {
    }

    /**
     * 构造函数
     * 直接解析任务单反馈信息
     *
     * @param paramTXR
     *         短报文
     */
    public TaskFKIMsg(TXRMsg paramTXR) {
        if (paramTXR.getMsgHexStr().length() > 30) {
            TaskFkiStr = paramTXR.getMsgHexStr().substring(6);
            //Log.v("FDBDChatTest","TASKstr ===> "+TaskFkiStr);
            setUser(TaskFkiStr.substring(1, 12));
            setTaskNum(TaskFkiStr.substring(12, 24));
            setTaskState(TaskFkiStr.substring(24));
        } else {
            // TODO: 2017/2/9  当得到任务单回执报文过短时的处理
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
     * 获取任务状态
     */
    public String getTaskState() {
        return taskState;
    }

    /**
     * 设置任务状态
     */
    private void setTaskState(String taskState) {
        this.taskState = taskState;
    }
}
