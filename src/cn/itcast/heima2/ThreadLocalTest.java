package cn.itcast.heima2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 线程范围内共享变量
 *
 */
public class ThreadLocalTest {
	
	static ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
	static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();
	
	public static void main(String[] args) {
		for(int i = 0; i < 2; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " has put data: " + data );
					tl.set(data);
					/*MyThreadScopeData myData = new MyThreadScopeData();
					myData.setName("name:" + data);
					myData.setAge(data);
					myThreadScopeData.set(myData);*/
					MyThreadScopeData.getThreadInstance().setName("name:" + data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int data = tl.get();
			System.out.println("A from " + Thread.currentThread().getName()
					+ "get data : " + data );
			/*MyThreadScopeData myData = myThreadScopeData.get();
			System.out.println("A from " + Thread.currentThread().getName()
					+ "getMydata : " + myData.getName() + "," +  myData.getAge());*/
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("A from " + Thread.currentThread().getName()
					+ "getMydata : " + myData.getName() + "," +  myData.getAge());
			
		}
	}
	
	static class B{
		public void get(){
			int data = tl.get();
			System.out.println("B from " + Thread.currentThread().getName()
					+ "get data : " + data );
			/*MyThreadScopeData myData = myThreadScopeData.get();
			System.out.println("B from " + Thread.currentThread().getName()
					+ "getMydata : " + myData.getName() + "," +  myData.getAge());*/
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("B from " + Thread.currentThread().getName()
					+ "getMydata : " + myData.getName() + "," +  myData.getAge());
		}
	}
	

}

class MyThreadScopeData{
	private MyThreadScopeData(){}
	
	public static /*synchronized*/ MyThreadScopeData getThreadInstance(){
		MyThreadScopeData instance = map.get();
		if(instance == null){
			instance = new MyThreadScopeData(); // 懒汉式
			map.set(instance);
		}
		return instance;
	}
//	private static MyThreadScopeData instance =  null;//new MyThreadScopeData();//饿汉模式
	private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
	
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}



