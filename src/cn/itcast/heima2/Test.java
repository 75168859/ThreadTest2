package cn.itcast.heima2;
/**
 * 
 *
 */
public class Test {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t = new ThreadTest();
		t.start();
		
		Thread t2 = new Thread(new ThreadRunTest());
		t2.start();
	}
}

class ThreadTest extends Thread{
	@Override
	public void run() {
		super.run();
	}
}

class ThreadRunTest implements Runnable{
	public void run() {
		
	}
} 





