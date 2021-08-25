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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	private ModelAndView mav;
	
	private void libraryUserAuthentication(Authentication authentication) {
		userDetails = (UserDetails) authentication.getPrincipal();
		logger.info("userDetails" + userDetails.getUsername());
		currentLibraryUser = libraryUserService.findLibraryUserByEmail(userDetails.getUsername());
		logger.info("currentLibraryUser"+ currentLibraryUser.getFirstName());
	}
	
	@RequestMapping(value ="/identification",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showLoginForm() {
		logger.info("HTTP GET request received at /identification in showLoginForm");
		mav = new ModelAndView("identification");
		return mav;
	} 
	
	@RequestMapping(value ="/monCompte",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showLibraryUserInfos (Authentication authentication, ModelMap modelMap) {
		logger.info("HTTP GET request received at /monCompte in showLibraryUserInfos");
		libraryUserAuthentication(authentication);
		modelMap.addAttribute("userDetails", userDetails);
		modelMap.addAttribute("currentLibraryUser", currentLibraryUser);
		modelMap.addAttribute("borrows", currentLibraryUser.getBorrows());
		mav = new ModelAndView("monCompte");
		return mav;
		
	}
	@RequestMapping(value ="/modifierMonCompte",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView editLibraryUserInfos(Model model) {
		logger.info("HTTP GET request received at /monCompte in editLibraryUserInfos");
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("currentLibraryUser", currentLibraryUser);
		mav = new ModelAndView("modifierMonCompte");
		return mav;
	}

	
	@RequestMapping(value ="/modifierMonCompteInfosPerso",method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editLibraryUserInfos(String firstName, String lastName, String address, String phone) {
		logger.info("HTTP POST request received at /monCompte in editLibraryUserInfos");
		libraryUserService.libraryUserInfosModification(currentLibraryUser,firstName,lastName,address,phone);
		mav = new ModelAndView("redirect:/monCompte");
		return mav;
	}
	
	@RequestMapping(value ="/modifierMonMotDePasse",method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editLibraryUserPassword(String password) {
		logger.info("HTTP POST request received at /monCompte in editLibraryUserInfos");
		libraryUserService.libraryUserPasswordModification(currentLibraryUser, password);
		mav = new ModelAndView("redirect:/monCompte");
		return mav;
	}
	
	@RequestMapping(value ="/modifierMonAdresseEmail",method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editLibraryUserEmail(Authentication authentication,String email) {
		logger.info("HTTP POST request received at /monCompte in editLibraryUserInfos");
		libraryUserAuthentication(authentication);
		libraryUserService.libraryUserEmailModification(currentLibraryUser, email);
		mav = new ModelAndView("redirect:/logout");
		return mav;
	}
	
	@RequestMapping(value ="/prolongerUnLivre",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView extendABorrow(Integer id) {
		logger.info("HTTP GET request received at /monCompte in extendABorrow");
		Borrow borrow = borrowService.findOneBorrowById(id);
		if(borrow.isAlreadyExtended()==true){
			mav = new ModelAndView("redirect:/monCompte");
			return mav;
		} else {
		borrowService.extendBorrow(borrow);	
		}
		mav = new ModelAndView("redirect:/monCompte");
		return mav;
	}
	
}
