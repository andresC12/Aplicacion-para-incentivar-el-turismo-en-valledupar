package com.example.proyectomovil.Models;

import java.util.ArrayList;
import java.util.List;

public class Actividad {
    public int id_actividad;
    public String nombre;
    public String descripcion;
    public String imagen;
    public String fecha;
    public List<Sitio> sitios = new ArrayList<>();
}
