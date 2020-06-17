package com.example.proyectomovil.Models;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    public int id_evento;
    public String nombre;
    public String descripcion;
    public String imagen;
    public String fecha_inicio;
    public String fecha_fin;
    public String favorito;
    public String calificacion;
    public List<Sitio> sitios = new ArrayList<>();
}
