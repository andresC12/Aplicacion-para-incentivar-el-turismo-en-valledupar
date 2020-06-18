package com.example.proyectomovil.Views.Eventos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Listados.ListaDeEventosCliente;
import com.example.proyectomovil.Listados.ListaDeSitios;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Routes.api;
import com.example.proyectomovil.Views.Calificacion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewEvento extends AppCompatActivity {

    Evento evento;
    public RecyclerView recyclerView;
    public ListaDeSitiosCliente adapter;
    public static Activity activity_viewEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_evento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_viewEvento = this;

        TextView txt_nombre_evento = findViewById(R.id.txt_nombre_evento_info);
        TextView txt_descripcion_evento = findViewById(R.id.txt_descripcion_evento_info);
        TextView txt_fecha_evento = findViewById(R.id.txt_fecha_evento_info);
        ImageView img_evento = findViewById(R.id.img_evento_info);
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calificacion.class);
                intent.putExtra("id_accion", evento.id_evento);
                intent.putExtra("accion", "evento");
                startActivity(intent);
            }
        });

        int id_evento = getIntent().getIntExtra("id_evento", 0);
        if (id_evento != 0){

            evento = new EventoController().buscar(this, id_evento);
            if(evento.imagen != null){
                Picasso.get()
                        .load(api.server_imagenes_eventos+evento.imagen)
                        .into(img_evento);
            }
            txt_nombre_evento.setText(evento.nombre);
            txt_descripcion_evento.setText(evento.descripcion);
            if(evento.fecha_fin.equals("null")==false) {
                txt_fecha_evento.setText(evento.fecha_inicio+" hasta "+evento.fecha_fin);
            }else{
                txt_fecha_evento.setText(evento.fecha_inicio);
            }

            recyclerView = (RecyclerView) findViewById(R.id.recycler_sitios_evento);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ListaDeSitiosCliente(evento.sitios, this);
            recyclerView.setAdapter(adapter);
        }

    }

}
