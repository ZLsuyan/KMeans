package kMeans;
/**
 * @author ����  
 * @date 2015/12/13
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import kMeans.Point;
import kMeans.ReadDataset;

public class KMeans {
	/**
	 * K-��ֵ����
	 * @param K
	 * @param MaxNum
	 * @param SSE
	 * @param avergx[]
	 * @param avergy[]
	 * @param t
	 * @return clusters
	 */
	public static Random random = new Random() ;
	
	//�����ʼ�����Ĵصĸ���
	public static final int K = 7 ;
	
	//�������ݵ�ĸ���
//	public static final int MAXNUM = 10000;							

	/**
	 * ����ÿһ�����ݵ㵽ÿһ�����ĵľ���
	 */
    public static double distance(double x1,double y1,double x2,double y2){         
    	return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
    
    /**
     * ÿһ�����ݵ㵽�ڼ������ĵľ�����С�������±�
     * @param a
     * @return
     */
    public static int getMinNum(double a[]){                           
    	//��ʼ����С����Ϊһ���ܴ����
    	double Min=Math.exp(4000.0); 			                        
    	int j=0;
    	for(int i=0;i<a.length;i++){
    		if(a[i]<Min){
    			Min=a[i];
    			j = i;
    		}
    	}
    	//���ص��ڼ������ľ�����С��������кţ��±��0��ʼ
    	return j;                                                       
    }
    
    /**
     * ���¼����������
     * @param x
     * @param num
     * @return
     */
    public static double averg(double[] x,int num){                  
    	double sum=0;
    	for(int i=0;i<num;i++){
    		sum+= x[i];
    	} 
    	return sum/num;
    	
    }

    /**
     * �жϵ�������ǰ��Ĵ���λ���Ƿ񲻱�,
     * ֻҪ��һ�����ı䶯��Χ������ֵ���򷵻�false�����䶯��ΧС��һ����ֵ������Ϊ���䣬����true
     * @param avergx
     * @param avergy
     * @param new_avergx
     * @param new_avergy
     * @return
     */
  
    public static boolean isEqual(double[] avergx,double[] avergy, double[] new_avergx, double[] new_avergy){   
    	boolean flag = true ;   	
    	for(int i = 0 ; i < avergx.length ; i ++){
    		if(distance(avergx[i], avergy[i], new_avergx[i], new_avergy[i]) > 1E-5){
    			flag = false ;
    		}
    	}   	
    	return flag ;
    }
    
    /**
     * ������
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException{
		//�����ݼ��ļ��л�ȡ���ݵ�ĸ�����������MAXNUM��
		ReadDataset rds = new ReadDataset();
		int MAXNUM = rds.getTextLines();
		
		//����һ��Point���󣬱����ȡ�������ݼ��е����ݵ�
		Point[] my_point = new Point[MAXNUM]; 
		my_point = rds.getDataset();
		
		//clusters�д��ÿһ���ص��б�list
		ArrayList<ArrayList<Point>> clusters = new ArrayList<ArrayList<Point>>() ;       
	
		//����һ�����飬��������ÿ�ε������K�����ĵ�X��Y����
		double[] avergx = new double[K];                                
    	double[] avergy = new double[K];
    	
    	//��ʼ������
    	for(int i=0; i < K ; i ++){
//    		avergx[i] = my_point[(i*(MAXNUM/K))+random.nextInt()*((i+1)*(MAXNUM/K)-(i*(MAXNUM/K))+1)].getX();
//    		avergx[i] = my_point[(i*(MAXNUM/K))+random.nextInt()*((i+1)*(MAXNUM/K)-(i*(MAXNUM/K))+1)].getX();
    		
    		avergx[i] = my_point[i].getX() ;
    		avergy[i] = my_point[i].getY() ;
    	}	    
	    
    	/*
    	 * *******************************K-Means�㷨����********************************
    	 * ��ʼѭ���������о۴أ�ѭ����ֹ������������
    	 * 1.��������t����ĳһ������ֵ
    	 * 2.����ǰ�����д��ĵ�λ�ñ䶯��Χ�����룩С��ĳһ������ֵ������Ϊ���Ĳ��ٱ䶯
    	 * ֻҪ��һ���������㣬�ͽ���ѭ��
    	 */
    	
    	//�����������t������ʼ��Ϊ0�������������ڸ�����ֵ֮��ʱѭ��
	    int t=0;
	    while(t<100){
	    	//ÿ�����½��о۴�ǰ���Ƚ�clusters�����
	    	clusters.clear();
	    	// ��ÿһ�����ݵ�(�������ĵ�)���ɵ����������һ������
	    	for(int i=0;i<MAXNUM;i++){                                    
	    		for(int m = 0 ; m < K ; m ++){
	    			/*
	    			 * ����ÿһ��������һ��list�б�������K���б�
	    			 * ����K���б�ȫ�����뵽clusters��ȥ
	    			 */
	    			ArrayList<Point> list = new ArrayList<Point>(); 
	    			clusters.add(list);                                      
	    		}
	    		double SSE[] = new double[K];
	    		for(int j=0;j<K;j++){
	    			/*
	    			 * ��������ɵ����ݵ���ѡǰK����Ϊ��ʼ���ģ�
	    			 * ����ÿ�����ݵ㣨�������ĵ㣩��K�����ĵľ��룬
	    			 * ���ĵ㵽����ľ�����СΪ0���ɷ�������Ĵ���
	    			 */
	    			SSE[j] = distance(avergx[j],avergy[j],my_point[i].getX(),my_point[i].getY());      		                                                                            
	    		}
	    		/*
	    		 * ��ȡ��ǰ���ݵ㵽�ڼ������ľ�����̣������±�minNum���±��0��ʼ����
	    		 * ����ǰ���ݵ㣨my_point[i]�����뵽��������Ĵأ�minNum����ȥ	    			    	
	    		 */
	    		int minNum = getMinNum(SSE);                          
	    		clusters.get(minNum).add(my_point[i]);				  
	    	}
	    	
	    	//��ʱ��������
	    	double[] new_avergx = new double[K];                                
	    	double[] new_avergy = new double[K];
	    	
	    	for(int i=0;i<K;i++){
	    		//����ÿһ���أ�������а��������ݵ����
	    		int size = clusters.get(i).size();
	    		//��ÿһ�����ݵ����Ϊsize�Ĵأ�������СΪsize�����鱣��������е������
	    		double[] X = new double[size];
	    		double[] Y = new double[size];
	    		for(int j=0;j<size;j++){	    			
	    			X[j] = clusters.get(i).get(j).getX();            
	    			Y[j] = clusters.get(i).get(j).getY();	    			
	    		}
	    		//���¼���ÿ���صĴ������꣬����ʱ����
	    		new_avergx[i] = averg(X,size);                     
	    		new_avergy[i] = averg(Y,size);	    		
	    	}
	    	
	    	/*
	    	 * �Ƚϸ���ǰ��Ĵ��ı䶯��Χ��
	    	 * ������ֵ֮�ڣ������ѭ����
	    	 * ���򣬴����Է����䶯������´�������
	    	 */
    		if(!isEqual(avergx, avergy, new_avergx, new_avergy)){
    			avergx = new_avergx ;                           
	    		avergy = new_avergy ;
    		} else {
    			break ;
    		}
    		t++;
	    }
	    
	    //***********************************���н�����*************************************
	    System.out.println("��ʼ�������ݵ����£�");
        for(int i=0;i<MAXNUM;i++){
	       System.out.println("("+my_point[i].getX()+", "+my_point[i].getY()+")");
	    }
        System.out.println();
        
        
        System.out.println("��ʼѡȡ��"+K+"���صĴ���Ϊ��");
        //ѡ���������ݵ��ǰK����Ϊ��ʼ����
        for(int i=0;i<K;i++){
 	       System.out.println("("+my_point[i].getX()+", "+my_point[i].getY()+")");
 	    }
        System.out.println();
        
        
        
        for(int i=0;i<K;i++){
        	//�����ÿһ���ص����ݵ�ĸ���
            int size = clusters.get(i).size();
            System.out.println("K-means�㷨�󣬵�"+(i+1)+"�����е�ĸ���Ϊ��"+size+",���ݵ�Ϊ��");

            //����ÿһ���أ�ÿ8�����ݵ㻻������������֮���ÿո����
    		for(int j =1;j<size+1;j++){
    			if(j%8==0){
    				System.out.println(clusters.get(i).get(j-1).toString());
    			}else{
    				System.out.print(clusters.get(i).get(j-1).toString()+"  ");
    				}
    			}
            System.out.println();
        }
        
        
        
        //�������ĵ㣬���ݻ��ֵĴز�ͬ�������**********************************************
        for(int i=1;i<K+1;i++){
        	//��ȡÿһ�����е����ݵ�ĸ���
        	int size = clusters.get(i-1).size();
        	/*
        	 * ָ���ֱ𱣴�ÿһ���ص�X��Y������ļ�·�����ļ���
        	 * ��cu��+i+��x.txt����ʾ��i��x�����ļ�
        	 * ��cu��+i+��x.txt����ʾ��i��y�����ļ�
        	 */
        	File xfile= new File("C:/Users/Zengli/Desktop/�����ھ�-ʵ�����ݼ�/test4/cu"+i+"x.txt");
        	File yfile= new File("C:/Users/Zengli/Desktop/�����ھ�-ʵ�����ݼ�/test4/cu"+i+"y.txt");
        	for(int j=0;j<size;j++){
        		//��ÿһ�����е��������ݵ��x��y�������ݷֱ�д��ָ���ļ���
        		ReadDataset.append(xfile, clusters.get(i-1).get(j).getX()+" ");
        		ReadDataset.append(yfile, clusters.get(i-1).get(j).getY()+" ");
            }
        }
  }
}
