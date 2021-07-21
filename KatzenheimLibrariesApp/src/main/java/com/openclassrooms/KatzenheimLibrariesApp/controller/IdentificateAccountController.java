package com.openclassrooms.KatzenheimLibrariesApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.service.BorrowService;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryUserService;

@Controller
public class IdentificateAccountController {
	@Autowired
	LibraryUserService libraryUserService;
	@Autowired
	BorrowService borrowService;
	private final Logger logger = LoggerFactory.getLogger(BooksListAndFormController.class);
	private UserDetails userDetails;
	private LibraryUser currentLibraryUser;
	
	private void libraryUserAuthentication(Authentication authentication) {
		userDetails = (UserDetails) authentication.getPrincipal();
		logger.info("userDetails" + userDetails.getUsername());
		currentLibraryUser = libraryUserService.findLibraryUserByEmail(userDetails.getUsername());
		logger.info("currentLibraryUser"+ currentLibraryUser.getFirstName());
	}
	
	
	@GetMapping("/identification")
	public String showLoginForm() {
		logger.info("HTTP GET request received at /identification in showLoginForm");
		return "identification";
	} 
	
	@GetMapping("/monCompte")
	public String showLibraryUserInfos (Authentication authentication, ModelMap modelMap) {
		logger.info("HTTP GET request received at /monCompte in showLibraryUserInfos");
		libraryUserAuthentication(authentication);
		modelMap.addAttribute("userDetails", userDetails);
		modelMap.addAttribute("currentLibraryUser", currentLibraryUser);
		modelMap.addAttribute("borrows", currentLibraryUser.getBorrows());
		return "monCompte";
	}
	
	@GetMapping("/modifierMonCompte")
	public String editLibraryUserInfos(Model model) {
		logger.info("HTTP GET request received at /monCompte in editLibraryUserInfos");
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("currentLibraryUser", currentLibraryUser);
		return "modifierMonCompte";
	}

	//Rajouter binding
	@PostMapping("/modifierMonCompteInfosPerso")
	public String editLibraryUserInfos(String firstName, String lastName, String address, String phone) {
		logger.info("HTTP POST request received at /monCompte in editLibraryUserInfos");
		libraryUserService.libraryUserInfosModification(currentLibraryUser,firstName,lastName,address,phone);
		return "redirect:/monCompte";
		
	}
	//Rajouter binding
	@PostMapping("/modifierMonMotDePasse")
	public String editLibraryUserPassword(String password) {
		logger.info("HTTP POST request received at /monCompte in editLibraryUserInfos");
		libraryUserService.libraryUserPasswordModification(currentLibraryUser, password);
		return "redirect:/monCompte";
	}
	//Rajouter binding
	@PostMapping("/modifierMonAdresseEmail")
public String editLibraryUserEmail(Authentication authentication,String email) {
		logger.info("HTTP POST request received at /monCompte in editLibraryUserInfos");
		libraryUserAuthentication(authentication);
		libraryUserService.libraryUserEmailModification(currentLibraryUser, email);
		return "redirect:/logout";
	}
	
	
	@GetMapping(path="/prolongerUnLivre")
	public String extendABorrow(Integer id) {
		logger.info("HTTP GET request received at /monCompte in extendABorrow");
		Borrow borrow = borrowService.findOneBorrowById(id);
		if(borrow.isAlreadyExtended()==true){
			return "redirect:/monCompte";
		} else {
		borrowService.extendBorrow(borrow);	
		}
		return "redirect:/monCompte";
	}
	
}
