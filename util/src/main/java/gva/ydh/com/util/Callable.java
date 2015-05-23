package gva.ydh.com.util;

/* 回调接口,回调方法运行于异步线程  
* @ClassName: Callable     
* @version 1.0 2012-1-16 下午5:56:42     
* @param <T>  
*/  
public interface Callable<T> {  
     
   public T call() throws Exception;  
} 
