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
			this.setTitle("client端");
			this.setLocation(300, 400);
			this.setSize(300, 400);
			
			tf=new TextField();
			ta=new TextArea();
			
			this.add(BorderLayout.SOUTH,tf);
			this.add(BorderLayout.NORTH, ta);
			pack();//窗口自动适应大小，使窗口能正好显示里面所有的控件。
			this.setVisible(true);
			
		}
	
	}
