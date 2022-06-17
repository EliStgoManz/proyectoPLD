package exportacion;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class Conexion {
	
	public Connection getConnection(String database) {
		
		Connection myConn = null;
		try {
			InitialContext ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/" + database);
			myConn = ds.getConnection();
		} catch (Exception ex) {
			System.out.println(Conexion.class.getName()+ " Se genero un error al solicitar la conexion a la base de datos [" + database + "]. " );
		} finally {
			if (myConn == null){
				System.out.println(Conexion.class.getName()+"No se logro conectar con la base de datos [" + database + "]");
			}
		}
		return myConn;
	}
}