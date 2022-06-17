<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.Xcss" />
	<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
	<script src="JS/jquery.autocomplete.js"></script>	
	
	<script>
	jQuery(function(){
		$("#country").autocomplete("list.jsp");
	});
   </script>
	
</head>
<body>
<br><br>
<font face="verdana" size="2">
<font size="4">Java(jsp)/jQuery Autocompletar Ejemplo :::</font>
<br><br>
	Seleccionar Ciudad :	
	<input type="text" id="country" name="country" class="input_text"/>
	
</font>
</body>
</html>