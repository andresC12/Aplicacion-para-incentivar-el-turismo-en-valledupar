package com.example.proyectomovil.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {


    public static String nombreBD = "BDV13";
    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(" +
                "id_usuario integer PRIMARY KEY autoincrement," +
                "identificacion text," +
                "nombre text," +
                "apellido text," +
                "email text," +
                "usuario text," +
                "clave text," +
                "recordar text)");

        db.execSQL("create table actividades(" +
                "id_actividad integer," +
                "nombre text," +
                "imagen text," +
                "fecha text," +
                "favorito text," +
                "calificacion text," +
                "descripcion text)");

        db.execSQL("create table actividades_favoritas(" +
                "id_actividad_favorita integer PRIMARY KEY autoincrement," +
                "id_actividad integer)");

        db.execSQL("create table eventos(" +
                "id_evento integer," +
                "nombre text," +
                "imagen text," +
                "fecha_inicio text," +
                "fecha_fin text," +
                "favorito text," +
                "calificacion text," +
                "descripcion text)");

        db.execSQL("create table eventos_favoritos(" +
                "id_evento_favorito integer PRIMARY KEY autoincrement," +
                "id_evento integer)");


        db.execSQL("create table sitios(" +
                "id_sitio integer," +
                "nombre text," +
                "calificacion text," +
                "descripcion text," +
                "direccion text," +
                "tipo text," +
                "favorito text," +
                "latitud double," +
                "longitud double)");


        db.execSQL("create table imagen_sitio(" +
                "id_imagen_sitio integer PRIMARY KEY autoincrement," +
                "id_sitio integer," +
                "url text)");
        db.execSQL("create table sitios_favoritos(" +
                "id_sitio_favorito integer PRIMARY KEY autoincrement," +
                "id_sitio integer)");

        db.execSQL("create table sitios_eventos(" +
                "id_sitio_evento integer PRIMARY KEY autoincrement," +
                "id_sitio integer," +
                "id_evento integer)");

        db.execSQL("create table sitios_actividades(" +
                "id_sitio_actividad integer PRIMARY KEY autoincrement," +
                "id_sitio integer," +
                "id_actividad integer)");

        db.execSQL("create table calificaciones(" +
                "id_calificacion integer PRIMARY KEY autoincrement," +
                "id_accion integer," +
                "calificacion integer," +
                "tipo_accion text)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
}

