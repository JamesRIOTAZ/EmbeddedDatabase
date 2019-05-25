package kryin.embeded;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteTest {
	
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT fname, lname FROM user");
		return res;
	}
	
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
		initialise();
	}

	private void initialise() throws SQLException {
		if(!hasData) {
			hasData = true;
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
		
			if(!res.next()) {
				System.out.println("Building the User table with prepopulated values.");
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE user(id integer,"
					+ "fname varchar(60)," + "lName varchar(60),"
					+ "primary key (id));");
				
			PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?);");
			prep.setInt(1, 1);
			prep.setString(2, "James");
			prep.setString(3, "McDonald");
			prep.execute();
			
			PreparedStatement prep2 = con.prepareStatement("INSERT INTO user values(?,?,?);");
			prep2.setInt(1, 2);
			prep2.setString(2, "Ashley");
			prep2.setString(3, "Carver");
			prep2.execute();
			
			}
		}
	}
	
	public void addUser(String firstName, String lastName) throws ClassNotFoundException, SQLException {
		if(con==null) {
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?);");
		prep.setString(2, firstName);
		prep.setString(3, lastName);
		prep.execute();
	}

}
