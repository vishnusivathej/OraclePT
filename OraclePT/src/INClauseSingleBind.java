import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class INClauseSingleBind implements Runnable{
	public void run() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select max(student_id) from students";
			ResultSet rs = stmt.executeQuery(SQL);
			int MAXVALUE = 0, i = 0 ;
			while (rs.next()) {
				MAXVALUE = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			SQL = "select * From students where student_id in (select regexp_substr(?,'[^,]+', 1, level) from dual connect BY regexp_substr(?,'[^,]+', 1, level) is not null)";
			PreparedStatement pstmt = oraCon.prepareStatement(SQL);
			while (i < 1000000) {
				String temp = "";
				int NO_OF_BINDS = OraRandom.randomUniformInt(100);
				NO_OF_BINDS = NO_OF_BINDS<3?3:NO_OF_BINDS;
				
				for (int l = 1 ; l <= NO_OF_BINDS; l++) {
					temp  = temp + Integer.toString(OraRandom.randomUniformInt(MAXVALUE)) + ",";
				}
				temp = temp.substring(0, temp.length() - 1);
				pstmt.setString(1, temp);
				pstmt.setString(2, temp);
				rs = pstmt.executeQuery();
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
