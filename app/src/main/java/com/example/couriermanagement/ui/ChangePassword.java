package com.example.couriermanagement.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couriermanagement.db.query;

public class ChangePassword extends AppCompatActivity {
    private static String oldPassword;
    private static String empid;

    public static void setEmpid(String empid) {
        ChangePassword.empid = empid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        initButtonThread thread = new initButtonThread();
        thread.start();
    }
    public static void setOldPassword(String oldPassword) {
        ChangePassword.oldPassword = oldPassword;
    }

    private class initButtonThread extends Thread{
        private EditText old,newp,confirm;
        @Override
        public void run() {
            old = findViewById(R.id.old_password);
            newp = findViewById(R.id.ch_new_password);
            confirm = findViewById(R.id.ch_confirm_password);
            Button submit = findViewById(R.id.ch_password_submit_bt);
            Button back = findViewById(R.id.ch_password_back_bt);
            back.setOnClickListener(v-> finish());
            submit.setOnClickListener(v->{
                if(check()){
                    query.EmpQuery.changePassword(ChangePassword.this,empid,newp.getText().toString());
                    Toast.makeText(ChangePassword.this,"Password changed successfully",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        private boolean check(){
            boolean flag=true;
            if(old.getText().toString().equals("")||!old.getText().toString().equals(oldPassword)){
                old.setBackgroundColor(Color.rgb(255,150,150));
                flag=false;
            }
            if(!newp.getText().toString().equals(confirm.getText().toString())||newp.getText().toString().equals("")){
                newp.setBackgroundColor(Color.rgb(255,150,150));
                confirm.setBackgroundColor(Color.rgb(255,150,150));
                flag=false;
            }
            if(newp.getText().toString().equals(oldPassword)){
                Toast.makeText(ChangePassword.this,"try a different password",Toast.LENGTH_LONG).show();
                flag=false;
            }
            return flag;
        }
    }
}