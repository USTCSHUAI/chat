package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	public class ChatServer {
	
		private ServerSocket server = null;
		
		private List<ServerThread> serverThreads = new ArrayList<ServerThread>();
		
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
				//解决打开多个Server端的情况
				System.out.println("请关闭已经打开的Server端，重新开启");
				System.exit(0);
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
	          //为每个客户端连接开启一个线程  
	            ServerThread st = new ServerThread(client);
	            serverThreads.add(st);
	            new Thread(st).start();  
			}
			
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
		private class ServerThread implements Runnable{
			private Socket client = null;
			private DataInputStream dis = null;
			private DataOutputStream dos = null;
			private boolean b_connect = false;
			
			public  ServerThread(Socket client) {
				this.client=client;
				try {
					dis = new DataInputStream(this.client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream()); 
					b_connect=true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void run() { 
				
				try{
					while(b_connect){  
		                //接收从客户端发送过来的数据  
		                String str= dis.readUTF();
		                if(str == null || "".equals(str)){  
		                	b_connect = false;  
		                }else{  
		                    if("bye".equals(str)){  
		                    	b_connect = false;  
		                    }else{  
		                        //将接收到的字符串前面加上输出到控制台
		                        System.out.println(str);
		                        //将Client端发送过来的数据广播出去
		                        for(int i=0;i<serverThreads.size();i++){
		                        	ServerThread st = serverThreads.get(i);
		                        	st.dos.writeUTF(str);
		                        }
		                        
		                    }  
		                }  
		            }
				}
				catch(IOException e){
					System.out.println("client端关闭了");
					//e.printStackTrace();
				}
				finally{
					try{
						if(dis!=null) dis.close();
						if(dos!=null) dos.close();
						if(client!=null) client.close();
					}
					catch(IOException e){
						e.printStackTrace();
					}				
				}
			}			
		}
	
	}
