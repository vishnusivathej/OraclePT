import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ParseFailure {

	
	void runLoad(int NO_OF_THREADS) {

		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new parseFailure());
			i++;
		}
	
	}

	class parseFailure implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL  = "select * from parsefailure where t1 = ?";
				int i = 0 ;
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				ResultSet rs;
				while (i < 1000000) {
					pstmt.setInt(1, OraRandom.randomUniformInt(1000000));
					try {
						rs = pstmt.executeQuery();
						while (rs.next()) {
							
						}
						rs.close();
					}
					catch(Exception E) {
						
					}
					i++;
				}
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	
	

}
