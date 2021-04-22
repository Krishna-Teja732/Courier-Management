package com.example.couriermanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class CustomerMenu extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_menu);
        Button trackBt=findViewById(R.id.track_package_bt);
        trackBt.setOnClickListener(v->{
            Intent intent = new Intent(CustomerMenu.this,TrackPackage.class);
            startActivity(intent);
        });
        Button back_button = findViewById(R.id.customer_back_buttom);
        back_button.setOnClickListener(v -> finish());
    }
}
