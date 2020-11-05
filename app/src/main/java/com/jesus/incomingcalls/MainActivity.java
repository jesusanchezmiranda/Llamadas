package com.jesus.incomingcalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    FileClass fc = new FileClass();

    public static final String TAG = MainActivity.class.getName() + "xyzxy";
    private static final int PERMISO = 100;
    public static TextView tvText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private boolean option1, option2;
    private String valor = "";

    private String[] codigosPermisos = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                return viewSettingsActivity();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean viewSettingsActivity() {
        //intencion
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tvText = findViewById(R.id.tvText);
        getPermissions();
        ArrayList<Call> calls = new ArrayList<>();

        File f = new File(getApplicationContext().getExternalFilesDir(null), "Llamadas.csv");
        if(!f.exists()){
            f = new File(getApplicationContext().getExternalFilesDir(null), "Llamadas.csv");
            fc.saveExternalFile(calls, getApplicationContext());
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        saveSharedPreferences();

        setOnTextView();

    }

    private void setOnTextView() {
        valor = sharedPreferences.getString("listPref", "listPref");

        if (valor.equalsIgnoreCase("getfilesDir")) {
            // choice 1
            ArrayList<Call> lista = fc.readFile(getApplicationContext());
            StringBuilder aux = new StringBuilder();
            for (int i = 0; i < lista.size(); i++) {
                aux.append(lista.get(i)).append("\n");
            }
            tvText.setText(aux.toString());
        }
        else if (valor.equalsIgnoreCase("getExternalfilesDir")){
            // choice 2
            ArrayList<Call> lista = fc.readFile(MainActivity.this);
            Collections.sort(lista);
            StringBuilder aux = new StringBuilder();
            for (int i = 0; i < lista.size(); i++) {
                aux.append(lista.get(i)).append("\n");
            }

            tvText.setText(aux.toString());
        }

    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int phonePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            int callPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
            int contactsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

            if (phonePermission == PackageManager.PERMISSION_GRANTED && callPermission == PackageManager.PERMISSION_GRANTED && contactsPermission == PackageManager.PERMISSION_GRANTED) {

            }else{
                //you have to request the permissions that you haven´t got

                if (shouldShowRequestPermissionRationale(String.valueOf(codigosPermisos))){
                    explainPermission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG);
                }else{
                    requestPermissions(codigosPermisos, PERMISO);
                }

            }

        } else {
            //do the next because you have all the permissions
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISO:
                if(grantResults.length>0){
                    boolean ReadCallLogPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContacts = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if(ReadCallLogPermission && ReadPhoneStatePermission && ReadContacts ){

                    }else{
                        explainPermission();
                    }
                }
        }
    }

    @SuppressLint("NewApi")
    private void pidoPermiso(){
        requestPermissions(codigosPermisos, PERMISO);
    }


    private void explainPermission(String... permissions) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permisssion1);
        builder.setMessage(R.string.permission2);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pidoPermiso();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", getPackageName(), null)));
            }
        });
        builder.show();

    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        valor = sharedPreferences.getString(key, "na");

    }

    private void saveSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("listPref", valor);

        editor.commit();
    }


}
