/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Actividad;
import entidad.BeneFideicomiso;
import entidad.BeneFisica;
import entidad.BeneMoral;
import entidad.Beneficiario;
import entidad.Cliente;
//import entidad.Domicilio;
import entidad.Giro;
import entidad.Pais;
import entidad.TipoIdentificacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import utilidades.Fecha;
import utilidades.Rutas;

/**
 *
 * @author Israel Osiris GarcÃ­a
 */
public class DatosBeneficiario {
    
    private Conexion2 cnn = new Conexion2();
    private ResultSet conjuntoResultados;   
    boolean bandera;
//    String fech = "2001-01-01";
    
    
    
    
    
    public boolean changeBeneFideicomiso(BeneFideicomiso b,String perfilid,String usuarioEdicion  ){
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosBeneficiario.class.getName()+ ".changeBeneFideicomiso() "+e);
		}
        
        CallableStatement instruccion;
        boolean resultado = false;
        //rellenos  
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        java.sql.Date sqlFechaNacimiento = null;
        java.sql.Date sqlFechaNa = null;
        
        SimpleDateFormat formato;
        formato = new SimpleDateFormat("yyyy-MM-dd");        
        String fechaNacimiento = b.getFechanacimiento();
        String fechaZeros ="2001-01-01";
//        try{
//            if(b.getFechanacimiento() != null){
//                
//                java.util.Date utilFechaNa = formato.parse( fechaZeros );
//                sqlFechaNa = new java.sql.Date ( utilFechaNa.getTime() );
//            }else{
//                java.util.Date utilFechaNacimiento = formato.parse( fechaNacimiento );
//                  sqlFechaNacimiento = new java.sql.Date ( utilFechaNacimiento.getTime() );
//            }
//            
//        } catch(ParseException es){
//            es.printStackTrace();
//            sqlFechaNacimiento=sqlDate;
//        }
        try {
            instruccion = conex.prepareCall("{call usp_change_beneficiariofideicomiso(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            instruccion.setString(1, b.getCliente().getCliente_Id());
            instruccion.setInt(2, b.getNrobene());
            instruccion.setString(3, b.getRazonsocial());
            instruccion.setString(4, b.getRfc());
            instruccion.setString(5, b.getNrofideicomiso());
            instruccion.setString(6, b.getTepais() );
            instruccion.setString(7, b.getTelefono());
            instruccion.setString(8, b.getTipodomi());
            instruccion.setDate(9, sqlDate );
            instruccion.setString(10, b.getImagencedulafiscal() );
            instruccion.setString(11, b.getInstitucionFiduciaria() );
            instruccion.setString(12, b.getImagenactaconstitutiva() );
            instruccion.setString(13, b.getImagenpodernotarial() );
            instruccion.setString(14, b.getImagenIdRepresentante() );
            instruccion.setString(15, b.getRlnombre() );
            instruccion.setString(16, b.getRlapellidopaterno() );
            instruccion.setString(17, b.getRlapellidomaterno() );            
            if ( b.getFechanacimiento() != null && !b.getFechanacimiento().isEmpty()){
                java.util.Date utilFechaNacimiento = formato.parse( fechaNacimiento );
                 sqlFechaNacimiento = new java.sql.Date ( utilFechaNacimiento.getTime() );
                instruccion.setDate(18, sqlFechaNacimiento);
            } else {
                java.util.Date utilFechaNa = formato.parse( fechaZeros );
                sqlFechaNa = new java.sql.Date ( utilFechaNa.getTime() );
                instruccion.setDate(18, sqlFechaNa);
            }
            instruccion.setString(19, b.getRlRFC() );
            instruccion.setString(20, b.getRlCURP() );
//            instruccion.setString(21, b.getRlIdentifica_Id() );
            if ( b.getIdentifica_id().getIdentifica_id() != null && !b.getIdentifica_id().getIdentifica_id().isEmpty()){
                instruccion.setString(21, b.getIdentifica_id().getIdentifica_id() );
            } else {
                instruccion.setString(21, "14");
            }             
             
             
            instruccion.setString(22, b.getRlAutoridadEmiteId() );
            instruccion.setString(23, b.getRlNumeroId() );
            instruccion.setString(24, b.getRlIdentificacionTipo() );
            instruccion.setString(25, b.getEmail() );
            instruccion.setString(26, b.getPais());
            instruccion.setString(27, b.getEstado_prov());
            instruccion.setString(28, b.getCiudad());
            instruccion.setString(29, b.getColonia());
            instruccion.setString(30, b.getCalle());
            instruccion.setString(31, b.getNumexterior());
            instruccion.setString(32, b.getNuminterior());
            instruccion.setString(33, b.getCodpostal());
            instruccion.setInt(34, Integer.parseInt(perfilid));
            instruccion.setString(35, usuarioEdicion);
            instruccion.execute(); 
            //Seteamos el exito de la transaccion 
            resultado = true;
             //Limpieza del ambiente        
            instruccion.close();
            conex.close(); 
        }
        catch (SQLException exSql){
        	System.out.println("ERROR actualiza beneFideicomiso"+DatosBeneficiario.class.getName()+ ".changeBeneFideicomiso()  "+exSql);
            DatosCrearLog L = new DatosCrearLog();
    		L.Log(Rutas.rutaCarga, b.getCliente().getCliente_Id(), "DatosBeneficiario line 142 : ", exSql.toString());
    		resultado = false; //Seteamos el fracaso de la transaccion
        } catch (Exception es){
        	System.out.println("ERROR no actualiza beneFideicomiso"+DatosBeneficiario.class.getName()+ ".changeBeneFideicomiso()  "+es);
            resultado = false; //Seteamos el fracaso de la transaccion 
        } finally{
        	try{
               if(conex != null)
                   conex.close();               
             }catch(SQLException es){
            	 System.out.println("ERROR cerrar conexión "+DatosBeneficiario.class.getName()+ ".changeBeneFideicomiso()  "+es);
             }//fin del cath         
           return resultado;
       }//fin del finally
    }//Fin del metodo insertar
    
    
    public boolean changeBeneMoral(BeneMoral b,String perfilid,String usuarioEdicion ){
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexion "+DatosBeneficiario.class.getName()+ ".changeBeneMoral() "+e);            
		}
        
        CallableStatement instruccion;
        boolean resultado = false;
        //rellenos
       ///// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       //// Date date = format.parse(fech);   
        
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        SimpleDateFormat formato;
        java.sql.Date sqlFechaNa = null;
        
        java.sql.Date sqlFechaConstitucion = null;      
        java.sql.Date sqlFechaNacimiento = sqlDate;
        String fechaZeros ="2001-01-01";
        formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNacimiento = b.getRLFechaNacimiento();
        String fechaConstitucion = b.getFechaconstitucion();
        
        try {
            instruccion = conex.prepareCall("{call usp_change_benefisicariomoral(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            instruccion.setString(1, b.getCliente().getCliente_Id());
            instruccion.setInt(2, b.getNrobene());
            instruccion.setString(3, b.getRazonsocial());
            instruccion.setString(4, b.getRfc());
            instruccion.setString(5, b.getPais().getPais());
            if ( b.getFechaconstitucion() != null && !b.getFechaconstitucion().isEmpty()){
                java.util.Date utilFechaConstitucion = formato.parse( fechaConstitucion );
            sqlFechaConstitucion = new java.sql.Date ( utilFechaConstitucion.getTime() );
               instruccion.setDate(6, sqlFechaConstitucion); 
            }else{
               java.util.Date utilFechaNa = formato.parse( fechaZeros );
                sqlFechaNa = new java.sql.Date ( utilFechaNa.getTime() );
                instruccion.setDate(6, sqlFechaNa );
            }
            instruccion.setString(7, b.getTepais().getPais());
            instruccion.setString(8, b.getTelefono());
            instruccion.setString(9, b.getTipodomi());
            instruccion.setString(10, b.getGiro().getGiro_id() );
            instruccion.setDate(11, sqlDate );
            instruccion.setString(12, b.getImagencedulafiscal() );
            instruccion.setString(13, b.getImagenactaconstitutiva());
            instruccion.setString(14, b.getImagencompdomicilio());
            instruccion.setString(15, b.getImagenpodernotarial());
            instruccion.setString(16, b.getImagenidrepresentantelegal());
            instruccion.setString(17, b.getEmail());
            instruccion.setString(18, b.getRlNombre());
            instruccion.setString(19, b.getRlApellidoPaterno());
            instruccion.setString(20, b.getRlApellidoMaterno());
            if ( b.getRLFechaNacimiento() != null && !b.getRLFechaNacimiento().isEmpty()){
                java.util.Date utilFechaNacimiento = formato.parse( fechaNacimiento );
                  sqlFechaNacimiento = new java.sql.Date ( utilFechaNacimiento.getTime() );
                instruccion.setDate(21, sqlFechaNacimiento );
            }else{
               java.util.Date utilFechaNa = formato.parse( fechaZeros );
                sqlFechaNa = new java.sql.Date ( utilFechaNa.getTime() );
                instruccion.setDate(21, sqlFechaNa );
            } 
            instruccion.setString(22, b.getRLRFC());
            instruccion.setString(23, b.getRLCURP());
            if ( b.getIdentifica_id().getIdentifica_id() != null && !b.getIdentifica_id().getIdentifica_id().isEmpty()){
                instruccion.setString(24, b.getIdentifica_id().getIdentifica_id() );
            } else {
                instruccion.setString(24, "14");
            }
            instruccion.setString(25, b.getRLAutoridadEmiteId() );
            instruccion.setString(26, b.getRLNumeroID());
            instruccion.setString(27, b.getRLIdentificacionTipo());
            instruccion.setString(28, b.getRlPais());
            instruccion.setString(29, b.getEstado_prov());
            instruccion.setString(30, b.getCiudad());
            instruccion.setString(31, b.getColonia());
            instruccion.setString(32, b.getCalle());
            instruccion.setString(33, b.getNumexterior());
            instruccion.setString(34, b.getNuminterior());
            instruccion.setString(35, b.getCodpostal());   
            instruccion.setInt(36, Integer.parseInt(perfilid));
            instruccion.setString(37, usuarioEdicion);
            instruccion.execute(); 
            //Seteamos el exito de la transaccion 
            resultado = true;
             //Limpieza del ambiente        
            instruccion.close();
            conex.close(); 
        } catch (SQLException exSql){
        	System.out.println("ERROR actualiza beneMoral sql "+DatosBeneficiario.class.getName()+ ".changeBeneMoral()  "+exSql);
            //Seteamos el fracaso de la transaccion 
            resultado = false;
        } catch (Exception es){
        	System.out.println("ERROR actualiza BeneMoral"+DatosBeneficiario.class.getName()+ ".changeBeneMoral()  "+es);
            //Seteamos el fracaso de la transaccion 
            resultado = false;
        } finally{
               try {
	               if(conex != null){
	                   conex.close();
	               }               
               }catch(SQLException es){
            	   System.out.println("ERROR cerrar Conexión "+DatosBeneficiario.class.getName()+ ".changeBeneMoral()  "+es);
               }//fin del cath         
           return resultado;
       }//fin del finally
    }//Fin del metodo insertar
    
    
    
    public boolean changeBeneFisica(BeneFisica b,String perfilid,String usuarioEdicion ){
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosBeneficiario.class.getName()+ ".changeBeneFisica() "+e);
		}
        CallableStatement instruccion;
        boolean resultado = false;
        
        //rellenos
                
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        java.sql.Date sqlFechaNacimiento = null;
        java.sql.Date sqlFechaNa = null;
        
        SimpleDateFormat formato;
        formato = new SimpleDateFormat("yyyy-MM-dd");        
        String fechaNacimiento = b.getFechanacimiento();
        String fechaZeros = "2001-01-01";
        
                      
        try {
            instruccion = conex.prepareCall("{call usp_change_beneficiariofisica(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            instruccion.setString(1, b.getCliente().getCliente_Id());
            instruccion.setInt(2, Integer.parseInt( b.getNrobene() ));
            instruccion.setString(3, b.getNombre());
            instruccion.setString(4, b.getApellidopaterno());
            instruccion.setString(5, b.getApellidomaterno());
            if ( b.getFechanacimiento() != null && !b.getFechanacimiento().isEmpty()){
                   java.util.Date utilFechaNacimiento = formato.parse( fechaNacimiento );
            sqlFechaNacimiento = new java.sql.Date ( utilFechaNacimiento.getTime() );
                instruccion.setDate(6, sqlFechaNacimiento);
            } else {
                 java.util.Date utilFechaNa = formato.parse( fechaZeros );
                sqlFechaNa = new java.sql.Date ( utilFechaNa.getTime() );
                instruccion.setDate(6, sqlFechaNa);
            }
            instruccion.setString(7, b.getRfc());
            if ( b.getPaisnacim().getPais() != null && !b.getPaisnacim().getPais().isEmpty()){
                instruccion.setString(8, b.getPaisnacim().getPais());
            } else {
                instruccion.setNull(8, java.sql.Types.NULL);
            }
            if ( b.getActividad().getActividad_Id() != null && !b.getActividad().getActividad_Id().isEmpty()){
                instruccion.setString(9, b.getActividad().getActividad_Id());
            } else {
                instruccion.setNull(9, java.sql.Types.NULL);
            }
            if ( b.getIdentifica_id().getIdentifica_id() != null && !b.getIdentifica_id().getIdentifica_id().isEmpty()){
                instruccion.setString(10, b.getIdentifica_id().getIdentifica_id() );
            } else {
                instruccion.setString(10, "14");
            }
            instruccion.setString(11, b.getIdentificaciontipo());
            instruccion.setString(12, b.getNumeroid());
            if ( b.getPaisnacio().getPais() != null && !b.getPaisnacio().getPais().isEmpty() ){
                instruccion.setString(13, b.getPaisnacio().getPais());
            } else {
                instruccion.setNull(13, java.sql.Types.NULL );
            }
            
            instruccion.setString(14, b.getCurp());
            if ( b.getTepais().getPais() != null && !b.getTepais().getPais().isEmpty()){
                instruccion.setString(15, b.getTepais().getPais());
            } else {
                instruccion.setNull(15, java.sql.Types.NULL);
            }
            
            instruccion.setString(16, b.getTelefono());
            instruccion.setString(17, b.getImagenId() );
            instruccion.setString(18, b.getImagencedulafiscal() );
            instruccion.setString(19, b.getAutoridadEmiteId() );
            instruccion.setString(20, b.getImagenCurp());
            instruccion.setString(21, b.getImagenCompDomicilio());
            instruccion.setString(22, b.getEmail());
            instruccion.setString(23, b.getPais());
            instruccion.setString(24, b.getEstado_prov());
            instruccion.setString(25, b.getCiudad());
            instruccion.setString(26, b.getColonia());
            instruccion.setString(27, b.getCalle());
            instruccion.setString(28, b.getNumexterior());
            instruccion.setString(29, b.getNuminterior());
            instruccion.setString(30, b.getCodpostal());
            instruccion.setInt(31, Integer.parseInt(perfilid));
            instruccion.setString(32, usuarioEdicion);
            instruccion.execute(); 
            
            //Seteamos el exito de la transaccion 
            resultado = true;
            
             //Limpieza del ambiente        
            instruccion.close();
            conex.close(); 
            
            
        }
        catch (SQLException exSql){
        	System.out.println("ERROR sql changeBeneFisica"+DatosBeneficiario.class.getName()+ ".changeBeneFisica() "+exSql);
            //Seteamos el fracaso de la transaccion 
            resultado = false;
        } catch (Exception es){
        	System.out.println("ERROR changeBeneFisica"+DatosBeneficiario.class.getName()+ ".changeBeneFisica() "+es);
            //Seteamos el fracaso de la transaccion 
            resultado = false;
        } finally{
               try{
	               if(conex != null){
	                   conex.close();
	               }
               }catch(SQLException es){
            	   System.out.println("ERROR cerrar conexión "+DatosBeneficiario.class.getName()+ ".changeBeneFisica() "+es);
               }//fin del cath         
           return resultado;
       }//fin del finally
    }//Fin del metodo insertar
    
