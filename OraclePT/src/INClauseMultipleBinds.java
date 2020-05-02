import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class INClauseMultipleBinds implements Runnable{
	public void run() {
		try {
			int MaxValue = 0, i = 0 ;
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select max(student_id) from students";
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				MaxValue = rs.getInt(1);
			}
			rs.close();
			stmt.close();
				while (i < 1000000) {
					StringBuilder PSQL = new StringBuilder("Select distinct dept_id from students where student_id in (");
					int NO_OF_BINDS = OraRandom.randomUniformInt(100);
					NO_OF_BINDS = NO_OF_BINDS<3?3:NO_OF_BINDS;
					for (int temp = 1 ; temp <= NO_OF_BINDS; temp++) {
						PSQL.append("?,");
					}
					PSQL.deleteCharAt(PSQL.length() - 1);
					PSQL.append(")");
					PreparedStatement PSTMT = oraCon.prepareStatement(PSQL.toString());
					for (int temp = 1 ; temp <= NO_OF_BINDS; temp++) {
						PSTMT.setInt(temp, OraRandom.randomUniformInt(MaxValue));	
					}
					rs = PSTMT.executeQuery();
					while (rs.next()) {
					}
					rs.close();
					PSTMT.close();
					i++;
				}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
}
