/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.sun.jersey.core.util.Base64;
import datosRatos.DatosBeneficiario;
import entidad.BeneFideicomiso;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Pais;
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
 * @author Israel Osiris García
 */
@WebServlet(name = "ControlBeneficiarioFideicomiso", urlPatterns = {"/BeneficiarioFideicomiso"})
public class ControlBeneficiarioFideicomiso extends HttpServlet {

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
        
        String imagenCedula = "";
        String imagenActaConstitutiva = "";
        String imagenPoderNotarial = "";
        String imagenIdRepresentante ="";
        
        
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = new MultipartFormDataRequest(requestMulti);
            if (new DatosBeneficiario().RFCExist(request.getParameter("bIdCliente"), request.getParameter("bRFC"), "X")) {
                Mensajes.setMensaje("No se puede guardar el beneficiario. Ya existe un beneficiario con el mismo RFC con otro tipo de persona.");
                requestMulti.setAttribute("resultado", Mensajes.mensaje);
                requestMulti.setAttribute("exito", "1");
                requestMulti.getRequestDispatcher("/mensajes.jsp").forward(requestMulti, response);            
            } else {
            Hashtable files = request.getFiles();
            UploadFile file  = null;
            String zipFile = null;
        
        String idCliente = request.getParameter("bIdCliente");
        Cliente cliente = new Cliente();
        cliente.setCliente_Id(idCliente);    
            
//        Cliente cliente = new Cliente();
//        cliente.setCliente_Id( UsuarioTransitivo.getCliente().getCliente_Id() );
//        String idCliente = request.getParameter("bIdCliente");
        Cliente c = new Cliente();
       c.setCliente_Id(idCliente);
        //Validando si se trata de una edición o de un nuevo beneficiario
            String esEdicion = "";
            try{
                 esEdicion = request.getParameter("esEdicion");
            } catch ( Exception es){
                es.printStackTrace();
            }
            
        
        Integer nroBeneficiario = 0;
        Integer elNoBene = new DatosBeneficiario().getelNoBene(request.getParameter("bIdCliente"), request.getParameter("bRFC"), "X");
        if ( !esEdicion.equals("1")){ //Cuando no es una edicion 
            try{
                //Se recupera la cantidad de beneficiarios que tiene el cliente
                nroBeneficiario = new DatosBeneficiario().getNumeroBeneficiario(idCliente);
                //Se suma el beneficiario 
                nroBeneficiario += 1;

                if ( nroBeneficiario > 5 ){
                    Mensajes.setMensaje("No es posible agregar mas de cinco beneficiarios.");
                    requestMulti.setAttribute("resultado", Mensajes.mensaje);
                    requestMulti.setAttribute("exito", "1");
                    requestMulti.getRequestDispatcher("/mensajes.jsp").forward(requestMulti, response);
                }

            } catch (Exception es){
                nroBeneficiario += 1;
            }
        } else {
            nroBeneficiario = Integer.parseInt(request.getParameter("nroBene").toString().trim());
        }
        if (elNoBene == 0) {
            elNoBene = nroBeneficiario;
        }      
             
        
             try{
             //CARGA DE CEDULA FISCAL
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirCedulaBene + elNoBene + Rutas.separador);
             zipFile = request.getParameter("cedulaPFZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirCedulaBene + elNoBene + Rutas.separador + Rutas.dirCedulaBene + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenCedula = Renombra(archivoZip, Rutas.dirCedulaBene + elNoBene, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
                 System.out.println("ControlBeneficiarioFideicomiso.java cedulaPFZip "+es.toString());
                 
                 es.printStackTrace();
             }
             
              try{
             //CARGA DE CEDULA FISCAL
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirActaConstitutivaBene + elNoBene + Rutas.separador);
             zipFile = request.getParameter("ActaConstitutivaZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirActaConstitutivaBene + elNoBene + Rutas.separador + Rutas.dirActaConstitutivaBene + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenActaConstitutiva = Renombra(archivoZip, Rutas.dirActaConstitutivaBene + elNoBene, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
                 System.out.println("ControlBeneficiarioFideicomiso.java ActaConstitutivaZip "+es.toString());
                 
                 es.printStackTrace();
             }
               try{
             //CARGA DE Poder notarial
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarialBene + elNoBene + Rutas.separador);
             zipFile = request.getParameter("PoderNotarialZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarialBene + elNoBene + Rutas.separador + Rutas.dirRlPoderNotarialBene + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenPoderNotarial = Renombra(archivoZip, Rutas.dirRlPoderNotarialBene + elNoBene, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
                 System.out.println("ControlBeneficiarioFideicomiso.java PoderNotarialZip "+es.toString());
                 
                 es.printStackTrace();
             }
               try{
             //CARGA DE CEDULA FISCAL
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirIdentificacionBene + elNoBene + Rutas.separador);
             zipFile = request.getParameter("RepresentanteLegalZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirIdentificacionBene + elNoBene + Rutas.separador + Rutas.dirIdentificacionBene + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenIdRepresentante = Renombra(archivoZip, Rutas.dirIdentificacionBene + elNoBene, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
                 System.out.println("ControlBeneficiarioFideicomiso.java RepresentanteLegalZip "+es.toString());
                 
                 es.printStackTrace();
             }
               
        String  razonSocial =  request.getParameter("bRazonSocial");
        String  rfc = (String) request.getParameter("bRFC");
        String  nrofideicomiso =  request.getParameter("noFideicomiso");
        String  tepais =  request.getParameter("btelPais");
        String  telefono =  request.getParameter("btelefono");
        String  tipodomi = "";
        String  fechaRegistro  = "";
        String  institucionfiduciaria = request.getParameter("bInstitucionFiduciaria");
        String  rlnombre = request.getParameter("bnombre");
        String  rlpaterno = request.getParameter("bpaterno");
        String  rlmaterno = request.getParameter("bmaterno");
        String  rlfechaNacimiento = request.getParameter("bfechaNacimiento");
        String  rlrfc  = request.getParameter("rlRFC");
        String  rlcurp  = request.getParameter("bCURP");
        String btipoIdentificacion = request.getParameter("tipoIdentificacion");
        TipoIdentificacion tipoIdentificacioBene = new TipoIdentificacion(); 
        tipoIdentificacioBene.setIdentifica_id(btipoIdentificacion);
        String  rlautoridademiteid = request.getParameter("autoridadEmite"); 
        String  rlnumeroId = request.getParameter("numeroId");
        String  rlotroTipoId  = request.getParameter("otroTipoId");   
        String  email  =  request.getParameter("correo");
        String bcolonia = request.getParameter("bcolonia");
        String bcalle = request.getParameter("bcalle");
        String bexterior = request.getParameter("bexterior");
        String binterior = request.getParameter("binterior");
        String bcp = request.getParameter("bcp");
        String bpais = request.getParameter("bpaisDomicilio");
        String bestado = request.getParameter("bestado");
        String bCiudad = request.getParameter("bCiudad");
        try{
            if ( tepais.equals("MX")){
                  tipodomi = "N";
            } else{
                tipodomi = "E";
            } 
        } catch( Exception es){
            es.printStackTrace();
        }
        
        BeneFideicomiso beneFideicomiso = new BeneFideicomiso(cliente, nroBeneficiario, razonSocial, rfc, nrofideicomiso, tepais, telefono, tipodomi, fechaRegistro, imagenCedula, institucionfiduciaria, imagenActaConstitutiva, imagenPoderNotarial, imagenIdRepresentante, rlnombre, rlpaterno, rlmaterno, rlfechaNacimiento, rlrfc, rlcurp, tipoIdentificacioBene, rlautoridademiteid, rlnumeroId, rlotroTipoId, email, 
         bpais, bestado,  bCiudad, bcolonia, bcalle, bexterior, binterior, bcp);
                
        //RECUPARANDO LOS DATOS DEL DOMICILIO DEL BENEFICIARIO
//        String bcolonia = request.getParameter("bcolonia");
//        String bcalle = request.getParameter("bcalle");
//        String bexterior = request.getParameter("bexterior");
//        String binterior = request.getParameter("binterior");
//        String bcp = request.getParameter("bcp");
//        String bpais = request.getParameter("bpaisDomicilio");
//        String bestado = request.getParameter("bestado");
//        String bCiudad = request.getParameter("bCiudad"); 
//        Domicilio domicilioBeneficiario = new Domicilio();
//        domicilioBeneficiario.setColonia(bcolonia);
//        domicilioBeneficiario.setCalle(bcalle);
//        domicilioBeneficiario.setNumexterior(bexterior);
//        domicilioBeneficiario.setNuminterior(binterior);
//        domicilioBeneficiario.setCodpostal(bcp);
//        domicilioBeneficiario.setPais( new Pais(bpais) );
//        domicilioBeneficiario.setEstado_prov(bestado);
//        domicilioBeneficiario.setCiudad(bCiudad);
//        domicilioBeneficiario.setCliente(cliente);
//        domicilioBeneficiario.setNrobene(Integer.toString(nroBeneficiario));
HttpSession sesion1 = requestMulti.getSession();
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
                        es.printStackTrace();
                    }
                }
        new DatosBeneficiario().changeBeneFideicomiso(beneFideicomiso,perfilito,usuario);
        // new DatosBeneficiario().changeDomilio(domicilioBeneficiario);
                
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Mensajes.setMensaje("Se ha guardado correctamente el beneficiario.");
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
