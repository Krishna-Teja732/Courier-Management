package com.example.couriermanagement.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.couriermanagement.db.query;

import java.util.ArrayList;

public class TrackPackage extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_init);
        Button back = findViewById(R.id.track_init_back_bt);
        back.setOnClickListener(v-> finish());
        EditText packageId = findViewById(R.id.package_id);
        Button submit = findViewById(R.id.track_submit_bt);
        submit.setOnClickListener(v->{
            if(!packageId.getText().toString().equals("")){
                ArrayList<String> res = query.customerQuery(TrackPackage.this,packageId.getText().toString());
                if(res!=null){
                    setContentView(R.layout.track_res);
                    packageId.setText("");
                    TextView name = findViewById(R.id.track_package_name);
                    TextView info = findViewById(R.id.track_package_info);
                    Button back_bt = findViewById(R.id.track_res_back_bt);
                    back_bt.setOnClickListener(v1 -> finish());
                    name.setText(res.get(0));
                    info.setText("Package id: "+res.get(1)+"    Amt paid: "+res.get(2)+ "    status: --");
                }
                else{
                    Toast.makeText(TrackPackage.this,"incorrect package id",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(TrackPackage.this,"incorrect package id",Toast.LENGTH_LONG).show();
            }
        });
    }
}
