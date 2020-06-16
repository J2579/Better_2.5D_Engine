package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import configurations.Configurations;
import geom.KeyMovementCalculator;
import geom.Point3D;
import geom.Polygon3D;

/**
 * This is a sample of what you can do with DBCanvas.
 * 
 * @author J2579
 */

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	
	private static final int TICK_RATE = 60; //60 ticks per second
	private static final int WIDTH = 500;  //GUI is 400px wide
	private static final int HEIGHT = 500; //GUI is 400px tall
	
	private Timer timer;
	private ModelWindow window;
	
	private ArrayList<Polygon3D> objects;
	private KeyMovementCalculator calc;
	
	public static void main(String[] args) {
		GUI s = new GUI();
		s.run();
	}
	
	public void setUp() {
		Point3D.configureEnvironment(WIDTH, HEIGHT-50); //this should be scaled multiplicatively
		objects = Configurations.alphaTestOne();
		calc = new KeyMovementCalculator();
		configureKeyBindings();
		
	}
	
	private void configureKeyBindings() {
		JRootPane root = this.getRootPane();
		InputMap imap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = root.getActionMap();
		
		KeyStroke leftPress = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false); //Modularity!
		KeyStroke rightPress = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false);
		KeyStroke upPress = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false);
		KeyStroke downPress = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false);
		KeyStroke leftRelease = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true); 
		KeyStroke rightRelease = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true);
		KeyStroke upRelease = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true);
		KeyStroke downRelease = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true);
		KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		
		imap.put(leftPress, "leftPress");
		imap.put(rightPress, "rightPress");
		imap.put(upPress, "upPress");
		imap.put(downPress, "downPress");
		imap.put(leftRelease, "leftRelease");
		imap.put(rightRelease, "rightRelease");
		imap.put(upRelease, "upRelease");
		imap.put(downRelease, "downRelease");
		imap.put(esc, "DEBUG-BREAK");
		
		amap.put("leftPress", new MovementAction("left", false));
		amap.put("rightPress", new MovementAction("right", false));
		amap.put("upPress", new MovementAction("up", false));
		amap.put("downPress", new MovementAction("down", false));
		amap.put("leftRelease", new MovementAction("left", true));
		amap.put("rightRelease", new MovementAction("right", true));
		amap.put("upRelease", new MovementAction("up", true));
		amap.put("downRelease", new MovementAction("down", true));
		
		amap.put("DEBUG-BREAK", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				int a = 5; //Break here!
			}
		});
	}
	
	//movey movey
	private class MovementAction extends AbstractAction {
		
		private char dir; //This may have unexpected side-effects later. Too bad!
		private boolean release; //false: press, true: release
		
		public MovementAction(String s, boolean r) {
			this.dir = s.charAt(0);
			this.release = r;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(release) {
				switch(dir) {
					case 'l': calc.leftReleased();
						      break;
					case 'r': calc.rightReleased();
					          break;
					case 'u': calc.upReleased();
					          break;
					case 'd': calc.downReleased();
					          break;
				}
			}
			else {
				switch(dir) {
					case 'l': calc.leftPressed();
						      break;
					case 'r': calc.rightPressed();
					          break;
					case 'u': calc.upPressed();
					          break;
					case 'd': calc.downPressed();
					          break;
				}
			}
		}
	}
	
	public void run() {
	    setUp();
	    
	    
		setTitle("Model Test");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit button stops program
		
		//Add graphics window to GUI
		window = new ModelWindow(WIDTH, HEIGHT);
		add(window); 
		
		//Show the window
		setVisible(true);
		
		//Create the window's "Buffer"
		window.createAndSetBuffer();
		
		//Create the timer
		timer = new Timer(1000 / TICK_RATE, this); //Timer will tick TICK_RATE times per second
												   //[every (1000/TICK_RATE) ms]

		//Start the timer
		timer.start(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//If the timer gave us an action signal
		if(e.getSource().equals(timer)) {
			window.update();
			Point3D vector = calc.generateMovementVector();
			for(Polygon3D object : objects) {
				object.translateAllByVector(vector);
			//	System.out.println(object.pointAt(0));
			}
		}
		return;
	}
	
	private class ModelWindow extends DoubleBufferedCanvas {

		public ModelWindow(int width, int height) {
			super(width, height);
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.RED); 
			
			for(Polygon3D object : objects) {
				object.draw(g);
			}
		}
	}
}