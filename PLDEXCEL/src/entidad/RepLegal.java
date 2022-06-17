/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;




public class RepLegal {
    Cliente cliente;
    int Nrorep;
    String RLNombre;
    String RLApellidoPaterno;
    String RLApellidoMaterno;
    String RLFechaNacimiento;
    String RLRFC;
    String RLCURP;
    TipoIdentificacion identifica_id;
    String RLAutoridadEmiteId;
    String RLNumeroID;
    String RLIdentificacionTipo;
   String ImagenRLID;
   String ImagenRLPoderNotarial;
    
    
   SimpleDateFormat formato = null;
   public RepLegal() {
       
    }

    public RepLegal(Cliente cliente, int Nrorep, String RLNombre, String RLApellidoPaterno, String RLApellidoMaterno, String RLFechaNacimiento, String RLRFC, String RLCURP, TipoIdentificacion identifica_id, String RLAutoridadEmiteId, String RLNumeroID, String RLIdentificacionTipo, String ImagenRLID, String ImagenRLPoderNotarial) {
            this.cliente = cliente;
            this.Nrorep = Nrorep;
            this.RLNombre = RLNombre;
            this.RLApellidoPaterno = RLApellidoPaterno;
            this.RLApellidoMaterno = RLApellidoMaterno;
            this.RLFechaNacimiento = RLFechaNacimiento;
            this.RLRFC = RLRFC;
            this.RLCURP = RLCURP;
            this.RLFechaNacimiento = RLFechaNacimiento;
            this.identifica_id = identifica_id;
            this.RLAutoridadEmiteId = RLAutoridadEmiteId;
            this.RLNumeroID = RLNumeroID;
            this.RLIdentificacionTipo = RLIdentificacionTipo;
            this.ImagenRLID = ImagenRLID;
            this.ImagenRLPoderNotarial = ImagenRLPoderNotarial;

    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getNrorep() {
        return Nrorep;
    }

    public void setNrorep(int Nrorep) {
        this.Nrorep = Nrorep;
    }

    public String getRLNombre() {
        return RLNombre;
    }

    public void setRLNombre(String RLNombre) {
        this.RLNombre = RLNombre;
    }

    public String getRLApellidoPaterno() {
        return RLApellidoPaterno;
    }

    public void setRLApellidoPaterno(String RLApellidoPaterno) {
        this.RLApellidoPaterno = RLApellidoPaterno;
    }

    public String getRLApellidoMaterno() {
        return RLApellidoMaterno;
    }

    public void setRLApellidoMaterno(String RLApellidoMaterno) {
        this.RLApellidoMaterno = RLApellidoMaterno;
    }

    public String getRLFechaNacimiento() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.RLFechaNacimiento );
            RLFechaNacimiento = formato.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }

        return RLFechaNacimiento;
    }

    public void setRLFechaNacimiento(String RLFechaNacimiento) {
        this.RLFechaNacimiento = RLFechaNacimiento;
    }

    public String getRLRFC() {
        if ( RLRFC == null){
            RLRFC = "";
        }
        return RLRFC.trim();
    }

    public void setRLRFC(String RLRFC) {
        this.RLRFC = RLRFC;
    }

    public String getRLCURP() {
        if ( this.RLCURP == null){
            this.RLCURP = "";
        }

        return this.RLCURP.trim();
    }

     public void setRLCURP(String RLCURP) {
        this.RLCURP = RLCURP;
    }


    public TipoIdentificacion getIdentifica_id() {
        return identifica_id;
    }

    public void setIdentifica_id(TipoIdentificacion identifica_id) {
        this.identifica_id = identifica_id;
    }
    public String getRLAutoridadEmiteId() {
        return RLAutoridadEmiteId;
    }

    public void setRLAutoridadEmiteId(String RLAutoridadEmiteId) {
        this.RLAutoridadEmiteId = RLAutoridadEmiteId;
    }
    public String getRLNumeroID() {
        return RLNumeroID;
    }

    public void setRLNumeroID(String RLNumeroID) {
        this.RLNumeroID = RLNumeroID;
    }
    public String getRLIdentificacionTipo() {
        return RLIdentificacionTipo;
    }

    public void setRLIdentificacionTipo(String RLIdentificacionTipo) {
        this.RLIdentificacionTipo = RLIdentificacionTipo;
    }
 
    public String getImagenRLID() {
        return ImagenRLID;
    }

    public void setImagenRLID(String ImagenRLID) {
        this.ImagenRLID = ImagenRLID;
    }
  
    public String getImagenRLPoderNotarial() {
        return ImagenRLPoderNotarial;
    }

    public void setImagenRLPoderNotarial(String ImagenRLPoderNotarial) {
        this.ImagenRLPoderNotarial = ImagenRLPoderNotarial;
    }
}
