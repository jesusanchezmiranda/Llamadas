package com.jesus.incomingcalls;

import java.util.Objects;
import java.io.Serializable;

public class Call  implements Serializable, Comparable<Call>{

    private String tlf, nombre, datee;

    public Call() {
        this("","", null);
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
                 Objects.equals(datee, call.datee)&&
                Objects.equals(tlf, call.tlf);
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

    public static Call fromCsvString(String csv){
        Call c = null;
        String[] partes = csv.split(";");
        if(partes.length ==8){
            String tlf = partes[0].trim();
            String nom = partes[1].trim();
            String date = partes[2].trim()+"; " +partes[3].trim()+"; "+ partes[4].trim()+"; " +partes[5].trim()+"; "+partes[6].trim()+"; "+partes[7].trim()+"; ";
            c = new Call (tlf, nom, date);
        }
        return c;

    }

    public static Call fromCsvString2(String csv){
        Call c = null;
        String[] partes = csv.split(";");
        if(partes.length ==8){
            String tlf = partes[0].trim();
            String nom = partes[1].trim();
            String date = partes[2].trim()+"; " +partes[3].trim()+"; "+ partes[4].trim()+"; " +partes[5].trim()+"; "+partes[6].trim()+"; "+partes[7].trim()+"; ";
            c = new Call (tlf, nom, date);
        }
        return c;

    }

}