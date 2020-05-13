package com.example.proyectomovil.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.proyectomovil.ListaEventos;
import com.example.proyectomovil.ListaSitios;
import com.example.proyectomovil.Listados.ListaDeEventos;
import com.example.proyectomovil.R;
import com.google.android.material.snackbar.Snackbar;

public class MenuInvitados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_invitados);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, /* Este codigo es para identificar tu request */ 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1 /* El codigo que puse a mi request */: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // aqui ya tengo permisos
                } else {
                    // aqui no tengo permisos
                }
                return;
            }
        }
    }

    public void MenuEventos(View view){
        Intent activity = new Intent(this, ListaEventos.class);
        startActivity(activity);
    }

    public void MenuSitios(View view){
        Intent activity = new Intent(this, ListaSitios.class);
        startActivity(activity);

    }

    public void MenuActividades(View view){
        Snackbar.make(view, "Esta opcion esta en proceso de creacion. Gracias", 10000)
                .setAction("Action", null).show();
    }

}
