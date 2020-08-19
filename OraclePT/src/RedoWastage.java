import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class RedoWastage {
	
	void run() {
		createTable();
		ExecutorService asd = Executors.newFixedThreadPool(20);
		int i = 0;
		while (i < 5) {
			asd.submit(new insertData());
			i++;
		}
	}
		
	void createTable() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			try {
				String SQL = "drop table redowastage";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			String SQL = "create table redowastage(roll number, name varchar2(30))";
			stmt.execute(SQL);
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	class insertData implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("insert into redowastage(roll, name) values (?,?)");
				int i = 0;
				while (i < 400) {
					pstmt.setInt(1, oraSequence.nextVal());
					pstmt.setString(2, OraRandom.randomString(30));
					pstmt.executeUpdate(); 
					if (i%1000==0) {
						System.out.println("Inserted " + i + "  rows --> " + Thread.currentThread().getName());
					}
					i++;
				}
				pstmt.close();
				oraCon.close();
				System.out.println("Load Complete");
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
