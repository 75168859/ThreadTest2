package cn.itcast.heima2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 子线程循环10次，接着主线程循环100，
 * 接着又回到子线程循环10次，接着再回到主线程又循环100次，如此循环50次
 *
 *要用到共同数据（包括同步锁）的若干个方法应该归在同一个类身上，
 *这种设计正好体现了高类聚和程序的健壮性
 *
 *
 */
public class TraditionalThreadCommunication {
	
	
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
			public void run() {
				
				
				for(int i = 1; i <= 50; i++){
					business.sub(i);
				}
			}
		}).start();
		
		
		for(int i = 1; i <= 50; i++){
			business.main(i);
		}
		
	}
}

/**
 * 锁是放在代表要操作的资源的类的内部方法中
 * 而不是线程代码中
 *
 */
class Business{
	private boolean bShouldSub = true;
	public synchronized void sub(int i ){
		while(!bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		for(int j = 1; j <= 10; j++){
			System.out.println(" sub thread sequence of .." + j + "  loop of " + i);
		}
		bShouldSub = false;
		this.notify();
	}
	
	public synchronized void main(int i ){
		while(bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j = 1; j <= 100; j++){
			System.out.println(" main thread sequence of .." + j  + "  loop of " + i);
		}
		bShouldSub = true;
		this.notify();
	}
	
}


