package listaEntidad;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import datosRatos.Conexion2;
import datosRatos.DatosClienteRaro;
import listaEntidad.Consumo;
import listaEntidad.Validaciones;


public class OperacionesDemonio {
	 int peligro;
		Validaciones v= new Validaciones();
		String implicado="";
		public boolean RevisionCliente(listaCliente c) throws IOException, ParseException, SQLException{
			Consumo consumo= new Consumo();
			CuerpoRespuesta cp;
			OperacionesCoincidencias oc= new OperacionesCoincidencias();
			String cuerpoJson;
			if(c instanceof ClienteFisico ){
				cp=consumo.obtenerCoincidencia(consumo.obtenerUrlFisica((ClienteFisico) c));
				cuerpoJson=cp.getCuerpoRespuesta().replaceAll("'", "");
				if(cp.getCodigoRespuesta()==200){
					peligro=1;
					String[]matches=v.recortarCoincidencia(cuerpoJson);
				//	System.out.println("si hubo coincidencia de "+c.getCliente_id());
					for(int i=0;i<matches.length;i++){
						System.out.println("match a escribir "+matches[i]);
						oc.insertarAvCoincidencia(implicado,c.getCliente_id(), v.obtenerMatchId(matches[i]),matches[i]);
					}
					//System.out.println(cp.getCuerpoRespuesta().substring(2500,2600));
					
					if(!new DatosClienteRaro().apagarBanderariesgo(c.getCliente_id()) ){
						
						oc.editarUltimaRevision(c.getCliente_id(),"0");	
						return false;
						
					}else{
						oc.editarUltimaRevision(c.getCliente_id(),""+peligro);
						
					}
					oc.insertarPistaAudit(cuerpoJson, c.getCliente_id());
					
					return true;
				}else{
					
					System.out.println("no hubo coincidencia de "+c.getCliente_id());
                    
					if(!new DatosClienteRaro().apagarBanderariesgo(c.getCliente_id()) ){
						oc.editarUltimaRevision(c.getCliente_id(),"0");	
						return false;
						
					}else{
						oc.editarUltimaRevision(c.getCliente_id(),""+peligro);
					}

					oc.insertarPistaAudit(cuerpoJson, c.getCliente_id());
					
					return false;
				}
					
					
			}else if(c instanceof ClienteMoral){
				cp=consumo.obtenerCoincidencia(consumo.obtenerUrlMoral((ClienteMoral) c));
				cuerpoJson=cp.getCuerpoRespuesta().replaceAll("'", "");
				if(cp.getCodigoRespuesta()==200){
					peligro=1;
					String[]matches=v.recortarCoincidencia(cuerpoJson);
					//	System.out.println("si hubo coincidencia de "+c.getCliente_id());
						for(int i=0;i<matches.length;i++){
							System.out.println("match a escribir "+matches[i]);
							oc.insertarAvCoincidencia(implicado,c.getCliente_id(), v.obtenerMatchId(matches[i]),matches[i]);
						}
					
						if(!new DatosClienteRaro().apagarBanderariesgo(c.getCliente_id()) ){
							oc.editarUltimaRevision(c.getCliente_id(),"0");	
							return false;
							
						}else{
							oc.editarUltimaRevision(c.getCliente_id(),""+peligro);
						}	
					
					oc.insertarPistaAudit(cuerpoJson, c.getCliente_id());
					return true;
				}else{
					if(!new DatosClienteRaro().apagarBanderariesgo(c.getCliente_id()) ){
						oc.editarUltimaRevision(c.getCliente_id(),"0");	
						return false;
						
					}else{
						oc.editarUltimaRevision(c.getCliente_id(),""+peligro);
					}
					oc.insertarPistaAudit(cuerpoJson, c.getCliente_id());
					return false;
				}
				
			}
			return false;
				
					
			
		}
		
//		private boolean existeApp(){
//			
//			Connection conex = null;
//			Conexion2 c=new Conexion2();   
//		    Statement instruccion;
//		    ResultSet conjuntoResultados=null;
//		    System.out.println("existeApp");
//		       try {
//		    	   conex = c.getConnection("dbpld");
//				instruccion = conex.createStatement( 
//				   ResultSet.TYPE_SCROLL_INSENSITIVE,
//				   ResultSet.CONCUR_READ_ONLY );
//				 String consulta = "Select*from varconfiguracion where wslistaobtenertoken='' ;";
//				 conjuntoResultados = instruccion.executeQuery(consulta); 
//				 
//				 if(conjuntoResultados.next()){
//					 conex.close();
//				      return true;
//				 }
//				 else{
//					 return false;
//				}
//		}catch(Exception e){
//			System.out.println(e.toString());
//			
//			return false;
//		}
//		}
		
