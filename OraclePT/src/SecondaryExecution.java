import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SecondaryExecution {

	
	void run() {
		ExecutorService asd = Executors.newFixedThreadPool(2);
		int i = 0;
		while (i < 1) {
			asd.submit(new FirstStatement());
			i++;
		}
	}
	
	class FirstStatement implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				
				String SQL = "select student_id from students where mark4=20";
				String SQL2 =" select max(student_id) from students2";
				Statement stmt1 = oraCon.createStatement();
				Statement stmt2 = oraCon.createStatement();
				PreparedStatement pstmt2 = oraCon.prepareStatement("select * from students2 where student_id = ?");
				int i = 0;
				ResultSet rs2 = stmt2.executeQuery(SQL2);
				int maxvalue = 0;
				while (rs2.next()) {
					maxvalue = rs2.getInt(1);
				}
				ResultSet rs = stmt1.executeQuery(SQL);
				ResultSet rs3;
				while (rs.next()) {
					
					i++;
					if (i%5==0) {
						pstmt2.setInt(1, OraRandom.randomUniformInt(maxvalue));
						rs3 = pstmt2.executeQuery();
						while (rs3.next()) {
							Thread.currentThread().sleep(1000);
						}
					}
					
					
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
