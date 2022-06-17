/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.sun.jersey.core.util.Base64;
import datosRatos.DatosBeneficiario;
import datosRatos.DatosRepLegal;
import entidad.Actividad;
import entidad.BeneFideicomiso;
import entidad.BeneFisica;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Pais;
import entidad.RepLegal;
import entidad.TipoIdentificacion;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import utilidades.Cadenas;
import utilidades.Fecha;
import utilidades.Mensajes;
import utilidades.Rutas;

/**
 *
 * @author Aldo Ulises
 */


/**
 *
 * @author Israel Osiris García
 */
@WebServlet(name = "ControlAltaRepresentantes", urlPatterns = {"/alta_representante"})
public class ControlAltaRepresentante extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest requestMulti, HttpServletResponse response)
            throws ServletException, IOException, UploadException {
//        response.setContentType("text/html;charset=UTF-8");
        
        String imagenRLID = "";
        String imagenRLCedulaFiscal = "";
        String imagenRLPoderNotarial = "";
        
        
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = new MultipartFormDataRequest(requestMulti);
            if (new DatosRepLegal().NOMExist(request.getParameter("bIdCliente"), request.getParameter("bnombre"),request.getParameter("bapellidopaterno"),request.getParameter("bapellidomaterno"))) {
                Mensajes.setMensaje("No se puede guardar el representante. Ya existe un representante con el mismo Nombre.");
                requestMulti.setAttribute("resultado", Mensajes.mensaje);
                requestMulti.setAttribute("exito", "1");
                requestMulti.getRequestDispatcher("/mensajes.jsp").forward(requestMulti, response);            
            } else {           
            Hashtable files = request.getFiles();
            UploadFile file  = null;
            String zipFile = null;
            
            //Buscando la candidad de beneficiarios que tine el cliente
                String idCliente = request.getParameter("bIdCliente");
            String Nrorep = request.getParameter("nrorep");
            Cliente c = new Cliente();
            c.setCliente_Id(idCliente);
            //Validando si se trata de una edicion o de un nuevo beneficiario
            String esEdicion = "";
            try{
                 esEdicion = request.getParameter("esEdicion");
                 if (esEdicion.equals("")){
                 esEdicion = "1";
                 }
            } catch ( Exception es){
                es.printStackTrace();
            }           
            Integer nroRepresentantes = 0;
            Integer elNoRep = new DatosRepLegal().getelNoRep(request.getParameter("bIdCliente"), Nrorep);    
            if (esEdicion.equals("1")){ //Cuando no es una edicion 
                try{
                    //Se recupera la cantidad de beneficiarios que tiene el cliente
                    nroRepresentantes = new DatosRepLegal().getNumeroRepresentantes(idCliente);
                    nroRepresentantes += 1;
                    elNoRep = nroRepresentantes;
                    System.out.println(elNoRep);
                    if ( nroRepresentantes > 5 ){
                        Mensajes.setMensaje("No es posible agregar más Representantes.");
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/mensajes.jsp").forward(requestMulti, response);
                    }
                } catch (Exception es){
                    nroRepresentantes += 1;
                }
            } else if (elNoRep == 0) {
                nroRepresentantes = Integer.parseInt(Nrorep);
                elNoRep = nroRepresentantes;
            }
//        if (elNoRep == 0) {
//            elNoRep = nroRepresentantes;
//        }            

        try{
             //CARGA DEL ID DEL BENEFICIARIO
                upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirIdentificacionRep + elNoRep + Rutas.separador);
                zipFile = request.getParameter("archivoRLIDZip");
                if ( zipFile.length() > 0 ){    
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirIdentificacionRep + elNoRep +  Rutas.separador + Rutas.dirIdentificacionRep + "_" + Fecha.getFechaHoraSistema() + ".zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenRLID = Renombra(archivoZip, Rutas.dirIdentificacionRep + elNoRep, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
                 System.out.println("ControlAlteRepresentante.java archivoRLIDZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            
            try{
             //CARGA DE CURP
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarialRep + elNoRep + Rutas.separador);
             zipFile = request.getParameter("PoderNotarialZip");
             if ( zipFile.length() > 0 ){    
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarialRep + elNoRep +   Rutas.separador + Rutas.dirRlPoderNotarialRep + "_" + Fecha.getFechaHoraSistema() + ".zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenRLPoderNotarial = Renombra(archivoZip, Rutas.dirRlPoderNotarialRep + elNoRep, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
            	 System.out.println("ControlAlteRepresentante.java PoderNotarialZip "+es.toString());
                 es.printStackTrace();
             }
            
            
            
        
        
        String bnombres = request.getParameter("bnombre");
        String bpaterno = request.getParameter("bpaterno");
        String bmaterno = request.getParameter("bmaterno");
        String bfechaNaciento = request.getParameter("bfechaNacimiento");
        String bRFC = request.getParameter("bRFC");  
        String bcurp = request.getParameter("bCURP");  
        String btipoIdentificacion = request.getParameter("tipoIdentificacion");
        TipoIdentificacion tipoIdentificacioBene = new TipoIdentificacion(); 
        tipoIdentificacioBene.setIdentifica_id(btipoIdentificacion);
        
        //aqui iria el otro tipo de id si es que se guaradara
        String bnumeroId = request.getParameter("numeroId");        
        String otroTipoId  = request.getParameter("otroTipoId");
        String autoridadEmite = request.getParameter("autoridadEmite");     
        
        


        //ASIGNANDO AL OBJETO BENEFICIARIO LOS DATOS RECABADOS

        RepLegal RepLegal = new RepLegal();
        RepLegal.setCliente(c);
        RepLegal.setNrorep(elNoRep);
        RepLegal.setRLNombre(bnombres);
        RepLegal.setRLApellidoPaterno(bpaterno);
        RepLegal.setRLApellidoMaterno(bmaterno);
        RepLegal.setRLFechaNacimiento(bfechaNaciento);
        RepLegal.setRLRFC(bRFC);
        RepLegal.setRLCURP(bcurp);
        RepLegal.setIdentifica_id(tipoIdentificacioBene);
        RepLegal.setRLAutoridadEmiteId(autoridadEmite);
        RepLegal.setRLIdentificacionTipo(otroTipoId);
        RepLegal.setRLNumeroID(bnumeroId); //EDITAR
        RepLegal.setImagenRLID(imagenRLID);
        RepLegal.setImagenRLPoderNotarial(imagenRLPoderNotarial);
        
 
   //AGREGAR INSTRUCCION QUE AGREGA EL DOMICILIO        
                HttpSession sesion1 = requestMulti.getSession();
                System.out.println();
                String perfilito=(sesion1.getAttribute("perfilId").toString());
               
                
                String usuario="";
                if ( requestMulti.getSession().getAttribute("usuario") != null){
                    try{

                        if ( requestMulti.getSession().getAttribute("tipo") != null &&  requestMulti.getSession().getAttribute("tipo")  == "T"){
                            UsuarioTransitivo ut = (UsuarioTransitivo)requestMulti.getSession().getAttribute("usuario") ;
                           usuario = ut.getCliente().getCliente_Id();
                            
                        } else {
                            ////UsuarioSistema us = (UsuarioSistema)request.getSession().getAttribute("usuario") ;
                            UsuarioSistema us = (UsuarioSistema)requestMulti.getSession().getAttribute("usuario") ;
                           usuario = us.getUsuario();
                           
                        }
                    } catch (Exception es ){
                    	System.out.println("ControlAlteRepresentante.java requestMulti "+es.toString());
                        
                        es.printStackTrace();
                    }
                }
             
        new DatosRepLegal().changeRepLegal(RepLegal,perfilito,usuario);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Mensajes.setMensaje("Se ha guardado correctamente el Representante Legal.");
            requestMulti.setAttribute("resultado", Mensajes.mensaje);
            requestMulti.setAttribute("exito", "1");
            requestMulti.getRequestDispatcher("/mensajes.jsp").forward(requestMulti, response);
        }
        }
        }
    }

    /**
     * Renombra los archivos para que los encuentre
     * 
     * @param original archivo original
     * @param directorio dir donde se guarda el archivo
     * @param cliente cliente
     * @páram extension extension
     * @return nombre del archivo renombrado
     */
     String Renombra(UploadFile archivoZip, String directorio, String c, String zip){
        File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip.getFileName());
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + "_tmp" + zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
    }           
    private String Renombra(File archivoZip, String directorio, String c, String zip) {
        File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip);
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() +"_tmp"+ zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
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
        try {
            processRequest(request, response);
        } catch (UploadException ex) {
            Logger.getLogger(ControlBeneficiarioFideicomiso.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (UploadException ex) {
            Logger.getLogger(ControlBeneficiarioFideicomiso.class.getName()).log(Level.SEVERE, null, ex);
        }
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

