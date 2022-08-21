package cryptoTradeMicroservice.dto;

import java.math.BigDecimal;

public class CryptoWalletDto {

	private Long id;
	
	private BigDecimal btc;
	
	private BigDecimal eth;  
	
	private BigDecimal ada; 
	
	private String emailAddress;

	public Long getId() {
		return id;
	}

	public BigDecimal getBtc() {
		return btc;
	}

	public BigDecimal getEth() {
		return eth;
	}

	public BigDecimal getAda() {
		return ada;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	
}
