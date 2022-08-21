package bankAccountMicroservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping("/bank-account/user/{email}")
	public BankAccount getBankAccount(@PathVariable String email) {
		return bankAccountRepository.findByEmailAddress(email);
	}

	@PutMapping("/bank-account/{email}/from/{from}/to/{to}/amount/{amount}/total/{total}")
	BankAccount exchangeCurrency(@PathVariable String email, @PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal amount, @PathVariable BigDecimal total) {
		BankAccount bankAcc = bankAccountRepository.findByEmailAddress(email);
		
		if(from.toUpperCase().equals("USD")) {
			bankAcc.setUsd(bankAcc.getUsd().subtract(amount));
		}
		else if(from.toUpperCase().equals("GBP")) {
			bankAcc.setGbp(bankAcc.getGbp().subtract(amount));
		}
		else if(from.toUpperCase().equals("CHF")) {
			bankAcc.setChf(bankAcc.getChf().subtract(amount));
		}
		else if(from.toUpperCase().equals("EUR")) {
			bankAcc.setEur(bankAcc.getEur().subtract(amount));
		}
		else if(from.toUpperCase().equals("RSD")) {
			bankAcc.setRsd(bankAcc.getRsd().subtract(amount));
		}
		
		if(to.toUpperCase().equals("USD")) {
			bankAcc.setUsd(bankAcc.getUsd().add(total));
		}
		else if(to.toUpperCase().equals("GBP")) {
			bankAcc.setGbp(bankAcc.getGbp().add(total));
		}
		else if(to.toUpperCase().equals("CHF")) {
			bankAcc.setChf(bankAcc.getChf().add(total));
		}
		else if(to.toUpperCase().equals("EUR")) {
			bankAcc.setEur(bankAcc.getEur().add(total));
		}
		else if(to.toUpperCase().equals("EUR")) {
			bankAcc.setRsd(bankAcc.getRsd().add(total));
		}
				
		return bankAccountRepository.save(bankAcc);
	}
	
	@PutMapping("/bank-account/{email}/update/{update}/quantity/{quantity}")
	public BankAccount updateOne(@PathVariable String email, @PathVariable String update, @PathVariable BigDecimal quantity) {
		
		BankAccount bankAcc = bankAccountRepository.findByEmailAddress(email);
		
		if(update.toUpperCase().equals("USD")) {
			bankAcc.setUsd(bankAcc.getUsd().add(quantity));
		}
		else if(update.toUpperCase().equals("GBP")) {
			bankAcc.setGbp(bankAcc.getGbp().add(quantity));
		}
		else if(update.toUpperCase().equals("CHF")) {
			bankAcc.setChf(bankAcc.getChf().add(quantity));
		}
		else if(update.toUpperCase().equals("EUR")) {
			bankAcc.setEur(bankAcc.getEur().add(quantity));
		}
		else if(update.toUpperCase().equals("RSD")) {
			bankAcc.setRsd(bankAcc.getRsd().add(quantity));
		}
				
		return bankAccountRepository.save(bankAcc);
	}
}
