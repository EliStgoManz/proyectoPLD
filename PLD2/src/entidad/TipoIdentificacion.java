/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *  Catálogo del tipo de indentificacion del SAT
 * @author israel.garcia
 * 
 */
public class TipoIdentificacion {
    String Identifica_id;
    String Descripcion;
    int EsOtro;

    public TipoIdentificacion() {
    }
    
    public TipoIdentificacion(String Identifica_Id){
        this.Identifica_id = Identifica_Id;
    }

    public TipoIdentificacion(String Identifica_id, String Descripcion, int EsOtro) {
        this.Identifica_id = Identifica_id;
        this.Descripcion = Descripcion;
        this.EsOtro = EsOtro;
    }

    public String getIdentifica_id() {  
        if ( Identifica_id == null){
            Identifica_id = "";
        }
        return Identifica_id;
    }

    public void setIdentifica_id(String Identifica_id) {
        this.Identifica_id = Identifica_id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getEsOtro() {
        return EsOtro;
    }

    public void setEsOtro(int EsOtro) {
        this.EsOtro = EsOtro;
    }
    
    
}
