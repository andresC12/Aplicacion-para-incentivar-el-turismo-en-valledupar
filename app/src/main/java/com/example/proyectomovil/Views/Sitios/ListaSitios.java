package com.example.proyectomovil.Views.Sitios;

import android.app.Activity;
import android.os.Bundle;

import com.example.proyectomovil.Api.ApiEventos;
import com.example.proyectomovil.Api.ApiSitios;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class ListaSitios extends AppCompatActivity {

    public static Activity activity_sitios;
    public static View imagen_loading;
    public static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sitios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_sitios = this;
        imagen_loading = (View) findViewById(R.id.imagen_loading_sitios);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_sitios_cliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ApiSitios api_peticion = new ApiSitios(this);
        api_peticion.execute();
    }

}
