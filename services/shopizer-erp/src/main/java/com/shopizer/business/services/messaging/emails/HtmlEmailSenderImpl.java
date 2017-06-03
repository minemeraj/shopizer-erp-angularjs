package com.shopizer.business.services.messaging.emails;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class HtmlEmailSenderImpl implements HtmlEmailSender {
	
	private static final String CHARSET = "UTF-8";
	private JavaMailSenderImpl mailSender;
	
    @Inject
    Configuration freemarkerConfiguration;
	
	@Value("${smtp.host}")
	private String host;
	@Value("${smtp.port}")
	private String port;
	@Value("${smtp.protocol}")
	private String protocol;
	@Value("${smtp.username}")
	private String username;
	@Value("${smtp.password}")
	private String password;
	@Value("${smtp.smtpAuth}")
	private String smtpAuth = "false";
	@Value("${smtp.starttls}")
	private String starttls = "false";

	private final static String TEMPLATE_PATH = "templates/email";
	
	@Override
	public void send(Email email)
			throws Exception {
		
		final String eml = email.getFrom();
		final String from = email.getFromEmail();
		//TODO multiple tos
		//final String to = email.getTo();
		final String subject = email.getSubject();
		final String tmpl = email.getTemplateName();
		final Map<String,String> templateTokens = email.getTemplateTokens();
		
		mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
		mailSender.setProtocol(protocol);
		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", smtpAuth);
		prop.put("mail.smtp.starttls.enable", starttls);
		mailSender.setJavaMailProperties(prop);

		

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException, IOException {

				// if email configuration is present in Database, use the same
				
				Validate.notNull(email);
				Validate.notEmpty(email.getTo());

				//mimeMessage.addRecipient(type, address);
				//TODO
				//mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				List<String> emailTos = email.getTo();
				for(String toEmail : emailTos) {
					mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
				}

				InternetAddress inetAddress = new InternetAddress();

				inetAddress.setPersonal(eml);
				inetAddress.setAddress(from);

				mimeMessage.setFrom(inetAddress);
				mimeMessage.setSubject(subject);

				Multipart mp = new MimeMultipart("alternative");

				// Create a "text" Multipart message
				BodyPart textPart = new MimeBodyPart();
				//freemarkerMailConfiguration.setClassForTemplateLoading(HtmlEmailSenderImpl.class, "/");
				String template = new StringBuilder(TEMPLATE_PATH).append("").append("/").append(tmpl).toString();
				Template textTemplate = freemarkerConfiguration.getTemplate(tmpl);
				final StringWriter textWriter = new StringWriter();
				try {
					textTemplate.process(templateTokens, textWriter);
				} catch (TemplateException e) {
					throw new MailPreparationException(
							"Can't generate text mail", e);
				}
				textPart.setDataHandler(new javax.activation.DataHandler(
						new javax.activation.DataSource() {
							public InputStream getInputStream()
									throws IOException {
								//return new StringBufferInputStream(textWriter
								//		.toString());
								return new ByteArrayInputStream(textWriter
										.toString().getBytes(CHARSET));
							}

							public OutputStream getOutputStream()
									throws IOException {
								throw new IOException("Read-only data");
							}

							public String getContentType() {
								return "text/plain";
							}

							public String getName() {
								return "main";
							}
						}));
				mp.addBodyPart(textPart);

				// Create a "HTML" Multipart message
				Multipart htmlContent = new MimeMultipart("related");
				BodyPart htmlPage = new MimeBodyPart();
				//freemarkerMailConfiguration.setClassForTemplateLoading(HtmlEmailSenderImpl.class, "/");
				Template htmlTemplate = freemarkerConfiguration.getTemplate(tmpl);
				final StringWriter htmlWriter = new StringWriter();
				try {
					htmlTemplate.process(templateTokens, htmlWriter);
				} catch (TemplateException e) {
					throw new MailPreparationException(
							"Can't generate HTML mail", e);
				}
				htmlPage.setDataHandler(new javax.activation.DataHandler(
						new javax.activation.DataSource() {
							public InputStream getInputStream()
									throws IOException {
								//return new StringBufferInputStream(htmlWriter
								//		.toString());
								return new ByteArrayInputStream(textWriter
										.toString().getBytes(CHARSET));
							}

							public OutputStream getOutputStream()
									throws IOException {
								throw new IOException("Read-only data");
							}

							public String getContentType() {
								return "text/html";
							}

							public String getName() {
								return "main";
							}
						}));
				htmlContent.addBodyPart(htmlPage);
				BodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(htmlContent);
				mp.addBodyPart(htmlPart);

				mimeMessage.setContent(mp);

			}
		};

		mailSender.send(preparator);
	}

}
