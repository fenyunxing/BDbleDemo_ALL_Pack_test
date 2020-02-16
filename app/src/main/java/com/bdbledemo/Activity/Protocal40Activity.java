package com.bdbledemo.Activity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bdbledemo.Activity.ScanActiviy.ScanBleActivity40;
import com.bdbledemo.Adapter.DataAdapter;
import com.bdbledemo.Adapter.ScanBleAdapter;
import com.bdbledemo.R;
import com.bdblesdk40.executor.handler.BDBLEHandler;
import com.bdblesdk40.executor.handler.BLEManager;
import com.bdblesdk40.executor.handler.BleScanManager;
import com.bdblesdk40.impl.AgentListener;
import com.bdblesdk40.impl.BleScanListener;
import com.bddomain40.models.entity.protocal4_0.BBXXMsg;
import com.bddomain40.models.entity.protocal4_0.DWXXMsg;
import com.bddomain40.models.entity.protocal4_0.FKXXMsg;
import com.bddomain40.models.entity.protocal4_0.GLZKMsg;
import com.bddomain40.models.entity.protocal4_0.ICXXMsg;
import com.bddomain40.models.entity.protocal4_0.SJXXMsg;
import com.bddomain40.models.entity.protocal4_0.TXXXMsg;
import com.bddomain40.models.entity.protocal4_0.ZJXXMsg;
import com.bddomain40.repository.protcals.protocal4_0;
import com.bddomain40.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bddomain40.repository.protcals.protocal4_0.MSG_TYPE_MIX;

/**
 * Created by admin on 2017/5/24.
 */

