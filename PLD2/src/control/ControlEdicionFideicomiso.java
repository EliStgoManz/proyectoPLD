/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosClienteRaro;
import datosRatos.DatosDomicilio;
import datosRatos.DatosFideicomiso;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Fideicomiso;
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
 * @author Salvador Valenzuela Saro
 */
@WebServlet(name = "ControlEdicionFideicomiso", urlPatterns = {"/EdicionFideicomiso"})
public class ControlEdicionFideicomiso extends HttpServlet {

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
       
        if ( cliente_id != null ){ // Si se trata de una verificacion 
            Cliente idCliente = new Cliente();
            idCliente.setCliente_Id(cliente_id);
        }
        
        
         //OBTENIENDO LOS DATOS DEL CLIENTE
            String where = " cliente_id ='" + cliente_id + "'";
            Cliente c = new DatosClienteRaro().get(where);
            
            //obteniendo los datos del fideicomiso
            where = "";
            where = " cliente_id ='" + cliente_id + "'";
            Fideicomiso f = new DatosFideicomiso().get(where);
            
                         /** Obteniendo los datos del domicilio **/
            where ="" ;
            where = " cliente_id = '" + cliente_id + "'";
            Domicilio d = new DatosDomicilio().get(where);
            
            HttpSession sesion1 = request.getSession();
            String perfilito=(sesion1.getAttribute("perfilId").toString());
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
                	System.out.println("ControlEdicionFideicomiso.java request "+es.toString());
                    
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
            
            if ( f != null){
            
                // DEL FIDEICOMISO
                request.setAttribute("cliente_id",f.getCliente().getCliente_Id());
                request.setAttribute("razonsocial",f.getRazonsocial());
                request.setAttribute("rfc",f.getRfc());
                request.setAttribute("nrofideicomiso",f.getNrofideicomiso());
                request.setAttribute("rlnombre",f.getRlnombre());
                request.setAttribute("rlapellidopaterno",f.getRlapellidopaterno());
                request.setAttribute("rlapellidomaterno",f.getRlapellidomaterno());
                request.setAttribute("rlfechanacimiento",f.getRlfechanacimiento());
                request.setAttribute("rlrfc",f.getRlrfc());
                request.setAttribute("rlidentifica_id",f.getIdentificacion().getIdentifica_id());
                request.setAttribute("rlautoridademiteid",f.getRlautoridademiteid());
                request.setAttribute("rlnumeroid",f.getRlnumeroid());
                request.setAttribute("rlcurp",f.getRlcurp());
                request.setAttribute("rlidentificaciontipo",f.getRlidentificaciontipo());
                request.setAttribute("fecharegistro",f.getFecharegistro());
                request.setAttribute("imagenactaconstitutiva",f.getImagenactaconstitutiva());
                request.setAttribute("imagencedulafiscal",f.getImagencedulafiscal());
                request.setAttribute("imagenrlid",f.getImagenrlid());
                request.setAttribute("noescritura",f.getNoEscritura());
                request.setAttribute("notaria",f.getNotaria());
                request.setAttribute("fechaNotarial",f.getFechaNotarial());
                request.setAttribute("nopoder",f.getRlNoPoder() );
                request.setAttribute("rlnotaria", f.getRlNotaria());
                request.setAttribute("rlfechaNotarial",f.getRlFechaNotarial());
//                request.setAttribute("imagenrlcedulafiscal",f.getImagenrlcedulafiscal());
                request.setAttribute("imagenrlpodernotarial",f.getImagenrlpodernotarial());
                request.setAttribute("institucionfiduciaria",f.getInstitucionFiduciaria());
            
            }
            
            if ( d != null){
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
                request.getRequestDispatcher("/datos_fideicomiso.jsp?idCliente=" + cliente_id + "&rfc=" + vRFC.trim().replace("&", "%26")).forward(request, response);
                } else {
                request.setAttribute("esEdicion", "1");
                request.getRequestDispatcher("/verificacion_fideicomiso.jsp?idCliente=" + cliente_id + "&rfc=" + vRFC.trim().replace("&", "%26")).forward(request, response);
                }
            
            
            //Buscando el idNumerico en la base de informix
            String idNumerico ="";
            if ( f.getRfc()!= null && !f.getRfc().isEmpty()){
                
                try{
                    idNumerico = new DatosClienteRaro().getClienteExterno(f.getRfc());
                } catch(Exception es){
                    idNumerico = "-10003";
                    es.printStackTrace();
                }
System.out.println(" Informix : " + idNumerico);
				
				String isCliente = "";
				try {
					isCliente = new DatosClienteRaro().getClienteVar(f.getRfc());
				} catch (Exception es) {
					isCliente = "-10003";
					es.printStackTrace();
				}	
				System.out.println(" VarUsuario : " + isCliente);
				
				if(isCliente == null || isCliente.isEmpty() || isCliente=="-10003"){  // IF  CnsultaCLiente 
					
				
                
                if (idNumerico != null && !idNumerico.isEmpty()){
                    String sql = "update varusuariotransitorio set cliente_id = '" + idNumerico + "' where rfc = '" + f.getRfc()+ "'";
                    try{
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                    } catch( Exception es ){
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                    }
                    
                }
				}
            }//ifn if idnumerico
            

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
