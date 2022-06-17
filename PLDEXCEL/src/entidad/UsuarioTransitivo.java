/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;

/**
 *
 * @author Israel Osiris García
 */
public class UsuarioTransitivo implements Serializable {

    static Cliente cliente;
    static String rfc;
    static String razonsocial;
    static int ejecutivo;
    static int supervisor_id;
    static String email;
    static String clave_de_acceso;
    static int intentos;
    static String ultcambiopass;
    static boolean primeraVez;
    static String siBeneficiario;
    static String siRepresentante;
    private static final long serialVersionUID = -3446575690068957560L;
    

    public UsuarioTransitivo() {
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        UsuarioTransitivo.cliente = cliente;
    }

    public static String getRfc() {
        return rfc;
    }

    public static void setRfc(String rfc) {
        UsuarioTransitivo.rfc = rfc;
    }

    public static String getRazonsocial() {
        return razonsocial;
    }

    public static void setRazonsocial(String razonsocial) {
        UsuarioTransitivo.razonsocial = razonsocial;
    }

    public static int getEjecutivo() {
        return ejecutivo;
    }

    public static void setEjecutivo(int ejecutivo) {
        UsuarioTransitivo.ejecutivo = ejecutivo;
    }

    public static int getSupervisor_id() {
        return supervisor_id;
    }

    public static void setSupervisor_id(int supervisor_id) {
        UsuarioTransitivo.supervisor_id = supervisor_id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UsuarioTransitivo.email = email;
    }

    public static String getClave_de_acceso() {
        return clave_de_acceso;
    }

    public static void setClave_de_acceso(String clave_de_acceso) {
        UsuarioTransitivo.clave_de_acceso = clave_de_acceso;
    }

    public static int getIntentos() {
        return intentos;
    }

    public static void setIntentos(int intentos) {
        UsuarioTransitivo.intentos = intentos;
    }

    public static String getUltcambiopass() {
        return ultcambiopass;
    }

    public static void setUltcambiopass(String ultcambiopass) {
        UsuarioTransitivo.ultcambiopass = ultcambiopass;
    }

    public static boolean isPrimeraVez() {
        return primeraVez;
    }

    public static void setPrimeraVez(boolean primeraVez) {
        UsuarioTransitivo.primeraVez = primeraVez;
    }

    public static String getSiBeneficiario() {
        return siBeneficiario;
    }

    public static void setSiBeneficiario(String siBeneficiario) {
        UsuarioTransitivo.siBeneficiario = siBeneficiario;
    }
    public static String getSiRepresentante() {
        return siRepresentante;
    }

    public static void setSiRepresentante(String siRepresentante) {
        UsuarioTransitivo.siRepresentante = siRepresentante;
    }
    
    
    public String tosString(){
        return cliente.getRazonSocial();
    }
   
    

            
}
