/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Israel Osiris Garcia
 */
import java.io.IOException;
import java.util.Properties; 
import java.util.Date; 
import javax.mail.Authenticator;
import javax.mail.Session; 
import javax.mail.Message; 
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport; 
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMultipart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.InternetAddress; 
import utilidades.Rutas;

/** 
* <p>Title: MailSender</p> 
* 
* <p>Description: </p> 
* 
* <p>Copyright: Copyright (c) 2005</p> 
* 
* <p>Company: </p> 
* 
* @author Fernando Arturi 
* @version 1.0 
*/ 

public class MailSender { 

    public MailSender() { 
   } 
    
    
   public static boolean send(String toAddress, 
       String ccAddress, String bccAddress, String subject, 
       boolean isHTMLFormat, StringBuffer body, boolean debug){ 

   
       String hostSmtp = Rutas.hostSmtp;
       String senderAddress = Rutas.senderAddress;
       
        MimeMultipart multipart = new MimeMultipart(); 

        Properties properties = new Properties(); 

        //LOCAL
//        properties.put("mail.smtp.host", hostSmtp); 
//        properties.put("mail.smtp.port", Rutas.port);
//        properties.put("mail.smtp.auth", "true");//estaba en false
//        properties.put("mail.smtp.starttls.enable", "true");//estaba en false
//        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
//PRODUCCION
        properties.put("mail.smtp.host", hostSmtp); 
        properties.put("mail.smtp.port", Rutas.port);
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "false");
        
        final String usuario = senderAddress;
        final String contrasena = Rutas.password;

        // creates a new session with an authenticator
            Authenticator autenticacion = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, contrasena);
                }
            };


       Session session = Session.getInstance(properties, autenticacion); 
       session.setDebug(debug); 
       try { 
          MimeMessage msg = new MimeMessage(session); 
          msg.setFrom(new InternetAddress(senderAddress)); 
          msg.setRecipients(Message.RecipientType.TO, toAddress); 
          msg.setRecipients(Message.RecipientType.CC, ccAddress); 
          //msg.setRecipients(Message.RecipientType.BCC, bccAddress); 
          msg.setSubject(subject); 
          msg.setSentDate(new Date()); 

          // BODY 
          MimeBodyPart mbp = new MimeBodyPart(); 
          if(isHTMLFormat){ 
             mbp.setText(body.toString(), "ISO-8859-1", "html"); 
             
          } 
          else{ 
             mbp.setText(body.toString()); 
          } 

          multipart.addBodyPart(mbp); 

          msg.setContent(multipart); 
          msg.saveChanges();
          Transport.send(msg); 
       } 
       catch (Exception mex){ 
          System.out.println(">> MailSender.send() error = " + mex ); 
          return false; 
       } 
       return true; 
      } 
   
   public void infoMail(String status, String contenido, String toAddress) {

            Properties props = null;

             try {

                    props = new Properties();
                    props.put("mail.smtp.host", "mail.efectivale.com.mx"); 
                    //props.put("mail.smtp.port", Rutas.port);
                    props.put("mail.smtp.auth", "false");
                    //props.put("mail.smtp.starttls.enable", "false");

             } catch (Exception e) {

                    System.out.println("MailSender.java infoMail() "+e.toString());

             }

             Session sesion = Session.getDefaultInstance(props);

             MimeMessage mensaje = new MimeMessage(sesion);

             try {

                    mensaje.setFrom(new InternetAddress("plataformapld@efectivale.com.mx"));

                    mensaje.addRecipient(RecipientType.TO, new InternetAddress("diana.juarez@efectivale.com.mx"));

                    mensaje.setSubject("DEMONIO ENVIO DE REPORTE DE PRECIOS STATUS - " + status);

                    if (status.equalsIgnoreCase("OK")) {

                           mensaje.setText("EL DEMONIO DE ENVIO DE REPORTE DE PRECIOS SE EJECUTO CORRECTAMENTE: \n" + contenido);

                    } else if (status.equalsIgnoreCase("ERROR")) {

                           mensaje.setText("OCURRIO UN ERROR EN LA EJECUCION DEL DEMONIO DE REPORTE DE PRECIOS:\n" + contenido);

                    }

                    Transport.send(mensaje);

             } catch (AddressException e) {

            	 System.out.println("MailSender.java AddressException"+e.toString());

             } catch (MessagingException e) {

            	 System.out.println("MailSender.java MessagingException"+e.toString());

             } catch (Exception e) {

            	 System.out.println("MailSender.java Exception"+e.toString());

             }

       }
   
      
   
   

}