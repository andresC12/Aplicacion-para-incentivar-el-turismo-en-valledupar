package com.example.proyectomovil.Routes;

public class api {
    public static String ip = "192.168.0.20";
    public static String server = "http://"+ip+":8000/api/";
    public static String server_imagenes_eventos = "http://"+ip+":8000/imagenes/eventos/";
    public static String server_imagenes_sitios = "http://"+ip+":8000/imagenes/";
    public static String server_imagenes_actividades = "http://"+ip+":8000/imagenes/actividades/";
    public static String getActividades = server+"getActividades";
    public static String getEventos = server+"getEventos";
    public static String getSitios = server+"getSitios";
    public static String actualizarCalificacion = server+"actualizarCalificacion";
}
