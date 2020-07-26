
public class Start {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException  {
		EnqTXITL a = new EnqTXITL();
		a.CreateTable();
		a.run(30);
	}
	
}
