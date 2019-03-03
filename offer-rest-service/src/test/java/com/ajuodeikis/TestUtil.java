package com.ajuodeikis;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import com.ajuodeikis.model.Offer;
import com.ajuodeikis.model.Status;

public class TestUtil {
	
	public static Date addDays(Date dt, int days) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, days);
		dt = c.getTime();
		  
		return dt;
	}
	
	public static double getRandomInt(int min, int max) {		  
		return min + Math.random() * (max - min);
	}
	
   public static boolean getRandomBoolean() {
       return Math.random() < 0.5;
   }
	
	public static Offer offerBuildRandom()
	{
		String description 		= RandomStringUtils.random(10, true, true);
		BigDecimal price 		= new BigDecimal(getRandomInt(0, 1000)) ;
		String currencyCode 	= RandomStringUtils.random(3, true, false);;
		Date expiration 		= addDays(new Date(), (int) (getRandomInt(-100, 100)));
		Boolean canceled 		= getRandomBoolean();
		
		Offer offer = new Offer(null,
								description,
								price,
								currencyCode,
								expiration,
								canceled);
		return offer;	
	}

	
	public static void offerAssert(
			String description,
			BigDecimal price,
			String currencyCode,
			Date expiration,
			Boolean canceled,
			Status status,
			Offer savedOffer) 
	{
		assertEquals(description, 	savedOffer.getDescription());
		assertEquals(price,			savedOffer.getPrice());
		assertEquals(currencyCode,	savedOffer.getCurrencyCode());
		assertEquals(expiration,	savedOffer.getExpiration());
		assertEquals(canceled, 		savedOffer.isCanceled());
		if ( status != null ) {
			assertEquals(status, 	savedOffer.getStatus());
		}
	 }

}
