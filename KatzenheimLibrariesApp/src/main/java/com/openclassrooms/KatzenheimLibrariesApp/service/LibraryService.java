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

	
	
}
