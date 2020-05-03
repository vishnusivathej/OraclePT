import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockPosition {
	HashMap<String, HashMap<String, String>>  workingset = new HashMap<String, HashMap<String, String>>();
	
	void workingSetDetails() {
		try {
			Connection oraCon = DBConnection.getOraConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select NXT_REPL, NXT_REPLAX, NXT_WRITE, NXT_WRITEAX, ADDR, CNUM_SET, SET_ID from X$KCBWDS where cnum_set> 1";
			ResultSet rs = stmt.executeQuery(SQL);
			rs.setFetchSize(10000);
			while (rs.next()) {
				HashMap<String, String> temp = new HashMap<>();
				temp.put("NXT_REPL", rs.getString("NXT_REPL"));
				temp.put("NXT_REPLAX",rs.getString("NXT_REPLAX"));
				temp.put("NXT_WRITE", rs.getString("NXT_WRITE"));
				temp.put("NXT_WRITEAX",rs.getString("NXT_WRITEAX"));
				temp.put("ADDR",rs.getString("ADDR"));
				temp.put("CNUM_SET",rs.getString("CNUM_SET"));
				workingset.put(rs.getString("SET_ID"), temp);
			}
			SQL = "select prv_repl,nxt_repl,set_ds,hladdr, tch,decode(state,0,'free',1,'xcur',2,'scur',3,'cr', 4,'read',5,'mrec',6,'irec',7,'write',8,'pi', 9,'memory',10,'mwrite',11,'donated', 12,'protected',  13,'securefile', 14,'siop',15,'recckpt', 16, 'flashfree',  17, 'flashcur', 18, 'flashna') STATE from x$bh";
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
	
	void checkBlockPosition(int file_no, int block_no) {
		
		
	}
	class Check implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraSysConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select count(*) from x$kcbwds where cnum_set > 0";
				ResultSet rs = stmt.executeQuery(SQL);
				int workingSets = 0;
				while(rs.next()) {
					workingSets = rs.getInt(1);
				}
				System.out.println("No of WorkingSets: " + workingSets);
				
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
		
	}
}
