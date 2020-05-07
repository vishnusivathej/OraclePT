import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
	HashMap<String, HashMap<String,String>> Buffers  = new HashMap<>();
	HashMap<String,String> BufferLinks = new HashMap<>();
	HashMap<String,HashSet<String>> Hotlist = new HashMap<>();
	HashMap<String,HashSet<String>> AuxList = new HashMap<>();
	HashMap<String,HashSet<String>> ColdList = new HashMap<>();
	HashMap<String, HashMap<String,String>> WriteList = new HashMap<>();
	void Check() {
		buildMap();
		test();
		for (String s: Buffers.keySet()) {
			System.out.println("Buffers in Working Set --> " + s + " ---> " +Buffers.get(s).size());
			System.out.println("HostList --> " + Hotlist.get(s).size());
			System.out.println("ColdList --> " + ColdList.get(s).size());
			}
			
			
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
					temp.put(rs.getString("PRV_REPL"), rs.getString("NXT_REPL"));
					Buffers.put(rs.getString("SET_DS"), temp);
				}
				else {
					Buffers.get(rs.getString("SET_DS")).put(rs.getString("PRV_REPL"), rs.getString("NXT_REPL"));
				}
			}
			rs.close();
			stmt.close();
			oraCon.close();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void test() {
		try {
			Connection oraCon = DBConnection.getOraSysConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select nxt_repl, nxt_replax,nxt_write, cold_hd, addr from x$kcbwds where cnum_set > 0";
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String temp = rs.getString("NXT_REPL");
				HashSet<String> temp1 = new HashSet<>();
				temp1.add(rs.getString("NXT_REPL"));
				System.out.println("MAIN LIST --> " + rs.getString("ADDR") );
				while (temp!=null) {
				//	System.out.println(temp  + " ---> " + Buffers.get(rs.getString("ADDR")).get(temp));
					temp =  Buffers.get(rs.getString("ADDR")).get(temp);
					temp1.add(temp);
				}
				Hotlist.put(rs.getString("ADDR"), temp1);
				temp = rs.getString("COLD_HD");
				temp1 = new HashSet<>();
				temp1.add(rs.getString("COLD_HD"));
				System.out.println("COLD LIST --> " + rs.getString("ADDR") );
				while (temp!=null) {
				//	System.out.println(temp  + " ---> " + Buffers.get(rs.getString("ADDR")).get(temp));
					temp =  Buffers.get(rs.getString("ADDR")).get(temp);
					temp1.add(temp);
				}
				ColdList.put(rs.getString("ADDR"), temp1);
			}
			
			
			
			
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void buildRegions() {
		try {
			Connection oraCon = DBConnection.getOraSysConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select nxt_repl, nxt_replax,nxt_write, cold_hd, addr from x$kcbwds where cnum_set > 0";
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				
				//HotList.put(rs.getString("ADDR"), rs.getString("NXT_REPL"));
				
			}
			
			
			
			for (String s: Buffers.keySet()) {
				
			}
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	

	
}
