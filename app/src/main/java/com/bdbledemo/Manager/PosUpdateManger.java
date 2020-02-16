package com.bdbledemo.Manager;

import com.bdbledemo.Listener.PosUpdateListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2018/5/9.
 */

public class PosUpdateManger {
    private static List<PosUpdateListener> posUpdateListenerList = new ArrayList();
    private static PosUpdateManger posUpdateManger;

    public PosUpdateManger() {
        registerPosUpdateManger();
    }

    private void registerPosUpdateManger() {
        if (posUpdateManger == null) {
            this.posUpdateManger = this;
        }
    }

    public void setPosUpdateListener(PosUpdateListener impl) {
        this.posUpdateListenerList.add(impl);
    }

    public void removePosUpdateListener(PosUpdateListener impl) {
        this.posUpdateListenerList.remove(impl);
    }

    public void callbackPosMsg(String posMsg) {
        Iterator localIterator = posUpdateListenerList.iterator();
        while (localIterator.hasNext()) {
            PosUpdateListener impl = (PosUpdateListener) localIterator
                    .next();
            impl.onPositionMsgAssembled(posMsg);
        }
    }
}
