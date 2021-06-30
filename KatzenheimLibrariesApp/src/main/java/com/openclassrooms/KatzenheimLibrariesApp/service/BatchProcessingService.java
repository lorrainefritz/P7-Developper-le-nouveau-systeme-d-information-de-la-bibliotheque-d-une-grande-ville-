package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.openclassrooms.KatzenheimLibrariesApp.controller.UserEditAdminController;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;

import antlr.debug.NewLineEvent;

@Service
public class BatchProcessingService {

	@Autowired
	LibraryUserService libraryUserService;
	@Autowired
	BorrowService borrowService;
//	@Autowired
//	private JavaMailSender javaMailSender;
	private final Logger logger = LoggerFactory.getLogger(BatchProcessingService.class);

	public void batchProcessing() {
		logger.info("in BatchProcessingService in batchProcessing method");
		List<Borrow> allBorrowList = borrowService.getAllBorrows();
		Date today = Date.from(Instant.now());
		for (Borrow borrow : allBorrowList) {
			Date returnDate = borrow.getReturnDate();
			// SI les dates sont == retourne 0 si today>returnDate =-1
			// today<returnDate = 1
			if (today.compareTo(returnDate) > 0) {
				
				borrow.setIsOutdated(true);
				Book book = borrow.getBook();
				Library library = book.getLibrary();
				LibraryUser libraryUser = libraryUserService.findLibraryUserByEmail(borrow.getLibraryUserEmail());
				String userEmail =libraryUser.getEmail();
				logger.info("in BatchProcessingService in batchProcessing method in isOutdated from user " + libraryUser.getEmail());
				try {
					sendmail(libraryUser,library,book);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
	
	
	
	private void sendmail(LibraryUser libraryUser, Library library, Book book) throws AddressException, MessagingException, IOException {
		logger.info("in BatchProcessingService in sendmail method");   
		Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("aehlinepomme@gmail.com", "fyarfjwtdcosywtl");
		      }
		   });
		   String emailBody = "Bonjour " + libraryUser.getFirstName() + " " + libraryUser.getLastName() + ","
					+ "\nLe livre " + book.getTitle()
					+ " est à rendre au plus vitre car sa date de retour est dépassée. \n Merci de le rendre au plus vite à la bibliothèque de "
					+ library.getName() + "\nCordialement. \nLes Bibliothèques de Katzenheim";
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("aehlinepomme@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(libraryUser.getEmail()));
		   msg.setSubject("Date de retour dépassée");
		   msg.setText(emailBody);
//		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
//		   messageBodyPart.setContent("Tutorials point email", "text/html");
		   messageBodyPart.setContent(emailBody, toString());
		   Transport.send(msg);   
		}	
	



}
