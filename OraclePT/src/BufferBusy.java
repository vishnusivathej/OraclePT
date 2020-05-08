import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BufferBusy {
	
	
	
	
	void combined() {
		createDraco();
		ExecutorService asd = Executors.newFixedThreadPool(30);
		int i = 0;
		while (i < 10) {
			asd.submit(new UpdateLoad());
			asd.submit(new InsertReverseKey());
			asd.submit(new SelectLoad());
			i++;
		}
	}
	
	class SelectLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				System.out.println("Starting Select load");
				String SQL = "select * from draco where order_id = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1,oraSequence.getval());
					pstmt.executeUpdate();
				}
				System.out.println("Select load Complete");
			}
			catch(Exception E) {
				E.printStackTrace();
			}
			
		}
	}
	
	void insertUpdate() {
		try {
			createDraco();
			ExecutorService asd = Executors.newFixedThreadPool(30);
			int i = 0;
			while (i < 30) {
				asd.submit(new UpdateLoad());
				asd.submit(new InsertReverseKey());
				i++;
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
		
	}
	class UpdateLoad implements Runnable{
		public void run() {
			try {

				Connection oraCon = DBConnection.getOraConn();
				System.out.println("Starting update load");
				String SQL = "update draco set State = 'COMPLETE' where order_id=?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1,oraSequence.getval()-10);
					pstmt.executeUpdate();
				}
				System.out.println("update load Complete");
			
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	void createDraco() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL;
			try {
				 SQL = "drop table draco";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				E.printStackTrace();
			}
			SQL = "create table Draco (order_id number, state varchar2(30), region varchar2(4), order_amount number)";
			
			stmt.execute(SQL);
			SQL = "create Unique index draco_pk on draco(order_id,state)";
			stmt.execute(SQL);
			SQL = "alter table draco allocate extent (size 1g)";
			stmt.execute(SQL);
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void insertReversekey() {
		try {
			createDraco();
			ExecutorService asd = Executors.newFixedThreadPool(30);
			int i = 0;
			while (i < 30) {
				asd.submit(new InsertReverseKey());
				i++;
			}
		
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	class InsertReverseKey implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				System.out.println("Starting Insert load");
				String SQL = "insert into draco (order_id , state , region , order_amount ) values (?,?,?,?)";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1,oraSequence.nextVal());
					pstmt.setString(2,  OraRandom.randomString(30));
					pstmt.setString(3,  OraRandom.randomString(4));
					pstmt.setInt(4,  OraRandom.randomUniformInt(100));
					pstmt.executeUpdate();
				}
				System.out.println("Insert load Complete");
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		
	}
}
