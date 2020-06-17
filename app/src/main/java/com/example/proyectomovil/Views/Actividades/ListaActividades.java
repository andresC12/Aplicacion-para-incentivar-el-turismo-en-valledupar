package com.example.proyectomovil.Views.Actividades;

import android.app.Activity;
import android.os.Bundle;

import com.example.proyectomovil.Api.ApiActividades;
import com.example.proyectomovil.Api.ApiEventos;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class ListaActividades extends AppCompatActivity {

    public static Activity activity_actividades;
    public static View imagen_loading;
    public static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_actividades = this;
        imagen_loading = (View) findViewById(R.id.imagen_loading_actividades);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_actividades_cliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ApiActividades api_peticion = new ApiActividades(this);
        api_peticion.execute();
    }

}
