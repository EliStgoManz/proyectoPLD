/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *Aqui se almacena un log con los cambios que hace el usaurio en todas las tablas de AV
 * @author israel.garcia
 */
public class Cambio {
    int idCambio;
    Cliente cliente;
    String textoCambio;

    public int getIdCambio() {
        return idCambio;
    }

    public void setIdCambio(int idCambio) {
        this.idCambio = idCambio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTextoCambio() {
        return textoCambio;
    }

    public void setTextoCambio(String textoCambio) {
        this.textoCambio = textoCambio;
    }
    
    
    
}
