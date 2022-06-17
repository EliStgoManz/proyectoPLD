package listaEntidad;

public class Coincidencia {
	private String matchid;
	private String cuerpoJson;
	private String explicacion;
	private String implicado;
	
	
	public Coincidencia(String matchid, String cuerpoJson, String explicacion,String implicado) {
		super();
		this.matchid = matchid;
		this.cuerpoJson = cuerpoJson;
		if(explicacion==null||explicacion.isEmpty()||explicacion.equals("null"))
			this.explicacion="";
		else
		this.explicacion = explicacion;
		
		this.implicado= implicado;
	}
	
	
	public Coincidencia(String matchid,String explicacion){
		this.matchid = matchid;
		if(explicacion==null||explicacion.isEmpty()||explicacion.equals("null"))
			this.explicacion="";
		else
		this.explicacion = explicacion;
	}
	
	
	public String getExplicacion() {
		return explicacion;
	}


	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}


	public String getImplicado() {
		return implicado;
	}


	public void setImplicado(String implicado) {
		this.implicado = implicado;
	}


	public String getMatchid() {
		return matchid;
	}
	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}
	public String getCuerpoJson() {
		return cuerpoJson;
	}
	public void setCuerpoJson(String cuerpoJson) {
		this.cuerpoJson = cuerpoJson;
	}
	public String getDescripcion() {
		return explicacion;
	}
	public void setDescripcion(String explicacion) {
		this.explicacion = explicacion;
	}
	
	

}
