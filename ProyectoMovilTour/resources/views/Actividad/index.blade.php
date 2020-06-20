@extends('web.layout')
@section('content')
 <h1 class="h3 mb-4 text-gray-800">Actividades</h1>
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
              <h6 class="m-0 font-weight-bold text-primary">Gestiona tus actividades &nbsp;&nbsp;<a style="color: white !important;" class="btn btn-primary" onclick="abrir_modal_actividad()"><i class="fa fa-plus"></i> Nuevo</a></h6> 
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Id</th>
                      <th>Nombre</th>
                      <th>Descripcion</th>
                      <th>Fecha</th>
                      <th>Opciones</th>
                    </tr>
                  </thead>
                  <tbody>
                   @foreach ($actividades as $actividad)
                    <tr>
                      <td>{{ $actividad->id_actividad }}</td>
                      <td>{{ $actividad->nombre }}</td>
                      <td>{{ $actividad->descripcion }}</td>
                      <td>{{ date('d/m/Y H:i',strtotime($actividad->fecha)) }}</td>
                      <td>
                      	<center>
                          
                      	<a onclick="abrir_modal_actividad_editar({{ $actividad->id_actividad }},'{{ $actividad->nombre }}','{{ $actividad->descripcion }}','{{ date('Y-m-d H:i', strtotime($actividad->fecha)) }}','{{ $actividad->imagen}}')" title="Editar"><i class="fa fa-edit"></i></a> &nbsp;&nbsp;<a href="{{ route('actividad/eliminar', $actividad->id_actividad) }}" title="Eliminar"><i class="fa fa-times"></i></a> 
                      </center>
                      	</td>
                    </tr>                           
                                                
                   @endforeach
                    
                   
                  </tbody>
                </table>
              </div>
            </div>
          </div>


    	<div class="modal" tabindex="-1" role="dialog" id="modal_actividad">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Actividad</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form action="{{ route('actividad/guardar') }}" method="POST" enctype="multipart/form-data">
		      	@csrf
		      <div class="modal-body" >
		       	 
                        <input type="hidden" class="form-control form-control-user"  name="id_actividad" id="id_actividad">
                        <center>
                          <img title="Imagen actual" id="img" style="border: solid 2px #000;" width="auto" height="100" >
                        </center>
                  
		       	  		<div class="form-group">
		       	  			<label>Nombre</label>
                        	<input type="text" class="form-control form-control-user" name="nombre" id="nombre" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Descripcion</label>
                        	<input type="text" class="form-control form-control-user" name="descripcion" id="descripcion" required>
                        </div>
                        <div class="form-group">
		       	  			    <label>Fecha</label>
                        	<input type="datetime-local" class="form-control form-control-user" name="fecha" id="fecha"  required>
                        </div>
                         <div class="form-group">
                          <label>Imagen</label>
                          <input type="file" class="form-control form-control-user" name="imagen" id="imagen">
                        </div>
                        <div class="form-group">
		       	  			<label>Sitios</label>
                    <div id="select_sitios">
                     <select class="form-control hasDatepicker form-control-line" id="sitios" name="sitios[]"  multiple="multiple">
                        
                        @foreach ($sitios as $sitio)
                          <option value="{{ $sitio->id_sitio }}">{{ $sitio->nombre }}</option>
                         @endforeach
                    </select>
                    </div>
                    <script type="text/javascript">
                    
                      $(document).ready(function() {
                         $("#sitios").select2({
                           width : '100%',
                          })
                      })
                    </script>   
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
			function abrir_modal_actividad() {
        $("#img").fadeOut()
				$("#modal_actividad").modal("show")
       
			}
			function abrir_modal_actividad_editar(id_actividad, nombre, descripcion, fecha,imagen) {
				$("#id_actividad").val(id_actividad)
				$("#nombre").val(nombre)
				$("#descripcion").val(descripcion)
				$("#fecha").val(fecha.replace(' ', 'T'))
        if(imagen != ""){
           $("#img").attr("src","/imagenes/actividades/"+imagen);
           $("#img").fadeIn()
        }else{
          $("#img").fadeOut()
        }

        var url = "buscarSitios/"+id_actividad
         var html_sitios = ""
        $.get(url, (response) => {
         
          @foreach ($sitios as $sitio)
          var selected = ""
          response.sitios.forEach((sitio)=>{
            if(sitio.id_sitio == {{ $sitio->id_sitio }}) selected = "selected"
          })
          html_sitios += "<option "+selected+" value="+{{ $sitio->id_sitio }}+">"+'{{ $sitio->nombre }}'+"</option>"
          @endforeach
          if (html_sitios != ""){
          $("#select_sitios").html('<select class="form-control hasDatepicker form-control-line" id="sitios" name="sitios[]"'+
            'multiple="multiple">'+html_sitios+'</select>'+'')
          $("#sitios").select2({
                           width : '100%',
                          })
        } 
        
        $("#modal_actividad").modal("show")
        })
        
			}
		</script>
@endsection