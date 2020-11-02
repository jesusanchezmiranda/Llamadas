package com.jesus.incomingcalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import static com.jesus.incomingcalls.MainActivity.TAG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileClass extends AppCompatActivity {

    private Call llamada;

    public FileClass() {
    }

    public FileClass(Call llamada) {
        this.llamada = llamada;
    }

    public Call getLlamada() {
        return llamada;
    }

    public void setLlamada(Call llamada) {
        this.llamada = llamada;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileClass fileClass = (FileClass) o;
        return llamada.equals(fileClass.llamada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(llamada);
    }


    @Override
    public String toString() {
        return "FileClass{" +
                "llamada=" + llamada +
                '}';
    }


    public ArrayList<StringBuilder> readExternalFile(Context context) { //ORDENADO
        ArrayList<StringBuilder> calls = new ArrayList();
        File f = new File(context.getExternalFilesDir(null), "Llamadas.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            StringBuilder texto = new StringBuilder();
            while((linea = br.readLine()) != null) {
                llamada.fromCsvString(linea, ";");
                texto.append(linea);
                texto.append('\n');
            }
            br.close();
            calls.add(texto);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return calls;
    }

    public boolean saveExternalFile(Call c, Context context) { //ORDENADO

        boolean result = true;
        File f = new File(context.getExternalFilesDir(null), "Llamadas.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f,true);
            fw.write(c.toString() + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;

    }


    public boolean saveFile(Call c, Context cont) {
        boolean result = true;
        File f = new File(cont.getFilesDir(), "Historial.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f,true);
            fw.write(c.toString() + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }



    public ArrayList<StringBuilder> readFile(Context context) {
        ArrayList<StringBuilder> calls = new ArrayList();
        String linea;
        File f = new File(context.getFilesDir(), "Historial.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            StringBuilder texto = new StringBuilder();
            while((linea = br.readLine()) != null) {
                //llamada.fromCsvString(linea, ";");
                texto.append(linea);
                texto.append('\n');
            }
            br.close();
            calls.add(texto);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return calls;
    }








}
