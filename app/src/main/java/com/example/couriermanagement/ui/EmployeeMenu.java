package com.example.couriermanagement.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class EmployeeMenu extends AppCompatActivity{
    private static String[] info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_menu);
        initButtonThread thread = new initButtonThread();
        thread.start();
        info = getIntent().getStringArrayExtra("details");
        while (thread.isAlive());
    }

    private class initButtonThread extends Thread{
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            Button logout = findViewById(R.id.logout_button);
            Button book = findViewById(R.id.book_button);
            Button createEmp = findViewById(R.id.track_package_bt);
            Button chPassword = findViewById(R.id.customer_feedback_bt);
            TextView staffID = findViewById(R.id.disp_staff_id);
            Button log_bt = findViewById(R.id.cost_est_bt);
            staffID.setText("Staff ID: "+info[0]+"   OfficeID: "+info[1]);
            logout.setOnClickListener(v -> finish());
            book.setOnClickListener(v -> {
                BookCourier.setEmpId(info[0]);
                BookCourier.setOfficeID(info[1]);
                Intent intent = new Intent(EmployeeMenu.this,BookCourier.class);
                startActivity(intent);
            });
            createEmp.setOnClickListener(v -> {
                CreateEmp.setOffice_id(info[1]);
                Intent intent = new Intent(EmployeeMenu.this,CreateEmp.class);
                startActivity(intent);
            });
            chPassword.setOnClickListener(v->{
                ChangePassword.setOldPassword(info[4]);
                ChangePassword.setEmpid(info[0]);
                Intent intent = new Intent(EmployeeMenu.this,ChangePassword.class);
                startActivity(intent);
            });
            log_bt.setOnClickListener(v->{
                Logs.setOfficeID(info[1]);
                Intent intent = new Intent(EmployeeMenu.this, Logs.class);
                startActivity(intent);
            });
        }
    }
}
