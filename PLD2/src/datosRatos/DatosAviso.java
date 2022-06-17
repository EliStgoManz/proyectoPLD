/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Aviso;
import entidad.Pais;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

/**
 *
 * @author edson
 */
public class DatosAviso {
    private Conexion2 cnn = new Conexion2();
    private ResultSet usuarioTransitorio;
    private ResultSet cliente;
    private ResultSet persona; 
    private ResultSet domicilio; 
    boolean bandera;
    
    public String eliminarAcentos(String palabra){
    	palabra= palabra.replaceAll("Á", "A");
    	palabra= palabra.replaceAll("É", "E");
    	palabra= palabra.replaceAll("Í", "I");
    	palabra= palabra.replaceAll("Ó", "O");
    	palabra= palabra.replaceAll("Ú", "U");
    	palabra= palabra.replaceAll("á", "a");
    	palabra= palabra.replaceAll("é", "e");
    	palabra= palabra.replaceAll("í", "i");
    	palabra= palabra.replaceAll("ó", "o");
    	palabra= palabra.replaceAll("ú", "u");
    	palabra=palabra.replaceAll("Ü","U");
    	palabra=palabra.replaceAll("ü","u");
    	return palabra;
    }
//    public static void main(String[]args){
//    	DatosAviso e= new DatosAviso();
//    	System.out.println(e.eliminarAcentos("ÁÉÍÓÚáéíóú"));
//    }
    
