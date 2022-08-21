package cryptoConversionMicroservice;

import java.math.BigDecimal;

public class CryptoWalletDto {

	private Long id;
	
	private BigDecimal btc;
	
	private BigDecimal eth;  
	
	private BigDecimal ada; 
	
	private Long bankAccountId;
	
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

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	
}