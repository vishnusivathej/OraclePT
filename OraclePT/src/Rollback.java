import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Rollback {
	
	
	void run(int NO_OF_THREADS) {
		CreateTable();
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 1;
		while (i < NO_OF_THREADS) {
			asd.submit(new rollback());
			i++;
		}
	}
	
	class rollback implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select max(roll) From students";
				ResultSet rs = stmt.executeQuery(SQL);
				int maxvalue = 0;
				while (rs.next()) {
					maxvalue = rs.getInt(1);
				}
				SQL = "delete from students where roll = ?";
				PreparedStatement pstmt  = oraCon.prepareStatement(SQL);
				oraCon.setAutoCommit(false);
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(maxvalue));
					pstmt.executeUpdate();
					oraCon.rollback();
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	void CreateTable() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL;
			try {
				SQL = "drop table students";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			SQL = "Create table students(roll number primary key, dept_id number, mark1 number, mark2 number, mark3 number) ";
			stmt.execute(SQL);
			SQL = "insert into students(roll, dept_id, mark1, mark2, mark3) values (?,?,?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 0;
			while (i < 1000000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setInt(2, OraRandom.randomUniformInt(100));
				pstmt.setInt(3, OraRandom.randomUniformInt(100));
				pstmt.setInt(4, OraRandom.randomUniformInt(100));
				pstmt.setInt(5, OraRandom.randomUniformInt(100));
				pstmt.addBatch();
				if (i%10000 == 0) {
					pstmt.executeBatch();
				}
				i++;
			}
			pstmt.executeBatch();
			pstmt.close();
			GatherStats a = new GatherStats("STUDENTS");
			a.run();
			stmt.close();
			oraCon.close();
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
}
