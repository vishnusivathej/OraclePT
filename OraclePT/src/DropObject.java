import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DropObject {
	
	volatile ArrayList<String> a = new ArrayList<String>();
	
	void run() {
		tablenames();
		ExecutorService asd = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 10) {
			asd.submit(new dropTable());
			i++;
		}
	}
	
	
	synchronized String readvalue() {
		int temp = a.size();
		String temp2 = a.get(OraRandom.randomUniformInt(temp));
		a.remove(temp2);
		return temp2;
	}
	
	void tablenames() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "Select table_name from dba_tables where table_name not like 'BIN%' and owner='VISHNU'";
			ResultSet rs = stmt.executeQuery(SQL);
			rs.setFetchSize(10000);
			while (rs.next()) {
				a.add(rs.getString(1));
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	class dropTable implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "drop table vishnu.";
				int i = 0;
				while (i < 10000) {
					String SQL2 = SQL + readvalue();
					stmt.execute(SQL2);
					i++;
				}
				stmt.close();
				oraCon.close();
				
				
			}
			catch(Exception E) {
				
			}
		}
	}
	
}
