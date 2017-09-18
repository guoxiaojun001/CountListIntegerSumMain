package com.test.biglist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** 
 * ʹ��ExecutorService��invokeAll�������� 
 */
public class CountSumWithCallable {

	/** 
	 * @param args 
	 * @throws InterruptedException  
	 * @throws ExecutionException  
	 */  
	public static void main(String[] args) throws InterruptedException, ExecutionException {  
		int threadCounts =19;//ʹ�õ��߳���  
		long sum=0;  
		ExecutorService exec=Executors.newFixedThreadPool(threadCounts);  
		List<Callable<Long>> callList=new ArrayList<Callable<Long>>();  
		//���ɺܴ��List  
		List<Integer> list = new ArrayList<Integer>();  
		for (int i = 0; i <= 1000000; i++) {  
			list.add(i);  
		}  
		int len=list.size()/threadCounts;//ƽ���ָ�List  
		//List�е�����û���߳����ࣨ���ٴ��ڣ�  
		if(len==0){  
			threadCounts=list.size();//����һ���̴߳���List�е�һ��Ԫ��  
			len=list.size()/threadCounts;//����ƽ���ָ�List  
		}  
		for(int i=0;i<threadCounts;i++){  
			final List<Integer> subList;  
			if(i==threadCounts-1){  
				subList=list.subList(i*len,list.size());  
			}else{  
				subList=list.subList(i*len, len*(i+1)>list.size()?list.size():len*(i+1));  
			}  
			//���������ڲ���ʵ��  
			callList.add(new Callable<Long>(){  
				public Long call() throws Exception {  
					long subSum=0L;  
					for(Integer i:subList){  
						subSum+=i;  
					}  
					System.out.println("������̣߳�"+Thread.currentThread().getName()+"��һ����List��������Ϊ��\tSubSum:"+subSum);  
					return subSum;  
				}  
			});  
		}  
		List<Future<Long>> futureList=exec.invokeAll(callList);  
		for(Future<Long> future:futureList){  
			sum+=future.get();  
		}  
		exec.shutdown();  
		System.out.println(sum);  
	}  


}
