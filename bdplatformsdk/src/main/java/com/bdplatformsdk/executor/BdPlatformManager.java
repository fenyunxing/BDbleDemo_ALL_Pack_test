package com.bdplatformsdk.executor;


import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bdplatformsdk.impl.PlatformListener;
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
import com.bdplatformsdk.repository.protcals.protocal_platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.bdplatformsdk.repository.protcals.protocal_platform.APP_BD2NET;
import static com.bdplatformsdk.repository.protcals.protocal_platform.APP_BD2NET_POS;
import static com.bdplatformsdk.repository.protcals.protocal_platform.APP_BD_LOGIN_FKI;
import static com.bdplatformsdk.repository.protcals.protocal_platform.APP_NET2BD;
import static com.bdplatformsdk.repository.protcals.protocal_platform.APP_NET2BD_POS;
import static com.bdplatformsdk.repository.protcals.protocal_platform.APP_SOS_FKI;
import static com.bdplatformsdk.repository.protcals.protocal_platform.appTypeList;

/**
 * Created by admin on 2017/5/22.
 */

public class BdPlatformManager {
    private static BdPlatformManager bdPlatformManager;
    public static List<PlatformListener> platformListener = new ArrayList();
    public static BdPlatformHandler bdPlatformHandler = null;

    /**
     * 获得BdPlatformManager单例
     *
     * @return 返回单例
     */
    public static BdPlatformManager getInstance() {
        if (bdPlatformManager == null)
            bdPlatformManager = new BdPlatformManager();
        return bdPlatformManager;
    }

