package hideData;
import java.util.ArrayList;
import java.util.Arrays;

public class HideScheme {
	private char[] im;
	private byte[] file;
	private int[][] zigZag;
	private char[] water;
	private int[][] d0;
	private int[][] d1;
	private int[][] a0;
	private int[][] a1;
	private int[][] newa0;
	private int[][] newa1;
	private int[] treeSelect;
	private int row;
	private byte[] finale;
	private int col;
	private int block;
	private Calctool T;
	private int[] temp;
	private int color;
	private int Bpp;
	private int DRI;
	private int Q;
	private int key;
	private final int picLen = 22;
	private final int infoLen = 8;

	public HideScheme(byte[] f,int[][]D0,int[][] D1,int[][] A0,int[][] A1,int[][] NEWA0,int[][] NEWA1,int[] tr,int[] s,int c,int dri,int Qt,int password){
		T = new Calctool();DRI=dri;
		file = f;
		key = password;
		Q=Qt;
		im=T.byte2char(f);
		finale = new byte[im.length*2];
		temp = new int[f.length];
		for(int j=0;j<f.length;j++){
			temp[j]=T.unsignDecoder(f[j]);
		}
		d0=D0;d1=D1;a0=A0;a1=A1;treeSelect=tr;color=c;newa0=NEWA0;newa1=NEWA1;
		row=(int)Math.ceil((double)s[0]/8/Q);col=(int)Math.ceil((double)s[1]/8/Q);//row,col分别是行和列上有多少MCU
		zigZag = T.iZigZag();
	}


	public byte[] hideinfo(byte[] w,byte[] word){
		int[][] DCT;int[] DC0 = new int[3];char[] DCcode;int mode = 255;
		if(word!=null && w!=null) mode =253;
		else if (word!=null) mode = 254;
		int nz = 0;ArrayList res;StringBuilder temp = new StringBuilder();
		int flag = 0;int payload = 0;boolean ex=false;boolean preprocess;
		int finale_pointer = 0;int pt = 22;
		//共50位验证信息， 开始8位为检测是否有隐藏信息的0xFF，key为4位10进制，14bit，后面22位表示water的字节,最多隐藏4M数据，并允许隐藏256B文字信息
		char[] tt = null;char[] ts = null;char[] wt = null;char[] ws = null;char[] check = T.int2char(mode,8);
		char[] kk = T.int2char(key,14);
		if(w!=null){
			tt=T.byte2char(w);ts=T.int2char((w.length),picLen);
			}
		if(word!=null){
			wt=T.byte2char(word);ws=T.int2char((word.length),infoLen);
		}
		
		water = new char[((tt==null)?0:tt.length)+((ts==null)?0:ts.length)+((wt==null)?0:wt.length)+((ws==null)?0:ws.length)+14+8];
		System.arraycopy(check, 0, water, 0, 8);
		System.arraycopy(kk, 0, water, 8,14);
		if(w!=null) {
			System.arraycopy(ts, 0, water, pt, picLen);
			System.arraycopy(tt, 0, water, picLen+pt, tt.length);
			pt += picLen+tt.length;}
		if(word!=null) {
			System.arraycopy(ws, 0, water, pt, infoLen);
			System.arraycopy(wt, 0, water, infoLen+pt, wt.length);
			pt += infoLen+wt.length;}

		block=(row*Q)*(col*Q);
		//不修改包括直流的前15个DCT,只修改后49位的所有DCT(不管是否非0)
//		nz = nonZeros(im);//先统计非零DCT系数的个数
		Bpp = (int)Math.ceil((double)(water.length)/(double)(49*block));
		for (int j = 0; j < row; j += 1) {
			for (int k = 0; k < col; k += 1) {
				//Y通道
				for(int zy=1;zy<=Math.pow(Q,2);zy++){
					preprocess = ( DRI!=0 && (j*col+k)!=0 && (j*col+k)%DRI==0 && zy == 1);
					res = DCTread(im,flag,treeSelect[0],DC0[0],j+1,k+1,zy,preprocess);
					DCT = (int[][]) res.get(0);
					flag += (int) res.get(1);
					DC0[0] = DCT[0][0];
					DCcode = (char[]) res.get(2);
					embeddedData(water,payload,DCT,treeSelect[0],DCcode,ex,j+1,k+1,temp,zy,preprocess);
					finale_pointer += dataWriter(temp,finale_pointer,preprocess);
					if(j==0 && k==0 && zy == 1){payload += 48*Bpp;}
					else{payload += 49*Bpp;}
					if(payload>water.length)
					{ex=true;}
				}
				if (color==3){
					//Cr Cb通道
					for (int zz=1;zz<=2;zz++){
						res = DCTread(im,flag,treeSelect[zz],DC0[zz],j+1,k+1,0,false);
						DCT = (int[][]) res.get(0);
						flag += (int) res.get(1);
						DC0[zz] = DCT[0][0];
						DCcode = (char[]) res.get(2);
						embeddedData(water,payload,DCT,treeSelect[zz],DCcode,ex,j+1,k+1,temp,0,false);
						finale_pointer += dataWriter(temp,finale_pointer,false);
						payload += 49;
						if(payload>water.length){ex=true;}
					}
				}
			}
		}
		if(temp.length()!=0){
			int len = temp.length();
			for(int i=0;i<8-len;i++){
				temp.append((char)49);}
			finale_pointer += dataWriter(temp,finale_pointer,false);
		}
		
		finale[finale_pointer]=(byte)255;finale_pointer++;
		finale[finale_pointer]=(byte)217;finale_pointer++;
		return Arrays.copyOfRange(finale, 0, finale_pointer);
	}
	
