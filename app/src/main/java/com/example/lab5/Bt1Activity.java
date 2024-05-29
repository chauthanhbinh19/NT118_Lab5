package com.example.lab5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Bt1Activity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public void processReceive(Context context, Intent intent){
        Toast.makeText(context,getString(R.string.you_have_a_message),
                Toast.LENGTH_LONG).show();
        TextView tvContext =findViewById(R.id.tv_content);

        final String SMS_EXTRA="pdus";
        Bundle bundle=intent.getExtras();
        Object[] messages=(Object[]) bundle.get(SMS_EXTRA);
        String sms="";
        SmsMessage smsMsg;
        for(int i=0;i<messages.length;i++){
            if(android.os.Build.VERSION.SDK_INT>=23){
                smsMsg=SmsMessage.createFromPdu((byte[]) messages[i],"");
            }else{
                smsMsg=SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            String msgBody=smsMsg.getMessageBody();
            String address=smsMsg.getDisplayOriginatingAddress();
            sms+=address +":\n"+msgBody +":\n";
        }
        tvContext.setText(sms);
    }
    private void initBroadcastReceiver(){
        filter=new IntentFilter
                ("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context,intent);
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt1);

        Button send=findViewById(R.id.send);
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Kiểm tra quyền SEND_SMS
//                if (ContextCompat.checkSelfPermission(Bt1Activity.this, Manifest.permission.SEND_SMS)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(Bt1Activity.this,
//                            new String[]{Manifest.permission.SEND_SMS},
//                            MY_PERMISSIONS_REQUEST_SEND_SMS);
//                } else {
//                    // Gửi tin nhắn nếu đã được cấp quyền
//                    sendSMS("0948199678","Hello World 1");
//                }
//            }
//        });
        initBroadcastReceiver();
    }
//    private void sendSMS(String phoneNumber, String message) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//            Toast.makeText(getApplicationContext(), "Tin nhắn đã được gửi.", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "Gửi tin nhắn thất bại, vui lòng thử lại sau.", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }
    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver==null){
            initBroadcastReceiver();
        }
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }
}