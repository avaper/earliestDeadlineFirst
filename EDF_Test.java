package p3;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Clase Práctica 3, ejercicio 2
 */
public class EDF_Test 
{
	private static PriorityQueue<ThreadP> pQueue = new PriorityQueue<ThreadP>();

	/**
	 * Clase que representa un hilo con prioridad
	 */
	public class ThreadP implements Runnable, Comparable<ThreadP>
	{
		String name;
		int periodo;
		int ejecucion;	
		int priority;
		
		/**
		 * 
		 * @param priority
		 */
		public ThreadP(String name, int periodo, int ejecucion)
		{
			this.name = name;
			this.periodo = periodo;
			this.ejecucion = ejecucion;
			this.priority = periodo;
		}
		
		@Override
		public int compareTo(ThreadP o) 
		{		
			if (priority < o.priority) return -1;
			else if (priority > o.priority) return 1;
			
			return 0;
		}
		
		@Override
		public void run() 
		{
			System.out.println("\tThread " + name + " ejecutandose");
			try 
			{
				Thread.sleep(ejecucion*1000);
				
				Terminar();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}	
		
		@Override
		public String toString()
		{
			return "[" + name + ", " + priority + "]";
		}
	}
	
	/**
	 * Controlador del test
	 * @param thread
	 */
	public synchronized static void Controller(ArrayList<ThreadP> threads)
	{
		float usoMax = 0;
		
		for (ThreadP threadP : threads) usoMax += (float) threadP.ejecucion/threadP.periodo;
		
		if (usoMax <= 1)
		{
			for (ThreadP threadP : threads) pQueue.add(threadP);

			System.out.println("Es planificable:\n\n\tValor de uso: " + usoMax + "\n");
			System.out.println("Planificacion: \n\n\tCola de prioridad de procesos:" + pQueue + "\n");
			System.out.println("Test de ejecucion:\n");
			
			ThreadP threadP = pQueue.poll();
			
			threadP.run();
		}
		else System.out.println("No es planificable:\n\n\tValor de uso: " + usoMax + "\n");
	}
	
	/**
	 * Saca un proceso de la cola y lo ejecuta, si no está vacía
	 */
	public synchronized static void Terminar()
	{
		if ( !pQueue.isEmpty() ) pQueue.poll().run();
	}
	
	/**
	 * MAIN
	 * @param args
	 */
	public static void main(String[] args) 
	{
		EDF_Test EDF = new EDF_Test();
		
		ArrayList<ThreadP> list = new ArrayList<ThreadP>();
		
		ThreadP thread1 = EDF.new ThreadP("A", 10, 1);
		ThreadP thread2 = EDF.new ThreadP("B",  6, 3);
		ThreadP thread3 = EDF.new ThreadP("C", 10, 2);
		ThreadP thread4 = EDF.new ThreadP("D", 10, 1);
		
		list.add(thread1);
		list.add(thread2);
		list.add(thread3);
		list.add(thread4);

		Controller(list);
		
		System.out.println("\nFinalizado:\n\n\tCola de prioridad de procesos: " + pQueue);
	}
}
