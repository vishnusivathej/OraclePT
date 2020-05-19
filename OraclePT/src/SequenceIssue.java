import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Connection;
import java.sql.ResultSet;

public class SequenceIssue {

	void run(int NO_OF_THREADS) {
		createSequence();
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new GenerateLoad());
			i++;
		}
		
	}
	
	class GenerateLoad implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select test.nextval from dual";
				Statement stmt = oraCon.createStatement();
				int i = 0;
				while (i < 10000000) {
					ResultSet rs = stmt.executeQuery(SQL);
					
					i++;
					
				}
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	void createSequence() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL;
			try {
				SQL = "drop sequence test";
				stmt.execute(SQL);
			}
			catch(Exception E) {
				
			}
			SQL = "create sequence test start with 1 increment by 1 cache 10";
			stmt.execute(SQL);
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
}
