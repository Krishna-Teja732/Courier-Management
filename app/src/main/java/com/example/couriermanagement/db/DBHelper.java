package com.example.couriermanagement.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public static final int version=1;
    public static final String name = "OFFICE DATABASE";


    public static final String CREATE_OFFICE = "CREATE TABLE "+ DB.CourierOffice.TABLE_NAME+"( " +
            DB.CourierOffice.OFFICE_ID+ " INTEGER PRIMARY KEY," +
            DB.CourierOffice.OFFICE_ADDRESS+ " VARCHAR(20) NOT NULL," +
            DB.CourierOffice.OFFICE_PHONE+ " NUMBER(10) NOT NULL,"+
            DB.CourierOffice.CITY + " VARCHAR(20),"+
            DB.CourierOffice.PIN_CODE + " NUMBER(10)"+
            ");";

    public static final String CREATE_EMPLOYEE = "CREATE TABLE "+ DB.Employee.TABLE_NAME+"( " +
            DB.Employee.EMP_ID+ " INTEGER PRIMARY KEY," +
            DB.Employee.EMP_OFFICE_ID+" INTEGER NOT NULL, "+
            DB.Employee.EMP_NAME + " VARCHAR(30) NOT NULL,"+
            DB.Employee.PASSWORD +" VARCHAR(30) NOT NULL,"+
            DB.Employee.RANK + " VARCHAR(20) NOT NULL,"+
            DB.Employee.EMP_SAL + " NUMBER(6) NOT NULL,"+
            "CONSTRAINT FK_OFFICE FOREIGN KEY ("+ DB.Employee.EMP_OFFICE_ID +") REFERENCES "+ DB.CourierOffice.TABLE_NAME +"("+ DB.CourierOffice.OFFICE_ID+")"+
            ");";

    public static final String CREATE_CUSTOMER = "CREATE TABLE "+ DB.Customer.TABLE_NAME+"("+
            DB.Customer.CUSTOMER_ID+" INTEGER PRIMARY KEY," +
            DB.Customer.CUSTOMER_NAME+" VARCHAR(20),"+
            DB.Customer.SENDER_ADDRESS+ " VARCHAR(50),"+
            DB.Customer.SENDER_PHNO + " NUMBER(10),"+
            DB.Customer.RECEIVER_ADDRESS + " VARCHAR(50),"+
            DB.Customer.RECEIVER_PHNO + " NUMBER(10)"+
            ");";

    public static final String CREATE_COURIER = "CREATE TABLE "+ DB.Courier.TABLE_NAME +"("+
            DB.Courier.COURIER_ID +" INTEGER PRIMARY KEY,"+
            DB.Courier.EMP_ID +" INTEGER,"+
            DB.Courier.COST +" INTEGER,"+
            DB.Courier.SRC_PIN_CODE + " NUMBER(6),"+
            DB.Courier.DEST_PIN_CODE + " NUMBER(6),"+
            DB.Courier.WEIGHT + " INTEGER,"+
            "CONSTRAINT FK_EMP FOREIGN KEY ("+ DB.Courier.EMP_ID +") REFERENCES "+ DB.Employee.TABLE_NAME+"("+ DB.Employee.EMP_OFFICE_ID+")"+
            ");";

    public static final String CREATE_LOGS = "CREATE TABLE "+ DB.Logs.TABLE_NAME +"("+
            DB.Logs.COURIER_ID+ " INTEGER,"+
            DB.Logs.CUSTOMER_ID+ " INTEGER,"+
            DB.Logs.OFFICE_ID+ " INTEGER,"+
            "CONSTRAINT FK_OFF FOREIGN KEY ("+ DB.Logs.OFFICE_ID +") REFERENCES "+ DB.CourierOffice.TABLE_NAME+"("+ DB.CourierOffice.OFFICE_ID+"),"+
            "CONSTRAINT FK_CUS FOREIGN KEY ("+ DB.Logs.CUSTOMER_ID +") REFERENCES "+ DB.Customer.TABLE_NAME+"("+ DB.Customer.CUSTOMER_ID+"),"+
            "CONSTRAINT FK_COU FOREIGN KEY ("+ DB.Logs.COURIER_ID +") REFERENCES "+ DB.Courier.TABLE_NAME+"("+ DB.Courier.COURIER_ID+")"+
            ");";

    public static final String CREATE_FEEDBACK = "CREATE TABLE "+ DB.Feedback.TABLE_NAME +"("+
            DB.Feedback.COURIER_ID +" INTEGER,"+
            DB.Feedback.OFFICE_ID + " INTEGER,"+
            DB.Feedback.CUSTOMER_FEEDBACK +" VARCHAR(100),"+
            DB.Feedback.FLAG + " NUMBER(1) DEFAULT 0,"+
            DB.Feedback.FEEDBACK_ANSWER + " VARCHAR(100),"+
            "CONSTRAINT FK_OFF FOREIGN KEY ("+ DB.Feedback.OFFICE_ID +") REFERENCES "+ DB.CourierOffice.TABLE_NAME+"("+ DB.CourierOffice.OFFICE_ID+"),"+
            "CONSTRAINT FK_COU FOREIGN KEY ("+ DB.Feedback.COURIER_ID +") REFERENCES "+ DB.Courier.TABLE_NAME+"("+ DB.Courier.COURIER_ID+")"+
            ");";

    public static final String DELETE_OFFICE = "DROP TABLE IF EXISTS "+ DB.CourierOffice.TABLE_NAME;
    public static final String DELETE_EMPLOYEE = "DROP TABLE IF EXISTS "+ DB.Employee.TABLE_NAME;
    public static final String DELETE_COURIER = "DROP TABLE IF EXISTS "+ DB.Courier.TABLE_NAME;
    public static final String DELETE_CUSTOMER = "DROP TABLE IF EXISTS "+ DB.Customer.TABLE_NAME;
    public static final String DELETE_LOGS = "DROP TABLE IF EXISTS "+ DB.Logs.TABLE_NAME;
    public static final String DELETE_FEEDBACK = "DROP TABLE IF EXISTS "+ DB.Feedback.TABLE_NAME;


    public DBHelper(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_OFFICE);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(CREATE_CUSTOMER);
        db.execSQL(CREATE_COURIER);
        db.execSQL(CREATE_LOGS);
        db.execSQL(CREATE_FEEDBACK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_OFFICE);
        db.execSQL(DELETE_EMPLOYEE);
        db.execSQL(DELETE_COURIER);
        db.execSQL(DELETE_CUSTOMER);
        db.execSQL(DELETE_LOGS);
        db.execSQL(DELETE_FEEDBACK);
        onCreate(db);
    }
}
