package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {

}
