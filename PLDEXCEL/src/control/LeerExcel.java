/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.ConsecutivoAviso;
import datosRatos.DatosAviso;
import entidad.Aviso;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.DateUtil;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import utilidades.Cadenas;
import utilidades.Fecha;
import utilidades.Rutas;

/**
 *
 * @author edson
 */
@WebServlet(name = "LeerExcel", urlPatterns = {"/LeerExcel"})
public class LeerExcel extends HttpServlet {

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
      //  response.setContentType("text/html;charset=ISO-8859-1");
    	response.setContentType("text/html;charset=UTF-8");
    	 XSSFWorkbook wb=null;
        int lineaxls = 0;    
        String nodoxml = "";
        try (PrintWriter out = response.getWriter()) {
            try{
                
                /*FileItemFactory es una interfaz para crear FileItem*/
            	System.out.println("Rutadocumentos: "+Rutas.rutaCarga);            	
                String path = Rutas.rutaCarga + Rutas.separador + "Excel" + Rutas.separador;
            	System.out.println("Ruta de Excel Avisos: "+path);
                String file_name = null;
                String Mes = null;
                
                FileItemFactory file_factory = new DiskFileItemFactory();

                /*ServletFileUpload esta clase convierte los input file a FileItem*/
                ServletFileUpload servlet_up = new ServletFileUpload(file_factory);
                /*sacando los FileItem del ServletFileUpload en una lista */
                List items = servlet_up.parseRequest(request);


                for(int i=0;i<items.size();i++){
                    /*FileItem representa un archivo en memoria que puede ser pasado al disco duro*/
                    FileItem item = (FileItem) items.get(i);
                    /*item.isFormField() false=input file; true=text field*/
                    if (! item.isFormField()){
                        File ruta  = new File (path);
                        if ( !ruta.exists() ){
                            ruta.mkdir();
                        }

                        /*cual sera la ruta al archivo en el servidor*/
                        String extension = "." + new Cadenas().getExtension(item.getName());
                        file_name = "SAT_" + Fecha.getFechaHoraSistema() + extension;
                        File archivo_server = new File(path + file_name);
                        
                        /*y lo escribimos en el servido*/
                        item.write(archivo_server);
                        //file_name = item.getName();
                    }else{
                        Mes = item.getString();
                    }
                }         
                try{
                    int values = 0;
                    int last_id_cliente = 0;
                    int id_cliente = 0;
                    boolean hayDatos = false;
                    List<String> message = new ArrayList<>();
                    DateFormat n_df = new SimpleDateFormat("yyyy-MM");
                    System.out.print("--------> Mes Reportado:"+Mes+"-01<------------");
                    Date date = n_df.parse(Mes);
                    DateFormat o_df = new SimpleDateFormat("yyyyMM");
                    String mes_reportado = o_df.format(date);
                    System.out.print("--------> Mes Reportado Out:"+mes_reportado+"<------------");
                    String ano_reportado = mes_reportado.substring(0, 4);
                    //Codigo para crear el Xml
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
                    org.w3c.dom.Document doc = docBuilder.newDocument();

                    org.w3c.dom.Element archivo = doc.createElement("archivo");
                    doc.appendChild(archivo);

                    Attr attr_1 = doc.createAttribute("xmlns:xsi");
                    attr_1.setValue("http://www.w3.org/2001/XMLSchema-instance");
                    archivo.setAttributeNode(attr_1);

                    Attr attr_2 = doc.createAttribute("xmlns");
                    attr_2.setValue("http://www.uif.shcp.gob.mx/recepcion/tpp");
                    archivo.setAttributeNode(attr_2);

                    Attr attr_3 = doc.createAttribute("xsi:schemaLocation");
                    attr_3.setValue("http://www.uif.shcp.gob.mx/recepcion/tpp tpp.xsd");
                    archivo.setAttributeNode(attr_3);

                    org.w3c.dom.Element informe = doc.createElement("informe");
                    archivo.appendChild(informe);

                    org.w3c.dom.Element mes = doc.createElement("mes_reportado");
                    informe.appendChild(mes);
                    mes.setTextContent(mes_reportado);

                    org.w3c.dom.Element sujeto_obligado = doc.createElement("sujeto_obligado");
                    informe.appendChild(sujeto_obligado);

                    org.w3c.dom.Element clave_sujeto_obligado = doc.createElement("clave_sujeto_obligado");
                    sujeto_obligado.appendChild(clave_sujeto_obligado);
                    clave_sujeto_obligado.setTextContent("EFE8908015L3");

                    org.w3c.dom.Element clave_actividad = doc.createElement("clave_actividad");
                    sujeto_obligado.appendChild(clave_actividad);
                    clave_actividad.setTextContent("TPP");

                     //Codigo para iterar sobre el Excel
                    System.out.println("------------------------->>FilePath:"+path + file_name+"<---------------------------------");
                    InputStream ExcelFileToRead = new FileInputStream(path + file_name);
                    wb = new XSSFWorkbook(ExcelFileToRead);
                   
                    XSSFSheet sheet=wb.getSheetAt(0);
                    XSSFRow row; 
                    XSSFCell cell;

                    Iterator rows = sheet.rowIterator();
                    
                    ConsecutivoAviso FolioConsecutivo = new ConsecutivoAviso();
                    int consecutivo = FolioConsecutivo.getConsecutivo();
                    String referencia = "00000000";
                    Cadenas cad1 = new Cadenas();
                    DecimalFormat fmtCP = new DecimalFormat("00000");
                    DecimalFormat fmtMoneda = new DecimalFormat("#############0.00");
                    
                    while (rows.hasNext()) {
                        lineaxls++;
                        row=(XSSFRow) rows.next();
                        XSSFCell first_cell = row.getCell(row.getFirstCellNum());
                        
                        if(first_cell != null && row.getRowNum() > 6 && first_cell.getCellType() != XSSFCell.CELL_TYPE_BLANK )
                        {
                            
                            values++;
                            DateFormat df = new SimpleDateFormat("yyyyMMdd");
                            XSSFCell id_cliente_cell = row.getCell((short) 0);
                            id_cliente_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                            id_cliente =(int) id_cliente_cell.getNumericCellValue();
                            System.out.println("------------------------>id del aviso: "+id_cliente+"<------------------------------------");
                            
                            if(id_cliente != last_id_cliente && id_cliente_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                            {
                                Aviso datosAviso = datosAviso(id_cliente);
                                System.out.println("------------------------>Hay Datos: "+datosAviso.gethayDatos()+"<------------------------------------");
                                
                                if(datosAviso.gethayDatos() == true)
                                {
                                    hayDatos = true;
                                    System.out.println("Datos del aviso: Tipopersona"+datosAviso.getTipoPersona()+",nombre:"+datosAviso.getNombre()+", ap_p:"+datosAviso.getApellido_paterno()
                                                        +", ap_ma:"+datosAviso.getApellido_materno()+", fecha_nac:"+datosAviso.getFecha_nacimiento()+", curp:"+datosAviso.getRfc()
                                                        +", pais:"+datosAviso.getPais()+", ac_ec"+datosAviso.getActividad_economica()+", rfc:"+datosAviso.getRfc());

                                    
                                    nodoxml = "aviso";
                                    org.w3c.dom.Element aviso = doc.createElement("aviso");
                                    informe.appendChild(aviso);

                                    nodoxml = "referencia_aviso";
                                    org.w3c.dom.Element referencia_aviso = doc.createElement("referencia_aviso");
                                    aviso.appendChild(referencia_aviso);
                                    consecutivo++;                                    
                                    referencia = "00000000" + Integer.toString(consecutivo);
                                    referencia = referencia.substring(referencia.length() - 8);
                                    referencia_aviso.setTextContent(ano_reportado + referencia);

                                    nodoxml = "prioridad";
                                    org.w3c.dom.Element prioridad = doc.createElement("prioridad");
                                    aviso.appendChild(prioridad);
                                    prioridad.setTextContent("1");

                                    nodoxml = "alerta";
                                    org.w3c.dom.Element alerta = doc.createElement("alerta");
                                    aviso.appendChild(alerta);

                                    nodoxml = "tipo_alerta";
                                    org.w3c.dom.Element tipo_alerta = doc.createElement("tipo_alerta");
                                    alerta.appendChild(tipo_alerta);
                                    tipo_alerta.setTextContent("100");

                                    nodoxml = "persona_aviso";
                                    org.w3c.dom.Element persona_aviso = doc.createElement("persona_aviso");
                                    aviso.appendChild(persona_aviso);

                                    nodoxml = "tipo_persona";
                                    org.w3c.dom.Element tipo_persona = doc.createElement("tipo_persona");
                                    persona_aviso.appendChild(tipo_persona);
                                    
                                    switch (datosAviso.getTipoPersona()){
                                        case "F":
                                            org.w3c.dom.Element persona_fisica = doc.createElement("persona_fisica");
                                            tipo_persona.appendChild(persona_fisica);

                                            nodoxml = "fisica : nombre";
                                            org.w3c.dom.Element nombre = doc.createElement("nombre");
                                            persona_fisica.appendChild(nombre);
                                            nombre.setTextContent(cad1.corrigeEncode(datosAviso.getNombre()));

                                            nodoxml = "fisica : apellido_paterno";
                                            org.w3c.dom.Element apellido_paterno = doc.createElement("apellido_paterno");
                                            persona_fisica.appendChild(apellido_paterno);
                                            apellido_paterno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_paterno()));

                                            nodoxml = "fisica : apellido_materno";
                                            org.w3c.dom.Element apellido_materno = doc.createElement("apellido_materno");
                                            persona_fisica.appendChild(apellido_materno);
                                            apellido_materno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_materno()));

                                            nodoxml = "fisica : fecha_nacimiento";
                                            org.w3c.dom.Element fecha_nacimiento = doc.createElement("fecha_nacimiento");
                                            persona_fisica.appendChild(fecha_nacimiento);
                                            fecha_nacimiento.setTextContent(datosAviso.getFecha_nacimiento());

                                            nodoxml = "fisica : rfc";
                                            org.w3c.dom.Element apo_rfc = doc.createElement("rfc");
                                            persona_fisica.appendChild(apo_rfc);
                                            apo_rfc.setTextContent(datosAviso.getRfc());

                                            nodoxml = "fisica : rfc";
                                            if (datosAviso.getCurp().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element curp = doc.createElement("curp");
                                                persona_fisica.appendChild(curp);
                                                curp.setTextContent(datosAviso.getCurp());
                                            }

                                            nodoxml = "fisica : pais_nacionalidad";
                                            org.w3c.dom.Element pais = doc.createElement("pais_nacionalidad");
                                            persona_fisica.appendChild(pais);
                                            pais.setTextContent(datosAviso.getPais());

                                            nodoxml = "fisica : actividad_economica";
                                            org.w3c.dom.Element actividad_economica = doc.createElement("actividad_economica");
                                            persona_fisica.appendChild(actividad_economica);
                                            actividad_economica.setTextContent(datosAviso.getActividad_economica());
                                        break;
                                            
                                        case "M":
                                            org.w3c.dom.Element persona_moral = doc.createElement("persona_moral");
                                            tipo_persona.appendChild(persona_moral);

                                            nodoxml = "moral : denominacion_razon";
                                            org.w3c.dom.Element denominacion_razon = doc.createElement("denominacion_razon");
                                            persona_moral.appendChild(denominacion_razon);
                                            denominacion_razon.setTextContent(datosAviso.getDenominacion_razon());

                                            nodoxml = "moral : fecha_constitucion";
                                            org.w3c.dom.Element fecha_constitucion = doc.createElement("fecha_constitucion");
                                            persona_moral.appendChild(fecha_constitucion);
                                            fecha_constitucion.setTextContent(datosAviso.getFecha_constitucion());

                                            nodoxml = "moral : rfc";
                                            org.w3c.dom.Element rfc = doc.createElement("rfc");
                                            persona_moral.appendChild(rfc);
                                            rfc.setTextContent(datosAviso.getRfc());

                                            nodoxml = "moral : pais_nacionalidad";
                                            org.w3c.dom.Element pais_nacionalidad = doc.createElement("pais_nacionalidad");
                                            persona_moral.appendChild(pais_nacionalidad);
                                            pais_nacionalidad.setTextContent(datosAviso.getPais());

                                            nodoxml = "moral : giro_mercantil";
                                            org.w3c.dom.Element giro_mercantil = doc.createElement("giro_mercantil");
                                            persona_moral.appendChild(giro_mercantil);
                                            giro_mercantil.setTextContent(datosAviso.getGiro_mercantil());

                                            org.w3c.dom.Element representante_apoderado = doc.createElement("representante_apoderado");
                                            persona_moral.appendChild(representante_apoderado);

                                            nodoxml = "moral : representante_apoderado : nombre";
                                            org.w3c.dom.Element apo_nombre = doc.createElement("nombre");
                                            representante_apoderado.appendChild(apo_nombre);
                                            apo_nombre.setTextContent(cad1.corrigeEncode(datosAviso.getNombre()));

                                            nodoxml = "moral : representante_apoderado : apellido_paterno";                                            
                                            org.w3c.dom.Element apo_apellido_paterno = doc.createElement("apellido_paterno");
                                            representante_apoderado.appendChild(apo_apellido_paterno);
                                            apo_apellido_paterno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_paterno()));

                                            nodoxml = "moral : representante_apoderado : apellido_materno";                                            
                                            org.w3c.dom.Element apo_apellido_materno = doc.createElement("apellido_materno");
                                            representante_apoderado.appendChild(apo_apellido_materno);
                                            apo_apellido_materno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_materno()));
                                            
                                            nodoxml = "moral : representante_apoderado : fecha_nacimiento";                                            
                                            org.w3c.dom.Element apo_fecha_nacimiento = doc.createElement("fecha_nacimiento");
                                            representante_apoderado.appendChild(apo_fecha_nacimiento);
                                            apo_fecha_nacimiento.setTextContent(datosAviso.getFecha_nacimiento());
                                            
                                            nodoxml = "moral : representante_apoderado : rfc";                                            
                                            if (datosAviso.getRfc_poderado().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element apo_rfc_2 = doc.createElement("rfc");
                                                representante_apoderado.appendChild(apo_rfc_2);
                                                apo_rfc_2.setTextContent(datosAviso.getRfc_poderado());
                                            }
                                            
                                            nodoxml = "moral : representante_apoderado : curp";                                            
                                            if (datosAviso.getCurp().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element apo_curp = doc.createElement("curp");
                                                representante_apoderado.appendChild(apo_curp);
                                                apo_curp.setTextContent(datosAviso.getCurp());
                                            }
                                        break;
                                        
                                        case "G":
                                            org.w3c.dom.Element gobierno = doc.createElement("persona_moral");
                                            tipo_persona.appendChild(gobierno);

                                            nodoxml = "gobierno : denominacion_razon";                                            
                                            org.w3c.dom.Element g_denominacion_razon = doc.createElement("denominacion_razon");
                                            gobierno.appendChild(g_denominacion_razon);
                                            g_denominacion_razon.setTextContent(datosAviso.getDenominacion_razon());

                                            nodoxml = "gobierno : fecha_constitucion";                                            
                                            org.w3c.dom.Element fecha_creacion = doc.createElement("fecha_constitucion");
                                            gobierno.appendChild(fecha_creacion);
                                            fecha_creacion.setTextContent(datosAviso.getFecha_constitucion());

                                            nodoxml = "gobierno : rfc";                                            
                                            org.w3c.dom.Element g_rfc = doc.createElement("rfc");
                                            gobierno.appendChild(g_rfc);
                                            g_rfc.setTextContent(datosAviso.getRfc());

                                            nodoxml = "gobierno : pais_nacionalidad";                                            
                                            org.w3c.dom.Element g_pais_nacionalidad = doc.createElement("pais_nacionalidad");
                                            gobierno.appendChild(g_pais_nacionalidad);
                                            g_pais_nacionalidad.setTextContent(datosAviso.getPais());

                                            nodoxml = "gobierno : giro_mercantil";                                            
                                            org.w3c.dom.Element g_giro_mercantil = doc.createElement("giro_mercantil");
                                            gobierno.appendChild(g_giro_mercantil);
                                            if (datosAviso.getGiro_mercantil().trim().compareTo("") != 0)
                                                g_giro_mercantil.setTextContent(datosAviso.getGiro_mercantil());
                                            else
                                                g_giro_mercantil.setTextContent("1000000");

                                            org.w3c.dom.Element g_representante_apoderado = doc.createElement("representante_apoderado");
                                            gobierno.appendChild(g_representante_apoderado);

                                            nodoxml = "gobierno : representante_apoderado : nombre";
                                            org.w3c.dom.Element g_apo_nombre = doc.createElement("nombre");
                                            g_representante_apoderado.appendChild(g_apo_nombre);
                                            g_apo_nombre.setTextContent(cad1.corrigeEncode(datosAviso.getNombre()));

                                            nodoxml = "gobierno : representante_apoderado : apellido_paterno";                                            
                                            org.w3c.dom.Element g_apo_apellido_paterno = doc.createElement("apellido_paterno");
                                            g_representante_apoderado.appendChild(g_apo_apellido_paterno);
                                            g_apo_apellido_paterno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_paterno()));

                                            nodoxml = "gobierno : representante_apoderado : apellido_materno";                                            
                                            org.w3c.dom.Element g_apo_apellido_materno = doc.createElement("apellido_materno");
                                            g_representante_apoderado.appendChild(g_apo_apellido_materno);
                                            g_apo_apellido_materno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_materno()));
                                            
                                            nodoxml = "gobierno : representante_apoderado : fecha_nacimiento";                                            
                                            org.w3c.dom.Element g_apo_fecha_nacimiento = doc.createElement("fecha_nacimiento");
                                            g_representante_apoderado.appendChild(g_apo_fecha_nacimiento);
                                            g_apo_fecha_nacimiento.setTextContent(datosAviso.getFecha_nacimiento());
                                            
                                            nodoxml = "gobierno : representante_apoderado : rfc";                                            
                                            if (datosAviso.getRfc_poderado().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element g_apo_rfc_2 = doc.createElement("rfc");
                                                g_representante_apoderado.appendChild(g_apo_rfc_2);
                                                g_apo_rfc_2.setTextContent(datosAviso.getRfc_poderado());
                                            }
                                            
                                            nodoxml = "gobierno : representante_apoderado : curp";                                            
                                            if (datosAviso.getCurp().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element g_apo_curp = doc.createElement("curp");
                                                g_representante_apoderado.appendChild(g_apo_curp);
                                                g_apo_curp.setTextContent(datosAviso.getCurp());
                                            }
                                        break;
                                        
                                        case "X":
                                            org.w3c.dom.Element fideicomiso = doc.createElement("fideicomiso");
                                            tipo_persona.appendChild(fideicomiso);

                                            nodoxml = "fideicomiso : denominación_razon";                                            
                                            org.w3c.dom.Element x_denominacion_razon = doc.createElement("denominacion_razon");
                                            fideicomiso.appendChild(x_denominacion_razon);
                                            x_denominacion_razon.setTextContent(datosAviso.getDenominacion_razon());

