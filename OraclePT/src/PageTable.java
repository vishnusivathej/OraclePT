import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PageTable {

	void RunRangeLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new RangeScanLoader());
			i++;
		}
	}
	void RunUniqueLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new UniqueScanLoader());
			i++;
		}
	}
	
	class RangeScanLoader implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select avg(mark1) from students where mark4=?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				ResultSet rs;
				int i = 0;
				while (i <5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(6400));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class UniqueScanLoader implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				int MAXVALUE = 0;
				String SQL = "select max(student_id) from students";
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				SQL = "select * from students where student_id = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(MAXVALUE));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
