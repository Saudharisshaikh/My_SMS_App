package com.example.readcsv.ScheduleSMS;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.readcsv.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.zip.Inflater;

public class bottomsheethandle extends BottomSheetDialogFragment{

    private bottomsheetinterface bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.bottomsheetdialog,container,false);

        Button buttonmic=view.findViewById(R.id.mic);
        Button buttoncsv=view.findViewById(R.id.mycsv);
        final Button buttonschedule=view.findViewById(R.id.myschedule);
        Button buttondelay=view.findViewById(R.id.mydelay);

        buttonmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetListener.Mic();
            }
        });

        buttoncsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetListener.Csv();
            }
        });

        buttonschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetListener.Schedule();
            }
        });
        buttondelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetListener.delay();
            }
        });

        return  view;

    }
    public interface bottomsheetinterface{
         void Mic();
         void Csv();
         void Schedule();
         void delay();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        try {

            bottomSheetListener=(bottomsheetinterface)context;
        }
        catch (Exception e){


            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
