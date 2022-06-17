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
public class Beneficiario {
    String cliente_id;
    int nroBeneficiario;
    String nombreCompleto;
    String tipoPersonaDesc;
    String tipoPersona;

    public Beneficiario(String cliente_id, int nroBeneficiario, String nombreCompleto, String tipoPersonaDesc, String tipoPersona) {
        this.cliente_id = cliente_id;
        this.nroBeneficiario = nroBeneficiario;
        this.nombreCompleto = nombreCompleto;
        this.tipoPersonaDesc = tipoPersonaDesc;
        this.tipoPersona = tipoPersona;
    }

    public Beneficiario() {
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }
    
    public int getNroBeneficiario() {
        return nroBeneficiario;
    }

    public void setNroBeneficiario(int nroBeneficiario) {
        this.nroBeneficiario = nroBeneficiario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoPersonaDesc() {
        return tipoPersonaDesc;
    }

    public void setTipoPersonaDesc(String tipoPersonaDesc) {
        this.tipoPersonaDesc = tipoPersonaDesc;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
    
    
    
}
