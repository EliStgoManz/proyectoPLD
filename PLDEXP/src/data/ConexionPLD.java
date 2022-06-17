package data;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ConexionPLD {
	
	public Connection getConnection(String database) {
		
		Connection myConn = null;
		try {
			System.out.println("1");			
			InitialContext ctx = new InitialContext();
			System.out.println("2");			
			Context env = (Context) ctx.lookup("java:comp/env");
			System.out.println("Env.-"+env);
			DataSource ds = (DataSource) env.lookup("jdbc/" + database);
			myConn = ds.getConnection();
		} catch (Exception ex) {
			System.out.println(ConexionPLD.class.getName()+ " Se genero un error al solicitar la conexion a la base de datos [" + database + "]. " );
		} finally {
			if (myConn == null){
				System.out.println(ConexionPLD.class.getName()+"No se logro conectar con la base de datos [" + database + "]");
			}
		}
		return myConn;
	}
}