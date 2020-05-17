import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.sql.Connection;
import java.sql.PreparedStatement;

class EnqTXITL{
	
	void run(int NO_OF_THREADS) {
		CreateTable();
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 1;
		while (i < NO_OF_THREADS) {
			asd.submit(new UpdateLoad());
			i++;
		}
	}
	
	
	class UpdateLoad implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "update students set mark1 = ? where roll = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(1000));
					pstmt.setInt(2, OraRandom.randomUniformInt(370));
					
					pstmt.executeUpdate();
					
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	void CreateTable() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL;
			try {
				SQL = "drop table temp";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			try {
				SQL = "drop table students";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			SQL = "Create table temp(roll number, dept_id number, mark1 number, mark2 number, mark3 number) pctfree 0";
			stmt.execute(SQL);
			SQL = "insert into temp(roll, dept_id, mark1, mark2, mark3) values (?,?,?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 0;
			while (i < 1000000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setInt(2, OraRandom.randomUniformInt(100));
				pstmt.setInt(3, OraRandom.randomUniformInt(100));
				pstmt.setInt(4, OraRandom.randomUniformInt(100));
				pstmt.setInt(5, OraRandom.randomUniformInt(100));
				pstmt.addBatch();
				if (i%10000 == 0) {
					pstmt.executeBatch();
				}
				i++;
			}
			pstmt.executeBatch();
			pstmt.close();
			SQL = "create table students pctfree 0 as select * From temp order by roll";
			stmt.execute(SQL);
			SQL = "create index students_idx on students(roll)";
			stmt.execute(SQL);
			SQL = "alter table students pctfree 10";
			stmt.execute(SQL);
			GatherStats a = new GatherStats("STUDENTS");
			a.run();
			SQL = "drop table temp";
			stmt.execute(SQL);
			stmt.close();
			oraCon.close();
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
	
}