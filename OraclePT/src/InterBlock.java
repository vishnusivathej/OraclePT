import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class InterBlock  {
	
	void run(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new Extra());
			asd.submit(new NoExtra());
			i++;
		}
	}
	
	class Extra implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				int MAXVALUE = 0;
				String SQL = "select max(roll) from classic_chaining";
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				SQL = "select c4 from classic_chaining where roll = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 10000000){
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
	
	class NoExtra implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				int MAXVALUE = 0;
				String SQL = "select max(roll) from classic_chaining";
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					MAXVALUE = rs.getInt(1);
				}
				SQL = "select c1 from classic_chaining where roll = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 10000000){
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
