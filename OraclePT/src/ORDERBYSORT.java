import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ORDERBYSORT {
	
	/* Following Indexes should be CREATED!
	STUDENTS_PK
	MARK3_IDX
	DEPT_SUB_MARK4_IDX
	MARK3_STUDENT_IDX
	 */
	
	void run(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new QueryEquality());
			i++;
		}
	}

	class QueryRange implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				Statement stmt = oraCon.createStatement();
				ResultSet rs = stmt.executeQuery("select max(student_id) from students");
				int MAXVALUE = 0;
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				rs.close();
				stmt.close();
				int i = 0;
				ArrayList<String> sortcolumns = new ArrayList<>();
				sortcolumns.add("NAME");
				sortcolumns.add("DEPT_ID");
				sortcolumns.add("SUB_ID");
				sortcolumns.add("MARK1");
				sortcolumns.add("MARK2");
				sortcolumns.add("MARK3");
				sortcolumns.add("MARK4");
				
				ArrayList<String> Predicates = new ArrayList<>();
				Predicates.add("STUDENT_ID");
				Predicates.add("MARK3");
				Predicates.add("DEPT_ID");
				Predicates.add("SUB_ID");
				
				
				while (i < 10000000) {
					StringBuilder SQL = new StringBuilder("select * from students where ");
					PreparedStatement pstmt;
					String temp = Predicates.get(OraRandom.randomUniformInt(3));
					if (temp.equals("DEPT_ID")) {
						SQL.append(temp + " = ? and sub_id = ? and mark4 between ? and ? ORDER BY " + sortcolumns.get(OraRandom.randomUniformInt(6)) +", " +sortcolumns.get(OraRandom.randomUniformInt(6)) );
						pstmt = oraCon.prepareStatement(SQL.toString());
						pstmt.setInt(1, OraRandom.randomUniformInt(100));
						pstmt.setInt(2, OraRandom.randomUniformInt(200));
						int k = OraRandom.randomUniformInt(3200);
						pstmt.setInt(3, k);
						pstmt.setInt(4, k+10);
					}
					else if (temp.equals("MARK3")) {
						SQL.append(temp +" between ? and ? ORDER BY "+ sortcolumns.get(OraRandom.randomUniformInt(6)) );
						pstmt = oraCon.prepareStatement(SQL.toString());
						int k = OraRandom.randomUniformInt(3200);
						pstmt.setInt(1, k);
						pstmt.setInt(2, k + 10);
					}
					else if (temp.equals("SUB_ID")) {
						SQL.append(temp +" between ? and ? and MARK4 = ? ORDER BY " + sortcolumns.get(OraRandom.randomUniformInt(6)));
						pstmt = oraCon.prepareStatement(SQL.toString());
						int k = OraRandom.randomUniformInt(3200);
						pstmt.setInt(1, k);
						pstmt.setInt(2, k + 10);
						pstmt.setInt(3, OraRandom.randomUniformInt(3200));
					}
					else {
						SQL.append(temp + " between ? and ?  order by "+ sortcolumns.get(OraRandom.randomUniformInt(6)) ) ;
						pstmt = oraCon.prepareStatement(SQL.toString());
						int k = OraRandom.randomUniformInt(MAXVALUE);
						pstmt.setInt(1, k);
						pstmt.setInt(2, k + 10);
					}
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					i++;
					rs.close();
					pstmt.close();
				}
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	
		
	}
	class QueryEquality implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				Statement stmt = oraCon.createStatement();
				ResultSet rs = stmt.executeQuery("select max(student_id) from students");
				int MAXVALUE = 0;
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				rs.close();
				stmt.close();
				int i = 0;
				ArrayList<String> sortcolumns = new ArrayList<>();
				sortcolumns.add("NAME");
				sortcolumns.add("DEPT_ID");
				sortcolumns.add("SUB_ID");
				sortcolumns.add("MARK1");
				sortcolumns.add("MARK2");
				sortcolumns.add("MARK3");
				sortcolumns.add("MARK4");
				
				ArrayList<String> Predicates = new ArrayList<>();
				Predicates.add("STUDENT_ID");
				Predicates.add("MARK3");
				Predicates.add("DEPT_ID");
				Predicates.add("SUB_ID");
				
				
				while (i < 10000000) {
					StringBuilder SQL = new StringBuilder("select * from students where ");
					PreparedStatement pstmt;
					String temp = Predicates.get(OraRandom.randomUniformInt(3));
					if (temp.equals("DEPT_ID")) {
						SQL.append(temp + " = ? and sub_id = ? and mark4 = ? ORDER BY " + sortcolumns.get(OraRandom.randomUniformInt(6)) +", " +sortcolumns.get(OraRandom.randomUniformInt(6)) );
						pstmt = oraCon.prepareStatement(SQL.toString());
						pstmt.setInt(1, OraRandom.randomUniformInt(100));
						pstmt.setInt(2, OraRandom.randomUniformInt(200));
						pstmt.setInt(3, OraRandom.randomUniformInt(3200));
					}
					else if (temp.equals("MARK3")) {
						SQL.append(temp +" = ? ORDER BY "+ sortcolumns.get(OraRandom.randomUniformInt(6)) );
						pstmt = oraCon.prepareStatement(SQL.toString());
						pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					}
					else if (temp.equals("SUB_ID")) {
						SQL.append(temp +" = ? and MARK4 = ? ORDER BY " + sortcolumns.get(OraRandom.randomUniformInt(6)));
						pstmt = oraCon.prepareStatement(SQL.toString());
						pstmt.setInt(1, OraRandom.randomUniformInt(200));
						pstmt.setInt(2, OraRandom.randomUniformInt(3200));
					}
					else {
						SQL.append(temp + " = ? order by "+ sortcolumns.get(OraRandom.randomUniformInt(6)) ) ;
						pstmt = oraCon.prepareStatement(SQL.toString());
						pstmt.setInt(1, OraRandom.randomUniformInt(MAXVALUE));	
					}
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					i++;
					rs.close();
					pstmt.close();
				}
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
}
