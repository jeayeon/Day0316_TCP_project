package Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static void main(String[] args) throws Exception {
		
		
		
		ServerSocket Server =null;
		Socket WithClient = null;
		
		
		Server = new ServerSocket();
		Server.bind(new InetSocketAddress("10.0.0.115",7890));
		
		while(true) {
			System.out.println("클라이언트 대기중");
			WithClient = Server.accept();// 위드클라이언트라는 참조변수로 클라이언트와 연결고리를 만듦
			System.out.println("클라이언트 정보   ip :"+WithClient.getInetAddress()+"port number : "+WithClient.getPort());
			
			GameCenter g = new GameCenter(WithClient);
			g.start();
			
		}
		
		
		

	}

}
