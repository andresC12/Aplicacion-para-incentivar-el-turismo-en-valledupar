package com.example.proyectomovil.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {


    public static String nombreBD = "bd2";
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
                "clave text)");

        db.execSQL("create table actividades(" +
                "id_actividad integer PRIMARY KEY autoincrement," +
                "nombre text," +
                "descripcion text)");

        db.execSQL("create table eventos(" +
                "id_evento integer PRIMARY KEY autoincrement," +
                "nombre text," +
                "descripcion text)");

        db.execSQL("create table sitios(" +
                "id_sitio integer PRIMARY KEY autoincrement," +
                "nombre text," +
                "descripcion text," +
                "latitud double," +
                "longitud double)");

        db.execSQL("create table sitios_eventos(" +
                "id_sitio_evento integer PRIMARY KEY autoincrement," +
                "id_sitio integer," +
                "id_evento integer)");

        db.execSQL("create table sitios_actividades(" +
                "id_sitio_actividad integer PRIMARY KEY autoincrement," +
                "id_sitio integer," +
                "id_actividad integer)");

        db.execSQL("insert into usuarios(nombre, usuario, clave) values ('Administrador', 'admin', 'admin123')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists terceros");
        db.execSQL("drop table if exists sucursales");
        onCreate(db);
    }
}

