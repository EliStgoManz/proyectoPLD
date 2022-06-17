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
public class Actividad {
    String Actividad_Id;
    String Descripcion;

    public Actividad() {
    }

    public Actividad(String Actividad_Id) {
        this.Actividad_Id = Actividad_Id;
    }
    
    

    public Actividad(String Actividad_Id, String Descripcion) {
        this.Actividad_Id = Actividad_Id;
        this.Descripcion = Descripcion;
    }

    public String getActividad_Id() {
        return Actividad_Id;
    }

    public void setActividad_Id(String Actividad_Id) {
        this.Actividad_Id = Actividad_Id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
}
