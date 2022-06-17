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
public class UsuarioSistema {
    String numero_interno;
    String usuario;
    String apellido_y_nombres;
    Perfil perfil;
    EstatusUsuario estatus; 
    Supervisor supervisor;
    String correo;
    boolean primeraVez;
    String clave_de_acceso;
    int Estatus;
    RepresentantePLD id_siseg;

    public UsuarioSistema() {
    }

    public UsuarioSistema(String numero_interno, String usuario, String apellido_y_nombres, Perfil perfilid, EstatusUsuario estatus, Supervisor supervisor, String correo, RepresentantePLD id_siseg) {
        this.numero_interno = numero_interno;
        this.usuario = usuario;
        this.apellido_y_nombres = apellido_y_nombres;
        this.perfil = perfilid;
        this.estatus = estatus;
        this.supervisor = supervisor;
        this.correo = correo;
        this.id_siseg = id_siseg;
    }


	

	public RepresentantePLD getId_siseg() {
		return id_siseg;
	}

	public void setId_siseg(RepresentantePLD id_siseg) {
		this.id_siseg = id_siseg;
	}

	public String getNumero_interno() {
        return numero_interno;
    }

    public void setNumero_interno(String numero_interno) {
        this.numero_interno = numero_interno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellido_y_nombres() {
        return apellido_y_nombres;
    }

    public void setApellido_y_nombres(String apellido_y_nombres) {
        this.apellido_y_nombres = apellido_y_nombres;
    }

    public Perfil getPerfilid() {
        return perfil;
    }

    public void setPerfilid(Perfil perfilid) {
        this.perfil = perfilid;
    }
    

    public EstatusUsuario getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusUsuario estatus) {
        this.estatus = estatus;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isPrimeraVez() {
        return primeraVez;
    }

    public void setPrimeraVez(boolean primeraVez) {
        this.primeraVez = primeraVez;
    }

    public String getClave_de_acceso() {
        return clave_de_acceso;
    }

    public void setClave_de_acceso(String clave_de_acceso) {
        this.clave_de_acceso = clave_de_acceso;
    }
    
    public String toString(){
        return usuario;
    }
    
    

    

    
    
    
    
    
}
