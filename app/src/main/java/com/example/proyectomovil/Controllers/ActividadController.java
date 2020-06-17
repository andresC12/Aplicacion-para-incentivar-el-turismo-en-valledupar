package com.example.proyectomovil.Controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.Models.Sitio;

import java.util.ArrayList;
import java.util.List;

public class ActividadController {

    public static String guardar(Context context, Actividad actividad){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_actividad", actividad.id_actividad);
            registro.put("nombre", actividad.nombre);
            registro.put("descripcion",actividad.descripcion);
            registro.put("imagen",actividad.imagen);
            registro.put("fecha",actividad.fecha);
            registro.put("calificacion",actividad.calificacion);
            Cursor es_favorito = basededatos.rawQuery("select * from actividades_favoritas where id_actividad = "+actividad.id_actividad, null);
            if(es_favorito.moveToFirst()){
                registro.put("favorito","1");
            }else{
                registro.put("favorito","0");
            }
            basededatos.insert("actividades", null, registro);

            Cursor fila = basededatos.rawQuery("select * from actividades order by id_actividad desc limit 1", null);
            int id_actividad_ultima = 0;
            if (fila.moveToFirst()) {
                id_actividad_ultima = fila.getInt(fila.getColumnIndex("id_actividad"));
            }
            for (Sitio sitio: actividad.sitios) {
                if(SitioController.buscar(context, sitio.id_sitio) == null){
                    new SitioController().guardar(context, sitio);
                }else{
                    new SitioController().editar(context, sitio);
                }
                ContentValues registro_intersecto = new ContentValues();
                registro_intersecto.put("id_sitio", sitio.id_sitio);
                registro_intersecto.put("id_actividad",id_actividad_ultima);
                basededatos.insert("sitios_actividades", null, registro_intersecto);
            }
            basededatos.close();
            return "Se registro correctamente";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo registrar";
        }
    }

    public static boolean establecer_favorito(Context context, int id_actividad){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_actividad", id_actividad);
            basededatos.insert("actividades_favoritas", null, registro);

            ContentValues registro2 = new ContentValues();
            registro2.put("favorito", "1");
            basededatos.update("actividades", registro2,"id_actividad = "+id_actividad, null);
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }
    public static boolean quitar_favorito(Context context, int id_actividad){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("actividades_favoritas", "id_actividad = "+id_actividad, null);
            ContentValues registro = new ContentValues();
            registro.put("favorito", "0");
            basededatos.update("actividades", registro,"id_actividad = "+id_actividad, null);
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public String editar(Context context, Actividad actividad, List<Sitio> sitios_actividad){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_actividad", actividad.id_actividad);
            registro.put("nombre", actividad.nombre);
            registro.put("descripcion",actividad.descripcion);
            registro.put("imagen",actividad.imagen);
            registro.put("fecha",actividad.fecha);
            registro.put("calificacion",actividad.calificacion);
            basededatos.update("actividades", registro,"id_actividad = "+actividad.id_actividad, null);

            basededatos.delete("sitios_actividades", "id_actividad = "+actividad.id_actividad, null);

            for (Sitio sitio: sitios_actividad) {
                ContentValues registro_intersecto = new ContentValues();
                registro_intersecto.put("id_sitio", sitio.id_sitio);
                registro_intersecto.put("id_actividad",actividad.id_actividad);
                basededatos.insert("sitios_actividades", null, registro_intersecto);
            }
            basededatos.close();
            return "Se actualizo correctamente el Actividad";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo registrar el Actividad";
        }
    }

    public String eliminar(Context context, int id_actividad){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("actividades", "id_actividad = "+id_actividad, null);
            basededatos.delete("sitios_actividades", "id_actividad = "+id_actividad, null);
            basededatos.close();
            return "Se elimino correctamente el Actividad";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo eliminar el Actividad";
        }
    }
    public static boolean eliminarTodos(Context context){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("actividades", "", null);
            basededatos.delete("sitios_actividades", "", null);
            basededatos.close();
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public List<Actividad> buscarTodos(Context context){
        List<Actividad> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from actividades order by calificacion desc", null);

            while(fila.moveToNext()) {
                Actividad actividad = new Actividad();
                actividad.id_actividad = fila.getInt(fila.getColumnIndex("id_actividad"));
                actividad.nombre = fila.getString(fila.getColumnIndex("nombre"));
                actividad.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                actividad.imagen = fila.getString(fila.getColumnIndex("imagen"));
                actividad.fecha = fila.getString(fila.getColumnIndex("fecha"));
                actividad.favorito = fila.getString(fila.getColumnIndex("favorito"));
                actividad.calificacion = fila.getString(fila.getColumnIndex("calificacion"));


                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_actividades where id_actividad = "+actividad.id_actividad, null);
                while(fila_sitios.moveToNext()) {

                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    actividad.sitios.add(sitio);
                }
                lista.add(actividad);
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }
    public static List<Actividad> buscarTodosFavoritos(Context context){
        List<Actividad> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from actividades where favorito = '1' order by calificacion desc", null);

            while(fila.moveToNext()) {
                Actividad actividad = new Actividad();
                actividad.id_actividad = fila.getInt(fila.getColumnIndex("id_actividad"));
                actividad.nombre = fila.getString(fila.getColumnIndex("nombre"));
                actividad.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                actividad.imagen = fila.getString(fila.getColumnIndex("imagen"));
                actividad.fecha = fila.getString(fila.getColumnIndex("fecha"));
                actividad.favorito = fila.getString(fila.getColumnIndex("favorito"));
                actividad.calificacion = fila.getString(fila.getColumnIndex("calificacion"));

                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_actividades where id_actividad = "+actividad.id_actividad, null);
                while(fila_sitios.moveToNext()) {

                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    actividad.sitios.add(sitio);
                }
                lista.add(actividad);
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }


    public List<Actividad> buscarTodosPorNombre(Context context, String nombre){
        List<Actividad> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from actividades where nombre like '%"+nombre+"%' order by calificacion desc", null);

            while(fila.moveToNext()) {
                Actividad actividad = new Actividad();
                actividad.id_actividad = fila.getInt(fila.getColumnIndex("id_actividad"));
                actividad.nombre = fila.getString(fila.getColumnIndex("nombre"));
                actividad.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                actividad.imagen = fila.getString(fila.getColumnIndex("imagen"));
                actividad.fecha = fila.getString(fila.getColumnIndex("fecha"));
                actividad.favorito = fila.getString(fila.getColumnIndex("favorito"));
                actividad.calificacion = fila.getString(fila.getColumnIndex("calificacion"));


                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_actividades where id_actividad = "+actividad.id_actividad, null);
                while(fila_sitios.moveToNext()) {

                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    actividad.sitios.add(sitio);
                }
                lista.add(actividad);
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }
    public static Actividad buscar(Context context, int id_actividad){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from actividades where id_actividad = "+id_actividad, null);

            while(fila.moveToNext()) {
                Actividad actividad = new Actividad();
                actividad.id_actividad = fila.getInt(fila.getColumnIndex("id_actividad"));
                actividad.nombre = fila.getString(fila.getColumnIndex("nombre"));
                actividad.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                actividad.imagen = fila.getString(fila.getColumnIndex("imagen"));
                actividad.fecha = fila.getString(fila.getColumnIndex("fecha"));
                actividad.favorito = fila.getString(fila.getColumnIndex("favorito"));
                actividad.calificacion = fila.getString(fila.getColumnIndex("calificacion"));


                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_actividades where id_actividad = "+actividad.id_actividad, null);
                while(fila_sitios.moveToNext()) {

                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    actividad.sitios.add(sitio);
                }
                return actividad;
            }

        }catch (Exception e){
            return null;
        }
        return null;
    }
}
