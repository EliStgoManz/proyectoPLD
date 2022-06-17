<%@page import="entidad.Perfil"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="datosRatos.DatosSupervisor"%>
<%@page import="entidad.Supervisor"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="utilidades.Mensajes"%>
<%@ include file="valida_login.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    //VALIDACION DEL PERFIL DE USUARIO
    HttpSession sesion = request.getSession();
    String perfilId = null;
    
    try{
        perfilId = sesion.getAttribute("perfilId").toString();
        Perfil perfil = (Perfil)sesion.getAttribute("perfil");
        request.setAttribute("permiso", perfil.getProspectos());
        if ( perfil.getProspectos().equals("N")){
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
<meta charset="UTF-8">
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
							<h1 class="title title-contrata-ahora">Sistema de PrevenciÛn
								de Lavado de Dinero</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<h5 class="title title-contrata-ahora white-text">
		Sistema de PrevenciÛn de Lavado de Dinero
		</h1>
		<div class="container">
			<div class="main row">
				<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
					<h2 class="text-muted">Alta de prospectos</h2>
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
					<form onsubmit="return validarFormulario()"
						action="registroProspecto" method="post" autocomplete="off">
						<input type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />" /> <input type="hidden"
							name="exito" id="exito" value="<c:out value="${exito}" />" />
						<table border="0">
							<tr>
								<td colspan="5">
									<h4>Datos del cliente</h4>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="" type="text" name="usuario"
											onkeypress="return esNumero(this, event)" id="usuario"
											onkeypress="return mascaraUsuario(event)"
											onkeydown="return mascaraUsuario(event)"
											onfocusout="return mascaraUsuario()" pattern="[A-Za-z]{0-9}"
											maxlength="9" value="A-">

									</div>
								</td>
								<td width="30px"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="nombre" name="nombre" size="80"
											value="<c:out value="${nombre}" />"> <label
											for="nombre" class=""> * Nombre</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="minus(this)"
											name="correo" id="correo" value="<c:out value="${correo}" />">
										<label for="correo" class=""> * Correo electrÛnico:</label>
									</div>
								</td>
								<td></td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="rfc" id="rfc" maxlength="13"
											value="<c:out value="${rfc}" />"> <label for="rfc"
											class=""> * RFC</label>
									</div>
								</td>
							</tr>
							<tr>
								<td id="celdaSupervisor" style="display: none;">
									<%
                                                        if ( sesion.getAttribute("usuario") != null){
                                                        UsuarioSistema us = (UsuarioSistema) sesion.getAttribute("usuario");
                                                        Supervisor s = us.getSupervisor();
                                                        if ( s != null && s.getUsuario() != null && !s.getUsuario().isEmpty())
                                                            out.println("<input type=\"hidden\" name=\"idSupervisor\" id=\"idSupervisor\" value=\"" + s.getUsuario() + "\"  />");
                                                    }

                                                    
                                                %> <label
									for="supervisor" class=""> * Supervisor</label> <select
									class="browser-default" name="supervisor" id="supervisor"
									disabled> Ò
										<% 
                                                                   Supervisor[] s = new DatosSupervisor().getList();
                                                                    //Verificamos que tengamos paÌses depuÈs de la consulta a la base de datos
                                                                    if ( s != null) {
                                                                        for ( int i = 0; i < s.length; i++ ){
                                                                            out.println("<option value=\"" + s[i].getUsuario() + "\">" + s[i].getNombre() + "</option>");
                                                                        }  
                                                                    }
                                                                                                                                        
                                                                %>
								</select>

								</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>
									<button class="btn btn-danger" id="btnGurdar">Guardar</button>
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
							target="_top"> TÈrminos y Condiciones </a></li>
					</ul>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery-3.3.1.js"></script>
		<script src="js/validaciones.js"></script>
		<script src="js/mdb.min.js.descarga"></script>


		<script>


  /*
   * ENVIO DE MENSAJES RESULTADO DE TRANSACCIONES 
   */




      txtRespuesta = document.getElementById('respuesta').value;
      txtExito = document.getElementById('exito').value;
      if ( txtExito == '1' && txtRespuesta.length > 0 ){
          alert (txtRespuesta);
      }


  /************************************/    
  document.querySelector("#usuario").addEventListener("keypress", function(event){
	if (event.keyCode == 32){
		return false;
	}
    }, false);


  function validarFormulario(){
      
      
            
      
      var txtCorreo = document.getElementById('correo').value;
      var txtRFC = document.getElementById('rfc').value;
      objRFC = document.getElementById('rfc');
      
      
      txtIdSaleForce = document.getElementById('usuario').value;
      if(txtIdSaleForce == null || txtIdSaleForce.length <= 7 || /^\s+$/.test(txtIdSaleForce)){
          alert('La longitud del Id Sale Force debe de ser de 6 Û 7 dÌgitos');
          document.getElementById('usuario').focus();
          return false;
      }   
      
      txtnombre = document.getElementById('nombre').value;
      if(txtnombre == null || txtnombre.length == 0 || /^\s+$/.test(txtnombre)){
          alert('El nombre del prospecto no puede dejarse en blanco');
          document.getElementById('nombre').focus();
          return false;
      }   
              


      document.getElementById('correo').value = txtCorreo.toLowerCase();
      if(txtCorreo == null || txtCorreo.length == 0 || /^\s+$/.test(txtCorreo)){
          alert('El corre electrÛnico no puede dejarse en blanco');
          document.getElementById('correo').focus();
          return false;
      }        

      //Que el correo tenga formato correcto

      object = document.getElementById('correo');
      valueForm = object.value;

      // Patron para el correo
      var patron=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,6})+$/;
      if(valueForm.search(patron)==0)
      {
              //Mail correcto
              // object.style.color="#000";

      } else {
      //Mail incorrecto
         //object.style.color="#f00";
          alert('El correo electrÛnico debe tener un formato v·lido');
          document.getElementById('correo').focus();
          return false;
      }


      //Que el correo no pertenezca a efectivale

      var n = txtCorreo.search('efectivale.com.mx');      
      var m = txtCorreo.search('fleetcor.com');
      if ( n  > 0 || m > 0){
          alert('El correo electrÛnico no debe pertenecer a Efectivale ni a Fleetcor');
          return false;
      }


      //Validacon de la estructura del RFC
      if(txtRFC == null || txtRFC.length == 0 || /^\s+$/.test(txtRFC)){
          alert('El RFC no puede dejarse en blanco');
          document.getElementById('rfc').focus();
          return false;
      }

      if ( !rfcValido(txtRFC, true)){
//     	  alert('Digito : '+ digitoEsperado);
          alert('El RFC debe tener un formato v·lido');
          document.getElementById('rfc').focus();
          return false;

      }


  }//fin de la funcion validarFormulario


