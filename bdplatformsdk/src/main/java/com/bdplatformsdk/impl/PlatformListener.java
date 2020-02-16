package com.bdplatformsdk.impl;


import com.bdplatformsdk.models.AppBd2BdByPosMsg;
import com.bdplatformsdk.models.AppBd2BdMsg;
import com.bdplatformsdk.models.AppLoginFKIMsg;
import com.bdplatformsdk.models.AppNet2BdByPosMsg;
import com.bdplatformsdk.models.AppNet2BdMsg;
import com.bdplatformsdk.models.AppSosFkiMsg;
import com.bdplatformsdk.models.CardtoCardMsg;
import com.bdplatformsdk.models.EmergencyResponse;
import com.bdplatformsdk.models.LoginFKIMsg;
import com.bdplatformsdk.models.PhonetoCardMsg;
import com.bdplatformsdk.models.TASKMsg;
import com.bdplatformsdk.models.TaskFKIMsg;
import com.bdplatformsdk.models.UsertoUserMsg;

/**
 *  平台数据接收接口类
 * 可得到解析后的平台数据
 *
 * Created by admin on 2017/3/22.
 * Modified by admin on 2017/04/07
 */

public abstract interface PlatformListener {

    /**
     * 接收到北斗卡直接发送信息
     * @param paramCardtoCardMsg 设备至设备通信信息解析对象
     */
    public abstract void onCardToCard(CardtoCardMsg paramCardtoCardMsg);

    /**
     * 接收到平台用户发来的信息
     * @param paramUsertoUserMsg  用户至用户通信信息解析对象
     */
    public abstract void onUserToUser(UsertoUserMsg paramUsertoUserMsg);

    /**
     * 接收到手机发来的信息
     * @param paramPhonetoCardMsg  手机至用户通信信息解析对象
     */
    public abstract void onPhoneToCard(PhonetoCardMsg paramPhonetoCardMsg);

    /**
     * 接收平台派发任务单
     * @param paramTASKMsg  平台派发任务单信息解析对象
     */
    public abstract void onPlatTask(TASKMsg paramTASKMsg);

    /**
     * 接收平台回复登录状态
     * @param paramLoginFKIMsg  平台登入反馈信息解析对象
     */
    public abstract void onPlatFkiLogin(LoginFKIMsg paramLoginFKIMsg);

    /**
     * 接收平台收到任务上报内容的反馈信息
     * @param paramTaskFKIMsg  平台接收任务单确认反馈信息解析对象
     */
    public abstract void onPlatTaskConfirm(TaskFKIMsg paramTaskFKIMsg);

    /**
     * 接收平台紧急报警回复
     * @param emergencyResponse 紧急报警回复对象
     */
    public abstract void onPlatEmergnConfirm(EmergencyResponse emergencyResponse);


    /**
     * 收到登录反馈
     * @param appLoginFKIMsg
     */
    public abstract void onAppLoginFki(AppLoginFKIMsg appLoginFKIMsg);


    /**
     * 收到公网到北斗网数据
     * @param appNet2BdMsg
     */
    public abstract void onAppNet2Bd(AppNet2BdMsg appNet2BdMsg);


    /**
     * 收到公网到北斗网数据（带位置）
     * @param appNet2BdMsg
     */
    public abstract void onAppNet2BdByPos(AppNet2BdByPosMsg appNet2BdMsg);


    /**
     * 救援站消息
     * @param appSosFkiMsg
     */
    public abstract void onAppSosFki(AppSosFkiMsg appSosFkiMsg);

    /**
     * 收到平台转发下的北斗到北斗的信息
     * @param appBd2BdMsg
     */
    public abstract void onAppBd2Bd(AppBd2BdMsg appBd2BdMsg);


    /**
     * 收到平台转发下的北斗到北斗的信息（带位置）
     * @param appBd2BdByPosMsg
     */
    public abstract void onAppBd2BdByPos(AppBd2BdByPosMsg appBd2BdByPosMsg);
}
