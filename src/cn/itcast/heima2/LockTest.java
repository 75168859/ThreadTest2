package cn.itcast.heima2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 与类  TraditionalThreadCommunication 做比较
 */

/**
 * 1.Lock比传统线程模型中 synchronized方式更加面向对象，与生活中的锁类似
 * 锁本身也应该是一个对象。两个线程执行的代码片段要实现同步互斥的效果
 * 他们必须用同一个Lock对象。锁是上再代表要操作的资源的类的内部方法中
 * 而不是线程代码中
 *
 *2.读写锁，分为读锁和写锁，多个读锁不互斥，读锁与写锁互斥
 *写锁与写锁互斥，这是又JVM自己控制的，你只要上好相应地锁即可
 *如果你的代码只读数据，可以很多人同时读，但不能同时写那就上读锁；
 *如果你的代码修改数据，只能有一个人在写，且不能同时读取，那就上写锁。
 *总之，读的时候上读锁，写的时候上写锁
 *
 */
public class LockTest {
	
	public static void main(String[] args) {
		new LockTest().init();
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
		Lock lock = new ReentrantLock();
		
		public void output(String name){
			int len = name.length();
			lock.lock();
			try{
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}finally{
				lock.unlock();
				
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
