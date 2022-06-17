/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author israel.garcia
 */
public class Cliente implements Serializable {

    private static final long serialVersionUID = -2797991865147904427L;
    String Cliente_Id;
    String TipoPersona;
    String FechaRegistro;
    String TipoDomicilio;
    String Telefono;
    Pais pais;
    String eMail;
    String Estado;
    String RazonSocial;
    int Validado; //en funcion de un boolean
    String FechaValidado;
    int DeclaroBeneficiario; // en función de un boolean
    int DeclaroOrigen; // en función de un boolean
    String UsuarioValido; // en función de un boolean
    String FechaCorte;
    String FechaRiesgo;
    String Mensaje;
    String UsuarioAsignado;
    boolean bloqueado;
    String fechaBloqueo;
    boolean borrado;
    String rfc;
    String Notas;
    String riesgo;
    String fechaRiesgo;
    String Descripcion;
    
    
    
    /**
     * AGREGACIONES
     */
    Domicilio domicilio;
    PersonaFisica personaFisica;
    PersonaMoral personaMoral;
    Fideicomiso fideicomiso;
    Gobierno gobierno;
    
    
    SimpleDateFormat formato;
    

    public Cliente() {
    }

    public String getCliente_Id() {
        return Cliente_Id.trim();
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

    public String getFechaRegistro() {
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.FechaRegistro );
            FechaRegistro = formato.format(f);
        } catch (Exception ex) {
            FechaRegistro = "";
//            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return FechaRegistro;
        
    }

    public void setFechaRegistro(String FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public String getTipoDomicilio() {
        return TipoDomicilio;
    }

    public void setTipoDomicilio(String TipoDomicilio) {
        this.TipoDomicilio = TipoDomicilio;
    }

    public String getTelefono() {
        if ( Telefono != null){
            Telefono = Telefono.trim();
        } else {
            Telefono ="";
        }
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
    
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public int getValidado() {
        return Validado;
    }

    public void setValidado(int Validado) {
        this.Validado = Validado;
    }

    public String getFechaValidado() {
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (this.FechaValidado!=null) {
                Date f = formato.parse(this.FechaValidado );
                FechaValidado = formato.format(f);
            } else  {
                FechaValidado = "";
            }
        } catch (Exception ex) {
            FechaValidado = "";
//            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FechaValidado;
    }

    public void setFechaValidado(String FechaValidado) {
        this.FechaValidado = FechaValidado;
    }

    public int getDeclaroBeneficiario() {
        return DeclaroBeneficiario;
    }

    public void setDeclaroBeneficiario(int DeclaroBeneficiario) {
        this.DeclaroBeneficiario = DeclaroBeneficiario;
    }

    public int getDeclaroOrigen() {
        return DeclaroOrigen;
    }

    public void setDeclaroOrigen(int DeclaroOrigen) {
        this.DeclaroOrigen = DeclaroOrigen;
    }

    public String getUsuarioValido() {
        if ( UsuarioValido != null ){
            return UsuarioValido;
        } else {
            UsuarioValido ="";
        }
        return UsuarioValido;        
        
    }

    public void setUsuarioValido(String UsuarioValido) {
        this.UsuarioValido = UsuarioValido;
    }

    public String getFechaCorte() {
        
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (this.FechaCorte!=null) {
                Date f = formato.parse(this.FechaCorte );
                FechaCorte = formato.format(f);
            } else {
                FechaCorte = "";
            }
        } catch (Exception ex) {
            FechaCorte = "";
//            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return FechaCorte;
    }

    public void setFechaCorte(String FechaCorte) {
        this.FechaCorte = FechaCorte;
    }
    
    public String getFechaRiesgo() {
        
        formato = new SimpleDateFormat("yyyy-MM-dd");
      try {
          if (this.FechaRiesgo!=null) {
              Date f = formato.parse(this.FechaRiesgo );
              FechaRiesgo = formato.format(f);
          } else {
        	  FechaRiesgo = "";
          }
      } catch (Exception ex) {
    	  FechaRiesgo = "";
//          Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
      return FechaRiesgo;
  }

  public void setFechaRiesgo(String FechaRiesgo) {
      this.FechaRiesgo = FechaRiesgo;
  }

    public String getMensaje() {
        if (Mensaje != null){
            return Mensaje;
        } else {
            Mensaje = "";
        }
        
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public String getUsuarioAsignado() {
        return UsuarioAsignado;
    }

    public void setUsuarioAsignado(String UsuarioAsignado) {
        this.UsuarioAsignado = UsuarioAsignado;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getFechaBloqueo() {
          formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (this.fechaBloqueo!=null) {
                Date f = formato.parse(this.fechaBloqueo );
                fechaBloqueo = formato.format(f);
            } else {
                fechaBloqueo = "";
            }
        } catch (Exception ex) {
            fechaBloqueo = "";
//            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaBloqueo;
    }

    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    public String getNotas() {
        if (Notas != null){
            return Notas;
        } else {
            Notas = "";
        }
        
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
    }
    
    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }
    
    
    /**
     * Crea una instancia según el tipo de persona que se haya seleccionado
     * @param personaSinTipo
     * @return 
     */
    public boolean determinaTipoPersona( Object personaSinTipo ){
        boolean valido = true;
        if ( personaSinTipo != null ){
            
            if ( personaSinTipo instanceof Fideicomiso ){
                this.fideicomiso = ( Fideicomiso ) personaSinTipo;
            } if ( personaSinTipo instanceof Gobierno ){
                this.gobierno = ( Gobierno ) personaSinTipo;
            } else if ( personaSinTipo instanceof PersonaFisica ){
                this.personaFisica = ( PersonaFisica ) personaSinTipo;
            } else if ( personaSinTipo instanceof PersonaMoral ){
                this.personaMoral = ( PersonaMoral ) personaSinTipo;
            } 
        } else {
            valido = false;
        }
        
        return valido;
    }
      
  }//fin clase