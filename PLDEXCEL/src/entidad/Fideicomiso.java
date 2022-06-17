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
public class Fideicomiso {
 
        Cliente cliente;
        String 	razonsocial;
        String 	rfc;
        String 	nrofideicomiso;
        String 	rlnombre;
        String 	rlapellidopaterno;
        String 	rlapellidomaterno;
        String 	rlfechanacimiento;
        String 	rlrfc;
        TipoIdentificacion identificacion;
        String 	rlautoridademiteid;
        String 	rlnumeroid;
        String 	rlcurp;
        String 	rlidentificaciontipo;
        String 	fecharegistro;
        String 	imagenactaconstitutiva;
        String 	imagencedulafiscal;
        String 	imagenrlid;
//        String 	imagenrlcedulafiscal;
        String 	imagenrlpodernotarial;
        String  institucionfiduciaria;
        
        SimpleDateFormat formato;
        
        
        
        String rlNoPoder;
        String rlNotaria;
        String rlFechaNotarial;
        String noEscritura;
        String fechaNotarial;
        String notaria;

    public Fideicomiso(Cliente cliente, String razonsocial, String rfc, 
    		String nrofideicomiso, String rlnombre, String rlapellidopaterno, 
    		String rlapellidomaterno, String rlfechanacimiento, String rlrfc,
    		TipoIdentificacion identificacion, String rlautoridademiteid,
    		String rlnumeroid, String rlcurp, String rlidentificaciontipo, 
    		String fecharegistro, String imagenactaconstitutiva, String imagencedulafiscal,
    		String imagenrlid, String imagenrlpodernotarial, String institucionfiduciaria,
    		String rlNoPoder,
    		String rlNotaria,String rlFechaNotarial,String noEscritura, String fechaNotarial,
    		String notaria) {
        this.cliente = cliente;
        this.razonsocial = razonsocial;
        this.rfc = rfc;
        this.nrofideicomiso = nrofideicomiso;
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
        this.fecharegistro = fecharegistro;
        this.imagenactaconstitutiva = imagenactaconstitutiva;
        this.imagencedulafiscal = imagencedulafiscal;
        this.imagenrlid = imagenrlid;
//        this.imagenrlcedulafiscal = imagenrlcedulafiscal;
        this.imagenrlpodernotarial = imagenrlpodernotarial;
        this.institucionfiduciaria = institucionfiduciaria;
        this.rlNoPoder=rlNoPoder;
        this.rlNotaria=rlNotaria;
        this.rlFechaNotarial=rlFechaNotarial;
        this.noEscritura=noEscritura;
        this.fechaNotarial=fechaNotarial;
        this.notaria=notaria;
    }

    public Fideicomiso() {
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNrofideicomiso() {
        if ( nrofideicomiso == null ){
            nrofideicomiso = "";
        }
            
        return nrofideicomiso.trim();
    }

    public void setNrofideicomiso(String nrofideicomiso) {
        this.nrofideicomiso = nrofideicomiso;
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
        } catch (ParseException ex) {
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
        if ( rlnumeroid == null ){
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

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getImagenactaconstitutiva() {
        return imagenactaconstitutiva;
    }

    public void setImagenactaconstitutiva(String imagenactaconstitutiva) {
        this.imagenactaconstitutiva = imagenactaconstitutiva;
    }

    public String getImagencedulafiscal() {
        return imagencedulafiscal;
    }

    public void setImagencedulafiscal(String imagencedulafiscal) {
        this.imagencedulafiscal = imagencedulafiscal;
    }

    public String getImagenrlid() {
        return imagenrlid;
    }

    public void setImagenrlid(String imagenrlid) {
        this.imagenrlid = imagenrlid;
    }

//    public String getImagenrlcedulafiscal() {
//        return imagenrlcedulafiscal;
//    }
//
//    public void setImagenrlcedulafiscal(String imagenrlcedulafiscal) {
//        this.imagenrlcedulafiscal = imagenrlcedulafiscal;
//    }

    public String getImagenrlpodernotarial() {
        return imagenrlpodernotarial;
    }

    public void setImagenrlpodernotarial(String imagenrlpodernotarial) {
        this.imagenrlpodernotarial = imagenrlpodernotarial;
    }

        
    
    public String getInstitucionFiduciaria() {
        return institucionfiduciaria;
    }

    public void setInstitucionFiduciaria(String institucionfiduciaria) {
        this.institucionfiduciaria = institucionfiduciaria;
    }

	public String getInstitucionfiduciaria() {
		return institucionfiduciaria;
	}

	public void setInstitucionfiduciaria(String institucionfiduciaria) {
		this.institucionfiduciaria = institucionfiduciaria;
	}

	public SimpleDateFormat getFormato() {
		return formato;
	}

	public void setFormato(SimpleDateFormat formato) {
		this.formato = formato;
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
	            Logger.getLogger(PersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
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
   
}
