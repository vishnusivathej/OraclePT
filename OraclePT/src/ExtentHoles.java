import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExtentHoles {
	private String tablename;
	ExtentHoles(String tablename){
		this.tablename = tablename;
	}
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select extent_id,file_id,block_id,block_id+blocks \"ENDING_BLOCK_ID\", blocks from dbA_extents where segment_name='" + tablename +"'";
				ResultSet rs = stmt.executeQuery(SQL);
				boolean firstrow = true;
				int prev = 0;
				int holes = 0;
				while (rs.next()) {
					if (firstrow) {
						prev = rs.getInt(4);
						firstrow = false;
					}
					else {
						if (rs.getInt(3) != prev) {
							holes ++;
							System.out.println("Hole at Extent_ID ==> " + rs.getInt(1));
							
						}
						prev = rs.getInt(4);
						
					}
					
				}
				System.out.println("Holes --> " + holes);
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
}