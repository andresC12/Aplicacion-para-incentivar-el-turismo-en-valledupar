package com.example.proyectomovil.Models;

import java.util.ArrayList;

public class Sitio {
    public int id_sitio;
    public String nombre;
    public String descripcion;
    public String direccion;
    public String tipo;
    public double latitud;
    public double longitud;
    public String favorito;
    public String calificacion;
    public ArrayList<Imagenes> imagenes = new ArrayList<>();
}
