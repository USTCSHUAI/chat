package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

	public class ChatServer {
	
		private ServerSocket server=null;
		
		
		public static void main(String[] args) {
			new ChatServer().start();
		}
		
		private void start(){
			boolean b_serverStart = false;
			try {
				//监听本地地址且端口号为8888
				server = new ServerSocket(8888);
				b_serverStart = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(b_serverStart){
				//等待客户端的连接，如果没有获取连接  
	            Socket client = null;
				try {
					client = server.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	            System.out.println("与客户端连接成功！");  
			}
		
		}
		
	//	private class ServerThread implements Runnable{
	//		private Socket client = null;
	//		
	//		public  ServerThread(Socket client) {
	//			this.client=client;
	//		}
	//		@Override
	//		public void run() {
	//			
	//		}
	//		
	//	}
	
	}
