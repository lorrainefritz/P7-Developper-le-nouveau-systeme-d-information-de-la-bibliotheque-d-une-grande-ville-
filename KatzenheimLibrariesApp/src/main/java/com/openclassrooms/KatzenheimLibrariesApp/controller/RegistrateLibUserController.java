package com.openclassrooms.KatzenheimLibrariesApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openclassrooms.KatzenheimLibrariesApp.dto.LibraryUserDtoDS;
import com.openclassrooms.KatzenheimLibrariesApp.dto.LibraryUserRegistrationDto;
import com.openclassrooms.KatzenheimLibrariesApp.repository.LibraryUserRepository;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryService;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryUserService;

@Controller
@RequestMapping("/sInscrire")
public class RegistrateLibUserController {
	@Autowired
	LibraryUserDtoDS libraryUserDtoDS;
	@Autowired
	LibraryUserService libraryUserService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);

		
	public RegistrateLibUserController(LibraryUserDtoDS libraryUserDtoDS) {
		super();
		this.libraryUserDtoDS = libraryUserDtoDS;
	}

	@ModelAttribute("libraryUser")
	public LibraryUserRegistrationDto libraryUserRegistrationDto() {
		return new LibraryUserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm (){
		logger.info("HTTP GET request received at /sInscrire in showRegistrationOrIdentificationForm");
		return "sInscrire";
	}
	
	@PostMapping
	public String libraryUserRegistrationFormSubmit(@Validated @ModelAttribute("libraryUser")LibraryUserRegistrationDto libraryUserDto, BindingResult bindingResult) {
		logger.info("HTTP POST request received at /sInscrire in libraryUserRegistrationFormSubmit");
		if (bindingResult.hasErrors()) {
			logger.info("HTTP POST request received at /sInscrire in libraryUserRegistrationFormSubmit in binding has errors");
			return "sInscrire";
		} else if(libraryUserService.emailAlreadyExists(libraryUserDto)) {
			logger.info("HTTP POST request received at /sInscrire in libraryUserRegistrationFormSubmit in emailAlreadyExists");
			return "sInscrire";
		}
		
		
		else {
			libraryUserDtoDS.save(libraryUserDto);
		}
		return "redirect:/identification"; 
	}
	
}
