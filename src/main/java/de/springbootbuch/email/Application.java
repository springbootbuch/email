package de.springbootbuch.email;

import static de.springbootbuch.email.PrepareMockSmtpListener.LATCH;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final JavaMailSender mailSender;

	public Application(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void run(String... args) throws Exception {
		this.mailSender.send(mimeMessage -> {
			final MimeMessageHelper message = 
				new MimeMessageHelper(
					mimeMessage, true, UTF_8.name()
				);
			message.setFrom("hallo@springbootbuch.de");
			message.setTo("leser@springbootbuch.de");
			message.setSubject("Danke!");
			message.setText(
				"Danke, dass Ihr mein Buch lest!",
				  "<html>"
				+ "<h1>Hallo!</h1>"
				+ "<p>Danke, dass ihr mein Buch lest!</p>"
				+ "</html>"
			);
		});
		LATCH.await();
	}
}
