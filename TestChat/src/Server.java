

import java.io.*;
import java.net.*;
import java.util.*;

//提供网络服务
public class Server {
	ServerSocket serverSocket = null;
	Socket socket = null;
	Boolean serverStartFlag = false;
	Boolean acceptFlag = false;
	List<Socket> Clientsocket = new ArrayList<Socket>();
    int i =1;
    String read_str = "";
	
	Server(){
		
			try {
				serverSocket = new ServerSocket(8800);
				System.out.println("服务器已启动...");
				serverStartFlag = true;
				while (serverStartFlag) {
					socket = serverSocket.accept();
					//把客户端的socket存到集合里
					Clientsocket.add(socket);
					acceptFlag = true;
					System.out.println(i++ + "个客户端已经连接" + socket);
					DataTransfer dt = new DataTransfer(socket);
					new Thread(dt).start();
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//建立子线程类，与客户端进行数据通信
	private class DataTransfer implements Runnable{
		Socket socket = null;
		DataInputStream dis = null;
		
		public DataTransfer(Socket socket){
			this.socket = socket;
			try {
				dis = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				while(true){
				read_str  = dis.readUTF();
				for(int i =0;i<Clientsocket.size();i++){
					DataOutputStream dos = new DataOutputStream(Clientsocket.get(i).getOutputStream());
					dos.writeUTF(read_str);
				}
				System.out.println(read_str);
				}   
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}

