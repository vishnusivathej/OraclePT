import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PageTable {

	
	class Loader implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				String SQL = "select avg(mark1) from students where mark4=?";
				PreparedStatement pstmt = oraCon.prepareStatement(SQL);
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
