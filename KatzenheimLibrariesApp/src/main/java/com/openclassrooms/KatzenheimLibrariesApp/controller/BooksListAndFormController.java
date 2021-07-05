package com.openclassrooms.KatzenheimLibrariesApp.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Stock;
import com.openclassrooms.KatzenheimLibrariesApp.service.BookService;
import com.openclassrooms.KatzenheimLibrariesApp.service.BorrowService;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryService;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryUserService;
import com.openclassrooms.KatzenheimLibrariesApp.service.StockService;

@Controller
public class BooksListAndFormController {
	@Autowired
	private BookService bookService;
	@Autowired
	private StockService stockService;
	@Autowired
	BorrowService borrowService;
	@Autowired
	LibraryUserService libraryUserService;
	@Autowired
	private LibraryService libraryService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);
	private Book currentBook;
	private List<Library>listOfLibraries; 
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/ajouterDesLivres")
	public String showBooksForm(ModelMap modelMap) {
		logger.info("HTTP GET request received at /ajouterDesLivres");
		listOfLibraries = libraryService.getAllLibraries();
		logger.info("Book");
		modelMap.addAttribute("book", new Book());
		logger.info("Stock");
		modelMap.addAttribute("stock", new Stock());
		logger.info("lib");
		modelMap.addAttribute("lib", new Library());
		logger.info("Libraries");
		modelMap.addAttribute("libraries",listOfLibraries);
		logger.info("Vers le post");
		return "/ajouterDesLivres";
	}	
	
	
	// prévoir un duplicate title exception?
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/ajouterDesLivres")
		public String submitBookForm(@Validated @ModelAttribute("book")Book book,BindingResult bindingResult,@Validated @ModelAttribute("stock")Stock stock,@ModelAttribute("lib")Library lib, Model model) {
			model.addAttribute("libraries", listOfLibraries);
			logger.info("HTTP POST request received at /ajouterDesLivres");
			if (bindingResult.hasErrors()) {
				logger.info("HTTP POST request received at /ajouterDesLivres where bindingResult hasErrors");
				return "/ajouterDesLivres";
			} else {
				logger.info("HTTP POST request received at /ajouterDesLivres where book title = " + book.getTitle() + " with stock totalOfCopies = " + stock.getTotalOfCopies()+ " and library name = " + lib.getName());
				
				
				// partie save stock 
				stock.setNumberOfCopiesAvailable(stock.getTotalOfCopies());
				stockService.saveStock(stock);
				//faire un set du stock dans book
				book.setStock(stock);
				//faire un set de la librairie
				Library library = libraryService.getOneLibraryByName(lib.getName());
				book.setLibrary(library);
				// partie save book	
				bookService.saveBook(book);
			}
			return "redirect:/listeDesLivres";
		}	
	

	// il faut faire changement de code ici => vers book en local et plus dans le controller
	
	@Transactional
	@GetMapping("/ajouterUneImageDeCouvertureAuLivre")
	public String addImageForm(Model model, Integer id) {
		logger.info("HTTP GET request recAeived at /ajouterUneImageDeCouvertureAuLivre");
		currentBook = bookService.getOneBookById(id);
		model.addAttribute("book", currentBook);
		return "ajouterUneImageDeCouvertureAuLivre";
	}

	@PostMapping("/ajouterUneImageDeCouvertureAuLivre")
	public String submitImageForm(@RequestParam("fileImage") MultipartFile multipartFileImage) {
		logger.info("HTTP POST request received at /ajouterUneImageDeCouvertureAuLivre");
		bookService.addImageCoverToBook(currentBook, multipartFileImage);
		return "redirect:/listeDesLivres";

	}
	
	//On a laissé keyword des fois que besoin de faire une recherche multicritères
	@Transactional
	@GetMapping("/listeDesLivres")
	public String getBooksList(Model model, @Param("keyword")String keyword) {
		logger.info("HTTP GET request received at /listeDesLivres");
		if(keyword!=null) {
			logger.info("HTTP GET request received at /listeDesLivres in keyword = "+ keyword);	
		model.addAttribute("books", bookService.getOneBookByTitle(keyword));
		model.addAttribute("keyword", keyword);
		return "listeDesLivres";
		}
		else {
			model.addAttribute("books", bookService.getAllBooks());
		}
		return "listeDesLivres";	
	}
	
	@GetMapping(path="/emprunterUnLivre")
	public String borrowABook(ModelMap modelMap, Integer id, Authentication authentication) {
		logger.info("HTTP GET request received at /emprunterUnLivre" + id);

		// Le livre
		Book book = bookService.getOneBookById(id);
		// Le stock du livre
		Stock stock = book.getStock();

		if (stock.isBookIsAvailable()) {
			logger.info("HTTP POST request received at /emprunterUnLivre in if bookIsAvailable");
			// on augmente le nombre d'exemplaire de livre qui sont sorti/on diminue le nombre de dispo
			stock.setNumberOfCopiesOut(stock.getNumberOfCopiesOut() + 1);
			stock.setNumberOfCopiesAvailable(stock.getNumberOfCopiesAvailable()-1);
			// on sauve le stock
			stockService.saveStock(stock);

			// l'utilisateur
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			LibraryUser libraryUser = libraryUserService.findLibraryUserByEmail(userDetails.getUsername());

			// l'emprunt
			Borrow borrow = new Borrow();
			
			borrowService.makeABorrow(libraryUser,borrow, book);
			libraryUserService.makeABorrow(libraryUser, borrow);
		}

		return "redirect:/monCompte";

	}
	
	
	
	
	
	// Attention au modelMap
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path="/modifierUnLivre") 
	public String editABook(ModelMap modelMap,Integer id) {
		logger.info("HTTP GET request received at /modifierUnLivre");
		Book book = bookService.getOneBookById(id);
		modelMap.addAttribute("book", book);
		modelMap.addAttribute("stock",book.getStock());
		modelMap.addAttribute("lib", new Library());
		modelMap.addAttribute("libraries", libraryService.getAllLibraries());
		return "/ajouterDesLivres";
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/supprimerUnLivre")
	public String deleteABook(Integer id) {
		logger.info("HTTP GET request received at /supprimerUnLivre");
		Book book = bookService.getOneBookById(id);
		Stock stock = book.getStock();
		bookService.deleteBook(book);
		stockService.deleteStock(stock);
		return "redirect:/listeDesLivres";
	}

}
