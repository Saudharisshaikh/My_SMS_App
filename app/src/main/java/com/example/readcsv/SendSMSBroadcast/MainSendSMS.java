package com.example.readcsv.SendSMSBroadcast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readcsv.R;

public class MainSendSMS extends AppCompatActivity {

    EditText edt_num;
    Button buttonsend;
    private  String Message="ActiveRingerMode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_send_sms);

        edt_num=findViewById(R.id.num);
        buttonsend=findViewById(R.id.sendmessage);

        getSupportActionBar().setTitle("On Ringer Mode");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number=edt_num.getText().toString();

                if (!number.isEmpty()){

                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(number,null,Message,null,null);

                    Toast.makeText(MainSendSMS.this, "send", Toast.LENGTH_SHORT).show();

                    if (ContextCompat.checkSelfPermission(MainSendSMS.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){


                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainSendSMS.this,Manifest.permission.SEND_SMS)){


                        }

                    }
                    else {

                        ActivityCompat.requestPermissions(MainSendSMS.this,new String[]{Manifest.permission.SEND_SMS},0);
                    }


                }

            }
        });

    }

    public void picknumber(View view) {

        Intent pick_intent = new Intent(Intent.ACTION_PICK);
        // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
        pick_intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pick_intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE },
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        int type = c.getInt(1);
                        showSelectedNumber(type, number);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    private void showSelectedNumber(int type, String number) {

        edt_num.setText(number.replaceAll("\\s",""));
    }

/*
    public void sendsms(View view) {

        String number=edt_num.getText().toString();

        if (!number.isEmpty()){

            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,Message,null,null);

            if (ContextCompat.checkSelfPermission(MainSendSMS.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){


                if (ActivityCompat.shouldShowRequestPermissionRationale(MainSendSMS.this,Manifest.permission.SEND_SMS)){


                }

            }
            else {

                ActivityCompat.requestPermissions(MainSendSMS.this,new String[]{Manifest.permission.SEND_SMS},0);
            }


        }
    }
    */
}
