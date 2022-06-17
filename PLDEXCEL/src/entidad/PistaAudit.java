/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *
 * @author gibran.matias
 */
public class PistaAudit {
    
    private String fecha;
    private String clienteId;
    private String campoTexto;
    private String valorAnterior;
    private String valorNuevo;
    private String perfil;
    private String usuarioAfectado; 

    public PistaAudit(String fecha, String clienteId, String campoTexto, String valorAnterior, String valorNuevo, String perfil, String usuarioAfectado) {
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.campoTexto = campoTexto;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.perfil = perfil;
        this.usuarioAfectado = usuarioAfectado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getCampoTexto() {
        return campoTexto;
    }

    public void setCampoTexto(String campoTexto) {
        this.campoTexto = campoTexto;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getUsuarioAfectado() {
        return usuarioAfectado;
    }

    public void setUsuarioAfectado(String usuarioAfectado) {
        this.usuarioAfectado = usuarioAfectado;
    }
    
    
    
}
