package com.example.readcsv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;
import com.wafflecopter.multicontactpicker.RxContacts.PhoneNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class delaynav extends AppCompatActivity {

    String Mytag="MySMS";

    int hr,min;

    int j=0;
    static String numb;
  //  Button buttonstop;

    ArrayList<PhoneNumber> phoneNumbers;
    private static final int CONTACT_PICKER_REQUEST = 991;
    private ArrayList<ContactResult> results = new ArrayList<>();
 //   EditText editTextnum,
       EditText     editTextmsg;

    static int i=0;

 //   EditText Allnums;
    Handler handler;

 //   EditText mydelaytime;



 //   ListView mylist;

    Button buttonsend;

//    Button buttonaddnum;

    ArrayList<String> Allnos;

    ArrayAdapter<String> arrayAdapter;

    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delaynav);

        getSupportActionBar().setTitle("Bulk SMS");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonsend=findViewById(R.id.send);


     //   buttonstop=findViewById(R.id.Stop);


//    buttonstart=findViewById(R.id.start);
        //  buttonstop=findViewById(R.id.stop);
//        mylist=findViewById(R.id.list);
      //  Allnos=new ArrayList<>();
    //    arrayAdapter=new ArrayAdapter<String>(delaynav.this,R.layout.support_simple_spinner_dropdown_item,Allnos);
  //      mylist.setAdapter(arrayAdapter);

     //   mydelaytime=findViewById(R.id.delaytime);

        handler=new Handler();

    //    editTextnum=findViewById(R.id.num);
        editTextmsg=findViewById(R.id.id_msg);

        //   Allnums=findViewById(R.id.id_nums);
  //      buttonaddnum=findViewById(R.id.addnumber);

        runnable=new Runnable() {
            @Override
            public void run() {


/*
                String d=mydelaytime.getText().toString();

                int delay=Integer.parseInt(d);

                String message=editTextmsg.getText().toString();

                final int del=delay*1000;



                if (i<Allnos.size()){
                    if (Allnos.get(i).isEmpty()||message.isEmpty()){




                        Toast.makeText(delaynav.this, "Please fill Number and Message field and delay time", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(Allnos.get(i),null,message,null,null);

                        Toast.makeText(delaynav.this, ""+Allnos.get(i)+""+message, Toast.LENGTH_SHORT).show();

                        Log.d(Mytag,"This is SMS and text "+Allnos.get(i)+""+message);
                    }


                    handler.postDelayed(this,del);
                    i++;
                }
                */



                String message=editTextmsg.getText().toString();

                if(i<results.size()){

                    phoneNumbers= (ArrayList<PhoneNumber>) results.get(i).getPhoneNumbers();
                    String num=phoneNumbers.get(0).getNumber();
                    Toast.makeText(delaynav.this, "first if is called", Toast.LENGTH_SHORT).show();

                    if(message.isEmpty()||num.isEmpty()){

                        Toast.makeText(delaynav.this, "Please select  Numbers and Message field and delay time again ...", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        SmsManager smsManager=SmsManager.getDefault();

                        smsManager.sendTextMessage(num,null,message,null,null);

                        Toast.makeText(delaynav.this, ""+num+" "+message, Toast.LENGTH_SHORT).show();

                        Log.d("----",""+num);

                    }


                    handler.postDelayed(this,2000);
                    i++;

                }

            }
        };
/*
        buttonaddnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numb=editTextnum.getText().toString();

                Allnos.add(numb);
                arrayAdapter.notifyDataSetChanged();

            }
        });
*/
        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permission();
            }
        });

    }

    private void permission() {

        if(ContextCompat.checkSelfPermission(delaynav.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();

            //       Getdata();

            runnable.run();
        }
        else {

            ActivityCompat.requestPermissions(delaynav.this,new String[]{Manifest.permission.SEND_SMS},1);

            Toast.makeText(delaynav.this,"Permission not Granted",Toast.LENGTH_SHORT).show();

        }
    }

    public void picknumber(View view) {
/*
        Intent pick_intent = new Intent(Intent.ACTION_PICK);
        // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
        pick_intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pick_intent, 1);
*/



        if (ContextCompat.checkSelfPermission(delaynav.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            new MultiContactPicker.Builder(delaynav.this) //Activity/fragment context
                    .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                    .hideScrollbar(false) //Optional - default: false
                    .showTrack(true) //Optional - default: true
                    .searchIconColor(Color.WHITE) //Optional - default: White
                    .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                    .handleColor(ContextCompat.getColor(delaynav.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                    .bubbleColor(ContextCompat.getColor(delaynav.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                    .bubbleTextColor(Color.WHITE) //Optional - default: White
                    .setTitleText("Select Contacts") //Optional - only use if required
                    .setSelectedContacts(results) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                    .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                    .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                    .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out) //Optional - default: No animation overrides
                    .showPickerForResult(CONTACT_PICKER_REQUEST);
        }else{
            Toast.makeText(delaynav.this, "Remember to go into settings and enable the contacts permission.", Toast.LENGTH_LONG).show();
        }
    }

    /*
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

//        editTextnum.setText(number.replaceAll("\\s",""));
    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            results.addAll(MultiContactPicker.obtainResult(data));
            if(results.size() > 0) {
                Log.d("MyTag", results.get(0).getDisplayName());
            }
        } else if(resultCode == RESULT_CANCELED){
            System.out.println("User closed the picker without selecting items.");
        }
    }

    }

