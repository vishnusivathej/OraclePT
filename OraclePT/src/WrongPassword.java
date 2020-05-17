import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WrongPassword {
	
	
	void runLoad(int NO_OF_THREADS) {

		ExecutorService asd = Executors.newFixedThreadPool(NO_OF_THREADS);
		int i = 0;
		while (i < NO_OF_THREADS) {
			asd.submit(new wrongPassword());
			i++;
		}
	
	}
	
	
	class wrongPassword implements Runnable{
		public void run() {
			try {
				int i = 0;
				while (i < 100000) {
					try {
						Connection oraCon = DBConnection.getFailedCon();
					}
					catch(Exception E) {
						
					}
					i++;
				}
				
				
			}
			catch(Exception E) {
				//E.printStackTrace();
			}
		}
	}
}
