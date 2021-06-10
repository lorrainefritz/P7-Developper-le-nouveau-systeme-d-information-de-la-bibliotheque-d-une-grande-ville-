package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	Book findByTitle(String title);

}
