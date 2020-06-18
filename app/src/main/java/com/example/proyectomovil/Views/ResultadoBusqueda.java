package com.example.proyectomovil.Views;

import android.app.Activity;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.BusquedaController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeActividadesCliente;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Views.Sitios.ListaSitios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ResultadoBusqueda extends AppCompatActivity {

    RecyclerView recyclerView;
    public static Activity activity_busqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_busqueda = this;
        String accion = getIntent().getStringExtra("accion");
        String tipo_sitio = getIntent().getStringExtra("tipo_sitio");
        String filtro = getIntent().getStringExtra("filtro");
        String orden = getIntent().getStringExtra("orden");
        TextView txt_accion = findViewById(R.id.txt_tipo_accion_busqueda);
        txt_accion.setText(accion);
        recyclerView = findViewById(R.id.recycler_busqueda);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        switch (accion){
            case "Sitios":
                List<Sitio> lista = new BusquedaController().buscarSitios(this, filtro,orden, tipo_sitio);
                ListaDeSitiosCliente adapter = new ListaDeSitiosCliente(lista, this);
                recyclerView.setAdapter(adapter);
                break;
            case "Eventos":
                List<Evento> lista2 = new BusquedaController().buscarEventos(this, filtro,orden);
                ListaDeEventosCliente adapter2 = new ListaDeEventosCliente(lista2, this);
                recyclerView.setAdapter(adapter2);
                break;
            case "Actividades":
                List<Actividad> lista3 = new BusquedaController().buscarActividades(this, filtro,orden);
                ListaDeActividadesCliente adapter3 = new ListaDeActividadesCliente(lista3, this);
                recyclerView.setAdapter(adapter3);
                break;
        }
    }

}
