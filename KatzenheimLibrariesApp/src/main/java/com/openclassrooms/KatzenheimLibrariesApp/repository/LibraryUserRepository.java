package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.LibraryUser;
@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Integer> {
	LibraryUser findByEmail(String email);
}
