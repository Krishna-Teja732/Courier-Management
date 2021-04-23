package com.example.couriermanagement.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class query {
    private static DBHelper helper;
    private static SQLiteDatabase database;
    private query(){}

    public static class EmpQuery{
        public static String[] Login(Context context, String username, String password){
            helper = new DBHelper(context);
            database = helper.getReadableDatabase();
            String[] info=null;

            String[] projection = {
                    DB.Employee.EMP_ID,
                    DB.Employee.EMP_OFFICE_ID,
                    DB.Employee.EMP_NAME,
                    DB.Employee.PASSWORD,
                    DB.Employee.RANK
            };
            String select = DB.Employee.EMP_ID +"= ?";
            String[] selectionArgs = {username};

            Cursor cursor = database.query(
                    DB.Employee.TABLE_NAME,
                    projection,
                    select,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            while(cursor.moveToNext()){
                String flag = cursor.getString(cursor.getColumnIndex(DB.Employee.PASSWORD));
                if(flag.equals(password)){
                    info = new String[5];
                    info[0] = cursor.getString(cursor.getColumnIndex(DB.Employee.EMP_ID));
                    info[1] = cursor.getString(cursor.getColumnIndex(DB.Employee.EMP_OFFICE_ID));
                    info[2] = cursor.getString(cursor.getColumnIndex(DB.Employee.EMP_NAME));
                    info[3] = cursor.getString(cursor.getColumnIndex(DB.Employee.RANK));
                    info[4] = cursor.getString(cursor.getColumnIndex(DB.Employee.PASSWORD));
                }
            }
            cursor.close();
            return info;
        }

        private static int findLastId(DBHelper helper){
            database = helper.getReadableDatabase();
            long id=99;
            String[] projection= {
                    DB.Employee.EMP_ID
            };

            Cursor cursor = database.query(DB.Employee.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);
            while(cursor.moveToNext()){
                id = cursor.getLong(cursor.getColumnIndex(DB.Employee.EMP_ID));
            }
            cursor.close();
            return (int)id+1;
        }

        public static int createEmp(Context context, String name, String rank, int office_id, int salary, String password){
            helper = new DBHelper(context);
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            int id=findLastId(helper);
            values.put(DB.Employee.EMP_ID,id);
            values.put(DB.Employee.EMP_OFFICE_ID,office_id);
            values.put(DB.Employee.EMP_NAME,name);
            values.put(DB.Employee.PASSWORD,password);
            values.put(DB.Employee.RANK,rank);
            values.put(DB.Employee.EMP_SAL,salary);
            database.insert(DB.Employee.TABLE_NAME,null,values);
            return id;
        }

        public static void changePassword(Context context,String username,String newPassword){
            helper = new DBHelper(context);
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DB.Employee.PASSWORD,newPassword);
            String selection = DB.Employee.EMP_ID + "= ?";
            String[] selectionArgs = {username};
            database.update(DB.Employee.TABLE_NAME, values, selection, selectionArgs);
        }

        public static int BookCourier(Context context, int empid, int officeid, ArrayList<String> customerinfo,ArrayList<Long> courierinfo){
            helper = new DBHelper(context);
            int cus_id=findLastCustomerId(helper);
            int cour_id=findLastCourierId(helper);
            database = helper.getWritableDatabase();
            ContentValues courierValues = new ContentValues();
            courierValues.put(DB.Courier.COURIER_ID,cour_id);
            courierValues.put(DB.Courier.EMP_ID,empid);
            courierValues.put(DB.Courier.COST,courierinfo.get(0));
            courierValues.put(DB.Courier.SRC_PIN_CODE,courierinfo.get(1));
            courierValues.put(DB.Courier.DEST_PIN_CODE,courierinfo.get(2));
            courierValues.put(DB.Courier.WEIGHT,courierinfo.get(3));
            database.insert(DB.Courier.TABLE_NAME,null,courierValues);

            ContentValues customerValues = new ContentValues();
            customerValues.put(DB.Customer.CUSTOMER_ID,cus_id);
            customerValues.put(DB.Customer.CUSTOMER_NAME,customerinfo.get(0));
            customerValues.put(DB.Customer.SENDER_ADDRESS,customerinfo.get(1));
            customerValues.put(DB.Customer.SENDER_PHNO,Long.parseLong(customerinfo.get(2)));
            customerValues.put(DB.Customer.RECEIVER_ADDRESS,customerinfo.get(3));
            customerValues.put(DB.Customer.RECEIVER_PHNO,Long.parseLong(customerinfo.get(4)));
            database.insert(DB.Customer.TABLE_NAME,null,customerValues);

            ContentValues logValues = new ContentValues();
            logValues.put(DB.Logs.COURIER_ID,cour_id);
            logValues.put(DB.Logs.CUSTOMER_ID,cus_id);
            logValues.put(DB.Logs.OFFICE_ID,officeid);
            database.insert(DB.Logs.TABLE_NAME,null,logValues);

            return cour_id;
        }

        private static int findLastCourierId(DBHelper helper) {
            database = helper.getReadableDatabase();
            int id=99;
            String[] projection = {
                    DB.Courier.COURIER_ID
            };
            Cursor cursor = database.query(DB.Courier.TABLE_NAME,projection,null,null,null,null,null);
            while(cursor.moveToNext()){
                id = (int) cursor.getLong(cursor.getColumnIndex(DB.Courier.COURIER_ID));
            }
            return id+1;
        }

        private static int findLastCustomerId(DBHelper helper) {
            database = helper.getReadableDatabase();
            int id=99;
            String[] projection = {
                    DB.Customer.CUSTOMER_ID
            };
            Cursor cursor = database.query(DB.Customer.TABLE_NAME,projection,null,null,null,null,null);
            while(cursor.moveToNext()){
                id = (int) cursor.getLong(cursor.getColumnIndex(DB.Customer.CUSTOMER_ID));
            }
            return id+1;
        }

        public static ArrayList<String> logs(Context context,String officeid){
            helper = new DBHelper(context);
            database = helper.getReadableDatabase();
            ArrayList<String> vals = new ArrayList<>();
            String selection = DB.Logs.OFFICE_ID + "=?";
            String[] selectionArgs = {officeid};
            Cursor cursor = database.query(DB.Logs.TABLE_NAME,null,selection,selectionArgs,null,null,null);
            int num=1;
            while (cursor.moveToNext()){
                String val =num +".  Courier ID:  " +
                        cursor.getString(cursor.getColumnIndex(DB.Logs.COURIER_ID)) +
                        "    Customer ID: " +
                        cursor.getString(cursor.getColumnIndex(DB.Logs.CUSTOMER_ID));
                vals.add(val);
                num++;
            }
            return vals;
        }
    }

    public static class OfficeQuery{
        private static int findLastId(DBHelper helper){
            database = helper.getReadableDatabase();
            long id=99;
            String[] projection= {
                    DB.CourierOffice.OFFICE_ID
            };

            Cursor cursor = database.query(
                    DB.CourierOffice.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);
            while(cursor.moveToNext()){
                id = cursor.getLong(cursor.getColumnIndex(DB.CourierOffice.OFFICE_ID));
            }
            cursor.close();
            return (int)id+1;
        }

        public static boolean createOffice(Context context,String address, Long number, String city, long pincode){
            helper = new DBHelper(context);
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DB.CourierOffice.OFFICE_ID,findLastId(helper));
            values.put(DB.CourierOffice.OFFICE_ADDRESS,address);
            values.put(DB.CourierOffice.OFFICE_PHONE,number);
            values.put(DB.CourierOffice.CITY,city);
            values.put(DB.CourierOffice.PIN_CODE,pincode);
            long flag = database.insert(DB.CourierOffice.TABLE_NAME,null,values);
            return flag!=-1;
        }
    }

    public static void initDB(Context context){
        if(OfficeQuery.createOffice(context,"address",(long)1234567891,"city",123456))
            System.out.println("Successful");
        else
            System.out.println("Unsuccessful");
        if(OfficeQuery.createOffice(context,"address1",(long)1234567891,"city",123456))
            System.out.println("Successful");
        else
            System.out.println("Unsuccessful");
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(DB.CourierOffice.TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            System.out.println(cursor.getLong(cursor.getColumnIndex(DB.CourierOffice.OFFICE_ID))+" "+
                    cursor.getString(cursor.getColumnIndex(DB.CourierOffice.OFFICE_ADDRESS)));
        }
        cursor.close();
        EmpQuery.createEmp(context,"admin1","admin",100,10000,"1234567");
        EmpQuery.createEmp(context,"admin2","admin",101,10000,"1234567");
    }

    public static ArrayList<String> customerQuery(Context context,String cour_id){
        ArrayList<String> res = new ArrayList<>();
        String cus_id="-1";
        helper = new DBHelper(context);
        database = helper.getReadableDatabase();
        String selection = DB.Logs.COURIER_ID + "=?";
        String[] args = {cour_id};
        @SuppressLint("Recycle") Cursor cursor = database.query(DB.Logs.TABLE_NAME,null, selection, args,null,null,null);
        while(cursor.moveToNext()){
            cus_id = cursor.getString(cursor.getColumnIndex(DB.Logs.CUSTOMER_ID));
        }
        if(cus_id.equals("-1")){
            return null;
        }
        selection = DB.Customer.CUSTOMER_ID + "=?";
        String[] args1 = {cus_id};
        cursor = database.query(DB.Customer.TABLE_NAME,null,selection,args1,null,null,null);
        while (cursor.moveToNext()){
            res.add(cursor.getString(cursor.getColumnIndex(DB.Customer.CUSTOMER_NAME)));
        }
        selection = DB.Courier.COURIER_ID + "=?";
        cursor = database.query(DB.Courier.TABLE_NAME,null,selection,args,null,null,null);
        while (cursor.moveToNext()){
            res.add(cursor.getString(cursor.getColumnIndex(DB.Courier.COURIER_ID)));
            res.add(cursor.getString(cursor.getColumnIndex(DB.Courier.COST)));
        }
        cursor.close();
        return res;
    }
    public static void close(){
        if(database!=null)
            database.close();
    }
}
