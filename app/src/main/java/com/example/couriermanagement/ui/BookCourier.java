package com.example.couriermanagement.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.couriermanagement.db.query;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BookCourier extends AppCompatActivity{
    private int OfficeID,EmpId;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_courier);
        initThread thread2 = new initThread();
        thread2.start();
        OfficeID = Integer.parseInt(getIntent().getStringExtra("officeID"));
        EmpId = Integer.parseInt(getIntent().getStringExtra("empID"));
        AutoCompleteTextView postal_code1 = findViewById(R.id.sender_postal_code);
        AutoCompleteTextView postal_code2 = findViewById(R.id.receiver_postal_code);
        String[] entries = getResources().getStringArray(R.array.postal_code);
        ArrayAdapter<String> items = new ArrayAdapter<>(BookCourier.this, R.layout.support_simple_spinner_dropdown_item,entries);
        postal_code1.setAdapter(items);
        postal_code2.setAdapter(items);
    }

    private class initThread extends Thread{
        private EditText cName,cAddress,cPin,cNum;
        private EditText rName,rAddress,rPin,rNum;
        private  EditText cost,weight;
        @Override
        public void run() {
            cName = findViewById(R.id.sender_name);
            cAddress = findViewById(R.id.sender_address);
            cPin = findViewById(R.id.sender_postal_code);
            cNum = findViewById(R.id.sender_phone_number);
            layout = findViewById(R.id.book_courier_layout);
            rName = findViewById(R.id.receiver_name);
            rAddress = findViewById(R.id.receiver_address);
            rPin = findViewById(R.id.receiver_postal_code);
            rNum = findViewById(R.id.receiver_phone_number);

            cost = findViewById(R.id.amount);
            weight = findViewById(R.id.weight);

            FloatingActionButton back = findViewById(R.id.book_back_button);
            back.setOnClickListener(v -> finish());
            Button submit = findViewById(R.id.book_courier_submit_bt);
            submit.setOnClickListener(v->{
                if(check()){
                    submit.setClickable(false);
                    ArrayList<String> customerInfo = new ArrayList<>();
                    customerInfo.add(cName.getText().toString());
                    customerInfo.add(cAddress.getText().toString());
                    customerInfo.add(cNum.getText().toString());
                    customerInfo.add(rName.getText().toString()+rAddress.getText().toString());
                    customerInfo.add(rNum.getText().toString());

                    ArrayList<Long> courierInfo = new ArrayList<>();
                    courierInfo.add(Long.parseLong(cost.getText().toString()));
                    courierInfo.add(Long.parseLong(cPin.getText().toString()));
                    courierInfo.add(Long.parseLong(rPin.getText().toString()));
                    courierInfo.add(Long.parseLong(weight.getText().toString()));

                    int id = query.EmpQuery.BookCourier(BookCourier.this,EmpId,OfficeID,customerInfo,courierInfo);
                    Snackbar.make(layout,"Courier id:"+id,Snackbar.LENGTH_INDEFINITE).setAction("DISMISS", v1 -> {
                        finish();
                        Toast.makeText(BookCourier.this,"booked successfully",Toast.LENGTH_LONG).show();
                    }).show();
                }
            });
        }

        private boolean check(){
            boolean flag=true;
            if(rAddress.getText().toString().equals("")){
                rAddress.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(rName.getText().toString().equals("")){
                rName.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(rNum.getText().toString().equals("")){
                rNum.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(rPin.getText().toString().equals("")){
                rPin.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(cAddress.getText().toString().equals("")){
                cAddress.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(cName.getText().toString().equals("")){
                cName.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(cNum.getText().toString().equals("")){
                cNum.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(cPin.getText().toString().equals("")){
                cPin.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(weight.getText().toString().equals("")){
                weight.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            if(cost.getText().toString().equals("")){
                cost.setBackgroundColor(Color.rgb(255,100,100));
                flag=false;
            }
            Toast.makeText(BookCourier.this,"Check info",Toast.LENGTH_LONG).show();
            return flag;
        }
    }
}
