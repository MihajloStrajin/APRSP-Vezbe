package bankAccountMicroservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

	@Id
	private Long id;
	
	@Column(name="usd")
	private BigDecimal usd;
	
	@Column(name="gbp", precision = 20, scale=5)
	private BigDecimal gbp;
	
	@Column(name="chf", precision = 20, scale=5)
	private BigDecimal chf;
	
	@Column(name="eur", precision = 20, scale=5)
	private BigDecimal eur;
	
	@Column(name="rsd", precision = 50, scale=5)
	private BigDecimal rsd;
	
	@Column(unique = true,name="email_address")
	private String emailAddress;
	
	public BankAccount() {
		
	}
	
	public BankAccount(Long id, BigDecimal usd, BigDecimal gbp, BigDecimal chf, BigDecimal eur, BigDecimal rsd, String emailAddress) {
		this.id = id;
		this.usd = usd;
		this.gbp = gbp;
		this.chf = chf;
		this.eur = eur;
		this.rsd = rsd;
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getUsd() {
		return usd;
	}

	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}

	public BigDecimal getGbp() {
		return gbp;
	}

	public void setGbp(BigDecimal gbp) {
		this.gbp = gbp;
	}

	public BigDecimal getChf() {
		return chf;
	}

	public void setChf(BigDecimal chf) {
		this.chf = chf;
	}

	public BigDecimal getEur() {
		return eur;
	}

	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}

	public BigDecimal getRsd() {
		return rsd;
	}

	public void setRsd(BigDecimal rsd) {
		this.rsd = rsd;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}