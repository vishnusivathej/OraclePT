import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CBC2 {

	
	void CBCLoad() {
		ExecutorService asd = Executors.newFixedThreadPool(20);
		int i = 0;
		while (i < 16) {
			asd.submit(new SelectLoadGeneric());
			i++;
		}
		i = 0 ;
		while (i < 5) {
			asd.submit(new updateLoadGeneric());
			i++;
		}
	}
	
	@SuppressWarnings("static-access")
	void CBCLoadSingle() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			String SQL = "select max(student_id) from students";
			Statement stmt = oraCon.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			int maxval = 0;
			while (rs.next()) {
				maxval  = rs.getInt(1);
				
			}
			rs.close();
			stmt.close();
			oraCon.close();
			int randomVal = OraRandom.randomUniformInt(maxval);
			ExecutorService asd = Executors.newFixedThreadPool(20);
			int i = 0;
			while (i < 16) {
				asd.submit(new SelectLoadSingle(randomVal));
				Thread.currentThread().sleep(100);
				i++;
			}
			i = 0 ;
			while (i < 2) {
				asd.submit(new updateLoadSingle(randomVal));
				i++;
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
		
	}
	
	class updateLoadGeneric implements Runnable{
		public void run() {
			try {
				ArrayList<Integer> values = new ArrayList<>();
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "update students2 set mark1 = ? where mark4= ? ";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 10000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(100));
					pstmt.setInt(2, OraRandom.randomUniformInt(6400));
					pstmt.addBatch();
					if (i %100 == 0) {
						pstmt.executeBatch();
					}
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		
	}
	
	class SelectLoadGeneric implements Runnable{
		public void run() {
			try {
				System.out.println("Starting Thread" + Thread.currentThread().getName());
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement stmt = oraCon.prepareStatement("select avg(mark1) from students where mark4 = ?");
				System.out.println("Acquired Connection");
				int i = 0;
				ResultSet rs;
				while (i < 1000000) {
					stmt.setInt(1, OraRandom.randomUniformInt(6400));
					rs = stmt.executeQuery();
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	class SelectLoadSingle implements Runnable{
		int val;
		SelectLoadSingle(int a){
			this.val = a;
		}
		public void run() {
			try {
				System.out.println("Starting Thread" + Thread.currentThread().getName());

				Connection oraCon = DBConnection.getOraConn();
				System.out.println("Acquired Connection");

				String SQL = "select * from students where student_id = " + val;
				Statement stmt = oraCon.createStatement();
				int i = 0;
				ResultSet rs;
				while (i < 10000000) {
					rs = stmt.executeQuery(SQL);
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
	class updateLoadSingle implements Runnable{
		int val;
		updateLoadSingle(int a ){
			this.val = a;
		}
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "update students set mark2 = ? where student_id = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 10000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					pstmt.setInt(2, val);
					pstmt.addBatch();
					if (i %100 == 0) {
						pstmt.executeBatch();
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

