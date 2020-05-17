
public class oraSequence {
	static volatile int value = 0;
	synchronized static int nextVal() {
		value++;
		return value;
	}
	synchronized static int getval() {
		return value;
	}
	synchronized static void setVal(int val) {
		value = val + 100000;
	}
	synchronized static void reset() {
		value  = 0;
	}
}
