package com.example.readcsv;

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
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MultiRepeatSMS extends AppCompatActivity {


    EditText editTextnum,editTextrepeat,editTextmsg;
    Button buttonsend;

    private Handler handler;
    static int i=0;

    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_repeat_sms);

        getSupportActionBar().setTitle("Repeat SMS");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextnum=findViewById(R.id.num);
        editTextrepeat=findViewById(R.id.repeat);
        editTextmsg=findViewById(R.id.Message);
        buttonsend=findViewById(R.id.Send);

        handler=new Handler();

        runnable=new Runnable() {
            @Override
            public void run() {


                String msg=editTextmsg.getText().toString();
                String rep=editTextrepeat.getText().toString();
                int repno=Integer.parseInt(rep);
                String myno=editTextnum.getText().toString();

                if (i<repno){

                    if(myno.isEmpty()&&msg.isEmpty()){

                        Toast.makeText(MultiRepeatSMS.this, "Please fill the message and number field....", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(myno,null,msg,null,null);

                        Toast.makeText(MultiRepeatSMS.this, ""+myno+" "+msg, Toast.LENGTH_SHORT).show();
                        Log.d("New",myno+""+msg);
                    }

                    handler.postDelayed(this,5000);
                    i++;
                }
            }
        };

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permission();
            }
        });
    }

    private void permission() {

        if(ContextCompat.checkSelfPermission(MultiRepeatSMS.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();

            //       Getdata();

            runnable.run();
        }
        else {

            ActivityCompat.requestPermissions(MultiRepeatSMS.this,new String[]{Manifest.permission.SEND_SMS},1);

            Toast.makeText(MultiRepeatSMS.this,"Permission not Granted",Toast.LENGTH_SHORT).show();

        }
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

        editTextnum.setText(number.replaceAll("\\s",""));
    }
}
