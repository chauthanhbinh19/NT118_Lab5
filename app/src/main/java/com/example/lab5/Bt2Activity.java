package com.example.lab5;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Bt2Activity extends AppCompatActivity {

    PowerStateChangeReceiver powerStateChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt2);

        powerStateChangeReceiver=new PowerStateChangeReceiver();

        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(powerStateChangeReceiver,filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký BroadcastReceiver khi Activity bị hủy
        unregisterReceiver(powerStateChangeReceiver);
    }
}