package org.openfuxml.communication;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorReporter
{
	final static Logger logger = LoggerFactory.getLogger(ErrorReporter.class);
	
	private static String host;
	private static boolean report=false;
	
	public static void init(String smtphost)
	{
		host=smtphost;
		report=true;
	}
	
	public static void report()
	{
		logger.debug("Reporter active?"+report);
	}
	
	public static void sendMsg() throws MessagingException
	{
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("thorsten.kisner@fernuni-hagen.de"));

		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse("thorsten.kisner@fernuni-hagen.de",false));
		msg.setSubject("openFuXML-Error:");
		msg.setText("Body".toString());

		msg.setSentDate(new Date());
		Transport.send(msg);
	}
}
