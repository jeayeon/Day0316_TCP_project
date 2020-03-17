package Client;

import java.net.Socket;

public class ClinetMain {

	public static void main(String[] args) throws Exception {
		
		Socket WithServer = new Socket("10.0.0.115",7890);
		new GameStart(WithServer);
		
		
		

	}

}
