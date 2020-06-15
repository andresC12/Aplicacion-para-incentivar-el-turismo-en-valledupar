package com.example.proyectomovil.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;
import com.example.proyectomovil.Models.Imagenes;
import com.example.proyectomovil.Models.Sitio;
import com.example.proyectomovil.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SitioController {

    public static String guardar(Context context, Sitio sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_sitio", sitio.id_sitio);
            registro.put("nombre", sitio.nombre);
            registro.put("descripcion",sitio.descripcion);
            registro.put("direccion",sitio.direccion);
            registro.put("tipo",sitio.tipo);
            registro.put("latitud",sitio.latitud);
            registro.put("longitud", sitio.longitud);
            Cursor es_favorito = basededatos.rawQuery("select * from sitios_favoritos where id_sitio = "+sitio.id_sitio, null);
            if(es_favorito.moveToFirst()){
                registro.put("favorito","1");
            }else{
                registro.put("favorito","0");
            }
            basededatos.insert("sitios", null, registro);
            basededatos.delete("imagen_sitio", "id_sitio="+sitio.id_sitio, null);
            for(Imagenes imagen: sitio.imagenes){
                ContentValues registro2 = new ContentValues();
                registro2.put("id_sitio", sitio.id_sitio);
                registro2.put("url", imagen.url);
                basededatos.insert("imagen_sitio", null, registro2);
            }
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
            registro.put("id_sitio", sitio.id_sitio);
            registro.put("nombre", sitio.nombre);
            registro.put("descripcion",sitio.descripcion);
            registro.put("direccion",sitio.direccion);
            registro.put("tipo",sitio.tipo);
            registro.put("latitud",sitio.latitud);
            registro.put("longitud", sitio.longitud);
            Cursor es_favorito = basededatos.rawQuery("select * from sitios_favoritos where id_sitio = "+sitio.id_sitio, null);
            if(es_favorito.moveToFirst()){
                registro.put("favorito","1");
            }else{
                registro.put("favorito","0");
            }
            basededatos.update("sitios", registro, "id_sitio="+sitio.id_sitio, null);
            basededatos.delete("imagen_sitio", "id_sitio="+sitio.id_sitio, null);
            for(Imagenes imagen: sitio.imagenes){
                ContentValues registro2 = new ContentValues();
                registro2.put("id_sitio", sitio.id_sitio);
                registro2.put("url", imagen.url);
                basededatos.insert("imagen_sitio", null, registro2);
            }
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

    public static String eliminarTodos(Context context){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("sitios", "", null);
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
                    sitio.direccion = fila.getString(fila.getColumnIndex("direccion"));
                    sitio.tipo = fila.getString(fila.getColumnIndex("tipo"));
                    sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                    sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                    sitio.favorito = fila.getString(fila.getColumnIndex("favorito"));

                    Cursor fila2 = basededatos.rawQuery("select * from imagen_sitio where id_sitio = "+sitio.id_sitio, null);

                    while(fila2.moveToNext()) {
                        Imagenes imagen = new Imagenes();
                        imagen.url = fila2.getString(fila2.getColumnIndex("url"));
                        sitio.imagenes.add(imagen);
                    }
                    lista.add(sitio);
                }

        }catch (Exception e){
            return null;
        }
        return lista;
    }

    public List<Sitio> buscarTodosFavoritos(Context context){
        List<Sitio> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from sitios where favorito = '1'", null);

            while(fila.moveToNext()) {
                Sitio sitio = new Sitio();
                sitio.id_sitio = fila.getInt(fila.getColumnIndex("id_sitio"));
                sitio.nombre = fila.getString(fila.getColumnIndex("nombre"));
                sitio.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                sitio.direccion = fila.getString(fila.getColumnIndex("direccion"));
                sitio.tipo = fila.getString(fila.getColumnIndex("tipo"));
                sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                sitio.favorito = fila.getString(fila.getColumnIndex("favorito"));

                Cursor fila2 = basededatos.rawQuery("select * from imagen_sitio where id_sitio = "+sitio.id_sitio, null);

                while(fila2.moveToNext()) {
                    Imagenes imagen = new Imagenes();
                    imagen.url = fila2.getString(fila2.getColumnIndex("url"));
                    sitio.imagenes.add(imagen);
                }
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
                sitio.direccion = fila.getString(fila.getColumnIndex("direccion"));
                sitio.tipo = fila.getString(fila.getColumnIndex("tipo"));
                sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                sitio.favorito = fila.getString(fila.getColumnIndex("favorito"));
                Cursor fila2 = basededatos.rawQuery("select * from imagen_sitio where id_sitio = "+sitio.id_sitio, null);

                while(fila2.moveToNext()) {
                    Imagenes imagen = new Imagenes();
                    imagen.url = fila2.getString(fila2.getColumnIndex("url"));
                    sitio.imagenes.add(imagen);
                }
                lista.add(sitio);
            }
        }catch (Exception e){
            return null;
        }
        return lista;
    }
    public static boolean establecer_favorito(Context context, int id_sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_sitio", id_sitio);
            basededatos.insert("sitios_favoritos", null, registro);

            ContentValues registro2 = new ContentValues();
            registro2.put("favorito", "1");
            basededatos.update("sitios", registro2,"id_sitio = "+id_sitio, null);
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }
    public static boolean quitar_favorito(Context context, int id_sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("sitios_favoritos", "id_sitio = "+id_sitio, null);
            ContentValues registro = new ContentValues();
            registro.put("favorito", "0");
            basededatos.update("sitios", registro,"id_sitio = "+id_sitio, null);
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }
    public static Sitio buscar(Context context, int id_sitio){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from sitios where id_sitio = "+id_sitio, null);
            if (fila.moveToFirst()) {
                Sitio sitio = new Sitio();
                sitio.id_sitio = fila.getInt(fila.getColumnIndex("id_sitio"));
                sitio.nombre = fila.getString(fila.getColumnIndex("nombre"));
                sitio.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                sitio.direccion = fila.getString(fila.getColumnIndex("direccion"));
                sitio.tipo = fila.getString(fila.getColumnIndex("tipo"));
                sitio.latitud = fila.getDouble(fila.getColumnIndex("latitud"));
                sitio.longitud = fila.getDouble(fila.getColumnIndex("longitud"));
                sitio.favorito = fila.getString(fila.getColumnIndex("favorito"));
                Cursor fila2 = basededatos.rawQuery("select * from imagen_sitio where id_sitio = "+sitio.id_sitio, null);

                while(fila2.moveToNext()) {
                    Imagenes imagen = new Imagenes();
                    imagen.url = fila2.getString(fila2.getColumnIndex("url"));
                    sitio.imagenes.add(imagen);
                }
                return sitio;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
