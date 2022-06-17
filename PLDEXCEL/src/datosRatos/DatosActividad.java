/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;


import entidad.Actividad;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author israel.garcia
 */
public class DatosActividad {
    boolean bandera;
    private Conexion2 cnn = new Conexion2();
    private ResultSet conjuntoResultados;    
    
    public ArrayList getList(String where)  {
        ArrayList listaPaises = new ArrayList();
        Connection conex = null;
        Statement instruccion;       
        
        try{
            conex = cnn.getConnection("dbpld");
            // crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            String consulta = "SELECT actividad_id, descripcion FROM avactividadeco ";
            String order = " order by descripcion";

            if ( where!= "" || where.length() > 0 ){
                consulta += "WHERE descripcion ILIKE('%" + where + "%')";
            }
            consulta += order;                
            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = true;

            while(conjuntoResultados.next()){                 
                String actividad_id = conjuntoResultados.getString( "actividad_id" );
                String descripcion = conjuntoResultados.getString( "descripcion" );
                listaPaises.add( new Actividad(actividad_id, descripcion) );
            }//fin del while

            conjuntoResultados.close();
            instruccion.close();
            conex.close();
       }catch(SQLException es){
    	  System.out.println("ERROR consulta getList "+DatosActividad.class.getName()+ ".getList() "+es);
          bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getList "+DatosActividad.class.getName()+ ".getList() "+e);
	   }finally{
           try{
               if(conex != null)
            	   conex.close();
           }catch(SQLException es){
        	   System.out.println("ERROR cerrar conexión "+DatosActividad.class.getName()+ ".getList() "+es);
           }
       }//fin del finally        
        return listaPaises;
    }
    
    
    public ArrayList getAutoCompletar(String where)  {
        ArrayList listaPaises = new ArrayList();
        Connection conex = null;
        Statement instruccion;
        
        try{
        	conex = cnn.getConnection("dbpld");
            // crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( 
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY );
            String consulta = "SELECT actividad_id, descripcion FROM avactividadeco ";
            String order = " order by descripcion";

            if ( where!= "" || where.length() > 0 ){
                consulta += "WHERE descripcion LIKE('%" + where.toUpperCase() + "%')";
            }
            consulta += order;                
            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = true;

            while(conjuntoResultados.next()){                 
                String actividad_id = conjuntoResultados.getString( "actividad_id" );
                String descripcion = conjuntoResultados.getString( "descripcion" );
                out.println( descripcion + "\n" );
            }//fin del while
            conjuntoResultados.close();
            instruccion.close();
            conex.close();
       } catch(SQLException es){
    	   System.out.println("ERROR consulta getAutoCompletar"+DatosActividad.class.getName()+".getAutoCompletar() "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getAutoCompletar"+DatosActividad.class.getName()+ ".getAutoCompletar() "+e);
	   }finally{
           try{
               if(conex != null)
                   conex.close();
           }catch(SQLException es){
        	   System.out.println("ERROR cerrar conexión "+DatosActividad.class.getName()+ ".getAutoCompletar() "+es);
           }
       }//fin del finally
        return listaPaises;
    }
}
