import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BindMismatch {

	void runLoad(int NO_OF_THREADS) {
	//	CreateTable();

		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new BindMismatchLoad2());
			i++;
		}
	
	}
	
	class BindMismatchLoad implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();				
				String SQL = "select * from BindMisMatch where t5 like ? or t5 like ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				ResultSet rs;
				int j = 0;
				while (j < 1000) {
					int i = 3 ;
					while (i < 20) {
						
						pstmt.setString(1, OraRandom.randomBindString(i) );
						pstmt.setString(2, OraRandom.randomBindString(i+1) );
						rs = pstmt.executeQuery();
						while (rs.next()) {
							
						}
						rs.close();
						i= i + 1;
					}
					j++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	
	}
	
	class BindMismatchLoad2 implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();				
				String SQL = "select * from BindMisMatch where t4 in (?,?,?)";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				ResultSet rs;
				int j = 0;
				while (j < 1000000) {
					setBind(pstmt, 1);
					setBind(pstmt, 2);
					setBind(pstmt, 3);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					rs.close();
					j++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		void setBind(PreparedStatement pstmt, int val) {
			try {
				int temp2 = OraRandom.randomUniformInt(5);
				if (temp2==1) {
					pstmt.setInt(val, OraRandom.randomSkewInt(100));
				}
				else if (temp2==2) {
					pstmt.setLong(val, OraRandom.randomUniformInt(100));
				}
				else if (temp2==3) {
					pstmt.setNString(val, Integer.toString(OraRandom.randomUniformInt(10000)));
				}
				else if (temp2==4) {
					pstmt.setString(val, Integer.toString(OraRandom.randomUniformInt(10000)));
				}
				else if (temp2==5) {
					pstmt.setShort(val, Short.parseShort(Integer.toString(OraRandom.randomUniformInt(10000))));
				}
				else {
					pstmt.setInt(val, Short.parseShort(Integer.toString(OraRandom.randomUniformInt(10000))));
				}
			}
			catch(Exception E) {
				
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
			 SQL = "create table BindMisMatch(t1 number primary key, t2 number, t3 number, t4 number, t5 varchar2(200))";
			stmt.execute(SQL);
			SQL = "create index t5_idx on BindMisMatch(t5)";
			stmt.execute(SQL);
			SQL = "create index t4_idx on bindmismatch(t4)";
			stmt.execute(SQL);
					
			SQL = "insert into BindMisMatch (t1, t2, t3, t4, t5) values (?,?,?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 0 ;
			while (i < 30000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setInt(2,  OraRandom.randomUniformInt(100));
				pstmt.setInt(3,  OraRandom.randomUniformInt(1000));
				pstmt.setInt(4,  OraRandom.randomUniformInt(10000));
				pstmt.setString(5,  OraRandom.randomString(200));
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
