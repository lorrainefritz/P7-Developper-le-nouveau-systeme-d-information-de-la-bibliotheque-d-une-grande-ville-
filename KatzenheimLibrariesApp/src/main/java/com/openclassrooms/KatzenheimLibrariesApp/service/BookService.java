package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Stock;
import com.openclassrooms.KatzenheimLibrariesApp.repository.BookRepository;
import com.openclassrooms.KatzenheimLibrariesApp.repository.StockRepository;

@Service
public class BookService {
	private final Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	BookRepository bookRepository;
	@Autowired
	StockService stockService;
	
	public List<Book> getAllBooks(){
		logger.info("in BookService in getAllBooks method"); 
		return bookRepository.findAll();
	}
	
		
	public Book getOneBookById(Integer id) {
		logger.info("in BookService in getOneBookById method");
		return bookRepository.getById(id);
	}
	

	public Book getOneBookByTitle(String title) {
		logger.info("in BookService in getOneBookByTitle method");
		return  bookRepository.findByTitle(title);
	}
	
	public List <Book> getBooksByTitle (String title){
		logger.info("in BookService in getBooksByTitle method");
		return bookRepository.findByTitleContaining(title);
	}
	
	
	
	public Book saveBook(Book book) {
		logger.info("in BookService in addBook method");
		return bookRepository.save(book);
	}
	
	public Book addImageCoverToBook(Book book, MultipartFile image) {
		logger.info("in BookService in addImageCoverToBook method");
		try {
			logger.info("in BookService in addImageCoverToBook method in try catch");
			book.setCover(Base64.getEncoder().encodeToString(image.getBytes()));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return bookRepository.save(book);
	}
	
	public void deleteBook(Book book) {
		logger.info("in BookService in deleteBook method");
		book.setStock(null);
		book.setLibrary(null);
		bookRepository.delete(book);
	}

	public void giveBackABook(Book book) {
		logger.info("in BookService in giveBackABook method");
		Stock stock = book.getStock();
		stock.setNumberOfCopiesOut(stock.getNumberOfCopiesOut()-1);
		stock.setNumberOfCopiesAvailable(stock.getNumberOfCopiesAvailable()+1);
		stockService.saveStock(stock);
		saveBook(book);
	}
	
	
}
