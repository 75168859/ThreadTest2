package cn.itcast.heima2;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TranditionalTimerTest {
	
	private static int count = 0;
	
	public static void main(String[] args) {
		/*new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("bomb");
			}
		}, 10000);//10秒以后,开始执行
*/		
	/*new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("bomb");
			}
		}, 10000,3000);//10秒以后,开始执行,以后每隔3秒执行一次
*/		

	//方式1
	class MyTimerTask extends TimerTask{
		@Override
		public void run() {
			count = (count+1)%2;
			System.out.println("bombing");
			new Timer().schedule(new MyTimerTask(), 2000 + 2000*count);//2秒一次4秒一次
		}
	}
	
//	new Timer().schedule(new MyTimerTask(), 3000);
	
	//方式2
	new Timer().schedule(new MyTimerTaskAbomb(), 2000);//2秒后开始执行
	

		while(true){
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);//1秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

//方式2
class MyTimerTaskAbomb extends TimerTask{
	@Override
	public void run() {
		System.out.println("bombing--a....");
		new Timer().schedule(new MyTimerTaskBbomb(), 3000);
	}
}

class MyTimerTaskBbomb extends TimerTask{
	@Override
	public void run() {
		System.out.println("bombing--b...");
		new Timer().schedule(new MyTimerTaskAbomb(), 2000);
	}
}




//quartz 开源工具 业务调度框架