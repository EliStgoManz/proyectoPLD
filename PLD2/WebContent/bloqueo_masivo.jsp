<%@ include file="valida_login.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>Efectivale - PLD</title>
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
					<h2 class="text-muted">Mantenimiento</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%
						out.println(request.getSession().getAttribute("user"));
					%>

					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarfrmBloqueo()" action="BloqueoMasivo"
						method="post" class="" id="frmBloqueo" name="fmrBloqueo"
						enctype="multipart/form-data">
						<table border="0">
							<tr>
								<td>
									<h4>Bloqueo / desbloqueo masivo</h4>
								</td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">* XLSX con los
										numeros de cliente a bloquear</label><br /> <input type="file"
									id="archivo" name="archivo">&nbsp;<img
									src="img/lupa.jpg" height="32" width="32" />
									<p class="help-block">Un solo archivo de máximo 10MB</p></td>
							</tr>
							<tr>
								<td>
									<div class="row">
										<div class="col-12">
											<div class="md-form form-sm">
												<input type="checkbox" id="chkBloqueo" name="chkBloqueo">
												<label for="chkBloqueo" class="">Fecha de bloqueo:
												</label>
											</div>
										</div>
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><input type="date" name="fechaBloqueo"
									id="fechaBloqueo"></td>
							</tr>

							<tr>
								<td width="350"></td>
								<td width="50"></td>
								<td width="350">
									<button class="btn btn-danger">Procesar</button>
								</td>
							</tr>
						</table>
					</form>



					<hr>

					<!--PROCESANDO EL BORRADO DE PROSPECTOS-->
					<form onsubmit="return validarfrmBorrado()" action="BorradoMasivo"
						method="post" class="" id="frmBorrar" name="frmBorrar">
						<table border="0">
							<tr>
								<td>
									<h4>Borrado de prospectos</h4>
								</td>
							</tr>
							<tr>
								<td>
									<div class="row">
										<div class="col-12">
											<div class="md-form form-sm">
												<input type="checkbox" id="chkFechaBorrado"
													name="chkFechaBorrado"> <label
													for="chkFechaBorrado" class="">Fecha de borrado a
													partir del: </label>
											</div>
										</div>
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><input class="chzn-select" type="date"
									name="fechaBorrado" id="fechaBorrado"></td>
							</tr>
							<tr>
								<td width="350"></td>
								<td width="50"></td>
								<td width="350">
									<button class="btn btn-danger">Borrar</button>
								</td>
							</tr>
						</table>
					</form>



				</div>
				<div class="col-md-3">
					<p>
					<p>
						<%@ include file="despliega_menu.jsp"%>
					</p>
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
		<script src="js/validaciones.js"></script>

		<script>
			function validarfrmBloqueo() {
				alert(document.getElementById('fechaBloqueo').value);

				input = document.getElementById('archivo');
				if (input.value == null || input.value.length == 0
						|| /^\s+$/.test(input.value)) {
					alert('No ha seleccionado ningún archivo Excel (*.xlsx)');
					return false;
				} else {
					if (!validarTamanoArchivo(input, 10)) {
						alert('El peso del archivo Excel excede los 10 MB que tiene como límite nuestro sistema');
						input = null;
						return false;
					}
				}

				txtFechaBloqueo = document.getElementById('fechaBloqueo').value;
				if (txtFechaBloqueo == null || txtFechaBloqueo.length == 0
						|| /^\s+$/.test(txtFechaBloqueo)) {
					alert('No ha indicado una fecha de bloqueo')
					return false;
				}

			}
			function validarfrmBorrado() {
				txtFechaBorrado = document.getElementById('fechaBorrado').value;
				if (txtFechaBorrado == null || txtFechaBorrado.length == 0
						|| /^\s+$/.test(txtFechaBorrado)) {
					alert('No ha indicado una fecha de borrado')
					return false;
				}
			}
		</script>


		<script>
			r = '${resultado}';
			e = '${exito}';
			if (e == '1' && r.length > 0) {
				alert(r);

			}
		</script>
</body>
</html>