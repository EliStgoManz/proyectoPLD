package listaEntidad;

import java.util.ArrayList;

public  abstract class listaCliente {
	private String cliente_id;
	private String tipoPersona;
	private String nombre;
	
	
	public listaCliente(String cliente_id, String tipoPersona, String nombre){
		super();
		this.cliente_id = cliente_id;
		this.tipoPersona = tipoPersona;
		this.nombre = nombre;
		
	}
	
	
    public abstract void setBeneficiarios(ArrayList<listaCliente>c);
    public abstract listaCliente getRepLegal();
	public abstract void setearDatos(String rfcP,String nombre,String apP,String apM,String curp,String rfc,String paisNac,String fechaNac);
	public abstract void setearDatos(String rfc,String curp,String fechaNac,String pais);
	
	public abstract void setRepresentantes(ArrayList<ClienteFisico>c);
	
	public String getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(String cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	


	

	
}
