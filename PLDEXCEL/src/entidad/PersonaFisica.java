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
public class PersonaFisica {
    
    
    Cliente cliente;
    String Nombre;
    String ApellidoPaterno;
    String ApellidoMaterno;
    String FechaNacimiento;
    String RFC;
    Pais paisnacimiento;
    Actividad actividad;
    TipoIdentificacion identificacion;
    String IdentificacionTipo;
    String NumeroId;
    String AutoridadEmiteId;
    String CURP;
    Pais paisnacionalidad;
    String FechaRegistro;
    String ImagenId;
    String ImagenCedulaFiscal;
    String ImagenCurp;
    String ImagenDeclaratoria;
    
    /** agregaciones**/
    BeneFisica beneficiario;
    SimpleDateFormat formato;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    public PersonaFisica() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getFechaNacimiento() {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.FechaNacimiento );
            FechaNacimiento = formato.format(f);
        } catch (Exception ex) {
            FechaNacimiento = "";
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public Pais getPaisnacimiento() {
        return paisnacimiento;
    }

    public void setPaisnacimiento(Pais paisnacimiento) {
        this.paisnacimiento = paisnacimiento;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public TipoIdentificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(TipoIdentificacion identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacionTipo() {
        return IdentificacionTipo;
    }

    public void setIdentificacionTipo(String IdentificacionTipo) {
        this.IdentificacionTipo = IdentificacionTipo;
    }

    public String getNumeroId() {
        return NumeroId;
    }

    public void setNumeroId(String NumeroId) {
        this.NumeroId = NumeroId;
    }

    public String getAutoridadEmiteId() {
        return AutoridadEmiteId;
    }

    public void setAutoridadEmiteId(String AutoridadEmiteId) {
        this.AutoridadEmiteId = AutoridadEmiteId;
    }

    public String getCURP() {
        if (this.CURP == null){
            this.CURP ="";
        } else {
            this.CURP = this.CURP.trim();
            
        }
        return this.CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public Pais getPaisnacionalidad() {
        return paisnacionalidad;
    }

    public void setPaisnacionalidad(Pais paisnacionalidad) {
        this.paisnacionalidad = paisnacionalidad;
    }

    public String getFechaRegistro() {
        
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.FechaRegistro );
            FechaRegistro = formato.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return FechaRegistro;
    }

    public void setFechaRegistro(String FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public String getImagenId() {
        return ImagenId;
    }

    public void setImagenId(String ImagenId) {
        this.ImagenId = ImagenId;
    }

    public String getImagenCedulaFiscal() {
        return ImagenCedulaFiscal;
    }

    public void setImagenCedulaFiscal(String ImagenCedulaFiscal) {
        this.ImagenCedulaFiscal = ImagenCedulaFiscal;
    }

    public String getImagenCurp() {
        return ImagenCurp;
    }

    public void setImagenCurp(String ImagenCurp) {
        this.ImagenCurp = ImagenCurp;
    }
   
    public String getImagenDeclaratoria() {
        return ImagenDeclaratoria;
    }

    public void setImagenDeclaratoria(String ImagenDeclaratoria) {
        this.ImagenDeclaratoria = ImagenDeclaratoria;
    }
    
    
}
