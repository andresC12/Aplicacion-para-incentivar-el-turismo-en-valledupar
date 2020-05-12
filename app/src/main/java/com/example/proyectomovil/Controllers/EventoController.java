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

    public String guardar(Context context, Evento evento, List<Sitio> sitios_evento){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("nombre", evento.nombre);
            registro.put("descripcion",evento.descripcion);
            basededatos.insert("eventos", null, registro);

            Cursor fila = basededatos.rawQuery("select * from eventos order by id_evento desc limit 1", null);
            int id_evento_ultimo = 0;
            if (fila.moveToFirst()) {
                id_evento_ultimo = fila.getInt(fila.getColumnIndex("id_evento"));
            }
            for (Sitio sitio: sitios_evento) {
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

    public List<Evento> buscarTodos(Context context){
        List<Evento> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from eventos", null);

            while(fila.moveToNext()) {
                Evento evento = new Evento();
                evento.id_evento = fila.getInt(fila.getColumnIndex("id_evento"));
                evento.nombre = fila.getString(fila.getColumnIndex("nombre"));
                evento.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
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

    public List<Evento> buscarTodosPorNombre(Context context, String nombre){
        List<Evento> lista = new ArrayList<>();
        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor fila = basededatos.rawQuery("select * from eventos where nombre like '%"+nombre+"%'", null);

            while(fila.moveToNext()) {
                Evento evento = new Evento();
                evento.id_evento = fila.getInt(fila.getColumnIndex("id_evento"));
                evento.nombre = fila.getString(fila.getColumnIndex("nombre"));
                evento.descripcion = fila.getString(fila.getColumnIndex("descripcion"));
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
