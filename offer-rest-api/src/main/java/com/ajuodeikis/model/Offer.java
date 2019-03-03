package com.ajuodeikis.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
//mport java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Offer {
	public static final int PRICE_SCALE = 2;
	public static final String EXPIRATION_PATTERN = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_TIMEZONE = "Europe/London";//TimeZone.getDefault().getID();
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	@NotBlank
	private String description;
	
	@NotNull
	@PositiveOrZero
	@Column(precision=10, scale=PRICE_SCALE)
	private BigDecimal price;
	
	@NotNull
	@Size(min = 3, max = 3)
	private String currencyCode;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EXPIRATION_PATTERN, timezone = DEFAULT_TIMEZONE)
	@NotNull
	protected Date expiration;
	
	@NotNull
	protected Boolean canceled;
	

	public Offer() {
		super();
	}
	
	public Offer(	Integer id, 
					String description, 
					BigDecimal price, 
					String currencyCode, 
					Date expiration, 
					Boolean canceled) 
	{
		super();
		
		this.setId(id);
		this.setDescription(description);
		this.setPrice(price);
		this.setCurrencyCode(currencyCode);
		this.setExpiration(expiration);
		this.setCanceled(canceled);
	}
	
	@Transient
	public Status getStatus() {
	    if ( ( canceled == null) || ( expiration == null) )	return null;
		
		if ( isCanceled() ) return Status.CANCELED;
	    if ( new Date().after(getExpiration()) ) return Status.EXPIRED;
	    return Status.ACTIVE;
	}
	
	public String statusToString() {
		return getStatus().name() ;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if ( description != null ) {
			this.description = description;
		}
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {		
		if ( price != null ) {
			this.price = price.setScale(PRICE_SCALE, RoundingMode.HALF_UP);
		}	
	}
	
	public double priceTodouble() {
		return this.getPrice().doubleValue();
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		if ( currencyCode != null ) {
			this.currencyCode = currencyCode;
		}
	}

	public Date getExpiration() {
		return expiration;
	}
	
	public String expirationToString() {
		return new SimpleDateFormat(EXPIRATION_PATTERN).format(this.getExpiration());
	}

	public void setExpiration(Date expiration) {
		if ( expiration != null ) {
			this.expiration = expiration;
		}
	}

	public Boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		if ( canceled != null ) {
			this.canceled = canceled;
		}
	}
	
}

