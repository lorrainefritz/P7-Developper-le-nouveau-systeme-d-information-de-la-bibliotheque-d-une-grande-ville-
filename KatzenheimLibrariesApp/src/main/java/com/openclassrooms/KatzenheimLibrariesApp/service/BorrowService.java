package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.KatzenheimLibrariesApp.controller.BooksListAndFormController;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.repository.BorrowRepository;

@Service
public class BorrowService {
	private final Logger logger = LoggerFactory.getLogger(BorrowService.class);
	
	@Autowired
	BorrowRepository borrowRepository;
	
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
		borrowRepository.delete(borrow);
	}

	public void makeABorrow(Borrow borrow, Book book) {
		// la partie temporelle de l'emprunt
		borrow.setStartDate(Date.from(Instant.now()));
		borrow.setReturnDate(Date.from(Instant.now().plus(28, ChronoUnit.DAYS)));
		//on set le livre
		borrow.setBook(book);
		saveBorrow(borrow);
	}
	
}
