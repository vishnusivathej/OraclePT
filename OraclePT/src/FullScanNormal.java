import java.sql.Connection;
import java.sql.Statement;

public class FullScanNormal implements Runnable{
	public void run() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			String SQL = "Select avg(mark1) from Practice4";
			Statement stmt = oraCon.createStatement();
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
