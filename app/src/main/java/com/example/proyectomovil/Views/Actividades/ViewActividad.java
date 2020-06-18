package com.example.proyectomovil.Views.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Views.Calificacion;
import com.example.proyectomovil.Controllers.ActividadController;
import com.example.proyectomovil.Listados.ListaDeSitiosCliente;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.R;
import com.example.proyectomovil.Routes.api;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActividad extends AppCompatActivity {

    Actividad actividad;
    public RecyclerView recyclerView;
    public ListaDeSitiosCliente adapter;
    public static Activity activity_viewActividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_actividad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity_viewActividad = this;

        TextView txt_nombre_actividad = findViewById(R.id.txt_nombre_actividad_info);
        TextView txt_descripcion_actividad = findViewById(R.id.txt_descripcion_actividad_info);
        TextView txt_fecha_actividad = findViewById(R.id.txt_fecha_actividad_info);
        ImageView img_actividad = findViewById(R.id.img_actividad_info);
        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calificacion.class);
                intent.putExtra("id_accion", actividad.id_actividad);
                intent.putExtra("accion", "actividad");
                startActivity(intent);
            }
        });

        int id_actividad = getIntent().getIntExtra("id_actividad", 0);
        if (id_actividad != 0){

            actividad = new ActividadController().buscar(this, id_actividad);
            if(actividad.imagen != null){
                Picasso.get()
                        .load(api.server_imagenes_actividades+actividad.imagen)
                        .into(img_actividad);
            }
            txt_nombre_actividad.setText(actividad.nombre);
            txt_descripcion_actividad.setText(actividad.descripcion);
            txt_fecha_actividad.setText(actividad.fecha);


            recyclerView = (RecyclerView) findViewById(R.id.recycler_sitios_actividad);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ListaDeSitiosCliente(actividad.sitios, this);
            recyclerView.setAdapter(adapter);
        }
    }

}
