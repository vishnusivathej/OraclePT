import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HardParsing {
	
	void runLoad(int NO_OF_THREADS) {
		//CreateTable();

		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new RunSoftParseLoad());
			i++;
		}
	
	}

	class RunHardParseLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select max(t1) from hardparse";
				System.out.println("Started Load from thread --> " + Thread.currentThread().getName());
				ResultSet rs = stmt.executeQuery(SQL);
				int MAXVALUE = 0;
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				rs.close();
				
				int i = 0 ;
				while (i < MAXVALUE) {
					SQL = "select * from hardparse where t1 = "+ OraRandom.randomUniformInt(MAXVALUE);
					rs = stmt.executeQuery(SQL);
					while (rs.next()) {
						
					}
					rs.close();
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	class RunSoftParseLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select max(t1) from hardparse";
				System.out.println("Started Load from thread --> " + Thread.currentThread().getName());
				ResultSet rs = stmt.executeQuery(SQL);
				int MAXVALUE = 0;
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				rs.close();
				SQL = "select * from hardparse where t1 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
				while (i < 50000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(MAXVALUE));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					i++;
				}
				System.out.println("Finished");
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	private void CreateTable() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "drop table hardparse";
			try {
				stmt.execute(SQL);
			}
			catch(Exception E) {
				E.printStackTrace();
			}
			 SQL = "create table hardparse(t1 number primary key, t2 number, t3 number, t4 number, t5 varchar2(40))";
			stmt.execute(SQL);
			SQL = "insert into hardparse (t1, t2, t3, t4, t5) values (?,?,?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 0 ;
			while (i < 300000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setInt(2,  OraRandom.randomUniformInt(100));
				pstmt.setInt(3,  OraRandom.randomUniformInt(1000));
				pstmt.setInt(4,  OraRandom.randomUniformInt(10000));
				pstmt.setString(5,  OraRandom.randomString(40));
				pstmt.addBatch();
				if (i%10000 == 0) {
					pstmt.executeBatch();
					System.out.println("Loaded " + i + "rows");
				}
				i++;
			}
			pstmt.executeBatch();
			pstmt.close();
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
}

