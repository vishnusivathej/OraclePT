import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoadLock {


	void run() {
		CreateTable("Vishnu");
		CloneTable("Vishnu");
		
	}
	class RunLoad implements Runnable{
		public void run() {

			try {
				Connection oraCon = DBConnection.getOraConn();
				

				
				
				
				String SQL = "";//"select table_name from user_tables where table_name like '" + tabName.toUpperCase() + "%'";
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
	}
	
	void CloneTable(String tabName) {

		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			int i = 0;
			while (i < 64000) {
				String SQL = "create table " + tabName + i + "( t1 number primary key, t2 varchar2(20), t3 varchar2(20))";
				stmt.execute(SQL);
				SQL = "insert into " + tabName +i +" select * from "+tabName;
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
			String SQL = "create table " + tabName + "( t1 number primary key, t2 varchar2(20), t3 varchar2(20)) ";
			stmt.execute(SQL);
			SQL = "insert into " + tabName +" (t1, t2 ,t3) values (?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 1;
			while (i < 1000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setString(2, OraRandom.randomString(20));
				pstmt.setString(3, OraRandom.randomString(20));
				pstmt.executeUpdate();
				i++;
			}
			pstmt.close();
			stmt.close();
			GatherStats a = new GatherStats(tabName);
			a.run();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	
	}

}
