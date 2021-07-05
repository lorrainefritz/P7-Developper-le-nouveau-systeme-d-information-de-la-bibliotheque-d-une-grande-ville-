package com.openclassrooms.KatzenheimLibrariesApp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	Book findByTitle(String title);

}
