package com.ajuodeikis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajuodeikis.model.Offer;
import com.ajuodeikis.repository.OfferRepository;


@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepository;
	
	public Offer findById(Integer id) {
		return offerRepository.findById(id).orElse(null);
	}
	
	public List<Offer> findAll() {
		List<Offer> offerList = new ArrayList<>();
		offerRepository.findAll().forEach(offerList::add);
		return offerList; 
	}
	
	public Offer save(Offer offer) {
		return offerRepository.save(offer);
	}

	public boolean existsById(Integer id) {
		return offerRepository.existsById(id);
	}

	public void deleteById(Integer id) {
		offerRepository.deleteById(id);		
	}
	
	public void deleteAll() {
		offerRepository.deleteAll();	
	}

	public Offer update(Integer id, Offer offer) {
		if ( offerRepository.existsById(id) ) {	
			offer.setId(id);
			return offerRepository.save(offer);
		} else {
			return null;
		}
	}

	public Offer updatePartial(Integer id, Offer offerPartial) {
		if ( offerRepository.existsById(id) ) {	
			Offer offer = offerRepository.findById(id).get();
			
			offer.setDescription(offerPartial.getDescription());
			offer.setPrice(offerPartial.getPrice());
			offer.setCurrencyCode(offerPartial.getCurrencyCode());
			offer.setExpiration(offerPartial.getExpiration());
			offer.setCanceled(offerPartial.isCanceled());
		
			return offerRepository.save(offer);
		} else {
			return null;
		}
	}

	
}
