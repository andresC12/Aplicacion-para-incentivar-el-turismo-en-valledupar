<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Sitio extends Model
{
    protected $table = 'sitio';
    protected $primaryKey = 'id_sitio';
    protected $fillable = [
    	'nombre',
    	'descripcion',
    	'direccion',
    	'latitud',
        'longitud',
        'tipo',
    ];

    public function imagenes()
	{
		return $this->hasMany(ImagenSitio::class, 'id_sitio');
	}
}
