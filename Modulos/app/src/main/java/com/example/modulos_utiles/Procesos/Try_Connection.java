package com.example.modulos_utiles.Procesos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Try_Connection {

    public Try_Connection() {
    }

    //Comprueba que tipo de conexión a red utiliza
    public void connection(Context context){
       ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo Wifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo Mobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (Wifi.isConnected()) {
                Toast.makeText(context, "Conectado a red WIFI", Toast.LENGTH_SHORT).show();
            }
            if (Mobile.isConnected()) {
                Toast.makeText(context, "Conectado a red Móvil", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            /* No estas conectado a internet */
            Toast.makeText(context, "No tienes conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }
}
