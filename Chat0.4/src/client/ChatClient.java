package client;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

	public class ChatClient extends JFrame{
	
		private TextField tf = null;
		private TextArea ta = null;
		private Socket client =null;
		private DataOutputStream  dos = null;
		
		public static void main(String[] args) {
			new ChatClient().launch();
		}
	
		private void launch() {
			this.setTitle("client��");
			this.setLocation(300, 400);
			this.setSize(300, 400);
			/*
			 * �رմ��ڵ����ַ���
			 * */
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addWindowListener(new WindowAdapter(){//Ϊ�˹رմ���
			   public void windowClosing(WindowEvent e)
			   {
				   disconnect();
			       System.exit(0);
			   }
			  });
			
			tf=new TextField();
			ta=new TextArea();
			
			//ΪTextField���ӻس��¼���Ӧ
			tf.addActionListener(new ClientLisenter());
			
			this.add(BorderLayout.SOUTH,tf);
			this.add(BorderLayout.NORTH, ta);
			pack();//�����Զ���Ӧ��С��ʹ������������ʾ�������еĿؼ���
			this.setVisible(true);
			
			connect();
			
		}

		private void connect() {
			//�ͻ��������뱾����8888�˿ڽ���TCP���� 
			try {
				client = new Socket("127.0.0.1", 8888);
				client.setSoTimeout(10000); 
				dos = new DataOutputStream(client.getOutputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		}
		
		private void disconnect(){	
			
				try {
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
					 * �ж�TextArea���Ƿ�������,����У�����Ҫ�ȼ���һ�����з���
					 * Ȼ���ټ������ݣ�����ֱ�Ӽ�������
					 * */
					if(ta.getText().trim().length()!=0){
						ta.setText(ta.getText()+"\n"+content);
					}
					else{
						ta.setText(content);
					}
					
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