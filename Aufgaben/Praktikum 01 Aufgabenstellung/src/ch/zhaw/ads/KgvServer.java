package ch.zhaw.ads;

import static java.lang.Math.abs;

public class KgvServer implements CommandExecutor {

	public int kgv(int a, int b){
		return abs(a * b) / ggT(a, b);
	}

	private int ggT(int a,int b){
		if(a == 0) return 0;
		int c = 0;
		while (b != 0){
			c = a % b;
			a = b;
			b = c;
		}
		return a;
	}

	@Override
	public String execute(String command) {
		String[] numbers = command.split("[ ,]+");
		int a = Integer.parseInt(numbers[0]);
		int b = Integer.parseInt(numbers[1]);
		return Integer.toString(kgv(a,b));
	}
}
