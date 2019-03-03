package com.ajuodeikis.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ajuodeikis.model.Id;
import com.ajuodeikis.model.Offer;
import com.ajuodeikis.service.OfferService;


@RestController
@RequestMapping("/offer")
public class OfferRestController {
	
	@Autowired
	private OfferService offerService;
	
	@RequestMapping(method=RequestMethod.GET, value="/all")
	public ResponseEntity<List<Offer>> findAllOffers() {
		List<Offer> offerList = offerService.findAll();
        if (offerList.isEmpty()) {
            return new ResponseEntity<List<Offer>>(offerList, HttpStatus.NO_CONTENT);
        } else {
        	return new ResponseEntity<List<Offer>>(offerList, HttpStatus.OK);
        }
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
    private ResponseEntity<?> getOffer(@PathVariable("id") Integer id) {
		if ( offerService.existsById(id) ) {
			Offer offer = offerService.findById(id);
        	return new ResponseEntity<Offer>(offer, HttpStatus.OK);	
		} else {
			return new ResponseEntity<Id>(new Id(id), HttpStatus.NOT_FOUND);	
		}
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Offer> saveOffer(@Valid @RequestBody Offer offer){
		offer.setId(null);
    	Offer savedOffer = offerService.save(offer);
    	return new ResponseEntity<Offer>(savedOffer, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> updateOffer(@PathVariable("id") Integer id, @Valid @RequestBody Offer offer){	
		Offer updatedOffer = offerService.update(id, offer);
		if ( updatedOffer != null ) {
        	return new ResponseEntity<Offer>(updatedOffer, HttpStatus.OK);	
		} else {
			return new ResponseEntity<Id>(new Id(id), HttpStatus.NOT_FOUND);	
		}		
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value="/{id}")
	public ResponseEntity<?> updateOfferPartial( @PathVariable("id") Integer id, @RequestBody Offer offerPartial) {
		Offer updatedOffer = offerService.updatePartial(id, offerPartial);
		if ( updatedOffer != null ) {
        	return new ResponseEntity<Offer>(updatedOffer, HttpStatus.OK);	
		} else {
			return new ResponseEntity<Id>(new Id(id), HttpStatus.NOT_FOUND);	
		}	     
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<Id> deleteOffer(@PathVariable("id") Integer id, HttpServletRequest request){
		if ( offerService.existsById(id) ) {
        	offerService.deleteById(id);
        	return new ResponseEntity<Id>(new Id(id), HttpStatus.OK);	
		} else {
			return new ResponseEntity<Id>(new Id(id), HttpStatus.NOT_FOUND);	
		}	
	}

}
