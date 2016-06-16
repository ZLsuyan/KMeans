package kMeans;
/**
 * @author ����  
 * @date 2015/12/13
 */

import java.io.*;

import kMeans.Point;

public class ReadDataset {
	// �����ļ�·��
	String filepath = "C:/Users/Zengli/Desktop/�����ھ�-ʵ�����ݼ�/raw_dataset/dataset2.txt" ;
	
	/**
	 * �����ݼ��ļ��й��ж����У������ж��ٸ����ݵ�
	 * @return
	 * @throws IOException
	 */
	public int getTextLines() throws IOException {
		//���ﶨ��һ���ַ������������Ľڵ��������ڶ�ȡ�ļ���һ���ַ�һ���ַ��Ķ�ȡ��
		FileReader fr = new FileReader(filepath); 
		//�ڶ���õ����������׽�һ�������������ڸ���Ч�ʵĶ�ȡ�ļ���һ��һ�еĶ�ȡ��
		BufferedReader br = new BufferedReader(fr); 
		//����ͳ����������0��ʼ
		int lines = 0;
		//readLine()�����ǰ��ж��ģ�����ֵ�����е�����
		while(br.readLine() != null) { 
			lines++; // ÿ��һ�У������x�ۼ�1
		}
		return lines; //�����ܵ�����
	}
	
	/**
	 * ��ȡ�����ݼ��е����ݵ㣬�����ݵ㱣�浽�����в�����
	 * @return
	 * @throws IOException
	 */
	public Point[] getDataset() throws IOException{
		//��ȡ���ݼ��ĵ�ĸ��������ı�����
		int MAXNUM = getTextLines();
		//����һ��Point���͵����飬���ڱ������ݵ������
		Point[] my_point = new Point[MAXNUM];
		//���ﶨ��һ���ַ������������Ľڵ��������ڶ�ȡ�ļ���һ���ַ�һ���ַ��Ķ�ȡ��
		FileReader fr = new FileReader(filepath); 
		//�ڶ���õ����������׽�һ�������������ڸ���Ч�ʵĶ�ȡ�ļ���һ��һ�еĶ�ȡ��
		BufferedReader br = new BufferedReader(fr); 
		String eachLine = null;
		for(int i=0;i<MAXNUM;i++){
			/*
			 * ��ÿһ�е�Ԫ�أ������ݿո�����Ԫ��
			 * ����ȡ����ÿһ�еĵ�һ��Ԫ����Ϊx���꣬�ڶ���Ԫ����Ϊy����
			 */
			eachLine = br.readLine();
			String datas[] = eachLine.split(" ");
			//����һ���µ�Point����
	    	Point point = new Point();                                 
	    	point.setX(Double.valueOf(datas[0])) ;
	    	point.setY(Double.valueOf(datas[1])) ;
	    			
	    	//������ŵ�������
	    	my_point[i] = point;									   
	    }
		return my_point;
	}

	/**
	 * �����г��Ĵض���.txt�ļ�
	 * @param file
	 * @param appendance
	 */
	public static void append(File file, String appendance) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
            FileWriter fw = new FileWriter(file, true);
            fw.write(appendance);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 
	/**
	 * ������
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		ReadDataset rds = new ReadDataset();
//		int MAXNUM = rds.getTextLines();
//		Point[] my_point = new Point[MAXNUM];
//		my_point = rds.getDataset();
//		for(int i=0;i<MAXNUM;i++){
//			System.out.println(my_point[i].getX()+","+my_point[i].getY());
//		}
		
		int lines = 0;
		ReadDataset rds = new ReadDataset();
		lines = rds.getTextLines();
		System.out.println(lines);

	}
}
