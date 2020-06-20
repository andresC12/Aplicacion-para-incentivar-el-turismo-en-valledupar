<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Evento;
use App\Actividad;
use App\Sitio;
use App\Favorito;
class APIController extends Controller
{
    public function getEventos()

    {   
        $eventos = Evento::all();
        foreach ($eventos as $evento) {
            $sql = "select sum(calificacion) as total_suma, count(calificacion) as total_calificaciones from calificaciones where accion = 'evento' and id_accion = ".$evento->id_evento; 
            $result = DB::select($sql);
            $total_suma = $result[0]->total_suma;
            $total_calificaciones = $result[0]->total_calificaciones;
            $calificacion = "0";
            if($total_calificaciones != 0){
                $calificacion = $total_suma / $total_calificaciones;
                $calificacion = round($calificacion,1);
            }
            $evento["calificacion"] =  strval($calificacion);
            $evento["sitios"] = $evento->sitios();
            foreach ($evento["sitios"] as $sitio) {
                $sql = "select sum(calificacion) as total_suma, count(calificacion) as total_calificaciones from calificaciones where accion = 'sitio' and id_accion = ".$sitio->id_sitio; 
                $result = DB::select($sql);
                $total_suma = $result[0]->total_suma;
                $total_calificaciones = $result[0]->total_calificaciones;
                $calificacion = "0";
                if($total_calificaciones != 0){
                    $calificacion = $total_suma / $total_calificaciones;
                    $calificacion = round($calificacion,1);
                }
                $sitio["calificacion"] =  strval($calificacion);
                $sitio["imagenes"] = $sitio->imagenes;
            }
            $evento->fecha_inicio = date('d/m/Y H:i', strtotime($evento->fecha_inicio));
            if($evento->fecha_fin){
                
            }$evento->fecha_fin = date('d/m/Y H:i', strtotime($evento->fecha_fin));
            
            
            
        }
        return response()->json([
            "eventos" => $eventos
        ]);
    }

    public function getActividades()
    {
        $actividades = Actividad::all();
        foreach ($actividades as $actividad) {
            $sql = "select sum(calificacion) as total_suma, count(calificacion) as total_calificaciones from calificaciones where accion = 'actividad' and id_accion = ".$actividad->id_actividad; 
            $result = DB::select($sql);
            $total_suma = $result[0]->total_suma;
            $total_calificaciones = $result[0]->total_calificaciones;
            $calificacion = "0";
            if($total_calificaciones != 0){
                $calificacion = $total_suma / $total_calificaciones;
                $calificacion = round($calificacion,1);
            }
            $actividad["calificacion"] =  strval($calificacion);
            $actividad["sitios"] = $actividad->sitios();
            foreach ($actividad["sitios"] as $sitio) {
                $sql = "select sum(calificacion) as total_suma, count(calificacion) as total_calificaciones from calificaciones where accion = 'sitio' and id_accion = ".$sitio->id_sitio; 
                $result = DB::select($sql);
                $total_suma = $result[0]->total_suma;
                $total_calificaciones = $result[0]->total_calificaciones;
                $calificacion = "0";
                if($total_calificaciones != 0){
                    $calificacion = $total_suma / $total_calificaciones;
                    $calificacion = round($calificacion,1);
                }
                $sitio["calificacion"] =  strval($calificacion);
                $sitio["imagenes"] = $sitio->imagenes;
            }
            $actividad->fecha = date('d/m/Y H:i', strtotime($actividad->fecha));
        }
        return response()->json([
            "actividades" => $actividades
        ]);
    }

    public function getSitios()
    {
        $sitios = Sitio::all();
        $tipos = [];
        foreach ($sitios as $sitio) {
            $sql = "select sum(calificacion) as total_suma, count(calificacion) as total_calificaciones from calificaciones where accion = 'sitio' and id_accion = ".$sitio->id_sitio; 
            $result = DB::select($sql);
            $total_suma = $result[0]->total_suma;
            $total_calificaciones = $result[0]->total_calificaciones;
            $calificacion = "0";
            if($total_calificaciones != 0){
                $calificacion = $total_suma / $total_calificaciones;
                $calificacion = round($calificacion,1);
            }
            $sitio["calificacion"] =  strval($calificacion);

            $tipo = strtoupper($sitio->tipo);
            if(!in_array($tipo, $tipos)) array_push($tipos, $tipo);
            $sitio['imagenes'] = $sitio->imagenes;
        }
        return response()->json([
            "sitios" => $sitios,
            "tipos" => $tipos
        ]);
    }

     public function actualizarCalificacion(Request $request)
    {

        $post = $request->all();
        if($post){
            $post = (object) $post;
            $sql = "select * from calificaciones where 
                id_accion = ".$post->id_accion.
                " and accion = '".$post->accion."'".
                " and calificacion = ".$post->calificacion_antigua.
                " limit 1";
               
            $result_select = DB::select($sql);
            if(count($result_select) > 0){
                DB::statement("delete from calificaciones where id_calificacion = ".$result_select[0]->id_calificacion);
            }
            DB::insert('INSERT INTO calificaciones (accion, id_accion, calificacion) VALUES (:accion, :id_accion, :calificacion)', [
                    'accion' => $post->accion,
                    'id_accion' => $post->id_accion,
                    'calificacion' => $post->calificacion_nueva
            ]);

            return response()->json([
                "mensaje" => "Calificacion guardada",
                "error" => "0"
            ]);
        }
        return response()->json([
            "mensaje" => "No se envio correctamente la solicitud",
            "error" => "1"
        ]);
    }
}