package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.KatzenheimLibrariesApp.controller.BooksListAndFormController;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.repository.BookRepository;
import com.openclassrooms.KatzenheimLibrariesApp.repository.BorrowRepository;

@Service
public class BorrowService {
	private final Logger logger = LoggerFactory.getLogger(BorrowService.class);
	
	@Autowired
	BorrowRepository borrowRepository;
	@Autowired
	BookService bookService;
	
	
	public List<Borrow> getAllBorrows(){
		logger.info("in BorrowService in getAllBorrows method");
		return borrowRepository.findAll();
	}
	
	public Borrow findOneBorrowById(Integer id) {
		logger.info("in BorrowService in findOneBorrowById method");
		return borrowRepository.getById(id);
	}
	
	public void saveBorrow(Borrow borrow) {
		logger.info("in BorrowService in saveBorrow method");
		borrowRepository.save(borrow);
	}
	
	public void deleteBorrow(Borrow borrow) {
		logger.info("in BorrowService in deleteBorrow method");
		bookService.giveBackABook(borrow.getBook());
		borrow.setBook(null);
		borrowRepository.delete(borrow);
	}

	public void makeABorrow(LibraryUser libraryUser, Borrow borrow, Book book) {
		logger.info("in BorrowService in makeABorrow method");
		// la partie temporelle de l'emprunt
		borrow.setStartDate(Date.from(Instant.now()));
		borrow.setReturnDate(Date.from(Instant.now().plus(28, ChronoUnit.DAYS)));
		//on set l'email de contact
		borrow.setLibraryUserEmail(libraryUser.getEmail());
		//on set le livre
		borrow.setBook(book);
		
		saveBorrow(borrow);
	}

	public void extendBorrow(Borrow borrow) {
		logger.info("in BorrowService in extendBorrow method");
		Date returnDate = borrow.getReturnDate();
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(returnDate);
		 cal.add(Calendar.DATE, 28);
		 Date date = (Date) cal.getTime();
		borrow.setReturnDate(date);
		borrow.setAlreadyExtended(true);
		saveBorrow(borrow);
	}
	
}
