package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.KatzenheimLibrariesApp.dto.LibraryUserDtoDS;
import com.openclassrooms.KatzenheimLibrariesApp.dto.LibraryUserRegistrationDto;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
import com.openclassrooms.KatzenheimLibrariesApp.entities.Role;
import com.openclassrooms.KatzenheimLibrariesApp.repository.LibraryUserRepository;
@Service
public class LibraryUserService implements LibraryUserDtoDS {
	private final Logger logger = LoggerFactory.getLogger(LibraryUserService.class);
	
	@Autowired
	private LibraryUserRepository libraryUserRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public LibraryUserService(LibraryUserRepository libraryUserRepository) {
		super();
		this.libraryUserRepository = libraryUserRepository;
	}

	@Override
	public LibraryUser save(LibraryUserRegistrationDto libraryUserRegistrationDto) {
		Role roleUserParDefaut = roleService.getOneRoleById(2);
		logger.info("In LibraryUserDtoDsImplem in save");
		LibraryUser libraryUser = new LibraryUser(libraryUserRegistrationDto.getFirstName(),
				libraryUserRegistrationDto.getLastName(), libraryUserRegistrationDto.getEmail(),
				libraryUserRegistrationDto.getAddress(), libraryUserRegistrationDto.getPhone(),
				passwordEncoder.encode(libraryUserRegistrationDto.getPassword()), Arrays.asList(roleUserParDefaut),
				libraryUserRegistrationDto.getBorrows());
		return saveLibraryUser(libraryUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("In LibraryUserDtoDsImplem in loadUserByUsername");
		LibraryUser libraryUser = libraryUserRepository.findByEmail(username);
		if (libraryUser == null) {
			throw new UsernameNotFoundException("Invalid email or Password");
		}
		return new org.springframework.security.core.userdetails.User(libraryUser.getEmail(), libraryUser.getPassword(),
				mapRolesToAuthorities(libraryUser.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	
// methodes standard générales 
	
	public LibraryUser getOneLibraryUserById(Integer id) {
		logger.info("in LibraryUserService in getOneLibraryUserById method");
		return libraryUserRepository.getById(id);
	}
	public LibraryUser getOneLibraryUserByEmail(String email) {
		logger.info("in LibraryUserService in getOneLibraryUserByEmail method");
		return libraryUserRepository.findByEmail(email);
	}
	public void deleteLibraryUser(LibraryUser libraryUser) {
		logger.info("in LibraryUserService in deleteLibraryUser method");
		libraryUserRepository.delete(libraryUser);
	}
	public LibraryUser saveLibraryUser (LibraryUser libraryUser) {
		logger.info("in LibraryUserService in saveLibraryUser method");
		return libraryUserRepository.save(libraryUser);
	}
	
	public LibraryUser findLibraryUserByEmail(String email) {
		logger.info("in LibraryUserService in findLibraryUserByEmail method");
	return libraryUserRepository.findByEmail(email);
	}
	
	
// methodes utiles specifiquement pour une personne connectée, 
//	pour ses modifications d'éléments de compte
	
	public LibraryUser emailModification (LibraryUser libraryUser, String email) {
		logger.info("in LibraryUserService in emailModification method");
		libraryUser.setEmail(email);
		return saveLibraryUser(libraryUser);
	}

	public boolean emailAlreadyExists(LibraryUserRegistrationDto libraryUserDto) {
		String email = libraryUserDto.getEmail();
		if (libraryUserRepository.findByEmail(email)!=null) {
			return true;
		}
		return false;
	}

	public void makeABorrow(LibraryUser libraryUser, Borrow borrow) {
		List<Borrow> borrows = (List<Borrow>) libraryUser.getBorrows();
		borrows.add(borrow);
		libraryUserRepository.save(libraryUser);
	}
	
	
	
}