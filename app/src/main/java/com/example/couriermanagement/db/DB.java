package com.example.couriermanagement.db;

import android.provider.BaseColumns;

public class DB {
    private DB(){}


    public static class CourierOffice implements BaseColumns{
        public static final String TABLE_NAME = "OFFICE";
        public static final String OFFICE_ID = "OFFICE_ID";
        public static final String OFFICE_ADDRESS = "OFFICE_ADDRESS";
        public static final String CITY = "CITY";
        public static final String PIN_CODE = "PIN_CODE";
        public static final String OFFICE_PHONE = "OFFICE_PHONE_NUMBER";
    }

    public static class Employee implements BaseColumns{
        public static final String TABLE_NAME = "EMPLOYEE";
        public static final String EMP_ID = "EMP_ID";
        public static final String PASSWORD = "PASSWORD";
        public static final String EMP_OFFICE_ID = "EMP_OFFICE_ID";
        public static final String EMP_NAME = "EMP_NAME";
        public static final String RANK = "RANK";
        public static final String EMP_SAL = "EMP_SALARY";
    }

    public static class Customer implements BaseColumns{
        public static final String TABLE_NAME = "CUSTOMER";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
        public static final String SENDER_PHNO = "SENDER_PHONE_NUM";
        public static final String RECEIVER_PHNO = "RECEIVER_PHONE_NUM";
        public static final String SENDER_ADDRESS = "SENDER_ADDRESS";
        public static final String  RECEIVER_ADDRESS = "RECEIVER_ADDRESS";
    }

    public static class Courier implements BaseColumns{
        public static final String TABLE_NAME = "COURIER";
        public static final String COURIER_ID = "COURIER_ID";
        public static final String SRC_PIN_CODE = "SOURCE_PIN_CODE";
        public static final String DEST_PIN_CODE = "DEST_PIN_CODE";
        //public static final String TYPE = "COURIER_TYPE";
        public static final String EMP_ID = "EMPLOYEE_ID";
        public static final String WEIGHT = "WEIGHT";
        public static final String COST = "COST";
    }

    public static class Logs implements BaseColumns{
        public static final String TABLE_NAME = "LOGS";
        public static final String COURIER_ID = "COURIER_ID";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String COURIER_STATUS = "COURIER_STATUS";
        public static final String OFFICE_ID = "OFFICE_ID";
    }

    public static class Feedback implements BaseColumns{
        public static final String TABLE_NAME = "FEEDBACK";
        public static final String COURIER_ID = "COURIER_ID";
        public static final String OFFICE_ID = "OFFICE_ID";
        public static final String CUSTOMER_FEEDBACK = "CUSTOMER_FEEDBACK";
        public static final String FLAG = "FEEDBACK_ANSWERED";
        public static final String FEEDBACK_ANSWER = "FEEDBACK_ANSWER";
    }
}
