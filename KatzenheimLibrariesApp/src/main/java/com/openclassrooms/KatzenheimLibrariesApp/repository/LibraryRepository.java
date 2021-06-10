package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

}