//    public boolean changeDomilio(Domicilio d ){
//        Connection conex;
//        conex = cnn.getConnection("dbpld");
//        CallableStatement instruccion;
//        boolean resultado = false;
//        
//        //rellenos
//        java.util.Date utilDate = new java.util.Date();
//        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//        
//            
//        
//        
//        try {
//            instruccion = conex.prepareCall("{call usp_change_DomicilioBene(?,?,?,?,?,?,?,?,?,?,?)}");
//            instruccion.setString(1, d.getCliente().getCliente_Id());
//            instruccion.setString(2, d.getNrobene());
//            instruccion.setString(3, d.getPais().getPais());
//            instruccion.setString(4, d.getEstado_prov());
//            instruccion.setString(5, d.getCiudad());
//            instruccion.setString(6, d.getColonia());
//            instruccion.setString(7, d.getCalle());
//            instruccion.setString(8, d.getNumexterior());
//            instruccion.setString(9, d.getNuminterior());
//            instruccion.setString(10, d.getCodpostal());
//            instruccion.setDate(11, sqlDate);
//            
//            
//            instruccion.execute(); 
//            
//            //Seteamos el exito de la transaccion 
//            resultado = true;
//            
//             //Limpieza del ambiente        
//            instruccion.close();
//            conex.close(); 
//            
//            
//        }
//        catch (SQLException exSql){
//            exSql.printStackTrace();
//            
//            //Seteamos el fracaso de la transaccion 
//            resultado = false;
//
//        } catch (Exception es){
//           es.printStackTrace();
//            //Seteamos el fracaso de la transaccion 
//            resultado = false;
//        } finally{
//               try
//           {
//               if(conex != null){
//                   conex.close();
//               }
//               
//           }//fin del try
//           catch(SQLException es)
//           {
//               es.printStackTrace();
//           }//fin del cath         
//           return resultado;
//       }//fin del finally
//    }//Fin del metodo insertar
    
    
    public BeneFideicomiso getBeneFideicomiso ( String where) {
        Connection conex = null;
        Statement instruccion;
        boolean hayDatos = false;
        BeneFideicomiso b = null;
                 
        try{
        	conex = cnn.getConnection("dbpld");
        	// crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            String consulta = "SELECT cliente_id, nrobene, razonsocial, rfc, nrofideicomiso, tepais, telefono, tipodomi, fecharegistro, imagencedulafiscal,"
                            + " institucionfiduciaria, imagenactaconstitutiva, imagenpodernotarial, imagenidrepresentantelegal, rlnombre, rlapellidopaterno, rlapellidomaterno, "
                            + " rlfechanacimiento, rlrfc, rlcurp, rlidentifica_id, rlautoridademiteid, rlnumeroid, rlidentificaciontipo, email, pais, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal" +
                            "	FROM benefideicomiso";
                            
            if ( where != null  ){
            	if ( !where.isEmpty() ){
            		consulta += " WHERE " + where;
                }
             }
            
            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = true;

            while(conjuntoResultados.next()){                 
                String  cliente_id=conjuntoResultados.getString("cliente_id");
                Integer  nrobene=conjuntoResultados.getInt("nrobene");
                String  razonsocial=conjuntoResultados.getString("razonsocial").trim();
                String  rfc=conjuntoResultados.getString("rfc").trim();
                String  nrofideicomiso=conjuntoResultados.getString("nrofideicomiso").trim();
                String  tepais=conjuntoResultados.getString("tepais").trim();
                String  telefono=conjuntoResultados.getString("telefono").trim();
                String  tipodomi=conjuntoResultados.getString("tipodomi").trim();
                String  fecharegistro=conjuntoResultados.getString("fecharegistro").trim();
                String imagencedulafiscal = conjuntoResultados.getString("imagencedulafiscal").trim();
                String institucionfiduciaria = conjuntoResultados.getString("institucionfiduciaria").trim();
                String imagenactaconstitutiva = conjuntoResultados.getString("imagenactaconstitutiva").trim();
                String imagenpodernotarial = conjuntoResultados.getString("imagenpodernotarial").trim();
                String imagenidrepresentantelegal = conjuntoResultados.getString("imagenidrepresentantelegal").trim();
                String rlnombre = conjuntoResultados.getString("rlnombre").trim();
                String rlapellidopaterno = conjuntoResultados.getString("rlapellidopaterno").trim();
                String rlapellidomaterno = conjuntoResultados.getString("rlapellidomaterno").trim();
                String rlfechanacimiento = conjuntoResultados.getString("rlfechanacimiento").trim();
                String rlrfc = conjuntoResultados.getString("rlrfc").trim();
                String rlcurp = conjuntoResultados.getString("rlcurp").trim();
                String identifica_id = conjuntoResultados.getString("rlidentifica_id").trim();
                String rlautoridademiteid = conjuntoResultados.getString("rlautoridademiteid").trim();
                String rlnumeroid = conjuntoResultados.getString("rlnumeroid").trim();   
                String rlidentificaciontipo = conjuntoResultados.getString("rlidentificaciontipo").trim();
                String email = conjuntoResultados.getString("email").trim();
                String pais=conjuntoResultados.getString("pais").trim();
                String estado_prov=conjuntoResultados.getString("estado_prov").trim();
                String ciudad=conjuntoResultados.getString("ciudad").trim();
                String colonia=conjuntoResultados.getString("colonia").trim();
                String calle=conjuntoResultados.getString("calle").trim();
                String numexterior=conjuntoResultados.getString("numexterior").trim();
                String numinterior=conjuntoResultados.getString("numinterior").trim();
                String codpostal=conjuntoResultados.getString("codpostal").trim();
                
                
                Cliente c = new Cliente();
                c.setCliente_Id(cliente_id);
                TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id); 
                
                b = new BeneFideicomiso(c, nrobene, razonsocial, rfc, nrofideicomiso, tepais, telefono, tipodomi,
                        fecharegistro, imagencedulafiscal, institucionfiduciaria, imagenactaconstitutiva, imagenpodernotarial,
                        imagenidrepresentantelegal, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc,rlcurp,
                        identificacion, rlautoridademiteid, rlnumeroid, rlidentificaciontipo, email, pais, estado_prov,
                        ciudad, colonia, calle, numexterior, numinterior, codpostal);                               
                
            }//fin del while
            conjuntoResultados.close();
            instruccion.close();
            conex.close();
            
       }  catch(SQLException es){
    	   System.out.println("ERROR sql getBeneFideicomiso"+DatosBeneficiario.class.getName()+ ".getBeneFideicomiso() "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getBeneFideicomiso"+DatosBeneficiario.class.getName()+ ".getBeneFideicomiso() "+e);
       }finally{
           try{
               if(conex != null)
                   conex.close();
           	}catch(SQLException es){
           		System.out.println("ERROR cerrar conexión "+DatosBeneficiario.class.getName()+ ".getBeneFideicomiso() "+es);
            }
       }//fin del finally
       return b;
    } // fin del metodo get
    
    public BeneFisica getBeneFisica ( String where) {
        Connection conex = null;
        Statement instruccion;
        boolean hayDatos = false;
        BeneFisica b = null;
        
        try{
        	conex = cnn.getConnection("dbpld");
        	// crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            String consulta = "SELECT cliente_id, nrobene, nombre, apellidopaterno, apellidomaterno, fechanacimiento, rfc, paisnacim, actividad_id, identifica_id, identificaciontipo, autoridademiteid, numeroid, paisnacio, curp, tepais, telefono, fecharegistro,  imagenid, imagencedulafiscal, imagencurp, imagencompdomicilio, email, pais, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal ";
                   consulta += " FROM benefisica ";
             if ( where != null  ){
            	 if ( !where.isEmpty() ){
            		 consulta += " WHERE " + where;
                 }
             }
            
            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = true;
            while(conjuntoResultados.next()){ 
                String  cliente_id=conjuntoResultados.getString("cliente_id");
                String  nrobene=conjuntoResultados.getString("nrobene").trim();
                String  nombre=conjuntoResultados.getString("nombre").trim();
                String  apellidopaterno=conjuntoResultados.getString("apellidopaterno").trim();
                String  apellidomaterno=conjuntoResultados.getString("apellidomaterno").trim();
                String  fechanacimiento=conjuntoResultados.getString("fechanacimiento").trim();
                String  rfc=conjuntoResultados.getString("rfc").trim();
                String  paisnacim=conjuntoResultados.getString("paisnacim");
                String  actividad_id=conjuntoResultados.getString("actividad_id");
                String  identifica_id=conjuntoResultados.getString("identifica_id");
                String  identificaciontipo=conjuntoResultados.getString("identificaciontipo").trim();
                String  AutoridadEmiteId=conjuntoResultados.getString("autoridademiteid").trim();
                String  numeroid=conjuntoResultados.getString("numeroid").trim();
                String  paisnacio=conjuntoResultados.getString("paisnacio");
                String  curp=conjuntoResultados.getString("curp").trim();
                String  tepais=conjuntoResultados.getString("tepais");
                String  telefono=conjuntoResultados.getString("telefono").trim();
                String  fecharegistro=conjuntoResultados.getString("fecharegistro").trim();
                String  ImagenId =conjuntoResultados.getString("imagenid").trim();
                String  imagencedulafiscal =conjuntoResultados.getString("imagencedulafiscal").trim();
                String  imagencurp = conjuntoResultados.getString("imagencurp").trim();
                String  imagencompdomicilio = conjuntoResultados.getString("imagencompdomicilio").trim();
                String  email = conjuntoResultados.getString("email").trim();
                String pais=conjuntoResultados.getString("pais").trim();
                String estado_prov=conjuntoResultados.getString("estado_prov").trim();
                String ciudad=conjuntoResultados.getString("ciudad").trim();
                String colonia=conjuntoResultados.getString("colonia").trim();
                String calle=conjuntoResultados.getString("calle").trim();
                String numexterior=conjuntoResultados.getString("numexterior").trim();
                String numinterior=conjuntoResultados.getString("numinterior").trim();
                String codpostal=conjuntoResultados.getString("codpostal").trim();
                


                Cliente c = new Cliente();
                c.setCliente_Id(cliente_id);
                
                Pais paisnacimiento = new Pais(paisnacim);
                Actividad actividad = new Actividad(actividad_id);
                TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id); 
                Pais paisnacionalidad = new Pais(paisnacio);
                Pais paisTelefono = new Pais(tepais);
                
                b = new BeneFisica(c, nrobene, nombre, apellidopaterno, apellidomaterno, fechanacimiento, rfc, 
                        paisnacimiento, actividad, identificacion, identificaciontipo, AutoridadEmiteId,  
                        numeroid, paisnacionalidad, curp, paisTelefono, telefono,  fecharegistro,
                        ImagenId, imagencedulafiscal, imagencurp, imagencompdomicilio, email, pais, estado_prov,
                        ciudad, colonia, calle, numexterior, numinterior, codpostal);
                                
                
            }//fin del while

            conjuntoResultados.close();
            instruccion.close();
            conex.close();
       }catch(SQLException es){
    	   System.out.println("ERROR sql getBeneFisica "+DatosBeneficiario.class.getName()+ ".getBeneFisica() "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getBeneFisica "+DatosBeneficiario.class.getName()+ ".getBeneFisica() "+e);
       }finally{
           try{
               if(conex != null)
                   conex.close();
           }catch(SQLException es){
        	   System.out.println("ERROR cerrar conexión "+DatosBeneficiario.class.getName()+ ".getBeneFisica() "+es);
           }
       }//fin del finally
       return b;
    } // fin del metodo get
    
    
    
    public BeneMoral getBeneMoral ( String where) {
        Connection conex = null;
        Statement instruccion;
        boolean hayDatos = false;
        BeneMoral b = null;
                 
        try{
        	conex = cnn.getConnection("dbpld");
        	// crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            String consulta = "SELECT cliente_id, nrobene, razonsocial, rfc, pais, fechaconstitucion, tepais, telefono, tipodomi, giro_id, fecharegistro, imagencedulafiscal, imagenactaconstitutiva, imagencompdomicilio, imagenpodernotarial, imagenidrepresentantelegal, email, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, rlcurp, rlidentifica_id, rlautoridademiteid, rlnumeroid, rlidentificaciontipo, rlpais, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal " +
                            "	FROM benemoral " ;
            if ( where != null  ){
            	if ( !where.isEmpty() ){
            		consulta += " WHERE " + where;
            	}
            }
            
            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = true;
            while(conjuntoResultados.next()){                
                String  cliente_id=conjuntoResultados.getString("cliente_id");
                int  nrobene=conjuntoResultados.getInt("nrobene");
                String  razonsocial=conjuntoResultados.getString("razonsocial").trim();
                String  rfc=conjuntoResultados.getString("rfc").trim();
                String  pais=conjuntoResultados.getString("pais").trim();
                String  fechaconstitucion=conjuntoResultados.getString("fechaconstitucion").trim();
                String  tepais=conjuntoResultados.getString("tepais").trim();
                String  telefono=conjuntoResultados.getString("telefono").trim();
                String  tipodomi=conjuntoResultados.getString("tipodomi").trim();
                String  giro_id=conjuntoResultados.getString("giro_id").trim();
                String  fecharegistro=conjuntoResultados.getString("fecharegistro").trim();
                String imagencedulafiscal = conjuntoResultados.getString("imagencedulafiscal").trim();
                String imagenactaconstitutiva = conjuntoResultados.getString("imagenactaconstitutiva").trim();
                String imagencompdomicilio = conjuntoResultados.getString("imagencompdomicilio").trim();
                String imagenpodernotarial = conjuntoResultados.getString("imagenpodernotarial").trim();
                String imagenidrepresentantelegal = conjuntoResultados.getString("imagenidrepresentantelegal").trim();
                String email = conjuntoResultados.getString("email").trim();
                String rlnombre = conjuntoResultados.getString("rlnombre").trim();
                String rlapellidopaterno = conjuntoResultados.getString("rlapellidopaterno").trim();
                String rlapellidomaterno = conjuntoResultados.getString("rlapellidomaterno").trim();
                String rlfechanacimiento=conjuntoResultados.getString("rlfechanacimiento").trim();
                String rlrfc=conjuntoResultados.getString("rlrfc").trim();
                String rlcurp=conjuntoResultados.getString("rlcurp").trim();
                String identifica_id=conjuntoResultados.getString("rlidentifica_id").trim();
                String rlautoridademiteid=conjuntoResultados.getString("rlautoridademiteid").trim();
                String rlnumeroid=conjuntoResultados.getString("rlnumeroid").trim();
                String rlidentificaciontipo=conjuntoResultados.getString("rlidentificaciontipo").trim();
                String rlpais=conjuntoResultados.getString("rlpais").trim();
                String estado_prov=conjuntoResultados.getString("estado_prov").trim();
                String ciudad=conjuntoResultados.getString("ciudad").trim();
                String colonia=conjuntoResultados.getString("colonia").trim();
                String calle=conjuntoResultados.getString("calle").trim();
                String numexterior=conjuntoResultados.getString("numexterior").trim();
                String numinterior=conjuntoResultados.getString("numinterior").trim();
                String codpostal=conjuntoResultados.getString("codpostal").trim();

                
                Cliente c = new Cliente();
                c.setCliente_Id(cliente_id);
                
                Pais paisBene = new Pais(pais);
                Pais paieTelefono = new Pais(tepais);
                 TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id);
                Giro giro = new Giro(giro_id);             
                b = new BeneMoral(c, nrobene, razonsocial, rfc, paisBene, fechaconstitucion,
                        paieTelefono, telefono, tipodomi, giro, fecharegistro, imagencedulafiscal, 
                        imagenactaconstitutiva, imagencompdomicilio, imagenpodernotarial, 
                        imagenidrepresentantelegal, email, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, 
                        rlrfc, rlcurp, identificacion, rlautoridademiteid, rlnumeroid, rlidentificaciontipo, rlpais, estado_prov,
                        ciudad, colonia, calle, numexterior, numinterior, codpostal);
                
                
            }//fin del while
            
            conjuntoResultados.close();
            instruccion.close();
            conex.close();
       }catch(SQLException es){
    	   System.out.println("ERROR sql getBeneMoral "+DatosBeneficiario.class.getName()+ ".getBeneMoral() "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR  getBeneMoral "+DatosBeneficiario.class.getName()+ ".getBeneMoral() "+e);
       }finally{
           try{
               if(conex != null)
                   conex.close();
           }catch(SQLException es){
        	   System.out.println("ERROR cerrar conexión "+DatosBeneficiario.class.getName()+ ".getBeneMoral() "+es);
           }
       }//fin del finally
       return b;
    } // fin del metodo get
    
