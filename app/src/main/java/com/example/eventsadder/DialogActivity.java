package com.example.eventsadder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class DialogActivity extends Activity {
    Spinner spnExcs;
    Spinner spnSets;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        spnExcs = findViewById(R.id.spnExcs);
        btnAdd = findViewById(R.id.btnAdd);
        spnSets = findViewById(R.id.spnSets);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, spnExcs.getSelectedItem().toString() + "  " + spnSets.getSelectedItem().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(DialogActivity.this, "No app could support this action", Toast.LENGTH_SHORT).show();
                }



                //Toast.makeText(getApplicationContext(),spn.getSelectedItem().toString()+edSets.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}