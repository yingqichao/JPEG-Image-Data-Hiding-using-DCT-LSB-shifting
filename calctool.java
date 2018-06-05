package test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class calctool {
	
	public int unsignDecoder(int a){//固定8位
		int b=a;
		if(a<0){
			b=255+a+1;
		}
		return b;
	}
	
	public int i_unsignDecoder(int a,int num){
		//注意：由于项目本身需求原因，这个函数并不是unsignDecoder的严格逆函数,下面的函数才是这个函数的逆函数
		//For Example——码字：00000,……,01111,10000,……,11111，输出：-31,……,-16,16,……,31
		//位数可自定义
		int b=a;
		if(num==0){b=0;}//这是AC交流表的结束符，很重要
		else if(a<Math.pow(2,num-1)){
			b=(int) -(Math.pow(2,num)-1-a);
		}
		return b;
	}
	
	public int signDecoder(int a,int num){
		int b=a;
		if(num==0){b=0;}
		else if(a<0){
			b=a+(int)Math.pow(2,num)-1;
		}
		return b;
	}
	
	public ArrayList indexOf(int[] a,int b){
		//数组中存在多个要找的值
		ArrayList index = new ArrayList();
		for(int j=0;j<a.length;j++){
			if (a[j]==b) 
			{index.add(j);}
		}
		if(index.size()==0){index.add(-1);}
		return index;
	}
	
	public int indexOf_1(int[] a,int b){
		//如果不会有重复或者只想找到第一个，或者只想查询是否存在，用这个
		for(int j=0;j<a.length;j++){
			if (a[j]==b) return j;
		}
		return -1;
	}
	
	public int indexOfChar_1(char[] a,int b){
		//同上，无法合并，https://stackoverflow.com/questions/50493361/java-to-write-a-method-that-can-be-applied-on-any-kind-of-array
		for(int j=0;j<a.length;j++){
			if (a[j]==b) return j;
		}
		return -1;
	}
	
	public String byteToBit(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }  
	
	public int[][] iZigZag() 
	{
		//Given index of row and col, output those in zigZag mode
		int[][] zigZag = new int[64][2];
		zigZag[0][0] = 0; // 0,0
		zigZag[0][1] = 0;
		zigZag[1][0] = 0; // 0,1
		zigZag[1][1] = 1;
		zigZag[2][0] = 1; // 1,0
		zigZag[2][1] = 0;
		zigZag[3][0] = 2; // 2,0
		zigZag[3][1] = 0;
		zigZag[4][0] = 1; // 1,1
		zigZag[4][1] = 1;
		zigZag[5][0] = 0; // 0,2
		zigZag[5][1] = 2;
		zigZag[6][0] = 0; // 0,3
		zigZag[6][1] = 3;
		zigZag[7][0] = 1; // 1,2
		zigZag[7][1] = 2;
		zigZag[8][0] = 2; // 2,1
		zigZag[8][1] = 1;
		zigZag[9][0] = 3; // 3,0
		zigZag[9][1] = 0;
		zigZag[10][0] = 4; // 4,0
		zigZag[10][1] = 0;
		zigZag[11][0] = 3; // 3,1
		zigZag[11][1] = 1;
		zigZag[12][0] = 2; // 2,2
		zigZag[12][1] = 2;
		zigZag[13][0] = 1; // 1,3
		zigZag[13][1] = 3;
		zigZag[14][0] = 0; // 0,4
		zigZag[14][1] = 4;
		zigZag[15][0] = 0; // 0,5
		zigZag[15][1] = 5;
		zigZag[16][0] = 1; // 1,4
		zigZag[16][1] = 4;
		zigZag[17][0] = 2; // 2,3
		zigZag[17][1] = 3;
		zigZag[18][0] = 3; // 3,2
		zigZag[18][1] = 2;
		zigZag[19][0] = 4; // 4,1
		zigZag[19][1] = 1;
		zigZag[20][0] = 5; // 5,0
		zigZag[20][1] = 0;
		zigZag[21][0] = 6; // 6,0
		zigZag[21][1] = 0;
		zigZag[22][0] = 5; // 5,1
		zigZag[22][1] = 1;
		zigZag[23][0] = 4; // 4,2
		zigZag[23][1] = 2;
		zigZag[24][0] = 3; // 3,3
		zigZag[24][1] = 3;
		zigZag[25][0] = 2; // 2,4
		zigZag[25][1] = 4;
		zigZag[26][0] = 1; // 1,5
		zigZag[26][1] = 5;
		zigZag[27][0] = 0; // 0,6
		zigZag[27][1] = 6;
		zigZag[28][0] = 0; // 0,7
		zigZag[28][1] = 7;
		zigZag[29][0] = 1; // 1,6
		zigZag[29][1] = 6;
		zigZag[30][0] = 2; // 2,5
		zigZag[30][1] = 5;
		zigZag[31][0] = 3; // 3,4
		zigZag[31][1] = 4;
		zigZag[32][0] = 4; // 4,3
		zigZag[32][1] = 3;
		zigZag[33][0] = 5; // 5,2
		zigZag[33][1] = 2;
		zigZag[34][0] = 6; // 6,1
		zigZag[34][1] = 1;
		zigZag[35][0] = 7; // 7,0
		zigZag[35][1] = 0;
		zigZag[36][0] = 7; // 7,1
		zigZag[36][1] = 1;
		zigZag[37][0] = 6; // 6,2
		zigZag[37][1] = 2;
		zigZag[38][0] = 5; // 5,3
		zigZag[38][1] = 3;
		zigZag[39][0] = 4; // 4,4
		zigZag[39][1] = 4;
		zigZag[40][0] = 3; // 3,5
		zigZag[40][1] = 5;
		zigZag[41][0] = 2; // 2,6
		zigZag[41][1] = 6;
		zigZag[42][0] = 1; // 1,7
		zigZag[42][1] = 7;
		zigZag[43][0] = 2; // 2,7
		zigZag[43][1] = 7;
		zigZag[44][0] = 3; // 3,6
		zigZag[44][1] = 6;
		zigZag[45][0] = 4; // 4,5
		zigZag[45][1] = 5;
		zigZag[46][0] = 5; // 5,4
		zigZag[46][1] = 4;
		zigZag[47][0] = 6; // 6,3
		zigZag[47][1] = 3;
		zigZag[48][0] = 7; // 7,2
		zigZag[48][1] = 2;
		zigZag[49][0] = 7; // 7,3
		zigZag[49][1] = 3;
		zigZag[50][0] = 6; // 6,4
		zigZag[50][1] = 4;
		zigZag[51][0] = 5; // 5,5
		zigZag[51][1] = 5;
		zigZag[52][0] = 4; // 4,6
		zigZag[52][1] = 6;
		zigZag[53][0] = 3; // 3,7
		zigZag[53][1] = 7;
		zigZag[54][0] = 4; // 4,7
		zigZag[54][1] = 7;
		zigZag[55][0] = 5; // 5,6
		zigZag[55][1] = 6;
		zigZag[56][0] = 6; // 6,5
		zigZag[56][1] = 5;
		zigZag[57][0] = 7; // 7,4
		zigZag[57][1] = 4;
		zigZag[58][0] = 7; // 7,5
		zigZag[58][1] = 5;
		zigZag[59][0] = 6; // 6,6
		zigZag[59][1] = 6;
		zigZag[60][0] = 5; // 5,7
		zigZag[60][1] = 7;
		zigZag[61][0] = 6; // 6,7
		zigZag[61][1] = 7;
		zigZag[62][0] = 7; // 7,6
		zigZag[62][1] = 6;
		zigZag[63][0] = 7; // 7,7
		zigZag[63][1] = 7;
		return zigZag;
	}
	
	public char[] byte2char(byte[] f){
		char[] str = new char[f.length*8];int k=0;
		for(int i=0;i<f.length;i++){
            for(int j=1;j<=8;j++){
                str[k]=(char) (((f[i]&(0x01<<(8-j)))>>(8-j))+48);
                k++;
            }
        }
		
		return str;
	}
	
	public int[] huffmanDecoder(char[] f,int flag,int[][] tree,int row,int col){
		//根据哈夫曼树来寻找码字对应的权重与位数
		//row col是正在执行这个函数的图中块的行、列，仅用于debug
		//res0是当前遍历位数，res1是找到的权值
		int[] res = new int[2];boolean found = false;int index;int i;

			for(i=int_min(tree[0]);i<=int_max(tree[0]);i++){
				index = indexOf_1(tree[1],bin2dec_str(f,flag,i));
				if(index != -1 && tree[0][index]== i){
					res[0] = i;res[1] = tree[2][index];
					found = true;break;
				}
			}
			if(found==false){res[0] = -1;res[1] = -1;}//要求上级程序强制跳过用户自定义字段

		
		return res;
		
	}
	
	public int bin2dec_str(char[] a,int flag,int len){
		int res = 0;int i;
		for (i=0;i<len;i++){
			if(a[flag+i]==49){
				res += Math.pow(2, len-1-i);
			}
		}
		return res;
	}
	
	public boolean twoDimensionalEqual(int[][] a, int b[][]) {
		  if (a.length != b.length) {
		   return false;
		  } else {
		   for (int i = 1; i < a.length; i++) {
			   //为了项目需要，从第二行开始看
		    if (a[i].length != b[i].length) {
		     return false;
		    } else {
		     if (!Arrays.equals(a[i], b[i])) {
		      return false;
		     }
		    }
		   }
		  }
		  return true;
		 }

	
	public int howManyBits(int a){
		//观察一个整数a至少需要多少位二进制来表示
		int b=0;
		for(int i=1;i<=100;i++){
			if(Math.abs(a)<Math.pow(2,i)){b=i;break;}
		}
		return b;
	}
	
	public byte[] str2byte_control(String a,int[] rst){
		//这个函数遇到0xFF会进行特殊处理
		int len = (int) (8*Math.ceil((double)a.length()/8));
		char[] b = new char[len];byte[] res = new byte[len/8+5000];
		for(int i=0;i<len;i++){
			if(i<a.length()){b[i] = a.charAt(i);}
			else{b[i]=(char)49;}//强制末尾补1
		}
		int cum = 0;int buff;int buff2=0;
		for(int i=0;i<b.length/8;i++){
			buff=bin2dec_str(b,i*8,8);if(i!=b.length/8-1) {buff2=bin2dec_str(b,(i+1)*8,8);}
			res[cum] = (byte)buff;cum++;
			if((i!=b.length-1)&&(buff==255)){
				if(buff2<208 || buff2>215){
					res[cum]=0;cum++;
				}
				else if(indexOf_1(rst,i)==-1){
					res[cum]=0;cum++;
			    }
			}
		}
				
		return Arrays.copyOfRange(res, 0, cum);
	}
	
	public byte[] str2byte(String a){
		int len = (int) (8*Math.ceil((double)a.length()/8));int buff = 0;
		char[] b = new char[len];byte[] res = new byte[len/8];
		for(int i=0;i<len;i++){
			if(i<a.length()){b[i] = a.charAt(i);}
			else{b[i]=(char)49;}//强制末尾补1
		}
		for(int i=0;i<b.length/8;i++){
			buff=bin2dec_str(b,i*8,8);
			res[i] = (byte)buff;
		}
		
		return res;
		
	}
	
	public boolean arrayEqualZero(int[] a){
		for(int i=0;i<a.length;i++){
			if(a[i]!=0) return false;
		}
		return true;
	}
	
	public int[] combineArrays(int[] a,int[] b){
		//找出最后一个非零
		int ind = 0;
		for(int i=0;i<a.length;i++){
			if(a[i]!=0) ind=i;
		}
		int[] c=a;
		System.arraycopy(a, 0, c, 0, ind+1);
		System.arraycopy(b, 0, c, ind+1, b.length);
		return c;
	}
	
	public char[] int2bit(int a,int len){
		//将码字对应的int重新变回长度为len的码字
		char[] res = new char[len];
		for(int i=0;i<len;i++){
			if(a>=Math.pow(2, len-1-i)){a -= Math.pow(2, len-1-i);res[i] = 49;}
			else{res[i] = 48;}
		}
		return res;
		
	}
	
	public char[] str2char(String a){
		char[] b = new char[a.length()];
		for(int i=0;i<a.length();i++){
			b[i]=a.charAt(i);
		}
		return b;
	}
	
	public int int_min(int[] array){  
        int minValue = array[0];  
        for (int i = 0; i<array.length;i++){  
            if (array[i]<minValue)  
                minValue = array[i];  
        }  
        return minValue;  
    }  
	
	public char[] charcmp(char[] a,char[]b){
		int len = Math.min(a.length,b.length);
		char[] c = new char[len];
		for(int i= 0;i<len;i++){
			if(a[i]!=b[i]){c[i] = (char)49;}
			else{c[i] = (char)48;}
		}
		return c;
	}
	
	public int int_max(int[] array){  
        int maxValue = array[0];  
        for (int i = 0; i<array.length;i++){  
            if (array[i]>maxValue)  
                maxValue = array[i];  
        }  
        return maxValue;  
    }  
	
	public String int2str(int a){
		//int转8位二进制
		String b = new String();
		char[] c = int2bit(a,8);
		for(int i=0;i<c.length;i++){
			b=b+c[i];
		}
		return b;
	}
	public int str2int(String a,int num){
		int res = 0;
		for(int i=0;i<num;i++){
			if(a.charAt(i)==49) res+=Math.pow(2,num-1-i);
		}
		return res;
	}
}
