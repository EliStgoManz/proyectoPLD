package listaEntidad;

import java.util.ArrayList;

public class ClienteMoral extends listaCliente {
	private ClienteFisico repLegal;
	private ArrayList<ClienteFisico>listaRepresentantes=new ArrayList<ClienteFisico>();
	private ArrayList<listaCliente>beneficiarios=new ArrayList<listaCliente>();
	private String rfc;
	
	public ClienteMoral(String cliente_id, String tipoPersona, String nombre,
			            ClienteFisico repLegal) {
		super(cliente_id, tipoPersona, nombre);
		this.repLegal=repLegal;
	
	}

	public ClienteMoral(String cliente_id, String tipoPersona, String nombre,String rfc){
		super(cliente_id, tipoPersona, nombre);
		this.rfc=rfc;
		
	}
	public void setearDatos(String rfcPrin,String nombre,String apP,String apM,String curp,String rfc2,String paisNac,String fechaNac)
	{ 
		rfc=rfcPrin;
		setRepLegal(new ClienteFisico(this.getCliente_id(), "F", nombre+" "+apP+" "+ apM,rfc2, curp, fechaNac,paisNac));
	}
	
	public ClienteMoral(String cliente_id, String tipoPersona, 
		     String nombre){
		super(cliente_id, tipoPersona, nombre);
		
	}
	
	@Override
	public listaCliente getRepLegal() {
		return repLegal;
	}

	public void setRepLegal(ClienteFisico repLegal) {
		this.repLegal = repLegal;
	}

	public ArrayList<ClienteFisico> getListaRepresentantes() {
		return listaRepresentantes;
	}

	@Override
	public void setRepresentantes(ArrayList<ClienteFisico> listaRepresentantes) {
		this.listaRepresentantes = listaRepresentantes;
	}

	public ArrayList<listaCliente> getBeneficiarios() {
		return beneficiarios;
	}

	@Override
	public void setBeneficiarios(ArrayList<listaCliente> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String beneficiarios(){
		String s="";
		System.out.println(beneficiarios.size());
		for(int i=0;i<beneficiarios.size();i++){
			s+=beneficiarios.get(i)+"\n";
		}
		return s;
	}
	public String representantes(){
		String s="";
		System.out.println(listaRepresentantes.size());
		for(int i=0;i<listaRepresentantes.size();i++){
			s+=listaRepresentantes.get(i)+"\n";
		}
		return s;
	}
	@Override
	public String toString(){
		return  "PERSONA MORAL"+"\n"+
				"cliente id: "+getCliente_id()+"\n"+
				"razon social: "+getNombre()+"\n"+
				"rfc: "+getRfc()+"\n"+
				"REPRESENTANTE LEGAL PRINCIPAL"+"\n"+
				repLegal
				+"BENEFICIARIOS LISTA "+"\n"+ beneficiarios()+"\n"+
				"REPRESENTANTES LISTA "+"\n"+ representantes()+"\n";
		
	}

	@Override
	public void setearDatos(String rfc, String curp, String fechaNac, String pais) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
