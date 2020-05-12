package com.example.proyectomovil.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;
import com.example.proyectomovil.Models.Usuario;

import java.text.SimpleDateFormat;

public class UsuarioController {


    public Usuario buscar(Context context){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from usuarios", null);
            if (fila.moveToFirst()) {
                Usuario usuario = new Usuario();
                usuario.id_usuario = fila.getInt(fila.getColumnIndex("id_usuario"));
                usuario.identificacion = fila.getString(fila.getColumnIndex("identificacion"));
                usuario.nombre = fila.getString(fila.getColumnIndex("nombre"));
                usuario.apellido = fila.getString(fila.getColumnIndex("apellido"));
                usuario.email = fila.getString(fila.getColumnIndex("email"));
                usuario.usuario = fila.getString(fila.getColumnIndex("usuario"));
                usuario.clave = fila.getString(fila.getColumnIndex("clave"));
                return usuario;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
