import java.sql.*;
import java.util.ArrayList;

public class OpenCursor {
	
	void run() {
		Thread a = new Thread(new Load());
		a.run();
	}
	
	class Load implements Runnable{
		public void run() {
			try {
				ArrayList<Object> a = new ArrayList<>();
				Connection oraCon = DBConnection.getOraConn();
				int i = 0;
				while (i < 10000) {
					Statement stmt = oraCon.createStatement();
					String SQL = "select * from students where mark4 = 123";
					
					ResultSet rs ;
					rs = stmt.executeQuery(SQL);
				//	a.add(rs);
					rs.next();
					rs.close();
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
