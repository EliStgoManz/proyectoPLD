package datosRatos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatosCrearLog {
	
//	PUBLIC STATIC VOID MAIN(STRING [] ARGS ){
//		DATOSCREARLOG L = NEW DATOSCREARLOG();
//		L.LOG("C:/USERS/52722/WORKSPACE-1-3-6/PLD2/CARGA", "A-000032", "DATOS", "TRONO NOMAS");
//	}
	
	
	/**
	 * @param Ruta
	 * @param cliente_id
	 * @param clase
	 * @param error
	 * @return
	 */
	/**
	 * @param Ruta
	 * @param cliente_id
	 * @param clase
	 * @param error
	 * @return
	 */
	public boolean Log(String Ruta,String cliente_id, String clase, String error){
		
			
		try{
			DatosCrearLog D=new DatosCrearLog();
			D.Delete(Ruta);
		}catch(Exception e){
			System.out.println("ERROR delete log "+DatosCrearLog.class.getName()+".Log() "+e);
		}
		
		
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss");
		String fechaTexto = formatter.format(sqlDate);
	    
	    File fichero;
	    FileWriter escribir;
	    PrintWriter info;
		fichero = new File(Ruta+"/Logs/"+sqlDate+".txt");
		
			if(!fichero.exists()){
				try {
				fichero.createNewFile();
				escribir = new FileWriter(fichero, true);
				info = new PrintWriter(escribir);
				info.write("Fecha-Hora    "+ "       Cliente "+ "  Clase.java  "+ "     Mensaje de error ");
				info.write("\n");
				info.println(fechaTexto+" "+cliente_id+"-"+clase+"            "+error);
				info.close();
				escribir.close();
				}catch(IOException ex){
					System.out.println("ERROR log PLD2 "+DatosCrearLog.class.getName()+".Log() "+ex);
				}
			}else{
				try {
						escribir = new FileWriter(fichero, true);
						info = new PrintWriter(escribir);
						info.println(fechaTexto+" "+cliente_id+"-"+clase+"            "+error);
						info.close();
						escribir.close();
				}catch(IOException ex){
					System.out.println("ERROR log PLD2 "+DatosCrearLog.class.getName()+".Log() "+ex);
				}			
			}		
		System.out.println("Log PLD2");
		return true;
	}
	
	
	public boolean Delete(String Ruta){	
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
		
		Date newDate = DatosCrearLog.subtractDays(fecha, 7);
//		System.out.println("La fecha después de restar "+7+" días: "+newDate.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTexto = formatter.format(newDate);
		System.out.println("La fecha del archivo Logs que eliminara " +fechaTexto);
				
		File fiche;
		fiche = new File(Ruta+"/Logs/"+fechaTexto+".txt");
		System.out.println("ruta del Archivo Logs : "+fiche);
		if(!fiche.exists()){
			return true;			
		}else{
			fiche.delete();
			return true;
		}	    	
	}
	
	public static Date subtractDays(Date fecha, int dias) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, -dias);
		return cal.getTime();
	}
}