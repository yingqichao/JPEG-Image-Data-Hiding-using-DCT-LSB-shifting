package hideData;

import javax.swing.*;
import java.awt.GridLayout ;

public class run extends JFrame{
	private String host,secret,word;
	private static String globeHost,globeSecret;
	private int key;
	private JPanel jHead,jP1,jP2,jP3,jP4,jP5,jSum;
	private JLabel jL1,jL11,jL12,jL2,jL21,jL22,jL3,jL4,jLtitle,jL5;
	private JTextField jThost,jTsecret,jTkey,jTword,jTglobeHost,jTglobeSecret;
	private JButton jB;
     
    public static void main(String[] args) {
        new run();
    }
     
    //���캯��
    public run(){
        //����
        jHead = new JPanel();
        jLtitle = new JLabel("ͼ������ϵͳ ���԰�");jLtitle.setFont(new   java.awt.Font("Dialog",   1,   24));jHead.add(jLtitle);
        //��һ��
        jP1 = new JPanel();
        jThost = new JTextField(7);jTglobeHost = new JTextField(15);
        jL1 = new JLabel("��������ͼ��");jL1.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL11 = new JLabel(".jpg");jL11.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL12 = new JLabel("\\");jL12.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP1.add(jL1);jP1.add(jTglobeHost);jP1.add(jL12);jP1.add(jThost);jP1.add(jL11);
        //�ڶ���
        jP2 = new JPanel();
        jTsecret = new JTextField(7);jTglobeSecret = new JTextField(15);
        jL2 = new JLabel("��������ͼ��");jL2.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL21 = new JLabel(".jpg");jL21.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL22 = new JLabel("\\");jL22.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP2.add(jL2);jP2.add(jTglobeSecret);jP2.add(jL22);jP2.add(jTsecret);jP2.add(jL21);
        //������
        jP3 = new JPanel();
        jTkey = new JTextField(10);jL3 = new JLabel("�������룺");jL3.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP3.add(jL3);jP3.add(jTkey);
        //������
        jP4 = new JPanel();
        jTword = new JTextField(10);jL4 = new JLabel("���ֽ��ܣ�");jL4.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP4.add(jL4);jP4.add(jTword);
        //������
        jP5 = new JPanel();
        jB = new JButton("��ʼ����");jL5 = new JLabel("Tips:���ṩ�ļ�������·�������鿽����");jL5.setFont(new   java.awt.Font("Dialog",   1,   15));
        jB.addActionListener(new java.awt.event.ActionListener()
              {
                  public void actionPerformed(java.awt.event.ActionEvent e)
                  {
                	  host = jThost.getText().trim();secret = jTsecret.getText().trim();word = jTword.getText().trim();
                	  globeHost = jTglobeHost.getText().trim();globeSecret = jTglobeSecret.getText().trim();
                      key = Integer.parseInt(jTkey.getText().trim());
                      JFrame jf = new JFrame("���Դ���");
                      jf.setSize(400, 400);
                      jf.setLocationRelativeTo(null);
                      jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                      long startTime=System.currentTimeMillis(); 
                      Jpeglibrary a = new Jpeglibrary(host,secret,word,3,key,globeHost+"\\",globeSecret+"\\");//0:IMAGE,1:MESSAGE,2:BOTH
              			a.hide();
              			long endTime=System.currentTimeMillis();
                      JOptionPane.showMessageDialog(
                              jf,
                              "������ɣ���������ʱ�䣺 "+(endTime-startTime)+"ms",
                              "Message",
                              JOptionPane.INFORMATION_MESSAGE
                      );

                  }
              });
        jP5.add(jL5);jP5.add(jB);
        
        jSum = new JPanel();
        GridLayout grid = new GridLayout(6,2) ;
        jSum.setLayout(grid) ;
        jSum.add(jHead);jSum.add(jP1);jSum.add(jP2);jSum.add(jP3);jSum.add(jP4);jSum.add(jP5);
         
        this.add(jSum);
        this.setSize(500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

//import java.awt.event.*;
//import java.awt.*;
//import javax.swing.*;
//
//public class run extends JPanel{
//  static JFrame myFrame;
//  private JTextField jTextFieldUser;
////  private JButton jButtonInput;
//  
//  public run(){
//    setLayout(new GridLayout(2,3));
//
//    add(getJTextFieldUser(), null);
//    
//    add(getJButtonInput(), null);
//    add(getJButtonInput(), null);
//    
//  }
//  
//  private JTextField getJTextFieldUser()
//  {
//      if (jTextFieldUser == null)
//      {
//          jTextFieldUser = new JTextField();
//          jTextFieldUser.setBounds(new Rectangle(200, 15, 200, 15));
//      }
//      return jTextFieldUser;
//  }
//  private JButton getJButtonInput()
//  {
////      if (jButtonInput == null)
////      {
//          JButton jButtonInput = new JButton();
//          jButtonInput.setText("�������");
//          jButtonInput.setSize(new Dimension(100, 28));
//          jButtonInput.setLocation(new Point(50, 350));
//          jButtonInput.addActionListener(new java.awt.event.ActionListener()
//          {
//              public void actionPerformed(java.awt.event.ActionEvent e)
//              {
//                  String userId = jTextFieldUser.getText().trim();// ��ȡ�û���
//                  System.out.println(userId);
//              }
//          });
////      }
//      return jButtonInput;
//  }
//  
//  //��������Ӧ�ó������
//  public static void main(String args[]){
//    myFrame = new JFrame("�б�����Ͽ�ʵ��");
//    run jt = new run();
//    myFrame.getContentPane().add("Center",jt);
//    myFrame.setSize(200,200);
//    //�����Ϣӳ��
//    myFrame.addWindowListener(new WindowAdapter() {
//      public void windowClosing(WindowEvent e) {System.exit(0);}
//    });
//    //��Ϊ�ɼ�
//    myFrame.setVisible(true);
//  }
//}

//import javax.swing.*;
//import javax.swing.event.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class run extends JPanel {  
////��������
//ProgressThread progressThread;    
//static JFrame myFrame;
////���캯��
//public run() {    
//  setLayout(new BorderLayout());	  
//  JPanel buttonPanel = new JPanel();
//  JButton startButton = new JButton("Start");
//  buttonPanel.add(startButton);
//  startButton.addActionListener(new ActionListener() {
//    public void actionPerformed(ActionEvent e) {    
//    startRunning();    
//    }
//    });  
//
//  JButton stopButton = new JButton("Stop");
//  buttonPanel.add(stopButton);
//  stopButton.addActionListener(new ActionListener() {
//    public void actionPerformed(ActionEvent e) {      
//    stopRunning();    
//    }
//    });          
//  add(buttonPanel, BorderLayout.SOUTH);  
//} 
////���¿�ʼ��ť�Ķ���
//public void startRunning() {
//  if(progressThread == null|| !progressThread.isAlive()) {    
//    progressThread = new ProgressThread(this);
//    progressThread.start();    
//  }  
//}
////����ֹͣ��ť�Ķ���
//public void stopRunning() {       
//  progressThread.setStop(true);          
//}  
////��������������ڴ�
//public static void main(String args[]){
//  myFrame = new JFrame("������");
//  run progressMonitorExample = new run();
//  myFrame.getContentPane().add("Center",progressMonitorExample);
//  myFrame.setSize(200,100);    
//  myFrame.addWindowListener(new WindowAdapter() {
//    public void windowClosing(WindowEvent e) {
//    System.exit(0);
//    }
//    });
//  myFrame.setVisible(true);
//}
//}
//
////������
//class ProgressThread extends Thread {
//ProgressMonitor monitor;
//boolean stopStatus = false;
//int min = 0;    
//int max = 50;
////���캯��
//public ProgressThread(Component parent){
//  monitor = new ProgressMonitor(parent,"Progress of Thread","Not Started",min,max);
//}
////ֹͣ
//public void setStop(boolean value){
//  stopStatus = value;
//}
////����
//public void run () {
////  monitor.setNote("Started");
////  for (int x=min;x<=max;x++) {
////    if(stopStatus){
////      monitor.close();
////      break;
////    }else{
//////      monitor.setProgress(x);
//////      monitor.setNote(""+(x*2)+"%");
////      try {        
////        sleep(100);
////      } catch (InterruptedException e) { 
////        // Ignore Exceptions        
////      }   
////    }     
////  }    
//}
//} 



//package hideData;
//import java.io.*;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class run {
//
//	public static void main(String[] args) {
//         
//		Jpeglibrary a = new Jpeglibrary("small","tiger","",3,1234);//0:IMAGE,1:MESSAGE,2:BOTH
//		a.hide();
//		System.out.println("Done");
//		
////		System.out.println("Welcome to OpenCV" + Core.VERSION);
////        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
////        Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
////        System.out.println("m = " + m.dump());
//
//	}
//	
//	private static int[][] value = new int[][] { 
//        { 5, 9, 2 }, 
//        { 3, -4, 5 }, 
//        { 2, 5, 1 }, 
//        { -8, 3, 3 }
//       }; 
//    public static void write(String filepath)throws Exception{
//        OutputStream out=new FileOutputStream(filepath);
//       ObjectOutputStream oout=new ObjectOutputStream(out);
//       oout.writeObject(value); 
//    } 
//    public static int[][] read(String filepath)throws Exception{
//       InputStream in=new FileInputStream(filepath);
//       ObjectInputStream oin=new ObjectInputStream(in);
//       return (int[][])(oin.readObject());
//    }
//    public static void print(int[][] arr){
//       if(arr==null){
//          System.out.println("����Ϊ��!");
//          return;
//       }
//       int row=arr.length;
//       int col=arr[0].length;
//       System.out.println("{");
//       for(int i=0;i<row;i++){
//          System.out.print("{");
//          for(int j=0;j<col;j++){
//              System.out.print(arr[i][j]+" ");
//          }
//          System.out.print("}");
//          System.out.println(); 
//       }
//       System.out.println("}");
//    }
//	
//	
//}