    /**
     * 判断该TXR报文是否为平台协议信息
     *
     * @param mtxr
     *         TXR报文信息
     */
    public static boolean ifPlatformData(TXRMsg mtxr) {
        switch (mtxr.getMsgTypeInt()) {
            case 1:
                String type = mtxr.getMsgHexStr().substring(0, 2);
                if(appTypeList.indexOf(type) == -1){
                    mtxr.setIfPlatform(false);
                    return false;
                }else{
                    mtxr.setIfPlatform(true);
                    mtxr.setPlatformDataType(type);
                    return true;
                }
            case 2:
                if (mtxr.getMsgHexStr().substring(2, 4).equals("AA")
                        || mtxr.getMsgHexStr().substring(2, 4).equals("AB")) {
                    mtxr.setIfPlatform(true);
                    switch (mtxr.getMsgHexStr().substring(4, 6)) {
                        case protocal_platform.TYPE_CARD_2_CARD_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_CARD_2_CARD_STR);
                            break;
                        case protocal_platform.TYPE_USER_2_USER_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_USER_2_USER_STR);
                            break;
                        case protocal_platform.TYPE_CARD_2_PHONE_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_CARD_2_PHONE_STR);
                            break;
                        case protocal_platform.TYPE_TASK_REV_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_TASK_REV_STR);
                            break;
                        case protocal_platform.TYPE_FKI_LOGIN_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_FKI_LOGIN_STR);
                            break;
                        case protocal_platform.TYPE_FKI_TASK_REPORT_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_FKI_TASK_REPORT_STR);
                            break;
                        case protocal_platform.EMERGENCY_PLATFORM_RESPONSE_STR:
                            mtxr.setPlatformDataType(protocal_platform.EMERGENCY_PLATFORM_RESPONSE_STR);
                            break;
                        case protocal_platform.TYPE_USER_2_USER_BYLOC_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_USER_2_USER_BYLOC_STR);
                            break;
                        case protocal_platform.TYPE_USER_2_PHONE_BYLOC_STR:
                            mtxr.setPlatformDataType(protocal_platform.TYPE_USER_2_PHONE_BYLOC_STR);
                            break;
                        default:
                            break;
                    }
                    return true;
                } else {
                    mtxr.setIfPlatform(false);
                    return false;
                }
            default:
                mtxr.setIfPlatform(false);
                return false;
        }
    }

    /**
     * 接收平台数据并下发通知
     *
     * @param mtxr
     *         TXR平台信息
     */
    public static void receivedPlatform(TXRMsg mtxr) {
        if (mtxr.isIfPlatform()) {
            Iterator localIterator = platformListener.iterator();
            while (localIterator.hasNext()) {
                PlatformListener localPlatformListener = (PlatformListener) localIterator
                        .next();
                switch (mtxr.getPlatformDataType()) {
                    case APP_BD_LOGIN_FKI:
                        AppLoginFKIMsg appLoginFKIMsg = new AppLoginFKIMsg(mtxr);
                        localPlatformListener.onAppLoginFki(appLoginFKIMsg);
                        break;
                    case APP_NET2BD:
                        AppNet2BdMsg appNet2BdMsg = new AppNet2BdMsg(mtxr);
                        localPlatformListener.onAppNet2Bd(appNet2BdMsg);
                        break;
                    case APP_NET2BD_POS:
                        AppNet2BdByPosMsg appNet2BdByPosMsg = new AppNet2BdByPosMsg(mtxr);
                        localPlatformListener.onAppNet2BdByPos(appNet2BdByPosMsg);
                        break;
                    case APP_BD2NET:
                        AppBd2BdMsg appBD2Net = new AppBd2BdMsg(mtxr);
                        localPlatformListener.onAppBd2Bd(appBD2Net); //TODO 实际上这里不应该是bd2bd，也不会接收到bd2net的消息，而是接收到APP_NET2BD的消息，这里写了只是代替
                        break;
                    case APP_BD2NET_POS:
                        AppBd2BdByPosMsg appBd2BdByPosMsg = new AppBd2BdByPosMsg(mtxr);
                        localPlatformListener.onAppBd2BdByPos(appBd2BdByPosMsg);
                        break;
                    case APP_SOS_FKI:
                        AppSosFkiMsg appSosFkiMsg = new AppSosFkiMsg(mtxr);
                        localPlatformListener.onAppSosFki(appSosFkiMsg);
                        break;
                    case protocal_platform.TYPE_CARD_2_CARD_STR:
                        CardtoCardMsg paramC2C = new CardtoCardMsg(mtxr);
                        localPlatformListener.onCardToCard(paramC2C);
                        break;
                    case protocal_platform.TYPE_USER_2_USER_STR:
                        UsertoUserMsg paramU2U = new UsertoUserMsg(mtxr);
                        localPlatformListener.onUserToUser(paramU2U);
                        break;
                    case protocal_platform.TYPE_CARD_2_PHONE_STR:
                        PhonetoCardMsg paramC2P = new PhonetoCardMsg(mtxr);
                        localPlatformListener.onPhoneToCard(paramC2P);
                        break;
                    case protocal_platform.TYPE_TASK_REV_STR:
                        TASKMsg paramTASKMsg = new TASKMsg(mtxr);
                        localPlatformListener.onPlatTask(paramTASKMsg);
                        break;
                    case protocal_platform.TYPE_FKI_TASK_REPORT_STR:
                        TaskFKIMsg paramTaskFKIMsg = new TaskFKIMsg(mtxr);
                        localPlatformListener.onPlatTaskConfirm(paramTaskFKIMsg);
                        break;
                    case protocal_platform.TYPE_FKI_LOGIN_STR:
                        LoginFKIMsg paramLoginFKIMsg = new LoginFKIMsg(mtxr);
                        localPlatformListener.onPlatFkiLogin(paramLoginFKIMsg);
                        break;
                    case protocal_platform.EMERGENCY_PLATFORM_RESPONSE_STR:
                        EmergencyResponse emergencyResponse = new EmergencyResponse(mtxr);
                        localPlatformListener.onPlatEmergnConfirm(emergencyResponse);
                        break;
                    case protocal_platform.TYPE_USER_2_USER_BYLOC_STR:
                        UsertoUserMsg usertoUserMsg = new UsertoUserMsg(mtxr);
                        localPlatformListener.onUserToUser(usertoUserMsg);
                        break;
                    case protocal_platform.TYPE_USER_2_PHONE_BYLOC_STR:
                        PhonetoCardMsg phonetoCardMsg = new PhonetoCardMsg(mtxr);
                        localPlatformListener.onPhoneToCard(phonetoCardMsg);
                        break;
                    default:
                        break;
                }
            }
        }
    }


}
