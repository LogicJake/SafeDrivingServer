package com.scy.driving.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class MailServiceTest {
	public void sendMail(String toMail, String subject, String content) throws IOException, MessagingException {
		// 创建Properties 类用于记录邮箱的一些属性
		Properties props = new Properties();
		
		InputStream inputStream = MailServiceTest.class.getClassLoader().getResourceAsStream("email.properties");
		
		props.load(inputStream); // 加载properties文件
		inputStream.close();
		
		// 使用环境属性和授权信息，创建邮件会话
		Session session = Session.getInstance(props);
		// 通过session得到transport对象
		Transport ts = session.getTransport();
		// 连接邮件服务器：邮箱类型，帐号，授权码
		ts.connect("smtp.qq.com", props.getProperty("mail.user"), props.getProperty("mail.password"));
		// 创建邮件消息
		MimeMessage message = new MimeMessage(session);
		// 设置发件人
		
		InternetAddress from = new InternetAddress(props.getProperty("mail.user"));
		message.setFrom(from);
		
		// 设置收件人的邮箱
		InternetAddress to = new InternetAddress(toMail);
		message.setRecipient(RecipientType.TO, to);
		
		// 设置邮件标题
		message.setSubject(subject);
		
		// 设置邮件的内容体
		message.setContent(content, "text/html;charset=UTF-8");
		
		// 发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	
	@Test
	public void test() throws IOException, MessagingException {
		sendMail("835410808@qq.com", "12", "12");
	}
	
}
