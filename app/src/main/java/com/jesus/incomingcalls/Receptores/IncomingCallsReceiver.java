package com.jesus.incomingcalls.Receptores;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.jesus.incomingcalls.Call;
import com.jesus.incomingcalls.FileClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static com.jesus.incomingcalls.MainActivity.TAG;

public class IncomingCallsReceiver extends BroadcastReceiver {

    FileClass fc = new FileClass();
    Call cc = new Call();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "Receiver de LLamadas entrantes");

        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);


            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG).show();
            }

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Toast.makeText(context, "Call Received", Toast.LENGTH_LONG).show();
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //Toast.makeText(context, "Phone Is Idle" + number, Toast.LENGTH_LONG).show();
                Hebraca h1 = new Hebraca(context, intent);
                h1.start();

            }
        }
        catch(Exception e){e.printStackTrace();}


    }

    public Call getCalls(Context context) {

        StringBuffer sb = new StringBuffer();

        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                null);

        int num = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int nom = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME); //no nos coje el nombre por lo que hay que hacer el metodo getContactName()
        int fec = cursor.getColumnIndex(CallLog.Calls.DATE);
        sb.append("Call details: \n \n");

        cursor.moveToLast();

            String tlf = cursor.getString(num);
            String nombre = cursor.getString(nom);
            String datee = cursor.getString(fec);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("YYYY; MM; dd; HH; mm; ss;");
            format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            datee = format.format(cal.getTime());

            nombre = getContactName(tlf, context);


            Call llamada = new Call(tlf, nombre, datee);



            Log.v(TAG, tlf + " " + nombre + " " + datee);

//            sb.append("\n PhoneNumber: " + tlf + "\n NOMBRE: " + nombre + "\n FECHA: "+ datee);
//            sb.append("\n-----------------------------------------");



        cursor.close();
        return llamada;

    }

    public String getContactName(final String phoneNumber, Context context) {

        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="Desconocido";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }




    public class Hebraca extends Thread{

        private Context context;
        private Intent intent;

        public Hebraca(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void run() {
            super.run();
            cc=getCalls(context);
            fc.saveFile(cc, context);
            fc.readFile(context);
            fc.saveExternalFile(cc,context);
            fc.readExternalFile(context);

        }


    }



}






