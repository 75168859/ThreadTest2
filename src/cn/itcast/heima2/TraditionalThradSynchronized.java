package cn.itcast.heima2;

/**
 * 
 *线程的互斥
 */
public class TraditionalThradSynchronized {
	
	public static void main(String[] args) {
		new TraditionalThradSynchronized().init();
	}
	
	private void init(){
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output("zhangxiaoxiang");
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output("lihuoming");
				}
			}
		}).start();
	}
	
	static class Outputer{
		public void output(String name){
			int len = name.length();
			synchronized(this){
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		//非静态方法的锁是 this
		public synchronized void output2(String name){
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		//静态方法的锁是  类的字节码对象
		public static synchronized void output3(String name){
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
	
}
