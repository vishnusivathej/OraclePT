import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MinMax {

	void run(int NO_OF_THREADS) {
		try {
			setMaxValue();
			deleteValue();
			ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
			asd.submit(new LoadTable());
			int i = 0;
			while (i < NO_OF_THREADS) {
				asd.submit(new MinLoad());
				asd.submit(new MaxLoad());
				i++;
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}		
	}
	
	class MinLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select min(student_id) from students";
				Statement stmt = oraCon.createStatement();
				int i = 0 ;
				ResultSet rs;
				while (i < 100000) {
					rs = stmt.executeQuery(SQL);
					while (rs.next()) {
						
					}
				}
				stmt.close();
				oraCon.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	
	class MaxLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select max(student_id) from students";
				Statement stmt = oraCon.createStatement();
				int i = 0 ;
				ResultSet rs;
				while (i < 100000) {
					rs = stmt.executeQuery(SQL);
					while (rs.next()) {
						
					}
				}
				stmt.close();
				oraCon.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	void setMaxValue() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select max(student_id) from students";
			ResultSet rs = stmt.executeQuery(SQL);
			int MAXVALUE  = 0;
			while(rs.next()) {
				MAXVALUE = rs.getInt(1);
			}
			oraSequence.setVal(MAXVALUE+1000);
			rs.close();
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void deleteValue() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "delete from students where student_id < (select min(student_id) - 100000 from students)";
			stmt.executeUpdate(SQL);
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	class LoadTable implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("insert into students (student_id, dept_id, name, sub_id,  day, mark1, mark2, mark3, mark4) values (?,?,?,?,to_date(trunc(dbms_random.value(2458485,2458849)),'J'),?,?,?,?)");
				int i = 0;
				while (i < 30099900) {
					pstmt.setInt(1 , oraSequence.nextVal());
					pstmt.setInt(2, OraRandom.randomUniformInt(100));
					pstmt.setString(3, OraRandom.randomString(30));
					pstmt.setInt(4, OraRandom.randomUniformInt(200));
					pstmt.setInt(5,  OraRandom.randomUniformInt(800));
					pstmt.setInt(6,  OraRandom.randomUniformInt(1600));
					pstmt.setInt(7, OraRandom.randomUniformInt(3200));
					pstmt.setInt(8,  OraRandom.randomUniformInt(6400));
					pstmt.addBatch();
					if (i%100 == 0) {
						pstmt.executeBatch();
					}
					i++;
				}
				pstmt.close();
				oraCon.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
