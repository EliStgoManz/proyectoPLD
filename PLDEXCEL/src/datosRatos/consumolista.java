package datosRatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import datosRatos.Conexion;
import listaEntidad.listaCliente;
import listaEntidad.ClienteFisico;
import listaEntidad.ClienteGobFid;
import listaEntidad.ClienteMoral;

public class consumolista {

	
	public void llenarBenefFideicomiso(ArrayList<listaCliente>cl,String clienteid) throws SQLException {
	    String razonsocial,rfc,rlnombre,rlapp,rlapm,rlcurp,rlrfc,rlfechanac,pais;
	    listaCliente cll;	   
	    Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;   
	    try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+consumolista.class.getName()+ ".llenarBenefFideicomiso() "+e);
		}
	    try {
	    	instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
			String consulta = "SELECT * from benefideicomiso where cliente_id='"+clienteid+"';";
		       conjuntoResultados = instruccion.executeQuery(consulta);
		       while(conjuntoResultados.next()){
		    	   razonsocial=conjuntoResultados.getString("razonsocial");
		    	   rfc=conjuntoResultados.getString("rfc");
		    	   rlnombre=conjuntoResultados.getString("rlnombre");
		    	   rlapp=conjuntoResultados.getString("rlapellidopaterno");
		    	   rlapm=conjuntoResultados.getString("rlapellidomaterno");
		    	   rlcurp=conjuntoResultados.getString("rlcurp");
		    	   rlrfc=conjuntoResultados.getString("rlrfc");
		    	   rlfechanac=conjuntoResultados.getString("rlfechanacimiento");
		    	   pais=conjuntoResultados.getString("pais");
		    	   cll=new ClienteMoral(clienteid,"X",razonsocial);
		    	   cll.setearDatos(rfc,rlnombre,rlapp,rlapm,rlcurp,rlrfc,pais,rlfechanac);
		    	   cl.add(cll);
		       }
		       conjuntoResultados.close();
		        
		} catch (SQLException e) {
			System.out.println("ERROR get beneficiarioFideicomiso "+consumolista.class.getName()+ ".llenarBenefFideicomiso() "+e);
		}finally{
			 conex.close();
		}
	       
	}
	
	public void llenarBenefMoral(ArrayList<listaCliente>cl,String clienteid) throws SQLException {
		String razonsocial,rfc,rlnombre,rlapp,rlapm,rlcurp,rlrfc,pais;
	    listaCliente cll;
	    Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	    try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+consumolista.class.getName()+ ".llenarBenefMoral() "+e);
		}
	    
	    try {
	    	instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			String consulta = "SELECT * from benemoral where cliente_id='"+clienteid+"';";
		    conjuntoResultados = instruccion.executeQuery(consulta);
		       while(conjuntoResultados.next()){
		    	   razonsocial=conjuntoResultados.getString("razonsocial");
		    	   rfc=conjuntoResultados.getString("rfc");
		    	   rlnombre=conjuntoResultados.getString("rlnombre");
		    	   rlapp=conjuntoResultados.getString("rlapellidopaterno");
		    	   rlapm=conjuntoResultados.getString("rlapellidomaterno");
		    	   rlcurp=conjuntoResultados.getString("rlcurp");
		    	   rlrfc=conjuntoResultados.getString("rlrfc");
		    	   pais=conjuntoResultados.getString("pais");
		    	   cll=new ClienteMoral(clienteid,"M",razonsocial);
		    	   cll.setearDatos(rfc,rlnombre,rlapp,rlapm,rlcurp,rlrfc,pais,null);
		    	   cl.add(cll);
		       }
		       conjuntoResultados.close();
		} catch (SQLException e) {
			System.out.println("ERROR consulta "+consumolista.class.getName()+ ".llenarBenefMoral() "+e);
		}finally{
			conex.close();
		}
	}

	public void llenarBenefFisica(ArrayList<listaCliente>cl,String clienteid) throws SQLException{
		System.out.println("entrro al metodo");
		String nombre,app,apm,fechanacimiento,rfc,curp,pais;
		listaCliente cll;
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    
	    try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+consumolista.class.getName()+ ".llenarBenefFisica() "+e);
		}
	    try {
			instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
			String consulta = "SELECT * from benefisica where cliente_id='"+clienteid+"';";
		    conjuntoResultados = instruccion.executeQuery(consulta);
		    while(conjuntoResultados.next()){
		    	   nombre=conjuntoResultados.getString("nombre");
		    	   app=conjuntoResultados.getString("apellidopaterno");
		    	   apm=conjuntoResultados.getString("apellidomaterno");
		    	   fechanacimiento=conjuntoResultados.getString("fechanacimiento");
		    	   rfc=conjuntoResultados.getString("rfc");
		    	   curp=conjuntoResultados.getString("curp");
		    	   pais=conjuntoResultados.getString("paisnacim");
		    	   cll=new ClienteFisico(clienteid, "F", nombre+" "+app+" "+apm);
		    	   cll.setearDatos(rfc, curp, fechanacimiento, pais);
		    	   cl.add(cll);
		       }
		    conjuntoResultados.close();
		} catch (SQLException e) {
			System.out.println("ERROR consulta llenarBenefFisica"+consumolista.class.getName()+ ".llenarBenefFisica() "+e);
		}finally {
			conex.close();
		}
	       
	}
	public ArrayList<listaCliente>benefsCliente(String clienteid) throws SQLException{
		ArrayList<listaCliente>beneficiarios= new ArrayList<listaCliente>();
		llenarBenefFideicomiso(beneficiarios,clienteid);
		llenarBenefMoral(beneficiarios, clienteid);
		llenarBenefFisica(beneficiarios, clienteid);
		return beneficiarios;
	}

	public ArrayList<ClienteFisico> repLegalCliente(String clienteid) throws SQLException{
		ArrayList<ClienteFisico> rep= new ArrayList<ClienteFisico>();
		String clienteidd="";
		String nombre="";
		String apP="";
		String apM="";
		String rfc="";
	    String curp="";
	    String fechanac="";
		Connection conex = null;
	    Conexion2 c=new Conexion2();   
	    Statement instruccion;
	    ResultSet conjuntoResultados=null;
	    try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+consumolista.class.getName()+ ".repLegalCliente()"+e);
		}
	    try {
			instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
			String consulta = "SELECT * from replegal where cliente_id='"+clienteid+"';";
			conjuntoResultados = instruccion.executeQuery(consulta);   
			   while(conjuntoResultados.next()){
				   clienteidd=clienteid;
			       nombre=conjuntoResultados.getString("rlnombre");
			       apP=conjuntoResultados.getString("rlapellidopaterno");
			       apM=conjuntoResultados.getString("rlapellidomaterno");
			       rfc=conjuntoResultados.getString("rlrfc");
			       curp=conjuntoResultados.getString("rlcurp");
			       fechanac=conjuntoResultados.getString("rlfechanacimiento");
			       rep.add(new ClienteFisico(clienteid,"F",nombre+" "+apP+" "+apM,rfc,curp,fechanac,""));
			    }
			   conjuntoResultados.close();
		} catch (SQLException e) {
			System.out.println("ERROR consulta repLegalCliente"+consumolista.class.getName()+ ".repLegalCliente() "+e);
		}finally {
			conex.close();
		}
		return rep;	
	}


	public listaCliente llenarDatosCliente(listaCliente cl) throws SQLException{
		Connection conex = null;
		Conexion2 c=new Conexion2();   
		Statement instruccion;
		ResultSet conjuntoResultados=null;
		
		try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+consumolista.class.getName()+ ".llenarDatosCliente() "+e);
		}
		
		instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );		
		String curp;
		String rfc;
		String genero;
		String paisNac;
		String fechaNac;	
	
		if(cl.getTipoPersona().equals("F")){
		   String consulta = "select*from avpersonafisica where cliente_id='"+cl.getCliente_id() +"'; ";
	       conjuntoResultados = instruccion.executeQuery(consulta);
		   if(conjuntoResultados.next()){				
				 curp=conjuntoResultados.getString("curp");
				 rfc=conjuntoResultados.getString("rfc");
				 //genero=conjuntoResultados.getString("nombre");
				 paisNac=conjuntoResultados.getString("paisnacio");
				 fechaNac=conjuntoResultados.getString("fechanacimiento");
				 cl.setearDatos( rfc,curp, fechaNac, paisNac);
				 // sigue llenar su arreglo de  beneficiarios
				 cl.setBeneficiarios(benefsCliente(cl.getCliente_id()));
			 }
		   conjuntoResultados.close();
			
		}else if(cl.getTipoPersona().equals("M")){
			 String consulta = "select*from avpersonamoral where cliente_id='"+cl.getCliente_id() +"'; ";
			 conjuntoResultados = instruccion.executeQuery(consulta);
			 String nombre,apP,apM,rfcP;
			 if(conjuntoResultados.next()){
				 rfcP=conjuntoResultados.getString("rfc");
				 nombre=conjuntoResultados.getString("rlnombre");
				 apP=conjuntoResultados.getString("rlapellidopaterno");
				 apM=conjuntoResultados.getString("rlapellidomaterno");
				 curp=conjuntoResultados.getString("rlcurp");
				 rfc=conjuntoResultados.getString("rlrfc");
				 //genero=conjuntoResultados.getString("nombre");
				 paisNac=conjuntoResultados.getString("pais");
				 fechaNac=conjuntoResultados.getString("rlfechanacimiento");
				 cl.setearDatos(rfcP,nombre,apP,apM, curp, rfc, paisNac, fechaNac);
				 //sigue llenar su arreglo de beneficiarios y representantes
				 cl.setRepresentantes(repLegalCliente(cl.getCliente_id()));
				 cl.setBeneficiarios(benefsCliente(cl.getCliente_id()));
			 }
			 conjuntoResultados.close();
			
		}else if(cl.getTipoPersona().equals("X")){
			 String consulta = "select*from avfideicomiso where cliente_id='"+cl.getCliente_id() +"'; ";
			 conjuntoResultados = instruccion.executeQuery(consulta);
			 String nombre,apP,apM,rfcP;
			 if(conjuntoResultados.next()){
				 rfcP=conjuntoResultados.getString("rfc");
				 nombre=conjuntoResultados.getString("rlnombre");
				 apP=conjuntoResultados.getString("rlapellidopaterno");
				 apM=conjuntoResultados.getString("rlapellidomaterno");
				 curp=conjuntoResultados.getString("rlcurp");
				 rfc=conjuntoResultados.getString("rlrfc");
				 //genero=conjuntoResultados.getString("nombre");
				 paisNac="";
				 fechaNac="";
				 cl.setearDatos( rfcP,nombre,apP,apM,curp, rfc, paisNac, fechaNac);
				 //sigue llenar su arreglo de representantes
				 cl.setRepresentantes(repLegalCliente(cl.getCliente_id()));
			 }
			 conjuntoResultados.close();
			 
		}else if(cl.getTipoPersona().equals("G")){
			 String consulta = "select*from avgobierno where cliente_id='"+cl.getCliente_id() +"'; ";
			 conjuntoResultados = instruccion.executeQuery(consulta);
			 String nombre,apP,apM,rfcP;
			 if(conjuntoResultados.next()){
			 rfcP=conjuntoResultados.getString("rfc");
			 nombre=conjuntoResultados.getString("rlnombre");
			 apP=conjuntoResultados.getString("rlapellidopaterno");
			 apM=conjuntoResultados.getString("rlapellidomaterno");
			 curp=conjuntoResultados.getString("rlcurp");
			 rfc=conjuntoResultados.getString("rlrfc");
			 //genero=conjuntoResultados.getString("nombre");
			 paisNac=conjuntoResultados.getString("pais");
			 fechaNac=conjuntoResultados.getString("rlfechanacimiento");
			 cl.setearDatos(rfcP,nombre,apP,apM,curp, rfc, paisNac, fechaNac);
			 //sigue llenar su arreglo de representantes
			 cl.setRepresentantes(repLegalCliente(cl.getCliente_id()));	 
			 }
			 conjuntoResultados.close();
		}
	
		conex.close();
		
		return cl;
	}

	//public static void main(String[]args){
	//	consumolista c= new consumolista();
	//	//este codigo va en tu consumo
	//	String tipoPersona="F"; //aqui obtienes el tipo de persona
	//	if(tipoPersona=="F"){
	//		ClienteFisico cliente=new ClienteFisico("A-XXXXXX","F","NOMBRE");
	//		cliente=(ClienteFisico) c.llenarDatosCliente(cliente);
	//	}else if(tipoPersona=="M"){
	//		ClienteMoral cliente=new ClienteMoral("A-XXXXXX","F","NOMBRE");
	//		cliente=(ClienteMoral) c.llenarDatosCliente(cliente);
	//	}else {
	//		ClienteGobFid cliente=new ClienteGobFid("A-XXXXXX",tipoPersona,"NOMBRE");
	//		cliente=(ClienteGobFid) c.llenarDatosCliente(cliente);
	//	}
	//}

}
