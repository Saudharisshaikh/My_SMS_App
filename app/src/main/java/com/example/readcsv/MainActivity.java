package com.example.readcsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextmsg;

    Handler handler;
    Runnable runnable;

    String message;
   Button button;
    public static int postion=0;

    public static  int ii=0;

    ColorDrawable [] Anmattioncolor={new ColorDrawable(Color.parseColor("#6bedfc")),
            new ColorDrawable(Color.parseColor("#c75cc1"))};
    ColorDrawable[] DefaultColors = {
            new ColorDrawable(Color.parseColor("#c75cc1")),
            new ColorDrawable(Color.parseColor("#01fee3")),
    };
    TransitionDrawable transitiondrawable1, transitiondrawable2;

    ArrayList<String> arrayList;

    ArrayList<String>passtoother;
    InputStream inputStream;

    ListView listView;

    public static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    arrayList=new ArrayList<>();
    listView=findViewById(R.id.list);
    passtoother=new ArrayList<>();

    editTextmsg=findViewById(R.id.id_message);


    button=findViewById(R.id.done);

    handler=new Handler();




    inputStream=getResources().openRawResource(R.raw.ncsv);
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        try {

            String csv;
            while ((csv=reader.readLine())!=null){


                arrayList.add(csv);
                try {

     //               Toast.makeText(this, ""+arrayList.get(i), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){

               //     Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

           // i++;
            }
        }

        catch (Exception e){

            Toast.makeText(this,"Exception "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        runnable=new Runnable() {
            @Override
            public void run() {


                String ms = editTextmsg.getText().toString();

                if (i < passtoother.size()) {


                    String numbers=passtoother.get(i).toString();

                    if (numbers.isEmpty() || ms.isEmpty()) {


                        Toast.makeText(MainActivity.this, "It is empty", Toast.LENGTH_SHORT).show();
                    } else {

                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(numbers,null,ms,null,null);

                        Toast.makeText(MainActivity.this, "" + numbers + "" + ms, Toast.LENGTH_SHORT).show();

                    }

                    handler.postDelayed(this, 10000);

                    i++;
                }


            }


        };



        ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           transitiondrawable1=new TransitionDrawable(Anmattioncolor);
           view.setBackground(transitiondrawable1);

                transitiondrawable1.startTransition(2200);

                transitiondrawable2 = new TransitionDrawable(DefaultColors);

                view.setBackground(transitiondrawable2);

                transitiondrawable2.startTransition(2200);


                postion=i;

                showdata(postion);


                Toast.makeText(MainActivity.this, ""+arrayList.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   permission();

//                message = editTextmsg.getText().toString();


                String ms = editTextmsg.getText().toString();

                if (ms.isEmpty()||passtoother.isEmpty()) {

                    Toast.makeText(MainActivity.this, "You don't select number in the list or message is empty.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        permission();

                    } catch (Exception e) {

                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                }
            }





        });


    }

  //  private void laymen() {



    private void permission() {

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();

        runnable.run();
        }
        else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},1);

            Toast.makeText(MainActivity.this,"Permission not Granted",Toast.LENGTH_SHORT).show();

        }

    }

    private void showdata(int postion) {

        int pos=postion;
        String val=arrayList.get(pos).toString();
        Toast.makeText(this, ""+val, Toast.LENGTH_SHORT).show();

        passtoother.add(val);


    }

}
/*
                Intent intent=new Intent(MainActivity.this,NavDrawer.class);
                intent.putStringArrayListExtra("mylist",passtoother);
                startActivity(intent);

*/