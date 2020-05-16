import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LibraryGatherStats {
	ArrayList<String> SQL = new ArrayList<>();
	
	
	
	void run(int NO_OF_THREADS) {
		GenerateSQL();
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 1;
		while (i < NO_OF_THREADS) {
			asd.submit(new SelectLoad());
			i++;
		}
		asd.submit(new GatherStatistics());
	}
	
	void GenerateSQL() {
		int i = 0;
		String temp = " * from students where student_id = ?";		
		while (i < 300 ) {
			SQL.add("select /* " + i +"*/ " + temp);
			i++;
		}
	}
	
	class GatherStatistics implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				int i = 0;
				while (i < 10000) {
					GatherStats a = new GatherStats("STUDENTS");
					a.run();
					Thread.currentThread().sleep(1000);
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	class SelectLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				int MaxValue = 0;
				Statement stmt = oraCon.createStatement();
				String sql = "select max(student_id) from students";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					MaxValue = rs.getInt(1);
				}
				int i = 0;
				while (i < 10000000) {
					PreparedStatement pstmt = oraCon.prepareStatement(SQL.get(OraRandom.randomUniformInt(300)));
					pstmt.setInt(1, OraRandom.randomUniformInt(MaxValue));
					rs = pstmt.executeQuery();
					while(rs.next()) {
						
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
	
	
}
