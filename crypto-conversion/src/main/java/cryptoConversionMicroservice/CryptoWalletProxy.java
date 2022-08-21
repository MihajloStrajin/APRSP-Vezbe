package cryptoConversionMicroservice;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("crypto-wallet")
public interface CryptoWalletProxy {
	
	@GetMapping("/crypto-wallet/{email}")
	CryptoWalletDto getWallet(@PathVariable String email);

	@PutMapping("/crypto-wallet/{email}/from/{from}/to/{to}/quantity/{quantity}/total/{total}")
	CryptoWalletDto exchange(@PathVariable String email, @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity, @PathVariable BigDecimal total);
}
