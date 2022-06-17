/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Israel Osiris García
 */
public class Mensajes {
    public static String mensaje;
    public static boolean respuestaServlet;

    public static String getMensaje() {
        return mensaje;
    }

    public static void setMensaje(String mensaje) {
        Mensajes.mensaje = mensaje;
    }

    public static boolean isRespuestaServlet() {
        return respuestaServlet;
    }

    public static void setRespuestaServlet(boolean respuestaServlet) {
        Mensajes.respuestaServlet = respuestaServlet;
    }
    
    
}
