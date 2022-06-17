package listaEntidad;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import datosRatos.Conexion2;


public class OperacionesCoincidencias {
	
	
	public boolean ingresarDescripcionCoincidencia(String clienteid,String descripcion,String matchid) throws SQLException {
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			String consulta = "update avcoincidencias set explicacion='"+descripcion+"'"
					+ "where match_id='"+matchid+"' and cliente_id='"+clienteid+"';";
		       conjuntoResultados = instruccion.executeQuery(consulta);
		       conjuntoResultados.close();
		       instruccion.close();
		       conex.close();
		} catch (Exception e) {
			if(e.getMessage().equals("La consulta no retornó ningún resultado.")){
				System.out.println("descripcion actualizada correctamente");
				
			}
				
				else{
					System.out.println("error al editar descripcion coincidencia");
					return false;
				}
		}finally {
			conex.close();
		}
	       
	       return true;
	}
	
    public  void editarUltimaRevision(String clienteid,String peligro) throws SQLException{
    	Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			if(peligro.equals("1")){
				String consulta = "update  avcliente set estado='V', riesgo='"+peligro+"', fechaanalisis=now() where cliente_id='"+clienteid+"';";
			       conjuntoResultados = instruccion.executeQuery(consulta);
			}
			else{
				String consulta = "update  avcliente set  riesgo='"+peligro+"', fechaanalisis=now() where cliente_id='"+clienteid+"';";
			       conjuntoResultados = instruccion.executeQuery(consulta);
			}
				conjuntoResultados.close();
				instruccion.close();
		       conex.close();
		}  catch (Exception e) {
			if(e.getMessage().equals("La consulta no retornó ningún resultado.")){
				System.out.println("última revisión actualizada correctamente");
		}
			
			else
			System.out.println("error al editar fecha en ultima revision");
		
		}finally {
			conex.close();
		}
    }
    
    
	public  void insertarAvCoincidencia(String implicado,String clienteid,String matchid,String json) throws SQLException {
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	    String consulta;
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			if(existeMatchId(matchid,clienteid)){
				 consulta = "update avcoincidencias set info='"+json+"' where match_id='"+matchid+"' and cliente_id='"+clienteid+"';";
				 System.out.println("Coincidencia ya existente!, no se vuelve a insertar");
				 
			}else
			     consulta = "insert into avcoincidencias (cliente_id,match_id,info,implicado)values('"+clienteid+"','"+matchid+"','"+json+"','"+implicado+"');";
		       conjuntoResultados = instruccion.executeQuery(consulta); 
		       instruccion.close();
		       conjuntoResultados.close();
		       conex.close();
		       } catch (Exception e) {
			if(e.getMessage().equals("La consulta no retornó ningún resultado.")){
				System.out.println("coincidencia insertada o editada correctamente");
				
			}
				
				else{
					e.printStackTrace();
					System.out.println("error al insertar coincidencia :" + e.toString());
				}
				
		}finally {
			conex.close();
		}
	     
	       
	}
	
	public  void insertarPistaAudit(String json,String clienteid){
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			  String consulta = "insert into pistaaudit (fecha,clienteid,campotexto,valor_original,perfilid,afectado)values(now(),'SISTEMA','RevisionListas','"+json+"','8','"+clienteid+"');";
		       conjuntoResultados = instruccion.executeQuery(consulta); 
		       conjuntoResultados.close();
		       instruccion.close();
//		       conex.close();
		} catch (Exception e) {
			if(e.getMessage().equals("La consulta no retornó ningún resultado.")){
				System.out.println("pistaaudit insertada correctamente");
				
			}
				
				else
				System.out.println("error al insertar en pistaaudit");
		}finally {
			try {
				conex.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	     
	}
	
	private boolean existeMatchId(String match,String clienteid) throws SQLException{
		Connection conex = null;
		Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			 String consulta = "Select*from avcoincidencias where match_id='"+match+"' "
			 		+ "and cliente_id='"+clienteid+"';";
			 conjuntoResultados = instruccion.executeQuery(consulta); 
			 
			 if(conjuntoResultados.next()){
				 conjuntoResultados.close();
				 instruccion.close();
				 conex.close();
			      return true;
			 }
			 else{
				 conjuntoResultados.close();
				 instruccion.close();
				 conex.close();
				 return false;
			}
	}catch(Exception e){
		System.out.println(e.toString());
		
		return false;
	}finally {
		conex.close();
	}
	       
	}
	
	
	public ArrayList<Coincidencia> armarCoincidenciasAmostrarEnPantalla(String clienteid,String tipoPersona) throws SQLException{
//		System.out.println("armando arreglo de cliente: "+clienteid+"***");
		ArrayList<Coincidencia>coincidencias=new ArrayList<Coincidencia>();
		Connection conex = null;
		Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	    Validaciones v= new Validaciones();
	    String cuerpo="";
	    String matchid="";
	    String explicacion="";
	    String culpable="";
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			 String consulta = "Select*from avcoincidencias where cliente_id='"+clienteid+"'";  /*and (explicacion ='' or explicacion is null)*/
			 conjuntoResultados = instruccion.executeQuery(consulta); 
			 
			 
			 while(conjuntoResultados.next()){
				
				 if(tipoPersona.equals("F")){
					 //cuerpo=v.obtenerDatosJsonFisico(conjuntoResultados.getString("info"));
					 cuerpo=conjuntoResultados.getString("info");
				 } 
				 else{
					 //cuerpo=v.obtenerJsonMoral(conjuntoResultados.getString("info"));
					 conjuntoResultados.getString("info");
				 }
				 matchid=conjuntoResultados.getString("match_id");
				 explicacion=conjuntoResultados.getString("explicacion");
				 culpable=conjuntoResultados.getString("implicado");
				 coincidencias.add(new Coincidencia(matchid,cuerpo,explicacion,culpable));
//				 
					
			 }
	}catch(Exception e){
		e.printStackTrace();
			System.out.println("error al armar las coincidencias de "+clienteid + " aqui: "+ e.toString());
			
	}finally{
		conex.close();
		return coincidencias;
	}
	       
	    	   
		
	}
	public String armarCoincidenciasAmostrarPantalla(String clienteid,String tipoPersona){
		Connection conex = null;
		Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	    Validaciones v= new Validaciones();
	    String s="";
	       try {
	    	   conex = c.getConnection("dbpld");
			instruccion = conex.createStatement( 
			   ResultSet.TYPE_SCROLL_INSENSITIVE,
			   ResultSet.CONCUR_READ_ONLY );
			 String consulta = "Select*from avcoincidencias where cliente_id='"+clienteid+"';";
			 conjuntoResultados = instruccion.executeQuery(consulta); 
			 conex.close();
			 
			 while(conjuntoResultados.next()){
				 s+="\n"+"COINCIDENCIA: ";
				 if(tipoPersona.equals("F"))
					 s+=v.obtenerDatosJsonFisico(conjuntoResultados.getString("info"));
				 else
					 s+=v.obtenerJsonMoral(conjuntoResultados.getString("info"));
			 }
	}catch(Exception e){
			System.out.println("error al armar las coincidencias de "+clienteid);
//			conex.close();
	}
	       
	    	   return s;
	       }
	//metodo nuevo
	
	public static void main(String[]args){
	
//		System.out.println("hola:");
//		//insertarAvCoincidencia("A-99999", "HNF","holi");
         OperacionesCoincidencias o = new OperacionesCoincidencias();
//		System.out.println("mira:");
          //System.out.println(o.existeMatchId("matchid"));
//         ArrayList<Coincidencia>c=  o.armarCoincidenciasAmostrarEnPantalla("A-888888", "F");
//       for(int i=0;i< c.size();i++){
//    	   System.out.println("OBJETO******************************");
//    	   System.out.print(c.get(i).getMatchid()+"*****");
//    	   System.out.println(c.get(i).getCuerpoJson()+"*****");
       // o.ingresarDescripcionCoincidencia("esta es la desc", "5ea78cf552faff000138067e");
       }
//		
   

}