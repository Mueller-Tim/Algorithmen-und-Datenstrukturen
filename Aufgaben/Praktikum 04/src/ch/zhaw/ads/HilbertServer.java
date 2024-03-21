package ch.zhaw.ads;

public class HilbertServer implements CommandExecutor{

	Turtle turtle;
	@Override
	public String execute(String command) /*throws Exception*/ {
		int depth = Integer.parseInt(command);
		double distance = 0.8 / (Math.pow(2, depth+1)-1);
		turtle = new Turtle(0.1,0.1);
		hilbert(depth, distance, -90);
		return null;
	}

	private void hilbert(int depth, double distance, double angle){
		if(depth >= 0){
			turtle.turn(-angle);

			hilbert(depth-1, distance, -angle);

			turtle.move(distance);
			turtle.turn(angle);

			hilbert(depth-1, distance, angle);

			turtle.move(distance);

			hilbert(depth-1, distance, angle);

			turtle.turn(angle);
			turtle.move(distance);

			hilbert(depth-1, distance, -angle);

			turtle.turn(-angle);
		}
	}
}
