/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correo;

import control.ControlCorreo;
import control.ControlProspectos;
import control.DigitoAlfanumerico;
import control.MailSender;
import datosRatos.DatosCrearLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import utilidades.Rutas;

/**
 *
 * @author israel.garcia
 */
public class Correos {
    
    private final String NOMBRE_USAUARIO ="##NOMBREUSUARIO" ;
    
    public boolean envioCorreoClienteInactivo(String correoDestino,String noCliente,String razonSocial){
  	  //invocando a las funciones que envian el correo/
  //    final String NOMBRE_USUARIO = "##NOMBREUSUARIO";
     
      Calendar cal= Calendar.getInstance();
      int year= cal.get(Calendar.YEAR);
     
      MailSender ms = new MailSender();
      String plantillaHTML = "";
      
      try {
          plantillaHTML = getPlantilla("cliente_inactivo.html"); //LA PLANTILLA PARA NUEVO USUARIO
          
      } catch (Exception ex) {
          Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("error al encotnrar plantilla"+ex.toString());
      }
      String mensaje="";
      boolean result= false;
      //Reemplazando las variables de la plantilla
//      mensaje = plantillaHTML.replace(NOMBRE_USUARIO, nombre);
//      plantillaHTML = mensaje;
//      mensaje = plantillaHTML.replace(CODIGO_ALFANUMERICO,"");
//      plantillaHTML = mensaje;
      mensaje=plantillaHTML.replace("##NOCLIENTE", noCliente);
      mensaje=mensaje.replace("##RAZON SOCIAL",razonSocial);
      
             
      String asunto = "Cliente inactivo";
      
      if ( !mensaje.isEmpty()){
      		out.print("enviando correo electr髇ico de inactivo  a cliente");
              result = ms.send(correoDestino,"", "" ,
              asunto, true, new StringBuffer(mensaje),true); 
              out.print("Resultado del env韔 del mensaje : " + result); 
      }
      return result;
  }
    
