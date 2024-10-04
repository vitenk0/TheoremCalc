package com.dvitenko.calc;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvitenko.calc.func.*;

@RestController
@RequestMapping("/api")
public class Controller {

	@PostMapping("/prime")
	public ResponseEntity<apiResponse<List<Integer>>> sieveOfAtkin(@RequestBody Prime prime){
		if (prime.start < 0 || prime.end < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new apiResponse<>(null, "Invalid Input: Start and end must be positive integers"));
		}
		if (prime.start > prime.end) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new apiResponse<>(null, "Invalid Input: Start must be less than or equal to end"));
		}

		try {
			CompletableFuture<List<Integer>> futurePrimes = calculatePrimesAsync(prime);
			List<Integer> primes = futurePrimes.orTimeout(10, TimeUnit.SECONDS).join();  // Timeout after 5 seconds

			if (primes.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									.body(new apiResponse<>(null, "No prime numbers found in the given range"));
			}
			return ResponseEntity.ok(new apiResponse<>(primes, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
								.body(new apiResponse<>(null, "The request timed out, try a smaller range"));
		}
	}

	@Async
	public CompletableFuture<List<Integer>> calculatePrimesAsync(Prime prime) {
		return CompletableFuture.supplyAsync(() -> prime.sieveOfAtkin());
	}

	@PostMapping("/parse")
	public ResponseEntity<apiResponse<String>> Parser(@RequestBody Tokenizer tokenizer) {
		List<String> tokens = tokenizer.tokenize();
		if (!tokenizer.isValid) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									.body(new apiResponse<>(null, "Invalid Input: " + tokenizer.message));
		}
        MathParser parser = new MathParser(tokens);
		return ResponseEntity.ok(new apiResponse<>(parser.evaluate().toString(), null));
	}

	@PostMapping("/latex")
	public ResponseEntity<apiResponse<String>> toLaTex(@RequestBody Tokenizer tokenizer) {
		List<String> tokens = tokenizer.tokenize();
		MathParser parser = new MathParser(tokens);
		return ResponseEntity.ok(new apiResponse<>(parser.evaluate().getCompleteLatex(), null));
	}

}
