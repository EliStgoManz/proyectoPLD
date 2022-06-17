/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *
 * @author israel.garcia
 */
public class Perfil {
    Integer perfilId;
    String descripcion;
    String prospectos;
    String captura;
    String verificacion;
    String avisos;
    String usuarios;
    String perfiles;

    public Perfil() {
    }

    public Perfil(Integer perfilId, String descripcion) {
        this.perfilId = perfilId;
        this.descripcion = descripcion;
    }
    
    

    public Perfil(Integer perfilId, String descripcion, String prospectos, String captura, String verificacion, String avisos, String usuarios, String perfiles) {
        this.perfilId = perfilId;
        this.descripcion = descripcion;
        this.prospectos = prospectos;
        this.captura = captura;
        this.verificacion = verificacion;
        this.avisos = avisos;
        this.usuarios = usuarios;
        this.perfiles = perfiles;
    }

    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProspectos() {
        return prospectos;
    }

    public void setProspectos(String prospectos) {
        this.prospectos = prospectos;
    }

    public String getCaptura() {
        return captura;
    }

    public void setCaptura(String captura) {
        this.captura = captura;
    }

    public String getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(String verificacion) {
        this.verificacion = verificacion;
    }

    public String getAvisos() {
        return avisos;
    }

    public void setAvisos(String avisos) {
        this.avisos = avisos;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(String perfiles) {
        this.perfiles = perfiles;
    }

    
    
    
    
            
}
