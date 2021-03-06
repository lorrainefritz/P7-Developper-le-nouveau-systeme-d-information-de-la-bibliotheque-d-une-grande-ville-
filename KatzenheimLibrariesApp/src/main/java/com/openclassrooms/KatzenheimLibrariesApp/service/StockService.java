package com.openclassrooms.KatzenheimLibrariesApp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Stock;
import com.openclassrooms.KatzenheimLibrariesApp.repository.StockRepository;

@Service
public class StockService {
	private final Logger logger = LoggerFactory.getLogger(StockService.class);
	@Autowired
	private StockRepository stockRepository;
	
	public List <Stock> getAllStocks(){
		logger.info("in StockService in getAllStocks method");
		return stockRepository.findAll();
	}
	
	public Stock getOneStockById(Integer id) {
		logger.info("in StockService in getOneStockById method");
		return stockRepository.getById(id);
	}
	
	public Stock saveStock (Stock stock) {
		logger.info("in StockService in saveStock method");
		
		if(stock.getNumberOfCopiesAvailable()>0){
			logger.info("in StockService in saveStock method in if copiesAvailable>0");
			stock.setBookIsAvailable(true);
		}else if(stock.getNumberOfCopiesAvailable()==0) {
			logger.info("in StockService in saveStock method in if copiesAvailable=0");
			stock.setBookIsAvailable(false);
		}
		return stockRepository.save(stock);
	}
	
	public void deleteStock (Stock stock) {
		logger.info("in StockService in deleteStock method" + stock.getId());
		stockRepository.delete(stock);
	}
}
