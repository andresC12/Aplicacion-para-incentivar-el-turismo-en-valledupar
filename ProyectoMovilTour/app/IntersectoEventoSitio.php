<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class IntersectoEventoSitio extends Model
{
    protected $table = 'intersecto_evento_sitio';
    protected $primaryKey = 'id_intersecto';

    public function sitio()
	{
		return $this->belongsTo(Sitio::class, 'id_sitio');
	}
	public function evento()
	{
		return $this->belongsTo(Evento::class, 'id_evento');
	}
}
