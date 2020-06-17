package com.example.proyectomovil.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;

public class CalificacionController {
    public static int obtenerCalificacion(Context context, String tipo_accion,int id_accion){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from calificaciones where tipo_accion = '"+tipo_accion+"' and id_accion = "+id_accion, null);
            int calificacion = 0;
            while(fila.moveToNext()) {
                calificacion =  fila.getInt(fila.getColumnIndex("calificacion"));
            }
            basededatos.close();
            return calificacion;
        }catch (Exception excepcion) {
            return 0;
        }
    }

    public static boolean actualizarCalificacion(Context context, String tipo_accion,int id_accion, int calificacion){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("calificaciones", "tipo_accion = '"+tipo_accion+"' and id_accion = "+id_accion, null);
            ContentValues registro = new ContentValues();
            registro.put("tipo_accion", tipo_accion);
            registro.put("id_accion", id_accion);
            registro.put("calificacion", calificacion);
            basededatos.insert("calificaciones", null, registro);
            basededatos.close();
            return true;
        }catch (Exception excepcion) {
            return false;
        }
    }
}
