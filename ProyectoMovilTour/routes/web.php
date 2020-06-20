<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::any('/','PersonaController@loginWeb');
Route::get('logout','PersonaController@logout')->name('logout');
Route::get('inicio', function(){
	return view('web.inicio');
})->name('inicio');;


Route::get('sitio/index','SitioController@index')->name('sitio/index');
Route::post('sitio/guardar','SitioController@guardar')->name('sitio/guardar');
Route::any('sitio/cargarImagen/{id_sitio}','SitioController@cargarImagen')->name('sitio/cargarImagen');
Route::get('sitio/eliminar/{id_sitio}','SitioController@eliminar')->name('sitio/eliminar');
Route::get('sitio/eliminarImagen/{id_imagen_sitio}','SitioController@eliminarImagen')->name('sitio/eliminarImagen');
Route::get('sitio/viewImagenes/{id_sitio}','SitioController@viewImagenes')->name('sitio/viewImagenes');


Route::get('evento/index','EventoController@index')->name('evento/index');
Route::post('evento/guardar','EventoController@guardar')->name('evento/guardar');
Route::get('evento/eliminar/{id_evento}','EventoController@eliminar')->name('evento/eliminar');
Route::get('evento/buscarSitios/{id_evento}','EventoController@buscarSitios')->name('evento/buscarSitios');


Route::get('actividad/index','ActividadController@index')->name('actividad/index');
Route::post('actividad/guardar','ActividadController@guardar')->name('actividad/guardar');
Route::get('actividad/eliminar/{id_actividad}','ActividadController@eliminar')->name('actividad/eliminar');
Route::get('actividad/buscarSitios/{id_actividad}','ActividadController@buscarSitios')->name('actividad/buscarSitios');

Route::get('usuario/index','PersonaController@index')->name('usuario/index');
Route::post('usuario/guardar','PersonaController@guardar')->name('usuario/guardar');
Route::get('usuario/eliminar/{id_actividad}','PersonaController@eliminar')->name('usuario/eliminar');

//RUTAS PARA LA API
Route::any('api/getEventos','APIController@getEventos')->name('api/getEventos');
Route::any('api/getActividades','APIController@getActividades')->name('api/getActividades');
Route::any('api/getSitios','APIController@getSitios')->name('api/getSitios');
Route::any('api/actualizarCalificacion','APIController@actualizarCalificacion')->name('api/actualizarCalificacion');
