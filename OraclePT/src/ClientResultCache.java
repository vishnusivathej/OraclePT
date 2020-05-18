import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientResultCache {
	
	void run() {
		CreateTable();
		ExecutorService asd = Executors.newFixedThreadPool(30);
		int i = 0;
		while (i < 30) {
			asd.submit(new RunLoad());
			i++;
		}
	}
	
	class RunLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select /*+ result_cache */ * from clientcache where t1 = ?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				int i = 0;
				while (i < 10000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(100));
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						
					}
					rs.close();
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
			String SQL = "create table clientcache( t1 number primary key, t2 varchar2(20), t3 varchar2(20)) ";
			stmt.execute(SQL);
			SQL = "insert into clientcache (t1, t2 ,t3) values (?,?,?)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 1;
			while (i < 1000000) {
				pstmt.setInt(1, oraSequence.nextVal());
				pstmt.setString(2, OraRandom.randomString(20));
				pstmt.setString(3, OraRandom.randomString(20));
				pstmt.addBatch();
				if (i%10000 == 0) {
					pstmt.executeBatch();
				}
				i++;
			}
			pstmt.executeBatch();
			pstmt.close();
			stmt.close();
			GatherStats a = new GatherStats("CLIENTCACHE");
			a.run();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
}
