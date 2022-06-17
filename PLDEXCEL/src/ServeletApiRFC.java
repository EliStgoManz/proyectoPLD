

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;
import rfcCurpAppi.token;

/**
 * Servlet implementation class ServeletApiRFC
 */
@WebServlet("/ServeletApiRFC")
public class ServeletApiRFC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeletApiRFC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String parametro1 = request.getParameter("rfc");
//		String parametro2 = request.getParameter("pantalla");
		
//		parametro2.style.display = "block";
		
		String ok ="{\"respuesta\" :";
		
		try{
			token t = new token();
			String respuesta = t.obtenerCoincidenciaRFC(parametro1).getCuerpoRespuesta();
			System.out.println("Mensaje : " + respuesta);
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(respuesta);
			
		    String mens= "\""+json.get("mensaje") + "\"";
		    System.out.println("Mensaje RFC :" + mens);
		    
		    ok+=mens+"}";
		    
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
			response.getWriter().write(ok);
			
		}catch(Exception e){
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
