<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class IntersectoActividadSitio extends Model
{
    protected $table = 'intersecto_actividad_sitio';
    protected $primaryKey = 'id_intersecto';

    public function sitio()
	{
		return $this->belongsTo(Sitio::class, 'id_sitio');
	}
	public function actividad()
	{
		return $this->belongsTo(Actividad::class, 'id_actividad');
	}
}
