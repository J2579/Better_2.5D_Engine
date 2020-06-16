package geom;

import java.util.Stack;

public class KeyMovementCalculator {

	//only the best design habits
	private static final int NONE = -1; //hehe null
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int UP = 2;
	private static final int DOWN = 3;
	private static final double inv_sqrt_2 = 1.0 / Math.sqrt(2);
	
	//kbstate
	private boolean[] keys; //left, right, up, down
//	private Stack<Integer> keyStack = new Stack<Integer>();
	private int recentDir = 0; //This could possibly be a stack. Possibly.....
	
	public KeyMovementCalculator() {
		keys = new boolean[4];
	}
	
	public Point3D generateMovementVector() {
		Point3D temp = new Point3D(); //0,0,0
		
		int numPressed = 0;
		for(int idx = 0; idx < keys.length; ++idx) {
			numPressed += (keys[idx] ? 1 : 0);
		}
		
		if(numPressed == 1) {
			if(keys[UP])
				temp.translate(-1, 0, 0);
			else if(keys[DOWN])
				temp.translate(1, 0, 0);
			else if(keys[LEFT])
				temp.translate(0, 0, -1);
			else
				temp.translate(0, 0, 1);
		}
		else if(numPressed == 2) { //process diag	
			
			if(keys[UP] && keys[DOWN] || keys[LEFT] && keys[RIGHT])
				; //do nothing here
			else if(keys[UP] && keys[LEFT])
				temp.translate(-1 * inv_sqrt_2, 0, -1 * inv_sqrt_2);
			else if(keys[UP] && keys[RIGHT])
				temp.translate(-1 * inv_sqrt_2, 0, 1 * inv_sqrt_2);
			else if(keys[DOWN] && keys[LEFT])
				temp.translate(1 * inv_sqrt_2, 0, -1 * inv_sqrt_2);
			else if(keys[DOWN] && keys[RIGHT])
				temp.translate(1 * inv_sqrt_2, 0, 1 * inv_sqrt_2);
		}
		temp.invert();
		return temp;
	}
	
	public void leftPressed() {
		keys[LEFT] = true;
		recentDir = LEFT;
	}
	
	public void leftReleased() {
		keys[LEFT] = false;
	}
	
	public void rightPressed() {
		keys[RIGHT] = true;
		recentDir = RIGHT;
	}
	
	public void rightReleased() {
		keys[RIGHT] = false;
	}
	
	public void upPressed() {
		keys[UP] = true;
		recentDir = UP;
	}
	
	public void upReleased() {
		keys[UP] = false;
	}
	
	public void downPressed() {
		keys[DOWN] = true;
		recentDir = DOWN;
	}
	
	public void downReleased() {
		keys[DOWN] = false;
	}
	
	@Override
	public String toString() {
		return "[Left: " + keys[LEFT] + ", Right: " + keys[RIGHT] + "Up: " + keys[UP] + "Down: " + keys[DOWN] + "]";
	}
}
