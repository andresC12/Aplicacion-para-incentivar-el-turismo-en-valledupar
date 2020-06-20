@extends('web.layout')
@section('content')
 <h1 class="h3 mb-4 text-gray-800">Usuarios</h1>
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
              <h6 class="m-0 font-weight-bold text-primary">Gestiona tus usuarios &nbsp;&nbsp;<a style="color: white !important;" class="btn btn-primary" onclick="abrir_modal_usuario()"><i class="fa fa-plus"></i> Nuevo</a></h6> 
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Id</th>
                      <th>Identificacion</th>
                      <th>Nombres</th>
                      <th>Apellidos</th>
                      <th>Email</th>
                      <th>Usuario</th>
                      <th>Clave</th>
                      <th>Opciones</th>
                    </tr>
                  </thead>
                  <tbody>
                   @foreach ($personas as $usuario)
                    <tr>
                      <td>{{ $usuario->id_persona }}</td>
                      <td>{{ $usuario->identificacion }}</td>
                      <td>{{ $usuario->nombres }}</td>
                      <td>{{ $usuario->apellidos }}</td>
                      <td>{{ $usuario->email }}</td>
                      <td>{{ $usuario->usuario }}</td>
                      <td>{{ $usuario->clave }}</td>
                      <td>
                      	<center>
                          
                      	<a onclick="abrir_modal_usuario_editar({{ $usuario->id_persona }},'{{ $usuario->identificacion }}','{{ $usuario->nombres }}','{{ $usuario->apellidos }}','{{ $usuario->email }}','{{ $usuario->usuario}}','{{ $usuario->clave}}')" title="Editar"><i class="fa fa-edit"></i></a> &nbsp;&nbsp;<a href="{{ route('usuario/eliminar', $usuario->id_persona) }}" title="Eliminar"><i class="fa fa-times"></i></a> 
                      </center>
                      	</td>
                    </tr>                           
                                                
                   @endforeach
                    
                   
                  </tbody>
                </table>
              </div>
            </div>
          </div>


    	<div class="modal" tabindex="-1" role="dialog" id="modal_usuario">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">Usuario</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form action="{{ route('usuario/guardar') }}" method="POST" enctype="multipart/form-data">
		      	@csrf
		      <div class="modal-body" >
		       	 
                        <input type="hidden" class="form-control form-control-user"  name="id_persona" id="id_persona">
                       
                  
                  <div class="form-group">
                    <label>Identificacion</label>
                          <input type="text" class="form-control form-control-user" name="identificacion" id="identificacion" required>
                        </div>
		       	  		<div class="form-group">
		       	  			<label>Nombres</label>
                        	<input type="text" class="form-control form-control-user" name="nombres" id="nombres" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Apellidos</label>
                        	<input type="text" class="form-control form-control-user" name="apellidos" id="apellidos" required>
                        </div>
                        <div class="form-group">
		       	  			<label>Email</label>
                        	<input type="text" class="form-control form-control-user" name="email" id="email"  required>
                        </div>
                         <div class="form-group">
                          <label>Usuario</label>
                          <input type="text" class="form-control form-control-user" name="usuario" id="usuario">
                        </div>
                        <div class="form-group">
                          <label>Clave</label>
                          <input type="text" class="form-control form-control-user" name="clave" id="clave">
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
			function abrir_modal_usuario() {
        $("#img").fadeOut()
				$("#modal_usuario").modal("show")
       
			}
			function abrir_modal_usuario_editar(id_persona,identificacion, nombres, apellidos, email,usuario, clave) {
				$("#id_persona").val(id_persona)
        $("#identificacion").val(identificacion)
        $("#nombres").val(nombres)
				$("#apellidos").val(apellidos)
        $("#email").val(email)
        $("#usuario").val(usuario)
        $("#clave").val(clave)
        $("#modal_usuario").modal("show")
			}
		</script>
@endsection