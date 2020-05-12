package com.example.proyectomovil.Views;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeSitios;
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

public class GestionSitios extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ListaDeSitios adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_sitios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_sitios_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ListaDeSitios(new SitioController().buscarTodos(this), this);
        recyclerView.setAdapter(adapter);
    }

    public void nuevoSitio(View view){
        Intent activity = new Intent(this, FormularioSitio.class);
        startActivity(activity);
    }

    public void bucarPorNombre(View view){
        TextView txt_nombre_sitio = findViewById(R.id.txt_search_sitio);
        String nombre = txt_nombre_sitio.getText().toString();
        List<Sitio> lista = new SitioController().buscarTodosPorNombre(this, nombre);
        adapter = new ListaDeSitios(lista, this);
        recyclerView.setAdapter(adapter);
    }

}
