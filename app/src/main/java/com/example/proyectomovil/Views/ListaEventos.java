package com.example.proyectomovil.Views;

import android.app.Activity;
import android.os.Bundle;

import com.example.proyectomovil.Api.ApiEventos;
import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Listados.ListaDeEventos;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public class ListaEventos extends AppCompatActivity {

    public static Activity activity_eventos;
    public static View imagen_loading;
    public static RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_eventos = this;
        imagen_loading = (View) findViewById(R.id.imagen_loading_eventos);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_eventos_cliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ApiEventos api_peticion = new ApiEventos(this);
        api_peticion.execute();
        /*

        adapter = new ListaDeEventosCliente(new EventoController().buscarTodos(this), this);
        recyclerView.setAdapter(adapter);

         */
    }

}
