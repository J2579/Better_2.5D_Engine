package configurations;

import java.awt.Color;
import java.util.ArrayList;

import geom.Point3D;
import geom.Polygon3D;

public class Configurations {
	
	public static ArrayList<Polygon3D> testPoint() {
		Point3D ctr = new Point3D();
		Polygon3D ctrGon = new Polygon3D(Color.WHITE);
		ctrGon.add(ctr);
		ArrayList<Polygon3D> objects = new ArrayList<Polygon3D>();
		objects.add(ctrGon);
		
		return objects;
	}
	
	public static ArrayList<Polygon3D> testSquare() {
		Point3D negZ = new Point3D(0,0,-100);
		Point3D posZ = new Point3D(0, 0, 100);
		Point3D negX = new Point3D(-100,0,0);
		Point3D posX = new Point3D(100,0,0);
		Polygon3D square = new Polygon3D(Color.WHITE);
		square.add(negX);
		square.add(posZ);
		square.add(posX);
		square.add(negZ);
		
		ArrayList<Polygon3D> objects = new ArrayList<Polygon3D>();
		objects.add(square);
		
		return objects;
	}
	
	public static ArrayList<Polygon3D> alphaTestOne() {
		Point3D negZ = new Point3D(0,0,-100);
		Point3D posZ = new Point3D(0, 0, 100);
		
		Point3D negX = new Point3D(-100,0,0);
		Point3D posX = new Point3D(100,0,0);
		
		Point3D negXnegZ = new Point3D(-100,0,-100);
		Point3D negXposZ = new Point3D(-100,0,100);
		Point3D posXnegZ = new Point3D(100,0,-100);
		Point3D posXposZ = new Point3D(100,0,100);
		
		Point3D negXnegZup = new Point3D(-100,100,-100);
		Point3D negXposZup = new Point3D(-100,100,100);
		Point3D posXnegZup = new Point3D(100,100,-100);
		Point3D posXposZup = new Point3D(100,100,100);
		
		Point3D ctr = new Point3D(0,0,0);
		
		Polygon3D square1 = new Polygon3D(Color.WHITE);
		square1.add(ctr);
		square1.add(negX);
		square1.add(negXposZ);  //   | X
		square1.add(posZ);      //   |
		
		Polygon3D square2 = new Polygon3D(Color.WHITE);
		square2.add(ctr);
		square2.add(posZ);
		square2.add(posXposZ);  //  |
		square2.add(posX);      //  | X
		
		Polygon3D square3 = new Polygon3D(Color.WHITE);
		square3.add(ctr);
		square3.add(posX);
		square3.add(posXnegZ);  //   |
		square3.add(negZ);      // X | 
		
		Polygon3D square4 = new Polygon3D(Color.WHITE);
		square4.add(ctr);
		square4.add(negZ);
		square4.add(negXnegZ);  // X |
		square4.add(negX);      //   | 

		Polygon3D wall1 = new Polygon3D(Color.BLUE);
		wall1.add(negXposZ);
		wall1.add(posXposZ);
		wall1.add(posXposZup);
		wall1.add(negXposZup);
		
		Polygon3D wall2 = new Polygon3D(Color.RED);
		wall2.add(negXposZ);
		wall2.add(negXnegZ);
		wall2.add(negXnegZup);
		wall2.add(negXposZup);
		
		ArrayList<Polygon3D> objects = new ArrayList<Polygon3D>();
		objects.add(square1);
		objects.add(square2);
		objects.add(square3);
		objects.add(square4);
		objects.add(wall1);
		objects.add(wall2);
		
		return objects;
	}
}
