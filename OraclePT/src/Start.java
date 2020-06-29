
public class Start {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException  {
		RandomLoad a = new RandomLoad();
		a.createTable();
		a.loadTable();
	}
	
}
