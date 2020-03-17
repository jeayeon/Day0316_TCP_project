package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GameCenter extends Thread {

	private Socket WithClient = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;	
	private String id = null;
	private ArrayList <String> member = new ArrayList<>();
	private ArrayList <String> Glist = new ArrayList<>();
	private StringTokenizer st = null;
	private String[] menu = {"1. 게임리스트","2. 요금정산"};
	
	
	GameCenter(Socket s) {
		WithClient = s;
		member.add("aa/11");
		member.add("bb/22");
		member.add("cc/33");
		Glist.add("롤/50");
		Glist.add("배그/60");
		Glist.add("피파/40");

	}


	@Override
	public void run() {
		Login();
		receive();
		//send();

	}


	private void send(String msg) { //객체의 직렬화 심심하면 찾아보기
			if(menu[0].equals(msg)) {// 클라이언트에서 게임리스트를 눌렀을때
				String list ="";
				for(int i =0;i<Glist.size();i++) {
					String game = Glist.get(i);
					list = list+(i+1)+". "+game+"\n";
				}
				try {
					byte[] Msg = list.getBytes();
					sendMsg.write(Msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}


	private void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						byte[] msg = new byte[200];
						reMsg.read(msg);
						String Msg =new String(msg).trim();
						send(Msg);
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
			}
		}).start();
		
		
	}


	private void Login() {
		try {
			while(true){
				reMsg = WithClient.getInputStream();
				byte[] Msg = new byte[100];
				reMsg.read(Msg);
				id = new String(Msg).trim();
				
				st = new StringTokenizer(id,"/");
				String memberId = st.nextToken();
				String memberPwd = st.nextToken();
				
				String end = null;
				for(int i = 0; i<member.size();i++) { 
					st = new StringTokenizer(member.get(i),"/");
					String mId = st.nextToken();
					String mPwd = st.nextToken();
					if(mId.equals(memberId)&&mPwd.equals(memberPwd)) {
						sendMsg = WithClient.getOutputStream();
						byte[] msg = "로그인 완료".getBytes();
						sendMsg.write(msg);
						end = new String(msg).trim();
						break;
						
					}
					
				}
				if(end != null) {
					if(end.equals("로그인 완료")) {
						break;
					}
				}else {
					sendMsg = WithClient.getOutputStream();
					byte[]msg = "아이디 혹은 비밀번호가 틀렸습니다".getBytes();
					sendMsg.write(msg);
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
