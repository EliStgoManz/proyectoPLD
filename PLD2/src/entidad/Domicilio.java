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
public class Domicilio {
    
    Cliente cliente;
    Pais pais;
    String estado_prov;
    String ciudad;
    String colonia;
    String calle;
    String numexterior;
    String numinterior;  
    String codpostal;
    String fecharegistro;
    String imagencomprobantedom;
    String nrobene;
    String delegacionMunicipio;
    
    SimpleDateFormat formato;
    
    
    public Domicilio() {
    }

    public Domicilio(Cliente cliente, Pais pais, String estado_prov, String ciudad, String colonia, String calle, String numexterior, String numinterior, String codpostal, String fecharegistro, String nrobene,String delegacionMunicipio) {
        this.cliente = cliente;
        this.pais = pais;
        this.estado_prov = estado_prov;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.calle = calle;
        this.numexterior = numexterior;
        this.numinterior = numinterior;
        this.codpostal = codpostal;
        this.fecharegistro = fecharegistro;
        this.nrobene = nrobene;
        this.delegacionMunicipio=delegacionMunicipio;
    }

    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    
    public String getDelegacionMunicipio() {
		return delegacionMunicipio;
	}

	public void setDelegacionMunicipio(String delegacionMunicipio) {
		this.delegacionMunicipio = delegacionMunicipio;
	}

	public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
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

    public String getFecharegistro() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fecharegistro );
            fecharegistro = formato.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getImagencomprobantedom() {
        return imagencomprobantedom;
    }

    public void setImagencomprobantedom(String imagencomprobantedom) {
        this.imagencomprobantedom = imagencomprobantedom;
    }

    public String getNrobene() {
        return nrobene;
    }

    public void setNrobene(String nrobene) {
        this.nrobene = nrobene;
    }
    
    
    
}