public class Protocal40Activity extends AppCompatActivity implements View.OnClickListener, AgentListener {
    private BleScanManager mbleScanManager;
    private ScanBleAdapter mscanAdapter;
    private DataAdapter mDataAdapter;
    private ListView lv_scanDevice;
    private ListView lv_Data;
    private List<BluetoothDevice> devices = new ArrayList();
    private boolean ifBleOpen;
    private Button btn_startScan;
    private Button btn_stopScan;
    private Button btn_disconnectDevice;
    private Button btn_ici;
    private Button btn_bsi;
    private Button btn_txa;
    private Button btn_dwa;
    private Button btn_send;
    private Button btn_cksc;
    private Button btn_xtzj;
    private Button btn_sjsc;
    private Button btn_bbdq;
    private Button btn_setSOS;
    private Button btn_back;
    private TextView tv_connectedDevice;
    private TextView tv_txr;
    private TextView tv_dwr;
    private TextView tv_sjxx;
    private TextView tv_ici;
    private TextView tv_fki;
    private TextView tv_bsi;
    private TextView tv_sos;
    private TextView tv_dl;
    private TextView tv_zjxx;
    private EditText et_sendText;
    private EditText et_setSOS;
    private Context mContext;
    private String sosNum;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_protocal40);
        viewsInit();
        dataInit();

        mContext = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        BLEManager.getInstance().agentListeners.add(this);
        bleInit();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        BLEManager.getInstance().agentListeners.remove(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mbleScanManager.scanDevice(false);
        BLEManager.getInstance().disConnectBle();
        finish();
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BDBLEHandler.ACTION_GATT_CONNECTED.equals(action)) {
                tv_connectedDevice.setText(BLEManager.getInstance().getDeviceName());
                invalidateOptionsMenu();
                //蓝牙连接返回操作
            } else if (BDBLEHandler.ACTION_GATT_DISCONNECTED.equals(action)) {
                tv_connectedDevice.setText("");
                invalidateOptionsMenu();
                //蓝牙断开返回操作
            } else if (BDBLEHandler.ACTION_DATA_AVAILABLE.equals(action)) {
                mDataAdapter.arr.add(BDMethod.castCalendarToString(Calendar.getInstance(), null) + "  " + intent.getStringExtra(BDBLEHandler.EXTRA_DATA));
                mDataAdapter.notifyDataSetChanged();
                //接收数据状态
            } else if (BDBLEHandler.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //发现服务通道状态
            } else if (BDBLEHandler.ACTION_RSSI.equals(action)) {
                intent.getStringExtra(BDBLEHandler.ACTION_RSSI);//接收到的RSSI值
            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BDBLEHandler.EXTRA_DATA);
        intentFilter.addAction(BDBLEHandler.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BDBLEHandler.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BDBLEHandler.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BDBLEHandler.ACTION_RSSI);
        return intentFilter;
    }

    private void dataInit() {
        mscanAdapter = new ScanBleAdapter(this);
        mDataAdapter = new DataAdapter(this);
        lv_Data.setAdapter(mDataAdapter);
        lv_scanDevice.setAdapter(mscanAdapter);
        lv_scanDevice.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BLEManager.getInstance()
                        .setDeviceAddress(((BluetoothDevice) devices.get(position)).getAddress())
                        .setDeviceName(((BluetoothDevice) devices.get(position)).getName());
                if (BLEManager.getInstance().connectBle(getApplicationContext())) {
                    mbleScanManager.scanDevice(false);
                }
            }
        });
        registerReceiver(mGattUpdateReceiver,
                makeGattUpdateIntentFilter());
    }

    protected void viewsInit() {
        lv_scanDevice = (ListView) findViewById(R.id.lv_scanDevice);
        lv_Data = (ListView) findViewById(R.id.lv_Data);
        btn_startScan = (Button) findViewById(R.id.btn_startScan);
        btn_stopScan = (Button) findViewById(R.id.btn_stopScan);
        btn_disconnectDevice = (Button) findViewById(R.id.btn_disconnectDevice);
        btn_ici = (Button) findViewById(R.id.btn_ici);
        btn_bsi = (Button) findViewById(R.id.btn_bsi);
        btn_txa = (Button) findViewById(R.id.btn_txa);
        btn_dwa = (Button) findViewById(R.id.btn_dwa);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_cksc = (Button) findViewById(R.id.btn_cksc);
        btn_xtzj = (Button) findViewById(R.id.btn_xtzj);
        btn_sjsc = (Button) findViewById(R.id.btn_sjsc);
        btn_bbdq = (Button) findViewById(R.id.btn_bbdq);
        btn_setSOS = (Button) findViewById(R.id.btn_setSOS);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_startScan.setOnClickListener(this);
        btn_stopScan.setOnClickListener(this);
        btn_disconnectDevice.setOnClickListener(this);
        btn_ici.setOnClickListener(this);
        btn_bsi.setOnClickListener(this);
        btn_txa.setOnClickListener(this);
        btn_dwa.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_cksc.setOnClickListener(this);
        btn_xtzj.setOnClickListener(this);
        btn_sjsc.setOnClickListener(this);
        btn_bbdq.setOnClickListener(this);
        btn_setSOS.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        tv_connectedDevice = (TextView) findViewById(R.id.tv_connectedDevice);
        tv_txr = (TextView) findViewById(R.id.tv_txr);
        tv_dwr = (TextView) findViewById(R.id.tv_dwr);
        tv_sjxx = (TextView) findViewById(R.id.tv_sjxx);
        tv_ici = (TextView) findViewById(R.id.tv_ici);
        tv_fki = (TextView) findViewById(R.id.tv_fki);
        tv_bsi = (TextView) findViewById(R.id.tv_bsi);
        tv_sos = (TextView) findViewById(R.id.tv_sos);
        tv_dl = (TextView) findViewById(R.id.tv_dl);
        tv_zjxx = (TextView) findViewById(R.id.tv_zjxx);
        et_sendText = (EditText) findViewById(R.id.et_sendText);
        et_setSOS = (EditText) findViewById(R.id.et_setSOS);

        toolbar = (Toolbar) findViewById(R.id.toolbarble);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_toolbar_menu, menu);
        if (BLEManager.getInstance().ConnectState) {
            //Log.v("FDBDChatTest","onCreateOptionsMenu  ===> 1");
            menu.findItem(R.id.ble_search).setVisible(false);
            menu.findItem(R.id.ble_connect).setVisible(true);
        } else {
            //Log.v("FDBDChatTest","onCreateOptionsMenu  ===> 2");
            menu.findItem(R.id.ble_search).setVisible(true);
            menu.findItem(R.id.ble_connect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }*/
        switch (item.getItemId()) {
            case R.id.ble_search:
                Intent localIntent = new Intent(this,
                        ScanBleActivity40.class);
                startActivity(localIntent);
                return true;
            case R.id.ble_connect:
                BLEManager.getInstance().disConnectBle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void bleInit() {
        mbleScanManager = new BleScanManager();
        //获得ifBleOpen如果为true则符合扫描条件，反之亦然
        ifBleOpen = mbleScanManager.bleScanInit(this, new BleScanListener() {
            @Override
            //设置扫描结果返回响应
            public void onScanResult(final BluetoothDevice bluetoothDevice, int rssi) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!devices.contains(bluetoothDevice)) {
                            devices.add(bluetoothDevice);
                            mscanAdapter.setDatas(devices);
                            mscanAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onScanFailed(int code) {

            }
        });
/*        if(ifBleOpen){
            mbleScanManager.scanDevice(true);
        }else{
            return;
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bsi:
                BLEManager.getInstance().sendGLJC(5);
                break;
            case R.id.btn_ici:
                BLEManager.getInstance().sendICJC(12);
                break;
            case R.id.btn_txa:
                BLEManager.getInstance().sendTXSQ(248112, MSG_TYPE_MIX, "你好ABC123");
                //BLEManager.getInstance().sendTXSQ(248112,MSG_TYPE_CHINESE,"你好");
                break;
            case R.id.btn_dwa:
                BLEManager.getInstance().sendDWSQ();
                break;
            case R.id.btn_send:
                try {
                    BLEManager.getInstance().send(et_sendText.getText().toString().getBytes("gbk"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_cksc:
                BLEManager.getInstance().sendCKSC(protocal4_0.BAUDRATE_115200);
                break;
            case R.id.btn_xtzj:
                BLEManager.getInstance().sendXTZJ(60);
                break;
            case R.id.btn_sjsc:
                BLEManager.getInstance().sendSJSC(5);
                break;
            case R.id.btn_bbdq:
                BLEManager.getInstance().sendBBDQ();
                break;
            case R.id.btn_setSOS:
                sosNum = et_setSOS.getText().toString();
                //BLEManager.getInstance().sendSET("60", et_setSOS.getText().toString());
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_disconnectDevice:
                BLEManager.getInstance().disConnectBle();
                break;
            case R.id.btn_startScan:
                mbleScanManager.scanDevice(true);
                break;
            case R.id.btn_stopScan:
                mbleScanManager.scanDevice(false);
                break;
            default:
                break;
        }
    }

/*    @Override
    public void onBDTXR(final TXRMsg txrMsg) {
        *//*if(BdPlatformManager.getInstance().ifPlatformData(txrMsg)){
            BdPlatformManager.getInstance().receivedPlatform(txrMsg);
        }else {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_txr.setText("通信类型：" + txrMsg.getMsgType() + "  内容：" + txrMsg.getMsgContent());
                }
            });
        }*//*
    }

    @Override
    public void onBDBSI(final BSIMsg bsiMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_bsi.setText("功率值：" + bsiMsg.getBeamPow1() + "-" + bsiMsg.getBeamPow2() + "-" + bsiMsg.getBeamPow3() + "-"
                        + bsiMsg.getBeamPow4() + "-" + bsiMsg.getBeamPow5() + "-" + bsiMsg.getBeamPow6() + "-"
                        + bsiMsg.getBeamPow7() + "-" + bsiMsg.getBeamPow8() + "-" + bsiMsg.getBeamPow9() + "-"
                        + bsiMsg.getBeamPow10());
            }
        });
    }

    @Override
    public void onBDICI(final ICIMsg iciMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_ici.setText("卡号：" + iciMsg.getICnum() + "  通信等级：" + iciMsg.getLevel());
            }
        });
    }

    @Override
    public void onBDFKI(final FKIMsg fkiMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_fki.setText("反馈指令名称：" + fkiMsg.getComStr() + "  指令执行情况：" + (fkiMsg.getComExeSituation() == true ? "Y" : "N")
                        + "  频度设置指示：" + (fkiMsg.getFreqSetIdication() == true ? "Y" : "N"));
            }
        });
    }

    @Override
    public void onBDDWR(final DWRMsg dwrMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_dwr.setText("经度：" + dwrMsg.getLongiDeg() + "  纬度：" + dwrMsg.getLatiDeg() + "  高度：" + dwrMsg.getGeoHig() + " 精度指示：" + dwrMsg.getPrecisionInd());
            }
        });
    }

    @Override
    public void onBDERR() {

    }

    @Override
    public void onRMC(final RMCMsg rmcMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_sjxx.setText("经度：" + rmcMsg.getLongiDeg() + "  纬度：" + rmcMsg.getLatiDeg() + "  北京时间：" + rmcMsg.getBJTime() + "  速度：" + rmcMsg.getSpeed()
                        + "  航向：" + rmcMsg.getDirection() + "  定位有效性：" + rmcMsg.getDWstaus());
            }
        });
    }

    @Override
    public void onBDHZ(final BDHZMsg bdhzMsg) {
        if (bdhzMsg.getBDHZMsgStyle().equals(BDHZMsg.BDHZ_STYLE_DLFK)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_dl.setText("电量:" + bdhzMsg.getPwrnumStr() + "%");
                }
            });
        } else if (bdhzMsg.getBDHZMsgStyle().equals(BDHZMsg.BDHZ_STYLE_SOS)) {
            sosNum = bdhzMsg.getSOSnumStr();
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_sos.setText("SOS状态:" + bdhzMsg.isIfOpenSos() + "  SOS上报卡号: " + bdhzMsg.getSOSnumStr());
                }
            });
        } else if (bdhzMsg.getBDHZMsgStyle().equals(BDHZMsg.BDHZ_STYLE_SET)) {
            switch (bdhzMsg.getSetStatus()) {
                case "A":
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "SOS卡号设置成功", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                    break;
                case "V":
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "SOS卡号设置失败", Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                default:

                    break;
            }
        }
    }*/

    @Override
    public void onBBXX(BBXXMsg paraMsg) {

    }

    @Override
    public void onDWXX(final DWXXMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_dwr.setText("源HEX ==> " + paraMsg.getDwxxHexStr() + "\n\r"
                        + "经度(度) ==> " + paraMsg.getLongitude() + "\n\r"
                        + "经度(度分秒) ==> " + paraMsg.getLngDeg() + "点秒 = " + paraMsg.getLngSS() + "\n\r"
                        + "纬度(度) ==> " + paraMsg.getLongitude() + "\n\r"
                        + "纬度(度分秒) ==> " + paraMsg.getLatDeg() + "点秒 = " + paraMsg.getLatSS() + "\n\r"
                        + "纬度(度分秒) ==> " + paraMsg.getLatDeg() + "\n\r"
                        + "定位类别 ==> " + paraMsg.getDwType() + "\n\r"
                        + "秘钥 ==> " + paraMsg.getIfKey() + "\n\r"
                        + "精度 ==> " + paraMsg.getAccuracy() + "\n\r"
                        + "紧急定位 ==> " + paraMsg.getIfEmergency() + "\n\r"
                        + "多值解 ==> " + paraMsg.getIfMultiValue() + "\n\r"
                        + "高程类型 ==> " + paraMsg.getHighType() + "\n\r"
                        + "时间 ==> " + paraMsg.getTime() + "\n\r"
                        + "高程 ==> " + paraMsg.getAltitude() + "\n\r"
                        + "高程异常 ==> " + paraMsg.getAltitudeAno() + "\n\r");
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getDwxxHexStr() + "\n\r"
                        + "经度(度) ==> " + paraMsg.getLongitude() + "\n\r"
                        + "经度(度分秒) ==> " + paraMsg.getLngDeg() + "点秒 = " + paraMsg.getLngSS() + "\n\r"
                        + "纬度(度) ==> " + paraMsg.getLongitude() + "\n\r"
                        + "纬度(度分秒) ==> " + paraMsg.getLatDeg() + "点秒 = " + paraMsg.getLatSS() + "\n\r"
                        + "纬度(度分秒) ==> " + paraMsg.getLatDeg() + "\n\r"
                        + "定位类别 ==> " + paraMsg.getDwType() + "\n\r"
                        + "秘钥 ==> " + paraMsg.getIfKey() + "\n\r"
                        + "精度 ==> " + paraMsg.getAccuracy() + "\n\r"
                        + "紧急定位 ==> " + paraMsg.getIfEmergency() + "\n\r"
                        + "多值解 ==> " + paraMsg.getIfMultiValue() + "\n\r"
                        + "高程类型 ==> " + paraMsg.getHighType() + "\n\r"
                        + "时间 ==> " + paraMsg.getTime() + "\n\r"
                        + "高程 ==> " + paraMsg.getAltitude() + "\n\r"
                        + "高程异常 ==> " + paraMsg.getAltitudeAno() + "\n\r"
        );
    }

    @Override
    public void onFKXX(final FKXXMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_fki.setText("源HEX ==> " + paraMsg.getFkxxHexStr() + "\n\r"
                        + "反馈标志 ==> " + paraMsg.getFkFlag() + "\n\r"
                        + "反馈内容 ==> " + paraMsg.getFkContent() + "\n\r");
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getFkxxHexStr() + "\n\r"
                        + "反馈标志 ==> " + paraMsg.getFkFlag() + "\n\r"
                        + "反馈内容 ==> " + paraMsg.getFkContent() + "\n\r"
        );
    }

    @Override
    public void onGLZK(final GLZKMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_bsi.setText("源HEX ==> " + paraMsg.getGlzkHexStr() + "\n\r"
                        + "功率 ==> " + paraMsg.getPw1() + "-"
                        + paraMsg.getPw2() + "-" + paraMsg.getPw3() + "-"
                        + paraMsg.getPw4() + "-" + paraMsg.getPw5() + "-"
                        + paraMsg.getPw6());
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getGlzkHexStr() + "\n\r"
                        + "功率 ==> " + paraMsg.getPw1() + "-"
                        + paraMsg.getPw2() + "-" + paraMsg.getPw3() + "-"
                        + paraMsg.getPw4() + "-" + paraMsg.getPw5() + "-"
                        + paraMsg.getPw6());
    }

    @Override
    public void onICXX(final ICXXMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_ici.setText("源HEX ==> " + paraMsg.getIcxxHexStr() + "\n\r"
                        + "帧号 ==> " + paraMsg.getFrameNum() + "\n\r"
                        + "通播ID ==> " + paraMsg.getBroadcastId() + "\n\r"
                        + "用户特征 ==> " + paraMsg.getUserCharacter() + "\n\r"
                        + "服务频度 ==> " + paraMsg.getServiceFreq() + "\n\r"
                        + "通信等级 ==> " + paraMsg.getLevel() + "\n\r"
                        + "加密标示 ==> " + paraMsg.getEncryptFlag() + "\n\r"
                        + "下属用户数 ==> " + paraMsg.getSubUserNum() + "\n\r");
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getIcxxHexStr() + "\n\r"
                        + "帧号 ==> " + paraMsg.getFrameNum() + "\n\r"
                        + "通播ID ==> " + paraMsg.getBroadcastId() + "\n\r"
                        + "用户特征 ==> " + paraMsg.getUserCharacter() + "\n\r"
                        + "服务频度 ==> " + paraMsg.getServiceFreq() + "\n\r"
                        + "通信等级 ==> " + paraMsg.getLevel() + "\n\r"
                        + "加密标示 ==> " + paraMsg.getEncryptFlag() + "\n\r"
                        + "下属用户数 ==> " + paraMsg.getSubUserNum() + "\n\r"
        );
    }

    @Override
    public void onSJXX(final SJXXMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_sjxx.setText("源HEX ==> " + paraMsg.getSjxxHexStr() + "\n\r"
                        + paraMsg.getYear() + "-" + paraMsg.getMonth() + "-" + paraMsg.getDay()
                        + "  " + paraMsg.getHour() + ":" + paraMsg.getMinute() + ":" + paraMsg.getSecond());
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getSjxxHexStr() + "\n\r"
                        + paraMsg.getYear() + "-" + paraMsg.getMonth() + "-" + paraMsg.getDay()
                        + "  " + paraMsg.getHour() + ":" + paraMsg.getMinute() + ":" + paraMsg.getSecond()
        );
    }

    @Override
    public void onTXXX(final TXXXMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_txr.setText("源HEX ==> " + paraMsg.getTxxxHexStr() + "\n\r"
                        + "发信方地址 ==> " + paraMsg.getSendIc() + "\n\r"
                        + "发信时间 ==> " + paraMsg.getSendTime() + "\n\r"
                        + "电文长度 ==> " + paraMsg.getMsgByteLength() + "\n\r"
                        + "电文形式 ==> " + paraMsg.getMsgType() + "\n\r"
                        + "通信形式 ==> " + paraMsg.getCommuType() + "\n\r"
                        + "内容 ==> " + paraMsg.getMsg() + "\n\r"
                        + "CRC ==> " + paraMsg.getCrcFlag() + "\n\r");
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getTxxxHexStr() + "\n\r"
                        + "发信方地址 ==> " + paraMsg.getSendIc() + "\n\r"
                        + "发信时间 ==> " + paraMsg.getSendTime() + "\n\r"
                        + "电文长度 ==> " + paraMsg.getMsgByteLength() + "\n\r"
                        + "电文形式 ==> " + paraMsg.getMsgType() + "\n\r"
                        + "通信形式 ==> " + paraMsg.getCommuType() + "\n\r"
                        + "内容 ==> " + paraMsg.getMsg() + "\n\r"
                        + "CRC ==> " + paraMsg.getCrcFlag() + "\n\r"
        );
    }

    @Override
    public void onZJXX(final ZJXXMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_zjxx.setText("源HEX ==> " + paraMsg.getZjxxHexStr() + "\n\r"
                        + "ic卡状态 ==> " + paraMsg.getIcStatus() + "\n\r"
                        + "硬件状态 ==> " + paraMsg.getHardwareStatus() + "\n\r"
                        + "电池电量 ==> " + paraMsg.getBatteryStatus() + "\n\r"
                        + "入站状态 ==> " + paraMsg.getInboundStatus() + "\n\r"
                        + "功率 ==> " + paraMsg.getPw1() + "-"
                        + paraMsg.getPw2() + "-" + paraMsg.getPw3() + "-"
                        + paraMsg.getPw4() + "-" + paraMsg.getPw5() + "-"
                        + paraMsg.getPw6());
            }
        });

        Log.v("FDBDTestLog",
                "源HEX ==> " + paraMsg.getZjxxHexStr() + "\n\r"
                        + "ic卡状态 ==> " + paraMsg.getIcStatus() + "\n\r"
                        + "硬件状态 ==> " + paraMsg.getHardwareStatus() + "\n\r"
                        + "电池电量 ==> " + paraMsg.getBatteryStatus() + "\n\r"
                        + "入站状态 ==> " + paraMsg.getInboundStatus() + "\n\r"
                        + "功率 ==> " + paraMsg.getPw1() + "-"
                        + paraMsg.getPw2() + "-" + paraMsg.getPw3() + "-"
                        + paraMsg.getPw4() + "-" + paraMsg.getPw5() + "-"
                        + paraMsg.getPw6()
        );
    }

}
