package control;

public class DigitoAlfanumerico {

	private int sumarDigitosAnio(int anio){
		int suma=0;
		String anioo=anio+"";
		for(int i=0;i<anioo.length();i++){
			suma+=Integer.parseInt(anioo.charAt(i)+"");
		}
		return suma;
	}

	private int primerDigitoRfc(String rfc){
	
		for(int i=0;i<rfc.length();i++){
			if(Character.isDigit(rfc.charAt(i)))
				return i;
		}
		return -1;
	}
	
	
	private String digitosRfc(String rfc){
		int i=primerDigitoRfc(rfc);
		return rfc.charAt(i)+""+rfc.charAt(i+1)+""+rfc.charAt(i+2)+""+rfc.charAt(i+3)+""+rfc.charAt(i+4)+""+rfc.charAt(i+5);
			
		}
	
	private int SumaPares(String numero){
		int suma=0;
		for(int i=0;i<numero.length();i++){
			if(i%2==0)
				suma+=Integer.parseInt(numero.charAt(i)+"");
		}
		return suma;
	}
	private int SumaNones(String numero){
		int suma=0;
		for(int i=0;i<numero.length();i++){
			if(i%2!=0)
				suma+=Integer.parseInt(numero.charAt(i)+"");
		}
		return suma;
	}
	
	private int numeroParaCompletarDecena(int numero){
		String num=numero+"";
		String ultimoDigito=num.charAt(num.length()-1)+"";
		if(ultimoDigito.equals("0"))
			return 0;
		return 10-Integer.parseInt(ultimoDigito);
	}
	
	public String calcularCodigoAlfaNumerico(String rfc,int anio){
			String digitosRfc=digitosRfc(rfc);
			String multiplicacion=(sumarDigitosAnio(anio)*Integer.parseInt(digitosRfc))+"";
		if(multiplicacion.length()<4){
			for(int i=4;i>multiplicacion.length();i--){
				multiplicacion="0"+multiplicacion;
			}
		}
		multiplicacion=multiplicacion.substring(multiplicacion.length()-4,multiplicacion.length());
		digitosRfc+=multiplicacion;
		int resultadoParcial=numeroParaCompletarDecena((SumaPares(digitosRfc)*3)+SumaNones(digitosRfc));
		
	          return rfc.substring(0,3)+""+multiplicacion+resultadoParcial;
	    }
	         
	
	public static void main(String[]args){
		DigitoAlfanumerico a= new DigitoAlfanumerico();
		System.out.println(a.calcularCodigoAlfaNumerico("GEA131018NA7",2021));
	}
}