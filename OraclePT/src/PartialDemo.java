import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PartialDemo {
	
	void RunFull(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new FULLEXEC());
			i++;
		}
	}

	void RunPartial(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new Partial());
			i++;
		}
	}
	class Partial implements Runnable{
		public void run() {
			try {
				Connection a = DBConnection.getOraConn();
				String SQL = "select * from students where mark3 = ?";
				PreparedStatement pstmt = a.prepareStatement(SQL);
				
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					int j = 0;
					while (rs.next()) {
						j++;
						if (j%30 == 0) {
							break;
						}
					
					}
					i++;
				}
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class FULLEXEC implements Runnable{
		public void run() {
			try {
				Connection a = DBConnection.getOraConn();
				String SQL = "select * from students where mark3 = ?";
				PreparedStatement pstmt = a.prepareStatement(SQL);
				
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
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
