package com.openclassrooms.KatzenheimLibrariesApp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Role;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryUserService;
import com.openclassrooms.KatzenheimLibrariesApp.service.RoleService;
import com.openclassrooms.KatzenheimLibrariesApp.service.StockService;

@Controller
public class UserEditAdminController {
	@Autowired
	RoleService roleService;
	@Autowired
	LibraryUserService libraryUserService;

	private final Logger logger = LoggerFactory.getLogger(UserEditAdminController.class);

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/userListForAdmin")
	public String showUserListForAdmin(Model model) {
		logger.info("HTTP GET request received at /userListForAdmin");
		model.addAttribute("libraryUsers", libraryUserService.getAllLibraryUsers());
		return "userListForAdmin";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/editUserByIdWhenAdmin")
	public String editUserByIdWhenAdmin(ModelMap modelmap, Integer id) {
		logger.info("HTTP GET request received at /editUserByIdWhenAdmin");
		LibraryUser libraryUser = libraryUserService.getOneLibraryUserById(id);
		modelmap.addAttribute("libraryUser", libraryUser);
		modelmap.addAttribute("currentLibraryUserRoles", libraryUser.getRoles());
		modelmap.addAttribute("allRoles", roleService.getAllRoles());
		modelmap.addAttribute("role", new Role());

		return "/editUserByIdWhenAdmin";

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editUserInfosWhenAdmin")
	public String editUserInfosWhenAdmin(@ModelAttribute("libraryUser") LibraryUser libraryUser,
			BindingResult bindingResult) {
		logger.info("HTTP GET request received at /editUserInfosWhenAdmin" + libraryUser.getEmail());
//		if (bindingResult.hasErrors()) {
//			logger.info("HTTP GET request received at /editUserInfosWhenAdmin in BingindResult hasErrors");
//			return "editUserByIdWhenAdmin";
//		}
		libraryUserService.libraryUserInfosModificationWhenAdmin(libraryUser);
		return "redirect:/userListForAdmin";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editUserPasswordWhenAdmin")
	public String editUserPasswordWhenAdmin(@ModelAttribute("libraryUser") LibraryUser libraryUser,
			BindingResult bindingResult) {
		logger.info("HTTP GET request received at /editUserPasswordWhenAdmin");
//		if (bindingResult.hasErrors()) {
//			logger.info("HTTP GET request received at /editUserPasswordWhenAdmin in BingindResult hasErrors");
//			return "editUserByIdWhenAdmin";
//		}
		libraryUserService.libraryUserPasswordModificationWhenAdmin(libraryUser);
		return "redirect:/userListForAdmin";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editUserEmailWhenAdmin")
	public String editUserEmailWhenAdmin(@ModelAttribute("libraryUser") LibraryUser libraryUser,
			BindingResult bindingResult) {
		logger.info("HTTP GET request received at /editUserEmailWhenAdmin");
//		if (bindingResult.hasErrors()) {
//			logger.info("HTTP GET request received at /editUserEmailWhenAdmin in BingindResult hasErrors");
//			return "editUserByIdWhenAdmin";
//		}
		libraryUserService.libraryUserEmailModificationWhenAdmin(libraryUser);
		return "redirect:/userListForAdmin";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteUserByIdWhenAdmin")
	public String deleteUserByIdWhenAdmin(Integer id) {
		logger.info("HTTP GET request received at /deleteUserByIdWhenAdmin");
		libraryUserService.deleteLibraryUser(id);
		return "redirect:/userListForAdmin";

	}	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editUserRolesWhenAdmin")
	public String editUserRolesWhenAdmin(@ModelAttribute("libraryUser") LibraryUser libraryUser, @ModelAttribute("role") Role role){
		logger.info("HTTP GET request received at /editUserEmailWhenAdmin" + role.getId() + role.getName() + libraryUser.getFirstName());
		Role newRole = roleService.getOneRoleById(role.getId());
		LibraryUser currentLibraryUser = libraryUserService.getOneLibraryUserById(libraryUser.getId());
		libraryUserService.libraryUserRoleModification(currentLibraryUser,newRole);
		return "redirect:/userListForAdmin";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteUserRoleWhenAdmin")
	public String deleteUserRoleWhenAdmin(@ModelAttribute("libraryUser") LibraryUser libraryUser, Integer id) {
		logger.info("HTTP GET request received at /deleteUserRoleWhenAdmin");
		LibraryUser currentLibraryUser = libraryUserService.getOneLibraryUserById(libraryUser.getId());
		Role role = roleService.getOneRoleById(id); 
		libraryUserService.deleteLibraryUserRole(currentLibraryUser,role);
		return "redirect:/userListForAdmin";

	}



}
