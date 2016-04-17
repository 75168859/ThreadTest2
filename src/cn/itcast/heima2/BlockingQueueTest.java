package cn.itcast.heima2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 *阻塞队列 
 *
 *ArrayBlockingQueue 
 *看帮助文档，了解各个方法的区别有对比表格
 *只有put方法和take方法才具有阻塞功能
 *
 *
 */
public class BlockingQueueTest {
	public static void main(String[] args) {
		//用3个空间的队列演示阻塞队列的功能和效果
		final BlockingQueue queue = new ArrayBlockingQueue(3);		
		for(int i = 0; i < 2; i++){
			new Thread(){
				public void run() {
					while(true){
						try {
							Thread.sleep((long)(Math.random()*1000));
							System.out.println(Thread.currentThread().getName() + "准备放数据！");
							queue.put(1);
							System.out.println(Thread.currentThread().getName()
									+"，已经放了数据，"
									+ " 队列目前有 " + queue.size() + " 个数据 ");
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				
			}.start();
		}
		
		new Thread(){
			public void run() {
				try {
					//将此处的睡眠时间分别改为 100 和 1000，观察运行结果
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName() + " 准备取数据！！！");
					queue.take();
					System.out.println(Thread.currentThread().getName() + " 已经能够取走数据，"
							+" 队列目前有 " + queue.size() + " 个数据！！！");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			};
			
		}.start();
		
	}
}
