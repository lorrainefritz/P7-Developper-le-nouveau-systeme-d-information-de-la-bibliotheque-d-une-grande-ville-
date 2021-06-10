package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

}
