import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FetchSIZE {
	public void run(int NO_OF_THREADS) {
		try {
			ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
			int i = 0;
			while (i < NO_OF_THREADS/6) {
				asd.submit(new FetchDEFAULT());
				asd.submit(new Fetch20());
				asd.submit(new Fetch40());
				asd.submit(new Fetch80());
				asd.submit(new Fetch160());
				asd.submit(new FetchIrrelevant());
				i++;
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	class FetchDEFAULT implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select /*DEFAULT_FETCH_SIZE*/ * from students where mark3 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
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
	class Fetch20 implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select /*20_FETCH_SIZE*/ * from students where mark3 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(20);
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
	class Fetch40 implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select /*40_FETCH_SIZE*/ * from students where mark3 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(40);
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
	class Fetch80 implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select /*80_FETCH_SIZE*/ * from students where mark3 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(80);
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
	class Fetch160 implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select /*160_FETCH_SIZE*/ * from students where mark3 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(160);
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
	class FetchIrrelevant implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select /*FETCH_IRRELEVANT*/ min(student_id) from students where mark3 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0 ;
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
