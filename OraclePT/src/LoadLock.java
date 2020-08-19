import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoadLock {


	void run() {
	//	CreateTable("Vishnu");
	//	CloneTable("Vishnu");
		ExecutorService asd = Executors.newFixedThreadPool(30);
		int i = 0;
		while (i < 60) {
			asd.submit(new RunLoad());
			i++;
		} 
	
	}
	class RunLoad implements Runnable{
		public void run() {

			try {
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt;
				ResultSet rs;
				int i = 0;
				while (i < 1000000) {
					String SQL = "select * from Vishnu" + OraRandom.randomSkewInt(33400) +" where t1 = ?";
					pstmt =  oraCon.prepareStatement(SQL);
					pstmt.setInt(1, OraRandom.randomUniformInt(1000));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					pstmt.close();
					i++;
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
			while (i < 640000) {
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
			while (i < 100) {
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
