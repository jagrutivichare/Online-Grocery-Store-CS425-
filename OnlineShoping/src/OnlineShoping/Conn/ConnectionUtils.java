package OnlineShoping.Conn;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		// Here I using MySql Database.
		//return MySQLConnUtils.getMySQLConnection();

		return OracleConnUtils.getOracleConnection();
		// return SQLServerConnUtils_JTDS.getSQLServerConnection_JTDS();
		// return SQLServerConnUtils_SQLJDBC.getSQLServerConnection_SQLJDBC();
	}

	public static void closeQuietly(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
		}
	}

	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
		}
	}
}
