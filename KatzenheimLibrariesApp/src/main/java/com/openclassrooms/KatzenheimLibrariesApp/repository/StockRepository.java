package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Stock;
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

}
