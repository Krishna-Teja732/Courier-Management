package com.example.couriermanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couriermanagement.db.query;

public class MainActivity extends AppCompatActivity {
    Button customer_button;
    Button emp_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        //query.initDB(MainActivity.this);
        iniButtonThread thread = new iniButtonThread();
        thread.start();
    }

    @Override
    protected void onDestroy() {
        query.close();
        super.onDestroy();
    }

    private class iniButtonThread extends Thread{
        public void run(){
            customer_button = findViewById(R.id.customer_button);
            emp_button = findViewById(R.id.emp_button);
            customer_button.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CustomerMenu.class);
                startActivity(intent);
            });
            emp_button.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EmployeeLogin.class);
                startActivity(intent);
            });
        }
    }
}