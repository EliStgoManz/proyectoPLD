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
public class PersonaMoral {
    Cliente cliente;
    String RazonSocial;
    String RFC;
    String FechaConstitucion;
    Pais pais;
    String RLNombre;
    String RLApellidoPaterno;
    String RLApellidoMaterno;
    String RLFechaNacimiento;
    String RLRFC;
    TipoIdentificacion identificacion;
    String RLAutoridadEmiteId;
    String RLNumeroId;
    String RLCURP;
    String RLIdentificacionTipo;
    Giro giro;
    String FechaRegistro;
    String ImagenActaConstitutiva;
    String ImagenCedulaFiscal;
    String ImagenRLId;
    String ImagenRLPoderNotarial;
    String ImagenDeclaratoria;
    
    
    
    String rlNoPoder;
    String rlNotaria;
    String rlFechaNotarial;
    String noEscritura;
    String fechaNotarial;
    String notaria;
    
    
    /**agregaciones**/
    BeneMoral beneficiario;
    
    SimpleDateFormat formato = null;

    public PersonaMoral(Cliente cliente, String RazonSocial, String RFC, String FechaConstitucion,
    		Pais pais, String RLNombre, String RLApellidoPaterno, String RLApellidoMaterno,
    		String RLFechaNacimiento, String RLRFC, TipoIdentificacion identificacion,
    		String RLAutoridadEmiteId, String RLNumeroId, String RLCURP, String RLIdentificacionTipo, 
    		Giro giro, String FechaRegistro, String ImagenActaConstitutiva, String ImagenCedulaFiscal,
    		String ImagenRLId, String ImagenRLPoderNotarial, String ImagenDeclaratoria, String rlNoPoder,
    		String rlNotaria,String rlFechaNotarial,String noEscritura, String fechaNotarial,String notaria) {
        this.cliente = cliente;
        this.RazonSocial = RazonSocial;
        this.RFC = RFC;
        this.FechaConstitucion = FechaConstitucion;
        this.pais = pais;
        this.RLNombre = RLNombre;
        this.RLApellidoPaterno = RLApellidoPaterno;
        this.RLApellidoMaterno = RLApellidoMaterno;
        this.RLFechaNacimiento = RLFechaNacimiento;
        this.RLRFC = RLRFC;
        this.identificacion = identificacion;
        this.RLAutoridadEmiteId = RLAutoridadEmiteId;
        this.RLNumeroId = RLNumeroId;
        this.RLCURP = RLCURP;
        this.RLIdentificacionTipo = RLIdentificacionTipo;
        this.giro = giro;
        this.FechaRegistro = FechaRegistro;
        this.ImagenActaConstitutiva = ImagenActaConstitutiva;
        this.ImagenCedulaFiscal = ImagenCedulaFiscal;
        this.ImagenRLId = ImagenRLId;
        this.ImagenRLPoderNotarial = ImagenRLPoderNotarial;
        this.ImagenDeclaratoria = ImagenDeclaratoria;
        this.rlNoPoder=rlNoPoder;
        this.rlNotaria=rlNotaria;
        this.rlFechaNotarial=rlFechaNotarial;
        this.noEscritura=noEscritura;
        this.fechaNotarial=fechaNotarial;
        this.notaria=notaria;
    }

    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getFechaConstitucion() {
        
        formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.FechaConstitucion );
            FechaConstitucion = formato.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return FechaConstitucion;
    }

    public void setFechaConstitucion(String FechaConstitucion) {
        this.FechaConstitucion = FechaConstitucion;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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

    public TipoIdentificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(TipoIdentificacion identificacion) {
        this.identificacion = identificacion;
    }

    public String getRLAutoridadEmiteId() {
        return RLAutoridadEmiteId;
    }

    public void setRLAutoridadEmiteId(String RLAutoridadEmiteId) {
        this.RLAutoridadEmiteId = RLAutoridadEmiteId;
    }

    public String getRLNumeroId() {
        if ( RLNumeroId == null ){
            RLNumeroId = "";
        }
        return RLNumeroId.trim();
        
    }

    public void setRLNumeroId(String RLNumeroId) {
        this.RLNumeroId = RLNumeroId;
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

    public String getRLIdentificacionTipo() {
        return RLIdentificacionTipo;
    }

    public void setRLIdentificacionTipo(String RLIdentificacionTipo) {
        this.RLIdentificacionTipo = RLIdentificacionTipo;
    }

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
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

    public String getImagenActaConstitutiva() {
        return ImagenActaConstitutiva;
    }

    public void setImagenActaConstitutiva(String ImagenActaConstitutiva) {
        this.ImagenActaConstitutiva = ImagenActaConstitutiva;
    }

    public String getImagenCedulaFiscal() {
        return ImagenCedulaFiscal;
    }

    public void setImagenCedulaFiscal(String ImagenCedulaFiscal) {
        this.ImagenCedulaFiscal = ImagenCedulaFiscal;
    }

    public String getImagenRLId() {
        return ImagenRLId;
    }

    public void setImagenRLId(String ImagenRLId) {
        this.ImagenRLId = ImagenRLId;
    }
    
    public String getImagenRLPoderNotarial() {
        return ImagenRLPoderNotarial;
    }

    public void setImagenRLPoderNotarial(String ImagenRLPoderNotarial) {
        this.ImagenRLPoderNotarial = ImagenRLPoderNotarial;
    }
    
    public String getImagenDeclaratoria() {
        return ImagenDeclaratoria;
    }

    public void setImagenDeclaratoria(String ImagenDeclaratoria) {
        this.ImagenDeclaratoria = ImagenDeclaratoria;
    }



	public String getRlNoPoder() {
		return rlNoPoder;
	}



	public void setRlNoPoder(String rlNoPoder) {
		this.rlNoPoder = rlNoPoder;
	}



	public String getRlNotaria() {
		return rlNotaria;
	}



	public void setRlNotaria(String rlNotaria) {
		this.rlNotaria = rlNotaria;
	}



	public String getRlFechaNotarial() {
		  formato = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date f = formato.parse(this.rlFechaNotarial );
	            rlFechaNotarial = formato.format(f);
	        } catch (ParseException ex) {
	        	System.out.println("error parseando fecha "+ex.toString());
	            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
	            return "";
	        }catch(Exception e) {
	        	System.out.println("error parseando fecha "+e.toString());
	        	return "";
	        }
	        
		
		return rlFechaNotarial;
	}



	public void setRlFechaNotarial(String rlFechaNotarial) {
		this.rlFechaNotarial = rlFechaNotarial;
	}



	public String getNoEscritura() {
		return noEscritura;
	}



	public void setNoEscritura(String noEscritura) {
		this.noEscritura = noEscritura;
	}



	public String getFechaNotarial() {
		formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date f = formato.parse(this.fechaNotarial );
            fechaNotarial = formato.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }

		return fechaNotarial;
	}



	public void setFechaNotarial(String fechaNotarial) {
		this.fechaNotarial = fechaNotarial;
	}



	public String getNotaria() {
		return notaria;
	}



	public void setNotaria(String notaria) {
		this.notaria = notaria;
	}



	public BeneMoral getBeneficiario() {
		return beneficiario;
	}



	public void setBeneficiario(BeneMoral beneficiario) {
		this.beneficiario = beneficiario;
	}



	public SimpleDateFormat getFormato() {
		return formato;
	}



	public void setFormato(SimpleDateFormat formato) {
		this.formato = formato;
	}
    
}
