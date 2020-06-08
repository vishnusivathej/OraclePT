import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PGAMultipleCursors {

	void run(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new Load());
			i++;
		}
	}
	
	class Load implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				ArrayList<ResultSet> a = new ArrayList<>();
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select dept_id,sub_id,avg(mark2) from students where mark4=123 group by dept_id,sub_id";
				int i = 0;
				while (i < 500) {
					Statement stmt = oraCon.createStatement();
					ResultSet rs = stmt.executeQuery(SQL);
					rs.next();
					a.add(rs);
					i++;
				}
				Thread.currentThread().sleep(100000);
				

			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
