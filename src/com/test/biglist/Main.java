package com.test.biglist;

import java.util.ArrayList;
import java.util.List;

/** 
 * ����List�����������ĺͲ����� 
 */  
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();  
		int threadCounts = 5;//���õ��߳���  
		//���ɵ�List����  
		for (int i = 1; i <= 10000; i++) {  
			list.add(i);  
		}  
		CountListIntegerSum countListIntegerSum=new CountListIntegerSum(list,threadCounts);  
		long sum=countListIntegerSum.getIntegerSum();  
		System.out.println("List�����������ĺ�Ϊ:"+sum);  
	}  


}
