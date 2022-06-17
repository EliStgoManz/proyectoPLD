/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *
 * @author Aldo Ulises
 */
public class Representante {
    String cliente_id;
    int nroRepresentantes;
    String nombreCompleto;
   

    public Representante(String cliente_id, int nroRepresentantes, String nombreCompleto) {
        this.cliente_id = cliente_id;
        this.nroRepresentantes = nroRepresentantes;
        this.nombreCompleto = nombreCompleto;
        
    }

    public Representante() {
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }
    
    public int getNroRepresentantes() {
        return nroRepresentantes;
    }

    public void setNroRepresentante(int nroRepresentantes) {
        this.nroRepresentantes = nroRepresentantes;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

        
    
    
}
