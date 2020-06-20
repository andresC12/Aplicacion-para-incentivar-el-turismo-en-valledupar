@extends('web.layout')
@section('content')
 <h1 class="h3 mb-4 text-gray-800">Eventos</h1>
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
              <h6 class="m-0 font-weight-bold text-primary">Gestiona tus eventos &nbsp;&nbsp;<a style="color: white !important;" class="btn btn-primary" onclick="abrir_modal_evento()"><i class="fa fa-plus"></i> Nuevo</a></h6> 
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Id</th>
                      <th>Nombre</th>
                      <th>Descripcion</th>
                      <th>Fecha Inicio</th>
                      <th>Fecha Fin</th>
                      <th>Opciones</th>
                    </tr>
                  </thead>
                  <tbody>
                   @foreach ($eventos as $evento)
                    <tr>
                      <td>{{ $evento->id_evento }}</td>
                      <td>{{ $evento->nombre }}</td>
                      <td>{{ $evento->descripcion }}</td>
                      <td>{{ date('d/m/Y H:i',strtotime($evento->fecha_inicio)) }}</td>
                      <td>@if($evento->fecha_fin)
                        {{ date('d/m/Y H:i',strtotime($evento->fecha_fin)) }}
                         @endif
                      </td>
                      <td>
                      	<center>
                          @php
                            $fecha_fin = null;
                            if($evento->fecha_fin) $fecha_fin = date('Y-m-d H:i', strtotime($evento->fecha_fin));
                          @endphp
                      	<a onclick="abrir_modal_evento_editar({{ $evento->id_evento }},'{{ $evento->nombre }}','{{ $evento->descripcion }}','{{ date('Y-m-d H:i', strtotime($evento->fecha_inicio)) }}','{{ $fecha_fin }}','{{ $evento->imagen}}')" title="Editar"><i class="fa fa-edit"></i></a> &nbsp;&nbsp;<a href="{{ route('evento/eliminar', $evento->id_evento) }}" title="Eliminar"><i class="fa fa-times"></i></a> 
                      </center>
                      	</td>
                    </tr>                           
                                                
                   @endforeach
                    
                   
                  </tbody>
                </table>
              </div>
            </div>
          </div>


    	<div class="modal" tabindex="-1" role="dialog" id="modal_evento">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Evento</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form action="{{ route('evento/guardar') }}" method="POST" enctype="multipart/form-data">
		      	@csrf
		      <div class="modal-body" >
		       	 
                        <input type="hidden" class="form-control form-control-user"  name="id_evento" id="id_evento">
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
		       	  			    <label>Fecha Inicio</label>
                        	<input type="datetime-local" class="form-control form-control-user" name="fecha_inicio" id="fecha_inicio"  required>
                        </div>
                        <div class="form-group">
		       	  			<label>Fecha Fin</label>
                        	<input type="datetime-local" class="form-control form-control-user" name="fecha_fin" id="fecha_fin">
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
			function abrir_modal_evento() {
        $("#img").fadeOut()
				$("#modal_evento").modal("show")
       
			}
			function abrir_modal_evento_editar(id_evento, nombre, descripcion, fecha_inicio, fecha_fin,imagen) {
				$("#id_evento").val(id_evento)
				$("#nombre").val(nombre)
				$("#descripcion").val(descripcion)
				$("#fecha_inicio").val(fecha_inicio.replace(' ', 'T'))
				$("#fecha_fin").val(fecha_fin.replace(' ', 'T'))
        if(imagen != ""){
           $("#img").attr("src","/imagenes/eventos/"+imagen);
           $("#img").fadeIn()
        }else{
          $("#img").fadeOut()
        }

        var url = "buscarSitios/"+id_evento
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
        
        $("#modal_evento").modal("show")
        })
        
			}
		</script>
@endsection