	public int dataWriter(StringBuilder a,int finale_pointer,boolean preprocess){
		int moveForward = 0;int res;
		while(a.length()>=8){
			res = T.bin2dec_string(a.toString(),0,8);
			finale[finale_pointer+moveForward] = (byte)res;
			a.delete(0, 8);
			moveForward++;
			if(finale[finale_pointer+moveForward-1]==-1) //也就是byte里的255
			{
				if(preprocess && T.bin2dec_string(a.toString(),0,8)>=208 && T.bin2dec_string(a.toString(),0,8)<=215)
				{preprocess=false;}
				else {
					finale[finale_pointer+moveForward]=0;moveForward++;}
			}
		}
		return moveForward;
	}

	public void embeddedData(char[] w, int payload,int[][] DCT,int select,char[] DCcode,boolean ex,int r,int c,StringBuilder emb,int Qnum,boolean pre){
		//先修改DCT，再根据新的DCT进行新的编码
		int[][] ac = null;int runLength = 0;int codeLength = 0;
		int runCode = 0;String huffcode = new String();int index;String code = new String();String temp = new String();
		int[][] newDCT;

		//嵌入完毕后，按之前的DCT来嵌入数据
		if (ex==false){newDCT = DCTembed(DCT,w,payload,r,c,select,Qnum);}
		else {newDCT=DCT;}
//		newDCT=DCT;

		//行首预处理
		if(pre){
			if(emb.length()%8!=0){
				int len = emb.length();
				for(int i=0;i<8-len%8;i++){
					emb.append((char)49);
				}
			}
			temp = T.int2bit(255,8);temp = temp.concat(T.int2bit(208+(r-2)%8,8));
			emb.append(temp);
		}

		if(r==1 && c==1 && (select==0 || select==1) && Qnum == 1){payload += 48*Bpp;}
		else{payload += 49*Bpp;}

		switch(select){
			case(0):ac = (newa0==null)?a0:newa0;break;
			case(1):ac = (newa1==null)?a1:newa1;break;
			case(16):ac = (newa0==null)?a0:newa0;break;
			case(17):ac = (newa1==null)?a1:newa1;break;
		}
		//复制直流码字
		for (int i=0;i<DCcode.length;i++){
			emb.append(DCcode[i]);
		}
		//交流编码
		for(int j=1;j<64;j++){
			//计算游程长度
			if (newDCT[zigZag[j][0]][zigZag[j][1]]==0){
				runLength += 1;
			}
			else{
				while(runLength>=16){
					index = T.indexOf_1(ac[2],16*15);
					huffcode = T.int2bit(ac[1][index],ac[0][index]);
					emb.append(huffcode);
					runLength -= 16;         
					
					
					
				}
				codeLength = T.howManyBits(newDCT[zigZag[j][0]][zigZag[j][1]]);runCode = runLength*16 + codeLength;
				index = T.indexOf_1(ac[2],runCode);//有可能会得到新的码字组合
				huffcode = T.int2bit(ac[1][index],ac[0][index]);
				code = T.int2bit(T.signDecoder(newDCT[zigZag[j][0]][zigZag[j][1]],codeLength),codeLength);
				emb.append(huffcode);
				emb.append(code);
				runLength = 0;
			}
			//判断到最后一个DCT时是否还存在游程长度
			if(j==63 && runLength!=0){
				index = T.indexOf_1(ac[2],0);
				huffcode = T.int2bit(ac[1][index],ac[0][index]);
				emb.append(huffcode);
			}
		}
//		return emb;

	}

