package hideData;

import java.io.*;
import java.util.ArrayList;
	import java.util.Arrays;

	public class Jpeglibrary {
		private static String globeHost;
		private static String globeSecret;
		private byte[] file;
		private byte[] water = null;
		private byte[] word = null;
		private byte[] kk = null;
		private byte[] dubious;
		private int[][] d2;
		private byte[] im1;
		private byte[] head;
		private Calctool Tool;
		private int[] size;
		private int block;
		private int[][] d0;
		private int[][] d1;
		private int[][] a0;
		private int[][] a1;
		private int[][] newa0=null;
		private int[][] newa1=null;
		private int[] treeSelect;
		private int[][] Q;
		private int DRI;
		private int color;
		private String im;
		private String w;
		private String hidePath;
		private String recoverPath = null;
		private String Description = null;
		private int RSTnum = 0;
		private int key = 10000;//表示没有设置密码
		private boolean changed = false;
		private int startOfHuff;
		private byte[] newHuff;
		private byte[] newHead;
		
		public Jpeglibrary(String a,String b,String description,int c,int password,String gH,String gS){
			Tool = new Calctool();
			d2 = new int[3][30];
			size = new int[2];
			treeSelect = new int[3];
			Q = new int[3][2];
			Description = description;
			color=c;im=a;w=b;
			key = password;
			globeHost = gH;globeSecret = gS;
			operation();
		}
		
		public void operation(){
			int a;int b;
			try{
				file = readStream(globeHost,im);}
			catch (Exception e){
				System.out.println("File Does Not Exist!");
			}
			d2=cutX(file);
			cutdata(file,d2);
			sof(head,d2);
			a=(int)Math.ceil((double)size[0]/8);b=(int)Math.ceil((double)size[1]/8);
			block=a*b;
			hfm(head,d2);
			if (color==3) 
				dri(head,d2);
			if (color==3) 
				sos(head,d2);//Selection of Huffman Tree for three color channels.
		}
		
		public void hide(){
			long startTime=System.currentTimeMillis(); 
			byte[] emb_im;int Qt = Q[0][0];
			HideScheme hide = new HideScheme(im1,d0,d1,a0,a1,newa0,newa1,treeSelect,size,color,DRI,Qt,key);
			if(w != null){
				try{
					water = readStream(globeSecret,w);}
				catch (Exception e){
					System.out.println("File Does Not Exist!");
				}
			}
			if(Description.length() != 0){
				try{
					word  = Description.getBytes("gbk");
					}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			emb_im = hide.hideinfo(water,word);
			
			if(changed){head=newHead;}
			
			dubious = new byte[head.length+emb_im.length];
			System.arraycopy(head, 0, dubious, 0, head.length);
			System.arraycopy(emb_im, 0, dubious, head.length, emb_im.length);

			hidePath = savePic(globeHost,im,dubious);
			long endTime=System.currentTimeMillis();
			System.out.println("Embed运行时间： "+(endTime-startTime)+"ms");
			startTime=System.currentTimeMillis(); 
			ExtractScheme exd = new ExtractScheme(hidePath,3,water);
			ArrayList<String> strs = exd.extract(key);
			
			System.out.println(strs.get(0));
			if(strs.size()>1){System.out.println(strs.get(1));}
			endTime=System.currentTimeMillis();
			System.out.println("Extract运行时间： "+(endTime-startTime)+"ms");
		}
		
		public static byte[] readStream(String a,String b) throws Exception{  
			String path = a+b+".jpg";
			FileInputStream fs = new FileInputStream(path);  
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];  
			int len = 0;  
			while (-1 != (len = fs.read(buffer))) {  
			outStream.write(buffer, 0, len);  
			}  
			outStream.close();  
			fs.close();  
			return outStream.toByteArray();  
		}
		
		private String savePic(String a,String c,byte[] b) {
			String path = a+c+".jpg";
			OutputStream out;
			try {
				out = new FileOutputStream(new File(path));
				out.write(b);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return path;
	    }
		
		public int[][] cutX(byte[] x){
			//找到所有关键字段位置，3行存储，1行标志，二行，三行起止点
			//另外，x中的数据都需要先unsignDecoder转为0-255
			int s = x.length;int k = 1;int i = 2;int j;
			d2[0][0]=Tool.unsignDecoder(x[1]);d2[1][0]=0;d2[2][0]=1;
			while(d2[0][k-1]!=218){
				d2[1][k]=i;
			    d2[0][k]=Tool.unsignDecoder(x[i+1]);
			    i=i+2+Tool.unsignDecoder(x[i+2])*256+Tool.unsignDecoder(x[i+3]);
			    d2[2][k]=i-1;
			    k=k+1; 
			}
			for (j=s-1;j<i;j--){
				    if((Tool.unsignDecoder(x[j-1])==255)&&(Tool.unsignDecoder(x[j])==217)) break;
			}
			
			d2[0][k]=217;d2[1][k]=i;d2[2][k]=j+1;
			
			return d2;
		}
		
		public void cutdata(byte[] x,int[][] d){
			int a =Tool.indexOf_1(d[0],218);
			int b =Tool.indexOf_1(d[0],217);
			int a1;int a2;int b1=0;
			head = Arrays.copyOfRange(x, 0, d[2][a]+1);
			byte[] im = Arrays.copyOfRange(x, d[1][b], d[2][b]-2);//-2:delete the last EOI message.
			byte[] im2 = new byte[im.length];
			int j=0;int i=0;//dynamically record the length of the revised sequence
			while(i<im.length){
				a1=im[i];if(i!=im.length-1) {a2=im[i+1];b1=Tool.unsignDecoder(a2);}
				if((i!=im.length-1)&&(Tool.unsignDecoder(im[i])==255)){
					switch(b1){
					case 0:im2[j]=im[i];j++;i+=2;break;//保存255，跳过后面的0
					case 255:i++;//无保存，仅跳过255
					case 208:im2[j]=im[i];j++;i++;RSTnum++;break;//保存这个RST字段
					case 209:im2[j]=im[i];j++;i++;RSTnum++;break;
					case 210:im2[j]=im[i];j++;i++;RSTnum++;break;
					case 211:im2[j]=im[i];j++;i++;RSTnum++;break;
					case 212:im2[j]=im[i];j++;i++;RSTnum++;break;
					case 213:im2[j]=im[i];j++;i++;RSTnum++;break;
					case 214:im2[j]=im[i];j++;i++;RSTnum++;break;
					case 215:im2[j]=im[i];j++;i++;RSTnum++;break;
					default:im2[j]=im[i+1];j++;i+=2;break;//跳过255，保存后面的非0/255
					}
				}
				else{
					im2[j]=im[i];j++;i++;
				}
			}
			im1=Arrays.copyOfRange(im2, 0, j);//delete zeros in the end of the sequence

		}
		
	    public void sof(byte[] x,int[][] d){
	    	int z = Tool.indexOf_1(d[0],192);
	    	int i = d[1][z];
	    	int[] temp = new int[19];
	    	for(int j=0;j<19;j++){
	    		temp[j]=Tool.unsignDecoder(x[j+i]);
	    	}
	    	int ph=i+5;int pw=i+7;
	    	size[0] =  Tool.unsignDecoder(x[ph])*256+Tool.unsignDecoder(x[ph+1]);
	    	size[1] =  Tool.unsignDecoder(x[pw])*256+Tool.unsignDecoder(x[pw+1]);
	    	i += 11;//skip some unused letters
	    	for(int j=0;j<3;j++){
	    		int k = Tool.unsignDecoder(x[i]);
	    		Q[j][0] = (k & 0xF0)/16;
	    		Q[j][1] = k & 0x0F;
	    		i += 3;
	    	}
	    }
	    
	    public void hfm(byte[] x,int[][] d){
	    	//可能有多个DHT字段,或者一个字段内有超过1张表
	      ArrayList res =Tool.indexOf(d[0],196);int thisLength=0;int totalOriLen = 0;int pointer;int pointerOrigin;boolean thisTreeNormal = true;
	      int huffLength;int[][] huffmanTree = null;
	      byte[] huffBuffer = new byte[4];byte[] d0buff=null;byte[] d1buff=null;byte[] a0buff=null;byte[] a1buff=null;
	      startOfHuff=(int) res.get(0);
	      for(int z=0;z<res.size();z++){
	    	int beginHuff=(int) res.get(z);huffLength = 0;
	    	pointer = d[1][beginHuff];pointerOrigin = d[1][beginHuff]+2;//please follow the straight-forward moving of this pointer
	    	thisLength = Tool.unsignDecoder(x[pointer+2])*256+Tool.unsignDecoder(x[pointer+3]);
	    	totalOriLen += thisLength+2;//算上了树前的2byte标识位
	    	int[] temp = new int[thisLength+4];
	    	for(int i=0;i<thisLength;i++){
	    		temp[i]=Tool.unsignDecoder(x[pointer+i]);
	    	}
	    	pointer += 4;
	    	while(huffLength<thisLength){
	    		int mode = Tool.unsignDecoder(x[pointer]);pointer += 1;thisTreeNormal = true;
	    		int[] huff_num = new int[16];int[] newhuff_num = new int[16];int total=0;
	    		for(int i=0;i<16;i++){//码字总个数
	    			huff_num[i] = x[pointer+i];total+=huff_num[i];
	    		}
	    		//先判断交流树是否完整，不完整则直接使用保存下来的树
	    		if(total<160 && (mode==16 || mode==17)){
	    			changed = true;thisTreeNormal = false;
	    			try{
	    				huffmanTree = read(mode+".txt");}
	    			catch(Exception e){System.out.println("Error in loading huffman tree!");}
	    				byte[] buff = new byte[huffmanTree[2].length+17];buff[0] = (byte)mode;
	    				for(int i=0;i<huffmanTree[0].length;i++){
	    					newhuff_num[huffmanTree[0][i]-1]++;
	    				}
	    				for(int i=0;i<16;i++){
	    					buff[i+1] = (byte)newhuff_num[i];
	    				}
	    				for(int i=0;i<huffmanTree[2].length;i++){
	    					buff[i+17] = (byte)huffmanTree[2][i];
	    				}
	    				if(mode==16){newa0 = huffmanTree;a0buff=buff;}
	    				else{ newa1 = huffmanTree;a1buff=buff;}

//	    			pointer += (16+total);huffLength += (pointer-pointerOrigin);pointerOrigin = pointer;
	    		}
	    			pointer +=16;int codePointer=0;int code=0;
	    			//直接拷贝正常树的二进制内容
	    			if(thisTreeNormal){
	    				switch(mode){
							case(0):d0buff = Arrays.copyOfRange(x, pointer-17, pointer+total);break;
							case(1):d1buff = Arrays.copyOfRange(x, pointer-17, pointer+total);break;
							case(16):a0buff = Arrays.copyOfRange(x, pointer-17, pointer+total);;break;
							case(17):a1buff = Arrays.copyOfRange(x, pointer-17, pointer+total);break;}
	    			}
	    			huffmanTree = new int[3][total];
	    			for(int i=0;i<16;i++){
	    				if(i!=0){
	    					code *= 2;
	    				}
	    				for(int j=0;j<huff_num[i];j++){
	    					huffmanTree[0][codePointer]=i+1;
	    					huffmanTree[1][codePointer]=code;
	    					huffmanTree[2][codePointer]=Tool.unsignDecoder(x[pointer+codePointer]);
	    					code++;codePointer++;
	    				}
	    			}
	    			huffLength += pointer + codePointer - pointerOrigin;pointer += codePointer;
	    			pointerOrigin = pointer;

	    			switch(mode){
	    			case(0):d0 = huffmanTree;break;
					case(1):d1 = huffmanTree;break;
					case(16):a0 = huffmanTree;break;
					case(17):a1 = huffmanTree;break;}
	    	}
		  }
	    	if(changed){
	    		int totalLen = d0buff.length+d1buff.length+a0buff.length+a1buff.length+2;//没有算上标识位的2byte
	    		newHead = new byte[head.length+totalLen+2-totalOriLen];newHuff = new byte[totalLen+2];
	    		newHuff[0] = (byte)255;newHuff[1] = (byte)196;newHuff[2] = (byte) (totalLen/256);newHuff[3] = (byte)(totalLen-256*newHuff[2]);
	    		System.arraycopy(d0buff, 0, newHuff, 4, d0buff.length);
	    		System.arraycopy(d1buff, 0, newHuff, 4+d0buff.length, d1buff.length);
	    		System.arraycopy(a0buff, 0, newHuff, 4+d0buff.length+d1buff.length, a0buff.length);
	    		System.arraycopy(a1buff, 0, newHuff, 4+d0buff.length+d1buff.length+a0buff.length, a1buff.length);
	    		System.arraycopy(head, 0, newHead, 0, d[1][startOfHuff]);	
	    		System.arraycopy(newHuff, 0, newHead, d[1][startOfHuff], newHuff.length);	
	    		System.arraycopy(head, d[1][startOfHuff+res.size()], newHead, d[1][startOfHuff]+newHuff.length, head.length-d[1][startOfHuff+res.size()]);	
	    	}
	    }
	    
	    
	    public int[][] read(String filepath) throws Exception{
	    	
	    		InputStream in=new FileInputStream(filepath);
	    	       ObjectInputStream oin=new ObjectInputStream(in);
	    	       return (int[][])(oin.readObject());
	
	    }
	    
	    public void dri(byte[] x,int[][] d){
	    	int z = Tool.indexOf_1(d[0],221);
	    	if(z!=-1){
	    	int pointer = d[1][z];
	    	int len = Tool.unsignDecoder(x[pointer+2])*256+Tool.unsignDecoder(x[pointer+3]);
	    	int[] temp = new int[len+2];
	    	for(int i=0;i<(len+2);i++){
	    		temp[i]=Tool.unsignDecoder(x[pointer+i]);
	    	}
	    	DRI = Tool.unsignDecoder(x[d[1][z]+4])*256+Tool.unsignDecoder(x[d[1][z]+5]);}
	    }
	    
	    public void sos(byte[] x,int[][] d){
	    	int z = Tool.indexOf_1(d[0],218);int a = d[1][z];
	    	int len = Tool.unsignDecoder(x[a+2])*256+Tool.unsignDecoder(x[a+3]);
	    	int[] temp = new int[len+2];
	    	for(int j=0;j<len+2;j++){
	    		temp[j]=Tool.unsignDecoder(x[j+a]);
	    	}
	    	
	    		int pointer = d[1][z]+6;
	    		for(int j=0;j<3;j++){
	    			treeSelect[j] = Tool.unsignDecoder(x[pointer]);
	    			pointer += 2;
	    		}
	    }
	    
	}

