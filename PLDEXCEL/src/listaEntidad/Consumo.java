package listaEntidad;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.net.ssl.SSLContext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import listaEntidad.listaCliente;
import listaEntidad.ClienteFisico;
import listaEntidad.ClienteMoral;
import listaEntidad.CuerpoRespuesta;
import okhttp3.*;

public class Consumo {
	private static final String SSLSecurityProtocol = null;
	Validaciones v= new Validaciones();
	ConsultasWs consulta= new ConsultasWs();
	
	private String obtenerToken() throws ParseException, SQLException{
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType,"");
				Request request = new Request.Builder()
//				  .url("https://uat.aml-meltsan.info/api/efe-fintech/oauth2/token?grant_type=client_credentials&client_id=FBIpaIXpwF5d7eqIPxM3JoRUA7wsWWTQ&client_secret=B0rDl6z4b7zbAVaCm2S2NU2jySPO6Ghl")
				  .url(consulta.obtenerRutaToken()+""+consulta.obtenerCliente_idService()+"&client_secret="+consulta.obtenerCliente_secretService())		
				  .method("POST", body)
				  .addHeader("Content-Type", "application/x-www-form-urlencoded")
				  .build();
				try {
				Response response = client.newCall(request).execute();
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(response.body().string());
					return ""+json.get("access_token");
					
				} catch (IOException e) {
					System.out.println(e.getMessage());
					return "";
				}
	}
	public String obtenerUrlFisica(ClienteFisico c) throws SQLException{
		boolean band=false;
		//String url="https://uat.aml-meltsan.info/api/efe-fintech/v1/aml/utils/kyc/personas/searchs/personas/fisicas/?";
		String url=consulta.obtenerRutaWsFisico();
		String nombreComp=c.getNombre();
		String fechaNac="";
		
		if(c.getCurp()!=null&&!c.getCurp().trim().isEmpty()){
			//c.setCurp(c.getCurp().replaceAll("\"",""));
			url+="curp="+c.getCurp();
			band=true;
		}
			
		if(c.getFechaNac()!=null&&!c.getFechaNac().trim().isEmpty()){
			fechaNac=v.cambiarFormatoFecha(c.getFechaNac());
			url+=(band?"&fechaNacimiento=":"fechaNacimiento=")+fechaNac;
			band=true;
		}
		if(c.getGenero()!=null&&!c.getGenero().trim().isEmpty()){
			url+=(band?"&genero=":"genero=")+c.getGenero();
		}
		//validar que siempre entre un nombre completo
		url+=(band?"&nombreCompleto=":"nombreCompleto=")+nombreComp;
		
		if(c.getPais()!=null&&!c.getPais().trim().isEmpty()){
			url+="&paisNacimiento="+c.getPais();
		}
		if(c.getRfc()!=null&&!c.getRfc().trim().isEmpty()){
			url+="&rfc="+c.getRfc();
		}
		System.out.println(url);
	    return url;
	}
	
	public String obtenerUrlMoral(ClienteMoral c) throws SQLException{
		//String url="https://uat.aml-meltsan.info/api/efe-fintech/v1/aml/utils/kyc/personas/searchs/personas/morales?";
		String url=consulta.obtenerRutaWsMoral();
		String razonSocial= c.getNombre();
		
		url+="razonSocial="+razonSocial;
		if(c.getRfc()!= null&&!c.getRfc().trim().isEmpty())
			url+="&rfc="+c.getRfc();
		System.out.println(url);
		return url;
		
	}
	 
	public CuerpoRespuesta obtenerCoincidencia(String url) throws IOException, ParseException, SQLException{
		System.out.println("url: "+url);
		//Service.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		JSONObject json=null;
		Response response=null;	
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url(url)	
				  .method("GET", null)
				  .addHeader("Authorization", obtenerToken())
				  .build();
				try{
				 response = client.newCall(request).execute();
				System.out.println(response.code());
				JSONParser parser = new JSONParser();
				 json = (JSONObject) parser.parse(response.body().string());
		}catch(Exception e){
			System.out.println(" ERROR AL HACER PETICION : "+ e.toString());
		}
				return new CuerpoRespuesta(json+"",response.code());
	}
	
	public boolean prueba(){
		return " ".equals("  ");
	}
	
	public static void main(String[] args) throws ParseException, IOException, SQLException, KeyManagementException, NoSuchAlgorithmException{
		Consumo c= new Consumo();
//		//ActivarProtocolo ac= new ActivarProtocolo ();
		//ac.habilita();
		String url= c.obtenerUrlFisica(new ClienteFisico("id","F","Joaquin Guzman Loera","","BADD110313HCMLNS09","2002-04-12","M"));
//		String url=c.obtenerUrlMoral(new ClienteMoral("id","tipopersona","Pinturas"));
	     System.out.println(url);
		System.out.println(c.obtenerCoincidencia(url).getCuerpoRespuesta());
	
		
	//	System.out.println("".trim().isEmpty());
		
	}		
	}