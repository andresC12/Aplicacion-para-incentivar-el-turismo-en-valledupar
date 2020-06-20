<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Evento;
use App\Sitio;
use App\IntersectoEventoSitio;
use Illuminate\Support\Facades\Storage;


class EventoController extends Controller
{
    public function index($mensaje = null)
    {
    	$eventos = Evento::all();
    	$sitios = Sitio::all();
        return view('evento.index',compact(['eventos', 'mensaje', 'sitios']));
    }

     public function buscarSitios($id_evento)
    {
    	$evento = Evento::find($id_evento);
        return response()->json(["sitios" => $evento->sitios()]);
    }
    public function guardar(Request $request)
    {
    	$post = $request->all();

    	if ($post) {
    		$post = (object) $post;
    		$evento = Evento::find($post->id_evento);
    		if(!$evento) $evento = new Evento;


    		$evento->fill($request->except(["_token", "id_evento","sitios","imagen"]));

    		
    		$file = $request->file('imagen');
             if ($file) {   
               //obtenemos el nombre del archivo
                $nombre = $file->getClientOriginalName();
                $ruta = '/imagenes/eventos';
               
            	$exists =Storage::disk('public')->exists($ruta);
            	Storage::disk('public')->put($ruta."/".$nombre,  \File::get($file));
            	$evento->imagen = $nombre;
        	}
        	$evento->save();
        	$result_delete = DB::statement('delete from intersecto_evento_sitio where id_evento = '.$evento->id_evento);

        	foreach ($post->sitios as $id_sitio) {
        		$intersecto = new IntersectoEventoSitio;
        		$intersecto->id_evento = $evento->id_evento;
        		$intersecto->id_sitio = $id_sitio;
        		$intersecto->save();
        	}
    		$mensaje = "Cambios guardados correctamente.";
    		$request->session()->flash('mensaje', $mensaje);
    		return redirect()->route('evento/index');
    	}
    }
    public function eliminar(Request $request, $id_evento)
    {
		$evento = Evento::find($id_evento);
		if($evento) $evento->delete();
		$mensaje = "El evento se elimino correctamente.";
		$request->session()->flash('mensaje', $mensaje);
		return redirect()->route('evento/index');

    }
}
