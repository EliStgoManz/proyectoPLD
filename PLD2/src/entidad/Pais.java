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
public class Pais {
    String pais;
    String descrpcion;

    public Pais() {
        
        
    }
    
    public Pais(String pais){
        this.pais = pais;
    }

    public Pais(String pais, String descrpcion) {
        this.pais = pais;
        this.descrpcion = descrpcion;
    }

    public String getPais() {
        if ( pais == null){
            pais = "";
            
        }
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
   
    
    public String getDescrpcion() {
        return descrpcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.descrpcion = descrpcion;
    }
    
    
    
}
