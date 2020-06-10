package com.example.readcsv;

import android.content.Intent;
import android.os.Bundle;

import com.example.readcsv.ScheduleSMS.MainScheduleActivity;
import com.example.readcsv.ScheduleSMS.bottomsheethandle;
import com.example.readcsv.SendSMSBroadcast.MainSendSMS;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;

public class NavDrawer extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener ,bottomsheethandle.bottomsheetinterface {



    Button button;

  //  TextView textViewsh;

    DrawerLayout drawer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        button=findViewById(R.id.mysheet);






    //            bottomsheethandle bottomsheethandle=new bottomsheethandle();
      //          bottomsheethandle.show(getSupportFragmentManager(),"My_Bottom_Sheet");




/*
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(NavDrawer.this);
                    bottomSheetDialog.setContentView(R.layout.bottomsheetdialog);
                    bottomSheetDialog.setCanceledOnTouchOutside(false);


                    TextView textViewmic = bottomSheetDialog.findViewById(R.id.mic);
                    TextView textViewcsv = bottomSheetDialog.findViewById(R.id.mycsv);
                    TextView textViewschedule = bottomSheetDialog.findViewById(R.id.myschedule);
                    TextView textViewdelay = bottomSheetDialog.findViewById(R.id.mydelay);
*/

        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My SMS APP");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            //    Intent intenth=new Intent(NavDrawer.this,Speechtotext.class);
            //   startActivity(intenth);

            drawer.closeDrawer(GravityCompat.START);

            // Handle the camera action
        } else if (id == R.id.voicesms) {

            Intent intenth = new Intent(NavDrawer.this, Speechtotext.class);
            startActivity(intenth);
        }


        else if (id == R.id.schedule) {

            Intent intenth = new Intent(NavDrawer.this, MainScheduleActivity.class);
           startActivity(intenth);

        }
        else if (id == R.id.delay) {

            Intent intenth = new Intent(NavDrawer.this, delaynav.class);
            startActivity(intenth);
        }

        else if (id==R.id.ringermode){

            Intent intent=new Intent(NavDrawer.this, MainSendSMS.class);
            startActivity(intent);
        }

        else if (id==R.id.RepeatSms){

            Intent intent=new Intent(NavDrawer.this, MultiRepeatSMS.class);
            startActivity(intent);
        }


        else if (id==R.id.help){

            Intent intent=new Intent(NavDrawer.this,help.class);
            startActivity(intent);

        }

/*
        else if (id == R.id.Repeat_SMS) {

            Intent intenth = new Intent(NavDrawer.this, RepeatSMS.class);
            startActivity(intenth);
        }

*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void Mic() {

        Intent intent=new Intent(NavDrawer.this,Speechtotext.class);
        startActivity(intent);
    }

    @Override
    public void Csv() {

        Intent intent=new Intent(NavDrawer.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void Schedule() {

        Intent intent=new Intent(NavDrawer.this,MainScheduleActivity.class);
        startActivity(intent);
    }

    @Override
    public void delay() {

        Intent intent=new Intent(NavDrawer.this,delaynav.class);
        startActivity(intent);
    }

    @Override
    public void ringermode() {

        Intent intent=new Intent(NavDrawer.this, MainSendSMS.class);
        startActivity(intent);
    }

    @Override
    public void repeatSMS() {

        Intent intent=new Intent(NavDrawer.this,MultiRepeatSMS.class);
        startActivity(intent);
    }

    public void myownbottomsheet(View view) {

/*
        bottomsheethandle bottomsheethandle=new bottomsheethandle();
                 bottomsheethandle.show(getSupportFragmentManager(),"My_Bottom_Sheet");

*/



    }

    public void voiceclick(View view) {

        Intent intent=new Intent(NavDrawer.this,Speechtotext.class);
        startActivity(intent);
    }

    public void scheduleclick(View view) {

        Intent intent=new Intent(NavDrawer.this, MainScheduleActivity.class);
        startActivity(intent);
    }

    public void ondelayclick(View view) {

        Intent intent=new Intent(NavDrawer.this,delaynav.class);
        startActivity(intent);
    }

    public void onringermodeon(View view) {

        Intent intent=new Intent(NavDrawer.this, MainSendSMS.class);
        startActivity(intent);
    }

    public void RepeatSMS(View view) {

        Intent intent=new Intent(NavDrawer.this,MultiRepeatSMS.class);
        startActivity(intent);
    }

    public void myGuide(View view) {

        startActivity(new Intent(NavDrawer.this,GuideActivity.class));
    }
}

