package cryptoWalletMicroservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "crypto_wallets")
public class CryptoWallet {

	@Id
	private Long id;

	@Column(precision = 20, scale = 5)
	private BigDecimal btc;

	@Column(precision = 20, scale = 5)
	private BigDecimal eth;

	@Column(precision = 20, scale = 5)
	private BigDecimal ada;

	@Column(name = "email_address")
	private String emailAddress;

	public CryptoWallet() {
		super();
	}

	public CryptoWallet(Long id, BigDecimal BTC, BigDecimal ETH, BigDecimal ADA, String emailAddress) {
		this.id = id;
		this.btc = BTC;
		this.eth = ETH;
		this.ada = ADA;
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBtc() {
		return btc;
	}

	public void setBtc(BigDecimal btc) {
		this.btc = btc;
	}

	public BigDecimal getEth() {
		return eth;
	}

	public void setEth(BigDecimal eth) {
		this.eth = eth;
	}

	public BigDecimal getAda() {
		return ada;
	}

	public void setAda(BigDecimal ada) {
		this.ada = ada;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