//Funci√≥n para validar un RFC
// Devuelve el RFC sin espacios ni guiones si es correcto
// Devuelve false si es inv√°lido
// (debe estar en may√∫sculas, guiones y espacios intermedios opcionales)
function rfcValido(rfc, aceptarGenerico = true) {
  const re       = /^([A-Z—&]{3,4}) ?(?:- ?)?(\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])) ?(?:- ?)?([A-Z\d]{2})([A\d])$/;
  var   validado = rfc.match(re);

  if (!validado)  //Coincide con el formato general del regex?
      return false;

  //Separar el d√≠gito verificador del resto del RFC
  const digitoVerificador = validado.pop(),
        rfcSinDigito      = validado.slice(1).join(''),
        len               = rfcSinDigito.length,

  //Obtener el digito esperado
        diccionario       = "0123456789ABCDEFGHIJKLMN&OPQRSTUVWXYZ —",
        indice            = len + 1;
  var   suma,
        digitoEsperado;
  

  if (len == 12) suma = 0
  else suma = 481; //Ajuste para persona moral

  for(var i=0; i<len; i++)
      suma += diccionario.indexOf(rfcSinDigito.charAt(i)) * (indice - i);
  digitoEsperado = 11 - suma % 11;
  if (digitoEsperado == 11) digitoEsperado = 0;
  else if (digitoEsperado == 10) digitoEsperado = "A";

  //El d√≠gito verificador coincide con el esperado?
  // o es un RFC Gen√©rico (ventas a p√∫blico general)?
  <% if(sesion.getAttribute("perfilId").toString().compareTo("2") == 0) { %>
//         alert("El digito correcto es: " + digitoEsperado);
  <% } %>
  if ((digitoVerificador != digitoEsperado)
   && (!aceptarGenerico || rfcSinDigito + digitoVerificador != "XAXX010101000"))
      return false;
  else if (!aceptarGenerico && rfcSinDigito + digitoVerificador == "XEXX010101000")
      return false;
 
  return rfcSinDigito + digitoVerificador;
}


