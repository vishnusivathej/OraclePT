import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class IntraBlock implements Runnable {

	public void run() {
		try {
			ArrayList<Integer> predicate = new ArrayList<>();
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select t0 from chained_rows_list";
			int MAXVALUE = 0;
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				predicate.add(rs.getInt(1));
			}
			rs.close();
			SQL = "select max(t0) from intrablock";
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				MAXVALUE = rs.getInt(1);
			}
			SQL = "select t350 from intrablock where t0 = ?";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			int i = 0 ;
			while (i < 10000000) {
				if (i%2 == 0 ) {
					pstmt.setInt(1, predicate.get(OraRandom.randomUniformInt(predicate.size() - 1)));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
				}
				else {
					pstmt.setInt(1, OraRandom.randomUniformInt(MAXVALUE));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						
					}
				}
				
				i++;
			}
			
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}

}
