package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConexionPLD;
import java.sql.Connection;

/**
 * Servlet implementation class ControlExport
 */
@WebServlet("/export")
public class ControlExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConexionPLD cnn = new ConexionPLD();	
	private ResultSet conjuntoResultados;	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlExport() {
        super();
        CreaExcel();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void CreaExcel(){
		Connection conex = null;	
		Statement instruccion;		
		String correos = "";
		try {			
			conex = cnn.getConnection("dbpld");
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select correo from varusuarios  as vu inner join varusuariotransitorio as"
					+ " vut on vut.ejecutivo=vu.numero_interno where vut.idcliente='A-000001';";

			conjuntoResultados = instruccion.executeQuery(consulta);

			if (conjuntoResultados.next()) {
				correos = conjuntoResultados.getString("correo") + ",";
			} // fin del while			
			System.out.println("Correo: "+correos);			
		} catch (SQLException es) {
			System.out.println("ERROR de SQL "+es);
		}catch (Exception e) {
			System.out.println("ERROR "+e);
		}	
	}

}
