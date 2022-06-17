package datosRatos;

import datosRatos.Validaciones;
import listaEntidad.Coincidencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Validaciones {

	public String cambiarFormatoFecha(String fecha) {
		String[] arreglo = fecha.split("-");
		String anio = arreglo[0];
		String mes = arreglo[1];
		String dia = arreglo[2];

		dia = dia.substring(0, 2);
		mes = mes.substring(0, 2);
		anio = anio.substring(0, 4);
		return dia + "%2F" + mes + "%2F" + anio;
	}

	public String pruebaMatchId() {
		Connection conex = null;
		Conexion2 c = new Conexion2();
		Statement instruccion;
		ResultSet conjuntoResultados = null;
		try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+Validaciones.class.getName()+"pruebaMatchId() "+e);
		}
		
		try {
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT * from avcoincidencias where cliente_id='A-888888';";
			conjuntoResultados = instruccion.executeQuery(consulta);
			conex.close();
			if (conjuntoResultados.next())
				return conjuntoResultados.getString("info");
		} catch (SQLException e) {
			System.out.println("ERROR sql pruebaMatchId "+Validaciones.class.getName()+"pruebaMatchId() "+e);
		}
		
		return null;
	}

	public String[] recortarCoincidencia(String json) {

		json = json.substring(34, json.length() - 4);
		String jsons[] = json.split("}");
		System.out.println("tamaño " + jsons.length);
		for (int i = 0; i < jsons.length; i++) {
			jsons[i] = jsons[i] + "}";
			if (jsons[i].charAt(0) == ',')
				jsons[i] = jsons[i].substring(1);
			if (jsons[i].charAt(0) != '{')
				jsons[i] = '{' + jsons[i];
			System.out.println(jsons[i]);
		}
		return jsons;
	}

	public String obtenerMatchId(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json1 = (JSONObject) parser.parse(json);

		return (String) json1.get("id") + "";
	}

	public String obtenerDatosJsonFisico(String json) throws ParseException {

		JSONParser parser = new JSONParser();
		JSONObject json1 = (JSONObject) parser.parse(json);

		// return (String) json1.get("id")+"";

		String s = "";
		s += "FirstName: " + json1.get("firstName") + "\n";
		s += "lastName: " + json1.get("lastName") + "\n";
		s += "aliases: " + json1.get("aliases") + "\n";
		s += "dob: " + json1.get("dob") + "\n";
		s += "placeBirth: " + json1.get("placeBirth") + "\n";
		s += "locations: " + json1.get("locations") + "\n";
		s += "curp: " + json1.get("curp") + "\n";
		s += "rfc: " + json1.get("rfc") + "\n";
		s += "genero: " + json1.get("genero") + "\n";
		return s;

	}

	public String obtenerJsonMoral(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json1 = (JSONObject) parser.parse(json);

		String s = "";
		s += "LastName :" + json1.get("lastName") + "\n";
		s += "RFC :" + json1.get("rfc") + "\n";
		s += "Domicilio" + json1.get("locations") + "\n";
		return s;
	}

	public static void main(String args[]) throws ParseException, SQLException {
		Validaciones v = new Validaciones();
		// System.out.println(v.cambiarFormatoFecha("1996-02-04 00:00:00"));
		// System.out.println(v.obtenerMatchId(v.pruebaMatchId()));
		System.out.println(v.obtenerDatosJsonFisico(v.pruebaMatchId()));
		// System.out.println((v.pruebaMatchId()));
		// System.out.println(v.recortarCoincidencia(v.pruebaMatchId()));
	}

}