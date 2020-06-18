package com.example.proyectomovil.Views;

import android.app.Activity;
import android.os.Bundle;

import com.example.proyectomovil.Api.ApiActualizarCalificacion;
import com.example.proyectomovil.Controllers.ActividadController;
import com.example.proyectomovil.Controllers.CalificacionController;
import com.example.proyectomovil.Controllers.EventoController;
import com.example.proyectomovil.Controllers.SitioController;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Calificacion extends AppCompatActivity {

    public ImageView btn_estrella_1,btn_estrella_2,btn_estrella_3,btn_estrella_4,btn_estrella_5;
    public int calificacion;
    public int calificacion_anterior;
    public String tipo_accion;
    public int id_accion;
    public static Activity activity_calificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificacion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_estrella_1 = findViewById(R.id.btn_calificacion_1);
        btn_estrella_2 = findViewById(R.id.btn_calificacion_2);
        btn_estrella_3 = findViewById(R.id.btn_calificacion_3);
        btn_estrella_4 = findViewById(R.id.btn_calificacion_4);
        btn_estrella_5 = findViewById(R.id.btn_calificacion_5);
        activity_calificaciones = this;
        btn_estrella_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pintar_estrellas_calificacion(1);
            }
        });
        btn_estrella_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pintar_estrellas_calificacion(2);
            }
        });
        btn_estrella_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pintar_estrellas_calificacion(3);
            }
        });
        btn_estrella_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pintar_estrellas_calificacion(4);
            }
        });
        btn_estrella_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pintar_estrellas_calificacion(5);
            }
        });
        TextView txt_nombre_accion_caificacion = findViewById(R.id.txt_nombre_accion_caificacion);
        tipo_accion = getIntent().getStringExtra("accion");

        id_accion = getIntent().getIntExtra("id_accion",0);
        switch (tipo_accion){
            case "actividad":
                toolbar.setTitle("Calificacion de actividad");
                calificacion_anterior = CalificacionController.obtenerCalificacion(this,"actividad", id_accion);
                pintar_estrellas_calificacion(calificacion_anterior);
                txt_nombre_accion_caificacion.setText(ActividadController.buscar(this, id_accion).nombre);
                break;
            case "evento":
                toolbar.setTitle("Calificacion de evento");
                calificacion_anterior = CalificacionController.obtenerCalificacion(this,"evento", id_accion);
                txt_nombre_accion_caificacion.setText(new EventoController().buscar(this, id_accion).nombre);
                pintar_estrellas_calificacion(calificacion_anterior);
                break;
            case "sitio":
                toolbar.setTitle("Calificacion de sitio");
                calificacion_anterior = CalificacionController.obtenerCalificacion(this,"sitio", id_accion);
                txt_nombre_accion_caificacion.setText(new SitioController().buscar(this, id_accion).nombre);
                pintar_estrellas_calificacion(calificacion_anterior);
                break;
        }
    }

    public void pintar_estrellas_calificacion(int calificacion){
        this.calificacion = calificacion;
        switch (calificacion){
            case 1:
                btn_estrella_1.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_2.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                btn_estrella_3.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                btn_estrella_4.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                btn_estrella_5.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                break;
            case 2:
                btn_estrella_1.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_2.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_3.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                btn_estrella_4.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                btn_estrella_5.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                break;
            case 3:
                btn_estrella_1.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_2.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_3.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_4.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                btn_estrella_5.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                break;
            case 4:
                btn_estrella_1.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_2.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_3.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_4.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_5.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                break;
            case 5:
                btn_estrella_1.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_2.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_3.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_4.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                btn_estrella_5.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_llena));
                break;
                default:
                    btn_estrella_1.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                    btn_estrella_2.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                    btn_estrella_3.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                    btn_estrella_4.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                    btn_estrella_5.setImageDrawable(this.getResources().getDrawable(R.drawable.estrella_vacia));
                    break;

        }
    }

    public void guardarCalificacion(View view){
        ApiActualizarCalificacion api_peticion = new ApiActualizarCalificacion(this,tipo_accion, id_accion, calificacion, calificacion_anterior);
        api_peticion.execute();
    }

}
