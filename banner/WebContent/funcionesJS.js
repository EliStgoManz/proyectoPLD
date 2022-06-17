
/* ARRAY DE IMAGENES */
ads = new Array(9);
ads[0] = "http://jarroba.com/wp-content/uploads/2012/03/Modelado.png";
ads[1] = "http://jarroba.com/wp-content/uploads/2012/03/LenguajesProgramacion.png";
ads[2] = "http://jarroba.com/wp-content/uploads/2012/03/AplicacionesMoviles.png";
ads[3] = "http://jarroba.com/wp-content/uploads/2012/03/TecnologiaWeb.png";
ads[4] = "http://jarroba.com/wp-content/uploads/2012/03/Servidores.png";
ads[5] = "http://jarroba.com/wp-content/uploads/2012/03/SistemasOperativos.png";
ads[6] = "http://jarroba.com/wp-content/uploads/2012/03/BaseDeDatos.png";
ads[7] = "http://jarroba.com/wp-content/uploads/2012/03/Hadware.png";
ads[8] = "http://jarroba.com/wp-content/uploads/2012/03/InteligenciaArtificial.png";


/* ARRAY DE URLs */
arrayURLs = new Array(9);
arrayURLs[0] = "http://jarroba.com/indice-ingenieria-del-software/"
arrayURLs[1] = "http://jarroba.com/lenguajes-de-programacion/"
arrayURLs[2] = "http://jarroba.com/aplicaciones-moviles/"
arrayURLs[3] = "http://jarroba.com/tecnologias-web/"
arrayURLs[4] = "http://jarroba.com/redes/"
arrayURLs[5] = "http://jarroba.com/sistemas-operativos/"
arrayURLs[6] = "http://jarroba.com/bases-de-datos/"
arrayURLs[7] = "http://jarroba.com/diseno-hardware/"
arrayURLs[8] = "http://jarroba.com/inteligencia-artificial/"


//variable para llevar la cuenta de la imagen siguiente
var longuitudArray = ads.length;
var contador = 0
// Cojemos un numero aleatorio
contador = Math.floor((Math.random() * longuitudArray))

var tiempo = 1// En segundos
var timer = tiempo * 1000;

function banner() {
	contador++;
	contador = contador % longuitudArray
	document.banner.src = ads[contador];
	setTimeout("banner()", timer);
}

function ponerURL() {
	contador2 = contador;
	url = window.open(arrayURLs[contador2]);
	return false;
}
