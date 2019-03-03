package com.ajuodeikis;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ajuodeikis.model.Offer;
import com.ajuodeikis.service.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={OfferRestServiceApplication.class})
@AutoConfigureMockMvc
public class OfferRestControllerTest {

	@Autowired
	private OfferService offerService;
	
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void getAllTest() throws Exception {	
		Offer randomOffer0 = TestUtil.offerBuildRandom();
		Offer randomOffer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(randomOffer0);
		Offer savedOffer1 = offerService.save(randomOffer1);

	    mvc.perform(get("/offer/all")
	    		.contentType(MediaType.APPLICATION_JSON))
			  	
	    		.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				
				.andExpect(jsonPath("$[0].id", 				is(savedOffer0.getId())))
				.andExpect(jsonPath("$[0].description", 	is(randomOffer0.getDescription())))
				.andExpect(jsonPath("$[0].price", 			is(randomOffer0.priceTodouble())))
				.andExpect(jsonPath("$[0].currencyCode", 	is(randomOffer0.getCurrencyCode())))
				.andExpect(jsonPath("$[0].expiration", 		is(randomOffer0.expirationToString())))
				.andExpect(jsonPath("$[0].canceled", 		is(randomOffer0.isCanceled())))
				.andExpect(jsonPath("$[0].status", 			is(randomOffer0.statusToString())))
				
				.andExpect(jsonPath("$[1].id", 				is(savedOffer1.getId())))
				.andExpect(jsonPath("$[1].description", 	is(randomOffer1.getDescription())))
				.andExpect(jsonPath("$[1].price", 			is(randomOffer1.priceTodouble())))
				.andExpect(jsonPath("$[1].currencyCode", 	is(randomOffer1.getCurrencyCode())))
				.andExpect(jsonPath("$[1].expiration", 		is(randomOffer1.expirationToString())))
				.andExpect(jsonPath("$[1].canceled", 		is(randomOffer1.isCanceled())))
				.andExpect(jsonPath("$[1].status", 			is(randomOffer1.statusToString())));
	    
	    offerService.deleteAll();
	}
	
	@Test
	public void getTest() throws Exception {	
		Offer randomOffer0 = TestUtil.offerBuildRandom();
		Offer randomOffer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(randomOffer0);
		offerService.save(randomOffer1);

	    mvc.perform(get("/offer/{id}", savedOffer0.getId())
	    		.contentType(MediaType.APPLICATION_JSON))
			  	
	    		.andExpect(status().isOk())
				
				.andExpect(jsonPath("$.id", 			is(savedOffer0.getId())))
				.andExpect(jsonPath("$.description", 	is(randomOffer0.getDescription())))
				.andExpect(jsonPath("$.price", 			is(randomOffer0.priceTodouble())))
				.andExpect(jsonPath("$.currencyCode", 	is(randomOffer0.getCurrencyCode())))
				.andExpect(jsonPath("$.expiration", 	is(randomOffer0.expirationToString())))
				.andExpect(jsonPath("$.canceled", 		is(randomOffer0.isCanceled())))
				.andExpect(jsonPath("$.status", 		is(randomOffer0.statusToString())));
	    
	    offerService.deleteAll();
	}
	
	@Test
	public void postTest() throws Exception {	
		Offer postOffer = TestUtil.offerBuildRandom();
		
	    mvc.perform(post("/offer")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.content(objectMapper.writeValueAsString(postOffer)))
	    
	    		.andExpect(status().isCreated())
				
				.andExpect(jsonPath("$.description", 	is(postOffer.getDescription())))
				.andExpect(jsonPath("$.price", 			is(postOffer.priceTodouble())))
				.andExpect(jsonPath("$.currencyCode", 	is(postOffer.getCurrencyCode())))
				.andExpect(jsonPath("$.expiration", 	is(postOffer.expirationToString())))
				.andExpect(jsonPath("$.canceled", 		is(postOffer.isCanceled())))
				.andExpect(jsonPath("$.status", 		is(postOffer.statusToString())));
	    
	    offerService.deleteAll();
	}
	
	@Test
	public void putTest() throws Exception {	
		Offer putOffer = TestUtil.offerBuildRandom();
		Offer postOffer = TestUtil.offerBuildRandom();
		Offer savedOffer = offerService.save(postOffer);
		
	    mvc.perform(put("/offer/{id}", savedOffer.getId())
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.content(objectMapper.writeValueAsString(putOffer)))
	    
	    		.andExpect(status().isOk())
	    		
	    		.andExpect(jsonPath("$.id", 			is(savedOffer.getId())))
				.andExpect(jsonPath("$.description", 	is(putOffer.getDescription())))
				.andExpect(jsonPath("$.price", 			is(putOffer.priceTodouble())))
				.andExpect(jsonPath("$.currencyCode", 	is(putOffer.getCurrencyCode())))
				.andExpect(jsonPath("$.expiration", 	is(putOffer.expirationToString())))
				.andExpect(jsonPath("$.canceled", 		is(putOffer.isCanceled())))
				.andExpect(jsonPath("$.status", 		is(putOffer.statusToString())));
	    
	    offerService.deleteAll();
	}
	
	@Test
	public void patchTest() throws Exception {	
		String description = "patchTest";
		BigDecimal price = BigDecimal.valueOf(13.131);
		
		Offer patchOffer = new Offer(	null,
										description,
										price,
										null,
										null,
										null);
		
		
		Offer postOffer = TestUtil.offerBuildRandom();
		Offer savedOffer = offerService.save(postOffer);
		
	    mvc.perform(patch("/offer/{id}", savedOffer.getId())
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.content(objectMapper.writeValueAsString(patchOffer)))
	    
	    		.andExpect(status().isOk())
	    		
	    		.andExpect(jsonPath("$.id", 			is(savedOffer.getId())))
				.andExpect(jsonPath("$.description", 	is(patchOffer.getDescription())))
				.andExpect(jsonPath("$.price", 			is(patchOffer.priceTodouble())))
				.andExpect(jsonPath("$.currencyCode", 	is(postOffer.getCurrencyCode())))
				.andExpect(jsonPath("$.expiration", 	is(postOffer.expirationToString())))
				.andExpect(jsonPath("$.canceled", 		is(postOffer.isCanceled())))
				.andExpect(jsonPath("$.status", 		is(postOffer.statusToString())));
	    
	    offerService.deleteAll();
	}
	
	@Test
	public void deleteTest() throws Exception {	
		Offer randomOffer0 = TestUtil.offerBuildRandom();
		Offer randomOffer1 = TestUtil.offerBuildRandom();
		
		Offer savedOffer0 = offerService.save(randomOffer0);
		offerService.save(randomOffer1);
		
	    mvc.perform(delete("/offer/{id}", savedOffer0.getId())
	    		.contentType(MediaType.APPLICATION_JSON))
	    
	    		.andExpect(status().isOk())
	    		
	    		.andExpect(jsonPath("$.id", 			is(savedOffer0.getId())));
	    
	    offerService.deleteAll();
	}
	
}
