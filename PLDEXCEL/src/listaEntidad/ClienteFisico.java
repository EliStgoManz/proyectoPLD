package listaEntidad;

import java.util.ArrayList;

public class ClienteFisico extends listaCliente {
	private String rfc;
	private String curp;
	private String fechaNac;
	private String pais;
	private String genero;
	private ArrayList<listaCliente> beneficiarios=new ArrayList<listaCliente>();
	
	public ClienteFisico(String cliente_id, String tipoPersona, 
					     String nombre,String rfc,String curp,
			             String fechaNac,String pais ) {
		super(cliente_id, tipoPersona, nombre);
		this.rfc=rfc;
		this.curp=curp;
		this.fechaNac=fechaNac;
		this.pais=pais;
		
		
		
	}
	
	
	public ClienteFisico(String cliente_id, String tipoPersona, 
		     String nombre){
		super(cliente_id, tipoPersona, nombre);
	}
	
	public String beneficiarios(){
		String s="";
		System.out.println(beneficiarios.size());
		for(int i=0;i<beneficiarios.size();i++){
			s+=beneficiarios.get(i)+"\n";
		}
		return s;
	}
	
	@Override
	public String toString(){
		return " PERSONA FISICA "+"\n"+
				" cliente id: "+getCliente_id()+"\n"+
				"nombre: "+getNombre()+"\n"+
				"rfc: "+getRfc()+"\n"+
				"curp: "+getCurp()+"\n"+
				"Fecha nac: "+getFechaNac()+"\n"+
				"pais: "+getPais()+"\n"+

				"BENEFICIARIOS LISTA "+"\n"+ beneficiarios();
		
	}

	@Override
	public void setearDatos(String rfc,String curp,
            String fechaNac,String pais){
		setRfc(rfc);
		setCurp(curp);
		setFechaNac(fechaNac);
		setPais(pais);
		//setGenero(genero);
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}


	public ArrayList<listaCliente> getBeneficiarios() {
		return beneficiarios;
	}

   @Override
	public void setBeneficiarios(ArrayList<listaCliente> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}


	@Override
	public void setearDatos(String rfcP, String nombre, String apP, String apM, String curp, String rfc, String paisNac,
			String fechaNac) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setRepresentantes(ArrayList<ClienteFisico> c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public listaCliente getRepLegal() {
		// TODO Auto-generated method stub
		return null;
	}
		
		// TODO Auto-generated constructor stub
	}


