package cn.itcast.heima2;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 子线程循环10次，接着主线程循环100，
 * 接着又回到子线程循环10次，接着再回到主线程又循环100次，如此循环50次
 *
 *要用到共同数据（包括同步锁）的若干个方法应该归在同一个类身上，
 *这种设计正好体现了高类聚和程序的健壮性
 *
 *
 *1.用两个具有1个空间的队列来实现同步通知的功能
 *2.阻塞队列与Semaphore有些相似，但也不同，阻塞队列是一方存放数据，另一方释放数据，
 *Semaphore 通常则是由同一方设置和释放信号量
 */
public class BlockingQueueCommunication {
	
	
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
	
	
	

/**
 * 锁是放在代表要操作的资源的类的内部方法中
 * 而不是线程代码中
 *
 */
	static class Business{
		
		
		BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
		BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
		
		{//匿名构造方法，运行时间在所有的构造方法之前
			
			
			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void sub(int i ){
			try {
				queue1.put(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			for(int j = 1; j <= 10; j++){
				System.out.println(" sub thread sequence of .." + j + "  loop of " + i);
			}
			
			try {
				queue2.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		public void main(int i ){
			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int j = 1; j <= 100; j++){
				System.out.println(" main thread sequence of .." + j  + "  loop of " + i);
			}
			
			try {
				queue1.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}



