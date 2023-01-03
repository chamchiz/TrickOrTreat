package server;

import static server.DBA.*;

import java.sql.*;

public class Dao {
	private static Dao dao;
	private Connection conn;
	private Dao() {}
	
	public static Dao getInstance() {
		if (dao == null)	dao = new Dao();
		return dao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int memberJoin(String memInfo) {
		Statement stmt = null;
		int result = 0;
		
		try {
			String[] str = memInfo.split("`");
			String sql = "insert into t_member_info (mi_id, mi_pw, mi_mail, mi_nick) values ('" + str[0] + "', '" + str[1] + "', '" + str[2] +"', '" + str[3] + "')";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.println("Dao 클래스의 memberJoin 메소드 오류 발생");
			e.printStackTrace();
		} finally {
			
		}
		
		return result;
	}
	
	public String memberLogin(String memInfo) {
		Statement stmt = null;
		ResultSet rs = null;
		String result = "로그인 실패";
		
		try {
			String[] str = memInfo.split("`");
			String sql = "select * from t_member_info where mi_id = '" + str[0] + "' and mi_pw = '" + str[1] + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				result = "";
				ResultSetMetaData md = rs.getMetaData() ;
				int column = md.getColumnCount();
				for (int i = 1; i <= column; i++) {
					result += "/" + rs.getString(i);
				}
				result = result.substring(1);
			}
		} catch (Exception e) {
			System.out.println("Dao 클래스의 memberLogin 메소드 오류 발생");
			e.printStackTrace();
		} finally {
			
		}
		
		return result;
	}
	
	public String find(String email) {
		Statement stmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			String sql = "select mi_id, mi_pw from t_member_info where mi_mail = '" + email + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ResultSetMetaData md = rs.getMetaData() ;
				int column = md.getColumnCount();
				for (int i = 1; i <= column; i++) {
					result += "/" + rs.getString(i);
				}
				result = result.substring(1);
			}
		} catch (Exception e) {
			System.out.println("Dao 클래스의 memberLogin 메소드 오류 발생");
			e.printStackTrace();
		} finally {
			
		}
		
		return result;
	}
	
	public String rankingView(String nickname) {
		Statement stmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			String sql = "select gr_nick, gr_time from t_game_record order by gr_time asc limit 0, 10";
			String survRanking = "";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				survRanking += "|" + rs.getString("gr_nick") + "/" + rs.getString("gr_time");
			}
			survRanking = survRanking.substring(1);
			
			String mySurv = "";
			sql = "select gr_nick, min(gr_time) from t_game_record where gr_nick = '" + nickname + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				mySurv = rs.getString(1) + "/" + rs.getString(2);
			}
			
			result = survRanking + "`" + mySurv;
			
		} catch (Exception e) {
			System.out.println("Dao 클래스의 memberLogin 메소드 오류 발생");
			e.printStackTrace();
		} finally {
			
		}
		
		return result;
	}
	
	public int rankingInsert(String record) {
		Statement stmt = null;
		int result = 0;
		
		try {
			String[] str = record.split("`");
			String sql = "insert into t_game_record (gr_nick, gr_time) values ('" + str[0] + "', '" + str[1] + "')";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.println("Dao 클래스의 memberJoin 메소드 오류 발생");
			e.printStackTrace();
		} finally {
			
		}
		
		return result;
	}
}
