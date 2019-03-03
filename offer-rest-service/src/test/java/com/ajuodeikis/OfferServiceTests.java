package com.ajuodeikis;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ajuodeikis.model.Offer;
import com.ajuodeikis.model.Status;
import com.ajuodeikis.service.OfferService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={OfferRestServiceApplication.class})
public class OfferServiceTests {

	@Autowired
	private OfferService offerService;
	
	@Test
	public void findAllTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		Offer offer1 = TestUtil.offerBuildRandom();
		
		offerService.save(offer0);
		offerService.save(offer1);
		
		List<Offer> offerList = offerService.findAll();
		assertEquals(2, offerList.size());
		
		offerService.deleteAll();
	}

	@Test
	public void findByIdTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		Offer offer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(offer0);
		Offer savedOffer1 = offerService.save(offer1);
		
		TestUtil.offerAssert(	savedOffer0.getDescription(),
								savedOffer0.getPrice(),
								savedOffer0.getCurrencyCode(),
								savedOffer0.getExpiration(),
								savedOffer0.isCanceled(),
								savedOffer0.getStatus(),
								offerService.findById(savedOffer0.getId()));
		
		TestUtil.offerAssert(	savedOffer1.getDescription(),
								savedOffer1.getPrice(),
								savedOffer1.getCurrencyCode(),
								savedOffer1.getExpiration(),
								savedOffer1.isCanceled(),
								savedOffer1.getStatus(),
								offerService.findById(savedOffer1.getId()));
		
		offerService.deleteAll();
	}
	
	@Test
	public void deleteByIdTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		Offer offer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(offer0);
		Offer savedOffer1 = offerService.save(offer1);
		
		offerService.deleteById(savedOffer1.getId());
		
		TestUtil.offerAssert(	savedOffer0.getDescription(),
								savedOffer0.getPrice(),
								savedOffer0.getCurrencyCode(),
								savedOffer0.getExpiration(),
								savedOffer0.isCanceled(),
								savedOffer0.getStatus(),
								offerService.findById(savedOffer0.getId()));
		
		List<Offer> offerList = offerService.findAll();
		assertEquals(1, offerList.size());
		
		offerService.deleteAll();
	}
	
	public void deleteAllTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		Offer offer1 = TestUtil.offerBuildRandom();
		
		offerService.save(offer0);
		offerService.save(offer1);
		
		offerService.deleteAll();
		
		List<Offer> offerList = offerService.findAll();
		assertEquals(0, offerList.size());
	}
	
	@Test
	public void existsByIdTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		Offer offer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(offer0);
		Offer savedOffer1 = offerService.save(offer1);
		
		offerService.deleteById(savedOffer1.getId());
			
		assertEquals(true, offerService.existsById(savedOffer0.getId()));
		assertEquals(false, offerService.existsById(savedOffer1.getId()));		
		
		offerService.deleteAll();
	}
	
	@Test
	public void updateTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		Offer offer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(offer0);

		offerService.update(savedOffer0.getId(), offer1);
		
		TestUtil.offerAssert(	offer1.getDescription(),
								offer1.getPrice(),
								offer1.getCurrencyCode(),
								offer1.getExpiration(),
								offer1.isCanceled(),
								offer1.getStatus(),
								offerService.findById(savedOffer0.getId()));	
		
		offerService.deleteAll();
	}
	
	@Test
	public void updatePartialTest() {
		Offer offer0 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(offer0);

		String description = "updatePartialTest";
		Offer updateOffer = new Offer(	null,
										description,
										null,
										null,
										null,
										null);
				
		offerService.updatePartial(savedOffer0.getId(), updateOffer);
			
		TestUtil.offerAssert(	updateOffer.getDescription(),
								savedOffer0.getPrice(),
								savedOffer0.getCurrencyCode(),
								savedOffer0.getExpiration(),
								savedOffer0.isCanceled(),
								savedOffer0.getStatus(),
								offerService.findById(savedOffer0.getId()));	

		offerService.deleteAll();
	}	
	
	@Test
	public void saveOfferActiveTest() {	
		String description = "Active offer";
		BigDecimal price = new BigDecimal(1).setScale(Offer.PRICE_SCALE, RoundingMode.HALF_UP);
		String currencyCode = "GBP";
		Date expiration = TestUtil.addDays(new Date(), 1);
		Boolean canceled = false;
		
		Offer activeOffer = new Offer(	null,
										description,
										price,
										currencyCode,
										expiration,
										canceled);

		Offer savedOffer = offerService.save(activeOffer);
		
		TestUtil.offerAssert(	description,
								price,
								currencyCode,
								expiration,
								canceled,
								Status.ACTIVE,
								savedOffer);
		
		offerService.deleteAll();
	}
	
	@Test
	public void saveOfferExpiredTest() {	
		String description = "Expired offer";
		BigDecimal price = new BigDecimal(2).setScale(Offer.PRICE_SCALE, RoundingMode.HALF_UP);
		String currencyCode = "EUR";
		Date expiration = TestUtil.addDays(new Date(), -1);
		Boolean canceled = false;
		
		Offer expiredOffer = new Offer(	null,
										description,
										price,
										currencyCode,
										expiration,
										canceled);

		Offer savedOffer = offerService.save(expiredOffer);
		
		TestUtil.offerAssert(	description,
								price,
								currencyCode,
								expiration,
								canceled,
								Status.EXPIRED,
								savedOffer);
		
		offerService.deleteAll();
	}
	
	@Test
	public void saveOfferCanceledTest() {	
		String description = "Canceled offer";
		BigDecimal price = new BigDecimal(3).setScale(Offer.PRICE_SCALE, RoundingMode.HALF_UP);
		String currencyCode = "USD";
		Date expiration = TestUtil.addDays(new Date(), 1);
		Boolean canceled = true;
		
		Offer canceledOffer = new Offer(null,
										description,
										price,
										currencyCode,
										expiration,
										canceled);
		
		Offer savedOffer = offerService.save(canceledOffer);
		
		TestUtil.offerAssert(	description,
								price,
								currencyCode,
								expiration,
								canceled,
								Status.CANCELED,
								savedOffer);
		
		offerService.deleteAll();
	}

}
