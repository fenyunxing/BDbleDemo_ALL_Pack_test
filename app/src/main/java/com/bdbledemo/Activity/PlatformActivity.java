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
import android.widget.ListView;
import android.widget.TextView;

import com.bdbledemo.Activity.ScanActiviy.ScanBleActivityPlatform;
import com.bdbledemo.Adapter.DataAdapter;
import com.bdbledemo.Adapter.ScanBleAdapter;
import com.bdbledemo.R;
import com.bdbledemo.Service.PosUploadSevice;
import com.bdblesdkuni.BeidouBleSdk;
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
import com.bddomainuni.repository.protcals.protocalEntity;
import com.bddomainuni.repository.tools.BDMethod;
import com.bdplatformsdk.executor.BdPlatformManager;
import com.bdplatformsdk.impl.PlatformListener;
import com.bdplatformsdk.models.AppBd2BdByPosMsg;
import com.bdplatformsdk.models.AppBd2BdMsg;
import com.bdplatformsdk.models.AppLoginFKIMsg;
import com.bdplatformsdk.models.AppNet2BdByPosMsg;
import com.bdplatformsdk.models.AppNet2BdMsg;
import com.bdplatformsdk.models.AppSosFkiMsg;
import com.bdplatformsdk.models.CardtoCardMsg;
import com.bdplatformsdk.models.EmergencyAlarm;
import com.bdplatformsdk.models.EmergencyContactMsg;
import com.bdplatformsdk.models.EmergencyResponse;
import com.bdplatformsdk.models.LoginFKIMsg;
import com.bdplatformsdk.models.PhonetoCardMsg;
import com.bdplatformsdk.models.PositionUploadMsg;
import com.bdplatformsdk.models.TASKMsg;
import com.bdplatformsdk.models.TaskFKIMsg;
import com.bdplatformsdk.models.UsertoUserMsg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bdplatformsdk.repository.protcals.protocal_platform.TASK_COMPLETED;
import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_CARD_2_PHONE_STR;
import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_USER_2_PHONE_BYLOC_STR;
import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_USER_2_USER_BYLOC_STR;
import static com.bdplatformsdk.repository.protcals.protocal_platform.TYPE_USER_2_USER_STR;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_location_add;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_login;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_login_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_emergency_alarm_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_emergency_cancel_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_tocard;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_tophone;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_tophoneByLoc;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_tophoneByLoc_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_tophone_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_touser;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_touserByLoc;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_touserByLoc_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_msg_touser_multCrd;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_task_confirm;
import static com.bdplatformsdk.repository.protcals.protocal_platform.gen_task_report;

/**
 * Created by admin on 2017/5/23.
 */

public class PlatformActivity extends AppCompatActivity implements View.OnClickListener, AgentListener, PlatformListener {
    private DataAdapter mDataAdapter;
    private ListView lv_Data;
    private Button btn_back;
    private Button btn_login;
    private Button btn_taskConfirm;
    private Button btn_taskReport;
    private Button btn_position;
    private Button btn_c2c;
    private Button btn_u2u;
    private Button btn_c2p;
    private Button btn_u2u_byPos;
    private Button btn_c2p_byPos;
    private Button btn_login_multCrd;
    private Button btn_position_multCrd;
    private Button btn_u2u_multCrd;
    private Button btn_c2p_multCrd;
    private Button btn_u2u_byPos_multCrd;
    private Button btn_c2p_byPos_multCrd;
    private Button btn_emergency;
    private Button btn_emergency_multCrd;
    private Button btn_emergency_cancel;
    private Button btn_emergency_cancel_multCrd;
    private Button btn_position_multCrd_cancel;
    private Button btn_position_cancel;
    private TextView tv_login;
    private TextView tv_task;
    private TextView tv_taskFki;
    private TextView tv_c2p;
    private TextView tv_c2c;
    private TextView tv_u2u;
    private TextView tv_emergnComfir;

    private BleScanManager mbleScanManager;
    private ScanBleAdapter mscanAdapter;
    private ListView lv_scanDevice;
    private List<BluetoothDevice> devices = new ArrayList();
    private Button btn_startScan;
    private Button btn_stopScan;
    private Button btn_disconnectDevice;
    private TextView tv_connectedDevice;
    private boolean ifBleOpen;

