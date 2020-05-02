import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Simulator {
	
	
	void inClauseMultipleBinds(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new INClauseMultipleBinds());
			i++;
		}
	}
	
	void intraBlock(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new IntraBlock());
			i++;
		}
	}
	
	void inClauseSingleBind(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new INClauseSingleBind());
			i++;
		}
	}
	
	
	
	void commitClean(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new CleanCommitCleanout());
			i++;
		}
	}
	
	void FullScanCommitCleanOut(int NO_OF_THREADS) {
		try {
			Connection oraCon = DBConnection.getOraConn();
			oraCon.setAutoCommit(false);
			Statement stmt = oraCon.createStatement();
			stmt.execute("Alter session force parallel dml parallel 8");
			String SQL = "update students set mark1 = 0 where student_id > (select max(student_id) - 500000 from students)";
			stmt.executeUpdate(SQL);
			stmt.execute("Alter system flush buffer_cache");
			fullScanNormal(NO_OF_THREADS);
			oraCon.commit();

		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	void FullScanUncommited(int NO_OF_THREADS) {
		try {
			Connection oraCon = DBConnection.getOraConn();
			oraCon.setAutoCommit(false);
			Statement stmt = oraCon.createStatement();
			stmt.execute("Alter session force parallel dml parallel 8");
			String SQL = "update students set mark1 = 0 where student_id > (select max(student_id) - 500000 from students)";
			stmt.executeUpdate(SQL);
			stmt.execute("Alter system flush buffer_cache");
			fullScanNormal(NO_OF_THREADS);
			Thread.currentThread().sleep(600000);
			oraCon.commit();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
	
	void fullScanNormal(int NO_OF_THREADS) {
		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new FullScanNormal());
			i++;
		}
	}
	
}
