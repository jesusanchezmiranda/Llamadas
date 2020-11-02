package com.jesus.incomingcalls;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializacionLlamadas {

    public boolean writeSerializable(Call localCall, Context cont) {
        ArrayList<Call> callsList = readSerializable(cont);
        callsList.add(localCall);


        try {
            FileOutputStream fos = new FileOutputStream(cont.getExternalFilesDir("callsList.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(callsList);
            oos.close();
            fos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<Call> readSerializable(Context cont) {
        ArrayList<Call> callsList = new ArrayList();

        try {
            FileInputStream fis = new FileInputStream(cont.getExternalFilesDir("callsList.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            callsList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Error");
            c.printStackTrace();
        }


        return callsList;
    }


}
