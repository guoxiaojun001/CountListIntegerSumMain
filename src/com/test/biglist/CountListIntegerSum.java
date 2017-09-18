package com.test.biglist;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * ����List�����������ĺ�<br> 
 * ���ö��̣߳��ָ�List���� 
 */ 
public class CountListIntegerSum {

	private long sum;//��������ĺ�  
	private CyclicBarrier barrier;//��դ���ϵ�(ͬ����)  
	private List<Integer> list;//��������List  
	private int threadCounts;//ʹ�õ��߳���  
	public CountListIntegerSum(List<Integer> list,int threadCounts) {  
		this.list=list;  
		this.threadCounts=threadCounts;  
	}  
	
	/** 
	 * ��ȡList�����������ĺ� 
	 * @return 
	 */  
	public long getIntegerSum(){  
		ExecutorService exec=Executors.newFixedThreadPool(threadCounts);  
		int len=list.size()/threadCounts;//ƽ���ָ�List  
		//List�е�����û���߳����ࣨ���ٴ��ڣ�  
		if(len==0){  
			threadCounts=list.size();//����һ���̴߳���List�е�һ��Ԫ��  
			len=list.size()/threadCounts;//����ƽ���ָ�List  
		}  
		barrier=new CyclicBarrier(threadCounts+1);  
		for(int i=0;i<threadCounts;i++){  
			//�����߳�����  
			if(i==threadCounts-1){//���һ���̳߳е�ʣ�µ�����Ԫ�صļ���  
				exec.execute(new SubIntegerSumTask(list.subList(i*len,list.size())));  
			}else{  
				exec.execute(new SubIntegerSumTask(list.subList(i*len, len*(i+1)>list.size()?list.size():len*(i+1))));  
			}  
		}  
		try {  
			barrier.await();//�ؼ���ʹ���߳�����դ���ȴ���ֱ�����е��̶߳�������դ��  
		} catch (InterruptedException e) {  
			System.out.println(Thread.currentThread().getName()+":Interrupted");  
		} catch (BrokenBarrierException e) {  
			System.out.println(Thread.currentThread().getName()+":BrokenBarrier");  
		}  
		exec.shutdown();  
		return sum;  
	}  
	/** 
	 * �ָ����List�����͵��߳����� 
	 * @author lishuai 
	 * 
	 */  
	public class SubIntegerSumTask implements Runnable{  
		private List<Integer> subList;  
		public SubIntegerSumTask(List<Integer> subList) {  
			this.subList=subList;  
		}  
		public void run() {  
			long subSum=0L;  
			for (Integer i : subList) {  
				subSum += i;  
			}    
			synchronized(CountListIntegerSum.this){//��CountListIntegerSum������ͬ��  
				sum+=subSum;  
			}  
			try {  
				barrier.await();//�ؼ���ʹ���߳�����դ���ȴ���ֱ�����е��̶߳�������դ��  
			} catch (InterruptedException e) {  
				System.out.println(Thread.currentThread().getName()+":Interrupted");  
			} catch (BrokenBarrierException e) {  
				System.out.println(Thread.currentThread().getName()+":BrokenBarrier");  
			}  
			System.out.println("������̣߳�"+Thread.currentThread().getName()+"��һ����List��������Ϊ��\tSubSum:"+subSum);  
		}  

	}  

}
