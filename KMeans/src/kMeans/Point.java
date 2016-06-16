package kMeans;
/**
 * @author 曾丽  
 * @date 2015/12/13
 */

public  class Point{                                     //定义一个Point类，表示数据点的坐标
	private double x ;
	private double y ;

	public Point() {                                     //定义一个无参方法
		super();
	}

	public Point(double x, double y) {                   //定义一个有参方法
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return "("+x+","+y+")";
	} 	

}
