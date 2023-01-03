package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBA {
// DB 관련 메소드들을 가진 클래스
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";					// jdbc 드라이버 주소
	static final String DB_URL = "jdbc:mysql://localhost:3306/trick_or_treat?useSSL=false";	// DB 접속 주소
	static final String USERNAME = "root"; // DB ID
	static final String PASSWORD = "1234"; // DB Password

	public static Connection getConnection() {
	// DB에 연결하여 컨넥션 객체를 리턴하는 메소드
		Connection conn = null;
		System.out.print("Data Base 접속 : ");
		try {
			Class.forName(JDBC_DRIVER); //Class 클래스의 forName()함수를 이용해서 해당 클래스를 메모리로 로드
			//URL, ID, password를 입력하여 데이터베이스에 접속합니다.
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			// DB 접속결과를 출력
			if (conn != null) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}
			conn.setAutoCommit(false);
			// 쿼리가 자동으로 commit 되는 것을 막는 명령으로 트랜잭션을 시작시킴
			
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn) {
		try { conn.close(); } catch(Exception e) { e.printStackTrace(); }
		// Connection 객체를 닫아 DB와의 연결을 끊어주는 메소드
	}

	public static void close(Statement stmt) {
	// PreparedStatement와 CallabledStatement는 Statement를 상속받으므로 따로 close()메소드가 필요 없음
		try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
		// Statement 객체를 닫아주는 메소드
	}

	public static void close(ResultSet rs) {
		try { rs.close(); } catch(Exception e) { e.printStackTrace(); }
		// ResultSet 객체를 닫아주는 메소드
	}

	public static void commit(Connection conn) {
	// transaction을 commit 시키는 메소드
		try {
			conn.commit();
			System.out.println("쿼리 성공");
		} catch(Exception e) { e.printStackTrace(); }
	}

	public static void rollback(Connection conn) {
	// transaction을 rollback 시키는 메소드
		try {
			conn.rollback();
			System.out.println("쿼리 실패");
		} catch(Exception e) { e.printStackTrace(); }
	}
}