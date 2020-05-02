import java.sql.Statement;
import java.sql.Connection;

public class FullScanSessParamChange implements Runnable{
	public void run() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "alter session set db_file_multiblock_read_count = 31";
			stmt.execute(SQL);
			SQL = "select avg(mark1) from students";
			int i = 0;
			while (i < 100000) {
				stmt.executeQuery(SQL);
				i++;
			}
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
}
