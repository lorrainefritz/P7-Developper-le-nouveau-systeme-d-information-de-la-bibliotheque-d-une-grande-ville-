package com.openclassrooms.KatzenheimLibrariesApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryService;

@Controller
public class LibraryController {
	@Autowired
	LibraryService libraryService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);

	@GetMapping("/listeDesBibliotheques")
	public String showLibrariesList(Model model) {
		logger.info("HTTP GET request received at /listeDesBibliothèques");
		model.addAttribute("libraries", libraryService.getAllLibraries());
		return ("/listeDesBibliotheques");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/ajouterUneBibliotheque")
	public String showLibraryForm(Model model) {
		logger.info("HTTP GET request received at /ajouterUneBibliothèque");
		model.addAttribute("library", new Library());
		return ("/ajouterUneBibliotheque");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/ajouterUneBibliotheque")
	public String submitLibraryForm(@Validated @ModelAttribute("library") Library library,
			BindingResult bindingResult) {
		logger.info("HTTP POST request received at /ajouterUneBibliothèque");
		if (bindingResult.hasErrors()) {
			logger.info("HTTP POST request received at /ajouterUneBibliothèque where bindingResult has error");
			return ("/ajouterUneBibliotheque");
		} else {
			logger.info(
					"HTTP POST request received at /ajouterUneBibliothèque where library name = " + library.getName());
			libraryService.saveLibrary(library);
		}
		return ("redirect:/listeDesBibliotheques");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/supprimerUneBibliotheque")
	public String deleteLibrary(Integer id) {
		logger.info("HTTP GET request received at /supprimerUneBibliothèque where library name = " + libraryService.getOneLibraryById(id).getName());
		Library library = libraryService.getOneLibraryById(id);
		libraryService.deleteLibrary(library);
		return ("redirect:/listeDesBibliotheques");
	}

	@PreAuthorize("hasRole('ADMIN')")	
	@GetMapping(path="/modifierUneBibliotheque")
	public String editLibrary(Model model, Integer id) {
		logger.info("HTTP GET request received at /modifierUneBibliothèque where library name = " + libraryService.getOneLibraryById(id).getName());
		model.addAttribute("library", libraryService.getOneLibraryById(id));
		return "ajouterUneBibliotheque";
	}
}
