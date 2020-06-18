package com.example.proyectomovil.Controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectomovil.Data.BaseDeDatos;
import com.example.proyectomovil.Models.Actividad;
import com.example.proyectomovil.Models.Evento;
import com.example.proyectomovil.Models.Imagenes;
import com.example.proyectomovil.Models.Sitio;

import java.util.ArrayList;
import java.util.List;

public class BusquedaController {

    public List<Sitio> buscarSitios(Context context, String like, String orden, String tipo){
        List<Sitio> lista = new ArrayList<>();
        String condiciones = "";
        String order_by = "desc";
        if(orden.equals("menor")){
            order_by = "asc";
        }
        if(tipo.equals("TODOS")){
            condiciones += "where tipo != ''";
        }else{
            condiciones += "where tipo = '"+tipo+"'";
        }
        if(like.equals("")== false){
            condiciones += " and nombre like '%"+like+"%' or descripcion like '%"+like+"%' or direccion like '%"+like+"%'";
        }


        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            String sql = "select * from sitios "+condiciones+" order by calificacion "+order_by;
            Cursor fila = basededatos.rawQuery(sql, null);

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
                    sitio.calificacion = fila.getString(fila.getColumnIndex("calificacion"));
                    Cursor fila2 = basededatos.rawQuery("select * from imagen_sitio where id_sitio = " + sitio.id_sitio, null);

                    while (fila2.moveToNext()) {
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

    public List<Evento> buscarEventos(Context context, String like, String orden){
        List<Evento> lista = new ArrayList<>();
        String condiciones = "";
        String order_by = "desc";
        if(orden.equals("menor")){
            order_by = "asc";
        }

        if(like.equals("")== false){
            condiciones += " where nombre like '%"+like+"%' or descripcion like '%"+like+"%' or fecha_inicio like '%"+like+"%' or fecha_fin like '%"+like+"%'";
        }


        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            String sql = "select * from eventos "+condiciones+" order by calificacion "+order_by;
            Cursor fila = basededatos.rawQuery(sql, null);

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
    public List<Actividad> buscarActividades(Context context, String like, String orden){
        List<Actividad> lista = new ArrayList<>();
        String condiciones = "";
        String order_by = "desc";
        if(orden.equals("menor")){
            order_by = "asc";
        }

        if(like.equals("")== false){
            condiciones += " where nombre like '%"+like+"%' or descripcion like '%"+like+"%' or fecha like '%"+like+"%'";
        }

        try {
            BaseDeDatos bd = new BaseDeDatos(context, BaseDeDatos.nombreBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            String sql = "select * from actividades "+condiciones+" order by calificacion "+order_by;
            Cursor fila = basededatos.rawQuery(sql, null);

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
}
