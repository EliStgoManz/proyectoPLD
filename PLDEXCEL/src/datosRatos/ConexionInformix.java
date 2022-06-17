/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;


import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;



/**
 *
 * @author Israel Osiris García
 */
public class ConexionInformix {
    private String driver;
    private String jbdcUrl;
    private String usuario;
    private String password;
    Connection conexion = null;
    
    public ConexionInformix (){
        //esta es la conexion de PLD
    }
    
    public Connection getConexion(){        
        try{          
            // DESARROLLO
            driver = "com.informix.jdbc.IfxDriver";     
            jbdcUrl = "jdbc:informix-sqli://10.250.193.56:1543/dbemis:INFORMIXSERVER=emisnet;IFX_LOCK_MODE_WAIT=5";
            usuario = "informix"; 
            password = "ifxfleetcor";    
            
            // PRODUCCION
//            driver = "com.informix.jdbc.IfxDriver";     
//            jbdcUrl = "jdbc:informix-sqli://usatlinfo.fleetcor.com:1543/dbemis:INFORMIXSERVER=emisnet;IFX_LOCK_MODE_WAIT=5";
//            usuario = "informix"; 
//            password = "in4mix";    
            
            //Inicia el intento de conexion            
            Class.forName(driver);           
            conexion = DriverManager.getConnection(jbdcUrl, usuario, password);
            System.out.println("CONEXIÓN A INFORMIX REALIZADA!");
            //boolean valid = conexion.isValid(100);
            //System.out.println(valid ? "TEST OK" : "TEST FAIL");             
        } catch (SQLException es){ 
            System.out.println(ConexionInformix.class.getName()+ ".getConexion()  ERROR CONEXIÓN A INFORMIX!");
            conexion = null;
        }catch ( ClassNotFoundException es){
        	System.out.println(ConexionInformix.class.getName()+ ".getConexion()  ERROR CONEXIÓN A INFORMIX!");
            conexion = null;
        }                 
        return conexion;        
    } //fin delmetodo getConexion
    
    
    
    
    public void cerrarConexion(){
        try{
            if(this.conexion != null){
                conexion.close();
            }
        } catch (SQLException es){
        	System.out.println(ConexionInformix.class.getName()+ ".cerrarConexion() ERROR al cerrar la conexión!");            
        }
    }
            
  
    public static void main(String args[]){
        //ConexionInformix c = new ConexionInformix();
        //Connection conex = c.getConexion();        
        File directorio  = new File ("C:\\Users\\israel.garcia\\Desktop\\plantillas\\45d45.html");
        String[] archivos = directorio.list();
        
        if (archivos == null)
            System.out.println("No hay ficheros en el directorio especificado");
         else { 
            Arrays.sort( archivos );
            System.out.println(Arrays.deepToString(archivos));
         }
    }
}


