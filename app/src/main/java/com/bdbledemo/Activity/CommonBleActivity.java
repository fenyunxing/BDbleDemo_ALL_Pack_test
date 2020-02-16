package com.bdbledemo.Activity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bdbledemo.Activity.ScanActiviy.ScanBleActivity;
import com.bdbledemo.Adapter.DataAdapter;
import com.bdbledemo.Adapter.ScanBleAdapter;
import com.bdbledemo.R;
import com.blesdk.executor.handler.BDBLEHandler;
import com.blesdk.executor.handler.BLEManager;
import com.blesdk.executor.handler.BleScanManager;
import com.blesdk.impl.AgentListener;
import com.blesdk.impl.BleScanListener;
import com.blesdk.tools.BDMethod;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Created by admin on 2017/6/9.
 */

public class CommonBleActivity extends AppCompatActivity implements View.OnClickListener, AgentListener {
    private BleScanManager mbleScanManager;
    private ScanBleAdapter mscanAdapter;
    private DataAdapter mDataAdapter;
    private ListView lv_Data;
    private Button btn_send;
    private TextView tv_connectedDevice;
    private EditText et_sendText;
    private Context mContext;
    private Toolbar toolbar;
    private CheckBox cbHexSend;
    private CheckBox cbHexRev;
    private boolean ifHexSend = false;
    private boolean ifHexRev = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_common);
        viewsInit();
        dataInit();
        BLEManager.getInstance().agentListeners.add(this);
        mContext = this;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                if(ifHexRev){
                    mDataAdapter.arr.add(BDMethod.castCalendarToString(Calendar.getInstance(), null) + "  " +
                            new String(intent.getStringExtra(BDBLEHandler.EXTRA_DATA_HEX)));
                }else {
                    mDataAdapter.arr.add(BDMethod.castCalendarToString(Calendar.getInstance(), null) + "  " +
                            new String(intent.getStringExtra(BDBLEHandler.EXTRA_DATA)));
                }
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
        intentFilter.addAction(BDBLEHandler.EXTRA_DATA_HEX);
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
        registerReceiver(mGattUpdateReceiver,
                makeGattUpdateIntentFilter());
    }

    protected void viewsInit() {
        lv_Data = (ListView) findViewById(R.id.lv_Data);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        tv_connectedDevice = (TextView) findViewById(R.id.tv_connectedDevice);
        et_sendText = (EditText) findViewById(R.id.et_sendText);
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
        cbHexSend = (CheckBox) findViewById(R.id.cb_hexsend);
        cbHexRev = (CheckBox) findViewById(R.id.cb_hexrev);
        cbHexSend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ifHexSend = isChecked == true?true:false;
            }
        });

        cbHexRev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ifHexRev = isChecked == true?true:false;
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
                        ScanBleActivity.class);
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
        mbleScanManager.bleScanInit(this, new BleScanListener() {
            @Override
            //设置扫描结果返回响应
            public void onScanResult(final BluetoothDevice bluetoothDevice, int rssi) {
            }

            @Override
            public void onScanFailed(int code) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                try {
                    if (BLEManager.getInstance().ConnectState) {
                        if(ifHexSend){
                            BLEManager.getInstance().bdbleHandler.send(BDMethod.castHexStringToBytes(et_sendText.getText().toString()));
                        }else {
                            BLEManager.getInstance().bdbleHandler.send(et_sendText.getText().toString().getBytes("gbk"));
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onData(String paraMsg) {

    }
}
