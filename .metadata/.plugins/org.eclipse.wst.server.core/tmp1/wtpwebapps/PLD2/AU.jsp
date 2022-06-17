<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<script>
        
        function validarFormulario(){
            
            var txtCorreo = document.getElementById('correo').value;
            var txtRFC = document.getElementById('rfc').value;
            objRFC = document.getElementById('rfc');
            
            
            if(txtCorreo == null || txtCorreo.length == 0 || /^\s+$/.test(txtCorreo)){
                alert('El corre electrónico no puede dejarse en blanco')
                return false;
            }        
            
            //Que el correo tenga formato correcto
             
            object = document.getElementById('correo');
            valueForm = object.value;
            
            // Patron para el correo
            var patron=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
            if(valueForm.search(patron)==0)
            {
                    //Mail correcto
                    // object.style.color="#000";
                    
            } else {
            //Mail incorrecto
               //object.style.color="#f00";
                alert('El correo electrónico debe tener un formato válido');
                return false;
            }
            
            
            //Que el correo no pertenezca a efectivale
            
            var n = txtCorreo.search('efectivale');
            var m = txtCorreo.search('fleetcor.com');
            if ( n  > 0 || m > 0){
                alert('El correo electrónico no debe pertenecer a Efectivale ni a Fleetcor');
                return false;
            }
            
            
            
            //Validacon de la estructura del RFC
            if(txtRFC == null || txtRFC.length == 0 || /^\s+$/.test(txtRFC)){
                alert('El RFC no puede dejarse en blanco')
                return false;
            }
            
            if ( !rfcValido(txtRFC, true)){
                
                
                //objRFC.style.color="#f00";
                alert('El RFC debe tener un formato válido')
                return false;
                
            }
            
            
        }//fin de la funcion validarFormulario
        
        
 //FunciÃ³n para validar un RFC
// Devuelve el RFC sin espacios ni guiones si es correcto
// Devuelve false si es invÃ¡lido
// (debe estar en mayÃºsculas, guiones y espacios intermedios opcionales)
function rfcValido(rfc, aceptarGenerico = true) {
        const re       = /^([A-ZÃ‘&]{3,4}) ?(?:- ?)?(\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])) ?(?:- ?)?([A-Z\d]{2})([A\d])$/;
        var   validado = rfc.match(re);

        if (!validado)  //Coincide con el formato general del regex?
            return false;

        //Separar el dÃ­gito verificador del resto del RFC
        const digitoVerificador = validado.pop(),
              rfcSinDigito      = validado.slice(1).join(''),
              len               = rfcSinDigito.length,

        //Obtener el digito esperado
              diccionario       = "0123456789ABCDEFGHIJKLMN&OPQRSTUVWXYZ Ã‘",
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

        //El dÃ­gito verificador coincide con el esperado?
        // o es un RFC GenÃ©rico (ventas a pÃºblico general)?
        if ((digitoVerificador != digitoEsperado)
         && (!aceptarGenerico || rfcSinDigito + digitoVerificador != "XAXX010101000"))
            return false;
        else if (!aceptarGenerico && rfcSinDigito + digitoVerificador == "XEXX010101000")
            return false;
        return rfcSinDigito + digitoVerificador;
}


//Handler para el evento cuando cambia el input
// -Lleva la RFC a mayÃºsculas para validarlo
// -Elimina los espacios que pueda tener antes o despuÃ©s
function validarInput(input) {
    var rfc         = input.value.trim().toUpperCase(),
        resultado   = document.getElementById("resultado"),
        valido;
        
    var rfcCorrecto = rfcValido(rfc);   // â¬…ï¸ AcÃ¡ se comprueba
  
    if (rfcCorrecto) {
    	valido = "Válido";
      resultado.classList.add("ok");
    } else {
    	valido = "No vÃ¡lido"
    	resultado.classList.remove("ok");
    }
        
    resultado.innerText = "RFC: " + rfc 
                        + "\nResultado: " + rfcCorrecto
                        + "\nFormato: " + valido;
}


$('#usuario').on("input", function() {
  var dInput = this.value;
  console.log(dInput);
  alert(dInput);
});     
    </script>


</head>
<body>
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
					<h2 class="text-muted">Alta de prospectos</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%
						HttpSession sesion = request.getSession();
						if (sesion.getAttribute("usuario") != null) {
							out.println(request.getSession().getAttribute("usuario").toString());
						} else {
							response.sendRedirect("logout.jsp");
						}
					%>
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarFormulario()"
						action="registroProspecto" method="post">
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
										<input class="" type="text" name="usuario" id="usuario"
											value="" onkeypress="mascaraUsuario()"
											onchange="mascaraUsuario()" placeholder="A-">

									</div>
								</td>
								<td width="30px"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="md-form form-sm">
										<input class="form-control" type="text" name="nombre"
											id="nombre" size="80" value=""> <label for="nombre"
											class=""> * Nombre</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" name="correo"
											id="correo" value=""> <label for="correo" class="">
											* Correo electrónico:</label>
									</div>
								</td>
								<td></td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" name="rfc" id="rfc"
											value=""> <label for="rfc" class=""> * RFC</label>
									</div>
								</td>
							</tr>
							<tr>
								<td><select class="browser-default" name="" id="option"
									disabled>
										<option value="" selected>Supervisor 1</option>
										<option value="">Supervisor 2</option>
										<option value="">Supervisor 3</option>
								</select></td>
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
									<button class="btn btn-danger">Guardar</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="col-md-3">
					<p>
						<a>Alta de prospectos</a><br /> <a href="logout.jsp">Salir</a>
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
							target="_top"> TÃ©rminos y Condiciones </a></li>
					</ul>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery-3.3.1.js"></script>
		<script src="js/mdb.min.js.descarga"></script>
		<script>
            var txtExito = document.getElementById('exito').value;
            var txtRespuesta = document.getElementById('respuesta').value;
            if ( txtExito == '1' && txtRespuesta.length > 0 ){
                alert (txtRespuesta);
            }
        </script>

		<script>
    function  mascaraUsuario(){
        
        txtUsuario = document.getElementById('usuario').value;
        txtPrefijo = 'A-';
        txtUsuario = txtUsuario.replace('A-', '');
        txtUsuario2 = txtUsuario.replace('A-', '');
        document.getElementById('usuario').value = txtPrefijo + txtUsuario2; 
    }
</script>
</body>
</html>