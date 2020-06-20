@extends('web.layout')
@section('content')
 <h1 class="h3 mb-4 text-gray-800">Sitios</h1>
 @if(session('mensaje'))
 	<div class="alert alert-info" id="div_mensaje">
 		<strong>{{ session('mensaje') }}</strong>
 	</div>
 	<script>
 		setTimeout(function(){ $("#div_mensaje").fadeOut() }, 5000);
 	</script>
 @endif
 <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Gestiona tus sitios &nbsp;&nbsp;<a style="color: white !important;" class="btn btn-primary" onclick="abrir_modal_sitio()"><i class="fa fa-plus"></i> Nuevo</a></h6> 
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Id</th>
                      <th>Nombre</th>
                      <th>Descripcion</th>
                      <th>Direccion</th>
                      <th>Latitud</th>
                      <th>Longitud</th>
                      <th>Tipo</th>
                      <th>Opciones</th>
                    </tr>
                  </thead>
                  <tbody>
                   @foreach ($sitios as $sitio)
                    <tr>
                      <td>{{ $sitio->id_sitio }}</td>
                      <td>{{ $sitio->nombre }}</td>
                      <td>{{ $sitio->descripcion }}</td>
                      <td>{{ $sitio->direccion }}</td>
                      <td>{{ $sitio->latitud }}</td>
                      <td>{{ $sitio->longitud }}</td>
                      <td>{{ $sitio->tipo }}</td>
                      <td>
                      	<center>
                      	<a href="{{ route('sitio/viewImagenes', $sitio->id_sitio) }}" title="Ver imagenes"><i class="fa fa-file-image"></i></a> &nbsp;&nbsp;<a onclick="abrir_modal_sitio_editar({{ $sitio->id_sitio }},'{{ $sitio->nombre }}','{{ $sitio->descripcion }}','{{ $sitio->direccion }}','{{ $sitio->latitud }}','{{ $sitio->longitud }}','{{ $sitio->tipo }}')" title="Editar"><i class="fa fa-edit"></i></a> &nbsp;&nbsp;<a href="{{ route('sitio/eliminar', $sitio->id_sitio) }}" title="Eliminar"><i class="fa fa-times"></i></a> 
                      </center>
                      	</td>
                    </tr>                           
                                                
                   @endforeach
                    
                   
                  </tbody>
                </table>
              </div>
            </div>
          </div>


    	<div class="modal" tabindex="-1" role="dialog" id="modal_sitio">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Sitio</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form action="{{ route('sitio/guardar') }}" method="POST">
		      	@csrf
		      <div class="modal-body" >
		       	 
                        <input type="hidden" class="form-control form-control-user"  name="id_sitio" id="id_sitio">
		       	  		<div class="form-group">
		       	  			<label>Nombre</label>
                        	<input type="text" class="form-control form-control-user" name="nombre" id="nombre" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Descripcion</label>
                        	<input type="text" class="form-control form-control-user" name="descripcion" id="descripcion" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Direccion</label>
                        	<input type="text" class="form-control form-control-user" name="direccion" id="direccion" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Latitud</label>
                        	<input type="text" class="form-control form-control-user" name="latitud" id="latitud" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Longitud</label>
                        	<input type="text" class="form-control form-control-user" name="longitud" id="longitud" required>
                        </div>
                        <div class="form-group">
                    <label>Tipo</label>
                          <input type="text" class="form-control form-control-user" name="tipo" id="tipo" required>
                        </div>
		       	  	
		       	  	  
                    
		      </div>
		      <div class="modal-footer">
		        <button type="submit" class="btn btn-primary">Guardar cambios</button>
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
		      </div>
		      </form>
		    </div>
		  </div>
		</div>

		<script>
			function abrir_modal_sitio() {
				$("#modal_sitio").modal("show")
			}
			function abrir_modal_sitio_editar(id_sitio, nombre, descripcion, direccion, latitud, longitud, tipo) {
				$("#id_sitio").val(id_sitio)
				$("#nombre").val(nombre)
				$("#descripcion").val(descripcion)
				$("#direccion").val(direccion)
				$("#latitud").val(latitud)
        $("#longitud").val(longitud)
        $("#tipo").val(tipo)
				$("#modal_sitio").modal("show")
			}
		</script>
@endsection