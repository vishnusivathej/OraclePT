import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CRException {

	/*
	 * Consistent reads exception
	 */
	
	void run(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new SelectLoad());
			i++;
		}
	}
	
	class SelectLoad implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select * from students where student_id < 100";
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					try {
						Thread.currentThread().sleep(5000);
					}
					catch(Exception E) {
						
					}
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
