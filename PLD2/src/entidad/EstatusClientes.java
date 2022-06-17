/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Israel Osiris Garcia
 */
public class EstatusClientes {
    String clienteId;
    String razonSocial;
    String rfc;
    String tipoPersona;
    String estatus;
    String usuariovalido;
    String fechaValido;
    String fechaBloqueo;
    String fechaModificacion;
    String mensaje;
    String idClienteNumerico;
    String notas;
    String ejecutivo;
    int riesgo;
    SimpleDateFormat formato;
    public EstatusClientes() {
    }

    public EstatusClientes(String clienteId, String razonSocial, String rfc, String tipoPersona, String estatus, String usuariovalido, String fechaValido, String fechaBloqueo, String fechaModificacion, String mensaje, String idClienteNumerico, String notas, String ejecutivo, int riesgo) {
        this.clienteId = clienteId;
        this.razonSocial = razonSocial;
        this.rfc = rfc;
        this.tipoPersona = tipoPersona;
        this.estatus = estatus;
        this.usuariovalido = usuariovalido;
        this.fechaValido = fechaValido;
        this.fechaBloqueo = fechaBloqueo;
        this.fechaModificacion = fechaModificacion;
        this.mensaje = mensaje;
        this.idClienteNumerico = idClienteNumerico;
        this.notas = notas;
        this.ejecutivo = ejecutivo;
        this.riesgo = riesgo;
       
    }
    
    

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getUsuariovalido() {
        return usuariovalido;
    }

    public void setUsuariovalido(String usuariovalido) {
        this.usuariovalido = usuariovalido;
    }

    public String getFechaValido() {
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (this.fechaValido!=null) {
                Date f = formato.parse(this.fechaValido );
                fechaValido = formato.format(f);
            } else {
                fechaValido = "";
            }
        } catch (Exception ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
            fechaValido = "";
        }
        
        
        return fechaValido;
    }

    public void setFechaValido(String fechaValido) {
        this.fechaValido = fechaValido;
    }

    public String getFechaBloqueo() {
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (this.fechaBloqueo!=null) {
                Date f = formato.parse(this.fechaBloqueo);
                fechaBloqueo = formato.format(f);
            } else {
                fechaBloqueo = "";
            }
        } catch (Exception ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
            fechaBloqueo = "";
        }
        return fechaBloqueo;
    }

    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public String getFechaModificacion() {
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fechaModificacion );
            fechaModificacion = formato.format(f);
        } catch (Exception ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
            fechaModificacion = "";
        }
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIdClienteNumerico() {
        return idClienteNumerico;
    }

    public void setIdClienteNumerico(String idClienteNumerico) {
        this.idClienteNumerico = idClienteNumerico;
    }
    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(String ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public int getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(int riesgo) {
        this.riesgo = riesgo;
    }
    
    
    
    
    
}
