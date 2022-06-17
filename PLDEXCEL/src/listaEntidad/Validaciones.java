package listaEntidad;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





public class Validaciones {
	
	public String cambiarFormatoFecha(String fecha){
		//fecha= fecha.substring(0,fecha.length()-9);
		String[] arreglo= fecha.split("-");
		String anio=arreglo[0];
		String mes = arreglo[1];
		String dia= arreglo[2];
				
				dia=dia.substring(0,2);
				mes=mes.substring(0,2);
				anio=anio.substring(0,4);
		return dia+ "%2F"+mes+"%2F"+anio;
	}
	
	public String pruebaMatchId() throws SQLException{
		Connection conex = null;
	    datosRatos.Conexion2 c=new datosRatos.Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       instruccion = conex.createStatement( 
	       ResultSet.TYPE_SCROLL_INSENSITIVE,
	       ResultSet.CONCUR_READ_ONLY );
	       String consulta = "SELECT * from avcoincidencias where cliente_id='A-888888';";
	       conjuntoResultados = instruccion.executeQuery(consulta);
	       conex.close();
	       if(conjuntoResultados.next())
	    	   return conjuntoResultados.getString("info");
	       	   return null;
	}
	
	public String[] recortarCoincidencia(String json){
		
		json= json.substring(34, json.length()-4);
		String jsons[]= json.split("}");
		System.out.println("tamaño "+jsons.length);
		for(int i=0;i<jsons.length;i++){
			jsons[i]=jsons[i]+"}";
			if(jsons[i].charAt(0)==',')
				jsons[i]=jsons[i].substring(1);
			if(jsons[i].charAt(0)!='{')
				jsons[i]='{'+jsons[i];
			System.out.println(jsons[i]);
			
		}
			//System.out.println();
		return jsons;
	}
	
	
//	public String obtenerMatchId(String json) throws ParseException{
//		JSONParser parser = new JSONParser();
//		JSONObject json1 = (JSONObject) parser.parse(json);
//		JSONArray jarray;
//		json1=(JSONObject) json1.get("data");
//		jarray=(JSONArray) json1.get("matches");
//		JSONObject object =(JSONObject) jarray.get(0);
//		 
//	//	json1= json1.get("matches");
//		//json1
//		 //System.out.println(object.size());
//		return object.get("id")+"";
//	}
	
	
	public String obtenerMatchId(String json) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject json1 = (JSONObject) parser.parse(json);
	
		return (String) json1.get("id")+"";
	}
	public String obtenerDatosJsonFisico(String json) throws ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject json1 = (JSONObject) parser.parse(json);
	
		//return (String) json1.get("id")+"";
		
		
		String s="";
		s+="<strong>NOMBRE</strong>: "+json1.get("firstName")+ "  <br></br>";
		s+="<strong>APELLIDO: </strong> "+json1.get("lastName")+ " <br></br>";
		s+="<strong>ALIAS: </strong>"+json1.get("aliases")+ "<br></br>";
		s+="<strong>FECHA NAC: </strong>"+json1.get("dob")+ "<br></br>";
		s+="<strong>LUGAR NACIMIENTO: </strong>"+json1.get("placeBirth")+ "<br></br>";
		s+="<strong>UBICACIONES: </strong>"+json1.get("locations")+ "<br></br>";
		s+="<strong>CURP: </strong>"+json1.get("curp")+ "<br></br>";;
		s+="<strong>RFC: </strong>"+json1.get("rfc")+ "<br></br>";
		s+="<strong>GENERO: </strong>"+json1.get("genero")+ "<br></br>";
		return s;
		
	}
	
	
	public String obtenerJsonMoral(String json) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject json1 = (JSONObject) parser.parse(json);
		
		String s="";
		s+="<b>NOMBRE :</b>"+json1.get("lastName")+ "<br></br>";
		s+="<b>RFC :</b>"+json1.get("rfc")+ "<br></br>";
		s+="<b>UBICACIONES :</b> "+json1.get("locations")+ "<br></br>";
		return s;
	}
}
