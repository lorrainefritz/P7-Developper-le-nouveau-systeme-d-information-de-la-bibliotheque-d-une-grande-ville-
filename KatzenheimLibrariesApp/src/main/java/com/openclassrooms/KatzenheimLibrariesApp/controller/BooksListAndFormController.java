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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	private ModelAndView mav;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/ajouterDesLivres",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showBooksForm(ModelMap modelMap) {
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
		mav = new ModelAndView("/ajouterDesLivres");
		return mav;
	}	
	
	
	
		@PreAuthorize("hasRole('ADMIN')")
		@RequestMapping(value ="/ajouterDesLivres",method = RequestMethod.POST)
		@ResponseBody
		public ModelAndView submitBookForm(@Validated @ModelAttribute("book")Book book,BindingResult bindingResult,@Validated @ModelAttribute("stock")Stock stock,@ModelAttribute("lib")Library lib, Model model) {
			model.addAttribute("libraries", listOfLibraries);
			logger.info("HTTP POST request received at /ajouterDesLivres");
			if (bindingResult.hasErrors()) {
				logger.info("HTTP POST request received at /ajouterDesLivres where bindingResult hasErrors");
				mav = new ModelAndView("/ajouterDesLivres");
				return mav;
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
			mav = new ModelAndView("redirect:/listeDesLivres");
			return mav;
		}	
	

		
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/ajouterUneImageDeCouvertureAuLivre",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView addImageForm(Model model, Integer id) {
		logger.info("HTTP GET request recAeived at /ajouterUneImageDeCouvertureAuLivre");
		currentBook = bookService.getOneBookById(id);
		model.addAttribute("book", currentBook);
		mav = new ModelAndView("ajouterUneImageDeCouvertureAuLivre");
		return mav;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/ajouterUneImageDeCouvertureAuLivre",method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView submitImageForm(@RequestParam("fileImage") MultipartFile multipartFileImage) {
		logger.info("HTTP POST request received at /ajouterUneImageDeCouvertureAuLivre");
		bookService.addImageCoverToBook(currentBook, multipartFileImage);
		mav = new ModelAndView("redirect:/listeDesLivres");
		return mav;
	}
	
	//On a laissé le terme keyword des fois que besoin de faire une recherche multicritères
	@Transactional
	@RequestMapping(value ="/listeDesLivres",method = RequestMethod.GET)
	@ResponseBody
		
	public ModelAndView getBooksList(Model model, @Param("keyword")String keyword) {
		logger.info("HTTP GET request received at /listeDesLivres");
		if(keyword!=null) {
			logger.info("HTTP GET request received at /listeDesLivres in keyword = "+ keyword);	
		model.addAttribute("books", bookService.getBooksByTitle(keyword));
		model.addAttribute("keyword", keyword);
		mav = new ModelAndView("listeDesLivres");
		return mav;
		
		}
		else {
			model.addAttribute("books", bookService.getAllBooks());
		}
		mav = new ModelAndView("listeDesLivres");
		return mav;	
	}
	@RequestMapping(value ="/emprunterUnLivre",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView borrowABook(ModelMap modelMap, Integer id, Authentication authentication) {
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
		mav = new ModelAndView("redirect:/monCompte");
		return mav;	
	}
	
	
	// Attention au modelMap
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/modifierUnLivre",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView editABook(ModelMap modelMap,Integer id) {
		logger.info("HTTP GET request received at /modifierUnLivre");
		Book book = bookService.getOneBookById(id);
		modelMap.addAttribute("book", book);
		modelMap.addAttribute("stock",book.getStock());
		modelMap.addAttribute("lib", new Library());
		modelMap.addAttribute("libraries", libraryService.getAllLibraries());
		mav = new ModelAndView("/ajouterDesLivres");
		return mav;	
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/supprimerUnLivre",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteABook(Integer id) {
		logger.info("HTTP GET request received at /supprimerUnLivre");
		Book book = bookService.getOneBookById(id);
		Stock stock = book.getStock();
		bookService.deleteBook(book);
		stockService.deleteStock(stock);
		mav = new ModelAndView("redirect:/listeDesLivres");
		return mav;	
		
	}

}
