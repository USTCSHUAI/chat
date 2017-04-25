

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class ChatClient extends JFrame{
	JPanel p_north = new JPanel();
	JPanel p_center = new JPanel();
	JPanel p_south = new JPanel();
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	JButton bt_clear_ta = new JButton("清屏");
	JButton bt_clear_tf = new JButton("重写");
	JButton bt_save_chatlog = new JButton("保存聊天记录");
	JLabel l_info = new JLabel();
	String title = "";
	Socket socket = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	boolean readFlag = false;
	String read_str = "";
	//String message = tf.getText().trim();
	
	//构造方法
	ChatClient(String title){
		serverConnect();
		setSize(600,500);
		setLocation(200,200);
		setVisible(true);
		setTitle(title);
		this.add(p_north,BorderLayout.NORTH);
		this.add(p_center,BorderLayout.CENTER);
		this.add(p_south,BorderLayout.SOUTH);
		
		p_north.setLayout(new BorderLayout());
		p_north.add(ta,BorderLayout.NORTH);
		p_north.add(tf,BorderLayout.SOUTH);
		
		p_center.setLayout(new FlowLayout(1,30,10));
		p_center.add(bt_clear_ta);
		p_center.add(bt_clear_tf);
		p_center.add(bt_save_chatlog);
		
		p_south.setLayout(new FlowLayout(1));
		p_south.add(l_info);
		tf.requestFocus();
		ta.setEditable(false);
		pack();
		
		MyActionListener ml = new MyActionListener();
		tf.addActionListener(ml);
		bt_clear_ta.addActionListener(ml);
		bt_clear_tf.addActionListener(ml);
		bt_save_chatlog.addActionListener(ml);
	}
	//连接服务器
	public void serverConnect(){
		try {
			socket = new Socket("127.0.0.1", 8800);
			readFlag = true;
			l_info.setText("已经连接至服务器:" + socket);				
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			ClientThread ct = new ClientThread();
			new Thread(ct).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
		l_info.setText("服务器连接失败 ！");
			
		}
	}
	
	//建立侦听器
	private class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//发送信息到server
			if(arg0.getSource().equals(tf)){
				 //if (message == null || message.equals("")) {
					//JOptionPane.showMessageDialog(null,"消息不能为空！", "提示",
							//JOptionPane.INFORMATION_MESSAGE);
				//}
				try {
					dos.writeUTF(tf.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				tf.setText(" ");
				tf.setText("");
			}
			//清空聊天记录
			else if(arg0.getSource().equals(bt_clear_ta)){
				ta.setText(" ");
				ta.setText("");
			}
			//重写
			else if(arg0.getSource().equals(bt_clear_tf)){
				tf.setText(" ");
				tf.setText("");
			}
			//保存聊天记录
			else if(arg0.getSource().equals(bt_save_chatlog)){
				try {
					FileWriter fw = new FileWriter("d:\\123.txt");
					fw.write(ta.getText());
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				l_info.setText("聊天记录已保存在d:\\123.txt ！");
			}
		}
		
	}

	//建立子线程类，读取服务器子线程发送过来的数据并写入TextArea
	private class ClientThread implements Runnable{

		public void run() {
			try {
				while(readFlag){
				read_str = dis.readUTF();
				ta.append(read_str+'\r' + '\n');
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}

