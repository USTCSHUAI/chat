package client;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;

	public class ChatClient extends JFrame{
	
		private TextField tf=null;
		private TextArea ta=null;
		public static void main(String[] args) {
			new ChatClient().launch();
		}
	
		private void launch() {
			this.setTitle("client��");
			this.setLocation(300, 400);
			this.setSize(300, 400);
			
			tf=new TextField();
			ta=new TextArea();
			
			this.add(BorderLayout.SOUTH,tf);
			this.add(BorderLayout.NORTH, ta);
			pack();//�����Զ���Ӧ��С��ʹ������������ʾ�������еĿؼ���
			this.setVisible(true);
			
		}
	
	}
