package com.example.proyectomovil.Views;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Busqueda extends AppCompatActivity {

    Spinner combo_accion;
    Spinner combo_tipo_sitio;
    Spinner combo_orden;
    LinearLayout linear_tipo_sitio;
    TextView txt_filtro;
    String accion = "";
    String tipo_sitio = "";
    String orden_puntuacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        combo_accion = findViewById(R.id.combo_tipo_busqueda);
        combo_tipo_sitio = findViewById(R.id.combo_tipo_sitio_busqueda);
        combo_orden = findViewById(R.id.combo_orden_busqueda);
        txt_filtro = findViewById(R.id.txt_filtro_busqueda);
        linear_tipo_sitio = findViewById(R.id.linar_busqueda_tipo_sitio);
        ArrayList<String> acciones = new ArrayList<>();
        acciones.add("SITIOS");
        acciones.add("EVENTOS");
        acciones.add("ACTIVIDADES");
        ArrayList<String> ordenes = new ArrayList<>();
        ordenes.add("Mayor calificacion");
        ordenes.add("Menor calificacion");
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.style_combo,acciones);
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(this, R.layout.style_combo, SitioController.obtenerTipos(this));
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(this, R.layout.style_combo, ordenes);

        combo_accion.setAdapter(adapter);
        combo_tipo_sitio.setAdapter(adapter2);
        combo_orden.setAdapter(adapter3);
        combo_accion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    linear_tipo_sitio.setVisibility(View.VISIBLE);
                    accion = "Sitios";
                }
                if (position==1){
                    linear_tipo_sitio.setVisibility(View.GONE);
                    accion = "Eventos";
                    tipo_sitio = null;
                }
                if (position==2){
                    linear_tipo_sitio.setVisibility(View.GONE);
                    accion = "Actividades";
                    tipo_sitio = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        combo_tipo_sitio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     ArrayList<String> lista = SitioController.obtenerTipos(getApplicationContext());
                     tipo_sitio = lista.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipo_sitio = null;
            }
        });
        combo_orden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    orden_puntuacion = "mayor";
                }
                if (position==1){
                    orden_puntuacion = "menor";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipo_sitio = null;
            }
        });
    }

    public void Consultar(View view){
        Intent intent = new Intent(this, ResultadoBusqueda.class);
        intent.putExtra("accion", accion);
        intent.putExtra("tipo_sitio", tipo_sitio);
        intent.putExtra("filtro",  txt_filtro.getText().toString());
        intent.putExtra("orden", orden_puntuacion);
        startActivity(intent);
    }

}
