package datosRatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConsultaWS {
	 
	public String consultarWsBene(){
		Conexion2 cnn = new Conexion2();
		Connection conex = null;
	    Statement instruccion;
	    ResultSet conjuntoResultados;   
	      
	        try{
	             conex = cnn.getConnection("dbpld");
	             // crea objeto Statement para consultar la base de datos
	             instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
	             String consulta = "select rutawsbene from varconfiguracion";
	             conjuntoResultados = instruccion.executeQuery(consulta);
	             while(conjuntoResultados.next()){ 
	                return  conjuntoResultados.getString("rutawsbene");
	             }//fin del while
	             conjuntoResultados.close();
	             instruccion.close();
	             conex.close();
	        }catch(SQLException es)	{
	        	System.out.println("ERROR consulta ruta ws SQL "+ConsultaWS.class.getName()+ "consultarWsBene() "+es);
	        }//fin del catch
	        catch (Exception e) {
	        	System.out.println("ERROR  obtener rutaws "+ConsultaWS.class.getName()+ "consultarWsBene() "+e);
			}finally {
				try{
	                if(conex != null)
	                    conex.close();
	            }catch(SQLException es){
	            	System.out.println("ERROR cerrar Conexión "+ConsultaWS.class.getName()+ "consultarWsBene() "+es);
	            }
	        }//fin del finally
	        return "no hay liga";
	}
	
	public String consultarWsRepLegal(){
		Conexion2 cnn = new Conexion2();
		Connection conex = null;
	    Statement instruccion;
	    ResultSet conjuntoResultados;   
	    
	    try{
	    	conex = cnn.getConnection("dbpld");
	        // crea objeto Statement para consultar la base de datos
	        instruccion = conex.createStatement( 
	        ResultSet.TYPE_SCROLL_INSENSITIVE,
	        ResultSet.CONCUR_READ_ONLY );

	        String consulta = "select rutawsrep from varconfiguracion";
	        conjuntoResultados = instruccion.executeQuery(consulta);
	        while(conjuntoResultados.next()){
	        	return  conjuntoResultados.getString("rutawsrep");
	        }//fin del while
	        
	        conjuntoResultados.close();
	        instruccion.close();
	        conex.close();
	        }catch(SQLException es){
	        	System.out.println("ERROR consulta rutaws SQL "+ConsultaWS.class.getName()+ "consultarWsRepLegal() "+es);
	    	}catch (Exception e) {
	    		System.out.println("ERROR consulta rutaws "+ConsultaWS.class.getName()+ "consultarWsRepLegal() "+e);
			}finally{
				try{
					if(conex != null)
	    			conex.close();
	            }catch(SQLException es){
	            	System.out.println("ERROR cerrar conexión "+ConsultaWS.class.getName()+ "consultarWsRepLegal() "+es);
	    		}
	    	}//fin del finally
	        return "no hay liga";
	}
	
	
	
}
