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
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Speechtotext extends AppCompatActivity {

    TextView textView;

    EditText editText;

    EditText editTextnum;

    Button buttonsend;

    public String   mymsg;

    public static   String  no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speechtotext);

        editText=findViewById(R.id.msg);

        editTextnum=findViewById(R.id.num);

        buttonsend=findViewById(R.id.btnsend);


        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mymsg=editText.getText().toString();

                if (mymsg.isEmpty()||no.isEmpty()){


                    Toast.makeText(Speechtotext.this, "Message or Number is Empty", Toast.LENGTH_SHORT).show();
                }

                else {


                    permission();
                }
            }
        });
    }

    private void permission() {


        if (ContextCompat.checkSelfPermission(Speechtotext.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            Getdata();
        }
        else {


            ActivityCompat.requestPermissions(Speechtotext.this,new String[]{Manifest.permission.SEND_SMS},
                    101);

            Toast.makeText(this, "Permission Not Granted ", Toast.LENGTH_SHORT).show();

        }
    }

    private void Getdata() {

        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(no,null,mymsg,null,null);

        Toast.makeText(this, "Sms Send Successfull", Toast.LENGTH_SHORT).show();
        Log.d("-------",mymsg+":"+no);


        no=null;
        mymsg=null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 1:

                if (resultCode==RESULT_OK&&null!=data){


                    ArrayList<String> res=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                   editText.setText(res.get(0));
                }

                break;

            case 2:


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
    }

    private void showSelectedNumber(int type, String number) {

        editTextnum.setText(number.replaceAll("\\s",""));

            no=number;
    }

    public void btnspeech(View view) {

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi Speak something");

        try {

            startActivityForResult(intent,1);
        }
        catch (Exception e){

            Toast.makeText(this, " "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void picknumber(View view) {

        Intent pick_intent = new Intent(Intent.ACTION_PICK);
        // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
        pick_intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pick_intent, 2);
    }

}