//Handler para el evento cuando cambia el input
// -Lleva la RFC a may√∫sculas para validarlo
// -Elimina los espacios que pueda tener antes o despu√©s
function validarInput(input) {
var rfc         = input.value.trim().toUpperCase(),
  resultado   = document.getElementById("resultado"),
  valido;

var rfcCorrecto = rfcValido(rfc);   // ‚¨ÖÔ∏è Ac√° se comprueba

if (rfcCorrecto) {
  valido = "V·lido";
resultado.classList.add("ok");
} else {
  valido = "No v·lido"
  resultado.classList.remove("ok");
}

resultado.innerText = "RFC: " + rfc 
                  + "\nResultado: " + rfcCorrecto
                  + "\nFormato: " + valido;
}


$('#usuario').on("input", function() {
var dInput = this.value;
console.log(dInput);
//alert(dInput);
});     
</script>

		<script>
    function  mascaraUsuario(event){
        
            //Evita los espacios en blanco
            var e = event || window.event
            var evento_key = e.keyCode || e.which        
            if ( evento_key == 32){
                   return false;
            }
            
            var x = document.getElementById('usuario').value;
            document.getElementById('usuario').value = x.trim();

            var exp = new RegExp(/^\s+|\s+$/);
            var web = document.getElementById('usuario').value;
            if(exp.test(web)){
                    return false;
            }
            
            

            txtUsuario = document.getElementById('usuario').value;
            txtPrefijo = 'A-';
            txtUsuario = txtUsuario.replace('A-', '');
            txtUsuario2 = txtUsuario.replace('A-', '');
            txtUsuario3 = txtUsuario2.replace('A', '');
            txtUsuario4 = txtUsuario3.replace('-', '');
            document.getElementById('usuario').value = txtPrefijo + txtUsuario4; 
            
        
        
        
    }
</script>


		<script>
var idleTime = 0;
$( document ).ready(function() {
    
    //Asignando permisos de perfil de usuario
    document.getElementById('btnGurdar').disabled = true;
    txtPermiso = '${permiso}';
    if ( txtPermiso == 'E' ){
        document.getElementById('btnGurdar').disabled = false;
    }
    
    //ASIGNANDO EL SUPERVISOR        
     txtSupervisor = document.getElementById('idSupervisor').value;
    if(txtSupervisor != null || txtSupervisor.length > 0){    
        
        var selector = document.getElementById('supervisor');
        for ( var i = 0; i<selector.length; i++){
            if ( txtSupervisor == selector.options[i].value ) {
                selector.selectedIndex = i; 
                document.getElementById('celdaSupervisor').style.display='block';
                break;
            }else {//if
                selector.selectedIndex = 0;
            }
        }//for 
    }
    
});
</script>
</body>


</html>