//    public Domicilio getBeneDomicilio ( String where) {
//        
//        Connection conex = null;
//        Statement instruccion;
//        boolean hayDatos = false;
//        Domicilio d = null;
//                 
//                
//
//                try
//       {
//
//            conex = cnn.getConnection("dbpld");
//
//              // crea objeto Statement para consultar la base de datos
//            instruccion = conex.createStatement( 
//            ResultSet.TYPE_SCROLL_INSENSITIVE,
//            ResultSet.CONCUR_READ_ONLY );
//
//            String consulta = "SELECT cliente_id, nrobene, pais, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal, fecharegistro " +
//                "	FROM tedomiexte ";
//                            
//            
//                                if ( where != null  ){
//                                    if ( !where.isEmpty() ){
//                                        consulta += " WHERE " + where;
//                                    }
//                                }
//            
//            conjuntoResultados = instruccion.executeQuery(consulta);
//            bandera = true;
//
//            while(conjuntoResultados.next()){ 
//                
//                String cliente_id=conjuntoResultados.getString("cliente_id");
//                String nrobene=conjuntoResultados.getString("nrobene").trim();
//                String pais=conjuntoResultados.getString("pais").trim();
//                String estado_prov=conjuntoResultados.getString("estado_prov").trim();
//                String ciudad=conjuntoResultados.getString("ciudad").trim();
//                String colonia=conjuntoResultados.getString("colonia").trim();
//                String calle=conjuntoResultados.getString("calle").trim();
//                String numexterior=conjuntoResultados.getString("numexterior").trim();
//                String numinterior=conjuntoResultados.getString("numinterior").trim();
//                String codpostal=conjuntoResultados.getString("codpostal").trim();
//                String fecharegistro=conjuntoResultados.getString("fecharegistro").trim();
//
//                
//                Cliente c = new Cliente();
//                c.setCliente_Id(cliente_id);
//                
//                Pais paisDomicilio = new Pais(pais);
//                
//                d = new Domicilio(c, paisDomicilio, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal, fecharegistro,  nrobene);
//                
//                                 
//
//                                
//                
//            }//fin del while
//
//            conjuntoResultados.close();
//            instruccion.close();
//            conex.close();
//       }
//       catch(SQLException es)
//       {
//           es.printStackTrace();
//           bandera  = false;
//       }//fin del catch
//       finally
//       {
//           try{
//               if(conex != null)
//                   conex.close();
//           }
//           catch(SQLException es)
//                   {
//                       es.printStackTrace();
//                   }
//
//
//       }//fin del finally
//
//       return d;
//    } // fin del metodo get
    
    public Integer getNumeroBeneficiario ( String idCliente) {        
        Connection conex = null;
        Statement instruccion;
        Integer nroBeneficiario = null;
                
        try{
        	conex = cnn.getConnection("dbpld");
        	// crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            String consulta = "select  max(nrobene)  as nrobene " +
                                "from  " +
                                "( " +
                                "	select nrobene from benefideicomiso where cliente_id ='" + idCliente + "' UNION" +
                                "	select nrobene from benefisica where cliente_id ='" + idCliente + "' UNION" +
                                "	select nrobene from benemoral where cliente_id ='" + idCliente + "'" +
                                ") as  b ";
            
            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = false;

            while(conjuntoResultados.next()){                 
                nroBeneficiario = conjuntoResultados.getInt("nrobene");
                bandera = true;                
            }//fin del while

            conjuntoResultados.close();
            instruccion.close();
            conex.close();
       } catch(SQLException es){
    	   System.out.println("ERROR sql getNumeroBeneficiario"+DatosBeneficiario.class.getName()+ ".getNumeroBeneficiario() "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getNumeroBeneficiario"+DatosBeneficiario.class.getName()+ ".getNumeroBeneficiario() "+e);
       }finally{
           try{
               if(conex != null)
                   conex.close();
           }catch(SQLException es){
        	   System.out.println("ERROR cerrarConexión "+DatosBeneficiario.class.getName()+ ".getNumeroBeneficiario() "+es);
           }
       }//fin del finally
       return nroBeneficiario;
    } // fin del metodo get
    
    
    public Beneficiario[] getBeneList ( String idCliente ) {
        Connection conex = null;
        Statement instruccion;
        Beneficiario[] listaBeneficiario = null;
        ArrayList lista = null;
        
        try {
        	conex = cnn.getConnection("dbpld");
        	// crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( 
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY );

            String consulta = 
                "SELECT cliente_id, nroBene, " +
                "COALESCE(nombre,'') || ' ' || COALESCE(apellidopaterno,'') || ' ' || COALESCE(apellidomaterno,'') as nombreCompleto," +
                "'Persona FÃ­sica' as tipoPersonaDesc, 'F' as tipoPersona " +
                "from benefisica where cliente_id = '" + idCliente + "' union " +
                "SELECT cliente_id, nroBene, " +
                "COALESCE(razonsocial, '') as nombreCompleto," +
                "'Persona Moral' as tipoPersonaDesc, 'M' as tipoPersona " +
                "from benemoral where cliente_id = '" + idCliente + "' union " +
                "SELECT cliente_id, nroBene, " +
                "COALESCE(razonsocial, '')  as nombreCompleto," +
                "'Fideicomiso' as tipoPersonaDesc, 'X' as tipoPersona " +
                "from benefideicomiso where cliente_id = '" + idCliente + "'";

            conjuntoResultados = instruccion.executeQuery(consulta);
            bandera = true;
            lista = new ArrayList();
                    
            while(conjuntoResultados.next()){ 
                String cliente_id = conjuntoResultados.getString("cliente_id");
                int nroBene = conjuntoResultados.getInt("nroBene");
                String nombreCompleto = conjuntoResultados.getString("nombreCompleto");
                String tipoPersonaDesc = conjuntoResultados.getString("tipoPersonaDesc");
                String tipoPersona = conjuntoResultados.getString("tipoPersona");
                lista.add(new Beneficiario(cliente_id, nroBene, nombreCompleto, tipoPersonaDesc, tipoPersona));
            }//fin del while

            conjuntoResultados.close();
            instruccion.close();
            conex.close();
            
            //Normalizando la lista 
            if ( lista != null ){
                listaBeneficiario = new Beneficiario[lista.size()];
                for ( int i = 0; i<listaBeneficiario.length; i++){
                    listaBeneficiario[i] = (Beneficiario) lista.get(i);
                }
            }
            
            
       } catch(SQLException es)      {
    	   System.out.println("ERROR sql getBeneList"+DatosBeneficiario.class.getName()+ " "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getBeneList"+DatosBeneficiario.class.getName()+ " "+e);
       }finally {
           try{
               if(conex != null)
                   conex.close();
           }catch(SQLException es){
        	   System.out.println("ERROR cerrarConexión getBeneList"+DatosBeneficiario.class.getName()+ " "+es);
           }
       }//fin del finally
       return listaBeneficiario;
    } // fin del metodo get
    
    
    public boolean limpiaBeneficiarios (String idCliente) {
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR Conexión limpiaBeneficiarios"+DatosBeneficiario.class.getName()+ " "+e1);
		}
        boolean resultado = false;
        String consulta = "";

        try {
            Statement instruccion = conex.createStatement();
            consulta = "DELETE FROM benefideicomiso where cliente_id ='" + idCliente + "'";
            instruccion.execute(consulta);    
            consulta = "DELETE FROM benefisica where cliente_id ='" + idCliente + "'";
            instruccion.execute(consulta);                
            consulta = "DELETE FROM benemoral where cliente_id ='" + idCliente + "'";
            instruccion.execute(consulta);
            instruccion.close();
            //Seteamos el exito de la transaccion 
            resultado = true;
        } catch (Exception e) {
        	System.out.println("ERROR limpiaBeneficiarios"+DatosBeneficiario.class.getName()+ " "+e);
        } finally {
            try {
                conex.close();         
            } catch (Exception ex) {
            	System.out.println("ERROR cerrarConexión limpiaBeneficiarios"+DatosBeneficiario.class.getName()+ " "+ex);
            }
        }
         //Limpieza del ambiente        
        return resultado;
    }
    
    /**
     * @param idCliente
     * @param RFC
     * @param tipo
     * @return true si el RFC existe en otra tabla de beneficiarios
     */
   
    public boolean RFCExist(String idCliente, String RFC, String tipo) {
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR Conexión RFCExist"+DatosBeneficiario.class.getName()+ " "+e1);
		}
        Statement instruccion;
        boolean resultado = false;
        String consulta = "";
        try {
            conex = cnn.getConnection("dbpld");

              // crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( 
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY );            
            if (tipo.compareTo("F") == 0) {
                consulta = "SELECT count(nrobene) as nrobene FROM benemoral WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "' and rfc<>''";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) {
                    if (conjuntoResultados.getInt("nrobene") == 1)
                        resultado = true;
                }
                consulta = "SELECT count(nrobene) as nrobene FROM benefideicomiso WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "' and rfc<>''";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) {
                    if (conjuntoResultados.getInt("nrobene") == 1)
                        resultado = true;
                }
            }
            if (tipo.compareTo("M") == 0) {
                consulta = "SELECT count(nrobene) as nrobene FROM benefisica WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "' and rfc<>''";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) {
                    if (conjuntoResultados.getInt("nrobene") == 1)
                        resultado = true;
                }
                consulta = "SELECT count(nrobene) as nrobene FROM benefideicomiso WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "' and rfc<>''";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) {
                    if (conjuntoResultados.getInt("nrobene") == 1)
                        resultado = true;
                }
            }            
            if (tipo.compareTo("X") == 0) {
                consulta = "SELECT count(nrobene) as nrobene FROM benemoral WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "' and rfc<>''";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) {
                    if (conjuntoResultados.getInt("nrobene") == 1)
                        resultado = true;
                }
                consulta = "SELECT count(nrobene) as nrobene FROM benefisica WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "' and rfc<>''";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) {
                    if (conjuntoResultados.getInt("nrobene") == 1)
                        resultado = true;
                }
            }            
        } catch (Exception e) {
        	System.out.println("ERROR RFCExist"+DatosBeneficiario.class.getName()+ " "+e);
        } finally {
            try {
                conex.close();         
            } catch (Exception ex) {
            	System.out.println("ERROR cerrar conexión RFCExist"+DatosBeneficiario.class.getName()+ " "+ex);
            }
        }
        return resultado;        
    }
            
    /**
     * @param idCliente
     * @param RFC
     * @param tipo
     * @return el numero de este beneficiario Ã³ 0 si es nuevo 
     */
    public int getelNoBene(String idCliente, String RFC, String tipo){
        Connection conex=null;
        try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR Conexión getelNoBene"+DatosBeneficiario.class.getName()+ " "+e1);
		}
        Statement instruccion;
        int resultado = 0;
        String consulta = "";
        try {
            conex = cnn.getConnection("dbpld");

              // crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement( 
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY );            
            if (tipo.compareTo("F") == 0) {
                consulta = "SELECT nrobene FROM benefisica WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "'";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) 
                    resultado = conjuntoResultados.getInt("nrobene");
            }
            if (tipo.compareTo("M") == 0) {
                consulta = "SELECT nrobene FROM benemoral WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "'";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) 
                    resultado = conjuntoResultados.getInt("nrobene");
            }            
            if (tipo.compareTo("X") == 0) {
                consulta = "SELECT nrobene FROM benefideicomiso WHERE cliente_id = '" + idCliente + "' AND rfc = '" + RFC + "'";
                conjuntoResultados = instruccion.executeQuery(consulta);            
                if (conjuntoResultados.next()) 
                    resultado = conjuntoResultados.getInt("nrobene");            }            
        } catch (Exception e) {
        	System.out.println("ERROR getelNoBene"+DatosBeneficiario.class.getName()+ " "+e);
        } finally {
            try {
                conex.close();         
            } catch (Exception ex) {
            	System.out.println("ERROR cerrar Conexión getelNoBene"+DatosBeneficiario.class.getName()+ " "+ex);
            }
        }
        return resultado;        
    }
}
