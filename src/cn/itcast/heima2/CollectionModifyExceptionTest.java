package cn.itcast.heima2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 同步集合
 *传统方式下用 Collection 工具类提供的 synchronized collection 
 *放法来获得同步集合，分析该方法的实现源码
 *
 *java5中提供了如下一些同步集合类
 *   通过看 java.util.concurrent包下地介绍可以知道有哪些并发集合
 *    ConcurrentHashMap
 *    CopyOnWriteArrayList
 *    CopyOnWriteArraySet
 * 传统方式下的Collection 在迭代集合时，不允许对集合进行修改
 *    根据AbstractList的 checkForComodification方法的源码，分析产生
 *    ConcurrentModificationException异常的原因   
 * 
 */
public class CollectionModifyExceptionTest {
	public static void main(String[] args) {
		
//		Collections.synchronizedMap(null);
//		HashSet set = new HashSet();
////		HashMap map = new HashMap();
//		Hashtable<K, V>
//		Collection users = new ArrayList();//不允许在迭代的时候删除
		Collection users = new CopyOnWriteArrayList();
		
		users.add(new User("张三", 20));
		users.add(new User("李四", 22));
		users.add(new User("王五", 34));
		
		Iterator iterator = users.iterator();
		while(iterator.hasNext()){
			User user = (User)iterator.next();
			if("张三".equals(user.getName())){
				users.remove(user);
			}else{
				System.out.println(user);
			}
		}
	}
}
