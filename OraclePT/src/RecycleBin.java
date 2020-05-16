import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecycleBin {

	void run() {
		CreateTable("Vishnu");
		CloneTable("Vishnu");
		
	}
	void DropTable(String tabName) {

		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select table_name from user_tables where table_name like '" + tabName.toUpperCase() + "%'";
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Statement stmt2 = oraCon.createStatement();
				stmt2.executeUpdate("drop table " + rs.getString(1));
				System.out.println("Dropping Table " + rs.getString(1));
				stmt2.close();
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	
	}
	
	void CloneTable(String tabName) {

		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			int i = 0;
			while (i < 33000) {
				String SQL = "create table " + tabName + i +" as select * from " + tabName;
				stmt.execute(SQL);
				i++;
			}
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	
	}
	
	void CreateTable(String tabName) {

		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "create table " + tabName + "( t1 number, t2 varchar2(3400), t3 varchar2(3400))";
			stmt.execute(SQL);
			SQL = "insert into " + tabName +" (t1, t2 ,t3) values (?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 1;
			while (i < 115) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setString(2, OraRandom.randomString(3400));
				pstmt.setString(3, OraRandom.randomString(3400));
				pstmt.executeUpdate();
				i++;
			}
			pstmt.close();
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	
	}
}

