package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Library;
import com.openclassrooms.KatzenheimLibrariesApp.repository.LibraryRepository;

@Service
public class LibraryService {
private final Logger logger = LoggerFactory.getLogger(LibraryService.class);	

@Autowired
LibraryRepository libraryRepository;

public List<Library> getAllLibraries(){
	logger.info("in LibraryService in getAllLibrary method");
	return libraryRepository.findAll();
}

public Library getOneLibraryById(Integer id) {
	logger.info("in LibraryService in getOneLibraryById method");
	return libraryRepository.getById(id);
}
public Library  getOneLibraryByName(String name) {
	logger.info("in LibraryService in getOneLibraryByName method");
	return libraryRepository.findByName(name);
}



public Library saveLibrary(Library library) {
	logger.info("in LibraryService in saveLibrary method");
	return libraryRepository.save(library);
}

public void deleteLibrary(Library library) {
	logger.info("in LibraryService in deleteLibrary method");
	libraryRepository.delete(library);
}




}
