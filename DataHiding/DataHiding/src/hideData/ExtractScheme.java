package hideData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ExtractScheme {
	private boolean firstMode = true;
    private byte[] file;
    private byte[] water;
    private byte[] dubious;
    private byte[] word;
    private int[][] d2;
    private byte[] im1;
    private byte[] head;
    private Calctool Tool;
    private int[] size;
    private int[][] d0;
    private int[][] d1;
    private int[][] a0;
    private int[][] a1;
    private int[] treeSelect;
    private int[][] Q;
    private int DRI;
    private int color;
    private String path;
    private String recoverPath;
    private int row;
    private int col;
    private int[][] zigzag;
    private char[] im;
    private int finale_pointer = 0;
    private int length = 0;
    private int wordLen = 0;
    private int key;
    private int mode;
    private static int HIDE_IMAGE = 0;
	private static int HIDE_MESSAGE = 1;
	private static int BOTH = 2;

    public ExtractScheme(String p,int c,byte[] w){
        Tool = new Calctool();
        water = w;
        d2 = new int[3][30];
        size = new int[2];
        treeSelect = new int[3];
        Q = new int[3][2];
        color=c;path=p;
        operation();
        zigzag = Tool.iZigZag();
    }

    public void operation(){
        int a;int b;
        try{
            file = readStream(path);}
        catch (Exception e){
            System.out.println("File Does Not Exist!");
        }
        d2=cutX(file);
        cutdata(file,d2);
        sof(head,d2);
        a=(int)Math.ceil((double)size[0]/8);b=(int)Math.ceil((double)size[1]/8);
        hfm(head,d2);
        if (color==3) dri(head,d2);
        if (color==3) sos(head,d2);//Selection of Huffman Tree for three color channels.
        im = Tool.byte2char(im1);
    }

    public ArrayList<String> extract(int password){
        int Qt = Q[0][0];key = password;ArrayList<String> ret = new ArrayList<String>();
        ArrayList res;int[][] DCT;int flag = 0;int[] DC0 = new int[3];StringBuilder a = new StringBuilder();
        boolean ex = false;int Bpp = 0;char[] cmp;
        row=(int)Math.ceil((double)size[0]/8/Qt);col=(int)Math.ceil((double)size[1]/8/Qt);
        for (int j = 0; j < row; j += 1) {
        	if(ex) break;
            for (int k = 0; k < col; k += 1) {
                //Y
            	if(ex) break;
                for(int zy=1;zy<=Math.pow(Qt,2);zy++){
                    res = DCTread(im,flag,treeSelect[0],DC0[0],j+1,k+1,zy);
                    DCT = (int[][]) res.get(0);DC0[0] = DCT[0][0];
                    flag += (int) res.get(1);
                    res = dataExtract(DCT,Bpp);
                    if(j==0 && k==0 && zy==1)//第一块确定嵌入信息长度
                    {Bpp = (int)res.get(1);
                    length = ((int)res.get(2));
                    if(length==Integer.MAX_VALUE){res.add("NO WATERMARK");return ret;}
                    else if(length==Integer.MAX_VALUE-1){res.add("WRONG KEYWORD");return ret;}
                    dubious = new byte[length+Bpp*20];
                    }
                    a.append((String)res.get(0));
                    finale_pointer += dataWriter(a,finale_pointer);
                    if(finale_pointer>=(length+wordLen)) {ex=true;break;}
                }
                //CrCb
                if(ex) break;
                if (color==3){
                    //Cr Cb通道
                    for (int zz=1;zz<=2;zz++){
                        res = DCTread(im,flag,treeSelect[zz],DC0[zz],j+1,k+1,0);
                        DCT = (int[][]) res.get(0);DC0[zz] = DCT[0][0];
                        flag += (int) res.get(1);
                        res = dataExtract(DCT,Bpp);
                        a.append((String)res.get(0));
                        finale_pointer += dataWriter(a,finale_pointer);
                        if(finale_pointer>=(length+wordLen)) {ex=true;break;}
                    }
                }
            }
        }
        	if(mode == HIDE_IMAGE){recoverPath = savePic(dubious);ret.add(recoverPath);return ret;}	//在安卓端这里是不需要的，直接返回即可
        	else if(mode == HIDE_MESSAGE){
        		recoverPath = "文字隐藏信息：" + new String(dubious);ret.add(recoverPath);return ret;
        	}
        	else{
        		String pth = savePic(dubious);
        		String wString = new String(word);
//        		recoverPath = "图文信息："+ pth + "。文字：" + wString;
        		ret.add(pth);ret.add(wString);return ret;
        	}
    }
    
    public int dataWriter(StringBuilder a,int finale_pointer){
		int moveForward = 0;int res;
		while(a.length()>=8){
			if(mode == BOTH && finale_pointer+moveForward==length && firstMode){
				wordLen = Tool.bin2dec_string(a.toString(),0,8);
				firstMode = false;word = new byte[wordLen];
			}
			else{
				res = Tool.bin2dec_string(a.toString(),0,8);
				if(firstMode) dubious[finale_pointer+moveForward] = (byte)res;
				else if(finale_pointer+moveForward-length-1<wordLen) word[finale_pointer+moveForward-length-1] = (byte)res;}
			a.delete(0, 8);
			moveForward++;
		}
		return moveForward;
	}

    public ArrayList dataExtract(int[][] D,int bpp){
        ArrayList res = new ArrayList(2);StringBuilder temp = new StringBuilder();char a;int b;int len=0;int last = 64;
        if(bpp==0){bpp=D[7][7];last = 63;}
        for(int i=15;i<last;i++){
            b = D[zigzag[i][0]][zigzag[i][1]];
            for(int j=0;j<bpp;j++){
                a = (char) (((b&(0x01<<j))>>j)+48);
                temp.append(a);
            }
        }
        if(last == 63){
        	if(bpp==0){len = Integer.MAX_VALUE;}
        	else{
        		String check = temp.substring(0,8);String password = temp.substring(8,8+14);
        		if(Tool.str2int(check,8)!=255 && Tool.str2int(check,8)!=254 && Tool.str2int(check,8)!=253) len = Integer.MAX_VALUE;
        		else if(Tool.str2int(password,14)!=key) len = Integer.MAX_VALUE-1;
        		else{
        			mode = 255-Tool.str2int(check,8);
        			String length = temp.substring(22,44);
        			len = Tool.str2int(length, 22);
        			}
        		temp.delete(0,44);
        	}
        }
        res.add(temp.toString());
        if(last == 63){
            res.add(bpp);
            res.add(len);
        }
        return res;
    }

    public ArrayList DCTread(char[] im,int flag,int select, int DC0,int r,int c,int Qnum){
        //返回系数，指针，直流code
        char[] DCcode;int[] redundant = new int[5];
        ArrayList res = new ArrayList(2);int[][] ac = null; int[][] dc = null;int[][] coeff = new int[8][8];int pointer = 0;
        int[] ans;int wordLen;int zeroLen;int diff;int ACnum = 1;int dct;int t1;int t2;int here;int skipStep;int bias = 0;
        switch(select){
            case(0):ac = a0;dc = d0;break;
            case(1):ac = a1;dc = d0;break;
            case(16):ac = a0;dc = d1;break;//低位ac，高位dc
            case(17):ac = a1;dc = d1;break;
        }
        //DC
        if(DRI!=0 && ((r-1)*col+c-1)!=0 && ((r-1)*col+c-1)%DRI==0 && (select==0 || select==1) && Qnum == 1){
            if(flag%8!=0){
                skipStep = flag%8;
                pointer +=8-skipStep;bias +=8-skipStep;
            }
            pointer +=16;DC0=0;bias +=16;
        }
        ans = Tool.huffmanDecoder(im,pointer+flag,dc,row,col);
        if(ans[0]==-1){
            t1 = Tool.bin2dec_str(im,pointer+flag,8);
            t2 = Tool.bin2dec_str(im,pointer+flag+8,8);
        }

        pointer += ans[0];wordLen = ans[1];
        diff = Tool.i_unsignDecoder(Tool.bin2dec_str(im,pointer+flag,wordLen),wordLen);
        coeff[0][0]= DC0 + diff;
        pointer += wordLen;

        if(DRI!=0 && ((r-1)*col+c-1)!=0 && ((r-1)*col+c-1)%DRI==0 && (select==0 || select==1) && Qnum == 1)
        {DCcode=Arrays.copyOfRange(im, bias+flag, pointer+flag);}
        else{DCcode=Arrays.copyOfRange(im, flag, pointer+flag);}
        //AC
        while(ACnum<=63){
            ans = Tool.huffmanDecoder(im,pointer+flag,ac,row,col);
            pointer += ans[0];
            if(ans[1]==0){//
                break;}
            zeroLen = (ans[1]&(0xF0))/16;wordLen = ans[1]&(0x0F);
            for(int j=0;j<zeroLen;j++){
                coeff[zigzag[ACnum][0]][zigzag[ACnum][1]] = 0;
                ACnum ++;
            }
            dct = Tool.i_unsignDecoder(Tool.bin2dec_str(im,pointer+flag,wordLen),wordLen);
            pointer += wordLen;

            coeff[zigzag[ACnum][0]][zigzag[ACnum][1]] = dct;
            ACnum ++;
        }

        res.add(coeff);
        res.add(pointer);
        res.add(DCcode);
        res.add(redundant);
        return res;

    }
		
		public static byte[] readStream(String a) throws Exception{  
//			String path = "C:\\Users\\yqc_s\\Desktop\\myjpeg0105final\\jpeg\\".concat(a).concat(".jpg");
			String path = a;
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
		
		private String savePic(byte[] b) {
			String path = "C:\\Users\\yqc_s\\Desktop\\myjpeg0105final\\jpeg\\".concat("decode.jpg");
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
			int b =Tool.indexOf_1(d[0],217);int bug;
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
					case 208:im2[j]=im[i];j++;i++;break;//保存这个RST字段
					case 209:im2[j]=im[i];j++;i++;break;
					case 210:im2[j]=im[i];j++;i++;break;
					case 211:im2[j]=im[i];j++;i++;break;
					case 212:im2[j]=im[i];j++;i++;break;
					case 213:im2[j]=im[i];j++;i++;break;
					case 214:im2[j]=im[i];j++;i++;break;
					case 215:im2[j]=im[i];j++;i++;break;
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
	      ArrayList res =Tool.indexOf(d[0],196);int thisLength;int pointer;int pointerOrigin;
	      int a;int huffLength;
	      for(int z=0;z<res.size();z++){
	    	a=(int) res.get(z);huffLength = 0;
	    	pointer = d[1][a];pointerOrigin = d[1][a]+2;//please follow the straight-forward moving of this pointer
	    	thisLength = Tool.unsignDecoder(x[pointer+2])*256+Tool.unsignDecoder(x[pointer+3]);
	    	int[] temp = new int[thisLength+4];
	    	for(int i=0;i<thisLength;i++){
	    		temp[i]=Tool.unsignDecoder(x[pointer+i]);
	    	}
	    	pointer += 4;
	    	while(huffLength<thisLength){
	    		int mode = Tool.unsignDecoder(x[pointer]);pointer += 1;
	    		int[] huff_num = new int[16];int total=0;
	    		for(int i=0;i<16;i++){//码字总个数
	    			huff_num[i] = x[pointer+i];total+=huff_num[i];
	    		}
	    		pointer +=16;int codePointer=0;int code=0;
	    		int[][] huffmanTree = new int[3][total];
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
	    			case(17):a1 = huffmanTree;break;
	    		}
	    	}
		  }
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