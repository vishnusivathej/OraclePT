import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BB {
	ArrayList<Integer> student_ids = new ArrayList<>();
	
	void runLoad() {
		//GenStudentIDs();
		ExecutorService asd = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 50) {
		//	asd.submit(new SelectLoad());
			asd.submit(new UpdateLoad());
			
			i++;
		}
	}
	
	class SelectLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where student_id = ?");
				int i = 0;
				ResultSet rs;
				while (i < 10000000) {
					pstmt.setInt(1, student_ids.get(OraRandom.randomUniformInt(student_ids.size()-1)));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						rs.getInt(1);
					}
					
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	class UpdateLoad implements Runnable{
		@SuppressWarnings("static-access")
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				oraCon.setAutoCommit(false);
				PreparedStatement pstmt = oraCon.prepareStatement("update students set mark1= ? where mark4 = ?");
				int i = 0;
				ResultSet rs;
				while (i < 10000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(100));
					pstmt.setInt(2,  OraRandom.randomUniformInt(100) //student_ids.get(OraRandom.randomUniformInt(student_ids.size()-1))
							);
					pstmt.executeUpdate();
					i++;
				//	Thread.currentThread().sleep(10);
					oraCon.commit();
					
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	void GenStudentIDs() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			String SQL = "select student_id from students where dbms_rowid.rowid_block_number(rowid) in (select dbms_rowid.rowid_block_number(rowid) From students where student_id in (1,5000))";
			Statement stmt = oraCon.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				student_ids.add(rs.getInt(1));
			}
			rs.close();
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
}
