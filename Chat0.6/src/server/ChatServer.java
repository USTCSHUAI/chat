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
		                        //��Client�˷��͹��������ݹ㲥��ȥ
		                        for(int i=0;i<serverThreads.size();i++){
		                        	ServerThread st = serverThreads.get(i);
		                        	st.dos.writeUTF(str);
		                        }
		                        
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
