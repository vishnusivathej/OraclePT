import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtils;

public class Graphs {
	public static void main(String[] args) {
		
		SQL_REPORT("0sbbcuruzd66f");
	}
	
	 static Connection getOraSysConn() {
         try {
                 Class.forName("oracle.jdbc.driver.OracleDriver");
                 return DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.20:1521:nfsdb1","vishnu","oracle");
         }
         catch(Exception E) {
                 if (E.toString().contains("ClassNotFoundException")) {
                         System.out.println("Java Driver not found");
                         System.out.println("Please Download Oracle JDBC driver(ojdbc6|11<what ever>.jar) and place in $JRE_HOME/lib/ext");
                 }
                 else {
                 	E.printStackTrace();
                 }
                 return null;
         }
	 }
	 
	 static void SQL_REPORT(String SQL_ID) {
		 try {
			 Connection oraCon = getOraSysConn();
			 Statement stmt = oraCon.createStatement();
			 String SQL = "select sql_id,s.snap_id,sum(s.EXECUTIONS_DELTA) executions,"
			 		+ "round(sum(s.DISK_READS_DELTA)/sum(s.EXECUTIONS_DELTA)) dreads_perx,"
			 		+ "sum(s.BUFFER_GETS_DELTA)/sum(s.EXECUTIONS_DELTA) bgets_perx,"
			 		+ "sum(s.CPU_TIME_DELTA)/sum(s.EXECUTIONS_DELTA) cpu_perx,"
			 		+ "sum(s.ELAPSED_TIME_DELTA)/sum(s.EXECUTIONS_DELTA) elap_perx,"
			 		+ "sum(s.IOWAIT_DELTA)/sum(s.EXECUTIONS_DELTA) iowait_perx,  "
			 		+ "sum(s.sorts_delta)/sum(s.EXECUTIONS_DELTA) iowait_perx,  "
			 		+ "sum(s.ROWS_PROCESSED_DELTA)/sum(s.EXECUTIONS_DELTA) iowait_perx, "
			 		+ "sum(s.FETCHES_DELTA)/sum(s.EXECUTIONS_DELTA) iowait_perx   from dba_hist_snapshot n,dba_hist_sqlstat s  where  s.snap_id=n.snap_id  and s.sql_id='"+SQL_ID+"' and n.begin_interval_time>=trunc(sysdate-3) group by sql_id,s.snap_id";
			 ResultSet rs = stmt.executeQuery(SQL);
			 XYSeries Executions = new XYSeries(SQL_ID);
			 XYSeries DiskReads = new XYSeries(SQL_ID);
			 XYSeries BufferGets = new XYSeries(SQL_ID);
			 XYSeries CpuTime = new XYSeries(SQL_ID);
			 XYSeries ElapsedTime = new XYSeries(SQL_ID);
			 XYSeries IoWaitTime = new XYSeries(SQL_ID);
			 XYSeries Sorts = new XYSeries(SQL_ID);
			 XYSeries RowsProcessed = new XYSeries(SQL_ID);
			 XYSeries Fetches = new XYSeries(SQL_ID);
			 
			 while(rs.next()) {
				 Executions.add(rs.getInt(2), rs.getInt(3));
				 DiskReads.add(rs.getInt(2), rs.getInt(4));
				 BufferGets.add(rs.getInt(2), rs.getInt(5));
				 CpuTime.add(rs.getInt(2), rs.getInt(6));
				 ElapsedTime.add(rs.getInt(2), rs.getInt(7));
				 IoWaitTime.add(rs.getInt(2), rs.getInt(8));
				 Sorts.add(rs.getInt(2), rs.getInt(9));
				 RowsProcessed.add(rs.getInt(2), rs.getInt(10));
				 Fetches.add(rs.getInt(2), rs.getInt(11)); 
			 }
			 generateGraph(SQL_ID, "EXECUTIONS", Executions);
			 generateGraph(SQL_ID, "DISK_READS", DiskReads);
			 generateGraph(SQL_ID, "BUFFER_GETS", BufferGets);
			 generateGraph(SQL_ID, "CPU_TIME", CpuTime);
			 generateGraph(SQL_ID, "ELAPSED_TIME", ElapsedTime);
			 generateGraph(SQL_ID, "IOWAIT_TIME", IoWaitTime);
			 generateGraph(SQL_ID, "ROWS_PROCESSED", Sorts);
			 generateGraph(SQL_ID, "FETCHES", RowsProcessed);
		 }
		 catch(Exception E) {
			 E.printStackTrace();
		 }
	 }
	 
	 static void generateGraph(String SQL_ID, String statistic, XYSeries Series) {
		 try {
			 XYSeriesCollection coll = new XYSeriesCollection();
			 coll.addSeries(Series);
			 JFreeChart barChart = ChartFactory.createXYLineChart(statistic+"_PER_X", "SNAP_ID", statistic, coll);
			 barChart.getPlot().setBackgroundPaint(Color.WHITE);
			 barChart.setBackgroundPaint(Color.WHITE);
			 File BarChart = new File( "/tmp/"+statistic+".jpeg" ); 
			 ChartUtils.saveChartAsJPEG( BarChart , barChart , 640 , 480 );
		 }
		 catch(Exception E) {
			 E.printStackTrace();
		 }
	 }
	 void topExec() {
		try {
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void genChart(String SQL) {
		try {
			
		}
		catch(Exception E) {
			
		}
	}
}
