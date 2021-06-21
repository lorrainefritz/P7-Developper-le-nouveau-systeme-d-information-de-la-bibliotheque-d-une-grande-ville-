package com.openclassrooms.KatzenheimLibrariesApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryUserService;

@Controller
public class IdentificateAcountController {
	@Autowired
	LibraryUserService libraryUserService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);
	
	
	@GetMapping("/identification")
	public String showLoginForm() {
		logger.info("HTTP GET request received at /identification in showLoginForm");
		return "identification";
	} 
	
	@GetMapping("/monCompte")
	public String showLibraryUserInfos (Authentication authentication, ModelMap modelMap) {
		logger.info("HTTP GET request received at /monCompte in showLibraryUserInfos");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		logger.info("userDetails" + userDetails.getUsername());
		LibraryUser currentLibraryUser = libraryUserService.findLibraryUserByEmail(userDetails.getUsername());
		logger.info("currentLibraryUser"+ currentLibraryUser.getFirstName());
		
		modelMap.addAttribute("userDetails", userDetails);
		modelMap.addAttribute("currentLibraryUser", currentLibraryUser);
		modelMap.addAttribute("borrows", currentLibraryUser.getBorrows());
		
		return "monCompte";
	}
}
