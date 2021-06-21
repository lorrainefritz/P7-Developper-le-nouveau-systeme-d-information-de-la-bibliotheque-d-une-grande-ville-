package com.openclassrooms.KatzenheimLibrariesApp.dto;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;

public interface LibraryUserDtoDS extends UserDetailsService {
LibraryUser save (LibraryUserRegistrationDto registrationDto);
}
