package com.bdbledemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bdbledemo.R;

/**
 * Created by admin on 2017/5/27.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_to_21;
    private Button btn_to_40;
    private Button btn_to_platform;
    private Button btn_to_21_40;
    private Button btn_to_common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        viewsInit();
    }

    protected void viewsInit() {

        btn_to_21 = (Button) findViewById(R.id.btn_to_21);
        btn_to_40 = (Button) findViewById(R.id.btn_to_40);
        btn_to_platform = (Button) findViewById(R.id.btn_to_platform);
        btn_to_21_40 = (Button) findViewById(R.id.btn_to_21_40);
        btn_to_common = (Button) findViewById(R.id.btn_to_common);
        btn_to_21.setOnClickListener(this);
        btn_to_40.setOnClickListener(this);
        btn_to_platform.setOnClickListener(this);
        btn_to_21_40.setOnClickListener(this);
        btn_to_common.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_21:
                Intent localIntent1 = new Intent(this,Protocal21Activity.class);
                startActivity(localIntent1);
                break;
            case R.id.btn_to_40:
                Intent localIntent2 = new Intent(this,Protocal40Activity.class);
                startActivity(localIntent2);
                break;
            case R.id.btn_to_21_40:
                Intent localIntent3 = new Intent(this,ProtocalUniActivity.class);
                startActivity(localIntent3);
                break;
            case R.id.btn_to_platform:
                Intent localIntent4 = new Intent(this,PlatformActivity.class);
                startActivity(localIntent4);
                break;
            case R.id.btn_to_common:
                Intent localIntent5 = new Intent(this,CommonBleActivity.class);
                startActivity(localIntent5);
                break;
            default:
                break;
        }
    }
}
