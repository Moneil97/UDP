
public class SimpleTimer {

	long start;
	private long interval;
	
	public SimpleTimer(boolean b) {
		if (b) start = now();
	}

	private long now() {
		return System.nanoTime();
	}
	
//	public void setInterval(int sec){
//		interval = sec * 1000000000;
//	}
	
	public void setInterval(double sec){
		interval = (long) (sec * 1000000000);
	}
	
	public boolean isReady(){
		return (now() - start > interval);
	}

	public void reset() {
		start = now();
	}
	
	

}
