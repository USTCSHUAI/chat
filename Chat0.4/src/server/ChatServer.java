package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

	public class ChatServer {
	
		private ServerSocket server = null;
		
		public static void main(String[] args) {
			new ChatServer().start();
		}
		
		private void start(){
			boolean b_serverStart = false;
			try {
				//�������ص�ַ�Ҷ˿ں�Ϊ8888
				server = new ServerSocket(8888);
				b_serverStart = true;
			} catch (IOException e) {
				//����򿪶��Server�˵����
				System.out.println("��ر��Ѿ��򿪵�Server�ˣ����¿���");
				System.exit(0);
			}
			
			while(b_serverStart){
				//�ȴ��ͻ��˵����ӣ����û�л�ȡ����  
	            Socket client = null;
				try {
					client = server.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	            System.out.println("��ͻ������ӳɹ���");  
	          //Ϊÿ���ͻ������ӿ���һ���߳�  
	            new Thread(new ServerThread(client)).start();  
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
			private boolean b_connect = false;
			
			public  ServerThread(Socket client) {
				this.client=client;
				try {
					dis = new DataInputStream(this.client.getInputStream());
					b_connect=true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void run() { 
				
				try{
					while(b_connect){  
		                //���մӿͻ��˷��͹���������  
		                String str= dis.readUTF();
		                if(str == null || "".equals(str)){  
		                	b_connect = false;  
		                }else{  
		                    if("bye".equals(str)){  
		                    	b_connect = false;  
		                    }else{  
		                        //�����յ����ַ���ǰ��������������̨
		                        System.out.println(str);
		                    }  
		                }  
		            }
				}
				catch(IOException e){
					System.out.println("client�˹ر���");
					//e.printStackTrace();
				}
				finally{
					try{
						if(dis!=null) dis.close();
						if(client!=null) client.close();
					}
					catch(IOException e){
						e.printStackTrace();
					}				
				}
			}			
		}
	
	}
