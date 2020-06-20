<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Persona extends Model
{
    protected $table = 'persona';
    protected $primaryKey = 'id_persona';

     protected $fillable = [
    	'identificacion',
    	'nombres',
    	'apellidos',
    	'email',
    	'usuario',
    	'clave',
    ];

     public function favoritos()
	{
		return $this->hasMany(Favorito::class, 'id_persona');
	}
}
