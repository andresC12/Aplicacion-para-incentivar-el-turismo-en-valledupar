@extends('web.layout')
@section('content')
<h1 class="h3 mb-4 text-gray-800">Imagenes de {{ $sitio->nombre }}</h1>
<div class="row">
	<div class="col-sm-6">
		<a style="color: white !important;" class="btn btn-primary" onclick="$('#modal_imagen').modal('show')"><i class="fa fa-plus"></i> Nueva imagen</a><br><br>
	</div>
	<div class="col-sm-6" style="text-align: right;">
		<a style="color: white !important;" class="btn btn-danger " href="{{ route('sitio/index') }}">Volver</a><br><br>
	</div>
</div>
 
 @if(session('mensaje'))
 	<div class="alert alert-info" id="div_mensaje">
 		<strong>{{ session('mensaje') }}</strong>
 	</div>
 	<script>
 		setTimeout(function(){ $("#div_mensaje").fadeOut() }, 5000);
 	</script>
 @endif
<style type="text/css">
	.imagen{
		border: solid 3px #000;
		margin-bottom: 10px;	
		margin-left:  10px;	
		background: black;
	}
	.flotante{
		margin-bottom: 20px !important;
	}
	.flotante:hover{
		opacity: 0.8;
	}
</style>
	@foreach ($sitio->imagenes as $imagen)

		<td><a class="flotante" onclick="eliminar({{ $imagen->id_imagen_sitio }})" title="Eliminar imagen"><img class="imagen" src="/imagenes/{{ $imagen->imagen }}" width="250" height="250"></a></td>
		
	@endforeach
<div class="modal" tabindex="-1" role="dialog" id="modal_imagen">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Nueva imagen</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form method="POST" action="{{ route('sitio/cargarImagen', $sitio->id_sitio) }}" enctype="multipart/form-data">
		      	
		     
		      	@csrf
		      <div class="modal-body" >
		       	 
		       	  		<div class="form-group">
		       	  			<label>Imagen</label>
                        	<input type="file" data-max-size="2048" class="form-control form-control-user" name="imagen_file" id="imagen_file" required>
                        </div>
                        
		      </div>
		      <div class="modal-footer">
		        <button type="submit" class="btn btn-primary">Guardar imagen</button>
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
		      </div>
		      </form>
		    </div>
		  </div>
		</div>
	<script type="text/javascript">
		function eliminar(id_imagen_sitio) {
			var r = confirm("¿Seguro que desea eliminar esta imagen de la lista?")
			if(r==true){
				url = "../eliminarImagen/"+id_imagen_sitio
				$.get(url, (response) => {
					if(response == true){
						location.reload()
					}else{
						alert("hubo un error al eliminar la imagen")
					}
				})
			}
		}
	</script>
@endsection