package com.jesus.incomingcalls;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serializable;

public class Call  implements Serializable, Comparable<Call>{

    SerializacionLlamadas sl = new SerializacionLlamadas();

    private String tlf, nombre, datee;

    public Call() {
    }

    public Call(String tlf, String nombre, String datee) {
        this.tlf = tlf;
        this.nombre = nombre;
        this.datee = datee;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    @Override
    public String toString() {
        return  tlf+"; " + nombre+"; " + datee;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Call)) return false;
        Call call = (Call) o;
        return Objects.equals(nombre, call.nombre) &&
                Objects.equals(tlf, call.tlf) &&
                Objects.equals(datee, call.datee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tlf, nombre, datee);
    }


    @Override
    public int compareTo(Call call) {

        int sort = this.nombre.compareTo(call.nombre);
        if(sort==0){
            sort = datee.compareTo(call.datee);
            if(sort==0){
                sort = tlf.compareTo(call.tlf);
            }
        }
        return sort;

    }


    public String toCsv(){
        return tlf + ";" + nombre + ";" + datee + "\n";
    }

    public static Call fromCsvString(String csv, String separator){
        Call c = null;
        String[] partes = csv.split(separator);
        if(partes.length ==3){
            c = new Call (partes[0], partes[1], partes[2]);
        }
        return c;

    }





}
