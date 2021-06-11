package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Borrow;
@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

}
