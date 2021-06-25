package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private JavaMailSender javaMailSender;
	private final Logger logger = LoggerFactory.getLogger(BatchProcessingService.class);

	public void batchProcessing() {
		logger.info("HTTP GET request received at /batchProcessing");
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
				LibraryUser libraryUser = null;

				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

				try {

					helper.setTo(libraryUser.getEmail());
					helper.setText("Bonjour " + libraryUser.getFirstName() + " " + libraryUser.getLastName() + ","
							+ "\nLe livre " + book.getTitle()
							+ " est à rendre au plus vitre car sa date de retour est dépassée. \n Merci de le rendre au plus vite à la bibliothèque de "
							+ library.getName() + "\nCordialement. \nLes Bibliothèques de Katzenheim");
					helper.setSubject("Date de retour dépassée");
				} catch (MessagingException e) {

					e.printStackTrace();
				}

				javaMailSender.send(mimeMessage);

			}

		}

	}

}
