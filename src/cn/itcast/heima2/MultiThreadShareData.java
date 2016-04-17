package cn.itcast.heima2;

/**
 * 7.avi
 * 设计4个线程，其中两个线程每次对j增加1，另外两个线程每次对j减少1，写出程序
 * 
 */
/**
 *多线程共享数据 
 *1.如果每个线程执行的代码相同，可以使用同一个Runnable对象
 *这个Runnable对象中有那个共享数据，例如，卖票系统就可以这么做
 *
 *2.如果每个线程执行的代码不同，这时候需要不同的Runnable对象
 *有如下两种方式来实现这些Runnable对象之间的数据共享
 *2.1 将共享数据封装在另外一个对象中，然后将这个对象逐一传递给各个
 *Runnable对象。每个线程对共享数据的操作方法也分配到那个对象身上去完成，
 *这样容易实现针对该数据进行的各个操作的互斥和通信
 *
 *2.2将这些Runnable对象作为某一个类中的内部类，共享数据作为这个外部类中的成员变量，
 *每个线程对共享数据的操作方法也分配给外部类，以便实现对共享数据进行的各个操作的互斥和通信
 *作为内部类的各个Runnable对象调用外部类的这些方法
 *
 *上面这两种方式的组合：将共享数据封装在另外一个对象中，每个线程对共享数据的操作方法也分配到那个对象身上去完成
 *对象作为这个外部类中的成员变量或方法中的局部变量，每个线程的Runnable对象作为外部类中的成员内部类或局部内部类
 *
 *总之，要同步互斥的几段代码最好是分别放在几个独立的方法中，这些方法再放在同一个类中，这样比较容易实现他们之间的
 *同步互斥和通信
 */
public class MultiThreadShareData {
	public static void main(String[] args) {
		final ShareData1 data = new ShareData1();
		//卖票
		/*new Thread(data).start();
		new Thread(data).start();*/
		
		//2.1
		ShareData1 data2 = new ShareData1();
		new Thread(new MyRunnable1(data2)).start();
		new Thread(new MyRunnable2(data2)).start();
		
		//2.2
		final ShareData1 data1 = new ShareData1();
		new Thread(new Runnable() {
			public void run() {
				data1.decrement();
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				data1.increment();
			}
		}).start();
		
		
	}
}

class MyRunnable1 implements Runnable{
	private ShareData1 data1;
	public MyRunnable1(ShareData1 data1) {
		this.data1 = data1;
	}
	
	@Override
	public void run() {
		data1.decrement();
	}
}

class MyRunnable2 implements Runnable{
	private ShareData1 data1;
	public MyRunnable2(ShareData1 data1) {
		this.data1 = data1;
	}
	
	@Override
	public void run() {
		data1.increment();
	}
}

class ShareData1 /*implements Runnable*/{
	//卖票
	/*private int count = 100;
	@Override
	public void run() { 
		while(true){
			count--;
		}
	}*/
	
	private int j = 0;
	public synchronized void increment(){
		j++;
	}
	
	public synchronized void decrement(){
		j--;
	}
	
}
