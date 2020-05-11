import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomLoad {
	@SuppressWarnings("static-access")
	void loadTable() throws InterruptedException {
		ExecutorService asd = Executors.newFixedThreadPool(30);
		int i = 0;
		while (i < 10) {
			asd.submit(new InsertLoad());
			i++;
		}
		i = 0 ;
		System.out.println("Loading Data... Sleepin for 10 seconds");
		asd.shutdown();
		while(!asd.isShutdown()) {
			Thread.currentThread().sleep(1000);
		}
		asd.shutdownNow();
		asd = Executors.newFixedThreadPool(30);
		
	}
	void updateLoad() {
		ExecutorService asd = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 4) {
			asd.submit(new UpdateLoad());
			i++;
		}
		i = 0;
		while (i < 5) {
			asd.submit(new Update2());
			i++;
		}
		
	}
	void selectLoad() {
		ExecutorService asd = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 10) {
			asd.submit(new SelectLoad());
			i++;
		}
	}
	void MixedLoad() {
		
	}
	
	
	void createTable() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			try {
				String SQL = "drop table students";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			String SQL = "  create table students(student_id number primary key, dept_id number, name varchar2(30) not null, sub_id number, day date, mark1 number, mark2 number, mark3 number, mark4 number)";
			stmt.execute(SQL);
		//	SQL = "create index name on students(name)";
		//	stmt.execute(SQL);
			System.out.println("Created Tables and indexes, Starting Load");
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void PageTable() {
		
	}
	class InsertLoad implements Runnable{
		public void run() {
			try {
				System.out.println("Staring Insert Thread -->" + Thread.currentThread().getName());
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("insert into students (student_id, dept_id, name, sub_id,  day, mark1, mark2, mark3, mark4) values (?,?,?,?,to_date(trunc(dbms_random.value(2458485,2458849)),'J'),?,?,?,?)");
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1 , oraSequence.nextVal());
					pstmt.setInt(2, OraRandom.randomUniformInt(100));
					pstmt.setString(3, OraRandom.randomString(30));
					pstmt.setInt(4, OraRandom.randomUniformInt(200));
					pstmt.setInt(5,  OraRandom.randomUniformInt(1600));
					pstmt.setInt(6,  OraRandom.randomUniformInt(3200));
					pstmt.setInt(7, OraRandom.randomUniformInt(4800));
					pstmt.setInt(8,  OraRandom.randomUniformInt(6400));
					//pstmt.executeUpdate();
					pstmt.addBatch();
					if (i%1000 == 0) {
						pstmt.executeBatch();
						System.out.println("loaded " + oraSequence.getval());
					}
					i++;
				}
				pstmt.close();
				oraCon.close();
				  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				   LocalDateTime now = LocalDateTime.now();  
				   System.out.println(dtf.format(now));  
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		
	}
	class UpdateLoad implements Runnable{
		public void run() {
			try {
				System.out.println("Staring Update Thread -->" + Thread.currentThread().getName());
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("Update students set mark1 = ? where mark1 = ? ");
				String SQL = "select max(student_id) from students";
				Statement stmt = oraCon.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				int maxvalue= 0;
				while(rs.next()) {
					maxvalue = rs.getInt(1);
				}
				System.out.println(maxvalue);
				rs.close();
				stmt.close();
				int i = 0;
				while (i < 4000000) {
					pstmt.setInt(2 , 3200);
					pstmt.setInt(1, OraRandom.randomSkewInt(100));
					pstmt.executeUpdate();
					i++;
					
				}
				pstmt.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class Update2 implements Runnable{
		public void run() {
			try {
				System.out.println("Staring Update Thread -->" + Thread.currentThread().getName());
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("Update students set mark1 = ? where mark1 = ? ");
				String SQL = "select max(student_id) from students";
				Statement stmt = oraCon.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				int maxvalue= 0;
				while(rs.next()) {
					maxvalue = rs.getInt(1);
				}
				System.out.println(maxvalue);
				rs.close();
				stmt.close();
				int i = 0;
				while (i < 4000000) {
					pstmt.setInt(2 , 3200);
					pstmt.setInt(1, OraRandom.randomSkewInt(100));
					pstmt.executeUpdate();
					i++;
					
				}
				pstmt.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class SelectLoad implements Runnable{
		public void run() {
			try {
				System.out.println("Staring Select  Thread -->" + Thread.currentThread().getName());
				Connection oraCon = DBConnection.getOraConn();
			
				PreparedStatement pstmt = oraCon.prepareStatement("select avg(mark1) from  test where mark4 = ?");
				pstmt.setFetchSize(50000);
				int i = 0;
				while (true) {
					while (i < 1000000) {
						pstmt.setInt(1, OraRandom.randomUniformInt(100));
						ResultSet rs = pstmt.executeQuery();
						while(rs.next()) {
							rs.getInt(1);
						}
						i++;
						rs.close();
					}
				}
	
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	
	
	
	class DeleteLoad implements Runnable{
		public void run() {
			try {
				System.out.println("Staring Delete Thread -->" + Thread.currentThread().getName());
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("delete from RandomLoad where roll = ? ");
				String SQL = "select max(roll) from randomload";
				Statement stmt = oraCon.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				int maxvalue= 0;
				while(rs.next()) {
					maxvalue = rs.getInt(1);
				}
				rs.close();
				stmt.close();
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1 , OraRandom.randomUniformInt(maxvalue));
					pstmt.executeUpdate();
					i++;
				}
				pstmt.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		
	}
}
