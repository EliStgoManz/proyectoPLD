/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import datosRatos.Conexion2;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gibran.matias
 */
@WebServlet(urlPatterns = {"/ServletBenef"})
public class ServletBenef extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletBenef</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletBenef at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws SQLException 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     private String estadoCliente(String idCliente) throws SQLException{
            Connection conex = null;
            Conexion2 c=new Conexion2();   
            Statement instruccion;
            ResultSet conjuntoResultados=null;
            try{
              conex = c.getConnection("dbpld");
              instruccion = conex.createStatement( 
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY );
          
              String consulta = "select estado from avcliente where cliente_id='"+idCliente+"'";
              conjuntoResultados = instruccion.executeQuery(consulta);
              while(conjuntoResultados.next()){ 
                    return conjuntoResultados.getString("estado");
              }
              conjuntoResultados.close();
              instruccion.close();
              
              }catch(Exception e){
                    e.printStackTrace();
              }finally {
            	  conex.close();
              }
            return "";
      }
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parametro1 = request.getParameter("idsales");
       Connection conex = null;
            Conexion2 c=new Conexion2();   
            Statement instruccion;
            ResultSet conjuntoResultados=null;
            String jsonResult="{\"Beneficiarios\" :[";
            try{
              conex = c.getConnection("dbpld");
              instruccion = conex.createStatement( 
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY );
          
              String consulta = 
                      "SELECT cliente_id, nroBene, " +
                      "COALESCE(nombre,'') || ' ' || COALESCE(apellidopaterno,'') || ' ' || COALESCE(apellidomaterno,'') as nombreCompleto," +
                      "'Persona Fisica' as tipoPersonaDesc, 'F' as tipoPersona " +
                      "from benefisica where cliente_id = '" + parametro1 + "' union " +
                      "SELECT cliente_id, nroBene, " +
                      "COALESCE(razonsocial, '') as nombreCompleto," +
                      "'Persona Moral' as tipoPersonaDesc, 'M' as tipoPersona " +
                      "from benemoral where cliente_id = '" + parametro1 + "' union " +
                      "SELECT cliente_id, nroBene, " +
                      "COALESCE(razonsocial, '')  as nombreCompleto," +
                      "'Fideicomiso' as tipoPersonaDesc, 'X' as tipoPersona " +
                      "from benefideicomiso where cliente_id = '" + parametro1 + "'";
               conjuntoResultados = instruccion.executeQuery(consulta);
           
               while(conjuntoResultados.next()){ 
                    
                     jsonResult+="{\"cliente_id\" :";
                     jsonResult+= "\""+conjuntoResultados.getString("cliente_id")+"\""+",";
                     jsonResult+= "\"nrobene\" :"+"\""+conjuntoResultados.getString("nrobene")+"\""+",";
                     jsonResult+= "\"nombre_completo\" :"+"\""+conjuntoResultados.getString("nombrecompleto")+"\""+",";
                     jsonResult+= "\"tipo_persona\" :"+"\""+conjuntoResultados.getString("tipopersonadesc")+"\""+",";
                     jsonResult+= "\"tipo_person\" :"+"\""+conjuntoResultados.getString("tipopersona")+"\""+",";
                     jsonResult+= "\"estadoCliente\" :"+"\""+estadoCliente(conjuntoResultados.getString("cliente_id"))+"\"";
                     jsonResult+="},";
                   }
               
               if(jsonResult.charAt(jsonResult.length()-1)==',')
                     jsonResult=jsonResult.substring(0, jsonResult.length()-1);
               jsonResult+="]}";//fin del while
               
               conjuntoResultados.close();
               instruccion.close();
            
            }catch(Exception e){
                e.printStackTrace();
            }finally {
            	try {
					conex.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
           response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(jsonResult);

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
