package cryptoTradeMicroservice;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cryptoTradeMicroservice.dto.BankAccountDto;
import cryptoTradeMicroservice.dto.CryptoWalletDto;
import cryptoTradeMicroservice.dto.CurrencyExchangeDto;
import cryptoTradeMicroservice.proxy.BankAccountProxy;
import cryptoTradeMicroservice.proxy.CryptoWalletProxy;
import cryptoTradeMicroservice.proxy.CurrencyExchangeProxy;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CryptoTradeController {
	
	@Autowired
	CryptoTradeRepository repo;

	@Autowired
	private CryptoWalletProxy walletProxy;

	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;

	@Autowired
	private BankAccountProxy bankProxy;
	
	double addTo;
	double quantityToReduce;
	
	@GetMapping("/crypto-trade/from/{from}/to/{to}/quantity/{quantity}/user/{email}")
	@RateLimiter(name = "default")
	private ResponseEntity<Object> trade(@PathVariable String from, @PathVariable String to, @PathVariable String email, @PathVariable BigDecimal quantity) {
		
		if (from.toLowerCase().equals("btc") || from.toLowerCase().equals("eth") || from.toLowerCase().equals("ada")) {
			CryptoWalletDto wallet = walletProxy.getWallet(email);
			if(wallet == null) {
				return ResponseEntity.ok("CRYPTO WALLET NOT FOUND");
			}

			try {
				if(from.toLowerCase().equals("btc")) {
					if (wallet.getBtc().compareTo(quantity) < 0) {
						throw new RuntimeException("BTC");
					}
				}
				else if(from.toLowerCase().equals("eth")) {
					if (wallet.getEth().compareTo(quantity) < 0) {
						throw new RuntimeException("ETH");
					}
				}
				else if(from.toLowerCase().equals("ada")) {
					if (wallet.getAda().compareTo(quantity) < 0) {
						throw new RuntimeException("ADA");
					}
				}
				else {
					return ResponseEntity.ok("UNSUPORTED CURRENCY");
				}
					
			} catch (Exception e) {
				return ResponseEntity.ok("NOT ENOUGH " + e.getMessage());
			}

			quantityToReduce = 0 - quantity.doubleValue();

			if (to.toLowerCase().equals("eur") || to.toLowerCase().equals("usd")) {
				CryptoTrade ct = repo.findByExchangeFromAndExchangeTo(from, to);

				addTo =  quantity.multiply(ct.getMultiplier()).doubleValue();
			} else if (to.toLowerCase().equals("chf") || to.toLowerCase().equals("gbp")
					|| to.toLowerCase().equals("rsd")) {

				CryptoTrade ct = repo.findByExchangeFromAndExchangeTo(from, "USD");

				CurrencyExchangeDto ce = currencyExchangeProxy.getExchange("USD", to.toUpperCase());

				addTo = quantity.multiply(ct.getMultiplier()).multiply(ce.getConversionMultiple()).doubleValue();
			}

			walletProxy.updateOne(email, from, new BigDecimal(addTo).setScale(5, RoundingMode.HALF_UP));

			BankAccountDto bankAccount = bankProxy.updateOne(email, to, new BigDecimal(addTo).setScale(5, RoundingMode.HALF_UP));

			return ResponseEntity.ok(bankAccount);
		}
		else if (from.toLowerCase().equals("eur") || from.toLowerCase().equals("usd")) {
			BankAccountDto bankAccount = bankProxy.getBankAccount(email);
			if(bankAccount == null) {
				return ResponseEntity.ok("BANK ACCOUNT NOT FOUND");
			}

			try {
				if(from.toLowerCase().equals("eur")) {
					if (bankAccount.getEur().compareTo(quantity) < 0) {
						throw new RuntimeException("EUR");
					}
				}
				else if(from.toLowerCase().equals("usd")) {
					if (bankAccount.getUsd().compareTo(quantity) < 0) {
						throw new RuntimeException("USD");
					}
				}
			} catch (Exception e) {
				return ResponseEntity.ok("NOT ENOUGH " + e.getMessage());
			}

			quantityToReduce = 0 - quantity.doubleValue();

			CryptoTrade cryptoTrade = repo.findByExchangeFromAndExchangeTo(from, to);

			addTo = quantity.multiply(cryptoTrade.getMultiplier()).doubleValue();

			CryptoWalletDto wallet = walletProxy.updateOne(email, to, new BigDecimal(addTo));

			bankProxy.updateOne(email, from, new BigDecimal(quantityToReduce));

			return ResponseEntity.ok(wallet);
		}
		else if (from.toLowerCase().equals("chf") || from.toLowerCase().equals("gbp")
				|| from.toLowerCase().equals("rsd")) {
			BankAccountDto bankAccount = bankProxy.getBankAccount(email);
			if(bankAccount == null) {
				return ResponseEntity.ok("BANK ACCOUNT NOT FOUND");
			}
			
			try {
				if(from.toLowerCase().equals("rsd")) {
					if (bankAccount.getEur().compareTo(quantity) < 0) {
						throw new RuntimeException("RSD");
					}
				}
				else if(from.toLowerCase().equals("chf")) {
					if (bankAccount.getUsd().compareTo(quantity) < 0) {
						throw new RuntimeException("CHF");
					}
				}
				else if(from.toLowerCase().equals("gbp")) {
					if (bankAccount.getUsd().compareTo(quantity) < 0) {
						throw new RuntimeException("GBP");
					}
				}
			} catch (Exception e) {
				return ResponseEntity.ok("NOT ENOUGH " + e.getMessage());
			}
			
			quantityToReduce = 0 - quantity.doubleValue();
			
			CryptoTrade cryptoTrade = repo.findByExchangeFromAndExchangeTo("USD", to.toUpperCase());

			CurrencyExchangeDto ce = currencyExchangeProxy.getExchange(from.toUpperCase(), "USD");
			
			addTo = quantity.multiply(cryptoTrade.getMultiplier()).multiply(ce.getConversionMultiple()).doubleValue();
			
			CryptoWalletDto wallet = walletProxy.updateOne(email, to, new BigDecimal(addTo));

			bankProxy.updateOne(email, from, new BigDecimal(quantityToReduce));

			return ResponseEntity.ok(wallet);
		}
		
		return null;
	}
}
