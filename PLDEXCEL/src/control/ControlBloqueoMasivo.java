/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosBloqueo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utilidades.Fecha;
import utilidades.Mensajes;
import utilidades.Rutas;

/**
 *
 * @author israel.garcia
 */
@WebServlet(name = "ControlBloqueoMasivo", urlPatterns = {"/BloqueoMasivo"})
public class ControlBloqueoMasivo extends HttpServlet {

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
        //response.setContentType("text/html;charset=UTF-8");
        ArrayList clientesBloquear = new ArrayList();
        String rutaArchivo = "";
        String[] listaClientes;
        String cliente = ""; 
        boolean bandera = false;
        
        
        
        
        
                
        /***
         *      AGREGAR LA LOGICA PARA LECTURA DEL ARCHIVO EXCEL
         */        
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = new MultipartFormDataRequest(requestMulti);
            Hashtable files = request.getFiles();
            UploadFile file  = null;

            try{

               upBean.setFolderstore(Rutas.rutaTemporales);
               file = (UploadFile) files.get("archivo");
               if ( file.getFileSize() > 0 ){
                   
                   file.setFileName( Fecha.getFechaHoraSistema() + file.getFileName() ) ;
                   upBean.store(request, "archivo");
                   
                   
                   //Lectura del archivo excel
                    try  {
                        rutaArchivo = Rutas.rutaTemporales + Rutas.separador + file.getFileName();
                        FileInputStream archivoExcel = new FileInputStream(new File(rutaArchivo));
			// leer archivo excel
			XSSFWorkbook worbook = new XSSFWorkbook(archivoExcel);
//			obtener la hoja que se va leer
			XSSFSheet sheet = worbook.getSheetAt(0);
			//obtener todas las filas de la hoja excel
			Iterator<Row> rowIterator = sheet.iterator();
                        //Create a DataFormatter to format and get each cell's value as String
                        DataFormatter dataFormatter = new DataFormatter();
                        

			Row row;
			// se recorre cada fila hasta el final
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				//se obtiene las celdas por fila
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell;
				//se recorre cada celda
				while (cellIterator.hasNext()) {
					// se obtiene la celda en específico y se la imprime
					cell = cellIterator.next();
                                        cliente += dataFormatter.formatCellValue(cell) + ",";
				}
				
                            }
                    } catch (Exception e) {
                            e.printStackTrace();        
                    } // try de la lecura del excel

                   
                   
                   
                   

               }

            } catch ( Exception es ){
            	System.out.println("ControlBloqueoMasivo.java rutaTemporales "+es.toString());
                
                es.printStackTrace();
            } //carga del archivo
            
            String fecha  = request.getParameter("fechaBloqueo");
            cliente = cliente.substring(0, cliente.length() - 1 );
            listaClientes = cliente.split(",");


            bandera = new DatosBloqueo().bloqueoMasivo(listaClientes, fecha);    
        }//multipart
        
            if ( bandera ){
                Mensajes.setMensaje("Se ha realizado el bloqueo masivo");
            } else {
                Mensajes.setMensaje("No se ha podido realizar el bloqueo masivo, intente de nuevo");
            }
            
            requestMulti.setAttribute("resultado", Mensajes.mensaje);
            requestMulti.setAttribute("exito", "1");
            requestMulti.getRequestDispatcher("/bloqueo_masivo.jsp").forward(requestMulti, response);
        
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
            Logger.getLogger(ControlBloqueoMasivo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControlBloqueoMasivo.class.getName()).log(Level.SEVERE, null, ex);
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
