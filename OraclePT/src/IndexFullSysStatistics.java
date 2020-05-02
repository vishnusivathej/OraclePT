import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*+
 * Make sure that the full table scan objects are stored in slow filesystem or mediocre IO
 */
public class IndexFullSysStatistics {

	void fullScanLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS+3);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new FullScan());
			i++;
		}
		asd.submit(new IndexScan());
		
	}
	void indexLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS+3);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new IndexScan());
			i++;
		}
		asd.submit(new FullScan());
	}
	
	class FullScan implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select avg(mark1) from students";
				Statement stmt = oraCon.createStatement();
				int i = 0 ;
				while (i < 1000000) {
					ResultSet rs = stmt.executeQuery(SQL);
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
	
	
	class IndexScan implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select avg(mark1) from students where mark4 = ? and dept_id = 0";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(6400));
					ResultSet rs = pstmt.executeQuery();
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
