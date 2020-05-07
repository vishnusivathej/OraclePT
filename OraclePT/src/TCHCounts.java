import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCHCounts {
	void RunLoad(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new UniqueScanLoader());
			i++;
		}
	}
	
	class UniqueScanLoader implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select avg(mark1) from students where student_id =?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				ResultSet rs;
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(10000000));
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
