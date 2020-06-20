<?php

namespace App\Http\Middleware;

use Illuminate\Foundation\Http\Middleware\VerifyCsrfToken as Middleware;

class VerifyCsrfToken extends Middleware
{
    /**
     * The URIs that should be excluded from CSRF verification.
     *
     * @var array
     */
    protected $except = [
        "http://192.168.0.20:8000/api/getEventos",
        "http://192.168.0.20:8000/api/getActividades",
        "http://192.168.0.20:8000/api/getSitios",
        "http://192.168.0.20:8000/api/actualizarCalificacion",
    ];
}
