package com.example.proyectomovil.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Sitio;

import java.util.ArrayList;
import java.util.List;

public class EventoController {

    public String guardar(Context context, Evento evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_evento", evento.id_evento);
            registro.put("nombre", evento.nombre);
            registro.put("descripcion",evento.descripcion);
            registro.put("imagen",evento.imagen);
            registro.put("fecha_inicio",evento.fecha_inicio);
            registro.put("fecha_fin",evento.fecha_fin);
            registro.put("calificacion",evento.calificacion);
            Cursor es_favorito = basededatos.rawQuery("select * from eventos_favoritos where id_evento = "+evento.id_evento, null);
            if(es_favorito.moveToFirst()){
                registro.put("favorito","1");
            }else{
                registro.put("favorito","0");
            }
            basededatos.insert("eventos", null, registro);

            Cursor fila = basededatos.rawQuery("select * from eventos order by id_evento desc limit 1", null);
            int id_evento_ultimo = 0;
            if (fila.moveToFirst()) {
                id_evento_ultimo = fila.getInt(fila.getColumnIndex("id_evento"));
            }
            for (Sitio sitio: evento.sitios) {
                if(SitioController.buscar(context, sitio.id_sitio) == null){
                    new SitioController().guardar(context, sitio);
                }else{
                    new SitioController().editar(context, sitio);
                }
                ContentValues registro_intersecto = new ContentValues();
                registro_intersecto.put("id_sitio", sitio.id_sitio);
                registro_intersecto.put("id_evento",id_evento_ultimo);
                basededatos.insert("sitios_eventos", null, registro_intersecto);
            }
            basededatos.close();
            return "Se registro correctamente el evento";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo registrar el evento";
        }
    }

    public static boolean establecer_favorito(Context context, int id_evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_evento", id_evento);
            basededatos.insert("eventos_favoritos", null, registro);

            ContentValues registro2 = new ContentValues();
            registro2.put("favorito", "1");
            basededatos.update("eventos", registro2,"id_evento = "+id_evento, null);
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }
    public static boolean quitar_favorito(Context context, int id_evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("eventos_favoritos", "id_evento = "+id_evento, null);
            ContentValues registro = new ContentValues();
            registro.put("favorito", "0");
            basededatos.update("eventos", registro,"id_evento = "+id_evento, null);
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public String editar(Context context, Evento evento, List<Sitio> sitios_evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("nombre", evento.nombre);
            registro.put("descripcion",evento.descripcion);
            basededatos.update("eventos", registro,"id_evento = "+evento.id_evento, null);

            basededatos.delete("sitios_eventos", "id_evento = "+evento.id_evento, null);

            for (Sitio sitio: sitios_evento) {
                ContentValues registro_intersecto = new ContentValues();
                registro_intersecto.put("id_sitio", sitio.id_sitio);
                registro_intersecto.put("id_evento",evento.id_evento);
                basededatos.insert("sitios_eventos", null, registro_intersecto);
            }
            basededatos.close();
            return "Se actualizo correctamente el evento";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo registrar el evento";
        }
    }

    public String eliminar(Context context, int id_evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("eventos", "id_evento = "+id_evento, null);
            basededatos.delete("sitios_eventos", "id_evento = "+id_evento, null);
            basededatos.close();
            return "Se elimino correctamente el evento";
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return "Error no se pudo eliminar el evento";
        }
    }
    public static boolean eliminarTodos(Context context){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.delete("eventos", "", null);
            basededatos.delete("sitios_eventos", "", null);
            basededatos.close();
            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public List<Evento> buscarTodos(Context context){
        List<Evento> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from eventos order by calificacion desc", null);

            while(fila.moveToNext()) {
                Evento evento = new Evento();
                evento.id_evento = fila.getInt(fila.getColumnIndex("id_evento"));
                evento.nombre = fila.getString(fila.getColumnIndex("nombre"));
                evento.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                evento.imagen = fila.getString(fila.getColumnIndex("imagen"));
                evento.fecha_inicio = fila.getString(fila.getColumnIndex("fecha_inicio"));
                evento.fecha_fin = fila.getString(fila.getColumnIndex("fecha_fin"));
                evento.favorito = fila.getString(fila.getColumnIndex("favorito"));
                evento.calificacion = fila.getString(fila.getColumnIndex("calificacion"));


                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_eventos where id_evento = "+evento.id_evento, null);
                while(fila_sitios.moveToNext()) {

                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    evento.sitios.add(sitio);
                }
                lista.add(evento);
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }
    public List<Evento> buscarTodosFavoritos(Context context){
        List<Evento> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from eventos where favorito = '1' order by calificacion desc", null);

            while(fila.moveToNext()) {
                Evento evento = new Evento();
                evento.id_evento = fila.getInt(fila.getColumnIndex("id_evento"));
                evento.nombre = fila.getString(fila.getColumnIndex("nombre"));
                evento.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                evento.imagen = fila.getString(fila.getColumnIndex("imagen"));
                evento.fecha_inicio = fila.getString(fila.getColumnIndex("fecha_inicio"));
                evento.fecha_fin = fila.getString(fila.getColumnIndex("fecha_fin"));
                evento.favorito = fila.getString(fila.getColumnIndex("favorito"));
                evento.calificacion = fila.getString(fila.getColumnIndex("calificacion"));


                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_eventos where id_evento = "+evento.id_evento, null);
                while(fila_sitios.moveToNext()) {

                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    evento.sitios.add(sitio);
                }
                lista.add(evento);
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }

    public List<Evento> buscarTodosPorNombre(Context context, String nombre){
        List<Evento> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from eventos where nombre like '%"+nombre+"%' order by calificacion desc", null);

            while(fila.moveToNext()) {
                Evento evento = new Evento();
                evento.id_evento = fila.getInt(fila.getColumnIndex("id_evento"));
                evento.nombre = fila.getString(fila.getColumnIndex("nombre"));
                evento.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                evento.imagen = fila.getString(fila.getColumnIndex("imagen"));
                evento.fecha_inicio = fila.getString(fila.getColumnIndex("fecha_inicio"));
                evento.fecha_fin = fila.getString(fila.getColumnIndex("fecha_fin"));
                evento.favorito = fila.getString(fila.getColumnIndex("favorito"));
                evento.calificacion = fila.getString(fila.getColumnIndex("calificacion"));

                lista.add(evento);

                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_eventos where id_evento = "+evento.id_evento, null);
                while(fila_sitios.moveToNext()) {
                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    evento.sitios.add(sitio);
                }
            }

        }catch (Exception e){
            return null;
        }
        return lista;
    }
    public Evento buscar(Context context, int id_evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from eventos where id_evento = "+id_evento, null);

            while(fila.moveToNext()) {
                Evento evento = new Evento();
                evento.id_evento = fila.getInt(fila.getColumnIndex("id_evento"));
                evento.nombre = fila.getString(fila.getColumnIndex("nombre"));
                evento.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
                evento.imagen = fila.getString(fila.getColumnIndex("imagen"));
                evento.fecha_inicio = fila.getString(fila.getColumnIndex("fecha_inicio"));
                evento.fecha_fin = fila.getString(fila.getColumnIndex("fecha_fin"));
                evento.favorito = fila.getString(fila.getColumnIndex("favorito"));
                evento.calificacion = fila.getString(fila.getColumnIndex("calificacion"));
                Cursor fila_sitios = basededatos.rawQuery("select * from sitios_eventos where id_evento = "+evento.id_evento, null);
                while(fila_sitios.moveToNext()) {
                    int id_sitio = fila_sitios.getInt(fila_sitios.getColumnIndex("id_sitio"));
                    Sitio sitio = new SitioController().buscar(context, id_sitio);
                    evento.sitios.add(sitio);
                }

                return evento;
            }

        }catch (Exception e){
            return null;
        }
        return null;
    }
}
