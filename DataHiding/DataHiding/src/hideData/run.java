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
     
    //构造函数
    public run(){
        //标题
        jHead = new JPanel();
        jLtitle = new JLabel("图像隐藏系统 电脑版");jLtitle.setFont(new   java.awt.Font("Dialog",   1,   24));jHead.add(jLtitle);
        //第一行
        jP1 = new JPanel();
        jThost = new JTextField(7);jTglobeHost = new JTextField(15);
        jL1 = new JLabel("输入隐藏图像：");jL1.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL11 = new JLabel(".jpg");jL11.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL12 = new JLabel("\\");jL12.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP1.add(jL1);jP1.add(jTglobeHost);jP1.add(jL12);jP1.add(jThost);jP1.add(jL11);
        //第二行
        jP2 = new JPanel();
        jTsecret = new JTextField(7);jTglobeSecret = new JTextField(15);
        jL2 = new JLabel("输入隐藏图像：");jL2.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL21 = new JLabel(".jpg");jL21.setFont(new   java.awt.Font("Dialog",   1,   15));
        jL22 = new JLabel("\\");jL22.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP2.add(jL2);jP2.add(jTglobeSecret);jP2.add(jL22);jP2.add(jTsecret);jP2.add(jL21);
        //第三行
        jP3 = new JPanel();
        jTkey = new JTextField(10);jL3 = new JLabel("设置密码：");jL3.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP3.add(jL3);jP3.add(jTkey);
        //第四行
        jP4 = new JPanel();
        jTword = new JTextField(10);jL4 = new JLabel("文字介绍：");jL4.setFont(new   java.awt.Font("Dialog",   1,   15));
        jP4.add(jL4);jP4.add(jTword);
        //第五行
        jP5 = new JPanel();
        jB = new JButton("开始隐藏");jL5 = new JLabel("Tips:请提供文件的完整路径，建议拷贝。");jL5.setFont(new   java.awt.Font("Dialog",   1,   15));
        jB.addActionListener(new java.awt.event.ActionListener()
              {
                  public void actionPerformed(java.awt.event.ActionEvent e)
                  {
                	  host = jThost.getText().trim();secret = jTsecret.getText().trim();word = jTword.getText().trim();
                	  globeHost = jTglobeHost.getText().trim();globeSecret = jTglobeSecret.getText().trim();
                      key = Integer.parseInt(jTkey.getText().trim());
                      JFrame jf = new JFrame("测试窗口");
                      jf.setSize(400, 400);
                      jf.setLocationRelativeTo(null);
                      jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                      long startTime=System.currentTimeMillis(); 
                      Jpeglibrary a = new Jpeglibrary(host,secret,word,3,key,globeHost+"\\",globeSecret+"\\");//0:IMAGE,1:MESSAGE,2:BOTH
              			a.hide();
              			long endTime=System.currentTimeMillis();
                      JOptionPane.showMessageDialog(
                              jf,
                              "隐藏完成，本次运行时间： "+(endTime-startTime)+"ms",
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
//          jButtonInput.setText("输入完成");
//          jButtonInput.setSize(new Dimension(100, 28));
//          jButtonInput.setLocation(new Point(50, 350));
//          jButtonInput.addActionListener(new java.awt.event.ActionListener()
//          {
//              public void actionPerformed(java.awt.event.ActionEvent e)
//              {
//                  String userId = jTextFieldUser.getText().trim();// 获取用户名
//                  System.out.println(userId);
//              }
//          });
////      }
//      return jButtonInput;
//  }
//  
//  //主函数，应用程序入口
//  public static void main(String args[]){
//    myFrame = new JFrame("列表框和组合框实例");
//    run jt = new run();
//    myFrame.getContentPane().add("Center",jt);
//    myFrame.setSize(200,200);
//    //添加消息映射
//    myFrame.addWindowListener(new WindowAdapter() {
//      public void windowClosing(WindowEvent e) {System.exit(0);}
//    });
//    //设为可见
//    myFrame.setVisible(true);
//  }
//}

//import javax.swing.*;
//import javax.swing.event.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class run extends JPanel {  
////变量声明
//ProgressThread progressThread;    
//static JFrame myFrame;
////构造函数
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
////按下开始按钮的动作
//public void startRunning() {
//  if(progressThread == null|| !progressThread.isAlive()) {    
//    progressThread = new ProgressThread(this);
//    progressThread.start();    
//  }  
//}
////按下停止按钮的动作
//public void stopRunning() {       
//  progressThread.setStop(true);          
//}  
////主函数，程序入口处
//public static void main(String args[]){
//  myFrame = new JFrame("进程条");
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
////进程类
//class ProgressThread extends Thread {
//ProgressMonitor monitor;
//boolean stopStatus = false;
//int min = 0;    
//int max = 50;
////构造函数
//public ProgressThread(Component parent){
//  monitor = new ProgressMonitor(parent,"Progress of Thread","Not Started",min,max);
//}
////停止
//public void setStop(boolean value){
//  stopStatus = value;
//}
////运行
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
//          System.out.println("数组为空!");
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
