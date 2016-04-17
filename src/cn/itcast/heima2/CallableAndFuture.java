package cn.itcast.heima2;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *Future 取得的结果类型好Callable返回的结果类型必须一致
 *这是通过反省来实现的
 *Callable要采用ExecutorService的submit方法提交
 *返回的future对象可以取消任务
 *
 *Callable这种类型的任务会返回结果，返回的结果由Future去拿，
 *当Callable执行完毕后，future就能拿到结果。
 *future没有结果的时候会一直等，
 */
public class CallableAndFuture {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<String> future = 
			threadPool.submit(
					new Callable<String>() {
						@Override
						public String call() throws Exception {
							Thread.sleep(2000);
							return "hello";
						}
					}
					
					);
		System.out.println("等待结果。。。");
		try {
//			System.out.println("拿到结果。。。" + future.get());//拿不到结果就一直等着
			System.out.println("拿到结果。。。" + future.get(1,TimeUnit.SECONDS));//等1秒就拿结果，拿不到就抛异常
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*CompletionService 用于提交一组Callable任务
		 * 其take方法返回已完成的一个Callable任务对应的future对象
		 * 
		 * 好比我同时中了几块地的麦子，然后就等待收割，收割时，则是哪块地先成熟了
		 * 则先去收割哪块麦子
		 */
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
		for(int i = 1; i <= 10; i++){
			final int seq = i;
			completionService.submit(new Callable<Integer>() {
				
				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
			});
		}
		
		for(int i = 0; i < 10; i++){
			try {
				System.out.println(
				completionService.take().get()
						);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	
	
}
