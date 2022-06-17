/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.sun.jersey.core.util.Base64;
import datosRatos.DatosBeneficiario;
import entidad.BeneMoral;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Giro;
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
 * @author Israel Osiris Garcia
 */
@WebServlet(name = "ControlBeneficiarioPersonaMoral", urlPatterns = {"/BeneficiarioPersonaMoral"})
public class ControlBeneficiarioPersonaMoral extends HttpServlet {

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
        
        String imagenCedula = "";
        String imagenActaConstitutiva = "";
        String imagencompdomicilio = "";
        String imagenPoderNotarial = "";
        String imagenIdRepresentante = "";
        
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){            
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = new MultipartFormDataRequest(requestMulti);
            if (new DatosBeneficiario().RFCExist(request.getParameter("bIdCliente"), request.getParameter("bRFC"), "M")) {
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
            Integer elNoBene = new DatosBeneficiario().getelNoBene(request.getParameter("bIdCliente"), request.getParameter("bRFC"), "M");                    
        if ( !esEdicion.equals("1")){ //Cuando no es una edicion 
            try{
                //Se recupera la cantidad de beneficiarios que tiene el cliente
                nroBeneficiario = new DatosBeneficiario().getNumeroBeneficiario(idCliente);
                //Se suma el beneficiario 
                nroBeneficiario += 1;

                if ( nroBeneficiario > 5 ){
                    Mensajes.setMensaje("No es posible agregar más de cinco beneficiarios.");
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
            	 System.out.println("ControlBeneficiarioPersonaMoral.java cedulaPFZip "+es.toString());
                 
                 es.printStackTrace();
             }
       
        try{
             //CARGA DE ACTA CONSTITUTIVA
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
            	 System.out.println("ControlBeneficiarioPersonaMoral.java ActaConstitutivaZip "+es.toString());
                 
                 es.printStackTrace();
             }
        
        try{
             //CARGA DE COMPROBANTE DOMICILIO
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDomBene + elNoBene + Rutas.separador);
             zipFile = request.getParameter("compDomicilioZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDomBene + elNoBene + Rutas.separador + Rutas.dirComprobanteDomBene + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagencompdomicilio = Renombra(archivoZip, Rutas.dirComprobanteDomBene + elNoBene, c.getCliente_Id(), ".zip" );
                }
             } catch ( Exception es ){
                 es.printStackTrace();
                 System.out.println("ControlBeneficiarioPersonaMoral.java compDomicilioZip "+es.toString());
                 
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
                 System.out.println("ControlBeneficiarioPersonaMoral.java PoderNotarialZip "+es.toString());
                 
                 es.printStackTrace();
             }
        
          try{
             //CARGA DE RepresentanteLegal
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
                 System.out.println("ControlBeneficiarioPersonaMoral.java RepresentanteLegalZip "+es.toString());
                 
                 es.printStackTrace();
             }
        
        String  razonsocial = request.getParameter("bRazonSocial");
        String  rfc = request.getParameter("bRFC");
        String  pais = request.getParameter("bpais");
        String  fechaconstitucion = request.getParameter("bfechaConstitucion");
        String  tepais = request.getParameter("btelPais");
        String  telefono = request.getParameter("btelefono");
        String  tipodomi ="";
        String  giro_id = request.getParameter("bGiro");
        String  fecharegistro = request.getParameter("");
        String  email = request.getParameter("correo");
        String  rlnombre = request.getParameter("bnombre");
        String  rlapellidopaterno = request.getParameter("bpaterno");
        String  rlapellidomaterno = request.getParameter("bmaterno");
        String rlfechanacimiento = request.getParameter("bfechaNacimiento");
        String rlrfc = request.getParameter("rlRFC");  
        String rlcurp = request.getParameter("bCURP");  
        String btipoIdentificacion = request.getParameter("tipoIdentificacion");
        TipoIdentificacion tipoIdentificacioBene = new TipoIdentificacion(); 
        tipoIdentificacioBene.setIdentifica_id(btipoIdentificacion);        
        //aqui iria el otro tipo de id si es que se guaradara
        String numeroid = request.getParameter("numeroId");        
        String otroTipoId  = request.getParameter("otroTipoId");
        String autoridadEmite = request.getParameter("autoridadEmite");
        String bcolonia = request.getParameter("bcolonia");
        String bcalle = request.getParameter("bcalle");
        String bexterior = request.getParameter("bexterior");
        String binterior = request.getParameter("binterior");
        String bcp = request.getParameter("bcp");
        String rlpais = request.getParameter("bpaisDomicilio");
        String bestado = request.getParameter("bestado");
        String bCiudad = request.getParameter("bCiudad");
        
        

        try{
            if ( pais.equals("MX")){
                  tipodomi = "N";
            } else{
                tipodomi = "E";
            } 
        } catch( Exception es){
            es.printStackTrace();
        }
        Giro g = new Giro();
        g.setGiro_id(giro_id);
        
        Pais paisBene = new Pais();
        paisBene.setPais(pais);
        
        Pais telPais = new Pais();
        telPais.setPais(tepais);
        
        
        //SETEANDO DATOS DEL BENEFICIARIO MORAL
        BeneMoral beneMoral = new BeneMoral();
        beneMoral.setCliente(cliente);
        beneMoral.setNrobene(nroBeneficiario);
        beneMoral.setRazonsocial(razonsocial);
        beneMoral.setRfc(rfc);
        beneMoral.setPais(paisBene);
        beneMoral.setFechaconstitucion(fechaconstitucion);
        beneMoral.setTepais(telPais);
        beneMoral.setTelefono(telefono);
        beneMoral.setTipodomi(tipodomi);
        beneMoral.setGiro(g);
        beneMoral.setFecharegistro(fecharegistro);
        beneMoral.setImagencedulafiscal(imagenCedula);
        beneMoral.setImagenactaconstitutiva(imagenActaConstitutiva);
        beneMoral.setImagencompdomicilio(imagencompdomicilio);
        beneMoral.setImagenpodernotarial(imagenPoderNotarial);
        beneMoral.setImagenidrepresentantelegal(imagenIdRepresentante);
        beneMoral.setEmail(email);
        beneMoral.setRlNombre(rlnombre);
        beneMoral.setRlApellidoPaterno(rlapellidopaterno);
        beneMoral.setRlApellidoMaterno(rlapellidomaterno);
        beneMoral.setRLFechaNacimiento(rlfechanacimiento);
        beneMoral.setRLRFC(rlrfc);
        beneMoral.setRLCURP(rlcurp);
        beneMoral.setIdentifica_id(tipoIdentificacioBene);
        beneMoral.setRLAutoridadEmiteId(autoridadEmite);
        beneMoral.setRLIdentificacionTipo(otroTipoId);
        beneMoral.setRLNumeroID(numeroid); //EDITAR
        beneMoral.setColonia(bcolonia);
        beneMoral.setCalle(bcalle);
        beneMoral.setNumexterior(bexterior);
        beneMoral.setNuminterior(binterior);
        beneMoral.setCodpostal(bcp);
        beneMoral.setRlPais(rlpais);
        beneMoral.setEstado_prov(bestado);
        beneMoral.setCiudad(bCiudad);
        
//        //RECUPARANDO LOS DATOS DEL DOMICILIO DEL BENEFICIARIO
//        String bcolonia = request.getParameter("bcolonia");
//        String bcalle = request.getParameter("bcalle");
//        String bexterior = request.getParameter("bexterior");
//        String binterior = request.getParameter("binterior");
//        String bcp = request.getParameter("bcp");
//        String bpais = request.getParameter("bpaisDomicilio");
//        String bestado = request.getParameter("bestado");
//        String bCiudad = request.getParameter("bCiudad");
//        
        
        
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
//        
//        
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
        new DatosBeneficiario().changeBeneMoral(beneMoral,perfilito,usuario);
       // new DatosBeneficiario().changeDomilio(domicilioBeneficiario);
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Mensajes.setMensaje("Se ha guardado correctamente el beneficiario.");
            requestMulti.setAttribute("resultado", Mensajes.mensaje);
            requestMulti.setAttribute("exito", "1");
            requestMulti.getRequestDispatcher("/mensajes.jsp").forward(requestMulti, response);
        }
            }       
        }//if multipart
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
            Logger.getLogger(ControlBeneficiarioPersonaMoral.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControlBeneficiarioPersonaMoral.class.getName()).log(Level.SEVERE, null, ex);
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
