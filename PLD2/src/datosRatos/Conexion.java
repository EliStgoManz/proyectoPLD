///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package datosRatos;
//
//
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//
//
///**
// *
// * @author Salvador Valenzuela
// */
//public class Conexion {
//    private String driver;
//    private String jbdcUrl;
//    private String usuario;
//    private String password;
//    Connection conexion = null;
//    
//    public Conexion (){
//        //esta es la conexion de PLD
//    }
//    
//        public Connection getConexion(){
//        
//        try{
//            
//            /*
//             * Configuracion de variables para el driver de conexi�n 
//    
//            */    
//            PRODUCCIÓN
//            driver = "org.postgresql.Driver";            
//            jbdcUrl = "jdbc:postgresql://usatlinfo:5432/dbpld";                       
//            usuario = "postgres"; 
//            password = "ver9batim";        
//             
////            LOCAL 
//              driver = "org.postgresql.Driver";            
//              jbdcUrl = "jdbc:postgresql://localhost:5432/dbpld";                       
//              usuario = "postgres"; 
//              password = "1234";         
//              
////            
//              
////            DESARROLLO
////            driver = "org.postgresql.Driver";            
////            jbdcUrl = "jdbc:postgresql://10.250.193.56:5434/dbpld";                       
////            usuario = "postgres"; 
////            password = "ver9batim";           
//            
//            
//            //Inicia el intento de conexion
//            
//            Class.forName(driver);  
//            String cadena = jbdcUrl + "?user=" + usuario + "&password=" + password + "&charSet=UTF-8&allowEncodingChanges =false";
//            //conexion = DriverManager.getConnection(jbdcUrl, usuario, password);
//            conexion = DriverManager.getConnection(cadena);
//            System.out.println("CONEXIÓN A POSTGRES REALIZADA!");
//            boolean valid = conexion.isValid(100);
//            System.out.println(valid ? "TEST OK" : "TEST FAIL"); 
//            
//        } catch (SQLException es){ 
//            es.printStackTrace();
//            conexion = null;
//        }catch ( ClassNotFoundException es){
//            es.printStackTrace();
//            conexion = null;
//        } 
//        catch (Exception es) {
//            es.printStackTrace();
//            conexion = null;
//        } 
//        return conexion;
//        
//    } //fin delmetodo getConexion
//    
//    public void cerrarConexion(){
//        try{
//            if(this.conexion != null){
//                conexion.close();
//            }
//        } catch (SQLException es){
//            es.printStackTrace();            
//        }
//    }
//          
//    public static void main(String[]args){
//            	Conexion c= new Conexion();
//            	c.getConexion();
//            }
//  
//}
