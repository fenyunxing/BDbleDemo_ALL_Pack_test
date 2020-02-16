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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bdbledemo.Activity.ScanActiviy.ScanBleActivityUni;
import com.bdbledemo.Adapter.DataAdapter;
import com.bdbledemo.Adapter.ScanBleAdapter;
import com.bdbledemo.R;
import com.bdblesdkuni.executor.handler.BDBLEHandler;
import com.bdblesdkuni.executor.handler.BLEManager;
import com.bdblesdkuni.executor.handler.BleScanManager;
import com.bdblesdkuni.impl.AgentListener;
import com.bdblesdkuni.impl.BleScanListener;
import com.bddomainuni.models.entity.protocal2_1.BSIMsg;
import com.bddomainuni.models.entity.protocal2_1.DWRMsg;
import com.bddomainuni.models.entity.protocal2_1.FKIMsg;
import com.bddomainuni.models.entity.protocal2_1.GGAMsg;
import com.bddomainuni.models.entity.protocal2_1.ICIMsg;
import com.bddomainuni.models.entity.protocal2_1.RMCMsg;
import com.bddomainuni.models.entity.protocal2_1.TXRMsg;
import com.bddomainuni.models.entity.protocal2_1.WAAMsg;
import com.bddomainuni.models.entity.protocal4_0.BBXXMsg;
import com.bddomainuni.models.entity.protocal4_0.DWXXMsg;
import com.bddomainuni.models.entity.protocal4_0.FKXXMsg;
import com.bddomainuni.models.entity.protocal4_0.GLZKMsg;
import com.bddomainuni.models.entity.protocal4_0.ICXXMsg;
import com.bddomainuni.models.entity.protocal4_0.SJXXMsg;
import com.bddomainuni.models.entity.protocal4_0.TXXXMsg;
import com.bddomainuni.models.entity.protocal4_0.ZJXXMsg;
import com.bddomainuni.models.entity.protocalBDHZ.BDHZMsg;
import com.bddomainuni.models.entity.protocalBDHZ.BDMSH;
import com.bddomainuni.models.entity.protocalBDHZ.BDQDX;
import com.bddomainuni.models.entity.protocalBDHZ.BDQKX;
import com.bddomainuni.models.entity.protocalBDHZ.BDQZX;
import com.bddomainuni.repository.protcals.protocal4_0;
import com.bddomainuni.repository.protcals.protocalEntity;
import com.bddomainuni.repository.tools.BDMethod;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bddomainuni.repository.protcals.protocal4_0.MSG_TYPE_CHINESE;
import static com.bddomainuni.repository.protcals.protocal4_0.MSG_TYPE_MIX;
import static com.bddomainuni.repository.protcals.protocalEntity.MODE_RDRN;
import static com.bddomainuni.repository.protcals.protocalEntity.TYPE_SET;
import static com.bddomainuni.repository.protcals.protocal_BDHZ.TYPE_LOGIN_NORMAL;

/**
 * Created by admin on 2017/5/31.
 */

public class ProtocalUniActivity extends AppCompatActivity implements View.OnClickListener, AgentListener {
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
    private Button btn_ici_21;
    private Button btn_bsi_21;
    private Button btn_txa_21;
    private Button btn_dwa_21;
    private Button btn_send;
    private Button btn_cksc;
    private Button btn_xtzj;
    private Button btn_sjsc;
    private Button btn_bbdq;
    private Button btn_setSOS;
    private Button btn_sos_open;
    private Button btn_sos_close;
    private Button btn_dljc;
    private Button btn_init;
    private Button btn_back;
    private Button btn_oks;
    private Button btn_zzm;
    private Button btn_shm;
    private Button btn_msc;
    private Button btn_vrq;

