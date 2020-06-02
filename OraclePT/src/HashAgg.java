import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HashAgg {

	void runHashAgg(int NO_OF_THREADS) {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now));  
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new HashLoad());
			i++;
		}
	}
	void runHashAggFilter(int NO_OF_THREADS) {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now));  
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new sortUnique());
			i++;
		}
	}
	
	class sortUnique implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select /*+USE_HASH_AGGREGATION*/ distinct student_id from students where mark4=25 order by student_id";
				int i = 0;
				ResultSet rs;
				
				while (i < 30000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
					while (rs.next()) {
						
					}
					i++;
				}
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	
	}
	class hashUnique implements Runnable{

		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select distinct student_id from students where mark4=25 ";
				int i = 0;
				ResultSet rs;
				
				while (i < 30000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
					while (rs.next()) {
						
					}
					
					i++;
				}
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	
	}
	class HashLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select /*+USE_HASH_AGGREGATION*/ dept_id,sub_id, mark4,count(*) From students group by dept_id,sub_id,mark4 order by dept_id,sub_id,mark4";
				int i = 0;
				ResultSet rs;
				
				while (i < 10000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
					while (rs.next()) {
						
					}
					
					i++;
				}
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class sortGroupbyNOSORT implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select /*+INDEX_ASC(STUDENTS,DEPT_SUB_IDX)*/ dept_id,sub_id,count(*) from students group by dept_id,sub_id order by dept_id";
				int i = 0;
				ResultSet rs;
				
				while (i < 10000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
					while (rs.next()) {
						
					}
					
					i++;
				}
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class sortGroupby implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select  dept_id,sub_id,count(*) from students group by dept_id,sub_id order by dept_id";
				int i = 0;
				ResultSet rs;
				
				while (i < 10000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
					while (rs.next()) {
						
					}
					
					i++;
				}
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class HashAggLoadFilter implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select /*+USE_HASH_AGGREGATION*/ dept_id,sub_id,count(*) from students where dept_id = 10 group by dept_id,sub_id";
				int i = 0;
				ResultSet rs;
				
				while (i < 10000) {
					rs = stmt.executeQuery(SQL);
					rs.setFetchSize(40000);
					while (rs.next()) {
						
					}
					
					i++;
				}
				 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
}
