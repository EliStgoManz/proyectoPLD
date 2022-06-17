<%@page import="utilidades.Mensajes"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@ include file="valida_login.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="datosRatos.DatosUsuarioRatos"%>
<%@page import="entidad.UsuarioSistema"%>
<%@page import="entidad.Perfil"%>
<%@page import="datosRatos.DatosPerfil"%>
<%
    //VALIDACION DEL PERFIL DE USUARIO
    HttpSession sesion = request.getSession();
    String perfilId = null;
    
    try{
        perfilId = sesion.getAttribute("perfilId").toString();
        Perfil perfil = (Perfil)sesion.getAttribute("perfil");
        request.setAttribute("permiso", perfil.getUsuarios());
        if ( perfil.getVerificacion().equals("N"))
        { 
            Mensajes.setMensaje("No tiene persmisos para ver esta pantalla.");
            request.setAttribute("resultado", Mensajes.mensaje);
            request.setAttribute("exito", "1");
            request.getRequestDispatcher("/mensajes.jsp").forward(request, response);
            
        }
    } catch (Exception es){
        System.out.println("Es un perfil de cliente");
        response.sendRedirect("/tipo_persona.jsp");
    }
    
%>
<!DOCTYPE html>
<html>
<head>
<title>Efectivale - PLD</title>
<meta name="viewport"
	content="widht=device-widht, user-scalable=no, initial-scale=1.0, minimun-scale=1.0">
