/**
 * 
 */
package com.tour.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.tour.repository.UserRepository;

/**
 * @author ramanand
 *
 */
@Service
public class EmailSender {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JavaMailSender javaMailSender;
	private TemplateEngine templateEngine;
	@Autowired
	private UserRepository userRepository;

	/**
	 * @param javaMailSender
	 * @param templateEngine
	 */
	@Autowired
	public EmailSender(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}

	/**
	 * @param user
	 * @param to
	 * @param subject
	 * @param text
	 */
	public void sendWelcomeMail(String name, String to, String subject, String text) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(message, true);
			Context context = new Context();

			context.setVariable("name", name);
			context.setVariable("main", text);
			context.setVariable("heading", "Welcome to Roverstrail.");
			String htmlContent = templateEngine.process("email-templete.html", context);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	
	public void sendForgetPasswordMail(String to, String subject) {
		logger.info("Inside EmailSender:sendForgetPasswordMail,To send Password Recovery Request.");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			logger.info("Inside try block");
			helper = new MimeMessageHelper(message, true);
			Context context = new Context();
			final String url = "roverstrail.com";
			context.setVariable("url", url);
			String htmlContent = templateEngine.process("password-recovery-email.html", context);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			javaMailSender.send(message);
			logger.info("Sucessfully executed try block");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		logger.info("Completed EmailSender:sendForgetPasswordMail");

	}

	/**
	 * @param request
	 * @param user
	 */
//	public void sendPasswordRecoveryRequest() {
//		logger.info("To send Password Recovery Request.");
//		try {
//			logger.info("Inside try block");
//			String subject = "Reset Password";
//			MultipartFile image = getImageContent();
//			MimeMessage email = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(email, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//					StandardCharsets.UTF_8.name());
////			user.setPasswordUpdateDate(LocalDate.now());
//			final String url = "roverstrail.com";
////					propConfig.getFrontEndIp() + "/reset-password?email=" + user.getEmail()+"&date="+LocalDateTime.now();
//			helper.setTo("ramq007@gmail.com");
//			helper.setSubject(subject);
//			Context context = new Context();
//			context.setVariable("url", url);
//			context.setVariable("wittybrainslogo", image.getName());
//			String htmlContent = templateEngine.process("password-recovery-email.html", context);
//			helper.setText(htmlContent, true);
//			helper.addInline(image.getName(), imageSource, image.getContentType());
//
//			javaMailSender.send(email);
//			logger.info("Sucessfully executed try block");
//
//		} catch (Exception e) {
////			logger.error("Problem with sending email to: {}, error message: {}", user.getEmail(), e.getMessage());
//			throw new BadRequestException("Problem in sending mail.");
//		}
////		userRepository.save(user);
//		logger.info("Returning after sending Password Recovery Request.");
//
//	}

//	InputStreamSource imageSource = null;
//
//	private MultipartFile getImageContent() throws IOException {
//		InputStream imageIs = null;
//
//		byte[] imageByteArray = null;
//		MultipartFile multipartFile = null;
//		imageIs = this.getClass().getClassLoader().getResourceAsStream("image/wittybrainslogo.png");
//		imageByteArray = IOUtils.toByteArray(imageIs);
//		multipartFile = new MockMultipartFile(imageIs.getClass().getName(), imageIs.getClass().getName(), "image/jpeg",
//				imageByteArray);
//		imageSource = new ByteArrayResource(imageByteArray);
//		return multipartFile;
//	}

	/**
	 * @param user
	 * @param to
	 * @param subject
	 * @param text
	 * @throws MessagingException
	 */
//	public void sendExpenseMail(User user, String to, String subject, String text) throws MessagingException {
//
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, true);
//		Context context = new Context();
//
//		context.setVariable("name", user.getName());
//		context.setVariable("main", text);
//		context.setVariable("heading", "Monthly Expense Report");
//		String htmlContent = templateEngine.process("email-templete.html", context);
//
//		helper.setTo(to);
//		helper.setSubject(subject);
//		helper.setText(htmlContent, true);
//		javaMailSender.send(message);
//
//	}

}
