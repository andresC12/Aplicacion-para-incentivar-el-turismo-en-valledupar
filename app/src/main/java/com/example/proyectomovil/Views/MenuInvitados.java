package com.example.proyectomovil.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.proyectomovil.R;
import com.google.android.material.snackbar.Snackbar;

public class MenuInvitados extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transicion = new Slide(Gravity.END);
        transicion.setDuration(10);
        transicion.setInterpolator(new DecelerateInterpolator());
        getWindow().setEnterTransition(transicion);
        setContentView(R.layout.activity_menu_invitados);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, /* Este codigo es para identificar tu request */ 1);
        View sitios_item = findViewById(R.id.navigation_sitios);
        sitios_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mapa.class);
                intent.putExtra("pintar_todos", 1);
                startActivity(intent);
            }
        });
        View favoritos_item = findViewById(R.id.navigation_favoritos);
        favoritos_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Favoritos.class);
                startActivity(intent);
            }
        });
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
