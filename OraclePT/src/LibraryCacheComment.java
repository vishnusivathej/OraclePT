import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class LibraryCacheComment {
	ArrayList<String> SQL = new ArrayList<>();
	
	
	
	void run(int NO_OF_THREADS) {
		GenerateSQL();
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 1;
		while (i < NO_OF_THREADS-3) {
			asd.submit(new SelectLoad());
			i++;
		}
		asd.submit(new Grants());
		
		//asd.submit(new AddComment());
	}
	
	void runSequence(int NO_OF_THREADS) {
		createSequence();
		int i = 1;
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		while (i < NO_OF_THREADS-3) {
			asd.submit(new sequenceLoad());
			i++;
		}

	}
	void GenerateSQL() {
		int i = 0;
		String temp = " * from students where student_id = ?";		
		while (i < 5000 ) {
			SQL.add("select /* " + i +"*/ " + temp);
			i++;
		}
	}
	
	class sequenceLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement pstmt = oraCon.createStatement();
				String SQL = "select ora.nextval from dual";
				ResultSet rs;
				int i = 0;
				while (i < 1000000) {
					rs = pstmt.executeQuery(SQL);
					while (rs.next()) {
						
					}
					rs.close();
					i++;
				}
				pstmt.close();
				oraCon.close();
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	void createSequence() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			String SQL = "drop sequence ora";
			Statement stmt = oraCon.createStatement();
			try {
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			SQL = "create sequence ora start with 1 increment by 1  cache 10";
			stmt.execute(SQL);
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	class Grants implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				int i = 0;
				while (i < 10000) {
					stmt.execute("grant all on vishnu.students to jahnavi");
					System.out.println("granting and revoking");
					Thread.currentThread().sleep(5000);
					stmt.execute("revoke all on vishnu.students from jahnavi");
					Thread.currentThread().sleep(5000);
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	class StatsManipulation implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				int i = 0;
				while (i < 1000) {
					System.out.println("manipulating Statistics");
					new GatherStats("STUDENTS").DeleteTableStats();
					Thread.currentThread().sleep(3000);
					new GatherStats("STUDENTS").GatherStatsInvalidate();
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	class SelectLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				int MaxValue = 0;
				Statement stmt = oraCon.createStatement();
				String sql = "select max(student_id) from students";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					MaxValue = rs.getInt(1);
				}
				int i = 0;
				while (i < 10000000) {
					PreparedStatement pstmt = oraCon.prepareStatement(SQL.get(OraRandom.randomUniformInt(300)));
					pstmt.setInt(1, OraRandom.randomUniformInt(MaxValue));
					rs = pstmt.executeQuery();
					while(rs.next()) {
						
					}
					pstmt.close();
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	void PartitionAddtionScenario() {
		createPartitionTable();
		ExecutorService asd = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 10) {
			asd.submit(new insertPartitionTable());

			i++;
			
		}
	}
	void createPartitionTable() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			
			String SQL = "create table temp (roll number, roll2 number) partition by range (roll2) interval (10) (partition p1 values less than (1))";
			stmt.execute(SQL);
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	class insertPartitionTable implements Runnable{
		public void run() {
			try {
				System.out.println("Starting Insert Load");
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("insert into temp(roll, roll2) values (?,?)");
				int i = 0;
				while (i < 10000) {
					pstmt.setInt(1, oraSequence.nextVal());
					pstmt.setInt(2, OraRandom.randomUniformInt(100000));
					pstmt.executeUpdate();
					i++;
				}
				pstmt.close();
				oraCon.close();
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
