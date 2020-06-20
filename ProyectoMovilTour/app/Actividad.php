<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Actividad extends Model
{
    protected $table = 'actividad';
    protected $primaryKey = 'id_actividad';

    protected $fillable = [
    	'nombre',
    	'descripcion',
    	'fecha',
    	'imagen',
    ];
    public function sitios()
	{
		$intersectos = IntersectoActividadSitio::all()->where('id_actividad', $this->id_actividad);
		$sitios = [];
		foreach ($intersectos as $intersecto) {
			array_push($sitios, $intersecto->sitio);
		}
		return $sitios;
	}
}
