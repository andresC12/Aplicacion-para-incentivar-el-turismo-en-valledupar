<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Evento extends Model
{
    protected $table = 'evento';
    protected $primaryKey = 'id_evento';

     protected $fillable = [
    	'nombre',
    	'descripcion',
    	'fecha_inicio',
    	'fecha_fin',
    	'imagen',
    ];
    public function sitios()
	{
		$intersectos = IntersectoEventoSitio::all()->where('id_evento', $this->id_evento);
		$sitios = [];
		foreach ($intersectos as $intersecto) {
			array_push($sitios, $intersecto->sitio);
		}
		return $sitios;
	}
}
