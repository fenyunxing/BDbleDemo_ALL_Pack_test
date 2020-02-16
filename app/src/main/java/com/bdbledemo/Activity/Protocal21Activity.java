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
import android.widget.Toast;

import com.bdbledemo.Activity.ScanActiviy.ScanBleActivity21;
import com.bdbledemo.Adapter.DataAdapter;
import com.bdbledemo.Adapter.ScanBleAdapter;
import com.bdbledemo.R;
import com.bdblesdk.executor.handler.BDBLEHandler;
import com.bdblesdk.executor.handler.BLEManager;
import com.bdblesdk.executor.handler.BleScanManager;
import com.bdblesdk.impl.AgentListener;
import com.bdblesdk.impl.BleScanListener;
import com.bddomain.models.entity.protocal2_1.BSIMsg;
import com.bddomain.models.entity.protocal2_1.DWRMsg;
import com.bddomain.models.entity.protocal2_1.FKIMsg;
import com.bddomain.models.entity.protocal2_1.ICIMsg;
import com.bddomain.models.entity.protocal2_1.RMCMsg;
import com.bddomain.models.entity.protocal2_1.TXRMsg;
import com.bddomain.models.entity.protocal2_1.WAAMsg;
import com.bddomain.models.entity.protocalBDHZ.BDHZMsg;
import com.bddomain.repository.protcals.protocal2_1;
import com.bddomain.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Protocal21Activity extends AppCompatActivity implements View.OnClickListener, AgentListener {
    private BleScanManager mbleScanManager;
    private ScanBleAdapter mscanAdapter;
    private DataAdapter mDataAdapter;
    private ListView lv_scanDevice;
    private ListView lv_Data;
    private List<BluetoothDevice> devices = new ArrayList();
    private boolean ifBleOpen;
    private Button btn_login;
    private Button btn_startScan;
    private Button btn_stopScan;
    private Button btn_disconnectDevice;
    private Button btn_ici;
    private Button btn_bsi;
    private Button btn_txa;
    private Button btn_dwa;
    private Button btn_send;
    private Button btn_sos_open;
    private Button btn_sos_close;
    private Button btn_dljc;
    private Button btn_init;
    private Button btn_setSOS;
    private Button btn_back;
    private Button btn_waa;
    private TextView tv_connectedDevice;
    private TextView tv_txr;
    private TextView tv_dwr;
    private TextView tv_rmc;
    private TextView tv_ici;
    private TextView tv_fki;
    private TextView tv_bsi;
    private TextView tv_sos;
    private TextView tv_dl;
    private EditText et_sendText;
    private EditText et_setSOS;
    private Context mContext;
    private String sosNum;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_protocal21);
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
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_startScan = (Button) findViewById(R.id.btn_startScan);
        btn_stopScan = (Button) findViewById(R.id.btn_stopScan);
        btn_disconnectDevice = (Button) findViewById(R.id.btn_disconnectDevice);
        btn_ici = (Button) findViewById(R.id.btn_ici);
        btn_bsi = (Button) findViewById(R.id.btn_bsi);
        btn_txa = (Button) findViewById(R.id.btn_txa);
        btn_dwa = (Button) findViewById(R.id.btn_dwa);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_sos_open = (Button) findViewById(R.id.btn_sos_open);
        btn_sos_close = (Button) findViewById(R.id.btn_sos_close);
        btn_dljc = (Button) findViewById(R.id.btn_dljc);
        btn_init = (Button) findViewById(R.id.btn_ztjc);
        btn_setSOS = (Button) findViewById(R.id.btn_setSOS);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_waa = (Button) findViewById(R.id.btn_waa);
        btn_login.setOnClickListener(this);
        btn_startScan.setOnClickListener(this);
        btn_stopScan.setOnClickListener(this);
        btn_disconnectDevice.setOnClickListener(this);
        btn_ici.setOnClickListener(this);
        btn_bsi.setOnClickListener(this);
        btn_txa.setOnClickListener(this);
        btn_dwa.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_sos_open.setOnClickListener(this);
        btn_sos_close.setOnClickListener(this);
        btn_dljc.setOnClickListener(this);
        btn_init.setOnClickListener(this);
        btn_setSOS.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_waa.setOnClickListener(this);
        tv_connectedDevice = (TextView) findViewById(R.id.tv_connectedDevice);
        tv_txr = (TextView) findViewById(R.id.tv_txr);
        tv_dwr = (TextView) findViewById(R.id.tv_dwr);
        tv_rmc = (TextView) findViewById(R.id.tv_rmc);
        tv_ici = (TextView) findViewById(R.id.tv_ici);
        tv_fki = (TextView) findViewById(R.id.tv_fki);
        tv_bsi = (TextView) findViewById(R.id.tv_bsi);
        tv_sos = (TextView) findViewById(R.id.tv_sos);
        tv_dl = (TextView) findViewById(R.id.tv_dl);
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
                        ScanBleActivity21.class);
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
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!devices.contains(bluetoothDevice)) {
                            devices.add(bluetoothDevice);
                            mscanAdapter.setDatas(devices);
                            mscanAdapter.notifyDataSetChanged();
                        }
                    }
                });*/
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
            case R.id.btn_login:
                new Thread(new Runnable() {
                    public void run() {
                        BLEManager.getInstance().sendCCZDC("0");
                        try {
                            Thread.sleep(500L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        BLEManager.getInstance().sendCCPWD("2", "000000");
                    }
                }).start();

                break;
            case R.id.btn_bsi:
                BLEManager.getInstance().sendCCRMO(2, 3);
                break;
            case R.id.btn_ici:
                BLEManager.getInstance().sendCCICA();
                break;
            case R.id.btn_txa:
                //BLEManager.getInstance().sendCCTXA("0141814",COMMTYPE_NORMAL,HANZI,"你好");//汉字
                //BLEManager.getInstance().sendCCTXA("0141814",COMMTYPE_NORMAL,DAIMA,"123ABCD");//代码
                //BLEManager.getInstance().sendCCTXA("0141814",COMMTYPE_NORMAL,HUNFA,"你好123ABCD");//混发
                BLEManager.getInstance().sendCCTXA("0209676");
                break;
            case R.id.btn_dwa:
                BLEManager.getInstance().sendCCDWA("0209676");
                break;
            case R.id.btn_waa:
                BLEManager.getInstance().sendWBA("0209676", protocal2_1.TYPE_LOW, "0", "60");
                break;
            case R.id.btn_send:
                try {
                    BLEManager.getInstance().bdbleHandler.send(et_sendText.getText().toString().getBytes("gbk"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_sos_open:
                BLEManager.getInstance().sendSOS(sosNum, true, "请求支援！");
                break;
            case R.id.btn_sos_close:
                BLEManager.getInstance().sendSOS(sosNum, false, "请求支援！");
                break;
            case R.id.btn_dljc:
                BLEManager.getInstance().sendDLJC();
                break;
            case R.id.btn_ztjc:
                BLEManager.getInstance().sendINIT();
                break;
            case R.id.btn_setSOS:
                sosNum = et_setSOS.getText().toString();
                BLEManager.getInstance().sendSET("60", et_setSOS.getText().toString());
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


    @Override
    public void onBDTXR(final TXRMsg txrMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_txr.setText("通信类型：" + txrMsg.getMsgType() + "  内容：" + txrMsg.getMsgContent());
            }
        });
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
        Log.v("FDBDTestLog", "ICI主页面");
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
                tv_rmc.setText("经度：" + rmcMsg.getLongiDeg() + "  纬度：" + rmcMsg.getLatiDeg() + "  北京时间：" + rmcMsg.getBJTime() + "  速度：" + rmcMsg.getSpeed()
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
    }

    @Override
    public void onWAA(WAAMsg paraMsg) {

    }


}