    public Aviso getData ( int id_cliente){
        Connection conex = null;
        Statement instruccion;
        boolean hayDatos = false;
        Aviso aviso = null;
        
        conex = cnn.getConnection("dbpld");
        try{
            String Cliente_Id = null;
            String TipoPersona = null;
            String TipoDomicilio = null;
            String numero_fideicomiso = null;
            String denominacion_razon = null;
            String fecha_constitucion = null;
            String rfc = null;
            String pais = null;
            String giro_mercantil = null;
            String nombre = null;
            String apellido_paterno = null;
            String apellido_materno = null;
            String fecha_nacimiento = null; 
            String rfc_poderado = null;
            String curp = null;
            String actividad_economica = null;
            String colonia = null;
            String calle = null;
            String numero_exterior = null;
            String numero_interior = null;
            String codigo_postal = null;
            String pais_dom = null;
            String estado = null;
            String ciudad = null;
            
            // crea objeto Statement para consultar la base de datos
            instruccion = conex.createStatement();
            String consulta_usuario_transitorio = "SELECT *  from varusuariotransitorio as us \n" + "where us.cliente_id = '"+id_cliente+"'; ";
            usuarioTransitorio = instruccion.executeQuery(consulta_usuario_transitorio);
            boolean val = usuarioTransitorio.next();
            if(val != false){
                System.out.println("------------------------>ENtro<------------------------------------");
                while(val){
                    Cliente_Id = usuarioTransitorio.getString("idcliente");
                    val = usuarioTransitorio.next();
                }
                String consulta_cliente = "SELECT * FROM avcliente as ac \n" +
                                  "where ac.cliente_id = '"+Cliente_Id+"' AND (estado = 'V' OR estado = 'A' OR estado = 'R'); ";

                cliente = instruccion.executeQuery(consulta_cliente);
                if (cliente.next()){
                    TipoPersona = cliente.getString("tipopersona");
                    TipoDomicilio = cliente.getString("tipodomicilio");

                    aviso = new Aviso();
                    String consulta_persona;
                    switch (TipoPersona) {
                        case "F":
                            //consulta_persona = "SELECT * from avpersonafisica  where cliente_id = '"+Cliente_Id+"'; ";
                            consulta_persona = "SELECT nombre,"+//translate(nombre, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as nombre, " +
                                  //  " translate(apellidopaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as apellidopaterno, " +
                            		"apellidopaterno,"+
                                    //" translate(apellidomaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as apellidomaterno, fechanacimiento, rfc, paisnacim, " +
                                  "apellidomaterno,fechanacimiento,rfc,paisnacim,"+
                                    " actividad_id,"+
                                  //translate(curp, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as curp " +
                                  "curp"+ 
                                  " FROM avpersonafisica where cliente_id = '" + Cliente_Id + "';";
                            persona = instruccion.executeQuery(consulta_persona);
                            while(persona.next()){
                                nombre = eliminarAcentos(persona.getString("nombre"));
                                apellido_paterno = eliminarAcentos(persona.getString("apellidopaterno"));
                                apellido_materno = eliminarAcentos(persona.getString("apellidomaterno"));
                                fecha_nacimiento = eliminarAcentos(persona.getString("fechanacimiento"));
                                rfc = eliminarAcentos(persona.getString("rfc"));
                                pais = eliminarAcentos(persona.getString("paisnacim"));
                                actividad_economica = eliminarAcentos(persona.getString("actividad_id"));
                                curp = eliminarAcentos(persona.getString("curp"));
                            }
                            break;

                        case "M":
                            //consulta_persona = "SELECT * from avpersonamoral  where cliente_id = '"+Cliente_Id+"'; ";
                            consulta_persona = "SELECT razonsocial,fechaconstitucion, rfc, pais, giro_id,"+
                            //translate(razonsocial, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as razonsocial, fechaconstitucion, rfc, pais, giro_id, " +
                                   // " translate(rlnombre, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlnombre, " +
                                   "rlnombre,"+ 
                                   //" translate(rlapellidopaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidopaterno, " +                                    
                                   "rlapellidopaterno,"+ 
                                 //  " translate(rlapellidomaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidomaterno, rlfechanacimiento, rlrfc, " +
                                  "rlapellidomaterno,rlfechanacimiento,rlrfc,"+  
                                // " translate(rlcurp, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlcurp " +
                                  "rlcurp"+  
                                    " FROM avpersonamoral where cliente_id = '" + Cliente_Id + "';";                            
                            persona = instruccion.executeQuery(consulta_persona);
                            while(persona.next()){
                                denominacion_razon = eliminarAcentos(persona.getString("razonsocial"));
                                fecha_constitucion = eliminarAcentos(persona.getString("fechaconstitucion"));
                                rfc = eliminarAcentos(persona.getString("rfc"));
                                pais = eliminarAcentos(persona.getString("pais"));
                                giro_mercantil = eliminarAcentos(persona.getString("giro_id"));
                                nombre = eliminarAcentos(persona.getString("rlnombre"));
                                apellido_paterno = eliminarAcentos(persona.getString("rlapellidopaterno"));
                                apellido_materno = eliminarAcentos(persona.getString("rlapellidomaterno"));
                                fecha_nacimiento = eliminarAcentos(persona.getString("rlfechanacimiento"));
                                rfc_poderado =eliminarAcentos( persona.getString("rlrfc"));
                                curp = eliminarAcentos(persona.getString("rlcurp"));
                            }
                            aviso.setFecha_constitucion(fecha_constitucion);
                            break;

                        case "X":
                            //consulta_persona = "SELECT * from avfideicomiso where cliente_id = '"+Cliente_Id+"'; ";
                            consulta_persona = "SELECT razonsocial,fecharegistro, nrofideicomiso, rfc,"+ //translate(razonsocial, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as razonsocial, fecharegistro, nrofideicomiso, rfc, " +
                                    //" translate(rlnombre, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlnombre, " +
                                    "rlnombre,"+ 
                                    "rlapellidopaterno,"+ 
                                    //  " translate(rlapellidomaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidomaterno, rlfechanacimiento, rlrfc, " +
                                     "rlapellidomaterno,rlfechanacimiento,rlrfc,"+  
                                   // " translate(rlcurp, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlcurp " +
                                     "rlcurp"+  
//                                    " translate(rlapellidopaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidopaterno, " +                                    
//                                    " translate(rlapellidomaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidomaterno, rlfechanacimiento, rlrfc, " +
//                                    " translate(rlcurp, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlcurp " +
                                    " FROM avfideicomiso where cliente_id = '" + Cliente_Id + "';";                            
                            persona = instruccion.executeQuery(consulta_persona);
                            while(persona.next()){
                                denominacion_razon = eliminarAcentos(persona.getString("razonsocial"));
                                fecha_constitucion = eliminarAcentos(persona.getString("fecharegistro"));
                                numero_fideicomiso = eliminarAcentos(persona.getString("nrofideicomiso"));
                                rfc = eliminarAcentos(persona.getString("rfc"));
                                nombre = eliminarAcentos(persona.getString("rlnombre"));
                                apellido_paterno = eliminarAcentos(persona.getString("rlapellidopaterno"));
                                apellido_materno = eliminarAcentos(persona.getString("rlapellidomaterno"));
                                fecha_nacimiento = eliminarAcentos(persona.getString("rlfechanacimiento"));
                                rfc_poderado = eliminarAcentos(persona.getString("rlrfc"));
                                curp = eliminarAcentos(persona.getString("rlcurp"));
                            }
                            aviso.setFecha_constitucion(fecha_constitucion);
                            break;

                        case "G":
                            //consulta_persona = "SELECT * from avgobierno where cliente_id = '"+Cliente_Id+"'; ";
                            consulta_persona = "SELECT razonsocial, fechacreacion, rfc, pais, giro_id,"+//translate(razonsocial, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as razonsocial, fechacreacion, rfc, pais, giro_id, " +
//                                    " translate(rlnombre, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlnombre, " +
//                                    " translate(rlapellidopaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidopaterno, " +                                    
//                                    " translate(rlapellidomaterno, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlapellidomaterno, rlfechanacimiento, rlrfc, " +
//                                    " translate(rlcurp, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as rlcurp " +
								"rlnombre,"+ 
								"rlapellidopaterno,"+
								"rlapellidomaterno,rlfechanacimiento,rlrfc,"+ 
								"rlcurp"+  
							" FROM avgobierno where cliente_id = '" + Cliente_Id + "';";                                                        
                            persona = instruccion.executeQuery(consulta_persona);
                            while(persona.next()){
                                denominacion_razon = eliminarAcentos(persona.getString("razonsocial"));
                                fecha_constitucion = eliminarAcentos(persona.getString("fechacreacion"));
                                rfc = eliminarAcentos(persona.getString("rfc"));
                                pais = eliminarAcentos(persona.getString("pais"));
                                giro_mercantil = eliminarAcentos(persona.getString("giro_id"));
                                nombre = eliminarAcentos(persona.getString("rlnombre"));
                                apellido_paterno = eliminarAcentos(persona.getString("rlapellidopaterno"));
                                apellido_materno = eliminarAcentos(persona.getString("rlapellidomaterno"));
                                fecha_nacimiento = eliminarAcentos(persona.getString("rlfechanacimiento"));
                                rfc_poderado = eliminarAcentos(persona.getString("rlrfc"));
                                curp = eliminarAcentos(persona.getString("rlcurp"));
                            }
                            aviso.setFecha_constitucion(fecha_constitucion);
                            break;
                    }
                    System.out.println("------------------------>Termino Persona y Inicio Domicilio en Id:"+Cliente_Id+"<------------------------------------");
                    //String consulta_domicilio = "SELECT *  from avdomicilioext where cliente_id = '"+Cliente_Id+"'; ";
                    String consulta_domicilio = "SELECT colonia,calle,numexterior,numinterior,codpostal,estado_prov,ciudad,pais "+//translate(colonia, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as colonia, " +
//                            " translate(calle, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as calle, " +
//                            " translate(numexterior, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as numexterior, numinterior, codpostal, pais, " +
//                            " translate(estado_prov, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as estado_prov, " +                                    
//                            " translate(ciudad, 'Ã¡,Ã©,Ã­,Ã³,Ãº,Ã�,Ã‰,Ã�,Ã“,Ãš,Ã±,Ã‘,Ã¼,Ãœ,'',Â´,Â°', 'a,e,i,o,u,A,E,I,O,U,n,N,u,U, , , ') as ciudad" +
                            " FROM avdomicilioext where cliente_id = '" + Cliente_Id + "';";                                                                            
                    domicilio = instruccion.executeQuery(consulta_domicilio);
                    while(domicilio.next()){
                        colonia = eliminarAcentos(domicilio.getString("colonia").trim());
                        calle = eliminarAcentos(domicilio.getString("calle").trim());
                        numero_exterior = eliminarAcentos(domicilio.getString("numexterior").trim());
                        numero_interior = eliminarAcentos(domicilio.getString("numinterior").trim());
                        codigo_postal = eliminarAcentos(domicilio.getString("codpostal"));
                        pais_dom = eliminarAcentos(domicilio.getString("pais"));
                        estado = eliminarAcentos(domicilio.getString("estado_prov"));
                        ciudad = eliminarAcentos(domicilio.getString("ciudad"));

                    }
                    System.out.println("------------------------>Termino Domicilio en Id:"+Cliente_Id+"<------------------------------------");
                    aviso.setCliente_Id(Cliente_Id);
                    aviso.setTipoPersona(TipoPersona);
                    aviso.setTipoDomicilio(TipoDomicilio);
                    aviso.setDenominacion_razon(denominacion_razon);
                    aviso.setNumero_fideicomiso(numero_fideicomiso);
                    aviso.setGiro_mercantil(giro_mercantil);
                    aviso.setNombre(nombre);
                    aviso.setApellido_paterno(apellido_paterno);
                    aviso.setApellido_materno(apellido_materno);
                    aviso.setFecha_nacimiento(fecha_nacimiento);
                    aviso.setRfc(rfc);
                    aviso.setRfc_poderado(rfc_poderado);
                    aviso.setPais(pais);
                    aviso.setActividad_economica(actividad_economica);
                    aviso.setCurp(curp);
                    aviso.setPais_dom(pais_dom);
                    aviso.setColonia(colonia);
                    aviso.setCalle(calle);
                    aviso.setNumero_exterior(numero_exterior);
                    aviso.setNumero_interior(numero_interior);
                    aviso.setCodigo_postal(codigo_postal);
                    aviso.setEstado(estado);
                    aviso.setCiudad(ciudad);
                    hayDatos = true;
                    aviso.sethayDatos(hayDatos);
                    System.out.println("nombre: "+apellido_paterno);
                    usuarioTransitorio.close();
                    persona.close();
                    cliente.close();
                    domicilio.close();
                    instruccion.close();
                    conex.close();
                    System.out.println("------------------------>Termino Aviso en Id:"+Cliente_Id+"<------------------------------------");
                } else {
                    aviso = new Aviso();
                    hayDatos = false;
                    aviso.sethayDatos(hayDatos);                    
                }
            }else {
                aviso = new Aviso();
                hayDatos = false;
                aviso.sethayDatos(hayDatos);
            }
       }
       catch(SQLException es)       {
    	   System.out.println("ERROR consulta getDatos"+DatosAviso.class.getName()+ ".getData() "+es);
           bandera  = false;
       }catch (Exception e) {
    	   System.out.println("ERROR getDatos"+DatosAviso.class.getName()+ ".getData() "+e);
	   }finally{
            try{
               if(conex != null)
                   conex.close();
            }catch(SQLException es){
            	System.out.println("ERROR cerrar conexión "+DatosAviso.class.getName()+ ".getData() "+es);
            }
	   }//fin del finally
        System.out.println("------------------------>Salio<------------------------------------");
       return aviso;
    }
}
