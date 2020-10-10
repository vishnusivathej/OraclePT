import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BufferCache {
	
	
	
	void bufferCBCtest() {
		
	}
	
	void bufferPinTest() {
		ExecutorService asd = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 20) {
			asd.submit(new BufferPinTest());
		}
	}
	
	void findBlockPost(int file, int block) {
		try {
			Connection oraCon = DBConnection.getOraSysConn();
			Statement stmt = oraCon.createStatement();
			String SQL = "select NXT_REPL,NXT_REPLAX,COLD_HD From X$KCBWDS where CNUM_SET > 1";
			ResultSet Rs = stmt.executeQuery(SQL);
			String MainQueueStart = null;
			String AuxQueueStart = null;
			String MidPoint;
			while (Rs.next()) {
				MainQueueStart = Rs.getString(1);
				AuxQueueStart = Rs.getString(2);
				MidPoint = Rs.getString(3);
			}
			HashMap<String,String> MainQueue  = new HashMap<String,String>();
			HashMap<String,String> wholeQueue = new HashMap<>();
			System.out.println("printing Blocks current States");
			SQL = "select prv_repl,nxt_repl, hladdr, tch,decode(state,0,'free',1,'xcur',2,'scur',3,'cr', 4,'read',5,'mrec',6,'irec',7,'write',8,'pi', 9,'memory',10,'mwrite',11,'donated', 12,'protected',  13,'securefile', 14,'siop',15,'recckpt', 16, 'flashfree',  17, 'flashcur', 18, 'flashna') STATE from x$bh where DBABLK="+block +" and DBARFIL=" + file;
			Rs = stmt.executeQuery(SQL);
			while (Rs.next()) {
				System.out.println("PREV==>" + Rs.getString(1) + " NXT==>" + Rs.getString(2) + "  LATCH==>" + Rs.getString(3) + " TCH_COUNT==>" + Rs.getInt(4) + " State==>" + Rs.getString(5));
			}
			System.out.println("Getting Current Position ");
			SQL = "select nxt_repl from x$bh where DBABLK=" + block + " and DBARFIL=" + file  + " and state = 1";
			Rs = stmt.executeQuery(SQL);
			String BLKPOS="" ;
			while (Rs.next()) {
				BLKPOS = Rs.getString(1);
			}
			System.out.println("Current pointer --> " + BLKPOS);
			SQL="select prv_repl,nxt_repl from x$bh";
			Rs = stmt.executeQuery(SQL);
			Rs.setFetchSize(100000);
			while (Rs.next()) {
				wholeQueue.put(Rs.getString(1),Rs.getString(2));
			}
			System.out.println("Total Blocks in WorkingSet " + wholeQueue.size() );
		//	
			int pos = 0;
			String temp = MainQueueStart;
			while (temp!=wholeQueue.get(temp)) {
				pos++;
				if (temp.equals(BLKPOS)) {
					System.out.println("Position in the Main LRU workingSet " + pos);
					break;
				}
				temp = wholeQueue.get(temp);
			}
			
			System.out.println("Aux Queue Check... ");
			pos = 0;
			temp = AuxQueueStart;
			while (temp!=wholeQueue.get(temp)) {
				pos ++ ;
				if (temp.equals(BLKPOS)) {
					System.out.println("Position in the AUX LRU workingSet " + pos);
					break;
				}
				temp = wholeQueue.get(temp);
			}
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
	
	
	class BufferPinTest implements Runnable{
		public void run() {
			try {
				Connection oraCon = DBConnection.getOraConn();
				Statement stmt = oraCon.createStatement();
				String SQL = "select * from temp where rowid='AAASLLAAHAAAAGvACM'";
				while (true) {
					ResultSet rs = stmt.executeQuery(SQL);
					while (rs.next()) {
						rs.getInt(1);
					}
				}
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
	
	void checkWorkingSet() {


		String lruChains= "select NXT_REPL,NXT_REPLAX,NXT_WRITE,NXT_WRITEAX,cold_hd from x$kcbwds where CNUM_SET>1";
		String BufferCache="select prv_repl,nxt_repl,tch,decode(state,0,'free',1,'xcur',2,'scur',3,'cr', 4,'read',5,'mrec',6,'irec',7,'write',8,'pi', 9,'memory',10,'mwrite',11,'donated', 12,'protected',  13,'securefile', 14,'siop',15,'recckpt', 16, 'flashfree',  17, 'flashcur', 18, 'flashna') STATE from x$bh";

		try {
			Connection oraCon = DBConnection.getOraSysConn();
			Statement stmt = oraCon.createStatement();
			ResultSet rs = stmt.executeQuery(BufferCache);
			rs.setFetchSize(100000);
			Statement stmt2 = oraCon.createStatement();
			ResultSet rs2 = stmt2.executeQuery(lruChains);
			rs2.setFetchSize(100000);
			String MidPoint ;
			HashMap<String,String> mainlruqueue ;
			HashMap<String,String> auxlruqueue;
			HashMap<String,String> wmainlruqueue ;
			HashMap<String,String> wauxlruqueue;
			int MidPoint_position = 0;
			HashMap<String,String> wholeQueue = new HashMap<>();
			HashMap<String,Integer> tchcounts = new HashMap<>();
			HashMap<String,String> bufstates = new HashMap<>();
			HashMap<String,Integer> mainstates = new HashMap<>();
			HashMap<String,Integer> auxstates = new HashMap<>();
			HashMap<String,Integer> wmainstates = new HashMap<>();
			HashMap<String,Integer> wauxstates = new HashMap<>();
			int i = 0;
			while (rs.next()) {
				wholeQueue.put(rs.getString(1),rs.getString(2));
				bufstates.put(rs.getString(1), rs.getString(4));
				tchcounts.put(rs.getString(1), rs.getInt(3));
				i++;
			}
			System.out.println("Got Details");
			HashMap<String,Integer> totalbufagg= new HashMap<>();
			for (String temp : bufstates.keySet()) {
				int temp1 = totalbufagg.get(bufstates.get(temp))==null?0:totalbufagg.get(bufstates.get(temp));
				totalbufagg.put(bufstates.get(temp),1+temp1);
			}
			System.out.println("Total Buffers in Buffer cache --> " + i);
			System.out.print ("Total Buffer States in Buffer cache:"+ " States ");
			for (String temp : totalbufagg.keySet() ) {
				System.out.print("   " +temp + " --> " + totalbufagg.get(temp));
			}
			System.out.println("");
			while (rs2.next()) {
				mainlruqueue = new HashMap<>();
				auxlruqueue = new HashMap<>();
				wmainlruqueue = new HashMap<>();
				wauxlruqueue = new HashMap<>();
				String mainptr = rs2.getString(1);
				String auxptr = rs2.getString(2);
				String wmainptr = rs2.getString(3);
				String wauxptr = rs2.getString(4);
				MidPoint = rs2.getString(5);
				//System.out.print(MidPoint);
				while (mainptr!=wholeQueue.get(mainptr)) {
					if (MidPoint.equals(mainptr)) {
						MidPoint_position = mainlruqueue.size();
					}
					mainlruqueue.put(mainptr, wholeQueue.get(mainptr));
					mainptr = wholeQueue.get(mainptr);
					
				}
				while (auxptr!=wholeQueue.get(auxptr)) {
					auxlruqueue.put(auxptr, wholeQueue.get(auxptr));
					auxptr = wholeQueue.get(auxptr);
				}
				
				while (wmainptr!=wholeQueue.get(wmainptr)) {
					wmainlruqueue.put(wmainptr, wholeQueue.get(wmainptr));
					wmainptr = wholeQueue.get(wmainptr);
				}
				while (wauxptr!=wholeQueue.get(wauxptr)) {
					wauxlruqueue.put(wauxptr, wholeQueue.get(wauxptr));
					wauxptr = wholeQueue.get(wauxptr);
				}
				
				System.out.print("Total Buffers in LRU --> " + mainlruqueue.keySet().size() + " Midpoint Position --> " + MidPoint_position + " States ");
				for (String temp : mainlruqueue.keySet()) {
					int temp1 = mainstates.get(bufstates.get(temp))==null?0:mainstates.get(bufstates.get(temp));
					mainstates.put(bufstates.get(temp),1+temp1);
				}
				for (String temp : mainstates.keySet() ) {
					System.out.print("  " +temp + " --> " + mainstates.get(temp));
				}
				System.out.println("");
				System.out.print("Total Buffers in AUX LRU --> " + auxlruqueue.keySet().size()+ " States ");
				for (String temp : auxlruqueue.keySet()) {
					int temp1 = auxstates.get(bufstates.get(temp))==null?0:auxstates.get(bufstates.get(temp));
					auxstates.put(bufstates.get(temp),1+temp1);
				}
				for (String temp : auxstates.keySet() ) {
					System.out.print("  " +temp + " --> " + auxstates.get(temp));
				}
				
				
				
				System.out.println("");
				System.out.print("Total Buffers in WLRU --> " + wmainlruqueue.keySet().size() + " States ");
				for (String temp : wmainlruqueue.keySet()) {
					int temp1 = wmainstates.get(bufstates.get(temp))==null?0:wmainstates.get(bufstates.get(temp));
					wmainstates.put(bufstates.get(temp),1+temp1);
				}
				for (String temp : wmainstates.keySet() ) {
					System.out.print("  " +temp + " --> " + wmainstates.get(temp));
				}
				System.out.println("");
				System.out.print("Total Buffers in WAUX LRU --> " + wauxlruqueue.keySet().size()+ " States ");
				for (String temp : wauxlruqueue.keySet()) {
					int temp1 = wauxstates.get(bufstates.get(temp))==null?0:wauxstates.get(bufstates.get(temp));
					wauxstates.put(bufstates.get(temp),1+temp1);
				}
				for (String temp : wauxstates.keySet() ) {
					System.out.print("  " +temp + " --> " + wauxstates.get(temp));
				}
				
			}
			rs.close();
			rs2.close();
			stmt.close();
			stmt2.close();
			oraCon.close();
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	
	
	}
}
