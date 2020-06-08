import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Connection;
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
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt1 = oraCon.createStatement();
				String SQL = "select student_id from students where mark4=20";
				
				String SQL2 = "select avg(mark1) from students2";
				String SQL3 = "@";
				Statement stmt2 = oraCon.prepareStatement(SQL3);
				int i = 0;
				ResultSet rs2;
				Statement stmt3 = oraCon.createStatement();
				int j = 0;
				while (j < 1000000) {
					ResultSet rs = stmt1.executeQuery(SQL);
					rs.setFetchSize(4);
					while (rs.next()) {
						
						rs2 = stmt3.executeQuery(SQL2);
						while (rs2.next()) {
							
						}
						rs2.close();
						if (i%10==0) {
							stmt2.execute(SQL3);
						}
						i++;
					}
					rs.close();
					j++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
