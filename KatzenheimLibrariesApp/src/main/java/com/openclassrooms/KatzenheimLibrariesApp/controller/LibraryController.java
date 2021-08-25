package com.openclassrooms.KatzenheimLibrariesApp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryService;

@RestController
public class LibraryController {
	@Autowired
	LibraryService libraryService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);
	private ModelAndView mav;
	
	
	@RequestMapping(value ="/listeDesBibliotheques",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showLibrariesList(Model model) {
		logger.info("HTTP GET request received at /listeDesBibliothèques");
		model.addAttribute("libraries", libraryService.getAllLibraries());
		mav = new ModelAndView("/listeDesBibliotheques");
		return mav;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/ajouterUneBibliotheque",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showLibraryForm(Model model) {
		logger.info("HTTP GET request received at /ajouterUneBibliothèque");
		model.addAttribute("library", new Library());
		mav = new ModelAndView("/ajouterUneBibliotheque");
		return mav;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/ajouterUneBibliotheque",method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView submitLibraryForm(@Validated @ModelAttribute("library") Library library,
			BindingResult bindingResult) {
		logger.info("HTTP POST request received at /ajouterUneBibliothèque");
		if (bindingResult.hasErrors()) {
			logger.info("HTTP POST request received at /ajouterUneBibliothèque where bindingResult has error");
			mav = new ModelAndView("/ajouterUneBibliotheque");
			return mav;
		} else {
			logger.info(
					"HTTP POST request received at /ajouterUneBibliothèque where library name = " + library.getName());
			libraryService.saveLibrary(library);
		}
		mav = new ModelAndView("redirect:/listeDesBibliotheques");
		return mav;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value ="/supprimerUneBibliotheque",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteLibrary(@PathVariable Integer id) {
		logger.info("HTTP GET request received at /supprimerUneBibliothèque where library name = " + libraryService.getOneLibraryById(id).getName());
		Library library = libraryService.getOneLibraryById(id);
		libraryService.deleteLibrary(library);
		mav = new ModelAndView("redirect:/listeDesBibliotheques");
		return mav;
	}

	@PreAuthorize("hasRole('ADMIN')")	
	@RequestMapping(value ="/modifierUneBibliotheque",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView editLibrary(Model model, Integer id) {
		logger.info("HTTP GET request received at /modifierUneBibliothèque where library name = " + libraryService.getOneLibraryById(id).getName());
		model.addAttribute("library", libraryService.getOneLibraryById(id));
		mav = new ModelAndView("ajouterUneBibliotheque");
		return mav;
	}
}
