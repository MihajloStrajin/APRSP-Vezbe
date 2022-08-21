package cryptoWalletMicroservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoWalletController {
	
	@Autowired
	private CryptoWalletRepository walletRepository;
	
	@GetMapping("/crypto-wallet/{email}")
	public CryptoWallet getWallet(@PathVariable String email) {
		return walletRepository.findByEmailAddress(email);
	}
	
	@PutMapping("/crypto-wallet/{email}/from/{from}/to/{to}/quantity/{quantity}/total/{total}")
	public CryptoWallet exchange(@PathVariable String email, @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable BigDecimal total) {
		CryptoWallet wallet = walletRepository.findByEmailAddress(email);
		
		if(from.toLowerCase().equals("btc")) {
			wallet.setBtc(wallet.getBtc().subtract(quantity));
		}
		else if(from.toLowerCase().equals("eth")) {
			wallet.setEth(wallet.getEth().subtract(quantity));
		}
		else if(from.toLowerCase().equals("ada")) {
			wallet.setAda(wallet.getAda().subtract(quantity));
		}
		
		if(to.toLowerCase().equals("btc")) {
			wallet.setBtc(wallet.getBtc().add(total));
		}
		else if(to.toLowerCase().equals("eth")) {
			wallet.setEth(wallet.getEth().add(total));
		}
		else if(to.toLowerCase().equals("ada")) {
			wallet.setAda(wallet.getAda().add(total));
		}
		
		return walletRepository.save(wallet);
	}
	
	@PutMapping("/crypto-wallet/{email}/update/{update}/quantity/{quantity}")
	public CryptoWallet updateOne(@PathVariable String email, @PathVariable String update, @PathVariable BigDecimal quantity) {
		
		CryptoWallet wallet = walletRepository.findByEmailAddress(email);
		
		if(update.toLowerCase().equals("btc")) {
			wallet.setBtc(wallet.getBtc().add(quantity));
		}
		else if(update.toLowerCase().equals("ada")) {
			wallet.setAda(wallet.getAda().add(quantity));
		}
		else if(update.toLowerCase().equals("eth")) {
			wallet.setEth(wallet.getEth().add(quantity));
		}
		
		return walletRepository.save(wallet);
	}
}
