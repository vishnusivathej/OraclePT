

public class Start {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException  {
		holes();
	}
	static void holes() {
		ExtentHoles a = new ExtentHoles("STUDENTS");
		a.run();
	}
	static void load() {
		try {
			RandomLoad a = new RandomLoad();
			a.createTable();
			a.loadTable();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
}