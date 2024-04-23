package doa;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class liaison {
		
		private static Connection connection;
		
		private liaison() throws SQLException
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost/jeumemoire", "root", "");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		public static Connection getInstance() throws SQLException
		{
			if(connection==null)
				new liaison();
			return connection;
		}

	}

