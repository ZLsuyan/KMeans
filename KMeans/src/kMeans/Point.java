package kMeans;
/**
 * @author ����  
 * @date 2015/12/13
 */

public  class Point{                                     //����һ��Point�࣬��ʾ���ݵ������
	private double x ;
	private double y ;

	public Point() {                                     //����һ���޲η���
		super();
	}

	public Point(double x, double y) {                   //����һ���вη���
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