//                                            org.w3c.dom.Element fecha_registro = doc.createElement("fecha_constitucion");
//                                            fideicomiso.appendChild(fecha_registro);
//                                            fecha_registro.setTextContent(datosAviso.getFecha_constitucion());

                                            nodoxml = "fideicomiso : rfc";                                            
                                            org.w3c.dom.Element x_rfc = doc.createElement("rfc");
                                            fideicomiso.appendChild(x_rfc);
                                            x_rfc.setTextContent(datosAviso.getRfc());

                                            org.w3c.dom.Element x_apoderado_delegado = doc.createElement("apoderado_delegado");
                                            fideicomiso.appendChild(x_apoderado_delegado);
                                            
                                            nodoxml = "fideicomiso : apoderado_delegado : nombre";                                            
                                            org.w3c.dom.Element x_apo_nombre = doc.createElement("nombre");
                                            x_apoderado_delegado.appendChild(x_apo_nombre);
                                            x_apo_nombre.setTextContent(cad1.corrigeEncode(datosAviso.getNombre()));

                                            nodoxml = "fideicomiso : apoderado_delegado : apellido_paterno";
                                            org.w3c.dom.Element x_apo_apellido_paterno = doc.createElement("apellido_paterno");
                                            x_apoderado_delegado.appendChild(x_apo_apellido_paterno);
                                            x_apo_apellido_paterno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_paterno()));

                                            nodoxml = "fideicomiso : apoderado_delegado : apellido_materno";                                            
                                            org.w3c.dom.Element x_apo_apellido_materno = doc.createElement("apellido_materno");
                                            x_apoderado_delegado.appendChild(x_apo_apellido_materno);
                                            x_apo_apellido_materno.setTextContent(cad1.corrigeEncode(datosAviso.getApellido_materno()));
                                            
                                            nodoxml = "fideicomiso : apoderado_delegado : fecha_nacimiento";                                            
                                            org.w3c.dom.Element x_apo_fecha_nacimiento = doc.createElement("fecha_nacimiento");
                                            x_apoderado_delegado.appendChild(x_apo_fecha_nacimiento);
                                            x_apo_fecha_nacimiento.setTextContent(datosAviso.getFecha_nacimiento());
                                            
                                            nodoxml = "fideicomiso : apoderado_delegado : rfc";                                            
                                            if (datosAviso.getRfc_poderado().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element x_apo_rfc_2 = doc.createElement("rfc");
                                                x_apoderado_delegado.appendChild(x_apo_rfc_2);
                                                x_apo_rfc_2.setTextContent(datosAviso.getRfc_poderado());
                                            }
                                            
                                            nodoxml = "fideicomiso : apoderado_delegado : curp";                                            
                                            if (datosAviso.getCurp().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element x_apo_curp = doc.createElement("curp");
                                                x_apoderado_delegado.appendChild(x_apo_curp);
                                                x_apo_curp.setTextContent(datosAviso.getCurp());
                                            }
                                        break;
                                    }
                                    nodoxml = "tipo_domicilio";
                                    org.w3c.dom.Element tipo_domicilio = doc.createElement("tipo_domicilio");
                                    persona_aviso.appendChild(tipo_domicilio);
                                    
                                    switch(datosAviso.getTipoDomicilio())
                                    {
                                        case "N":
                                            org.w3c.dom.Element nacional = doc.createElement("nacional");
                                            tipo_domicilio.appendChild(nacional);

                                            nodoxml = "nacional : colonia";
                                            org.w3c.dom.Element colonia = doc.createElement("colonia");
                                            nacional.appendChild(colonia);
                                            colonia.setTextContent(cad1.corrigeEncode(datosAviso.getColonia()));

                                            nodoxml = "nacional : calle";                                            
                                            org.w3c.dom.Element calle = doc.createElement("calle");
                                            nacional.appendChild(calle);
                                            calle.setTextContent(cad1.corrigeEncode(datosAviso.getCalle()));

                                            nodoxml = "nacional : numero_exterior";                                             
                                            org.w3c.dom.Element numero_exterior = doc.createElement("numero_exterior");
                                            nacional.appendChild(numero_exterior);
                                            numero_exterior.setTextContent(datosAviso.getNumero_exterior());

                                            nodoxml = "nacional : numero_interior";                                            
                                            if (datosAviso.getNumero_interior().trim().compareTo("") != 0) {
                                                org.w3c.dom.Element numero_interior = doc.createElement("numero_interior");
                                                nacional.appendChild(numero_interior);
                                                numero_interior.setTextContent(datosAviso.getNumero_interior());
                                            }
                                          
                                            nodoxml = "nacional : codigo_postal";                                            
                                            org.w3c.dom.Element codigo_postal = doc.createElement("codigo_postal");
                                            nacional.appendChild(codigo_postal);
                                            codigo_postal.setTextContent(datosAviso.getCodigo_postal());
                                        break;
                                        
                                        case "E":
                                            org.w3c.dom.Element extranjero = doc.createElement("extranjero");
                                            tipo_domicilio.appendChild(extranjero);
                                            
                                            nodoxml = "extranjero : pais";
                                            org.w3c.dom.Element epais = doc.createElement("pais");
                                            extranjero.appendChild(epais);
                                            epais.setTextContent(cad1.corrigeEncode(datosAviso.getPais_dom()));
                                            
                                            nodoxml = "extranjero : estado_provincia";
                                            org.w3c.dom.Element eestado = doc.createElement("estado_provincia");
                                            extranjero.appendChild(eestado);
                                            eestado.setTextContent(cad1.corrigeEncode(datosAviso.getColonia()));
                                            
                                            nodoxml = "extranjero : ciudad_poblacion";                                            
                                            org.w3c.dom.Element eciudad = doc.createElement("ciudad_poblacion");
                                            extranjero.appendChild(eciudad);
                                            eciudad.setTextContent(cad1.corrigeEncode(datosAviso.getColonia()));

                                            nodoxml = "extranjero : colonia";                                            
                                            org.w3c.dom.Element ecolonia = doc.createElement("colonia");
                                            extranjero.appendChild(ecolonia);
                                            ecolonia.setTextContent(cad1.corrigeEncode(datosAviso.getColonia()));

                                            nodoxml = "extranjero : calle";                                            
                                            org.w3c.dom.Element ecalle = doc.createElement("calle");
                                            extranjero.appendChild(ecalle);
                                            ecalle.setTextContent(cad1.corrigeEncode(datosAviso.getCalle()));

                                            nodoxml = "extranjero : numero_exterior";                                            
                                            org.w3c.dom.Element enumero_exterior = doc.createElement("numero_exterior");
                                            extranjero.appendChild(enumero_exterior);
                                            enumero_exterior.setTextContent(datosAviso.getNumero_exterior());

                                            nodoxml = "extranjero : codigo_postal";
                                            org.w3c.dom.Element ecodigo_postal = doc.createElement("codigo_postal");
                                            extranjero.appendChild(ecodigo_postal);
                                            ecodigo_postal.setTextContent(datosAviso.getCodigo_postal());
                                        break;
                                    }

                                    

                                    org.w3c.dom.Element detalle_operaciones = doc.createElement("detalle_operaciones");
                                    aviso.appendChild(detalle_operaciones);
                                
                                    org.w3c.dom.Element datos_operacion = doc.createElement("datos_operacion");
                                    detalle_operaciones.appendChild(datos_operacion);

                                    nodoxml = "fecha_operacion";
                                    org.w3c.dom.Element fecha_operacion = doc.createElement("fecha_operacion");
                                    XSSFCell fecha_operacion_cell = row.getCell((short) 72);
                                    if (fecha_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        if (DateUtil.isCellDateFormatted(fecha_operacion_cell)) {

                                            Date fecha_operacion_val = fecha_operacion_cell.getDateCellValue();
                                            String date_fecha_operacion = df.format(fecha_operacion_val);
                                            fecha_operacion.setTextContent(date_fecha_operacion);
                                        }else
                                        {
                                            int fecha_operacion_val =(int) fecha_operacion_cell.getNumericCellValue();
                                            fecha_operacion.setTextContent(Integer.toString(fecha_operacion_val));  
                                        }
                                    }
                                    else if(fecha_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String fecha_operacion_val =(String) fecha_operacion_cell.getStringCellValue();
                                        fecha_operacion.setTextContent(fecha_operacion_val);
                                    }
                                    datos_operacion.appendChild(fecha_operacion);

                                    nodoxml = "emisor : codigo_postal";
                                    org.w3c.dom.Element d_codigo_postal = doc.createElement("codigo_postal");
                                    XSSFCell d_codigo_postal_cell = row.getCell((short) 73);
                                    if (d_codigo_postal_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                        int d_codigo_postal_val =(int) d_codigo_postal_cell.getNumericCellValue();
                                        d_codigo_postal.setTextContent(fmtCP.format(d_codigo_postal_val));
                                    } else if(d_codigo_postal_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)  {
                                        //String d_codigo_postal_val =(String) d_codigo_postal_cell.getStringCellValue();
                                        int d_codigo_postal_val =(int) d_codigo_postal_cell.getNumericCellValue();
                                        d_codigo_postal.setTextContent(fmtCP.format(d_codigo_postal_val));
                                    }
                                    datos_operacion.appendChild(d_codigo_postal);

                                    nodoxml = "tipo_operacion";
                                    org.w3c.dom.Element tipo_operacion = doc.createElement("tipo_operacion");
                                    XSSFCell tipo_operacion_cell = row.getCell((short) 74);
                                    if (tipo_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int tipo_operacion_val =(int) tipo_operacion_cell.getNumericCellValue();
                                        tipo_operacion.setTextContent(Integer.toString(tipo_operacion_val));
                                    }
                                    else if(tipo_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String tipo_operacion_val =(String) tipo_operacion_cell.getStringCellValue();
                                        tipo_operacion.setTextContent(tipo_operacion_val);
                                    }
                                    datos_operacion.appendChild(tipo_operacion);

                                    nodoxml = "cantidad"; 
                                    org.w3c.dom.Element cantidad = doc.createElement("cantidad");
                                    XSSFCell cantidad_cell = row.getCell((short) 75);
                                    if (cantidad_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int cantidad_val =(int) cantidad_cell.getNumericCellValue();
                                        cantidad.setTextContent(Integer.toString(cantidad_val));
                                    }
                                    else if(cantidad_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String cantidad_val =(String) cantidad_cell.getStringCellValue();
                                        cantidad.setTextContent(cantidad_val);
                                    }
                                    datos_operacion.appendChild(cantidad);

                                    nodoxml = "datos_liquidacion";                                     
                                    org.w3c.dom.Element datos_liquidacion = doc.createElement("datos_liquidacion");
                                    datos_operacion.appendChild(datos_liquidacion);

                                    nodoxml = "fecha_pago";                                     
                                    org.w3c.dom.Element fecha_pago = doc.createElement("fecha_pago");
                                    XSSFCell fecha_pago_cell = row.getCell((short) 76);
                                    if (fecha_pago_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        if (DateUtil.isCellDateFormatted(fecha_operacion_cell)) {

                                            Date fecha_pago_val = fecha_pago_cell.getDateCellValue();
                                            String date_fecha_pago = df.format(fecha_pago_val);
                                            fecha_pago.setTextContent(date_fecha_pago);
                                        }else
                                        {
                                            int fecha_pago_val =(int) fecha_pago_cell.getNumericCellValue();
                                            fecha_pago.setTextContent(Integer.toString(fecha_pago_val));
                                        }
                                    }
                                    else if(fecha_pago_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String fecha_pago_val =(String) fecha_pago_cell.getStringCellValue();
                                        fecha_pago.setTextContent(fecha_pago_val);
                                    }
                                    datos_liquidacion.appendChild(fecha_pago);

                                    nodoxml = "instrumento_monetario";                                     
                                    org.w3c.dom.Element instrumento_monetario = doc.createElement("instrumento_monetario");
                                    XSSFCell instrumento_monetario_cell = row.getCell((short) 77);
                                    if (instrumento_monetario_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int instrumento_monetario_val =(int) instrumento_monetario_cell.getNumericCellValue();
                                        instrumento_monetario.setTextContent(Integer.toString(instrumento_monetario_val));
                                    }
                                    else if(instrumento_monetario_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String instrumento_monetario_val =(String) instrumento_monetario_cell.getStringCellValue();
                                        instrumento_monetario.setTextContent(instrumento_monetario_val);
                                    }
                                    datos_liquidacion.appendChild(instrumento_monetario);

                                    nodoxml = "moneda";                                     
                                    org.w3c.dom.Element moneda = doc.createElement("moneda");
                                    XSSFCell moneda_cell = row.getCell((short) 78);
                                    if (moneda_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int moneda_val =(int) moneda_cell.getNumericCellValue();
                                        moneda.setTextContent(Integer.toString(moneda_val));
                                    }
                                    else if(moneda_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String moneda_val =(String) moneda_cell.getStringCellValue();
                                        moneda.setTextContent(moneda_val);
                                    }
                                    datos_liquidacion.appendChild(moneda);

                                    nodoxml = "monto_operacion"; 
                                    org.w3c.dom.Element monto_operacion = doc.createElement("monto_operacion");
                                    XSSFCell monto_operacion_cell = row.getCell((short) 79);
                                    monto_operacion.setTextContent(fmtMoneda.format(monto_operacion_cell.getNumericCellValue()));

                                    datos_liquidacion.appendChild(monto_operacion);
                                }else
                                {
                                    message.add("Numero de Cliente:"+id_cliente+" no encontrado en estado validado o por validar");
                                    values--;
                                    hayDatos = false;
                                }
                            }else {
                                if(hayDatos == true) {
                                
                                    Node aviso = informe.getLastChild();
                                    Node detalle_operaciones = aviso.getLastChild();

                                    org.w3c.dom.Element datos_operacion = doc.createElement("datos_operacion");
                                    detalle_operaciones.appendChild(datos_operacion);

                                    nodoxml = "fecha_operacion : 2";                                     
                                    org.w3c.dom.Element fecha_operacion = doc.createElement("fecha_operacion");
                                    XSSFCell fecha_operacion_cell = row.getCell((short) 72);
                                    if (fecha_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        if (DateUtil.isCellDateFormatted(fecha_operacion_cell)) {

                                            Date fecha_operacion_val = fecha_operacion_cell.getDateCellValue();
                                            String date_fecha_operacion = df.format(fecha_operacion_val);
                                            fecha_operacion.setTextContent(date_fecha_operacion);
                                        }else
                                        {
                                            int fecha_operacion_val =(int) fecha_operacion_cell.getNumericCellValue();
                                            fecha_operacion.setTextContent(Integer.toString(fecha_operacion_val));  
                                        }
                                    }
                                    else if(fecha_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String fecha_operacion_val =(String) fecha_operacion_cell.getStringCellValue();
                                        fecha_operacion.setTextContent(fecha_operacion_val);
                                    }
                                    datos_operacion.appendChild(fecha_operacion);

                                    nodoxml = "emisor : codigo_postal : 2";                                     
                                    org.w3c.dom.Element d_codigo_postal = doc.createElement("codigo_postal");
                                    XSSFCell d_codigo_postal_cell = row.getCell((short) 73);
                                    if (d_codigo_postal_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                        int d_codigo_postal_val =(int) d_codigo_postal_cell.getNumericCellValue();
                                        d_codigo_postal.setTextContent(fmtCP.format(d_codigo_postal_val));
                                    } else if(d_codigo_postal_cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                                        //String d_codigo_postal_val =(String) d_codigo_postal_cell.getStringCellValue();
                                        int d_codigo_postal_val =(int) d_codigo_postal_cell.getNumericCellValue();                                        
                                        d_codigo_postal.setTextContent(fmtCP.format(d_codigo_postal_val));
                                    }
                                    datos_operacion.appendChild(d_codigo_postal);

                                    nodoxml = "tipo_operacion : 2";                                     
                                    org.w3c.dom.Element tipo_operacion = doc.createElement("tipo_operacion");
                                    XSSFCell tipo_operacion_cell = row.getCell((short) 74);
                                    if (tipo_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int tipo_operacion_val =(int) tipo_operacion_cell.getNumericCellValue();
                                        tipo_operacion.setTextContent(Integer.toString(tipo_operacion_val));
                                    }
                                    else if(tipo_operacion_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String tipo_operacion_val =(String) tipo_operacion_cell.getStringCellValue();
                                        tipo_operacion.setTextContent(tipo_operacion_val);
                                    }
                                    datos_operacion.appendChild(tipo_operacion);

                                    nodoxml = "cantidad : 2";                                     
                                    org.w3c.dom.Element cantidad = doc.createElement("cantidad");
                                    XSSFCell cantidad_cell = row.getCell((short) 75);
                                    if (cantidad_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int cantidad_val =(int) cantidad_cell.getNumericCellValue();
                                        cantidad.setTextContent(Integer.toString(cantidad_val));
                                    }
                                    else if(cantidad_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String cantidad_val =(String) cantidad_cell.getStringCellValue();
                                        cantidad.setTextContent(cantidad_val);
                                    }
                                    datos_operacion.appendChild(cantidad);

                                    org.w3c.dom.Element datos_liquidacion = doc.createElement("datos_liquidacion");
                                    datos_operacion.appendChild(datos_liquidacion);

                                    nodoxml = "fecha_pago : 2";                                     
                                    org.w3c.dom.Element fecha_pago = doc.createElement("fecha_pago");
                                    XSSFCell fecha_pago_cell = row.getCell((short) 76);
                                    if (fecha_pago_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        if (DateUtil.isCellDateFormatted(fecha_operacion_cell)) {

                                            Date fecha_pago_val = fecha_pago_cell.getDateCellValue();
                                            String date_fecha_pago = df.format(fecha_pago_val);
                                            fecha_pago.setTextContent(date_fecha_pago);
                                        }else
                                        {
                                            int fecha_pago_val =(int) fecha_pago_cell.getNumericCellValue();
                                            fecha_pago.setTextContent(Integer.toString(fecha_pago_val));
                                        }
                                    }
                                    else if(fecha_pago_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String fecha_pago_val =(String) fecha_pago_cell.getStringCellValue();
                                        fecha_pago.setTextContent(fecha_pago_val);
                                    }
                                    datos_liquidacion.appendChild(fecha_pago);

                                    nodoxml = "instrumento_monetario : 2";                                     
                                    org.w3c.dom.Element instrumento_monetario = doc.createElement("instrumento_monetario");
                                    XSSFCell instrumento_monetario_cell = row.getCell((short) 77);
                                    if (instrumento_monetario_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int instrumento_monetario_val =(int) instrumento_monetario_cell.getNumericCellValue();
                                        instrumento_monetario.setTextContent(Integer.toString(instrumento_monetario_val));
                                    }
                                    else if(instrumento_monetario_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String instrumento_monetario_val =(String) instrumento_monetario_cell.getStringCellValue();
                                        instrumento_monetario.setTextContent(instrumento_monetario_val);
                                    }
                                    datos_liquidacion.appendChild(instrumento_monetario);

                                    nodoxml = "moneda : 2";                                     
                                    org.w3c.dom.Element moneda = doc.createElement("moneda");
                                    XSSFCell moneda_cell = row.getCell((short) 78);
                                    if (moneda_cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                                    {
                                        int moneda_val =(int) moneda_cell.getNumericCellValue();
                                        moneda.setTextContent(Integer.toString(moneda_val));
                                    }
                                    else if(moneda_cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                                    {
                                        String moneda_val =(String) moneda_cell.getStringCellValue();
                                        moneda.setTextContent(moneda_val);
                                    }
                                    datos_liquidacion.appendChild(moneda);

                                    nodoxml = "monto_operacion : 2"; 
                                    org.w3c.dom.Element monto_operacion = doc.createElement("monto_operacion");
                                    XSSFCell monto_operacion_cell = row.getCell((short) 79);
                                    monto_operacion.setTextContent(fmtMoneda.format(monto_operacion_cell.getNumericCellValue()));

                                    datos_liquidacion.appendChild(monto_operacion);
                                }else
                                {
                                    values--;
                                }
                            }
                            last_id_cliente =  id_cliente;
                            System.out.println("------------------------>Last Id Cliente: "+last_id_cliente+"<------------------------------------");
                        }
                    }
                    FolioConsecutivo.setConsecutivo(consecutivo);

                    TransformerFactory factory = TransformerFactory.newInstance();
                    Transformer transformer = factory.newTransformer();

                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                //    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                    StringWriter sw = new StringWriter();
                    StreamResult result = new StreamResult(sw);
                    DOMSource source = new DOMSource(doc);
                    transformer.transform(source, result);
                    String xmlString = sw.toString();

                    File file=new File(path + file_name.substring(0,file_name.indexOf(".")) + ".xml");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    bw.write(xmlString);
                    bw.flush();
                    bw.close();
                    wb.close();
                    File f = new File(path + file_name);
                    f.delete();
                    message.add("Se importaron "+values+" registros exitosamente");
                    request.setAttribute("message", message.toString());
                    request.setAttribute("file_name", file_name.substring(0,file_name.indexOf(".")) +".xml");
                }catch (IOException | IllegalArgumentException | ParseException | ParserConfigurationException | TransformerException | DOMException e) {
                    request.setAttribute("message", "Ooops 1!!! Parece que el archivo no tiene el formato correcto. Linea " + lineaxls + " - nodo - " + nodoxml + " " + Arrays.toString(e.getStackTrace()));
                }
            }catch(Exception e) {
                request.setAttribute("message", "Ooops 2!!! Parece que el archivo no tiene el formato correcto. Linea " + lineaxls + " - nodo - " + nodoxml + " " + Arrays.toString(e.getStackTrace()));
            }
            
             request.getRequestDispatcher("/avisos.jsp").forward(request, response);
        }
    }
    
    private Aviso datosAviso(int id_cliente) throws ParseException
    {
        Aviso datosAviso;
        datosAviso = new DatosAviso().getData(id_cliente);
        return datosAviso;
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
