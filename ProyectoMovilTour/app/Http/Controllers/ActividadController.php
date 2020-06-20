<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Actividad;
use App\Sitio;
use App\IntersectoActividadSitio;
use Illuminate\Support\Facades\Storage;

class ActividadController extends Controller
{
    public function index($mensaje = null)
    {
    	$actividades = Actividad::all();
    	$sitios = Sitio::all();
        return view('actividad.index',compact(['actividades', 'mensaje', 'sitios']));
    }

     public function buscarSitios($id_actividad)
    {
    	$actividad = Actividad::find($id_actividad);
        return response()->json(["sitios" => $actividad->sitios()]);
    }
    public function guardar(Request $request)
    {
    	$post = $request->all();

    	if ($post) {
    		$post = (object) $post;
    		$actividad = Actividad::find($post->id_actividad);
    		if(!$actividad) $actividad = new Actividad;


    		$actividad->fill($request->except(["_token", "id_actividad","sitios","imagen"]));

    		
    		$file = $request->file('imagen');
             if ($file) {   
               //obtenemos el nombre del archivo
                $nombre = $file->getClientOriginalName();
                $ruta = '/imagenes/actividades';
               
            	$exists =Storage::disk('public')->exists($ruta);
            	Storage::disk('public')->put($ruta."/".$nombre,  \File::get($file));
            	$actividad->imagen = $nombre;
        	}
        	$actividad->save();
        	$result_delete = DB::statement('delete from intersecto_actividad_sitio where id_actividad = '.$actividad->id_actividad);

        	foreach ($post->sitios as $id_sitio) {
        		$intersecto = new IntersectoActividadSitio;
        		$intersecto->id_actividad = $actividad->id_actividad;
        		$intersecto->id_sitio = $id_sitio;
        		$intersecto->save();
        	}
    		$mensaje = "Cambios guardados correctamente.";
    		$request->session()->flash('mensaje', $mensaje);
    		return redirect()->route('actividad/index');
    	}
    }
    public function eliminar(Request $request, $id_actividad)
    {
		$actividad = Actividad::find($id_actividad);
		if($actividad) $actividad->delete();
		$mensaje = "La actividad se elimino correctamente.";
		$request->session()->flash('mensaje', $mensaje);
		return redirect()->route('actividad/index');

    }
}
