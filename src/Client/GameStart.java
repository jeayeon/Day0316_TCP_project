package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class GameStart {

	private Socket WithServer = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private Scanner in = new Scanner(System.in);

	GameStart(Socket c) {

		WithServer = c;
		Login();
//		send();
		receive();
	}

	private void receive() {
		// TODO Auto-generated method stub

	}

	private void send(String a) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (a.equals("로그인 완료")) {
					while (true) {
						System.out.println("원하시는 메뉴를 선택하세요");
						System.out.println("1. 게임리스트");
						System.out.println("2. 요금 정산");
						String call = null;
						switch (call = in.nextLine()) {

						case "1":
							try {
								byte[] go = (call + ". 게임리스트").getBytes();
								sendMsg.write(go);
								GameList();
								break;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						case "2":
						default: // 나중에 종료 만들것

						}

					}
				} else {
					Login();
				}

			}

		}).start();

	}

	protected void GameList() {// 서버로부터 게임 메뉴를 받아오고 선택한내용을 전달해주는 메소드
		// 우선매뉴 받아오는거까지만
		try {
			System.out.println("게임 목록입니다 하고싶은 게임을 선택하세요");
			byte[] gameList = new byte[200];
			reMsg.read(gameList);
			String msg = new String(gameList).trim();
			System.out.println(msg);
			
			System.out.println("하고자하는 번호 클릭 ,30초당 요금");
			byte[] choice = in.nextLine().getBytes();
			
			long totalTime = 0;
			
			long startTime = System.currentTimeMillis();
			while(true) {
				System.out.println("게임을 종료하시려면 x 버튼을 누르세요");
				if(in.nextLine().equalsIgnoreCase("x")) {
					break;
				}
			}
			long endTime = System.currentTimeMillis();
			
			totalTime = 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void Login() {
		try {
			sendMsg = WithServer.getOutputStream();
			System.out.println("로그인할 아이디를 입력하세요");
			String id = in.nextLine();
			System.out.println("비밀번호를 입력하세요");
			String pwd = in.nextLine();
//			byte[] log = (id+"/"+pwd).getBytes(); 이렇게도쓸수있다.
			byte[] log = new byte[100];
			String login = id + "/" + pwd;
			log = login.getBytes();
			sendMsg.write(log);

			reMsg = WithServer.getInputStream();
			byte[] Msg = new byte[100];
			reMsg.read(Msg);
			String remsg = new String(Msg);
			remsg = remsg.trim();
			System.out.println(remsg);
			send(remsg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
