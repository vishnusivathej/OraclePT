import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HashAgg {

	void runHashAgg(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new HashLoad());
			i++;
		}
	}
	void runHashAggFilter(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new HashLoadFilter());
			i++;
		}
	}
	class HashLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select dept_id,sub_id,count(*) from students group by dept_id,sub_id";
				int i = 0;
				ResultSet rs;
				
				while (i < 10000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
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
	class HashLoadFilter implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select dept_id,sub_id,count(*) from students where sub_id = 123 group by dept_id,sub_id";
				int i = 0;
				ResultSet rs;
				
				while (i < 10000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
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
