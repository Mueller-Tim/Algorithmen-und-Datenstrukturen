
package ch.zhaw.ads;

import javax.swing.*;

public class SnowflakeServer  implements  CommandExecutor{

	private Turtle turtle;

	@Override
	public String execute(String command) /*throws Exception*/ {
		turtle = new Turtle();
		snowflake(Integer.parseInt(command), 0.7);
		snowflake(Integer.parseInt(command), 0.7);
		snowflake(Integer.parseInt(command), 0.7);
		return turtle.getTrace();
	}

	private void snowflake(int step, double distance){
		if(step == 0){
			turtle.move(distance);
		} else{
			step--;
			distance = distance / 3;
			snowflake(step, distance);
			turtle.turn(60);
			snowflake(step, distance);
			turtle.turn(-120);
			snowflake(step, distance);
			turtle.turn(60);
			snowflake(step, distance);
		}

	}
}
