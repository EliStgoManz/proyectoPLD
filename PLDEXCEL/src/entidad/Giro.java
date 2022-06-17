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
public class Giro {
    String Giro_id;
    String Descropcion;

    public Giro() {
    }

    public Giro(String Giro_id) {
        this.Giro_id = Giro_id;
    }
    
    

    public Giro(String Giro_id, String Descropcion) {
        this.Giro_id = Giro_id;
        this.Descropcion = Descropcion;
    }

    public String getGiro_id() {
        return Giro_id;
    }

    public void setGiro_id(String Giro_id) {
        this.Giro_id = Giro_id;
    }

    public String getDescropcion() {
        return Descropcion;
    }

    public void setDescropcion(String Descropcion) {
        this.Descropcion = Descropcion;
    }
    
    
    
}