	public int[][] DCTembed(int[][] coeff, char[] w,int payload,int r,int c,int select,int Qnum){
		int temp;int LSB;boolean flag = false;
		for(int i=15;i<64;i++){
			if (payload+Bpp*(i-15)>w.length){flag=true;break;}
			if(r==1 && c==1 && i==63 && (select==0 || select==1) && Qnum==1){coeff[zigZag[i][0]][zigZag[i][1]] = Bpp;}
			else{
				temp = coeff[zigZag[i][0]][zigZag[i][1]];
				coeff[zigZag[i][0]][zigZag[i][1]] = temp-temp%((int)Math.pow(2, Bpp));
				LSB = 0;
				for(int j=0;j<Bpp;j++){
					if (payload+Bpp*(i-15)+j+1>w.length){flag=true;break;}
					LSB += (w[payload+Bpp*(i-15)+j]==48)?0:(Math.pow(2, j));//低位在前
				}
				coeff[zigZag[i][0]][zigZag[i][1]] += LSB;
				if (flag) break;
			}
		}
		return coeff;
	}

	public ArrayList DCTread(char[] im,int flag,int select, int DC0,int r,int c,int Qnum,boolean pre){
		//返回系数，指针，直流code
		char[] DCcode;int[] redundant = new int[5];
		ArrayList res = new ArrayList(2);int[][] ac = null; int[][] dc = null;int[][] coeff = new int[8][8];int pointer = 0;
		int[] ans;int wordLen;int zeroLen;int diff;int ACnum = 1;int dct;int t1;int t2;int skipStep;int bias = 0;int wrong;
		switch(select){
			case(0):ac = a0;dc = d0;break;
			case(1):ac = a1;dc = d0;break;
			case(16):ac = a0;dc = d1;break;//低位ac，高位dc
			case(17):ac = a1;dc = d1;break;
		}
		//DC
		if(pre){
			//必须满足：1.DRI不是0.  2.当前MCU块（由r、c算出）是DRI的正整数倍。  3.当前是直流。  4.如果Y的采样比CrCb密集，则必须是第一个Y的采样块
			if(flag%8!=0){
				skipStep = flag%8;
				pointer +=8-skipStep;bias +=8-skipStep;
			}
			t1 = T.bin2dec_str(im,pointer+flag,8);
			t2 = T.bin2dec_str(im,pointer+flag+8,8);
			DC0=0;//bias是将flag与pointer在这里对齐
			if(t1==255 && t2>=208 && t2<=215){
				pointer +=16;bias +=16;}
		}
		ans = T.huffmanDecoder(im,pointer+flag,dc,row,col);
		if(ans[0]==-1){
			//如果进入到这个if里，说明码字读取错误，需要作进一步检查
			//一般地，不会进入到这个if，建议在这个if里设置断点，以防程序挂掉
			wrong = 1;
			t1 = T.bin2dec_str(im,pointer+flag,8);
			t2 = T.bin2dec_str(im,pointer+flag+8,8);
		}

		pointer += ans[0];wordLen = ans[1];
		diff = T.i_unsignDecoder(T.bin2dec_str(im,pointer+flag,wordLen),wordLen);
		coeff[0][0]= DC0 + diff;
		pointer += wordLen;
		if(pre)
		{DCcode=Arrays.copyOfRange(im, bias+flag, pointer+flag);}
		else{DCcode=Arrays.copyOfRange(im, flag, pointer+flag);}
		//AC
		while(ACnum<=63){
			ans = T.huffmanDecoder(im,pointer+flag,ac,row,col);
			pointer += ans[0];
			if(ans[1]==0){//
				break;}
			zeroLen = (ans[1]&(0xF0))/16;wordLen = ans[1]&(0x0F);
			for(int j=0;j<zeroLen;j++){
				coeff[zigZag[ACnum][0]][zigZag[ACnum][1]] = 0;
				ACnum ++;
			}
			dct = T.i_unsignDecoder(T.bin2dec_str(im,pointer+flag,wordLen),wordLen);
			pointer += wordLen;

			coeff[zigZag[ACnum][0]][zigZag[ACnum][1]] = dct;
			ACnum ++;
		}

		res.add(coeff);
		res.add(pointer);
		res.add(DCcode);
		res.add(redundant);
		return res;

	}
}
