/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israel.garcia
 */
public class DatosBloqueo {
    
    boolean bandera;
    private Conexion2 cnn = new Conexion2();
    
    
    public boolean bloqueoMasivo( String[] clientesBloquear, String fecha ){
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR Conexión bloqueoMasivo"+DatosBloqueo.class.getName()+ " "+e);
		}
        CallableStatement instruccion;
        boolean resultado = false;
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        java.sql.Date sqlFechaBloqueo = null;
        
        SimpleDateFormat formato;
        formato = new SimpleDateFormat("yyyy-MM-dd");        
        try{
            java.util.Date utilFechaBloqueo = formato.parse( fecha );
            sqlFechaBloqueo = new java.sql.Date ( utilFechaBloqueo.getTime() );
        } catch(ParseException es){
        	sqlFechaBloqueo = sqlDate;
        } 
        
        
        for ( int i = 0; i < clientesBloquear.length; i++){
            try {
                instruccion = conex.prepareCall("{call usp_change_bloqueo(?,?)}");
                instruccion.setString(1, clientesBloquear[i]);
                instruccion.setDate(2, sqlFechaBloqueo);
                instruccion.execute(); 
                //Seteamos el exito de la transaccion 
                resultado = true;
                 //Limpieza del ambiente        
                instruccion.close();
                
            } catch (SQLException exSql){
            	System.out.println("ERROR sql bloqueoMasivo"+DatosBloqueo.class.getName()+ " "+exSql);
                resultado = false;
            } catch (Exception es){
            	System.out.println("ERROR  bloqueoMasivo"+DatosBloqueo.class.getName()+ " "+es);
                resultado = false;
            } 

        } // for del listado de clientes por bloquear
        
        if ( conex!= null ){
            try { 
                conex.close();
            } catch (SQLException ex) {
            	System.out.println("ERROR cerrar conexión bloqueoMasivo"+DatosBloqueo.class.getName()+ " "+ex);
            }
        }
        
        return resultado;
    }//Fin del mÃ©todo bloqueo masivo
    
    
    
    public boolean borradoMasivo( String fecha ){
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión borradoMasivo"+DatosBloqueo.class.getName()+ " "+e);
		}
        CallableStatement instruccion;
        boolean resultado = false;
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        java.sql.Date sqlFechaBorrado = null;
        
        SimpleDateFormat formato;
        formato = new SimpleDateFormat("yyyy-MM-dd");        
        try{
            java.util.Date utilFechaBorrado = formato.parse( fecha );
            sqlFechaBorrado = new java.sql.Date ( utilFechaBorrado.getTime() );
        } catch(ParseException es){
        	sqlFechaBorrado = sqlDate;
        } catch ( Exception es ){
        	System.out.println("ERROR fecha borradoMasivo"+DatosBloqueo.class.getName()+ " "+es);               
        }
        
        
        
            try {
                instruccion = conex.prepareCall("{call usp_change_borrado(?)}");
                instruccion.setDate(1, sqlFechaBorrado);
                instruccion.execute(); 
                //Seteamos el exito de la transaccion 
                resultado = true;
                 //Limpieza del ambiente        
                instruccion.close();
                
            }
            catch (SQLException exSql){
            	System.out.println("ERROR SQL borradoMasivo"+DatosBloqueo.class.getName()+ " "+exSql);
                resultado = false;
            } catch (Exception es){
            	System.out.println("ERROR SQL borradoMasivo"+DatosBloqueo.class.getName()+ " "+es);
                //Seteamos el fracaso de la transaccion 
                resultado = false;
            } 
        
        
        if ( conex!= null ){
            try { 
                conex.close();
            } catch (SQLException ex) {
            	System.out.println("ERROR cerrar conexión borradoMasivo"+DatosBloqueo.class.getName()+ " "+ex);
            }
        }
        
        return resultado;
    }//Fin del metodo bloqueo masivo
}
