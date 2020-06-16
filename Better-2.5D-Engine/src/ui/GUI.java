package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import configurations.Configurations;
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
	
	
	public static void main(String[] args) {
		GUI s = new GUI();
		s.run();
	}
	
	public void setUp() {
		Point3D.configureEnvironment(WIDTH, HEIGHT-50); //this should be scaled multiplicatively
		objects = Configurations.AlphaTestOne();
		
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
		}
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