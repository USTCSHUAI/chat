package client;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

	public class ChatClient extends JFrame{
	
		private TextField tf = null;
		private TextArea ta = null;
		
		private Socket client =null;
		private DataOutputStream  dos = null;
		private DataInputStream dis = null;
		
		private boolean b_conn =false;
		
		public static void main(String[] args) {
			new ChatClient().launch();
		}
	
		private void launch() {
			this.setTitle("client端");
			this.setLocation(300, 400);
			this.setSize(300, 400);
			/*
			 * 关闭窗口的两种方法
			 * */
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addWindowListener(new WindowAdapter(){//为了关闭窗口
			   public void windowClosing(WindowEvent e)
			   {
				   disconnect();
			       System.exit(0);
			   }
			  });
			
			tf=new TextField();
			ta=new TextArea();
			
			//为TextField添加回车事件响应
			tf.addActionListener(new ClientLisenter());
			
			this.add(BorderLayout.SOUTH,tf);
			this.add(BorderLayout.NORTH, ta);
			pack();//窗口自动适应大小，使窗口能正好显示里面所有的控件。
			this.setVisible(true);
			
			connect();
			//连接上了之后，开启一个线程来进行接收服务器端的数据
			
			new Thread(new RecvThread()).start();
			
		}
		
		private class RecvThread implements Runnable{

			@Override
			public void run() {
				while(b_conn){
					String str = null;
					//从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常  
					try {
						str = dis.readUTF();
						//将信息写入到TextArea中
						ta.setText(ta.getText()+str+'\n');
					}catch(SocketException e1){
						System.out.println("bye");
					}catch (EOFException e) {
						System.out.println("推出了，bye - bye!");
					}catch (IOException e) {
						System.out.println("我关闭了也");
						e.printStackTrace();
					}
				}
			}
			
		}
		
		private void connect() {
			//客户端请求与本机在8888端口建立TCP连接 
			try {
				client = new Socket("127.0.0.1", 8888);
				//client.setSoTimeout(10000); 
				dos = new DataOutputStream(client.getOutputStream());
				dis = new DataInputStream(this.client.getInputStream());
				b_conn = true;
			} catch (UnknownHostException e) {
				System.out.println("没有找到需要连接的主机！！");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		}
		
		private void disconnect(){	
			
				try {
					b_conn = false;
					if(dos!=null) dos.close();
					if(client!=null) client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
		
		private class ClientLisenter implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent event) {
					String content = tf.getText();
					/*
					 * 判断TextArea中是否有内容,如果有，则需要先加入一个换行符，
					 * 然后再加入内容，否则直接加入内容
					 * */
					//由于服务器端会广播其发送的消息，因此，就不需要再这里完成显示了
//					if(ta.getText().trim().length()!=0){
//						ta.setText(ta.getText()+"\n"+content);
//					}
//					else{
//						ta.setText(content);
//					}
//					
					tf.setText("");
					
					try {
						dos.writeUTF(content);
						dos.flush();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
			
				}
			}
		
	
	}
