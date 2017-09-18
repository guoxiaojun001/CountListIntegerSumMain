package com.test.biglist;

import java.util.ArrayList;
import java.util.List;

/** 
 * 计算List中所有整数的和测试类 
 */  
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();  
		int threadCounts = 5;//采用的线程数  
		//生成的List数据  
		for (int i = 1; i <= 10000; i++) {  
			list.add(i);  
		}  
		CountListIntegerSum countListIntegerSum=new CountListIntegerSum(list,threadCounts);  
		long sum=countListIntegerSum.getIntegerSum();  
		System.out.println("List中所有整数的和为:"+sum);  
	}  


}