<meta http-equiv="Content-type" content="text/html; charset=utf-8;" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">
<!-- Bootstrap core CSS -->
<link href="./css/bootstrap.min.css" rel="stylesheet">
<!-- Material Design Bootstrap **** -->
<link href="./css/mdb.min.css" rel="stylesheet">
<!-- Your custom styles (optional) -->
<link href="./css/style.css" rel="stylesheet">
<link href="./css/forms.css" rel="stylesheet">
<link href="./css/xlarge.css" rel="stylesheet">
<link href="./css/large.css" rel="stylesheet">
<link href="./css/medium.css" rel="stylesheet">
<link href="./css/small.css" rel="stylesheet">
<link href="./css/xsmall.css" rel="stylesheet">
</head>
<body onload="startTimer(15)">
	<header id="header">
		<div class="container-fluid">
			<div class="row align-items-center pt-2 pb-2">
				<div class="col-12 col-sm-12 col-md-4 col-lg-2 col-xl-3">
					<a class="navbar-brand" href="/"> <img
						src="./img/logo-main.png" width="398px"
						class="img-fluid float-left logo mr-auto" style="maxwidth: 160px;"
						alt="Logo Efectivale">
					</a>
				</div>
				<div class="col-12 col-sm-12 col-md-5 col-lg-8 col-xl-7">
					<div class="row">
						<div class="col-12 header-contact-numbers">
							<h1 class="title title-contrata-ahora">Sistema de Prevención
								de Lavado de Dinero</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<h5 class="title title-contrata-ahora white-text">
		Sistema de Prevención de Lavado de Dinero
		</h1>
		<div class="container">
			<div class="main row">
				<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
					<h2 class="text-muted">Edición de Representantes PLD</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@ include file="detectausuario.jsp"%>
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<!-- SERVLET ControlCatalogoRepresentante -->
					<form action="UsuariosRepre" method="post"
						onsubmit="return  validarFormulario()" class="" autocomplete="off">
						<br> <input id="perfilId" name="perfilId" type="hidden"
							value="<c:out value="${perfilId}" />"> <input
							id="supervisorId" name="supervisorId" type="hidden"
							value="<c:out value="${supervisor}" />"> <input
							id="esEdicion" name="esEdicion" type="hidden"
							value="<c:out value="${esEdicion}" />">


						<table border="0">
							<tr>
								<td colspan=""></td>
							</tr>
							<tr>
								<td>


									<div class="md-form form-sm">

										<input type="text" class="form-control pl-2"
											onkeyup="mayus(this)" name="usuario" maxlength="50"
											id="usuario" value="<c:out value="${usuario}" />"
											data-validation-error-msg="Ingrese un nombre de usuario"
											data-validation="required length"
											data-validation-length="max50"> <label for="usuario">*
											Numero de Empleado</label>
									</div>

								</td>
								<td width="30px"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="10">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="nombre" name="nombre" value="<c:out value="${nombre}" />">
										<label for="nombre">* Nombre completo </label>
									</div>
									<div class="md-form form-sm">
										<input class="form-control" type="text"
											id="email" name="email" value="<c:out value="${email}" />">
										<label for="email">* Correo </label>
									</div>
								</td>
							</tr>

								<td></td>
								<td></td>
								<td></td>
								<td>
									<button class="btn btn-danger" id="btnGuardar">Guardar</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="col-md-3">
					<p>
						<%@ include file="despliega_menu.jsp"%>


					</p>
				</div>
			</div>
		</div>
		<footer>
			<div class="row footer-menu-bottom">
				<div class="col-12">
					<ul>
						<li>Todos los Derechos Reservados Efectivale S. de R.L</li>
						<li><a
							href="http://www.efectivale.com/comunicados/comunicado_LAAT.htm"
							target="_top"> Disposiciones Vigentes de la LAAT </a></li>
						<li><a
							href="http://www.efectivale.com.mx/avisos/avisos_de_privacidad.html"
							target="_top"> Aviso de Privacidad </a></li>
						<li><a
							href="http://www.efectivale.com.mx/terminos/TCGCLIENTESDespensa-Comida.pdf"
							target="_top"> Términos y Condiciones </a></li>
					</ul>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/validaciones.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/mdb.min.js.descarga"></script>

		<script>
            function validarFormulario(){
            txtUsuario = document.getElementById('usuario').value;
            if(txtUsuario == null || txtUsuario.length == 0 || /^\s+$/.test(txtUsuario)){
                alert('El usuario no puede dejarse en blanco');
                return false;
            }
            
            txtNombre = document.getElementById('nombre').value;
            if(txtNombre == null || txtNombre.length == 0 || /^\s+$/.test(txtNombre)){
                alert('El nombre no puede dejarse en blanco');
                return false;
            }
            
            txtPerfil = document.getElementById("perfil").value;
            if(txtPerfil == null || txtPerfil.length == 0 || /^\s+$/.test(txtPerfil)){
                alert('Debe seleccionar un perfil de usuario');
                return false;
            }
            
            
            txtCorreo = document.getElementById("correo").value;
            if(txtCorreo == null || txtCorreo.length == 0 || /^\s+$/.test(txtCorreo)){
                alert('El correo no puede dejarse en blanco');
                return false;
            }
            
            
                 //Que el correo no pertenezca a efectivale

      var n = txtCorreo.search('efectivale.com.mx');
      if ( n  < 0 ){
          alert('El correo electrónico debe pertenecer a Efectivale');
          document.getElementById('correo').focus();
          return false;
      }
      
      
      
            }//funcion validarFormulario
        </script>


		<script>
            
            const PERFIL_ADMINISTRADOR_TELEMARKETING = 3;
            const PERFIL_SUPERVISOR = 4;
            const PERFIL_EJECUTIVO_DE_VENTAS = 5;
            
            $(document).ready(function(){ //lo que pasa cuándo se ha cargaro la lágina
                //Asgiando permisos de usuario
                    //document.getElementById('btnGuardar').disabled = true;
                    document.getElementById("btnGuardar").style.visibility = 'hidden';
                   
                    
                    txtPermiso = '${permiso}';
                    if ( txtPermiso == 'E'){
                    document.getElementById("btnGuardar").style.visibility = 'visible'; 
                }
                
                
                ////Seteando el valor del supervidor
                esEdicion = document.getElementById('esEdicion').value;
                if  ( esEdicion == 1 ){ //la carga de la página proviene de una edición del usuario y no de un nuevo usuario
                    
                    
            
            
                    txtSupervisor = document.getElementById('supervisorId').value; //llega de la respuesta
                    if(txtSupervisor != null || txtSupervisor.length > 0){                          
                        var selector = document.getElementById('supervisor');
                        
                        
                        for ( var i = 0; i<selector.length; i++){
                            if ( txtSupervisor == selector.options[i].value ) {
                                selector.selectedIndex = i; 
                                
                                break;
                            }else {//if
                                selector.selectedIndex = 0;
                            }
                        }//for 
                    } // if si existe el supervisor
                    
                    
                    
                    
                    
                    txtPerfil = document.getElementById('perfilId').value;
                    if(txtPerfil != null || txtPerfil.length > 0){                          
                        
                        var selector2 = document.getElementById('perfil');
                        
                        for ( var i = 0; i<selector2.length; i++){
                            if ( txtPerfil == selector2.options[i].value ) {
                                selector2.selectedIndex = i;   
                                //alert ( document.getElementById('perfil').value);
                                document.getElementById('perfil').value = txtPerfil;
                                //alert ( document.getElementById('perfil').value);
                                
                                break;
                            }else {//if
                                selector2.selectedIndex = 0;
                            }
                        }//for 
                        
                        
                     //El campo supervisor solo aparecerá para usuarios que su perfil sea Ejecutivo de ventas.
                        celdaSupervisor = document.getElementById('celdaSupervisor');
                        
                        if ( txtPerfil == PERFIL_EJECUTIVO_DE_VENTAS ){
                             celdaSupervisor.style.display = 'block';
                        } else {
                             celdaSupervisor.style.display = 'none';
                            
                        }
                    
                    
                        //El perfil de administrador de telemarketing debe de tener la capacidad de modificar el supervisor de todos 
                        //los ejecutivos sin cambiar ningún otro dato.
                        txtPerfilUsuarioSistema = "";
                        <%
                            String perfil = (String) sesion.getAttribute("perfilId");    
                            out.println( "txtPerfilUsuarioSistema ='" + perfil + "';" );
                        %>
			//if ( txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR_TELEMARKETING && txtPerfil == PERFIL_EJECUTIVO_DE_VENTAS ) {
										if (txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR_TELEMARKETING) {
											document.getElementById("usuario").readOnly = true;
											document.getElementById("nombre").readOnly = true;
											document.getElementById("correo").readOnly = true;
											$("#perfil").prop("disabled", true);
											document
													.getElementById("celdaPerfil").disabled = true;

											//alert ( document.getElementById('perfil').value);

										}//FIN DE LA VALIDACIÓN DE SI SON LOS PERFILES DE LA REGLA

									} // if si existe el supervisor

								} else {
									document.getElementById('usuario').value = "";

								}//fin del if si se trata de una edición proveniendo de la consutla de usuario.jsp

							});

			function getPerfil() {
				//alert('cambiando perfil');
				txtPerfilId = document.getElementById("perfil").value;
				document.getElementById("perfilId").value = txtPerfilId;
			}
		</script>
</body>
</html>