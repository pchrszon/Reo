package nl.cwi.reo.components;
import nl.cwi.reo.runtime.Input;
import nl.cwi.reo.runtime.Output;

public class MyTestComponents {
	
	private static int k = 300000;
	
	public static void produce(Output<Integer> A) {
		for (int i = 0; i < k; i++)
			A.put(i);
	}
	
	public static void producer(Output<String> A) {
		for (int i = 0; i < k; i++)
			A.put("hello" + i);
	}
	
	public static void consume(Input<Integer> A) {
		long a = System.nanoTime();
		for (int i = 0; i < k; i++)
			A.get();
		long b = System.nanoTime();
		String time = String.format("%f", (b - a) / 1000000000.0);
		System.out.println("Consumer finished after " + time + " sec.");
	}

}
