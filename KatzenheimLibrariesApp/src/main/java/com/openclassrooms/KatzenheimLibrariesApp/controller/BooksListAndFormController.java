package com.openclassrooms.KatzenheimLibrariesApp.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Stock;
import com.openclassrooms.KatzenheimLibrariesApp.service.BookService;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryService;
import com.openclassrooms.KatzenheimLibrariesApp.service.StockService;

@Controller
public class BooksListAndFormController {
	@Autowired
	private BookService bookService;
	@Autowired
	private StockService stockService;
	@Autowired
	private LibraryService libraryService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);
	private Book currentBook;
	
	
	
	
//méthode normale 	
//	@GetMapping("/ajouterDesLivres")
//	public String BooksForm(Model model) {
//		logger.info("HTTP GET request received at /ajouterDesLivres");
//		model.addAttribute("book", new Book());
//		model.addAttribute("stock", new Stock());
//		return "ajouterDesLivres";
//	}
	
//		@PostMapping("ajouterDesLivres")
//		public String submitBookForm(@Validated @ModelAttribute("book") Book book, @ModelAttribute("stock") Stock stock,
//				BindingResult bindingResult) {
//			logger.info("HTTP POST request received at /ajouterDesLivres");
//			if (bindingResult.hasErrors()) {
//				logger.info("HTTP POST request received at /ajouterDesLivres where bindingResult hasErrors");
//				return "/ajouterDesLivres";
//			} else {
//				logger.info("HTTP POST request received at /ajouterDesLivres where book is add with ");
//				bookService.saveBook(book);
//				// partie stock
////				int totalOfCopies = stock.getTotalOfCopies();
////				stock.setTotalOfCopies(totalOfCopies + 1);
//				stockService.saveStock(stock);
//			}
//			return "redirect:/listeDesLivres";
//		}	
	
	
	
	@GetMapping("/ajouterDesLivres")
	public String showBooksForm(ModelMap modelMap) {
		logger.info("HTTP GET request received at /ajouterDesLivres");
		modelMap.addAttribute("book", new Book());
		modelMap.addAttribute("stock", new Stock());
		modelMap.addAttribute("lib", new Library());
		modelMap.addAttribute("libraries", libraryService.getAllLibraries());
		return "ajouterDesLivres";
	}	
	

//	// prévoir un duplicate title exception?
//	// possible que deux modelAttribute ne fonctionnent pas malgrès la modification de getMapping => dans ce cas aussi ModelMap map ici?
//	@PostMapping("ajouterDesLivres")
//	public String submitBookForm(@Validated ModelMap modelMap,
//			BindingResult bindingResult) {
//		Book book = (Book) modelMap.getAttribute("book");
//		Stock stock = (Stock) modelMap.getAttribute("stock");
//		Library library = (Library) modelMap.getAttribute("library");
//		logger.info("HTTP POST request received at /ajouterDesLivres where book title = " + book.getTitle() + " with stock totalOfCopies = " + stock.getTotalOfCopies()+ " and library name = " + library.getName());
//		if (bindingResult.hasErrors()) {
//			logger.info("HTTP POST request received at /ajouterDesLivres where bindingResult hasErrors");
//			return "/ajouterDesLivres";
//		} else {
//			logger.info("HTTP POST request received at /ajouterDesLivres");
//			
//			// partie save stock ordre avec save book?
//			stockService.saveStock(stock);
//			//faire un set du stock
//			book.setStock(stock);
//			//faire un set de la librairie
//			book.setLibrary(library);
//			// partie save book	
//			bookService.saveBook(book);
//		}
//		return "redirect:/listeDesLivres";
//	}
	
	// prévoir un duplicate title exception?
		// possible que deux modelAttribute ne fonctionnent pas malgrès la modification de getMapping => dans ce cas aussi ModelMap map ici?
		@PostMapping("ajouterDesLivres")
		public String submitBookForm(@Validated @ModelAttribute("book")Book book,@ModelAttribute("stock")Stock stock, @ModelAttribute("lib") Library lib,
				BindingResult bindingResult) {
			
			logger.info("HTTP POST request received at /ajouterDesLivres where book title = " + book.getTitle() + " with stock totalOfCopies = " + stock.getTotalOfCopies()+ " and library name = " + lib.getName());
			if (bindingResult.hasErrors()) {
				logger.info("HTTP POST request received at /ajouterDesLivres where bindingResult hasErrors");
				return "/ajouterDesLivres";
			} else {
				logger.info("HTTP POST request received at /ajouterDesLivres");
				
				// partie save stock ordre avec save book?
				stockService.saveStock(stock);
				//faire un set du stock
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
	@GetMapping("ajouterUneImageDeCouvertureAuLivre")
	public String addImageForm(Model model, Integer id) {
		logger.info("HTTP GET request received at /ajouterUneImageDeCouvertureAuLivre");
		currentBook = bookService.getOneBookById(id);
		model.addAttribute("book", currentBook);
		return "ajouterUneImageDeCouvertureAuLivre";
	}

	@PostMapping("ajouterUneImageDeCouvertureAuLivre")
	public String submitImageForm(@RequestParam("fileImage") MultipartFile multipartFileImage) {
		logger.info("HTTP POST request received at /ajouterUneImageDeCouvertureAuLivre");
		bookService.addImageCoverToBook(currentBook, multipartFileImage);
		return "redirect:/listeDesLivres";

	}
	
	//On a laissé keyword des fois que besoin de faire une recherche multicritères
	@GetMapping("/listeDesLivres")
	public String getBooksList(Model model, @Param("keyword")String keyword) {
		logger.info("HTTP GET request received at /listeDesLivres");
		if(keyword!=null) {
		model.addAttribute("books", bookService.getBooksByKeyword(keyword));
		model.addAttribute("keyword", keyword);
		}
		else {
			model.addAttribute("books", bookService.getAllBooks());
		}
		return "listeDesLivres";	
	}
	
	// Attention au modelMap
	@GetMapping(path="/modifierUnLivre") 
	public String editABook(Model model,Integer id) {
		logger.info("HTTP GET request received at /modifierUnLivre");
		model.addAttribute("book",bookService.getOneBookById(id));
		return "ajouterDesLivres";
	}
	
	@GetMapping("/supprimerUnLivre")
	public String deleteABook(Integer id) {
		logger.info("HTTP GET request received at /supprimerUnLivre");
		Book book = bookService.getOneBookById(id);
		bookService.deleteBook(book);
		return "redirect:/listeDesLivres";
	}
	//prévoir une méthode ajouter un exemplaire à un livre déjà existant. 

}
