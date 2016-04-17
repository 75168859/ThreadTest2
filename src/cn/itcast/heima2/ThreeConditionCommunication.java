package cn.itcast.heima2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 子线程循环10次，接着主线程循环100，
 * 接着又回到子线程循环10次，接着再回到主线程又循环100次，如此循环50次
 *
 *1.Condition 的功能类似在传统线程技术中的Object wait 和Object notify的功能。
 *在等待condition时，允许发生“虚假唤醒”，这通常作为基础平台语义的让步。对于大多数
 *应用程序，这带来的实际影响很小，因为Condition应该总是在一个循环中被等待，
 *并测试正被等待的状态声明。某个实现可以随意移除可能的虚假唤醒，但建议应用程序程序员总是假定
 *这些虚假唤醒可能发生，因此总是在一个循环中等待。
 *
 *2.一个锁内部可以有多个Condition，即有多路等待和通知，可以参看jdk1.5提供的Lock与Condition实现的可阻塞队列
 *应用案例，从中除了要体味算法，还要体味面向对象的封装。在传统的线程机制中一个监视器对象上只能有一路等待和通知
 *要想实现多路等待和通知，必须嵌套使用多个不同监视器对象。（如果只用一个Condition，
 *两个放的都在等，一旦一个放的进去了，那么它通知可能导致另一个放接着往下走）
 *
 *
 */
public class ThreeConditionCommunication {
	
	
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
			public void run() {
				for(int i = 1; i <= 50; i++){
					business.sub2(i);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				for(int i = 1; i <= 50; i++){
					business.sub3(i);
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
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		private int bShouldSub = 1;
		public void sub2(int i ){
			lock.lock();
			try{
				while(bShouldSub != 2){
					try {
						condition2.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				for(int j = 1; j <= 10; j++){
					System.out.println(" sub thread sequence of .." + j + "  loop of " + i);
				}
				bShouldSub = 3;
				condition3.signal();
			}finally{
				lock.unlock();
			}
			
		}
		
		public void sub3(int i ){
			lock.lock();
			try{
				while(bShouldSub != 3){
					try {
						condition3.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				for(int j = 1; j <= 20; j++){
					System.out.println(" sub thread sequence of .." + j + "  loop of " + i);
				}
				bShouldSub = 1;
				condition1.signal();
			}finally{
				lock.unlock();
			}
			
		}
		
		public void main(int i ){
			lock.lock();
			try{
				while(bShouldSub != 1){
					try {
						condition1.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for(int j = 1; j <= 100; j++){
					System.out.println(" main thread sequence of .." + j  + "  loop of " + i);
				}
				bShouldSub = 2;
				condition2.signal();
			}finally{
				lock.unlock();
			}
		}
		
		
		
	}
	
}




