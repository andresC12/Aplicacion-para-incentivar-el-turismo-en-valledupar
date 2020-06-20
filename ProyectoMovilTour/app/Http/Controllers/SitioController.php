<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Sitio;
use App\ImagenSitio;
use Illuminate\Support\Facades\Storage;


class SitioController extends Controller
{
    public function index($mensaje = null)
    {
    	$sitios = Sitio::all();

        return view('sitio.index',compact(['sitios', 'mensaje']));
    }

    public function guardar(Request $request)
    {
    	$post = $request->all();

    	if ($post) {
    		$post = (object) $post;
    		$sitio = Sitio::find($post->id_sitio);
    		if(!$sitio) $sitio = new Sitio;
    		$sitio->fill($request->except(["_token", "id_sitio"]));
    		$sitio->save();
    		$mensaje = "Cambios guardados correctamente.";
    		$request->session()->flash('mensaje', $mensaje);
    		return redirect()->route('sitio/index');
    	}
    }
    public function eliminar(Request $request, $id_sitio)
    {
		$sitio = Sitio::find($id_sitio);
		if($sitio) $sitio->delete();
		$mensaje = "El sitio se elimino correctamente.";
		$request->session()->flash('mensaje', $mensaje);
		return redirect()->route('sitio/index');

    }

    public function viewImagenes($id_sitio)
    {
		$sitio = Sitio::find($id_sitio);
		if(!$sitio){
			echo "No existe el sitio";
			die; 
		} 
		return view('sitio.imagenes_de_sitio',compact(['sitio']));

    }

    public function eliminarImagen(Request $request, $id_imagen_sitio)
    {
		$imagenSitio = ImagenSitio::find($id_imagen_sitio);
		if($imagenSitio){

			$exists =Storage::disk('public')->exists('/imagenes/'.$imagenSitio->imagen);
			if($exists) Storage::disk('public')->delete('/imagenes/'.$imagenSitio->imagen);
			$imagenSitio->delete();
			$mensaje = "La imagen se elimino correctamente.";
			$request->session()->flash('mensaje', $mensaje);
			return true;
		} 
		

    }

     public function cargarImagen(Request $request, $id_sitio)
    {
		$sitio = Sitio::find($id_sitio);
        $post = $request->all();

        if ($post) {
            $file = $request->file('imagen_file');
             if ($file) {   
               //obtenemos el nombre del archivo
               $nombre = $file->getClientOriginalName();
               $ruta = '/imagenes';
               
               $exists =Storage::disk('public')->exists($ruta);
               
               Storage::disk('public')->put($ruta."/".$nombre,  \File::get($file));

               $sitio_imagen = new ImagenSitio;
               $sitio_imagen->id_sitio = $id_sitio;
               $sitio_imagen->imagen = $nombre;
               $sitio_imagen->save(); 
               $mensaje = "La imagen se guardo correctamente.";
			   $request->session()->flash('mensaje', $mensaje);
               return redirect()->route('sitio/viewImagenes', $id_sitio);
             }
           
        }
    }


}
