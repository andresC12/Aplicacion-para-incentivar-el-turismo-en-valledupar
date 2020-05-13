package com.example.proyectomovil.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {


    public static String nombreBD = "bd6";
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

        db.execSQL("insert into usuarios(nombre, usuario, clave, recordar) values ('Administrador', 'admin', 'admin123','')");
        db.execSQL("insert into eventos(id_evento, nombre, descripcion) values (1,'Festival vallenato', 'Es la festividad principal de valledupar y se realiza entre los meses de marzo y abril.')");
        db.execSQL("insert into eventos(id_evento, nombre, descripcion) values (2,'Semana santa', 'Es una festividad mundial que se celebra en abril y en valledupar se celebra igualmente la semana en honor a jesus.')");

        db.execSQL("insert into sitios(id_sitio, nombre, descripcion, latitud, longitud) values (1,'Parque de la leyenda vallenata', 'Lugar para eventos masivos.',10.4967405, -73.2646055)");
        db.execSQL("insert into sitios(id_sitio, nombre, descripcion, latitud, longitud) values (2,'Plaza alfonso lopez', 'Lugar para eventos masivos.',10.4776464, -73.2447422)");
        db.execSQL("insert into sitios(id_sitio, nombre, descripcion, latitud, longitud) values (3,'Rio guatapuri', 'Lugar para baños y reuniones.',10.5012928, -73.2714843)");

        db.execSQL("insert into sitios_eventos(id_evento, id_sitio) values (1,1)");
        db.execSQL("insert into sitios_eventos(id_evento, id_sitio) values (1,2)");
        db.execSQL("insert into sitios_eventos(id_evento, id_sitio) values (1,3)");
        db.execSQL("insert into sitios_eventos(id_evento, id_sitio) values (2,2)");
        db.execSQL("insert into sitios_eventos(id_evento, id_sitio) values (2,3)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists terceros");
        db.execSQL("drop table if exists sucursales");
        onCreate(db);
    }
}

