package com.example.proyectomovil.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FormularioSitio extends AppCompatActivity {

    public Sitio sitio = null;
    TextView txt_nombre_sitio, txt_descripcion_sitio, txt_latitud_sitio, txt_longitud_sitio;
    Button btn_eliminar_sitio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_sitio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_nombre_sitio = (TextView) findViewById(R.id.txt_nombre_sitio);
        txt_descripcion_sitio = (TextView) findViewById(R.id.txt_descripcion_sitio);
        txt_latitud_sitio = (TextView) findViewById(R.id.txt_latitud_sitio);
        txt_longitud_sitio = (TextView) findViewById(R.id.txt_longitud_sitio);
        btn_eliminar_sitio = (Button) findViewById(R.id.btn_eliminar_sitio);

        int id_sitio = getIntent().getIntExtra("id_sitio", 0);
        if (id_sitio != 0){
            sitio = new SitioController().buscar(this, id_sitio);
            txt_nombre_sitio.setText(sitio.nombre);
            txt_descripcion_sitio.setText(sitio.descripcion);
            txt_latitud_sitio.setText(String.valueOf(sitio.latitud));
            txt_longitud_sitio.setText(String.valueOf(sitio.longitud));
            btn_eliminar_sitio.setVisibility(View.VISIBLE);
        }

    }

    public void guardarSitio(View view){
        if(sitio == null){
            sitio = new Sitio();
            sitio.nombre = txt_nombre_sitio.getText().toString();
            sitio.descripcion = txt_descripcion_sitio.getText().toString();
            sitio.latitud = Double.parseDouble(txt_latitud_sitio.getText().toString());
            sitio.longitud = Double.parseDouble(txt_longitud_sitio.getText().toString());
            String respuesta = new SitioController().guardar(this, sitio);
            Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, GestionSitios.class);
            startActivity(intent);
        }else{
            sitio.nombre = txt_nombre_sitio.getText().toString();
            sitio.descripcion = txt_descripcion_sitio.getText().toString();
            sitio.latitud = Double.parseDouble(txt_latitud_sitio.getText().toString());
            sitio.longitud = Double.parseDouble(txt_longitud_sitio.getText().toString());
            String respuesta = new SitioController().editar(this, sitio);
            Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, GestionSitios.class);
            startActivity(intent);
        }
    }

    public void eliminarSitio(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea eliminar este sitio?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String respuesta = new SitioController().eliminar(getApplicationContext(), sitio.id_sitio);
                        Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), GestionSitios.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }

}
