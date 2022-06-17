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
public class EstatusUsuario {
    Integer estatusUsuarioId;
    String descripcion;

    public EstatusUsuario() {
    }

    public EstatusUsuario(Integer estatusUsuarioId, String descripcion) {
        this.estatusUsuarioId = estatusUsuarioId;
        this.descripcion = descripcion;
    }

    public Integer getEstatusUsuarioId() {
        return estatusUsuarioId;
    }

    public void setEstatusUsuarioId(Integer estatusUsuarioId) {
        this.estatusUsuarioId = estatusUsuarioId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
    
            
}
