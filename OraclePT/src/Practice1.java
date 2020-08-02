import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Practice1 {
	static Connection  oraCon = DBConnection.getOraConn();
	
	
	
	
	class Scenario1 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark1=? order by result desc");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	class Scenario2 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark2 < ? order by mark2");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	//mark2_mark1_idx 
	//mark1_idx
	//mark2_idx
	class Scenario3 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark2 < ? order by mark1");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	class Scenario4 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark1=? and result = ? ");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					pstmt.setString(2, OraRandom.randomYesNo());
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}	
	
	//mark1_mark2_idx
	
	class Scenario5 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark1=? and mark2 is null ");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
	class Scenario6 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark1 is null ");
				int i = 0;
				while (i < 5000000) {
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	//student_id reverse key! 
	
	class Scenario7 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select max(student_id) from students  ");
				int i = 0;
				while (i < 5000000) {
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
	//Student_id reverse key
	class Scenario8 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where student_id between ? and ? ");
				int i = 0;
				while (i < 5000000) {
					int temp = OraRandom.randomUniformInt(1000000);
					pstmt.setInt(1,temp);
					pstmt.setInt(2,temp + 100);
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}	
	
	
	
	class Scenario9 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where student_id =?");
				int i = 0;
				while (i < 5000000) {
					int temp = OraRandom.randomUniformInt(1000000);
					pstmt.setInt(1,temp);
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
	class Scenario10 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark1=? or mark2=? ");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					pstmt.setInt(2, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	class Scenario11 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark1=? and mark2=? ");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(3200));
					pstmt.setInt(2, OraRandom.randomUniformInt(3200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	class Scenario12 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select * from students where mark2 is null ");
				int i = 0;
				while (i < 5000000) {
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}	
	
	
	class Scenario13 implements Runnable{
		public void run() {
			try {
				PreparedStatement pstmt = oraCon.prepareStatement("select dept_id,max(mark1) from students where sub_id = ? group by dept_id  ");
				int i = 0;
				while (i < 5000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(200));
					ResultSet rs = pstmt.executeQuery();
					rs.setFetchSize(256);
					while (rs.next()) {
						rs.getInt(1);
					}
					i++;
					rs.close();
				}
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
	class Scenario14 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	class Scenario15 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	class Scenario16 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
	class Scenario17 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
	class Scenario18 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	class Scenario19 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	class Scenario20 implements Runnable{
		public void run() {
			try {
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
	
}
