/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *
 * @author Israel Osiris Garcia
 */
public class Supervisor  {
    String usuario;
    String nombre; 

    public Supervisor() {
    }

    public Supervisor(String usuario) {
        this.usuario = usuario;
    }

    
    
    public Supervisor(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
