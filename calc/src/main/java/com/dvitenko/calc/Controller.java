package com.dvitenko.calc;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvitenko.calc.excep.InvalidInputException;
import com.dvitenko.calc.func.*;

@RestController
@RequestMapping("/api")
public class Controller {

	@PostMapping("/prime")
	public ResponseEntity<apiResponse<List<Integer>>> sieveOfAtkin(@RequestBody Prime prime){
		if(prime.start < 0 || prime.end < 0) {
			return ResponseEntity.badRequest()
                                 .body(new apiResponse<>(null, "Invalid Input: Start and end must be positive integers"));
		}
		if(prime.start > prime.end) {
			return ResponseEntity.badRequest()
                                 .body(new apiResponse<>(null, "Invalid Input: Start must be less than or equal to end"));
		}
		return ResponseEntity.ok(new apiResponse<>(prime.sieveOfAtkin(), null));
	}

	@PostMapping("/parse")
	public String Parser(@RequestBody Tokenizer tokenizer) {
		List<String> tokens = tokenizer.tokenize();
		if (!tokenizer.isValid) {
			throw new InvalidInputException("Invalid Input: " + tokenizer.message);
		}
        MathParser parser = new MathParser(tokens);
		return parser.evaluate().toString();
	}

	@PostMapping("/latex")
	public String toLaTex(@RequestBody Tokenizer tokenizer) {
		List<String> tokens = tokenizer.tokenize();
		MathParser parser = new MathParser(tokens);
		return parser.evaluate().getCompleteLatex();
	}

}
