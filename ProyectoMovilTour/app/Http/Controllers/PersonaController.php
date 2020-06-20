<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Persona;

class PersonaController extends Controller
{
    public function loginWeb(Request $request)
    {
    	$post = $request->all();
    	if ($post) {
    	 	$post = (object) $post;
    	 	$usuario = Persona::where('usuario',$post->usuario)->where('clave', $post->clave)->where('tipo_usuario', "admin")->first();
    	 	if($usuario){

    			session([
                 'id_persona' => $usuario->id_persona
    			]);
    			return view('web.inicio');
    	 	}
    	 	return back()->withErrors(['mensaje'=>"Credenciales invalidas"]);
    	}
    	return view('web.login');
    }

    public function logout(Request $request)
    {
        $request->session()->flush();
        return redirect('/');
    }

    public function index($mensaje = null)
    {
        $personas = Persona::all()->where('tipo_usuario', 'usuario');
        return view('usuario.index',compact(['personas', 'mensaje']));
    }

    public function guardar(Request $request)
    {
        $post = $request->all();

        if ($post) {
            $post = (object) $post;
            $persona = Persona::find($post->id_persona);
            if(!$persona) $persona = new Persona;
            $persona->fill($request->except(["_token", "id_persona"]));
            $persona->save();
            $mensaje = "Cambios guardados correctamente.";
            $request->session()->flash('mensaje', $mensaje);
            return redirect()->route('usuario/index');
        }
    }
    public function eliminar(Request $request, $id_persona)
    {
        $persona = Persona::find($id_persona);
        if($persona) $persona->delete();
        $mensaje = "El usuario se elimino correctamente.";
        $request->session()->flash('mensaje', $mensaje);
        return redirect()->route('usuario/index');

    }
}
