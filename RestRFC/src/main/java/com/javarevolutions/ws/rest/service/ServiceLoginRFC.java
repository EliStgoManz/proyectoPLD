package com.javarevolutions.ws.rest.service;

import java.io.Console;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.json.Json;
import javax.json.JsonObject;
//import javax.json.Json;
//import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

//import org.codehaus.jackson.JsonParser;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ConexionBD.Conexion;
import ConexionBD.Conexion2;
import usuario.login;

@Path("/JavaRevolutions")
public class ServiceLoginRFC {
	
	private Conexion cnn = new Conexion();
	private ResultSet conjuntoResultados; 
	boolean hayDatos;
	
	
	@Path("gett")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String get(){
		System.out.println("hola desde system");
			return "Debera Funcionar";
			
	}
    
	@POST
	@Path("/validaStatus")
	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces("application/json")
	@Produces({MediaType.APPLICATION_JSON})
	public Object validaStatus(Object vo,@HeaderParam("ws_user") String user,@HeaderParam("ws_password") String pwd){
		String rfc=""+vo;
		rfc=rfc.replace("{rfc=", "");
		rfc=rfc.replace("}", "");
		
		if(ValidaUsuario(user,pwd)){ 
			
		Connection conex = null;
	      Conexion c=new Conexion();   
	      Statement instruccion;
	      ResultSet conjuntoResultados=null; 
	      
	      String resultado="";
	       
	          try{
	                conex = c.getConexion();
	                instruccion = conex.createStatement( 
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_READ_ONLY );
	                
	                 String consulta = "select c.cliente_id as idcliente,c.estado as estado,t.cliente_id as cliente_id"
	                 		+ " from avcliente c "
                    		+ "inner join varusuariotransitorio t on c.cliente_id=t.idcliente "
                    		+ "where t.rfc='"+rfc+"'";
	                 
	                
	                 
	                 conjuntoResultados = instruccion.executeQuery(consulta);
	                 
	                 if(conjuntoResultados.next()){ 
	                        resultado+="{\"id_salesforce\" :";
	                        resultado+= conjuntoResultados.getString("idcliente")+",";
	                        
	                        resultado+="\"Estatus Cliente\" :";
	                        resultado+= conjuntoResultados.getString("estado")+",";
	                        
	                        resultado+= "\"Cliente Efectinet\" :"+conjuntoResultados.getString("cliente_id");
	                        resultado+=",";
	                        
	                     }else{
	                    	 resultado += "{\"Existe Cliente rfc \" : false";
	                     }
	                 
	                 
	                 if(resultado.charAt(resultado.length()-1)==',')
	                	 resultado=resultado.substring(0, resultado.length()-1);
		                 resultado+="}";
	                      
		                  
		                 JSONObject jsonObject = new JSONObject(resultado);
		                 System.out.println("resultado : "+resultado);
		                 System.out.println("jsonObject : "+jsonObject);
		                 		                 
		                 
		                 
		                 return jsonObject;
	                 }catch(Exception e){
	    System.out.println("Error sql : "+ e.toString());
	    }
	          
	          
	    }else{
	        return "Usuario y/o Contraseña Incorrecta";
	    }
		
		return "";
		
	}
	
	
	
