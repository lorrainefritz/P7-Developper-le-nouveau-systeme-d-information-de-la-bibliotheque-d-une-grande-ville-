package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Integer> {
	LibraryUser findByEmail(String email);
}
