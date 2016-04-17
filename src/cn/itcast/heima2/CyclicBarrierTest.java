package cn.itcast.heima2;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *CyclicBarrier 同步工具类
 *表示大家彼此等待，大家集合好后才开始出发，分散活动后又再制定地点集合碰面，
 *这就好比整个公司的人员利用周末时间集体郊游一样，先各自从家出发到公司集合后，在同事出发到公园游玩，
 *在指定地点集合后再同时开始就餐，。。。
 *
 *
 *
 */
public class CyclicBarrierTest {
	public static void main(String[] args) {
		ExecutorService service =  Executors.newCachedThreadPool();
		final CyclicBarrier cb = new CyclicBarrier(3);
		for(int i = 0; i < 3; i++){
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep((long)(Math.random()*10000));

						System.out.println("线程 " + Thread.currentThread().getName()
							+"即将到达集合地点1，当前已有 " + (cb.getNumberWaiting() + 1));
						
						cb.await();
					
						System.out.println("线程 " + Thread.currentThread().getName()
							+"即将到达集合地点2，当前已有 " + (cb.getNumberWaiting() + 1));
						
						cb.await();
						
						System.out.println("线程 " + Thread.currentThread().getName()
								+"即将到达集合地点3，当前已有 " + (cb.getNumberWaiting() + 1));
						cb.await();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);
		}
		
	}
}