		private boolean revisarBeneficiarios(ArrayList<listaCliente>beneficiarios) throws IOException, ParseException, SQLException{
			implicado="Beneficiario";
			boolean bandera = false;
			for(int i=0;i<beneficiarios.size();i++){
				if(bandera)
				 RevisionCliente(beneficiarios.get(i));
				else 
					bandera=RevisionCliente(beneficiarios.get(i));
				 if( !( beneficiarios.get(i) instanceof ClienteFisico))
					 if(bandera)
					 RevisionCliente(beneficiarios.get(i).getRepLegal());
					 else 
					 bandera=RevisionCliente(beneficiarios.get(i).getRepLegal());
			 }
			return bandera;	 
		}
		
		private boolean revisarRepresentantes(ArrayList<ClienteFisico>representantes) throws IOException, ParseException, SQLException{
			implicado="Representante";
			boolean bandera=false;
			for(int k=0;k<representantes.size();k++){
				if(bandera)
					RevisionCliente(representantes.get(k));
				else 
					bandera=RevisionCliente(representantes.get(k));
			}
			return bandera;
		}
		
		
		
		
	public boolean Revision(listaCliente c) throws IOException, ParseException, SQLException{
		implicado="Cliente";
				peligro=0;
				boolean bande=false;
				
					
				if(c instanceof ClienteFisico){
					ClienteFisico cf=(ClienteFisico) c;
					bande=RevisionCliente(cf);
					
					if(bande)
					 revisarBeneficiarios(cf.getBeneficiarios());
					else
						bande=revisarBeneficiarios(cf.getBeneficiarios());
						
				}else if(c instanceof ClienteMoral){
					ClienteMoral cm=(ClienteMoral) c;
					 bande=RevisionCliente(cm);   
					 implicado="Representante principal";
					 
					 if(bande)
					RevisionCliente(cm.getRepLegal());//Revision RepLegalPrincipal
					 else
					bande=RevisionCliente(cm.getRepLegal());
					
					 if(bande)
					revisarBeneficiarios(cm.getBeneficiarios());
					 else
					bande=revisarBeneficiarios(cm.getBeneficiarios());
					
					if(bande) 
					revisarRepresentantes(cm.getListaRepresentantes());
					else 
					bande=revisarRepresentantes(cm.getListaRepresentantes());
					
			}else{
				ClienteGobFid cgf=(ClienteGobFid) c;
				
				bande=RevisionCliente(new ClienteMoral(cgf.getCliente_id(),cgf.getTipoPersona(),cgf.getNombre()));
				implicado="Representante principal";
				if(bande)
				RevisionCliente(cgf.getRepLegal());
				else 
				bande=RevisionCliente(cgf.getRepLegal());
				
				if(bande)
					revisarRepresentantes(cgf.getListaRepresentantes());
				else
				bande=revisarRepresentantes(cgf.getListaRepresentantes());
				
			}
	
				return bande;
				
		}	
			

	 

//		public void correDemonio() throws SQLException, IOException, ParseException{
//			Operaciones o= new Operaciones();
//			ArrayList<Cliente>cl=o.obtenerId();
//			cl=o.llenarArrayClientes(cl);
//			Revision(cl);
//		}
}
