package geom;

import java.awt.Graphics;

public class Point3D implements Cloneable {

	//screen manip
	private static double sWidth, sWidthHalf, sHeight, sHeightHalf;
	private static final int PT_RD = 10;
	
	private double x,y,z; //internal
	private int ix, iy;   //external
	
	private static final double sin45 = Math.sqrt(2) / 2.00;
	private static final double cos30 = Math.cos(Math.toRadians(30));
	private static final double sin30 = Math.sin(Math.toRadians(30));
	
	//Subject to change.
	private static final double XTransIX = cos30;
	private static final double XTransIY = sin30;
	private static final double YTransIX = 0.0;
	private static final double YTransIY = -1.0;
	private static final double ZTransIX = cos30;
	private static final double ZTransIY = -sin30;

	//Graphics setup
	public static void configureEnvironment(double width, double height) {
		Point3D.sWidth = width;
		Point3D.sWidthHalf = width / 2.00;
		Point3D.sHeight = height;
		Point3D.sHeightHalf = height / 2.00;
	}
	
	//Lazyyyyyy
	public Point3D() {
		this(0,0,0);
	}
	
	public Point3D(double x, double y, double z) {
		//init
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.ix = 0;
		this.iy = 0;
		
		translate(x,y,z);
	}
	
	public void set(double newX, double newY, double newZ) {
		//Really, this is just a translation...
		double dx = newX - getX();
		double dy = newY - getY();
		double dz = newZ - getZ();
		translate(dx, dy, dz);
	}
	
	public void translate(double dx, double dy, double dz) {
		x += dx;
		y += dy;
		z += dz;
		extend_flat(dx, dy, dz);
	}
	
	private void extend_flat(double dx, double dy, double dz) {
		ix += XTransIX * dx;
		ix += YTransIX * dy; //consistency
		ix += ZTransIX * dz;
		
		iy += XTransIY * dx;
		iy += YTransIY * dy; //also consistency
		iy += ZTransIY * dz;
	}
	
	public void draw(Graphics g) {
		int[] xy = getFlat();
		int drawX = xy[0];
		int drawY = xy[1];
		g.fillOval(drawX - PT_RD, drawY - PT_RD, PT_RD * 2, PT_RD * 2); //circle
	}

	public int[] getFlat() {
		//We keep ix and iy in double precision - thus, any rounding errors
		//are maximized at ±1, without accumulating.
		int[] xy = new int[] {(int)(ix + sWidthHalf), (int)(sHeightHalf + iy)}; //trig makes it work 
		return xy;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
	@Override
	public Point3D clone() {
		return new Point3D(x,y,z);
	}
	
	public String screenPosition() {
		int[] xy = getFlat();
		return "[" + xy[0] + "," + xy[1] + "]";
	}
}
