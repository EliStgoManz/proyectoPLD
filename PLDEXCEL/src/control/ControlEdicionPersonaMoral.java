/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosClienteRaro;
import datosRatos.DatosDomicilio;
import datosRatos.DatosPersonaMoral;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.PersonaMoral;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import utilidades.Mensajes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Israel Osiris Garcï¿½a
 */
@WebServlet(name = "ControlEdicionPersonaMoral", urlPatterns = {"/EdicionPersonaMoral"})
public class ControlEdicionPersonaMoral extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
            boolean isVerificacion = true;
            String cliente_id = request.getParameter("idCliente");
            String vRFC = request.getParameter("rfc"); 
            if (request.getParameter("ver") != null && request.getParameter("ver").compareTo("no") == 0)
                isVerificacion = false;            
       
            if ( cliente_id != null ){ // Si se trata de una verificaciï¿½n 
                Cliente idCliente = new Cliente();
                idCliente.setCliente_Id(cliente_id);
            }
        
        
            //OBTENIENDO LOS DATOS DEL CLIENTE
            String where = " cliente_id = '" + cliente_id + "'";
            Cliente c = new DatosClienteRaro().get(where);
            
            //OBTENIENDO LOS DATOS DE LA PERSONA MORAL
            /** Obteniendo los datos de la persona fï¿½sica **/
            where ="";
            where = " cliente_id ='" + cliente_id + "'";
            System.out.println("Cliente : "+where);
            PersonaMoral pm = new DatosPersonaMoral().get(where);
            
            
             /** Obteniendo los datos del domicilio **/
            where ="" ;
            where = " cliente_id = '" + cliente_id + "'";
            Domicilio d = new DatosDomicilio().get(where);
            
            HttpSession sesion1 = request.getSession();
            String perfilito=(sesion1.getAttribute("perfilId").toString());

            System.out.println("PERFIL : "+perfilito);
            String usuario="";
            if ( request.getSession().getAttribute("usuario") != null){
                try{

                    if ( request.getSession().getAttribute("tipo") != null &&  request.getSession().getAttribute("tipo")  == "T"){
                        UsuarioTransitivo ut = (UsuarioTransitivo)request.getSession().getAttribute("usuario") ;
                       usuario = ut.getCliente().getCliente_Id();
                        
                    } else {
                        ////UsuarioSistema us = (UsuarioSistema)request.getSession().getAttribute("usuario") ;
                        UsuarioSistema us = (UsuarioSistema)request.getSession().getAttribute("usuario") ;
                       usuario = us.getUsuario();
                       
                    }
                } catch (Exception es ){
                	System.out.println("ControlEdicionPersonaMoral.java request "+es.toString());
                    
                    es.printStackTrace();
                }
            }
            
            
         // Candado para el registro de usuario con otra sesion
            System.out.println("Usuario: " + usuario);
            System.out.println("Cliente_id: " + cliente_id);
            
            if (usuario.equals(cliente_id) == false && perfilito.equals("6") ){ 	
//            	
            	System.out.println("Entrorrrr");
            	
           	 Mensajes.mensaje =  "El Usuario modificado no coincide con el usuario de la Sesión, \n " ;
           	 Mensajes.mensaje += "Error de sesión, vuelva a intentarlo.";
           	request.setAttribute("resultado", Mensajes.mensaje);
            request.setAttribute("exito", "1");
            request.getRequestDispatcher("/mensajes_login.jsp").forward(request, response);
            
            
            }else{
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if ( c != null){
                // ENVIANDO LOS DATOS DEL CLIENTE
                request.setAttribute("cliente_id",c.getCliente_Id());
                request.setAttribute("tipopersona",c.getTipoPersona());
                request.setAttribute("fecharegistro",c.getFechaRegistro());
                request.setAttribute("tipodomicilio",c.getTipoDomicilio());
                request.setAttribute("telefono",c.getTelefono());
                request.setAttribute("pais",c.getPais().getPais());
                request.setAttribute("email",c.geteMail());
                request.setAttribute("estado",c.getEstado());
                request.setAttribute("razonsocial",c.getRazonSocial());
                request.setAttribute("validado",c.getValidado());
                request.setAttribute("fechavalidado",c.getFechaValidado());
                request.setAttribute("declarobeneficiario",c.getDeclaroBeneficiario());
                request.setAttribute("declaroorigen",c.getDeclaroOrigen());
                request.setAttribute("usuariovalido",c.getUsuarioValido());
                request.setAttribute("fechacorte",c.getFechaCorte());
                request.setAttribute("mensaje",c.getMensaje().trim());
                request.setAttribute("usuarioasignado",c.getUsuarioAsignado());
                request.setAttribute("bloqueado",c.isBloqueado());
                request.setAttribute("fechabloqueo",c.getFechaBloqueo());
                request.setAttribute("borrado",c.isBorrado());
                request.setAttribute("notas",c.getNotas().trim());
                request.setAttribute("riesgo",c.getRiesgo());
            }
            
            if( pm != null){
            
            //DE LA PERSONAM MORAL
            
            request.setAttribute("razonsocial",pm.getRazonSocial());
            request.setAttribute("rfc",pm.getRFC());
            request.setAttribute("fechaconstitucion",pm.getFechaConstitucion());
            request.setAttribute("noescritura",pm.getNoEscritura());
            request.setAttribute("notaria",pm.getNotaria());
        
            request.setAttribute("fechaNotarial",pm.getFechaNotarial());
            System.out.println("***************la fechanotaria a mostrar es "+pm.getFechaNotarial());
            request.setAttribute("nopoder",pm.getRlNoPoder() );
            request.setAttribute("rlnotaria", pm.getRlNotaria());
            request.setAttribute("rlfechaNotarial",pm.getRlFechaNotarial());
            System.out.println("***************la rlfechanotaria a mostrar es "+pm.getFechaNotarial());
            request.setAttribute("paisEmpresa",pm.getPais().getPais());
            request.setAttribute("rlnombre",pm.getRLNombre());
            request.setAttribute("rlapellidopaterno",pm.getRLApellidoPaterno());
            request.setAttribute("rlapellidomaterno",pm.getRLApellidoMaterno());
            request.setAttribute("rlfechanacimiento",pm.getRLFechaNacimiento());
            request.setAttribute("rlrfc",pm.getRLRFC());
            request.setAttribute("identifica_id",pm.getIdentificacion().getIdentifica_id());
            request.setAttribute("rlautoridademiteid",pm.getRLAutoridadEmiteId());
            request.setAttribute("rlnumeroid",pm.getRLNumeroId());
            request.setAttribute("rlcurp",pm.getRLCURP());
            request.setAttribute("rlidentificaciontipo",pm.getRLIdentificacionTipo());
            request.setAttribute("giro_id",pm.getGiro().getGiro_id());
            request.setAttribute("fecharegistro",pm.getFechaRegistro());
            request.setAttribute("imagenactaconstitutiva",pm.getImagenActaConstitutiva());
            request.setAttribute("imagencedulafiscal",pm.getImagenCedulaFiscal());
            request.setAttribute("imagenrlid",pm.getImagenRLId());
            request.setAttribute("imagenrlpodernotarial",pm.getImagenRLPoderNotarial());
            request.setAttribute("imagendeclaratoria",pm.getImagenDeclaratoria());
            }
            
            if ( d != null ){
            
            //DEL DOMICILIO
            request.setAttribute("paisDomicilio",d.getPais().getPais());
            request.setAttribute("estado_prov",d.getEstado_prov());
            request.setAttribute("ciudad",d.getCiudad());
            request.setAttribute("colonia",d.getColonia());
            request.setAttribute("calle",d.getCalle());
            request.setAttribute("numexterior",d.getNumexterior());
            request.setAttribute("numinterior",d.getNuminterior());
            request.setAttribute("codpostal",d.getCodpostal());
            request.setAttribute("fecharegistro",d.getFecharegistro());
            request.setAttribute("imagencomprobantedom",d.getImagencomprobantedom());
            request.setAttribute("delegacion", d.getDelegacionMunicipio());
            }
            
            
            if ( !isVerificacion ){
                request.setAttribute("esEdicion", "1");
                request.getRequestDispatcher("/datos_persona_moral.jsp?idCliente=" + cliente_id + "&rfc=" + vRFC.trim().replace("&", "%26")).forward(request, response);
            } else {
                request.setAttribute("esEdicion", "1");
                request.getRequestDispatcher("/verificacion_persona_moral.jsp?idCliente=" + cliente_id + "&rfc=" + vRFC.trim().replace("&", "%26")).forward(request, response);
            }
            
            
            //Buscando el idNumerico en la base de informix
            String idNumerico ="";
            
            String RFC = request.getParameter("rfc");
            if ( RFC != null && !RFC.isEmpty()){
                try{
                    idNumerico = new DatosClienteRaro().getClienteExterno(pm.getRFC());
                } catch(Exception es){
                    idNumerico = "-10003";
                }
System.out.println(" Informix : " + idNumerico);
				
				String isCliente = "";
				try {
					isCliente = new DatosClienteRaro().getClienteVar(RFC);
				} catch (Exception es) {
					isCliente = "-10003";
					es.printStackTrace();
				}	
				System.out.println(" VarUsuario : " + isCliente);
				
				if(isCliente == null || isCliente.isEmpty() || isCliente=="-10003"){  // IF  CnsultaCLiente 
					
				
                if (idNumerico != null && !idNumerico.isEmpty()){
                    String sql = "update varusuariotransitorio set cliente_id = '" + idNumerico + "' where rfc = '" + RFC+ "'";
                    try{
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                    } catch( Exception es ){
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                    }
                    
                }
				}
            }//ifn if idnumÃ©rico
            
        }
            }//candado sesion
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
