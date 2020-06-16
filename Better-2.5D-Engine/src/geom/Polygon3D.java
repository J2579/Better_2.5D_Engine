package geom;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Polygon3D {
	
	//Amortized O(1) addLast, O(1) get
	private ArrayList<Point3D> points;
	private Color color;
	
	public Polygon3D(Color color) {
		points = new ArrayList<Point3D>();
		this.color = color;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int size() {
		return points.size();
	}
	
	public void add(Point3D point) {
		points.add(point); //addLast()
	}
	
	public Point3D pointAt(int idx) {
		return points.get(idx);
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		
		Iterator<Point3D> it = points.iterator();
		Point3D current, next;

		if(!it.hasNext()) //empty polygon
			return;
		current = it.next();
		
		while(it.hasNext()) {
			next = it.next();
			connectLine(g, current, next);
			current = next;
		}	
		connectLine(g, points.get(0), points.get(points.size() - 1));
	}
	
	private void connectLine(Graphics g, Point3D one, Point3D two) {
		int[] curPts = one.getFlat();
		int[] nextPts = two.getFlat();
		
//		System.out.println("Connected " + one + two);
		g.drawLine(curPts[0], curPts[1], nextPts[0], nextPts[1]); //consider the height of the screen?
	}
	
	public String screenPositionAll() {
		StringBuilder sb = new StringBuilder();
		Iterator<Point3D> it = points.iterator();
		while(it.hasNext()) {
			sb.append(it.next().screenPosition());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	//fun stuff
	
	//If a polygon has already been prismatically attached, causes undefined behavior.
	
	/* TODO: Find a way to keep the nodes from linking. Use a sentinel?
	public static Polygon3D attachPrismatic(Polygon3D one, Polygon3D two) {
		
		if(one.size() != two.size())
			throw new IllegalArgumentException("I'm too tired to make this use case work rn");
		
		Polygon3D merged = new Polygon3D(one.getColor());

		for(int idx = 0; idx < one.size(); ++idx) {
			merged.add(one.pointAt(idx));
			merged.add(two.pointAt(idx));
		}
		
		for(int idx = 0; idx < one.size(); ++idx) {
			merged.add(one.pointAt(idx));
		}
		
	} */
	
}
