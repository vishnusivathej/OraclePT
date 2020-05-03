import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class Test {
	HashMap<String,HashMap<String,String>> Buffers = new HashMap<>();
	HashMap<String,HashMap<String,String>> ActualDetails = new HashMap<>();
	
	void findBuffer(int file_id, int block_no) {
		buildMap();
		
	}
	void buildMap() {
		try {
			
			Connection oraCon = DBConnection.getOraSysConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select obj, dbarfil, dbablk,prv_repl,nxt_repl,set_ds,hladdr,  tch,decode(state,0,'free',1,'xcur',2,'scur',3,'cr', 4,'read',5,'mrec',6,'irec',7,'write',8,'pi', 9,'memory',10,'mwrite',11,'donated', 12,'protected',  13,'securefile', 14,'siop',15,'recckpt', 16, 'flashfree',  17, 'flashcur', 18, 'flashna') STATE from x$bh";
			ResultSet rs = stmt.executeQuery(SQL);
			rs.setFetchSize(500000);
			while (rs.next()) {
				if (Buffers.get(rs.getString("SET_DS")) == null){
					HashMap<String, String> temp = new HashMap<>();
					Buffers.put(rs.getString("SET_DS"), temp);
				}
				else {
					Buffers.get(rs.getString("SET_DS")).put(rs.getString("PRV_REPL"), rs.getString("NXT_REPL"));
				}
				HashMap<String, String> temp = new HashMap<>();
				temp.put("OBJ", rs.getString("OBJ"));
				temp.put("DBARFIL", rs.getString("DBARFIL"));
				temp.put("DBABLK", rs.getString("DBABLK"));
				temp.put("HLADDR", rs.getString("HLADDR"));
				temp.put("STATE", rs.getString("STATE"));
				temp.put("TCH", rs.getString("TCH"));
				ActualDetails.put(rs.getString("PRV_REPL"), temp);
			}
			SQL = "select NXT_REPL, prv_repl, addr,COLD_HD from x$kcbwds where cnum_set > 0";
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				int i = 0;
				System.out.println("Parsing CHAIN OF WORKING SET   --> " + rs.getString("ADDR"));
				System.out.println("Displaying HotSection");
				HashMap<String,String> workingSet = Buffers.get(rs.getString("ADDR"));
				String Pointer = rs.getString("NXT_REPL");
				while (Pointer != null) {
					System.out.println(Pointer + " ---> " + workingSet.get(Pointer));
					Pointer = workingSet.get(Pointer);
					i++;
				}
				System.out.println("Buffers in the HostSection ---> " + workingSet.size() + " ---> " + (i -2));
				System.out.println("Displaying ColdSection!"); 
				Pointer = rs.getString("COLD_HD");
				while (Pointer != null) {
					System.out.println(Pointer + " ---> " + workingSet.get(Pointer));
					Pointer = workingSet.get(Pointer);
					i++;
				}
				System.out.println("Buffers in the ColdSection  ---> " + workingSet.size() + " ---> " + (i -2));
			}
			
			rs.close();
			stmt.close();
			oraCon.close();
			
		}
		catch (Exception E) {
			E.printStackTrace();
		}
	}
}
