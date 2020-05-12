package com.example.proyectomovil.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SitioController {

    public String guardar(Context context, Sitio sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("nombre", sitio.nombre);
            registro.put("descripcion",sitio.descripcion);
            registro.put("latitud",sitio.latitud);
            registro.put("longitud", sitio.longitud);
            basededatos.insert("sitios", null, registro);
            basededatos.close();
            return "Se registro correctamente el sitio";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo registrar el sitio";
        }
    }

    public String editar(Context context, Sitio sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("nombre", sitio.nombre);
            registro.put("descripcion",sitio.descripcion);
            registro.put("latitud",sitio.latitud);
            registro.put("longitud", sitio.longitud);
            basededatos.update("sitios", registro, "id_sitio="+sitio.id_sitio, null);
            basededatos.close();
            return "Se actualizo correctamente el sitio";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo actualizar el sitio";
        }
    }

    public String eliminar(Context context, int id_sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("sitios", "id_sitio="+id_sitio, null);
            basededatos.close();
            return "Se elimino correctamente el sitio";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo eliminar el sitio";
        }
    }




    public List<Sitio> buscarTodos(Context context){
        List<Sitio> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from sitios", null);

                while(fila.moveToNext()) {
                    Sitio sitio = new Sitio();
                    sitio.id_sitio = fila.getInt(fila.getColumnIndex("id_sitio"));
                    sitio.nombre = fila.getString(fila.getColumnIndex("nombre"));
                    sitio.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                    sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                    sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                    lista.add(sitio);
                }

        }catch (Exception e){
            return null;
        }
        return lista;
    }

    public List<Sitio> buscarTodosPorNombre(Context context, String nombre){
        List<Sitio> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from sitios where nombre like '%"+nombre+"%'", null);
            if (fila.moveToFirst()) {
                Sitio sitio = new Sitio();
                sitio.id_sitio = fila.getInt(fila.getColumnIndex("id_sitio"));
                sitio.nombre = fila.getString(fila.getColumnIndex("nombre"));
                sitio.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                lista.add(sitio);
            }
        }catch (Exception e){
            return null;
        }
        return lista;
    }
    public Sitio buscar(Context context, int id_sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from sitios where id_sitio = "+id_sitio, null);
            if (fila.moveToFirst()) {
                Sitio sitio = new Sitio();
                sitio.id_sitio = fila.getInt(fila.getColumnIndex("id_sitio"));
                sitio.nombre = fila.getString(fila.getColumnIndex("nombre"));
                sitio.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                return sitio;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
