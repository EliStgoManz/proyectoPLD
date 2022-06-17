<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Efectivale - PLD</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="widht=device-widht, user-scalable=no, initial-scale=1.0, minimun-scale=1.0">
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
	function validarFormulario() {
		txtCorreo = document.getElementById('correo').value;
		if (txtCorreo == null || txtCorreo.length == 0
				|| /^\s+$/.test(txtCorreo)) {
			alert('El correo electrónico no debe dejarse en blanco');
			return false;
		}
	}
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
					<h2 class="text-muted">Recuperación de contraseña</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead"></p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarFormularioRecuperar()"
						action="ControlCorreo" method="post" class="">
						<input type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />" /> <input type="hidden"
							name="exito" id="exito" value="<c:out value="${exito}" />" />

						<table border="0">
							<tr>
								<td colspan="5">Ingrese la dirección de correo electrónico
									con la que se registró en el sistema<br>
								<br>
								<br>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" onkeyup="mayus(this)" type="text"
											id="usuario" name="usuario" value=""> <label
											for="correo">* usuario</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="email" onkeyup="minus(this)"
											id="correo" name="correo" value=""> <label
											for="correo">* correo electrónico</label>
									</div>
								</td>
								<td width="30px"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>
									<button class="btn btn-danger">Aceptar</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="col-md-3">
					<p></p>
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
		<script src="js/bootstrap.min.js"></script>
		<script src="js/mdb.min.js.descarga"></script>
		<script src="js/validaciones.js"></script>


		<script>
			var txtExito = document.getElementById('exito').value;
			var txtRespuesta = document.getElementById('respuesta').value;

			if (txtExito == '1' && txtRespuesta.length > 0) {
				alert(txtRespuesta);
				txtExito = '0';
			}
		</script>

		<script>
			function validarFormularioRecuperar() {
				txtusuario = document.getElementById('usuario').value;
				if (txtusuario == null || txtusuario.length == 0
						|| /^\s+$/.test(txtusuario)) {
					alert('El usuario no puede dejarse en blanco');
					document.getElementById('usuario').focus();
					return false;
				}

				txtcorreo = document.getElementById('correo').value;
				if (txtcorreo == null || txtcorreo.length == 0
						|| /^\s+$/.test(txtcorreo)) {
					alert('El correo electrónico no puede dejarse en blanco');
					document.getElementById('correo').focus();
					return false;
				}

			}
		</script>
</body>
</html>