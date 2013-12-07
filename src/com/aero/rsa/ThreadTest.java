package com.aero.rsa;

public abstract class ThreadTest
{
	/*
	 * �ok �ekirdekli i�lemcilerle �al���rken kullan�lacak y�ntemlerin test edilmesi.
	 * 
	 */
	
	public static final class TestThread extends Thread
	{
		private int threadId;
		
		public int result;
		
		public TestThread(int id)
		{
			this.threadId = id;
		}
		
		@Override
		public void run()
		{
			for(int i = 1; i < 100; i++)
			{
				System.out.println(this.threadId + ". thread , " + i + ". i�lem.");
				result = i;
			}
		}
	}
	
	public static void StartTest()
	{
		// Thread'lerin olu�turulmas�;
		TestThread thread1 = new TestThread(1);
		TestThread thread2 = new TestThread(2);
		TestThread thread3 = new TestThread(3);
		TestThread thread4 = new TestThread(4);
		
		// Thread'lerin �al��t�r�lmas�;
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		while(thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive())
		{
			// D�rt thread de tamamlanana kadar bekle;
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
		
		System.out.println("Thread1 result = " + thread1.result);
		System.out.println("Thread2 result = " + thread2.result);
		System.out.println("Thread3 result = " + thread3.result);
		System.out.println("Thread4 result = " + thread4.result);
	}
}
