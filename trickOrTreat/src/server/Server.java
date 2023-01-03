package server;

import static server.DBA.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.*;


public class Server {
	public Server() {
		try {
			ServerSocket server = new ServerSocket(7777);
			System.out.println("서버 시작");
			while (true) {
				Socket client = server.accept();
				new TCPMulServerReceiver(client).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class TCPMulServerReceiver extends Thread {
		DataOutputStream dos;
		DataInputStream dis;
		
		public TCPMulServerReceiver(Socket client) {
			try {
				dos = new DataOutputStream(client.getOutputStream());
				dis = new DataInputStream(client.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				Connection conn = getConnection();
				Dao dao= Dao.getInstance();
				dao.setConnection(conn);
				int result = 0;
				String memInfo = "";
				if (dis != null) {
					String[] str = dis.readUTF().split("/");
					switch (str[0]) {
					case"join":
					// 회원가입
						result = dao.memberJoin(str[1]);
						if (result == 1) {
							commit(conn);
							dos.writeUTF("성공");
						} else {
							rollback(conn);
							dos.writeUTF("실패");
						}
						break;
					case"login":
					// 로그인
						memInfo = dao.memberLogin(str[1]);
						System.out.println(str[1]);
						dos.writeUTF(memInfo);
						break;
					case"email":
						String code = Email.sendMail(str[0], str[1]);
						dos.writeUTF(code);
						break;
					case"find":
					// ID / 비밀번호 찾기	
						memInfo = dao.find(str[1]);
						dos.writeUTF(Email.sendMail(memInfo, str[1]));
						break;
					case"rankingView":
					// 랭킹 불러오기
						memInfo = dao.rankingView(str[1]);
						dos.writeUTF(memInfo);
						break;
					case"rankingInsert":
					// 랭킹 등록하기
						result = dao.rankingInsert(str[1]);
						if (result == 1) {
							commit(conn);
							dos.writeUTF("성공");
						} else {
							rollback(conn);
							dos.writeUTF("실패");
						}
						break;
					}
				}
				close(conn);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
