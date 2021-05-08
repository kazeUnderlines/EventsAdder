package com.example.eventsadder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView lv;
    SwipeRefreshLayout srl;
    public static ArrayList<String> ev = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        lv = findViewById(R.id.list);
        srl = findViewById(R.id.swiperefresh);


        if(hasPermission(this, Manifest.permission.READ_CALENDAR)&&hasPermission(this,Manifest.permission.WRITE_CALENDAR))
        {
            readEvent();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR},101);
        }



        fab.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(),DialogActivity.class);
            startActivity(intent);

        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
                recreate();
                }
        });

    }
    private boolean hasPermission(Context context,String permission){
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        if(requestCode == 101){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                readEvent();
            }else{
                Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void readEvent(){
        ev = Utility.readCalendarEvent(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,android.R.id.text1,ev){
            @Override
            public View getView(int pos, View convert, ViewGroup group) {
                View v = super.getView(pos,convert,group);
                TextView t1 = v.findViewById(android.R.id.text1);
                TextView t2 = v.findViewById(android.R.id.text2);
                t1.setText(getItem(pos).substring(0,getItem(pos).indexOf(" ")));
                t2.setText(getItem(pos).substring(getItem(pos).indexOf(" ")+1));
                return v;
            }
        };
        lv.setAdapter(aa);
    }


}

