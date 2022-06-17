/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import datosRatos.Conexion2;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
@WebServlet(urlPatterns = {"/ServletRepLeg"})
public class ServletRepLeg extends HttpServlet {

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
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           
         /*   out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletRepLeg</title>");  
            out.println("json:");
            out.println("</head>");
            out.println("<body>");
           // out.println("<h1>Servlet ServletRepLeg at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");*/
            doGet(request, response);
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
              instruccion.close();
              conjuntoResultados.close();
              
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
            String jsonResult="{\"Representantes\" :[";
            try{
              conex = c.getConnection("dbpld");
              instruccion = conex.createStatement( 
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY );
          
              String consulta = 
                      "SELECT cliente_id, nrorep, " +
                      "COALESCE(rlnombre,'') || ' ' || COALESCE(rlapellidopaterno,'') || ' ' || COALESCE(rlapellidomaterno,'') as nombrecompleto " +
                      "from replegal where cliente_id = '" + parametro1 + "' ";
               conjuntoResultados = instruccion.executeQuery(consulta);
           
               while(conjuntoResultados.next()){ 
                    String client=conjuntoResultados.getString("cliente_id");
                    String nr=conjuntoResultados.getString("nrorep");
                    String nc=conjuntoResultados.getString("nombrecompleto");
                    jsonResult+="{"+"\"client_id\": "+"\""+client+"\""+",";
                     jsonResult+= "\"nrorep\": "+"\""+nr+"\""+",";
                     jsonResult+= "\"nombre_completo\": "+"\""+nc+"\""+",";
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
         
          
            //return jsonResult;

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
