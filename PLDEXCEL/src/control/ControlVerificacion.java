/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosClienteRaro;
import datosRatos.DatosDomicilio;
import datosRatos.DatosPersonaFisica;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.PersonaFisica;
import entidad.UsuarioTransitivo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Israel Osiris Garcia
 */
@WebServlet(name = "ControlVerificacion", urlPatterns = {"/Verificacion"})
public class ControlVerificacion extends HttpServlet {

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
        
        String cliente_id = request.getParameter("perfil");
        /**
         * obteniedo los datos del Cliente
         */
        String where = " cliente_id ='" + UsuarioTransitivo.getCliente().getCliente_Id().trim() + "'";
        Cliente c = new DatosClienteRaro().get(where);
        
        
        /** Obteniendo los datos de la persona fisica **/
        where ="";
        where = " cliente_id ='" + UsuarioTransitivo.getCliente().getCliente_Id().trim() + "'";
        PersonaFisica pf = new DatosPersonaFisica().get(where);
        
        
        /** Obteniendo los datos del domicilio **/
        where ="" ;
        where = " cliente_id = '" + UsuarioTransitivo.getCliente().getCliente_Id().trim() + "'";
        Domicilio d = new DatosDomicilio().get(where);
        
        
        try (PrintWriter out = response.getWriter()) {
             //RECUPERANDO LOS DATOS DEL CLIENTE
            if ( c != null){
                request.setAttribute("cliente_id",c.getCliente_Id());
                request.setAttribute("tipopersona",c.getTipoPersona());
                request.setAttribute("fecharegistro",c.getFechaRegistro());
                request.setAttribute("tipodomicilio",c.getTipoDomicilio());
                request.setAttribute("telefono",c.getTelefono());
                request.setAttribute("pais",c.getPais().getPais());
                request.setAttribute("email",c.geteMail()); UsuarioTransitivo.setEmail(c.geteMail());
                request.setAttribute("estado",c.getEstado());
                request.setAttribute("razonsocial",c.getRazonSocial());
                request.setAttribute("validado",c.getValidado());
                request.setAttribute("fechavalidado",c.getFechaValidado());
                request.setAttribute("declarobeneficiario",c.getDeclaroBeneficiario());
                request.setAttribute("declaroorigen",c.getDeclaroOrigen());
                request.setAttribute("usuariovalido",c.getUsuarioValido());
                request.setAttribute("fechacorte",c.getFechaCorte());
                request.setAttribute("mensaje",c.getMensaje());
                request.setAttribute("usuarioasignado",c.getUsuarioAsignado());
                request.setAttribute("bloqueado",c.isBloqueado());
                request.setAttribute("fechabloqueo",c.getFechaBloqueo());
                request.setAttribute("borrado",c.isBorrado());
                request.setAttribute("notas",c.getNotas());        
            }
            
            if ( pf != null ){
                //  DE LA PERSONA FiSICA
                request.setAttribute("nombre",pf.getNombre());
                request.setAttribute("apellidopaterno",pf.getApellidoPaterno());
                request.setAttribute("apellidomaterno",pf.getApellidoMaterno());
                request.setAttribute("fechanacimiento",pf.getFechaNacimiento());
                request.setAttribute("rfc",pf.getRFC());
                request.setAttribute("paisnacim",pf.getPaisnacimiento().getPais());
                request.setAttribute("actividad_id",pf.getActividad().getActividad_Id());
                request.setAttribute("identifica_id",pf.getIdentificacion().getIdentifica_id());
                request.setAttribute("identificaciontipo",pf.getIdentificacionTipo() ) ;
                request.setAttribute("numeroid",pf.getNumeroId());
                request.setAttribute("autoridademiteid",pf.getAutoridadEmiteId());
                request.setAttribute("curp",pf.getCURP());
                request.setAttribute("paisnacio",pf.getPaisnacionalidad().getPais());
                request.setAttribute("fecharegistro",pf.getFechaRegistro());
                request.setAttribute("imagenid",pf.getImagenId());
                request.setAttribute("imagencedulafiscal",pf.getImagenCedulaFiscal());
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
      
            }
            
            
            request.setAttribute("esEdicion", "1");
            request.getRequestDispatcher("/datos_persona_fisica.jsp").forward(request, response);
        }
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
