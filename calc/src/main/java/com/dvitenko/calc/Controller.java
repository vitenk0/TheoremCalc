package com.dvitenko.calc;


import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dvitenko.calc.func.*;

@RestController
@RequestMapping("/api")
public class Controller {

	@PostMapping("/prime")
	public List<Integer> SieveOfAtkin(@RequestBody Prime prime) {
		return prime.sieveOfAtkin();
	}

}
