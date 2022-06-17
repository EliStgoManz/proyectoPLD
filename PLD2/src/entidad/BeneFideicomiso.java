/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Israel Osiris Garcia
 */
public class BeneFideicomiso {
    
    Cliente  cliente; 
    int  nrobene ;
    String  razonsocial;
    String  rfc ;
    String  nrofideicomiso ;
    String  tepais ;
    String  telefono; 
    String  tipodomi ;
    String  fecharegistro;
    String imagencedulafiscal;
    String institucionfiduciaria;
    String imagenactaconstitutiva;
    String imagenpodernotarial;
    String imagenidrepresentantelegal;
    String rlnombre;
    String rlapellidopaterno;
    String rlapellidomaterno;
    String rlfechanacimiento;
    String rlrfc;
    String rlcurp;
    TipoIdentificacion identifica_id;
    String rlautoridademiteid;
    String rlnumeroid;
    String rlidentificaciontipo;
    String email;
    String pais;
    String estado_prov;
    String ciudad;
    String colonia;
    String calle;
    String numexterior;
    String numinterior;  
    String codpostal;
    SimpleDateFormat formato;
    
    public BeneFideicomiso() {
    }

    public BeneFideicomiso(Cliente cliente, int nrobene, String razonsocial, String rfc, String nrofideicomiso, 
            String tepais, String telefono, String tipodomi, String fecharegistro, String imagencedulafiscal, 
            String institucionfiduciaria, String imagenactaconstitutiva, String imagenpodernotarial,
            String imagenidrepresentantelegal, String rlnombre, String rlapellidopaterno, String rlapellidomaterno,
            String rlfechanacimiento, String rlrfc, String rlcurp, TipoIdentificacion identifica_id, String rlautoridademiteid,
            String rlnumeroid, String rlidentificaciontipo, String email, String  pais, String estado_prov, String ciudad, 
            String colonia, String calle, String numexterior, String numinterior, String codpostal) {
      
        this.cliente = cliente;
        this.nrobene = nrobene;
        this.razonsocial = razonsocial;
        this.rfc = rfc;
        this.nrofideicomiso = nrofideicomiso;
        this.tepais = tepais;
        this.telefono = telefono;
        this.tipodomi = tipodomi;
        this.fecharegistro = fecharegistro;
        this.imagencedulafiscal = imagencedulafiscal; 
        this.institucionfiduciaria = institucionfiduciaria;
        this.imagenactaconstitutiva = imagenactaconstitutiva;
        this.imagenpodernotarial = imagenpodernotarial;
        this.imagenidrepresentantelegal = imagenidrepresentantelegal;
        this.rlnombre = rlnombre;
        this.rlapellidopaterno = rlapellidopaterno;
        this.rlapellidomaterno = rlapellidomaterno;
        this.rlfechanacimiento = rlfechanacimiento;
        this.rlrfc = rlrfc;
        this.rlcurp = rlcurp;
        this.identifica_id = identifica_id;
        this.rlautoridademiteid = rlautoridademiteid;
        this.rlnumeroid = rlnumeroid;
        this.rlidentificaciontipo = rlidentificaciontipo;
        this.email = email;
        this.pais = pais;
        this.estado_prov = estado_prov;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.calle = calle;
        this.numexterior = numexterior;
        this.numinterior = numinterior;
        this.codpostal = codpostal;
        
        
    }

    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getNrobene() {
        return nrobene;
    }

    public void setNrobene(int nrobene) {
        this.nrobene = nrobene;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNrofideicomiso() {
        return nrofideicomiso;
    }

    public void setNrofideicomiso(String nrofideicomiso) {
        this.nrofideicomiso = nrofideicomiso;
    }

    public String getTepais() {
        return tepais;
    }

    public void setTepais(String tepais) {
        this.tepais = tepais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipodomi() {
        return tipodomi;
    }

    public void setTipodomi(String tipodomi) {
        this.tipodomi = tipodomi;
    }

    public String getFecharegistro() {
        
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fecharegistro );
            fecharegistro = formato.format(f);
        } catch (Exception ex) {
            fecharegistro = "";
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getImagencedulafiscal() {
        return imagencedulafiscal;
    }

    public void setImagencedulafiscal(String imagencedulafiscal) {
        this.imagencedulafiscal = imagencedulafiscal;
    }
        
    public String getInstitucionFiduciaria() {
        return institucionfiduciaria;
    }

    public void setInstitucionFiduciaria(String institucionfiduciaria) {
        this.institucionfiduciaria = institucionfiduciaria;
    }
    
    public String getImagenactaconstitutiva() {
        return imagenactaconstitutiva;
    }

    public void setImagenactaconstitutiva(String imagenactaconstitutiva) {
        this.imagenactaconstitutiva = imagenactaconstitutiva;
    }
    
    public String getImagenpodernotarial() {
        return imagenpodernotarial;
    }

    public void setImagenpodernotarial(String imagenpodernotarial) {
        this.imagenpodernotarial = imagenpodernotarial;
    }
    
    public String getImagenIdRepresentante() {
        return imagenidrepresentantelegal;
    }

    public void setImagenIdRepresentante(String imagenidrepresentantelegal) {
        this.imagenidrepresentantelegal = imagenidrepresentantelegal;
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
    
    public String getFechanacimiento() {
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

    public void setFechanacimiento(String rlfechanacimiento) {
        this.rlfechanacimiento = rlfechanacimiento;
    }
    
    public String getRlRFC() {
        return rlrfc;
    }

    public void setRlRFC(String rlrfc) {
        this.rlrfc = rlrfc;
    }
    
    public String getRlCURP() {
        return rlcurp;
    }

    public void setRlCURP(String rlcurp) {
        this.rlcurp = rlcurp;
    }
    
    public TipoIdentificacion getIdentifica_id() {
        return identifica_id;
    }

    public void setIdentifica_id(TipoIdentificacion identifica_id) {
        this.identifica_id = identifica_id;
    }
    
    public String getRlAutoridadEmiteId() {
        return rlautoridademiteid;
    }

    public void setRlAutoridadEmiteId(String rlautoridademiteid) {
        this.rlautoridademiteid = rlautoridademiteid;
    }
    
    public String getRlNumeroId() {
        return rlnumeroid;
    }

    public void setRlNumeroId(String rlnumeroid) {
        this.rlnumeroid = rlnumeroid;
    }
    
    public String getRlIdentificacionTipo() {
        return rlidentificaciontipo;
    }

    public void setRlIdentificacionTipo(String rlidentificaciontipo) {
        this.rlidentificaciontipo = rlidentificaciontipo;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

       public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado_prov() {
        return estado_prov;
    }

    public void setEstado_prov(String estado_prov) {
        this.estado_prov = estado_prov;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumexterior() {
        return numexterior;
    }

    public void setNumexterior(String numexterior) {
        this.numexterior = numexterior;
    }

    public String getNuminterior() {
        return numinterior;
    }

    public void setNuminterior(String numinterior) {
        this.numinterior = numinterior;
    }

    public String getCodpostal() {
        if ( codpostal != null){
            codpostal = codpostal.trim();
        } else {
            codpostal ="";
        }
        
        return codpostal;
    }

    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

}
