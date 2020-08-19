import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SessionCachedCursors {
	ArrayList<String> SQLs = new ArrayList<>();
	
	
	void run() {
		buildSQLs();
		ExecutorService asd = Executors.newFixedThreadPool(30);
		int i = 0;
		while (i < 20) {
			asd.submit(new runLoad());
			i++;
		}
	}
	
	
	void buildSQLs() {
		String head = "select /*";
		String tail = " */ * from students where student_id = ?";
		for (int i = 0 ; i < 600; i++) {
			String SQL = head + Integer.toBinaryString(i) + tail;
			SQLs.add(SQL);
		}
	}
	class runLoad implements Runnable{
		public void run() {
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
				Connection oraCon = DBConnection.getOraConn();
				int i = 0;
				while (i < 100000) {
					PreparedStatement pstmt = oraCon.prepareStatement(SQLs.get(OraRandom.randomUniformInt(SQLs.size()-1)));
					pstmt.setInt(1, OraRandom.randomUniformInt(100000));
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
					rs.close();
					pstmt.close();
					
					i++;
				}
				oraCon.close();
				 dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				    now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
