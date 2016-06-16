package kMeans;
/**
 * @author 曾丽  
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
	 * K-均值聚类
	 * @param K
	 * @param MaxNum
	 * @param SSE
	 * @param avergx[]
	 * @param avergy[]
	 * @param t
	 * @return clusters
	 */
	public static Random random = new Random() ;
	
	//定义初始和最后的簇的个数
	public static final int K = 7 ;
	
	//定义数据点的个数
//	public static final int MAXNUM = 10000;							

	/**
	 * 计算每一个数据点到每一个簇心的距离
	 */
    public static double distance(double x1,double y1,double x2,double y2){         
    	return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
    
    /**
     * 每一个数据点到第几个簇心的距离最小，返回下标
     * @param a
     * @return
     */
    public static int getMinNum(double a[]){                           
    	//初始化最小距离为一个很大的数
    	double Min=Math.exp(4000.0); 			                        
    	int j=0;
    	for(int i=0;i<a.length;i++){
    		if(a[i]<Min){
    			Min=a[i];
    			j = i;
    		}
    	}
    	//返回到第几个簇心距离最小的这个序列号，下标从0开始
    	return j;                                                       
    }
    
    /**
     * 重新计算簇心坐标
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
     * 判断迭代聚类前后的簇心位置是否不变,
     * 只要有一个簇心变动范围大于阈值，则返回false；若变动范围小于一个阈值，可视为不变，返回true
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
     * 主函数
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException{
		//从数据集文件中获取数据点的个数，保存在MAXNUM中
		ReadDataset rds = new ReadDataset();
		int MAXNUM = rds.getTextLines();
		
		//创建一个Point对象，保存读取到的数据集中的数据点
		Point[] my_point = new Point[MAXNUM]; 
		my_point = rds.getDataset();
		
		//clusters中存放每一个簇的列表list
		ArrayList<ArrayList<Point>> clusters = new ArrayList<ArrayList<Point>>() ;       
	
		//创建一个数组，用来保存每次迭代后的K个簇心的X和Y坐标
		double[] avergx = new double[K];                                
    	double[] avergy = new double[K];
    	
    	//初始化簇心
    	for(int i=0; i < K ; i ++){
//    		avergx[i] = my_point[(i*(MAXNUM/K))+random.nextInt()*((i+1)*(MAXNUM/K)-(i*(MAXNUM/K))+1)].getX();
//    		avergx[i] = my_point[(i*(MAXNUM/K))+random.nextInt()*((i+1)*(MAXNUM/K)-(i*(MAXNUM/K))+1)].getX();
    		
    		avergx[i] = my_point[i].getX() ;
    		avergy[i] = my_point[i].getY() ;
    	}	    
	    
    	/*
    	 * *******************************K-Means算法核心********************************
    	 * 开始循环迭代进行聚簇，循环终止条件有两个：
    	 * 1.迭代次数t超过某一给定阈值
    	 * 2.迭代前后所有簇心的位置变动范围（距离）小于某一给定阈值，可视为簇心不再变动
    	 * 只要有一个条件满足，就结束循环
    	 */
    	
    	//定义迭代次数t，并初始化为0。当迭代次数在给定阈值之内时循环
	    int t=0;
	    while(t<100){
	    	//每次重新进行聚簇前，先将clusters中清空
	    	clusters.clear();
	    	// 将每一个数据点(包括簇心点)分派到离它最近的一个簇中
	    	for(int i=0;i<MAXNUM;i++){                                    
	    		for(int m = 0 ; m < K ; m ++){
	    			/*
	    			 * 对于每一个簇生成一个list列表，共生成K个列表
	    			 * 将这K个列表全部加入到clusters中去
	    			 */
	    			ArrayList<Point> list = new ArrayList<Point>(); 
	    			clusters.add(list);                                      
	    		}
	    		double SSE[] = new double[K];
	    		for(int j=0;j<K;j++){
	    			/*
	    			 * 在随机生成的数据点中选前K个作为初始簇心，
	    			 * 计算每个数据点（包括簇心点）到K个簇心的距离，
	    			 * 簇心点到自身的距离最小为0，可放入自身的簇中
	    			 */
	    			SSE[j] = distance(avergx[j],avergy[j],my_point[i].getX(),my_point[i].getY());      		                                                                            
	    		}
	    		/*
	    		 * 获取当前数据点到第几个簇心距离最短，返回下标minNum，下标从0开始计算
	    		 * 将当前数据点（my_point[i]）加入到离它最近的簇（minNum）中去	    			    	
	    		 */
	    		int minNum = getMinNum(SSE);                          
	    		clusters.get(minNum).add(my_point[i]);				  
	    	}
	    	
	    	//临时保存质心
	    	double[] new_avergx = new double[K];                                
	    	double[] new_avergy = new double[K];
	    	
	    	for(int i=0;i<K;i++){
	    		//对于每一个簇，计算簇中包含的数据点个数
	    		int size = clusters.get(i).size();
	    		//对每一个数据点个数为size的簇，创建大小为size的数组保存簇中所有点的坐标
	    		double[] X = new double[size];
	    		double[] Y = new double[size];
	    		for(int j=0;j<size;j++){	    			
	    			X[j] = clusters.get(i).get(j).getX();            
	    			Y[j] = clusters.get(i).get(j).getY();	    			
	    		}
	    		//重新计算每个簇的簇心坐标，并临时保存
	    		new_avergx[i] = averg(X,size);                     
	    		new_avergy[i] = averg(Y,size);	    		
	    	}
	    	
	    	/*
	    	 * 比较更新前后的簇心变动范围，
	    	 * 若在阈值之内，则结束循环；
	    	 * 否则，簇心仍发生变动，则更新簇心坐标
	    	 */
    		if(!isEqual(avergx, avergy, new_avergx, new_avergy)){
    			avergx = new_avergx ;                           
	    		avergy = new_avergy ;
    		} else {
    			break ;
    		}
    		t++;
	    }
	    
	    //***********************************运行结果输出*************************************
	    System.out.println("初始所有数据点如下：");
        for(int i=0;i<MAXNUM;i++){
	       System.out.println("("+my_point[i].getX()+", "+my_point[i].getY()+")");
	    }
        System.out.println();
        
        
        System.out.println("初始选取的"+K+"个簇的簇心为：");
        //选择所有数据点的前K个作为初始簇心
        for(int i=0;i<K;i++){
 	       System.out.println("("+my_point[i].getX()+", "+my_point[i].getY()+")");
 	    }
        System.out.println();
        
        
        
        for(int i=0;i<K;i++){
        	//计算出每一个簇的数据点的个数
            int size = clusters.get(i).size();
            System.out.println("K-means算法后，第"+(i+1)+"个簇中点的个数为："+size+",数据点为：");

            //对于每一个簇，每8个数据点换行输出，点与点之间用空格隔开
    		for(int j =1;j<size+1;j++){
    			if(j%8==0){
    				System.out.println(clusters.get(i).get(j-1).toString());
    			}else{
    				System.out.print(clusters.get(i).get(j-1).toString()+"  ");
    				}
    			}
            System.out.println();
        }
        
        
        
        //将聚类后的点，根据划分的簇不同进行输出**********************************************
        for(int i=1;i<K+1;i++){
        	//获取每一个簇中的数据点的个数
        	int size = clusters.get(i-1).size();
        	/*
        	 * 指定分别保存每一个簇的X、Y坐标的文件路径和文件名
        	 * ”cu”+i+”x.txt”表示簇i的x坐标文件
        	 * ”cu”+i+”x.txt”表示簇i的y坐标文件
        	 */
        	File xfile= new File("C:/Users/Zengli/Desktop/数据挖掘-实验数据集/test4/cu"+i+"x.txt");
        	File yfile= new File("C:/Users/Zengli/Desktop/数据挖掘-实验数据集/test4/cu"+i+"y.txt");
        	for(int j=0;j<size;j++){
        		//将每一个簇中的所有数据点的x和y坐标数据分别写入指定文件中
        		ReadDataset.append(xfile, clusters.get(i-1).get(j).getX()+" ");
        		ReadDataset.append(yfile, clusters.get(i-1).get(j).getY()+" ");
            }
        }
  }
}