    private Toolbar toolbar;
    private BeidouBleSdk beidouBleSdk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_platform);
        viewInit();
        dataInit();
        registerReceiver(mGattUpdateReceiver,
                makeGattUpdateIntentFilter());

    }

    @Override
    public void onResume() {
        super.onResume();
        BLEManager.getInstance().agentListeners.add(this);
        BdPlatformManager.getInstance().platformListener.add(this);
        bleInit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BLEManager.getInstance().agentListeners.remove(this);
        BdPlatformManager.getInstance().platformListener.remove(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final Intent serviceIntent3 = new Intent(this, PosUploadSevice.class);
        serviceIntent3.setAction("com.bdbledemo.POSITION_UPLOAD_SERVICE");
        stopService(serviceIntent3);
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BDBLEHandler.ACTION_GATT_CONNECTED.equals(action)) {
                tv_connectedDevice.setText(BLEManager.getInstance().getDeviceName());
                invalidateOptionsMenu();
            } else if (BDBLEHandler.ACTION_GATT_DISCONNECTED.equals(action)) {
                tv_connectedDevice.setText("");
                invalidateOptionsMenu();
            } else if (BDBLEHandler.ACTION_DATA_AVAILABLE.equals(action)) {
                mDataAdapter.arr.add(BDMethod.castCalendarToString(Calendar.getInstance(), null) + "  " + intent.getStringExtra(BDBLEHandler.EXTRA_DATA));
                mDataAdapter.notifyDataSetChanged();
                //接收数据状态
            } else if (BDBLEHandler.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //发现服务通道状态
            } else if (BDBLEHandler.ACTION_RSSI.equals(action)) {

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

    private RMCMsg bdrmcMessage = new RMCMsg();
    private DWRMsg bddwrMessage = new DWRMsg();

    public void dataInit() {
        beidouBleSdk = BeidouBleSdk.getInstance(getApplicationContext());
        //此处为仿真RMC与DWR数据，实际使用直接从RMC、DWR数据得到
        rmcMsg.setLongitude(119d);
        rmcMsg.setLatitude(26d);
        rmcMsg.SetDWstaus("A");

        bdrmcMessage.SetDWstaus("A");
        bdrmcMessage.setLatitude("26", "30", "15", "N");
        bdrmcMessage.setLongitude("119", "20", "15", "E");
        bdrmcMessage.setDirection("210");
        bdrmcMessage.setSpeed("110");

        bddwrMessage.setGeoHig("20", "m");

        mscanAdapter = new ScanBleAdapter(this);
        lv_scanDevice.setAdapter(mscanAdapter);
        lv_scanDevice.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BLEManager.getInstance()
                        .setDeviceAddress(((BluetoothDevice) devices.get(position)).getAddress())
                        .setDeviceName(((BluetoothDevice) devices.get(position)).getName());
                //if (BLEManager.getInstance().connectBle(getApplicationContext())) {
                beidouBleSdk.startConnectBle();
                    mbleScanManager.scanDevice(false);
            }
        });
        mPositionUploadMsg = new PositionUploadMsg.Builder()
                .setIfValid(bdrmcMessage.getDWstaus())
                .setLat(bdrmcMessage.getLatiH(), bdrmcMessage.getLatiM(), bdrmcMessage.getLatiS())
                .setLng(bdrmcMessage.getLongiH(), bdrmcMessage.getLongiM(), bdrmcMessage.getLongiS())
                .setLatOri(bdrmcMessage.getLatiOrin())
                .setLngOri(bdrmcMessage.getLongOrin())
                .setHigh(bddwrMessage.getGeoHig())
                .setOrin(bdrmcMessage.getDirection())
                .setSpeed(bdrmcMessage.getSpeed())
                .build();

        emergencyAlarm.setTypeAlarm(2)
                .setLng(119.339130)
                .setLat(26.189130)
                .setAltitude(20)
                .setSpeed(110)
                .setDateAndTime("180321153421")
                .setBattery(98)
                .setEmergencyMsg("求救，此地泥石流被困，等到救援");


        /*for (int i = 0; i < 4; i++) {
            testModel = new TestModel();
            testModel.setNum(i + 1);
            testList.add(testModel);
        }
        for (int i = 0; i < testList.size(); i++) {
            Log.v("FDBDTestLog", "列表" + i + ":" + testList.get(i).getNum());
        }
        testList.clear();
        testModel = new TestModel();
        testModel.setNum(5);
        testList.add(testModel);
        for (int i = 0; i < testList.size(); i++) {
            Log.v("FDBDTestLog", "新列表" + i + ":" + testList.get(i).getNum());
        }*/
    }
    /*List<TestModel> testList = new ArrayList<TestModel>(4);
    TestModel testModel ;
    public class TestModel {
        int num = 0;

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }*/

    public void viewInit() {
        lv_Data = (ListView) findViewById(R.id.lv_Data);
        mDataAdapter = new DataAdapter(this);
        lv_Data.setAdapter(mDataAdapter);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_taskConfirm = (Button) findViewById(R.id.btn_taskConfirm);
        btn_taskReport = (Button) findViewById(R.id.btn_taskReport);
        btn_position = (Button) findViewById(R.id.btn_position);
        btn_c2c = (Button) findViewById(R.id.btn_c2c);
        btn_u2u = (Button) findViewById(R.id.btn_u2u);
        btn_c2p = (Button) findViewById(R.id.btn_c2p);
        btn_u2u_byPos = (Button) findViewById(R.id.btn_u2u_byPos);
        btn_c2p_byPos = (Button) findViewById(R.id.btn_c2p_byPos);
        btn_login_multCrd = (Button) findViewById(R.id.btn_login_multCrd);
        btn_position_multCrd = (Button) findViewById(R.id.btn_position_multCrd);
        btn_u2u_multCrd = (Button) findViewById(R.id.btn_u2u_multCrd);
        btn_c2p_multCrd = (Button) findViewById(R.id.btn_c2p_multCrd);
        btn_u2u_byPos_multCrd = (Button) findViewById(R.id.btn_u2u_byPos_multCrd);
        btn_c2p_byPos_multCrd = (Button) findViewById(R.id.btn_c2p_byPos_multCrd);
        btn_emergency = (Button) findViewById(R.id.btn_emergency);
        btn_emergency_multCrd = (Button) findViewById(R.id.btn_emergency_multCrd);
        btn_emergency_cancel = (Button) findViewById(R.id.btn_ok);
        btn_emergency_cancel_multCrd = (Button) findViewById(R.id.btn_emergency_cancel_multCrd);
        btn_position_cancel = (Button) findViewById(R.id.btn_position_cancel);
        btn_position_multCrd_cancel = (Button) findViewById(R.id.btn_position_multCrd_cancel);
        btn_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_taskConfirm.setOnClickListener(this);
        btn_taskReport.setOnClickListener(this);
        btn_position.setOnClickListener(this);
        btn_c2c.setOnClickListener(this);
        btn_u2u.setOnClickListener(this);
        btn_c2p.setOnClickListener(this);
        btn_u2u_byPos.setOnClickListener(this);
        btn_c2p_byPos.setOnClickListener(this);
        btn_login_multCrd.setOnClickListener(this);
        btn_position_multCrd.setOnClickListener(this);
        btn_u2u_multCrd.setOnClickListener(this);
        btn_c2p_multCrd.setOnClickListener(this);
        btn_u2u_byPos_multCrd.setOnClickListener(this);
        btn_c2p_byPos_multCrd.setOnClickListener(this);
        btn_emergency.setOnClickListener(this);
        btn_emergency_multCrd.setOnClickListener(this);
        btn_emergency_cancel.setOnClickListener(this);
        btn_emergency_cancel_multCrd.setOnClickListener(this);
        btn_position_cancel.setOnClickListener(this);
        btn_position_multCrd_cancel.setOnClickListener(this);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_task = (TextView) findViewById(R.id.tv_task);
        tv_taskFki = (TextView) findViewById(R.id.tv_taskFki);
        tv_c2p = (TextView) findViewById(R.id.tv_c2p);
        tv_c2c = (TextView) findViewById(R.id.tv_c2c);
        tv_u2u = (TextView) findViewById(R.id.tv_u2u);
        tv_emergnComfir = (TextView) findViewById(R.id.tv_emergency);

        lv_scanDevice = (ListView) findViewById(R.id.lv_scanDevice);
        btn_startScan = (Button) findViewById(R.id.btn_startScan);
        btn_stopScan = (Button) findViewById(R.id.btn_stopScan);
        btn_disconnectDevice = (Button) findViewById(R.id.btn_disconnectDevice);
        btn_startScan.setOnClickListener(this);
        btn_stopScan.setOnClickListener(this);
        btn_disconnectDevice.setOnClickListener(this);
        tv_connectedDevice = (TextView) findViewById(R.id.tv_connectedDevice);

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
                        ScanBleActivityPlatform.class);
                startActivity(localIntent);
                return true;
            case R.id.ble_connect:
                BLEManager.getInstance().disConnectBle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mbleScanManager.scanDevice(false);
        BLEManager.getInstance().disConnectBle();
        finish();
    }

    String platfromCard = "0209704";
    String mainCard = "7654321";
    String revCard = "0141814";
    String userName = "15060877825";
    String password = "123456";
    String sendUser = "15000000001";
    String revUser = "13000000001";
    String taskNum = "123456789000";
    String revPhone = "13000000001";
    double lat = 26.189130;
    double lng = 119.339130;
    PositionUploadMsg mPositionUploadMsg;
    EmergencyAlarm emergencyAlarm = new EmergencyAlarm();
    RMCMsg rmcMsg = new RMCMsg();

    @Override
    public void onClick(View v) {
        final Intent serviceIntent = new Intent(this, PosUploadSevice.class);
        final Intent serviceIntent2 = new Intent(this, PosUploadSevice.class);
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                BLEManager.getInstance().send(
                        gen_login(platfromCard, userName, password, gen_location_add(true, true, true, lng, lat))
                );
                break;
            case R.id.btn_taskConfirm:
                BLEManager.getInstance().send(
                        gen_task_confirm(platfromCard, sendUser, taskNum)
                );
                break;
            case R.id.btn_taskReport:
                BLEManager.getInstance().send(
                        gen_task_report(platfromCard, sendUser, taskNum, TASK_COMPLETED, "任务备注")
                );
                break;
            case R.id.btn_position:
                serviceIntent.setAction("com.bdbledemo.POSITION_UPLOAD_SERVICE");
                startService(serviceIntent);
                /*BLEManager.getInstance().send(
                        gen_position_upload(platfromCard, sendUser, mPositionUploadMsg.getUploadMsg())
                );*/
                break;
            case R.id.btn_position_cancel:
                serviceIntent2.setAction("com.bdbledemo.POSITION_UPLOAD_SERVICE");
                stopService(serviceIntent2);
                break;
            case R.id.btn_position_multCrd_cancel:
                serviceIntent2.setAction("com.bdbledemo.POSITION_UPLOAD_SERVICE");
                stopService(serviceIntent2);
                break;
            case R.id.btn_c2c:
                BLEManager.getInstance().send(
                        gen_msg_tocard(revCard, "通信具体内容")
                );
                break;
            case R.id.btn_u2u:
                BLEManager.getInstance().send(
                        gen_msg_touser(platfromCard, sendUser, revUser, "通信具体内容")
                );
                break;
            case R.id.btn_c2p:
                BLEManager.getInstance().send(
                        gen_msg_tophone(platfromCard, sendUser, revPhone, "通信具体内容")
                );
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
            case R.id.btn_u2u_byPos:
                BLEManager.getInstance().send(
                        gen_msg_touserByLoc(platfromCard, sendUser, revUser, "你好"
                                , gen_location_add(true, true, true, lng, lat))
                );
                break;
            case R.id.btn_c2p_byPos:
                BLEManager.getInstance().send(
                        gen_msg_tophoneByLoc(platfromCard, sendUser, revPhone, "你好"
                                , gen_location_add(true, true, true, lng, lat))
                );
                break;
            case R.id.btn_login_multCrd:
                BLEManager.getInstance().send(
                        gen_login_multCrd(platfromCard, mainCard, userName, password,
                                gen_location_add(true, true, true, lng, lat))
                );
                break;
            case R.id.btn_position_multCrd:
                serviceIntent.setAction("com.bdbledemo.POSITION_UPLOAD_SERVICE");
                startService(serviceIntent);
                break;
            case R.id.btn_u2u_multCrd:
                BLEManager.getInstance().send(
                        gen_msg_touser_multCrd(platfromCard, mainCard, sendUser, revUser, "你好")
                );
                break;
            case R.id.btn_c2p_multCrd:
                BLEManager.getInstance().send(
                        gen_msg_tophone_multCrd(platfromCard, mainCard, sendUser, revPhone, "你好")
                );
                break;
            case R.id.btn_u2u_byPos_multCrd:
                BLEManager.getInstance().send(
                        gen_msg_touserByLoc_multCrd(platfromCard, mainCard, sendUser, revUser, "你好"
                                , gen_location_add(true, true, true, lng, lat))
                );
                break;
            case R.id.btn_c2p_byPos_multCrd:
                BLEManager.getInstance().send(
                        gen_msg_tophoneByLoc_multCrd(platfromCard, mainCard, sendUser, revPhone, "你好"
                                , gen_location_add(true, true, true, lng, lat))
                );
                break;
            case R.id.btn_emergency:
//                BLEManager.getInstance().send(
//                        gen_msg_emergency_alarm(platfromCard, sendUser, emergencyAlarm.getEmergencyMsgBytes())
//                );

                BLEManager.getInstance().sendAppSos("123456","15060877825","00","默认求救,SOS!",rmcMsg);
                break;
            case R.id.btn_emergency_multCrd:
                BLEManager.getInstance().send(
                        gen_msg_emergency_alarm_multCrd(platfromCard, mainCard, sendUser, emergencyAlarm.getEmergencyMsgBytes())
                );
                break;
            case R.id.btn_ok:
//                BLEManager.getInstance().send(
//                        gen_msg_emergency_cancel(platfromCard, sendUser, emergencyAlarm.getEmergencyMsgBytes())
//                );
                BLEManager.getInstance().sendAppOk("123456","一切平安！",rmcMsg);

                break;
            case R.id.btn_emergency_cancel_multCrd:
                BLEManager.getInstance().send(
                        gen_msg_emergency_cancel_multCrd(platfromCard, mainCard, sendUser, emergencyAlarm.getEmergencyMsgBytes())
                );
                break;
            default:
                break;
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
    public void onCardToCard(final CardtoCardMsg paramCardtoCardMsg) {
        Log.v("FDBDTestLog", "设备对设备信息onCardToCard");
        runOnUiThread(new Runnable() {
            public void run() {
                tv_c2c.setText("接收方卡号：" + paramCardtoCardMsg.getRevCard() + "  通信内容：" + paramCardtoCardMsg.getMsg());
            }
        });
    }

    @Override
    public void onUserToUser(final UsertoUserMsg paramUsertoUserMsg) {
        Log.v("FDBDTestLog", "用户对用户信息onUserToUser");
        if (paramUsertoUserMsg.getUserToUserType().equals(TYPE_USER_2_USER_STR)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_u2u.setText("多卡:" + paramUsertoUserMsg.isIfMultiCrd() + paramUsertoUserMsg.getMainCrd() + " (无位置) 发送方用户名：" + paramUsertoUserMsg.getSendUser() + "  接收方用户名:" + paramUsertoUserMsg.getRevUser()
                            + "  通信内容：" + paramUsertoUserMsg.getMsg());
                }
            });
        } else if (paramUsertoUserMsg.getUserToUserType().equals(TYPE_USER_2_USER_BYLOC_STR)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_u2u.setText("多卡:" + paramUsertoUserMsg.isIfMultiCrd() + paramUsertoUserMsg.getMainCrd() + " (带位置)发送方用户名：" + paramUsertoUserMsg.getSendUser() + "  接收方用户名:" + paramUsertoUserMsg.getRevUser()
                            + "  通信内容：" + paramUsertoUserMsg.getMsg() + " 位置信息："
                            + paramUsertoUserMsg.isLocValid() + " " + paramUsertoUserMsg.getLngOrin() + " "
                            + paramUsertoUserMsg.getLatOrin() + " " + paramUsertoUserMsg.getLng() + " "
                            + paramUsertoUserMsg.getLat());
                }
            });
        }

    }

    @Override
    public void onPhoneToCard(final PhonetoCardMsg paramPhonetoCardMsg) {
        Log.v("FDBDTestLog", "手机对设备信息onPhoneToCard");
        if (paramPhonetoCardMsg.getUserToPhoneType().equals(TYPE_CARD_2_PHONE_STR)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_c2p.setText("多卡:" + paramPhonetoCardMsg.isIfMultiCrd() + paramPhonetoCardMsg.getMainCrd() + " (无位置) 发送方用户名：" + "接收方用户名：" + paramPhonetoCardMsg.getUser() + "  发送方手机号：" + paramPhonetoCardMsg.getPhone()
                            + "  通信内容：" + paramPhonetoCardMsg.getMsg());
                }
            });
        } else if (paramPhonetoCardMsg.getUserToPhoneType().equals(TYPE_USER_2_PHONE_BYLOC_STR)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    tv_c2p.setText("多卡:" + paramPhonetoCardMsg.isIfMultiCrd() + paramPhonetoCardMsg.getMainCrd() + " (带位置)发送方用户名：" + paramPhonetoCardMsg.getUser() + "  发送方手机号：" + paramPhonetoCardMsg.getPhone()
                            + "  通信内容：" + paramPhonetoCardMsg.getMsg() + " 位置信息："
                            + paramPhonetoCardMsg.isLocValid() + " " + paramPhonetoCardMsg.getLngOrin() + " "
                            + paramPhonetoCardMsg.getLatOrin() + " " + paramPhonetoCardMsg.getLng() + " "
                            + paramPhonetoCardMsg.getLat());
                }
            });
        }
    }

    @Override
    public void onPlatTask(final TASKMsg paramTASKMsg) {
        Log.v("FDBDTestLog", "平台下发任务onPlatTask");
        runOnUiThread(new Runnable() {
            public void run() {
                tv_task.setText("接收任务用户：" + paramTASKMsg.getUser() + "  任务单号：" + paramTASKMsg.getTaskNum()
                        + "  任务内容：" + paramTASKMsg.getTaskMsg());
            }
        });
    }

    @Override
    public void onPlatFkiLogin(final LoginFKIMsg paramLoginFKIMsg) {
        Log.v("FDBDTestLog", "平台登入反馈onPlatFkiLogin");
        runOnUiThread(new Runnable() {
            public void run() {
                EmergencyContactMsg em = paramLoginFKIMsg.getEmergencyContactMsg();
                tv_login.setText("登入用户：" + paramLoginFKIMsg.getUser() + "  登入状态：" + paramLoginFKIMsg.getLoginState()
                        + "紧急联系人数：" + em.getContactNum() + "  联系人1：" + em.getContFrtName() + em.getContFrtPhone()
                        + "  联系人2：" + em.getContSecName() + em.getContSecPhone());
            }
        });
    }

    @Override
    public void onPlatTaskConfirm(final TaskFKIMsg paramTaskFKIMsg) {
        Log.v("FDBDTestLog", "平台确认接收任务状态onPlatTaskConfirm");
        runOnUiThread(new Runnable() {
            public void run() {
                tv_taskFki.setText("接收任务用户：" + paramTaskFKIMsg.getUser() + "  任务单号：" + paramTaskFKIMsg.getTaskNum()
                        + "  任务完成情况：" + paramTaskFKIMsg.getTaskState());
            }
        });
    }

    @Override
    public void onPlatEmergnConfirm(final EmergencyResponse emergencyResponse) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_emergnComfir.setText("紧急阶段：" + emergencyResponse.getEmergencyState() + "  类型：" + emergencyResponse.getEmergencyType()
                        + "  求救内容：" + emergencyResponse.getEmergencyMsg());
            }
        });
    }

    @Override
    public void onAppLoginFki(AppLoginFKIMsg appLoginFKIMsg) {
        Log.v("FDBDTestLog","收到onAppLoginFki = "+appLoginFKIMsg.getPhone());
    }

    @Override
    public void onAppNet2Bd(AppNet2BdMsg appNet2BdMsg) {
        Log.v("FDBDTestLog","收到onAppNet2Bd =" + appNet2BdMsg.getSenderPhone());
        Log.v("FDBDTestLog","收到onAppNet2Bd =" + appNet2BdMsg.getContent());
    }

    @Override
    public void onAppNet2BdByPos(AppNet2BdByPosMsg appNet2BdByPosMsg) {
        Log.v("FDBDTestLog","收到appNet2BdByPosMsg =" + appNet2BdByPosMsg.getSenderPhone());
        Log.v("FDBDTestLog","收到appNet2BdByPosMsg =" + appNet2BdByPosMsg.getContent());
        Log.v("FDBDTestLog","lng"+appNet2BdByPosMsg.getRmcMsg().getLongitude());
        Log.v("FDBDTestLog","lat"+appNet2BdByPosMsg.getRmcMsg().getLatitude());
        Log.v("FDBDTestLog","height"+appNet2BdByPosMsg.getRmcMsg().getHeight());

    }

    @Override
    public void onAppSosFki(AppSosFkiMsg appSosFkiMsg) {
        Log.v("FDBDTestLog","getSosCode="+appSosFkiMsg.getSosCode());
        Log.v("FDBDTestLog","getSosCode="+appSosFkiMsg.getPhone());
        Log.v("FDBDTestLog","getSosCode="+appSosFkiMsg.getContent());
    }

    @Override
    public void onAppBd2Bd(AppBd2BdMsg appBd2BdMsg) {

    }

    @Override
    public void onAppBd2BdByPos(AppBd2BdByPosMsg appBd2BdByPosMsg) {

    }

    @Override
    public void onConnectBleSuccess() {

    }

    @Override
    public void onDisConnectBleSuccess() {

    }

    @Override
    public void onBDMSH(BDMSH paraMsg) {

    }

    @Override
    public void onBDQKX(BDQKX paraMsg) {

    }

    @Override
    public void onBDQDX(BDQDX paraMsg) {

    }

    @Override
    public void onBDQZX(BDQZX paraMsg) {

    }

    @Override
    public void onBDZDX(protocalEntity.BDZDXobj paraMsg) {

    }

    @Override
    public void onBDPWX(protocalEntity.BDPWXobj paraMsg) {

    }

    @Override
    public void onBDOKX(protocalEntity.CCOKSobj paraMsg) {

    }

    @Override
    public void onBDZZX(protocalEntity.CCZZMobj paraMsg) {

    }

    @Override
    public void onBDHMX(protocalEntity.CCSHMobj paraMsg) {

    }

    @Override
    public void onBDTXR(final TXRMsg paramTXRmsg) {
        if (BdPlatformManager.getInstance().ifPlatformData(paramTXRmsg)) {
            BdPlatformManager.getInstance().receivedPlatform(paramTXRmsg);
        } else {
            //普通txr消息
        }
    }

    @Override
    public void onBDBSI(BSIMsg paramMsg) {

    }

    @Override
    public void onBDICI(ICIMsg paramMsg) {

    }

    @Override
    public void onBDFKI(FKIMsg paramMsg) {

    }

    @Override
    public void onBDDWR(DWRMsg paramMsg) {

    }

    @Override
    public void onBDERR() {

    }

    @Override
    public void onRMC(RMCMsg paraMsg) {

    }

    @Override
    public void onGGA(GGAMsg paraMsg) {

    }

    @Override
    public void onBDHZ(BDHZMsg paraMsg) {

    }

    @Override
    public void onWAA(WAAMsg paraMsg) {

    }

    @Override
    public void onBBXX(BBXXMsg bbxxMsg) {

    }

    @Override
    public void onDWXX(DWXXMsg dwxxMsg) {

    }

    @Override
    public void onFKXX(FKXXMsg fkxxMsg) {

    }

    @Override
    public void onGLZK(GLZKMsg glzkMsg) {

    }

    @Override
    public void onICXX(ICXXMsg icxxMsg) {

    }

    @Override
    public void onSJXX(SJXXMsg sjxxMsg) {

    }

    @Override
    public void onTXXX(TXXXMsg txxxMsg) {

    }

    @Override
    public void onZJXX(ZJXXMsg zjxxMsg) {

    }
}
