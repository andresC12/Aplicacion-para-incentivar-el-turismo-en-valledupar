package com.example.proyectomovil.Views;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class MenuAdministrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Esta opcion se va a realizar para actualizar la info del administrador.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void GestionarEventos(View view){
        Intent activity = new Intent(this, GestionEventos.class);
        startActivity(activity);
    }
    public void GestionarSitios(View view){
        Intent activity = new Intent(this, GestionSitios.class);
        startActivity(activity);
    }

}
