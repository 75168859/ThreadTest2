package cn.itcast.heima2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		//固定线程池
//		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		//缓存线程池,任务执行不过来，自动增加新的线程
//		ExecutorService threadPool = Executors.newCachedThreadPool();
		//一个线程，好处，如果池子中的线程死了，会自动增加一个线程  （可以解决：如何实现线程死掉后重新启动）
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		for(int i = 1; i <= 10; i++){
			final int task = i;
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					for(int j = 1; j <= 10; j++){
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task " + task);
					}
				}
			});
		}
		
		System.out.println(" all of 10 task have committed!!");
//		threadPool.shutdown();//任务干完后关闭线程
//		threadPool.shutdownNow();//任务没执行完毕也关闭
		
		//定时器的线程池
		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("bombing !!!");
			}
		},
				10,//多长时间后执行,10秒后执行
				TimeUnit.SECONDS);
		//每隔多长时间执行一次，以固定饿频率执行
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("bombing!!!!!");
			}
		}, 
				6,//6秒后开始执行
				2, //每隔2秒执行一次
				TimeUnit.SECONDS);
		
	}
}