	@GET
	@Path("/validaUsu")
	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces("application/json")
	@Produces({MediaType.APPLICATION_JSON})
	public Object validaUsuario(@QueryParam ("rfc") String rfc,@HeaderParam("ws_user") String user,@HeaderParam("ws_password") String pwd){

//		String rfc2=rfc;
//		System.out.println("Parametro rfc : " + rfc2);
		if(ValidaUsuario(user,pwd)){ 
			
		Connection conex = null;
	      Conexion c=new Conexion();   
	      Statement instruccion;
	      ResultSet conjuntoResultados=null; 
	      
	      String resultado="";
	       
	          try{
	                conex = c.getConexion();
	                instruccion = conex.createStatement( 
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_READ_ONLY );
	                
	                 String consulta = "select c.cliente_id as id_salesforce,c.razonsocial as razonsocial,c.email as email, "
	                 		+ "v.apellido_y_nombres as ejecutivo, v.correo as ejecutivo_email, "
	                 		+ "s.nombre as supervisor, s.email as supervisor_email, "
	                 		+ "r.nombre as rep_legal, r.email as rep_legal_email "
	                 		+ "from avcliente c "
	                 		+ "inner join varusuariotransitorio t on c.cliente_id=t.idcliente "
	                 		+ "inner join varusuarios v on v.numero_interno=t.ejecutivo "
	                 		+ "left join avejecutivosupe a on a.ejecutivo=t.ejecutivo "
	                 		+ "left join avsuepervisores s on s.supervisor_id=a.supervisor_id "
	                 		+ "left join representantes_pld r on r.id_siseg=v.id_siseg "
	                 		+ "where t.rfc='"+rfc+"'";
	                 
	                
	                 conjuntoResultados = instruccion.executeQuery(consulta);
	                 if(conjuntoResultados.next()){ 
	                        resultado+="{\"id_salesforce\" : ";
	                        resultado+= conjuntoResultados.getString("id_salesforce")+",";
	                        resultado+="\"RazonSocial\" : ";
	                        resultado+= conjuntoResultados.getString("razonsocial")+",";
	                        resultado+= "\"Email\" : "+conjuntoResultados.getString("email")+",";
	                        resultado+= "\"Ejecutivo\" : "+conjuntoResultados.getString("ejecutivo")+",";
	                        resultado+= "\"Ejecutivo_Email\" : "+conjuntoResultados.getString("ejecutivo_email")+",";
	                        resultado+= "\"Supervisor\" : "+conjuntoResultados.getString("supervisor")+",";
	                        resultado+= "\"Supervisor_Email\" : "+conjuntoResultados.getString("supervisor_email")+",";
	                        resultado+= "\"Representante\" : "+conjuntoResultados.getString("rep_legal")+",";
	                        resultado+= "\"Representante_Email\" : "+conjuntoResultados.getString("rep_legal_email")+",";
	                        
	                     }else{
	                    	 resultado += " {\"isValueClient\" : false";
	                     }
	                 if(resultado.charAt(resultado.length()-1)==',')
	                	 resultado=resultado.substring(0, resultado.length()-1);
		                 resultado+="}";
		                 
		                 JSONObject jsonObject = new JSONObject(resultado);
		                 System.out.println("jsonObject : "+jsonObject.toString());
		                 return jsonObject;
		                 
//		                 GsonBuilder builder = new GsonBuilder();
//		                 builder.setPrettyPrinting();
//		                 Gson gson = builder.create();
//		                 Cliente cliente = gson.fromJson(resultado,Cliente.class);
//		                 resultado= gson.toJson(cliente);
//		                 System.out.println(""+resultado);
//		                 return resultado;
//		                 

	    }catch(Exception e){
	    System.out.println("Error sql : "+ e.toString());
	    }
	          
	          
	    }else{

	        return "Usuario y/o Contraseña Incorrecta";
	    }
		
		return "";
		
	}
//	class Cliente {
//	    public String id_salesforce;
//	    public String RazonSocial;
//	    public String Email;
//	    public String ShowAsString() {
//	         return "Cliente ["+id_salesforce+", "+ RazonSocial+ ", " +Email+ "]";
//	    }
//	}
	
	
	
	
	public boolean ValidaUsuario(String user, String pwd) {
		boolean bandera=false;
		
		Connection conex = null;
		Conexion2 c=new Conexion2();   
	      Statement instruccion;
	      ResultSet conjuntoResultados=null; 
	      
		try{
			conex = c.getConexion();
		}catch(Exception e){
			System.out.println("Conexión fallida: "+e.toString());
		}
	      
		try{
			user = user.trim();
			pwd = pwd.trim();

			
            instruccion = conex.createStatement( 
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY );
//            String consulta = "select usuario, clave_de_acceso"
//             		+ " from varusuarios "
//            		+ "where usuario='"+user+"'";
            
            String consulta = "select ws_user, ws_password"
             		+ " from ws_keys "
            		+ "where ws_user='"+user+"'";
             
            
            
             
             conjuntoResultados = instruccion.executeQuery(consulta);
             
             if(conjuntoResultados.next()){ 
//            	 login.setUser( conjuntoResultados.getString("usuario"));
//                 login.setPass( conjuntoResultados.getString("clave_de_acceso"));
                 login.setUser( conjuntoResultados.getString("ws_user"));
                 login.setPass( conjuntoResultados.getString("ws_password"));
                 
             }
             
             conjuntoResultados.close();
             instruccion.close();
             conex.close();
			
			if (user.length() <= 0 || pwd.length() <= 0) {
				bandera = false;
			}
			if(user.equals(""+login.getUser())&& pwd.equals(""+ login.getPass())){
				bandera = true;
			}
			
		}
	       catch(SQLException es)
	       {
	           es.printStackTrace();
	           bandera  = false;
	       }//fin del catch
	 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       finally
	       {
	           try{
	               if(conex != null)
	                   conex.close();
	           }
	           catch(SQLException es)
	                   {
	                       es.printStackTrace();
	                   }


	       }//fin del finally
		System.out.println("BANDERA : "+bandera);
		return bandera;
	}
	

	
	
}
