package consumoWS;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import okhttp3.MediaType;
//import listaEntidad.CuerpoRespuesta;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.misc.BASE64Encoder;

public class consumo {

	public CuerpoRespuesta obtenerCoincidencia(String url) throws IOException, ParseException, SQLException {
//		System.out.println("url: " + url);
		// Service.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		JSONObject json = null;
		Response response = null;

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n\r\n    \"IDSolicitud\" : \"2000\",\r\n\r\n    \"Cliente\" : \"770001\",\r\n\r\n    \"Consignatario\" : \"00067\"\r\n\r\n}");
		Request request = new Request.Builder()
				.url(""+url).method("POST", body)
				.addHeader("ws_user", "9FEC2939A15EB634DDA1B4018ED75E45")
				.addHeader("ws_password", "9E393CB78B8ED9645C6939E35AF9340D")
				.addHeader("Content-Type", "application/json").build();
			response = client.newCall(request).execute();
			
			JSONParser parser = new JSONParser();
			 json = (JSONObject) parser.parse(response.body().string());
			
			System.out.println("Tarjeta: "+ json.get("Tarjeta")+ "Cuenta: "+ json.get("Cuenta"));
			
			
			
		return new CuerpoRespuesta(json + "", response.code());
		
		
	}

	
	
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		consumo s = new consumo();
		System.out.println("respuesta : " + s.obtenerCoincidencia("http://10.250.193.60/wsParabilia/api/ConsultarSaldosClienteConsignatario").toString());
//		System.out.println(""+ json.get("Tarjeta"));
		
		
	}

	
	
	public class CuerpoRespuesta {

		private String cuerpoRespuesta;
		private int codigoRespuesta;

		public CuerpoRespuesta(String cuerpoRespuesta, int codigoRespuesta) {
			super();
			this.cuerpoRespuesta = cuerpoRespuesta;
			this.codigoRespuesta = codigoRespuesta;
		}

		public String getCuerpoRespuesta() {
			return cuerpoRespuesta;
		}

		public void setCuerpoRespuesta(String cuerpoRespuesta) {
			this.cuerpoRespuesta = cuerpoRespuesta;
		}

		public int getCodigoRespuesta() {
			return codigoRespuesta;
		}

		public void setCodigoRespuesta(int codigoRespuesta) {
			this.codigoRespuesta = codigoRespuesta;
		}

	}

}
