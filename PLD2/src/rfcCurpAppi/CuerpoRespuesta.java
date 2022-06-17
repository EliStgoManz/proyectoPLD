package rfcCurpAppi;


public class CuerpoRespuesta {

	private String cuerpoRespuesta;
	private int codigoRespuesta;
	
	
	
	public CuerpoRespuesta(String cuerpoRespuesta, int codigoRespuesta) {
		super();
		this.cuerpoRespuesta = cuerpoRespuesta;
		this.codigoRespuesta = codigoRespuesta;
	}
	
	
	public String getCuerpoRespuesta() {
		return cuerpoRespuesta;
	}
	public void setCuerpoRespuesta(String cuerpoRespuesta) {
		this.cuerpoRespuesta = cuerpoRespuesta;
	}
	public int getCodigoRespuesta() {
		return codigoRespuesta;
	}
	public void setCodigoRespuesta(int codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	
}
