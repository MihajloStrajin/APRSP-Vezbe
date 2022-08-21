package cryptoConversionMicroservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CryptoConversionController {

	@Autowired
	private CryptoExchangeProxy proxy;

	@Autowired
	private CryptoWalletProxy walletProxy;

	@GetMapping("/crypto-conversion/from/{from}/to/{to}/quantity/{quantity}/wallet/{email}")
	@RateLimiter(name = "default")
	public ResponseEntity<Object> getConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity, @PathVariable String email) {

		CryptoWalletDto wallet = walletProxy.getWallet(email);
		
		try {
			if(from.toLowerCase().equals("btc")) {
				if (wallet.getBtc().compareTo(quantity) < 0) {
					throw new Exception("btc");
				}
			}
			else if(from.toLowerCase().equals("eth")) {
				if (wallet.getEth().compareTo(quantity) < 0) {
					throw new Exception("eth");
				}
			}
			else if(from.toLowerCase().equals("ada")) {
				if (wallet.getAda().compareTo(quantity) < 0) {
					throw new Exception("ada");
				}
			}
		}
		catch (Exception e) {
			return ResponseEntity.ok("NOT ENOUGH " + e.getMessage());
		}

		CryptoConversion temp = proxy.getExchange(from, to);
		
		CryptoWalletDto walletExchanged = walletProxy.exchange(wallet.getEmailAddress(), from, to, quantity, quantity.multiply(temp.getMultiple()));

		return ResponseEntity.ok(walletExchanged); 
				
	}
}
