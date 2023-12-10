package agentie_imobiliara;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Clasa pentru conexiunea cu DB.
 * @author Florin
 *
 */
public class ConnectDB {
	private static BasicDataSource dataSource;
	
	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/agentie_imobiliara");
		dataSource.setUsername("postgres");
		dataSource.setPassword("sutihojohe84");
		dataSource.setMaxTotal(10);
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
