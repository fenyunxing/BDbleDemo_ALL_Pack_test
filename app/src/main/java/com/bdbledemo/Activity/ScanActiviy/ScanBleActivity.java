package com.bdbledemo.Activity.ScanActiviy;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bdbledemo.Adapter.ScanBleAdapter;
import com.bdbledemo.R;
import com.blesdk.BeidouBleSdk;
import com.blesdk.executor.handler.BLEManager;
import com.blesdk.executor.handler.BleScanManager;
import com.blesdk.impl.BleScanListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/17.
 */


public class ScanBleActivity extends AppCompatActivity {
    private ScanBleAdapter mscanAdapter;
    private ListView listViewContent;
    private List<BluetoothDevice> devices = new ArrayList();
    private BleScanManager mbleScanManager;
    private BeidouBleSdk beidouBleSdk;
    private boolean ifBleOpen = false;
    private Toolbar scanToolbar;

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.layout_activity_scan);
        viewsInit();
        dataInit();
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mbleScanManager.scanDevice(false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dataInit() {
        beidouBleSdk = BeidouBleSdk.getInstance(getApplicationContext());
        mscanAdapter = new ScanBleAdapter(this);
        listViewContent.setAdapter(this.mscanAdapter);
        listViewContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BLEManager.getInstance()
                        .setDeviceAddress(((BluetoothDevice) devices.get(position)).getAddress())
                        .setDeviceName(((BluetoothDevice) devices.get(position)).getName());
                //BLEManager.getInstance().connectBle(getApplicationContext());
                beidouBleSdk.startConnectBle();
                mbleScanManager.scanDevice(false);
                finish();
            }
        });
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
        if (ifBleOpen) {
            mbleScanManager.scanDevice(true);
        } else {
            return;
        }
    }

    protected void viewsInit() {
        listViewContent = (ListView) findViewById(R.id.device_list);
        scanToolbar = (Toolbar) findViewById(R.id.scantoolbar);
        scanToolbar.setTitle("");
        scanToolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(scanToolbar);
        scanToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

/*    @Override
    public void onBDHZ(BDHZMsg paraMsg) {
        if (paraMsg.getBDHZMsgStyle().equals(BDHZMsg.BDHZ_STYLE_SOS)) {
            Log.v("FDBDTestLog","scanActiity SOS");
            MainApp.getInstance().setSosCard(paraMsg.getSOSnumStr());
            //todo 开启新SOS功能
            MainApp.getInstance().setIsSOS(paraMsg.isIfOpenSos());
        }
    }*/
}
