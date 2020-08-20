import java.sql.CallableStatement;
import java.sql.Connection;

public class GatherStats {
	String tabName;
	GatherStats(String table){
		this.tabName = table;
	}
	void GatherStatsInvalidate() {

		try {
			Connection oraCon = DBConnection.getOraConn();
			CallableStatement cstmt = oraCon.prepareCall ("{call dbms_stats.gather_table_stats('VISHNU',?,cascade=>true, estimate_percent=>1, method_opt=>'FOR ALL COLUMNS SIZE 1', no_invalidate=>false)}");
			cstmt.setString(1, tabName);
			cstmt.executeUpdate();
			 cstmt = oraCon.prepareCall ("{call dbms_stats.gather_table_stats('VISHNU',?,cascade=>true, estimate_percent=>1, method_opt=>'FOR ALL COLUMNS SIZE AUTO', no_invalidate=>false)}");
				cstmt.setString(1, tabName);
				cstmt.executeUpdate();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	void DeleteTableStats() {

		try {
			Connection oraCon = DBConnection.getOraConn();
			CallableStatement cstmt = oraCon.prepareCall ("{call dbms_stats.delete_table_stats('VISHNU',?)}");
			cstmt.setString(1, tabName);
			cstmt.executeUpdate();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
}
