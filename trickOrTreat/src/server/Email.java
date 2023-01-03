package server;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email{
	final static String host = "smtp.naver.com";
	final static String user = "hoyeon150@naver.com";
	final static String password = "********";

	public static String sendMail(String kind, String recipient) {
		String result = "실패";
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.auth", "true");
//		prop.put("mail.smtp.ssl.enable", "true");
//		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			if (kind.equals("email")) {
				int code = (int)(Math.random() * 100000);
				
				message.setSubject("Trick or Treat 회원가입 인증번호입니다.");
				message.setText("인증번호는 [" + code + "] 입니다.");
				Transport.send(message);
				result = "" + code;
			} else if (!kind.equals("")) {
				String[] memInfo = kind.split("/");
				message.setSubject("Trick or Treat 고객님의 회원정보입니다.");
				message.setText("ID는 [" + memInfo[0] + "]\n비밀번호는 [" + memInfo[1] + "] 입니다.");
				Transport.send(message);
				result = "성공";
			}

			
		} catch (AddressException e) {
			System.out.println("Email 클래스의 sendMail() 메소드 오류");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("Email 클래스의 sendMail() 메소드 오류");
			e.printStackTrace();
		}
		
		return result;
	}

}