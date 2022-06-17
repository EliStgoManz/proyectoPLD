package listaEntidad;

import java.util.ArrayList;

public class ClienteGobFid extends listaCliente{
	
	private ClienteFisico repLegal;
	private ArrayList<ClienteFisico>listaRepresentantes= new ArrayList<ClienteFisico>();
	private String rfc;
	
	
	
	
	public ClienteGobFid(String cliente_id, String tipoPersona, String nombre,
			ClienteFisico repLegal) {
		super(cliente_id, tipoPersona, nombre);
		// TODO Auto-generated constructor stub
		this.repLegal= repLegal;
		
	}
	
	@Override
	public void setearDatos(String rfcP,String nombre,String apP,String apM,String curp,String rfc,String paisNac,String fechaNac)
	{	this.rfc=rfcP;
		setRepLegal(new ClienteFisico(this.getCliente_id(), "F", nombre+" "+apP+" "+ apM,rfc, curp, fechaNac,paisNac));
	}
	public ClienteGobFid(String cliente_id, String tipoPersona, 
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
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
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
		return (getTipoPersona().equals("G")?"GOBIERNO":"FIDEICOMISO")+"\n"+
				"cliente_id "+getCliente_id()+"\n"+
				"razon social: "+getNombre()+"\n"+
				"rfc: "+getRfc()+"\n"+
				"REPRESENTANTE LEGAL PRINCIPAL: "+"\n"+
				repLegal +
				"REPRESENTANTES LISTA "+"\n"+ representantes()+"\n";
				
	}

	
	
	
	
	@Override
	public void setearDatos(String rfc, String curp, String fechaNac, String pais) {
	
	}

	@Override
	public void setBeneficiarios(ArrayList<listaCliente> c) {
	}
}
