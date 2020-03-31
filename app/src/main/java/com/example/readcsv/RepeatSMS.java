package com.example.readcsv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RepeatSMS extends AppCompatActivity {

    EditText editTextmsg,editTextno,editTextrepeat;
    Button mysendbutton;


    Handler handler;
    Runnable runnable;
    public static int i=0;
    public static String messages;
    public static int repeatno;

   public static String nos;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_sms);


        editTextmsg=findViewById(R.id.id_mymessage);
        editTextno=findViewById(R.id.num);
        editTextrepeat=findViewById(R.id.repeatno);
        mysendbutton=findViewById(R.id.sendbutton);

        mysendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{

                permission();
            }
                catch (Exception e){
                    Toast.makeText(RepeatSMS.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        runnable=new Runnable() {
            @Override
            public void run() {

                messages=editTextmsg.getText().toString();

         //       repeatno=Integer.parseInt(rep);

                SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
                nos=sharedPreferences.getString("No",null);

                SharedPreferences sharedPreferences1=getSharedPreferences("myshare",MODE_PRIVATE);
                String repno=sharedPreferences1.getString("repeat",null);
                repeatno=Integer.parseInt(repno);

                 final int dd=1000;

                if(i<repeatno){
                    if (nos.isEmpty()||messages.isEmpty()){

                        Toast.makeText(RepeatSMS.this, "Either No or message is empty ..", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(RepeatSMS.this, ""+nos+""+messages, Toast.LENGTH_SHORT).show();
                    }

  try {
    handler.postDelayed(this, 10000);
    i++;
}  catch (Exception e){

     Toast.makeText(RepeatSMS.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
}

                }



               /*
                 for (int j=0;j<repeatno;j++){

                     Toast.makeText(RepeatSMS.this, ""+nos+""+messages, Toast.LENGTH_SHORT).show();
                 }
            */
            }
        };
    }

    public  void permission() {

        String myrepno=editTextrepeat.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences("myshare",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("repeat",myrepno);

        if(ContextCompat.checkSelfPermission(RepeatSMS.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();


            try{
            runnable.run();
        }catch (Exception e){

                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else {

            ActivityCompat.requestPermissions(RepeatSMS.this,new String[]{Manifest.permission.SEND_SMS},1);

            Toast.makeText(RepeatSMS.this,"Permission not Granted",Toast.LENGTH_SHORT).show();
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

        editTextno.setText(number.replaceAll("\\s",""));

        String num=editTextno.getText().toString();

        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("No",num);


        editor.apply();


    }
}
