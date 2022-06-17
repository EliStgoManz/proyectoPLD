/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author israel.garcia
 */
public class Aviso {
    String Cliente_Id;
    String TipoPersona;
    String TipoDomicilio;
    String denominacion_razon;
    String fecha_constitucion;
    String rfc;
    String pais;
    String giro_mercantil;
    String nombre;
    String apellido_paterno;
    String apellido_materno;
    String fecha_nacimiento; 
    String rfc_poderado;
    String curp;
    String actividad_economica;
    String colonia;
    String calle;
    String numero_exterior;
    String numero_interior;
    String codigo_postal;
    String numero_fideicomiso;
    String pais_dom;
    String estado;
    String ciudad;
    boolean hayDatos;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String cuidad) {
        this.ciudad = cuidad;
    }

    public String getPais_dom() {
        return pais_dom;
    }

    public void setPais_dom(String pais_dom) {
        this.pais_dom = pais_dom;
    }

    public String getNumero_fideicomiso() {
        return numero_fideicomiso;
    }

    public void setNumero_fideicomiso(String numero_fideicomiso) {
        this.numero_fideicomiso = numero_fideicomiso;
    }
    
    
    
    public boolean gethayDatos() {
        return hayDatos;
    }

    public void sethayDatos(boolean hayDatos) {
        this.hayDatos = hayDatos;
    }

    public String getCliente_Id() {
        return Cliente_Id;
    }

    public void setCliente_Id(String Cliente_Id) {
        this.Cliente_Id = Cliente_Id;
    }

    public String getTipoPersona() {
        return TipoPersona;
    }

    public void setTipoPersona(String TipoPersona) {
        this.TipoPersona = TipoPersona;
    }

    public String getTipoDomicilio() {
        return TipoDomicilio;
    }

    public void setTipoDomicilio(String TipoDomicilio) {
        this.TipoDomicilio = TipoDomicilio;
    }

    public String getDenominacion_razon() {
        return denominacion_razon;
    }

    public void setDenominacion_razon(String denominacion_razon) {
        this.denominacion_razon = denominacion_razon;
    }

    public String getFecha_constitucion() {
        return fecha_constitucion;
    }

    public void setFecha_constitucion(String fecha_constitucion) throws ParseException {
        DateFormat df_n = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = df_n.parse(fecha_constitucion);
        String date_fecha_cons = df.format(date);
        this.fecha_constitucion = date_fecha_cons;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        if (rfc != null && !rfc.isEmpty() && !(rfc.trim().compareTo("NULL") == 0))
            this.rfc = rfc;
        else
            rfc = "";
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGiro_mercantil() {
        return giro_mercantil;
    }

    public void setGiro_mercantil(String giro_mercantil) {
        this.giro_mercantil = giro_mercantil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) throws ParseException {
        DateFormat df_n = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = df_n.parse(fecha_nacimiento);
        String date_fecha_nac = df.format(date);
        this.fecha_nacimiento = date_fecha_nac;
    }

    public String getRfc_poderado() {
        return rfc_poderado;
    }

    public void setRfc_poderado(String rfc_poderado) {
        if (rfc_poderado != null && !rfc_poderado.isEmpty() && !(rfc_poderado.trim().compareTo("NULL") == 0))        
            this.rfc_poderado = rfc_poderado;
        else
            this.rfc_poderado = "";
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        if (curp != null && !curp.isEmpty() && !(curp.trim().compareTo("NULL") == 0))                
            this.curp = curp;
        else
            this.curp = "";
    }

    public String getActividad_economica() {
        return actividad_economica;
    }

    public void setActividad_economica(String actividad_economica) {
        this.actividad_economica = actividad_economica;
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

    public String getNumero_exterior() {
        return numero_exterior;
    }

    public void setNumero_exterior(String numero_exterior) {
        this.numero_exterior = numero_exterior;
    }

    public String getNumero_interior() {
        return numero_interior;
    }

    public void setNumero_interior(String numero_interior) {
        this.numero_interior = numero_interior;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    
    
    
    
      
  }//fin clase