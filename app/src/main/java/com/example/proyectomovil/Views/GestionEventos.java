package com.example.proyectomovil.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeEventos;
import com.example.proyectomovil.Listados.ListaDeSitios;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class GestionEventos extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ListaDeEventos adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_eventos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_eventos_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ListaDeEventos(new EventoController().buscarTodos(this), this);
        recyclerView.setAdapter(adapter);

    }
    public void nuevoEvento(View view){
        Intent activity = new Intent(this, FormularioEvento.class);
        startActivity(activity);
    }

    public void bucarPorNombre(View view){
        TextView txt_nombre_evento = findViewById(R.id.txt_search_evento);
        String nombre = txt_nombre_evento.getText().toString();
        List<Evento> lista = new EventoController().buscarTodosPorNombre(this, nombre);
        adapter = new ListaDeEventos(lista, this);
        recyclerView.setAdapter(adapter);
    }



}
