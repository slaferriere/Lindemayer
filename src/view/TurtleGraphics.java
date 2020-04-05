package view;

import java.util.Stack;

public class TurtleGraphics {
	
	private double xPosition;
	private double yPosition;
	private double heading;
	private double currHeading;
	private Stack<String> stack = new Stack<String>();
	
	public TurtleGraphics(double xPos, double yPos, double head) {
		xPosition = xPos;
		yPosition = yPos;
		heading = head;
		
	}
	
	public void updateHeading(char movement) {
		
		if(movement == '+') {
			currHeading = (currHeading + heading) % 360;
		}
		else if(movement == '-') {
			currHeading = (currHeading - heading) % 360;
		}
		
	}
	
	public void updatePosition() {
		xPosition = xPosition + 15 * Math.sin(Math.toRadians(currHeading));
		yPosition = yPosition + 15 * Math.cos(Math.toRadians(currHeading));
	}
	
	public double getxPosition() {
		return xPosition;
	}
	
	public double getyPosition() {
		return yPosition;
	}
	
	public String updateStack(char character, String state) {
		if(character == '[') {
			stack.push(state);
		} else if (character == ']') {
			return stack.pop();
		}
		return state;
	}

}
