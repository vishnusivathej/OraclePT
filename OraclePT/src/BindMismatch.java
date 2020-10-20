import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BindMismatch {

	void runLoad(int NO_OF_THREADS) {
		//CreateTable();

		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new BindMismatchLoad());
			i++;
		}
	
	}
	
	class BindMismatchLoad implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();				
				String SQL = "select * from BindMisMatch where t4 in (?,?,?)";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				ResultSet rs;
				int i = 0;
				while (i < 10000000) {
					setBind(pstmt, 1);
					setBind(pstmt, 2);
					setBind(pstmt, 3);
					rs = pstmt.executeQuery();
					while(rs.next()) {
						
					}
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		void setBind(PreparedStatement pstmt, int val) {
			try {
				int temp = OraRandom.randomUniformInt(5);
				if (temp==1) {
					pstmt.setInt(val, OraRandom.randomUniformInt(100));
				}
				else if (temp == 2) {
					pstmt.setLong(val, OraRandom.randomUniformInt(100));
				}
				else if (temp == 3) {
					pstmt.setString(val,  Integer.toString(OraRandom.randomUniformInt(1000)));
				}
				else if (temp==4) {
					pstmt.setNString(val, Integer.toString(OraRandom.randomUniformInt(1000)));
				}
				if (temp == 5) {
					pstmt.setShort(val, Short.parseShort(Integer.toString(OraRandom.randomUniformInt(1000))));
				}
				else {
					pstmt.setInt(val, OraRandom.randomUniformInt(1000));
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
			String SQL = "drop table BindMisMatch";
			try {
				stmt.execute(SQL);
			}
			catch(Exception E) {
				E.printStackTrace();
			}
			 SQL = "create table BindMisMatch(t1 number primary key, t2 number, t3 number, t4 number, t5 number)";
			stmt.execute(SQL);
			SQL = "create index t5_idx on BindMisMatch(t5)";
			stmt.execute(SQL);
			SQL = "insert into BindMisMatch (t1, t2, t3, t4, t5) values (?,?,?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 0 ;
			while (i < 3000000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setInt(2,  OraRandom.randomUniformInt(100));
				pstmt.setInt(3,  OraRandom.randomUniformInt(1000));
				pstmt.setInt(4,  OraRandom.randomUniformInt(10000));
				pstmt.setInt(5,  OraRandom.randomUniformInt(1000));
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
