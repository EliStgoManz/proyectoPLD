package listaEntidad;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import datosRatos.Conexion;
import datosRatos.Conexion2;

public class ConsultasWs {
     
String rutaToken="";
String wslistaclienteid="";
String wslistaclientesecret="";
String rutawslistasfisica="";
String rutawslistasmoral="";
	public String obtenerRutaToken() throws SQLException{
		 Connection conex = null;
		    Conexion2 c=new Conexion2();   
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
		       String consulta = "SELECT * from varconfiguracion where configuracion_id=1;";
		       conjuntoResultados = instruccion.executeQuery(consulta);
		       
		       if(conjuntoResultados.next())
		    	   rutaToken= conjuntoResultados.getString("wslistaobtenertoken");
		       
		           conex.close();
		           return rutaToken;
		       	  
	}
	public String obtenerCliente_idService() throws SQLException{
		 Connection conex = null;
		    Conexion2 c=new Conexion2();   
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
		       String consulta = "SELECT * from varconfiguracion where configuracion_id=1;";
		       conjuntoResultados = instruccion.executeQuery(consulta);
		       
		       if(conjuntoResultados.next())
		    	   wslistaclienteid=conjuntoResultados.getString("wslistaclienteid");
		       conex.close();
		    	   return wslistaclienteid;
		       	  
	}
	
	
	public String obtenerCliente_secretService() throws SQLException{
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
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
	       String consulta = "SELECT * from varconfiguracion where configuracion_id=1;";
	       conjuntoResultados = instruccion.executeQuery(consulta);
	       
	       if(conjuntoResultados.next())
	    	   wslistaclientesecret= conjuntoResultados.getString("wslistaclientesecret");
	       conex.close();
	       	   return wslistaclientesecret;
	}
	
	public String obtenerRutaWsFisico() throws SQLException{
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
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
	       String consulta = "SELECT * from varconfiguracion where configuracion_id=1;";
	       conjuntoResultados = instruccion.executeQuery(consulta);
	      
	       if(conjuntoResultados.next())
	    	   rutawslistasfisica =  conjuntoResultados.getString("rutawslistasfisica");
	       		conex.close();
	       	   return rutawslistasfisica;
	}
	
	public String obtenerRutaWsMoral() throws SQLException{
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
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
	       String consulta = "SELECT * from varconfiguracion where configuracion_id=1;";
	       conjuntoResultados = instruccion.executeQuery(consulta);
	       
	       if(conjuntoResultados.next())
	    	   rutawslistasmoral =  conjuntoResultados.getString("rutawslistasmoral");
	           conex.close();
	       	   return rutawslistasmoral;
	}
}