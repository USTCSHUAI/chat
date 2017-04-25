package client;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;

	public class ChatClient_v1 {
	
		private TextField tf =null;
		private TextArea ta = null;
		public static void main(String[] args) {
			new ChatClient_v1().launch();
		}
	
		private void launch() {
			JFrame frame=new JFrame();
			frame.setTitle("client¶Ë");
			frame.setLocation(300,400);
			frame.setSize(300,400);
			
			tf = new TextField();
			ta = new TextArea();
			frame.add(BorderLayout.SOUTH,tf);
			frame.add(BorderLayout.NORTH, ta);
			frame.pack();
			frame.setVisible(true);
		}
	
	}
