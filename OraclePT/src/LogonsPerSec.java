import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogonsPerSec {

	
	void run() {
		ExecutorService asd = Executors.newFixedThreadPool(100);
		int i = 0;
		while (i < 10) {
			asd.submit(new Login());
			i++;
		}
	}
	
	class Login implements Runnable{
		public void run() {
			try {
				while (true) {
					Connection oraCon = DBConnection.getOraConn();
					oraCon.close();
					System.out.println("Connection release");
				}
				
			}
			catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
