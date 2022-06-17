 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Israel Osiris Garcia
 */
public class Rutas {
    
    
    public static String rutaCarga ;
    public static String wstokenrfccurp;
    public static String wsrfc;
    public static String wverificasionrfc;
    
    public static String rutaDescarga;
    public static String rutaPlantillas;
    public static String rutaTemporales;
    public static String rutaWebSistema;
    public static String separador = System.getProperty("file.separator");
    public static String dirIdentificacion = "ID";
    public static String dirDeclaratoria = "DECLARATORIA";
    public static String dirComprobanteDom = "DOMICILIO";
    public static String dirComprobanteDomBene = "DOMICILIO_BENE";
    public static String dirCedula = "CEDULA";
    public static String dirActaConstitutiva = "ACTA_CONSTITUTIVA";
    public static String dirActaConstitutivaBene = "ACTA_CONSTITUTIVA_BENE";
    public static String dirRlIdentificacion = "RL_ID";
    public static String dirRlCedulaFiscal = "RL_CEDULA";
    public static String dirRlPoderNotarial = "RL_PODER_NOTARIAL";
    public static String dirRlPoderNotarialBene = "RL_PODER_NOTARIAL_BENE";
    public static String dirRlPoderNotarialRep = "RL_PODER_NOTARIAL_REP";
    public static String dirAreditaacion = "ACREDITACION";
    public static String dirFacultades = "FACULTADES";
    public static String dirIdentificacionBene = "ID_BENE";
    public static String dirIdentificacionRep = "ID_REP";
    public static String dirCedulaBene = "CEDULA_BENE";
    public static String dirCedulaRep = "CEDULA_REP";
    public static String dirCurp = "CURP";
    public static String dirCurpBene = "CURP_BENE";
    
    public static String    hostSmtp;
    public static String senderAddress;
    public static String port;
    public static String password;

    public static void setRutaCarga(String rutaCarga) {
        Rutas.rutaCarga = rutaCarga;
    }

    public static void setRutaDescarga(String rutaDescarga) {
        Rutas.rutaDescarga = rutaDescarga;
    }
    public static void setWstokenrfccurp(String wstokenrfccurp) {
        Rutas.wstokenrfccurp = wstokenrfccurp;
    }
    public static void setWsrfc(String wsrfc) {
        Rutas.wsrfc = wsrfc;
    }
    public static void setWverificasionrfc(String wverificasionrfc) {
        Rutas.wverificasionrfc = wverificasionrfc;
    }
    
    
    
    
    public static void setRutaPlantillas(String rutaPlantillas) {
        Rutas.rutaPlantillas = rutaPlantillas;
    }

    public static void setRutaTemporales(String rutaTemporales) {
        Rutas.rutaTemporales = rutaTemporales;
    }

    public static void setRutaWebSistema(String rutaWebSistema) {
        Rutas.rutaWebSistema = rutaWebSistema;
    }
    
    
    
    
    public static void setSeparador(String separador) {
        Rutas.separador = separador;
    }

    public static void setHostSmtp(String hostSmtp) {
        Rutas.hostSmtp = hostSmtp;
    }

    public static void setSenderAddress(String senderAddress) {
        Rutas.senderAddress = senderAddress;
    }

    public static void setPort(String port) {
        Rutas.port = port;
    }

    public static void setPassword(String password) {
        Rutas.password = password;
    }    
    
    
    
    
}
