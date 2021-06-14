package com.openclassrooms.KatzenheimLibrariesApp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	Book findByTitle(String title);

	@Query(value="SELECT * FROM books books WHERE CONCAT(books.title, books.author, books.publisher, books.language) LIKE %?1%", nativeQuery=true)
	List<Book> findByKeyword(@Param("keyword")String keyword);
}
