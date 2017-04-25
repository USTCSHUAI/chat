package client;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
			/*
			 * 关闭窗口的两种方法
			 * */
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addWindowListener(new WindowAdapter(){//为了关闭窗口
			   public void windowClosing(WindowEvent e)
			   {
			       System.exit(0);
			   }
			  });
			
			tf=new TextField();
			ta=new TextArea();
			
			//为TextField添加回车事件响应
			tf.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent event) {
					String content = tf.getText();
					/*
					 * 判断TextArea中是否有内容,如果有，则需要先加入一个换行符，
					 * 然后再加入内容，否则直接加入内容
					 * */
					if(ta.getText().trim().length()!=0){
						ta.setText(ta.getText()+"\n"+content);
					}
					else{
						ta.setText(content);
					}
					
					tf.setText("");
				}
			});
			
			this.add(BorderLayout.SOUTH,tf);
			this.add(BorderLayout.NORTH, ta);
			pack();//窗口自动适应大小，使窗口能正好显示里面所有的控件。
			this.setVisible(true);
			
		}
	
	}