    public boolean envioCorreoClienteInvalido(String nombre,  String comentario, String correoDestino, String correoPLD ){
        /*invocando a las funciones que envian el correo*/
        final String NOMBRE_USUARIO = "##NOMBREUSUARIO";
        final String MENSAJE = "##MENSAJE";
        final String CORREO_PLD = "##CORREOPLD";
        
        MailSender ms = new MailSender();
        String plantillaHTML = "";
        
        try {
            plantillaHTML = getPlantilla("cliente_invalido.html"); //LA PLANTILLA PARA NUEVO USUARIO
            
        } catch (IOException ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
        mensaje = plantillaHTML.replace(NOMBRE_USUARIO, nombre);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(MENSAJE, comentario);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(CORREO_PLD, correoPLD);
        plantillaHTML = mensaje;
        
               
        String asunto = "Informaci贸n importante";
        
        if ( !mensaje.isEmpty()){
                result = ms.send(correoDestino,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del env铆o del mensaje : " + result); 
        }
        return result;
                
    }// enviar correo
        
    public boolean envioCorreoClienteValidoPrincipal(String nombre, String rfc, String correoDestino){
        /*invocando a las funciones que envian el correo*/
        final String NOMBRE_USUARIO = "##NOMBREUSUARIO";
        final String CODIGO_ALFANUMERICO="##El indicador es: CODIGOALFANUMERICO";
        Calendar cal= Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        String codigo= new DigitoAlfanumerico().calcularCodigoAlfaNumerico(rfc, year);
        MailSender ms = new MailSender();
        String plantillaHTML = "";
        
        try {
            plantillaHTML = getPlantilla("cliente_validado.html"); //LA PLANTILLA PARA NUEVO USUARIO
            
        } catch (IOException ex) {
        	DatosCrearLog L = new DatosCrearLog();
       		L.Log(Rutas.rutaCarga, rfc, "Correos line 132 : ", ex.toString());
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
        mensaje = plantillaHTML.replace(NOMBRE_USUARIO, nombre);
        plantillaHTML = mensaje;
        mensaje = plantillaHTML.replace(CODIGO_ALFANUMERICO,"");
        plantillaHTML = mensaje;
        
        
        
               
        String asunto = "Informaci贸n validada";
        
        if ( !mensaje.isEmpty()){
        		out.print("enviando correo electr髇ico a cliente");
                result = ms.send(correoDestino,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del env韔 del mensaje : " + result); 
        }
        return result;
                
    }// enviar correo
    public boolean envioCorreoClienteValidoEjecutivoSupervisor(String nombre, String rfc,  String correoCopia ){
        /*invocando a las funciones que envian el correo*/
        final String NOMBRE_USUARIO = "##NOMBREUSUARIO";
        final String CODIGO_ALFANUMERICO=" ##El indicador es: CODIGOALFANUMERICO";
        
        Calendar cal= Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        String codigo= new DigitoAlfanumerico().calcularCodigoAlfaNumerico(rfc, year);
        MailSender ms = new MailSender();
        String plantillaHTML = "";
        
        try {
            plantillaHTML = getPlantilla("cliente_validado.html"); //LA PLANTILLA PARA NUEVO USUARIO
            
        } catch (IOException ex) {
        	DatosCrearLog L = new DatosCrearLog();
       		L.Log(Rutas.rutaCarga, rfc, "Correos line 171 : ", ex.toString());
            
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
        mensaje = plantillaHTML.replace(NOMBRE_USUARIO, nombre);
        plantillaHTML = mensaje;
        mensaje = plantillaHTML.replace(CODIGO_ALFANUMERICO, "El indicador  es: "+codigo);
        plantillaHTML = mensaje;
        
        
        
               
        String asunto = "Informaci贸n validada";
        
        if ( !mensaje.isEmpty()){
        	
                result = ms.send(correoCopia,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del env铆o del mensaje : " + result); 
        }
        return result;
                
    }// enviar correo
    
    public boolean envioCorreoClientePorValidar( String cliente, String correoDestino ){
        /*invocando a las funciones que envian el correo*/
        final String CLIENTE = "##CLIENTE";
        
        
        MailSender ms = new MailSender();
        String plantillaHTML = "";
        
        try {
            plantillaHTML = getPlantilla("cliente_por_validar_interno.html"); //LA PLANTILLA PARA NUEVO USUARIO
            
        } catch (IOException ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
                
        mensaje = plantillaHTML.replace(CLIENTE, cliente);
        plantillaHTML = mensaje;
        
               
        String asunto = "Informaci贸n por validar";
        
        if ( !mensaje.isEmpty()){
                result = ms.send(correoDestino,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del env铆o del mensaje : " + result); 
        }
        return result;
                
    }// enviar correo
    
    public boolean envioCorreoStatusRaV( String cliente, String correoDestino ){
        /*invocando a las funciones que envian el correo*/
        final String CLIENTE = "##CLIENTE";
        
        
        MailSender ms = new MailSender();
        String plantillaHTML = "";
        
        try {
            plantillaHTML = getPlantilla("clienteRaV.html"); //LA PLANTILLA PARA NUEVO USUARIO
            
        } catch (IOException ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
                
        mensaje = plantillaHTML.replace(CLIENTE, cliente);
        plantillaHTML = mensaje;
        
               
        String asunto = "Muchas Gracias por Actualizar Datos!";
        
        if ( !mensaje.isEmpty()){
                result = ms.send(correoDestino,"", "" ,asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del env铆o del mensaje : " + result); 
        }
        return result;
                
    }// enviar correo
    
    public boolean envioCorreoClienteValido(String nombre, String correo ){
        /*invocando a las funciones que envian el correo*/
        MailSender ms = new MailSender();
        String ruta = Rutas.rutaPlantillas;
        String plantillaHTML = "";
        try {
            plantillaHTML = new Correos().getPlantilla("cliente_por_validar.html"); //LA PLANTILLA PARA NUEVO USUARIO
        } catch (Exception ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
        mensaje = plantillaHTML.replace(NOMBRE_USAUARIO, nombre);
        plantillaHTML = mensaje;
                       
        String asunto = "Validando informaci贸n";
        
        if ( !mensaje.isEmpty()){
                result = ms.send(correo,"", "" ,
                asunto, true, new StringBuffer(mensaje),true);
                out.print("Resultado del env铆o del mensaje : " + result); 
        }
        return result;
                
    }
  
    public String getPlantilla(String nombrePlantilla) throws IOException{
        boolean hayURL = false;
        
        String plantilla ="";
        URL archivoRemoto = null;
        
        System.getProperty("catalina.base");
        Properties properties = System.getProperties();
        
        try {
             archivoRemoto = new URL (Rutas.rutaPlantillas + "/" +  nombrePlantilla);
             hayURL = true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String ruta = Rutas.rutaPlantillas + "/" +  nombrePlantilla;
        
        
        
        if ( !hayURL ){
            try {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(new File ( ruta ).toURL().openStream(),
                            "UTF8"));
                    String sCadena = "";
                    try {
                        while ((sCadena = in.readLine())!=null) {
                                plantilla += sCadena;

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
            }   
        } else {
            try {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(archivoRemoto.openStream(),
                            "UTF8"));
                    String sCadena = "";
                    try {
                        while ((sCadena = in.readLine())!=null) {
                                plantilla += sCadena;

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    
        return plantilla;
    }//fin getPlantilla
    
    
}
