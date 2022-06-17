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

/**
 *
 * @author israel.garcia
 */
public class Gobierno {
    Cliente cliente;
    String razonsocial;
    String actividadobjetosocial;
    String rfc;
    String fechacreacion;
    Pais pais;
    Giro giro;
    
    /** COMIENZAN LOS ATRIBUTOS DEL REPRESENTANTE LEGAL**/
    String rlnombre;
    String rlapellidopaterno;
    String rlapellidomaterno;
    String rlfechanacimiento;
    String rlrfc;
    TipoIdentificacion identificacion;
    String rlautoridademiteid;
    String rlnumeroid;
    String rlcurp;
    String rlidentificaciontipo;
    String imagenrlid;
    String imagenrlfacultades;
    
    /*CONTINUAN LOS DATOS DE GOBIERNO*/
    String fecharegistro;
    String imagenacreditacion;
    String  imagencedulafiscal;
    
    SimpleDateFormat formato;
    
    
    public Gobierno(){
        
    }

    public Gobierno(Cliente cliente, String razonsocial, String actividadobjetosocial, String rfc, String fechacreacion, Pais pais, Giro giro, String rlnombre, String rlapellidopaterno, String rlapellidomaterno, String rlfechanacimiento, String rlrfc, TipoIdentificacion identificacion, String rlautoridademiteid, String rlnumeroid, String rlcurp, String rlidentificaciontipo, String imagenrlid, String imagenrlfacultades, String fecharegistro, String imagenacreditacion, String imagencedulafiscal) {
        this.cliente = cliente;
        this.razonsocial = razonsocial;
        this.actividadobjetosocial = actividadobjetosocial;
        this.rfc = rfc;
        this.fechacreacion = fechacreacion;
        this.pais = pais;
        this.giro = giro;
        this.rlnombre = rlnombre;
        this.rlapellidopaterno = rlapellidopaterno;
        this.rlapellidomaterno = rlapellidomaterno;
        this.rlfechanacimiento = rlfechanacimiento;
        this.rlrfc = rlrfc;
        this.identificacion = identificacion;
        this.rlautoridademiteid = rlautoridademiteid;
        this.rlnumeroid = rlnumeroid;
        this.rlcurp = rlcurp;
        this.rlidentificaciontipo = rlidentificaciontipo;
        this.imagenrlid = imagenrlid;
        this.imagenrlfacultades = imagenrlfacultades;
        this.fecharegistro = fecharegistro;
        this.imagenacreditacion = imagenacreditacion;
        this.imagencedulafiscal = imagencedulafiscal;
    }

    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getActividadobjetosocial() {
        return actividadobjetosocial;
    }

    public void setActividadobjetosocial(String actividadobjetosocial) {
        this.actividadobjetosocial = actividadobjetosocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFechacreacion() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fechacreacion );
            fechacreacion = formato.format(f);
        } catch (Exception ex) {
            fechacreacion = ""; 
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
    }

    public String getRlnombre() {
        return rlnombre;
    }

    public void setRlnombre(String rlnombre) {
        this.rlnombre = rlnombre;
    }

    public String getRlapellidopaterno() {
        return rlapellidopaterno;
    }

    public void setRlapellidopaterno(String rlapellidopaterno) {
        this.rlapellidopaterno = rlapellidopaterno;
    }

    public String getRlapellidomaterno() {
        return rlapellidomaterno;
    }

    public void setRlapellidomaterno(String rlapellidomaterno) {
        this.rlapellidomaterno = rlapellidomaterno;
    }

    public String getRlfechanacimiento() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.rlfechanacimiento );
            rlfechanacimiento = formato.format(f);
        } catch (Exception ex) {
            rlfechanacimiento = "";
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rlfechanacimiento;
    }

    public void setRlfechanacimiento(String rlfechanacimiento) {
        this.rlfechanacimiento = rlfechanacimiento;
    }

    public String getRlrfc() {
        if ( rlrfc == null){
            rlrfc = "";
        }
        return rlrfc.trim();
    }

    public void setRlrfc(String rlrfc) {
        this.rlrfc = rlrfc;
    }

    public TipoIdentificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(TipoIdentificacion identificacion) {
        this.identificacion = identificacion;
    }

    public String getRlautoridademiteid() {
        return rlautoridademiteid;
    }

    public void setRlautoridademiteid(String rlautoridademiteid) {
        this.rlautoridademiteid = rlautoridademiteid;
    }

    public String getRlnumeroid() {
        if (rlnumeroid == null){
            rlnumeroid = "";
        }
        return rlnumeroid.trim();
    }

    public void setRlnumeroid(String rlnumeroid) {
        this.rlnumeroid = rlnumeroid;
    }

    public String getRlcurp() {
        if ( rlcurp == null){
            rlcurp = "";
        }
        return rlcurp.trim();
    }

    public void setRlcurp(String rlcurp) {
        this.rlcurp = rlcurp;
    }

    public String getRlidentificaciontipo() {
        return rlidentificaciontipo;
    }

    public void setRlidentificaciontipo(String rlidentificaciontipo) {
        this.rlidentificaciontipo = rlidentificaciontipo;
    }

    public String getImagenrlid() {
        return imagenrlid;
    }

    public void setImagenrlid(String imagenrlid) {
        this.imagenrlid = imagenrlid;
    }

    public String getImagenrlfacultades() {
        return imagenrlfacultades;
    }

    public void setImagenrlfacultades(String imagenrlfacultades) {
        this.imagenrlfacultades = imagenrlfacultades;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getImagenacreditacion() {
        return imagenacreditacion;
    }

    public void setImagenacreditacion(String imagenacreditacion) {
        this.imagenacreditacion = imagenacreditacion;
    }

    public String getImagencedulafiscal() {
        return imagencedulafiscal;
    }

    public void setImagencedulafiscal(String imagencedulafiscal) {
        this.imagencedulafiscal = imagencedulafiscal;
    }
    
    
    
    
}
