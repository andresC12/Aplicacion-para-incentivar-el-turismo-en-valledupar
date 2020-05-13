package com.example.proyectomovil;

import android.app.Activity;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class ListaSitios extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ListaDeSitiosCliente adapter;
    public static Activity activity_sitios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sitios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity_sitios = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyler_sitios_cliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaDeSitiosCliente(new SitioController().buscarTodos(this), this);
        recyclerView.setAdapter(adapter);
    }

}
