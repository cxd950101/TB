package com.practice;

public class TestThread{
	public static void main(String[] args){ 
		
		Thread1 thread1 = new Thread1();
		thread1.start();
		
		
		
		/*Thread2 thread2 = new Thread2();
		Thread2 t1=new Thread(thread2);
		t1.start();*/
		new Thread(new Thread2()).start();
	}

}
