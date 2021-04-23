package com.example.couriermanagement.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couriermanagement.db.query;

public class CreateEmp extends AppCompatActivity {
    private int office_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_emp);
        office_id = Integer.parseInt(getIntent().getStringExtra("officeID"));
        initButtonThread thread = new initButtonThread();
        thread.start();
    }

    private class initButtonThread extends Thread{
        @SuppressLint("ResourceAsColor")
        @Override
        public void run() {
            Button createEmpBackBt = findViewById(R.id.create_emp_back_bt);
            Button createEmpSubmitBt = findViewById(R.id.create_emp_submit_bt);
            createEmpBackBt.setOnClickListener(v -> finish());
            createEmpSubmitBt.setOnClickListener(v -> {
                EditText name = findViewById(R.id.new_name);
                EditText rank = findViewById(R.id.rank);
                EditText salary = findViewById(R.id.salary);
                EditText newPassword = findViewById(R.id.new_password);
                EditText confirmPassword = findViewById(R.id.confirm_password);
                int flag=1;
                if(name.getText().toString().equals("")){
                    name.setBackgroundColor(Color.rgb(255,150,150));
                    flag=0;
                }
                if(rank.getText().toString().equals("")){
                    rank.setBackgroundColor(Color.rgb(255,150,150));
                    flag=0;
                }
                if(salary.getText().toString().equals("")){
                    salary.setBackgroundColor(Color.rgb(255,150,150));
                    flag=0;
                }
                if(newPassword.getText().toString().equals("")){
                    newPassword.setBackgroundColor(Color.rgb(255,150,150));
                    flag=0;
                }
                if(confirmPassword.getText().toString().equals("")){
                    confirmPassword.setBackgroundColor(Color.rgb(255,150,150));
                    flag=0;
                }
                if(!newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                    confirmPassword.setBackgroundColor(Color.RED);
                    newPassword.setBackgroundColor(Color.RED);
                    flag=0;
                }
                if(flag==1){
                    int id = query.EmpQuery.createEmp(CreateEmp.this,name.getText().toString(),
                            rank.getText().toString(),office_id,Integer.parseInt(salary.getText().toString())
                            ,confirmPassword.getText().toString());
                    Toast.makeText(CreateEmp.this,"created record, username: "+id,Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(CreateEmp.this,"Check info",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}