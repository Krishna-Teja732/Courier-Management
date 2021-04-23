package com.example.couriermanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.couriermanagement.db.query;

public class EmployeeLogin extends AppCompatActivity {
    private EditText username;
    private EditText password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);
        initButtonThread thread = new initButtonThread();
        thread.start();
        while (thread.isAlive());
    }

    private class initButtonThread extends Thread {
        public void run() {
            Button back_button = findViewById(R.id.back_button);
            Button submit = findViewById(R.id.track_submit_bt);
            username = findViewById(R.id.userName);
            password = findViewById(R.id.password);
            back_button.setOnClickListener(v -> finish());

            submit.setOnClickListener(v -> {
                String[] info = query.EmpQuery.Login(EmployeeLogin.this, username.getText().toString(), password.getText().toString());
                if (info != null) {
                    username.setText("");
                    password.setText("");
                    Intent intent = new Intent(EmployeeLogin.this, EmployeeMenu.class);
                    intent.putExtra("details",info);
                    startActivity(intent);
                } else {
                    Toast.makeText(EmployeeLogin.this, "incorret details", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