    private Button btn_bd_to_bd;
    private Button btn_bd_to_bd_bypos;
    private Button btn_bd_to_net;
    private Button btn_bd_to_net_bypos;
    private Button btn_bd_to_phone;
    private Button btn_bd_to_phone_bypos;
    private Button btn_bd_pos;
    private Button btn_bd_login;
    private Button btn_qjy;
    private Button btn_qzz;
    private Button btn_qok;

    private TextView tv_connectedDevice;
    private TextView tv_zdx;
    private TextView tv_pwx;
    private TextView tv_okx;
    private TextView tv_zzx;
    private TextView tv_hmx;
    private TextView tv_txr;
    private TextView tv_dwr;
    private TextView tv_sjxx;
    private TextView tv_ici;
    private TextView tv_fki;
    private TextView tv_bsi;
    private TextView tv_sos;
    private TextView tv_dl;
    private TextView tv_zjxx;
    private TextView tv_txr_21;
    private TextView tv_dwr_21;
    private TextView tv_ici_21;
    private TextView tv_fki_21;
    private TextView tv_bsi_21;
    private TextView tv_rmc_21;
    private TextView tv_gga_21;
    private EditText et_sendText;
    private EditText et_setSOS;
    private Context mContext;
    private String sosNum;

    private RMCMsg rmcTestMsg; //2.1协议RMC信息类

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_protocaluni);
        viewsInit();
        dataInit();
        //float num = 108.2255f;
        //Log.v("FDBDTestLog","输出"+ BDMethod.castLongToHexStringByNum((new Date().getTime())/1000,4));
        mContext = this;


    }



    @Override
    public void onResume() {
        super.onResume();
        BLEManager.getInstance().agentListeners.add(this);//往agentListeners集合添加对象
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
    public void onBackPressed() {//手机物理按键，返回
        super.onBackPressed();
        //取消蓝牙连接
        mbleScanManager.scanDevice(false);
        BLEManager.getInstance().disConnectBle();
        finish();
    }
     //广播接收类，接受gatt广播
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
    //添加要 监听的广播消息
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
//        lv_scanDevice.setAdapter(mscanAdapter);
//        lv_scanDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                BLEManager.getInstance()
//                        .setDeviceAddress(((BluetoothDevice) devices.get(position)).getAddress())
//                        .setDeviceName(((BluetoothDevice) devices.get(position)).getName());
//                if (BLEManager.getInstance().connectBle(getApplicationContext())) {
//                    mbleScanManager.scanDevice(false);
//                }
//            }
//        });
        //动态注册广播
        registerReceiver(mGattUpdateReceiver,
                makeGattUpdateIntentFilter());
        //2.1协议RMC信息类
        rmcTestMsg = new RMCMsg();
        rmcTestMsg.SetDWstaus("A");
        rmcTestMsg.setLatitude(26d);
        rmcTestMsg.setLongitude(119d);
        rmcTestMsg.setHeight(8);
    }

    protected void viewsInit() {
        lv_scanDevice = (ListView) findViewById(R.id.lv_scanDevice);
        lv_Data = (ListView) findViewById(R.id.lv_Data);//数据显示区列表
        btn_login = (Button) findViewById(R.id.btn_login);//初始化按钮
        btn_startScan = (Button) findViewById(R.id.btn_startScan);
        btn_stopScan = (Button) findViewById(R.id.btn_stopScan);
        btn_disconnectDevice = (Button) findViewById(R.id.btn_disconnectDevice);
        btn_ici = (Button) findViewById(R.id.btn_ici);//4.0iC按钮
        btn_bsi = (Button) findViewById(R.id.btn_bsi);//4.0 功率按钮
        btn_txa = (Button) findViewById(R.id.btn_txa);//4.0通信按钮
        btn_dwa = (Button) findViewById(R.id.btn_dwa);//4.0定位按钮
        btn_send = (Button) findViewById(R.id.btn_send);//发送按钮
        btn_cksc = (Button) findViewById(R.id.btn_cksc);//4.0串口输出按钮
        btn_xtzj = (Button) findViewById(R.id.btn_xtzj);//4.0系统自检按钮
        btn_sjsc = (Button) findViewById(R.id.btn_sjsc);//4.0时间输出按钮
        btn_bbdq = (Button) findViewById(R.id.btn_bbdq);//4.0版本读取按钮
        btn_setSOS = (Button) findViewById(R.id.btn_setSOS);//盒子掉电保存SOS卡号按钮
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_ici_21 = (Button) findViewById(R.id.btn_ici_21);//2.1 IC按钮
        btn_bsi_21 = (Button) findViewById(R.id.btn_bsi_21);//2.1功率按钮
        btn_txa_21 = (Button) findViewById(R.id.btn_txa_21);//2.1通信按钮
        btn_dwa_21 = (Button) findViewById(R.id.btn_dwa_21);//2.1定位按钮
        btn_sos_open = (Button) findViewById(R.id.btn_sos_open);//SOS开按钮
        btn_sos_close = (Button) findViewById(R.id.btn_sos_close);//SOS关按钮
        btn_dljc = (Button) findViewById(R.id.btn_dljc);//电量按钮
        btn_init = (Button) findViewById(R.id.btn_ztjc);//电量初始化按钮
        btn_oks = (Button) findViewById(R.id.btn_oks);//OKS按钮
        btn_zzm = (Button) findViewById(R.id.btn_zzm);//ZZM按钮
        btn_shm = (Button) findViewById(R.id.btn_shm);//SHM按钮
        btn_bd_to_net = (Button) findViewById(R.id.btn_bd_to_net);//2net按钮
        btn_bd_to_net_bypos = (Button) findViewById(R.id.btn_bd_to_net_bypos);//2netPOS按钮
        btn_bd_to_phone = (Button) findViewById(R.id.btn_bd_to_phone);//2PHONE按钮
        btn_bd_to_phone_bypos = (Button) findViewById(R.id.btn_bd_to_phone_bypos);//2PHONEPOS按钮
        btn_bd_pos = (Button) findViewById(R.id.btn_bd_pos);//位置上报按钮
        btn_bd_login = (Button) findViewById(R.id.btn_bd_login);//登录按钮
        btn_qjy = (Button) findViewById(R.id.btn_qjy);//一键SOS
        btn_qzz = (Button) findViewById(R.id.btn_qzz);//一键追踪
        btn_qok = (Button) findViewById(R.id.btn_qok);//一键OK
        btn_msc = (Button) findViewById(R.id.btn_msc);//工作模式
        btn_vrq = (Button) findViewById(R.id.btn_vrq);//版本查询
        btn_bd_to_bd = (Button) findViewById(R.id.btn_bd2bd);//2BD
        btn_bd_to_bd_bypos = (Button) findViewById(R.id.btn_bd2bd_pos);//2BDPOS

        btn_msc.setOnClickListener(this);
        btn_vrq.setOnClickListener(this);
        btn_bd_to_bd.setOnClickListener(this);
        btn_bd_to_bd_bypos.setOnClickListener(this);
        btn_qjy.setOnClickListener(this);
        btn_qzz.setOnClickListener(this);
        btn_qok.setOnClickListener(this);
        btn_oks.setOnClickListener(this);
        btn_zzm.setOnClickListener(this);
        btn_shm.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_bd_login.setOnClickListener(this);
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
        btn_ici_21.setOnClickListener(this);
        btn_bsi_21.setOnClickListener(this);
        btn_txa_21.setOnClickListener(this);
        btn_dwa_21.setOnClickListener(this);
        btn_sos_open.setOnClickListener(this);
        btn_sos_close.setOnClickListener(this);
        btn_dljc.setOnClickListener(this);
        btn_init.setOnClickListener(this);
        btn_bd_to_net.setOnClickListener(this);
        btn_bd_to_net_bypos.setOnClickListener(this);
        btn_bd_to_phone.setOnClickListener(this);
        btn_bd_to_phone_bypos.setOnClickListener(this);
        btn_bd_pos.setOnClickListener(this);
        tv_connectedDevice = (TextView) findViewById(R.id.tv_connectedDevice);
        tv_zdx = (TextView) findViewById(R.id.tv_zdx);
        tv_pwx = (TextView) findViewById(R.id.tv_pwx);
        tv_okx = (TextView) findViewById(R.id.tv_okx);
        tv_zzx = (TextView) findViewById(R.id.tv_zzx);
        tv_hmx = (TextView) findViewById(R.id.tv_hmx);
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
        tv_txr_21 = (TextView) findViewById(R.id.tv_txr_21);
        tv_dwr_21 = (TextView) findViewById(R.id.tv_dwr_21);
        tv_ici_21 = (TextView) findViewById(R.id.tv_ici_21);
        tv_fki_21 = (TextView) findViewById(R.id.tv_fki_21);
        tv_bsi_21 = (TextView) findViewById(R.id.tv_bsi_21);
        tv_rmc_21 = (TextView) findViewById(R.id.tv_rmc_21);
        tv_gga_21 = (TextView) findViewById(R.id.tv_gga_21);
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
            case R.id.ble_search://点击打开蓝牙按钮图标，跳转到扫描界面
                Intent localIntent = new Intent(this,
                        ScanBleActivityUni.class);
                startActivity(localIntent);
                return true;
            case R.id.ble_connect: //关闭蓝牙
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

    private boolean ifSosOpen = false;
    private boolean ifOKOpen = false;
    private boolean ifZzmOpen = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msc: //工作模式按钮，查询系统当前工作模式(CCMSC)
                BLEManager.getInstance().sendCCMSC();
                break;
            case R.id.btn_vrq://版本查询按钮
                BLEManager.getInstance().sendCCVRQ();
                break;
            case R.id.btn_bd2bd://（2DB按钮）北斗网到北斗网
                BLEManager.getInstance().sendBD2BD("123456", "你好");
                break;
            case R.id.btn_bd2bd_pos://(2DBPOS按钮) 北斗网到北斗网(带位置)
                BLEManager.getInstance().sendBD2BD_POS("123456", "你好", rmcTestMsg);
                break;
            case R.id.btn_qjy://(一键SOS按钮) 启动/关闭紧急求救
                ifSosOpen = !ifSosOpen;
                BLEManager.getInstance().sendCCQJY(ifSosOpen);
                break;
            case R.id.btn_qzz://（一键追踪按钮） 启动/关闭极限追踪
                ifZzmOpen = !ifZzmOpen;
                BLEManager.getInstance().sendCCQZZ(ifZzmOpen);
                break;
            case R.id.btn_qok: //（一键OK按钮）启动/关闭OK
                ifOKOpen = !ifOKOpen;
                BLEManager.getInstance().sendCCQOK(ifOKOpen);
                break;
            case R.id.btn_bd_login://（登录按钮） 北斗网登录
                BLEManager.getInstance().sendBDLOGIN("209704", TYPE_LOGIN_NORMAL, "15060877825", "123456");
                break;
            case R.id.btn_oks: //（OKS按钮）OK键设置并发送设置参数
                protocalEntity.CCOKSobj ccoksSobj = new protocalEntity.CCOKSobj();
                ccoksSobj.setType(TYPE_SET)
                        .setCenterCrd("123456")
                        .setMsg("你好");

                BLEManager.getInstance().sendCCOKS(ccoksSobj);
                break;
            case R.id.btn_zzm://（zzm按钮）极限追踪设置和查询（CCZZM）
                protocalEntity.CCZZMobj cczzMobj = new protocalEntity.CCZZMobj();
                cczzMobj.setType(TYPE_SET)
                        .setCenterCrd("123456")
                        .setFreq("10")
                        .setMode(MODE_RDRN);

                BLEManager.getInstance().sendCCZZM(cczzMobj);
                break;
            case R.id.btn_shm://（shm按钮）SOS设置和查询（CCSHM）
                protocalEntity.CCSHMobj ccshMobj = new protocalEntity.CCSHMobj();
                ccshMobj.setType(TYPE_SET)
                        .setCenterCrd("123456")
                        .setFreq("10")
                        .setMsg("你好");

                BLEManager.getInstance().sendCCSHM(ccshMobj);
                break;
            case R.id.btn_login://（初始化按钮）
                //开启一个线程发送初始化的设置信息
                new Thread(new Runnable() {
                    public void run() {
                        //终端自检输出设置(CCZDC)，freq 参数是输出频度  ‘0’-‘10’ 单位：秒，为‘0’时表示单次输出
                        BLEManager.getInstance().sendCCZDC("4");
                        try {
                            Thread.sleep(500L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*终端密码设置/登录(CCPWD)  参数type是  类型 ‘1’:密码设置 ‘2’:登录
                        *                        参数pwd是   密码，六位数字或者英文字母
                        * */
                        BLEManager.getInstance().sendCCPWD("2", "000000");
                    }
                }).start();

                break;
            case R.id.btn_bsi://（4.0功率按钮） 发送功率检测指令（4.0）
                                             //参数 freq   功率信息输出频度
                BLEManager.getInstance().sendGLJC(5);
                break;
            case R.id.btn_ici: /* （4.0IC 按钮） 发送IC检测指令（4.0）
                                                参数frameNum 帧号 */
                BLEManager.getInstance().sendICJC(12);
                break;
            case R.id.btn_txa://(4.0通信按钮) 发送通信申请信息（4.0）
                              /* @param revIc
                               *         接收卡号
                               * @param msgType
                               *         发送信息类型
                               * @param msg
                               *         发送内容  */
                /*try {
                    BLEManager.getInstance().bdbleHandler.send("$CCRMO,,3,*4F".getBytes("gbk"));//关二代
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                BLEManager.getInstance().sendTXSQ(248112, MSG_TYPE_MIX, "你好ABC123");
                //BLEManager.getInstance().sendTXSQ(248112,MSG_TYPE_CHINESE,"你好");
                break;
            case R.id.btn_dwa: //(4.0定位按钮)  发送定位申请指令（4.0）
                BLEManager.getInstance().sendDWSQ();
                break;
            case R.id.btn_send: //(发送按钮)
                try {
                    //获取编辑框内数据传入发送函数
                    BLEManager.getInstance().bdbleHandler.send(et_sendText.getText().toString().getBytes("gbk"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_cksc://（4.0串口输出按钮） 发送串口输出指令（4.0）
                                                    /* @param baudRate
                                                     设置传输速率（波特率）*/
                BLEManager.getInstance().sendCKSC(protocal4_0.BAUDRATE_115200);
                break;
            case R.id.btn_xtzj://（4.0系统自检按钮） 发送系统自检指令  freq 设置自检频率
                BLEManager.getInstance().sendXTZJ(60);
                break;
            case R.id.btn_sjsc: //（4.0时间输出按钮）
                BLEManager.getInstance().sendSJSC(5);
                break;
            case R.id.btn_bbdq: //（4.0 版本读取按钮）
                BLEManager.getInstance().sendBBDQ();
                break;
            case R.id.btn_setSOS: //（盒子掉电保存SOS卡号） 参数1 freq 设置频率   参数2 输入的卡号
                sosNum = et_setSOS.getText().toString(); //获取输入文本框信息
                BLEManager.getInstance().sendSET("60", sosNum);
                break;
            case R.id.btn_back: //(返回按钮)
                onBackPressed();
                break;
            case R.id.btn_ici_21: //（2.1 IC 按钮） 发送IC检测申请（2.1）
                BLEManager.getInstance().sendCCICA();
                break;
            case R.id.btn_bsi_21: //（2.1功率按钮）  发送功率检测申请（2.1）
                /*try {
                    BLEManager.getInstance().bdbleHandler.send("$CCRMO,,4,*48".getBytes("gbk"));//开二代
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                BLEManager.getInstance().sendCCRMO(2, 3);
                break;
            case R.id.btn_txa_21: //（2.1通信按钮） 发送通信申请（2.1）
                //BLEManager.getInstance().sendCCTXA("0141814");
                BLEManager.getInstance().sendCCTXA("0141814", 1, MSG_TYPE_CHINESE, "你好");
//                BLEManager.getInstance().sendBD2NET("209704","15060877825","你好");
//                BLEManager.getInstance().sendBD2NET_POS("123456","15060877825","你好",rmcTestMsg);
//                BLEManager.getInstance().sendBD2PHONE("123456","15060877825","你好");
//                BLEManager.getInstance().sendBD2PHONE_POS("123456","15060877825","你好",rmcTestMsg);
                break;
            case R.id.btn_bd_to_net: //（bd_to_net按钮） 北斗网到公网
                BLEManager.getInstance().sendBD2NET("209704", "15060877825", "你好");
                break;
            case R.id.btn_bd_to_net_bypos: //(bd_to_net_bypos按钮)  北斗网到公网(带位置)
                BLEManager.getInstance().sendBD2NET_POS("209704", "15060877825", "你好", rmcTestMsg);
                break;
            case R.id.btn_bd_to_phone: //（bd_to_phone 按钮） 北斗网到手机短信
                BLEManager.getInstance().sendBD2PHONE("209704", "15060877825", "你好");
                break;
            case R.id.btn_bd_to_phone_bypos: //（bd_to_phone_bypos 按钮） 北斗网到手机短信（带位置)
                BLEManager.getInstance().sendBD2PHONE_POS("209704", "15060877825", "你好", rmcTestMsg);
                break;
            case R.id.btn_bd_pos: //（位置上报按钮） 北斗网app位置上报
                List<RMCMsg> list = new ArrayList<>();
                list.add(rmcTestMsg);
                list.add(rmcTestMsg);
                list.add(rmcTestMsg);
                list.add(rmcTestMsg);
                list.add(rmcTestMsg);
                BLEManager.getInstance().sendAppPos("209704", list);
                break;
            case R.id.btn_dwa_21: //（2.1定位按钮）
                BLEManager.getInstance().sendCCDWA("0141814");
                break;
            case R.id.btn_disconnectDevice: //（断开连接按钮）
                BLEManager.getInstance().disConnectBle();
                break;
            case R.id.btn_startScan: //（开始扫描按钮）
                mbleScanManager.scanDevice(true);
                break;
            case R.id.btn_stopScan: //（停止扫描按钮）
                mbleScanManager.scanDevice(false);
                break;
            case R.id.btn_sos_open: //（SOS开按钮）  开启与关闭SOS功能
                BLEManager.getInstance().sendSOS(sosNum, true, "请求支援！");
                break;
            case R.id.btn_sos_close: //（SOS关按钮）  开启与关闭SOS功能
                BLEManager.getInstance().sendSOS(sosNum, false, "请求支援！");
                break;
            case R.id.btn_dljc://（电量按钮） 开启电量检测功能
                BLEManager.getInstance().sendDLJC();
                break;
            case R.id.btn_ztjc: //（电量检测初始化按钮）
                BLEManager.getInstance().sendINIT();
                break;
            default:
                break;
        }
    }

    /**
     * 接收到盒子SOS启动信息
     *
     */
    @Override
    public void onBDQDX(BDQDX data) {
        //Log.v("FDBDTestLog", "BDQDX" + data.getSosStatus());
        Toast.makeText(this, "BDQDX" + data.getSosStatus(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 接收到盒子极限追踪

     */
    @Override
    public void onBDQZX(BDQZX data) {
        Log.v("FDBDTestLog", "BDQZX" + data.getZzmStatus());

    }

    @Override
    public void onConnectBleSuccess() {
        Log.v("FDBDTestLog", "连接成功！！！");
    }

    @Override
    public void onDisConnectBleSuccess() {
        Log.v("FDBDTestLog", "连接断开！！！");
    }


    /**
     * 接收到盒子的工作模式信息
     * @param paraMsg
     */
    @Override
    public void onBDMSH(BDMSH paraMsg) {
        Log.v("FDBDTestLog", "接收到工作模式信息" + paraMsg.getWorkMode());
    }


    /**
     * 接收到盒子OK启动信息

     */
    @Override
    public void onBDQKX(BDQKX data) {
        Log.v("FDBDTestLog", "BDQKX" + data.getOkStatus());
    }

    /**
     * 接收到ZDX
     * 修改日期 2019/11/15
     */
    @Override
    public void onBDZDX(final protocalEntity.BDZDXobj paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_zdx.setText("中心卡：" + paraMsg.getBdCrd() + " 电量：" + paraMsg.getBattery() + "% 功率："
                        + paraMsg.getPower()[0] + "-"
                        + paraMsg.getPower()[1] + "-"
                        + paraMsg.getPower()[2] + "-"
                        + paraMsg.getPower()[3] + "-"
                        + paraMsg.getPower()[4] + "-"
                        + paraMsg.getPower()[5] + "-"
                        + paraMsg.getPower()[6] + "-"
                        + paraMsg.getPower()[7] + "-"
                        + paraMsg.getPower()[8] + "-"
                        + paraMsg.getPower()[9]
                        + " 频度：" + paraMsg.getFreq()
                        + "s 等级：" + paraMsg.getLevel()
                        + " 通讯长度：" + paraMsg.getMaxBit() + "bit"
                );
            }
        });
    }

    /**
     * 接收到PWX
     * 修改日期 2019/11/15
     */
    @Override
    public void onBDPWX(final protocalEntity.BDPWXobj paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_pwx.setText("状态：" + paraMsg.getStatus() + " 类型：" + paraMsg.getType());
            }
        });
    }

    /**
     * 接收到OKX
     * 修改日期 2019/11/15
     */
    @Override
    public void onBDOKX(final protocalEntity.CCOKSobj paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_okx.setText("中心卡：" + paraMsg.getCenterCrd() + " 信息：" + paraMsg.getMsg());
            }
        });
    }

    /**
     * 接收到ZZX
     * 修改日期 2019/11/15
     */
    @Override
    public void onBDZZX(final protocalEntity.CCZZMobj paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_zzx.setText("中心卡：" + paraMsg.getCenterCrd() + " 频度：" + paraMsg.getFreq() + " 模式：" + paraMsg.getMode());
            }
        });
    }

    /**
     * 接收到HMX
     * 修改日期 2019/11/15
     */
    @Override
    public void onBDHMX(final protocalEntity.CCSHMobj paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_hmx.setText("中心卡：" + paraMsg.getCenterCrd() + " 频度：" + paraMsg.getFreq());
            }
        });
    }


    /**
     * 接收通信信息接口
     *

     *         通信信息解析对象
     */
    @Override
    public void onBDTXR(final TXRMsg txrMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_txr_21.setText("通信类型：" + txrMsg.getMsgType() + "  内容：" + txrMsg.getMsgContent());
            }
        });
    }

    /**
     * 接收功率信息
     *

     *         功率信息解析对象
     */

    @Override
    public void onBDBSI(final BSIMsg bsiMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_bsi_21.setText("功率值：" + bsiMsg.getBeamPow1() + "-" + bsiMsg.getBeamPow2() + "-" + bsiMsg.getBeamPow3() + "-"
                        + bsiMsg.getBeamPow4() + "-" + bsiMsg.getBeamPow5() + "-" + bsiMsg.getBeamPow6() + "-"
                        + bsiMsg.getBeamPow7() + "-" + bsiMsg.getBeamPow8() + "-" + bsiMsg.getBeamPow9() + "-"
                        + bsiMsg.getBeamPow10());
            }
        });
    }


    /**
     * 接收IC信息
     *

     *         IC信息解析对象
     */
    @Override
    public void onBDICI(final ICIMsg iciMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_ici_21.setText("卡号：" + iciMsg.getICnum() + "  通信等级：" + iciMsg.getLevel());
            }
        });
    }


    /**
     * 接收反馈信息
     *

     *         反馈信息解析对象
     */

    @Override
    public void onBDFKI(final FKIMsg fkiMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_fki_21.setText("反馈指令名称：" + fkiMsg.getComStr() + "  指令执行情况：" + (fkiMsg.getComExeSituation() == true ? "Y" : "N")
                        + "  频度设置指示：" + (fkiMsg.getFreqSetIdication() == true ? "Y" : "N"));
            }
        });
    }

    /**
     * 接收定位信息
     *

     *         定位信息解析对象
     */
    @Override
    public void onBDDWR(final DWRMsg dwrMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_dwr_21.setText("经度：" + dwrMsg.getLongiDeg() + "  纬度：" + dwrMsg.getLatiDeg() + "  高度：" + dwrMsg.getGeoHig() + " 精度指示：" + dwrMsg.getPrecisionInd());
            }
        });
    }

    /**
     * 接收错误信息，即不在协议范围内的信息
     */
    @Override
    public void onBDERR() {

    }



    /**
     * 接收RMC信息
     *

     *         RMC信息解析对象
     */
    @Override
    public void onRMC(final RMCMsg rmcMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_rmc_21.setText("经度：" + rmcMsg.getLongiDeg() + "  纬度：" + rmcMsg.getLatiDeg() + "  北京时间：" + rmcMsg.getBJTime() + "  速度：" + rmcMsg.getSpeed()
                        + "  航向：" + rmcMsg.getDirection() + "  定位有效性：" + rmcMsg.getDWstaus());
            }
        });
    }


    /**
     * 接收GGA信息
     *
     * @param paraMsg
     *         GGA信息解析对象
     */
    @Override
    public void onGGA(final GGAMsg paraMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_gga_21.setText("经度：" + paraMsg.getLongiDeg() + "  纬度：" + paraMsg.getLatiDeg() + "  UTC时间：" + paraMsg.getUtcTime() + "是/否有效：" + paraMsg.getStatus()
                        + "  卫星数：" + paraMsg.getSatelitesNum() + "  天线高：" + paraMsg.getAntannaHeight());
            }
        });
    }


    /**
     * 接收BDHZ信息
     *

     *         BDHZ信息解析对象
     */
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


    /**
     * 接收--WAA信息方法（2.1协议）
     * 修改日期 2017/5/25
     *

     *        WAA信息对象
     */
    @Override
    public void onWAA(WAAMsg waaMsg) {

    }

    /**
     * 接收BBXX信息（4.0）
     *
     * @param paraMsg
     *         版本信息对象
     */

    @Override
    public void onBBXX(BBXXMsg paraMsg) {

    }


    /**
     * 接收DWXX信息（4.0）
     *
     * @param paraMsg
     *         定位信息对象
     */
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


    /**
     * 接收FKXX信息（4.0）
     *
     * @param paraMsg
     *         反馈信息对象
     */

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

    /**
     * 接收GLZK信息（4.0）
     *
     * @param paraMsg
     *         功率检测对象
     */

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


    /**
     * 接收ICXX信息（4.0）
     *
     * @param paraMsg
     *         IC信息对象
     */
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


    /**
     * 接收SJXX信息（4.0）
     *
     * @param paraMsg
     *         时间信息对象
     */

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

    /**
     * 接收TXXX信息（4.0）
     *
     * @param paraMsg
     *         通信信息对象
     */
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


    /**
     * 接收ZJXX信息（4.0）
     *
     * @param paraMsg
     *         自检信息对象
     */
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
