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
public class BeneMoral {
    
    Cliente cliente;
    int nrobene;
    String razonsocial;
    String rfc;
    Pais pais; 
    String fechaconstitucion;
    Pais tepais;
    String telefono;
    String tipodomi;
    Giro giro;
    String fecharegistro;
    String imagencedulafiscal;
    
    String imagenactaconstitutiva;
    String imagencompdomicilio;
    String imagenpodernotarial;
    String imagenidrepresentantelegal;
    String email;
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
    String rlpais;
    String estado_prov;
    String ciudad;
    String colonia;
    String calle;
    String numexterior;
    String numinterior;  
    String codpostal;
    
    SimpleDateFormat formato;

    public BeneMoral() {
    }

    
    
    public BeneMoral(Cliente cliente, int nrobene, String razonsocial, String rfc, Pais pais, String fechaconstitucion, Pais tepais, String telefono, String tipodomi, Giro giro, String fecharegistro, String imagencedulafiscal, String imagenactaconstitutiva, String imagencompdomicilio, String imagenpodernotarial, String imagenidrepresentantelegal, String email, String rlnombre, String rlapellidopaterno, String rlapellidomaterno, String rlfechanacimiento, String rlrfc, String rlcurp, TipoIdentificacion identifica_id,  String rlautoridademiteid, String rlnumeroid, String rlidentificaciontipo, String  rlpais, String estado_prov, String ciudad, 
            String colonia, String calle, String numexterior, String numinterior, String codpostal) {
        this.cliente = cliente;
        this.nrobene = nrobene;
        this.razonsocial = razonsocial;
        this.rfc = rfc;
        this.pais = pais;
        this.fechaconstitucion = fechaconstitucion;
        this.tepais = tepais;
        this.telefono = telefono;
        this.tipodomi = tipodomi;
        this.giro = giro;
        this.fecharegistro = fecharegistro;
        this.imagencedulafiscal = imagencedulafiscal;
        this.imagenactaconstitutiva = imagenactaconstitutiva;
        this.imagencompdomicilio = imagencompdomicilio;
        this.imagenpodernotarial = imagenpodernotarial;
        this.imagenidrepresentantelegal = imagenidrepresentantelegal;
        this.email = email;
        this.rlnombre = rlnombre;
        this.rlapellidopaterno = rlapellidopaterno;
        this.rlapellidomaterno = rlapellidomaterno;
        this.rlrfc = rlrfc;
        this.rlcurp = rlcurp;
        this.rlfechanacimiento = rlfechanacimiento;
        this.identifica_id = identifica_id; 
        this.rlautoridademiteid = rlautoridademiteid;
        this.rlnumeroid = rlnumeroid;
        this.rlidentificaciontipo = rlidentificaciontipo;
        this.rlpais = rlpais;
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getFechaconstitucion() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fechaconstitucion );
            fechaconstitucion = formato.format(f);
        } catch (Exception ex) {
            fechaconstitucion = "";
            Logger.getLogger(BeneMoral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fechaconstitucion;
    }

    public void setFechaconstitucion(String fechaconstitucion) {
        this.fechaconstitucion = fechaconstitucion;
    }

    public Pais getTepais() {
        return tepais;
    }

    public void setTepais(Pais tepais) {
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

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
    }

   public String getFecharegistro() {
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
    
    public String getImagenactaconstitutiva() {
        return imagenactaconstitutiva;
    }

    public void setImagenactaconstitutiva(String imagenactaconstitutiva) {
        this.imagenactaconstitutiva = imagenactaconstitutiva;
    }
    public String getImagencompdomicilio() {
        return imagencompdomicilio;
    }

    public void setImagencompdomicilio(String imagencompdomicilio) {
        this.imagencompdomicilio = imagencompdomicilio;
    } 
    
    public String getImagenpodernotarial() {
        return imagenpodernotarial;
    }

    public void setImagenpodernotarial(String imagenpodernotarial) {
        this.imagenpodernotarial = imagenpodernotarial;
    }
    
    public String getImagenidrepresentantelegal() {
        return imagenidrepresentantelegal;
    }

    public void setImagenidrepresentantelegal(String imagenidrepresentantelegal) {
        this.imagenidrepresentantelegal = imagenidrepresentantelegal;
    } 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRlNombre() {
        return rlnombre;
    }

    public void setRlNombre(String rlnombre) {
        this.rlnombre = rlnombre;
    }
    
    public String getRlApellidoPaterno() {
        return rlapellidopaterno;
    }

    public void setRlApellidoPaterno(String rlapellidopaterno) {
        this.rlapellidopaterno = rlapellidopaterno;
    }
    
    public String getRlApellidoMaterno() {
        return rlapellidomaterno;
    }

    public void setRlApellidoMaterno(String rlapellidomaterno) {
        this.rlapellidomaterno = rlapellidomaterno;
    }
    
    public String getRLFechaNacimiento() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.rlfechanacimiento );
            rlfechanacimiento = formato.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rlfechanacimiento;
    }

    public void setRLFechaNacimiento(String rlfechanacimiento) {
        this.rlfechanacimiento = rlfechanacimiento;
    }

    public String getRLRFC() {
        if ( rlrfc == null){
            rlrfc = "";
        }
        return rlrfc.trim();
    }

    public void setRLRFC(String rlrfc) {
        this.rlrfc = rlrfc;
    }

    public String getRLCURP() {
        if ( this.rlcurp == null){
            this.rlcurp= "";
        }

        return this.rlcurp.trim();
    }

     public void setRLCURP(String rlcurp) {
        this.rlcurp = rlcurp;
    }


    public TipoIdentificacion getIdentifica_id() {
        return identifica_id;
    }

    public void setIdentifica_id(TipoIdentificacion identifica_id) {
        this.identifica_id = identifica_id;
    }

    public String getRLAutoridadEmiteId() {
        return rlautoridademiteid;
    }

    public void setRLAutoridadEmiteId(String rlautoridademiteid) {
        this.rlautoridademiteid = rlautoridademiteid;
    }
    public String getRLNumeroID() {
        return rlnumeroid;
    }

    public void setRLNumeroID(String rlnumeroid) {
        this.rlnumeroid = rlnumeroid;
    }
    public String getRLIdentificacionTipo() {
        return rlidentificaciontipo;
    }

    public void setRLIdentificacionTipo(String rlidentificaciontipo) {
        this.rlidentificaciontipo = rlidentificaciontipo;
    }
    
       public String getRlPais() {
        return rlpais;
    }

    public void setRlPais(String rlpais) {
        this.rlpais = rlpais;
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
