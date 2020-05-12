package com.example.proyectomovil.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormularioEvento extends AppCompatActivity {

    ListView listView ;
    List<Sitio> sitios_evento = new ArrayList<>();
    public Evento evento;
    TextView txt_nombre_evento, txt_descripcion_evento;
    Button btn_eliminar_evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_evento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt_nombre_evento = (TextView) findViewById(R.id.txt_nombre_evento);
        txt_descripcion_evento = (TextView) findViewById(R.id.txt_descripcion_evento);
        btn_eliminar_evento = (Button)findViewById(R.id.btn_eliminar_evento);
        listView = findViewById(R.id.lista_sitios_evento);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {

                builder.setMessage("¿Seguro que desea quitar este sitio?")
                        .setPositiveButton("Quitar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sitios_evento.remove(position);
                                cargarListaSitios();
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
        });
        int id_evento = getIntent().getIntExtra("id_evento", 0);
        if (id_evento != 0){
            evento = new EventoController().buscar(this, id_evento);
            txt_nombre_evento.setText(evento.nombre);
            txt_descripcion_evento.setText(evento.descripcion);
            sitios_evento = evento.sitios;
            cargarListaSitios();
            btn_eliminar_evento.setVisibility(View.VISIBLE);
        }

    }

    public void guardarEvento(View view){
        if(evento == null){
            evento = new Evento();
            evento.nombre = txt_nombre_evento.getText().toString();
            evento.descripcion = txt_descripcion_evento.getText().toString();

            String respuesta = new EventoController().guardar(this, evento, sitios_evento);
            Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, GestionEventos.class);
            startActivity(intent);
        }else{
            evento.nombre = txt_nombre_evento.getText().toString();
            evento.descripcion = txt_descripcion_evento.getText().toString();
            String respuesta = new EventoController().editar(this, evento, sitios_evento);
            Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, GestionEventos.class);
            startActivity(intent);
        }
    }

    public void agregarSitio(View view){
        final List<Sitio> sitios = new SitioController().buscarTodos(this);
        String[] lista = new String[sitios.size()];
        int cont = 0;
        for(Sitio sitio :sitios){
            lista[cont] = sitio.nombre;
            cont++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione un sitio")
                .setItems(lista, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sitios_evento.add(sitios.get(which));
                        cargarListaSitios();
                    }
                });
        builder.create();
        builder.show();
    }
    public void cargarListaSitios(){
        String[] lista = new String[sitios_evento.size()];
        int cont = 0;
        for(Sitio sitio :sitios_evento){
            lista[cont] = (cont + 1)+". "+sitio.nombre;
            cont++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);

    }

    public void eliminarEvento(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea eliminar este evento?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String respuesta = new EventoController().eliminar(getApplicationContext(), evento.id_evento);
                        Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), GestionEventos.class);
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
