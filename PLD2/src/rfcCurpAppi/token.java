package rfcCurpAppi;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import okhttp3.*;
import utilidades.Rutas;
import datosRatos.DatosConfiguracion;

public class token {
	
	public static void main (String [] args) throws ParseException, SQLException, IOException{
		token t=new token();
		String rfc="GAFA9603064C3";
		
		String respuesta = t.obtenerCoincidenciaRFC(rfc).getCuerpoRespuesta();
		System.out.println("Mensaje : "+ respuesta);
		
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(respuesta);
	     System.out.println(""+json.get("mensaje"));
	}
	
	public CuerpoRespuesta obtenerCoincidenciaRFC(String rfc) throws IOException, ParseException, SQLException{
		System.out.println("rfc: "+rfc + " ruta  "+ Rutas.wverificasionrfc);
//		System.out.println("rfc: "+rfc + " ruta  https://prod.aml-meltsan.info/api/efe-fintech/v1/aml/utils/verificaciones/rfc");
		//Service.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		JSONObject json=null;
		Response response=null;	
		
		    OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\r\n    \"rfc\": \""+rfc+"\"\r\n}");
				Request request = new Request.Builder()
//				  .url("https://prod.aml-meltsan.info/api/efe-fintech/v1/aml/utils/verificaciones/rfc")
				  .url(Rutas.wverificasionrfc)
				  .method("POST", body)
				  .addHeader("Authorization",obtenerToken())
				  .build();
				try{
				 response = client.newCall(request).execute();
				System.out.println(response.code());
				JSONParser parser = new JSONParser();
				json = (JSONObject) parser.parse(response.body().string());
				
		}catch(Exception e){
			System.out.println("Token.java ERROR AL HACER PETICION : "+ e.toString());
		}
				return new CuerpoRespuesta(json+"",response.code());	
	}
	
	
	
	
	private String obtenerToken() throws ParseException, SQLException{
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType,"");
				Request request = new Request.Builder()
				  .url(Rutas.wstokenrfccurp)
//  			  .url(consulta.obtenerRutaToken()+""+consulta.obtenerCliente_idService()+"&client_secret="+consulta.obtenerCliente_secretService())		
				  .method("POST", body)
				  .addHeader("Content-Type", "application/x-www-form-urlencoded")
				  .build();
				try {
				Response response = client.newCall(request).execute();
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(response.body().string());
					System.out.println("Toquen :"+json.toJSONString());
					return ""+json.get("access_token");
					
				} catch (IOException e) {
					System.out.println("Obtener en Token: " + e.getMessage());
					return "";
				}
	}
}
