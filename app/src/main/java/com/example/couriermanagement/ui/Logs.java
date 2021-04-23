package com.example.couriermanagement.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couriermanagement.db.query;

import java.util.ArrayList;

public class Logs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        TextView text = findViewById(R.id.office_id);
        TextView noData = findViewById(R.id.no_data_found);
        String officeID = getIntent().getStringExtra("officeID");
        text.append(" "+ officeID);
        Button back = findViewById(R.id.logs_back_bt);
        back.setOnClickListener(v-> finish());
        ListView logs = findViewById( R.id.logs_list_view);
        ArrayList<String> values = query.EmpQuery.logs(Logs.this, officeID);
        if(values.isEmpty()){
            noData.setVisibility(View.VISIBLE);
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.log_item_layout,values);
            logs.setAdapter(adapter);
        }
    }
}