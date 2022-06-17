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
public class BeneFisica {
    String nrobene;
    String nombre;
    String apellidopaterno;
    String apellidomaterno;
    String fechanacimiento;
    String rfc;
    Pais paisnacim;
    Actividad actividad;
    TipoIdentificacion identifica_id; 
    String identificaciontipo;
    String AutoridadEmiteId;
    String numeroid;
    Pais paisnacio;
    String curp;
    Pais tepais;
    String telefono;
    String tipodomi;
    String fecharegistro;
    Domicilio domicilio;
    Cliente cliente;
    String ImagenId;
    String imagencedulafiscal;
    String imagencurp;
    String imagencompdomicilio;
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

    public BeneFisica() {
    }

    
    
    public BeneFisica(Cliente cliente, String nrobene, String nombre, String apellidopaterno, String apellidomaterno, String fechanacimiento, String rfc, Pais paisnacim, Actividad actividad, TipoIdentificacion identifica_id, String identificaciontipo, String AutoridadEmiteId, String numeroid, Pais paisnacio, String curp, Pais tepais, String telefono,  String fecharegistro, String  ImagenId, String imagencedulafiscal, String imagencurp, String imagencompdomicilio, String email, String  pais, String estado_prov, String ciudad, 
            String colonia, String calle, String numexterior, String numinterior, String codpostal) {
        this.cliente = cliente;
        this.nrobene = nrobene;
        this.nombre = nombre;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.fechanacimiento = fechanacimiento;
        this.rfc = rfc;
        this.paisnacim = paisnacim;
        this.actividad = actividad;
        this.identifica_id = identifica_id;
        this.identificaciontipo = identificaciontipo;
        this.AutoridadEmiteId = AutoridadEmiteId;
        this.numeroid = numeroid;
        this.paisnacio = paisnacio;
        this.curp = curp;
        this.tepais = tepais;
        this.telefono = telefono;
        this.fecharegistro = fecharegistro;
        this.ImagenId = ImagenId;
        this.imagencedulafiscal = imagencedulafiscal;
        this.imagencurp = imagencurp;
        this.imagencompdomicilio = imagencompdomicilio;
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

   

    
    
    
    public String getNrobene() {
        return nrobene;
    }

    public void setNrobene(String nrobene) {
        this.nrobene = nrobene;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getFechanacimiento() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fechanacimiento );
            fechanacimiento = formato.format(f);
        } catch (Exception ex) {
            fechanacimiento = "";
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Pais getPaisnacim() {
        return paisnacim;
    }

    public void setPaisnacim(Pais paisnacim) {
        this.paisnacim = paisnacim;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public TipoIdentificacion getIdentifica_id() {
        return identifica_id;
    }

    public void setIdentifica_id(TipoIdentificacion identifica_id) {
        this.identifica_id = identifica_id;
    }

    public String getIdentificaciontipo() {
        return identificaciontipo;
    }

    public void setIdentificaciontipo(String identificaciontipo) {
        this.identificaciontipo = identificaciontipo;
    }

    public String getAutoridadEmiteId() {
        return AutoridadEmiteId;
    }

    public void setAutoridadEmiteId(String AutoridadEmiteId) {
        this.AutoridadEmiteId = AutoridadEmiteId;
    }
    
    
    public String getNumeroid() {
        return numeroid;
    }

    public void setNumeroid(String numeroid) {
        this.numeroid = numeroid;
    }

    public Pais getPaisnacio() {
        return paisnacio;
    }

    public void setPaisnacio(Pais paisnacio) {
        this.paisnacio = paisnacio;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
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

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getImagenId() {
        return ImagenId;
    }

    public void setImagenId(String ImagenId) {
        this.ImagenId = ImagenId;
    }

    public String getImagencedulafiscal() {
        return imagencedulafiscal;
    }

    public void setImagencedulafiscal(String imagencedulafiscal) {
        this.imagencedulafiscal = imagencedulafiscal;
    }
    
    public String getImagenCurp() {
        return imagencurp;
    }

    public void setImagenCurp(String imagencurp) {
        this.imagencurp = imagencurp;
    }
    public String getImagenCompDomicilio() {
        return imagencompdomicilio;
    }

    public void setImagenCompDomicilio(String imagencompdomicilio) {
        this.imagencompdomicilio = imagencompdomicilio;
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
