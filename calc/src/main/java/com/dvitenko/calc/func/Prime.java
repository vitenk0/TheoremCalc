package com.dvitenko.calc.func;

import java.util.ArrayList;
import java.util.List;

public class Prime {
    private int start;
    private int end;

    public Prime(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public List<Integer> sieveOfAtkin() {
        boolean[] isPrime = new boolean[end + 1];
        
        if (end > 2) {
            isPrime[2] = true;
        }
        if (end > 3) {
            isPrime[3] = true;
        }

        // Apply the Sieve of Atkin's conditions
        for (int x = 1; x * x <= end; x++) {
            for (int y = 1; y * y <= end; y++) {
                // First condition: n = 4*x^2 + y^2
                int n = 4 * x * x + y * y;
                if (n <= end && (n % 12 == 1 || n % 12 == 5)) {
                    isPrime[n] = !isPrime[n];
                }

                // Second condition: n = 3*x^2 + y^2
                n = 3 * x * x + y * y;
                if (n <= end && n % 12 == 7) {
                    isPrime[n] = !isPrime[n];
                }

                // Third condition: n = 3*x^2 - y^2
                if (x > y) {
                    n = 3 * x * x - y * y;
                    if (n <= end && n % 12 == 11) {
                        isPrime[n] = !isPrime[n];
                    }
                }
            }
        }

        // Eliminate squares of primes
        for (int n = 5; n * n <= end; n++) {
            if (isPrime[n]) {
                for (int k = n * n; k <= end; k += n * n) {
                    isPrime[k] = false;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= end; i++) {
            if (isPrime[i] && i >= start) {
                primes.add(i);
            }
        }

        return primes;
    }
}
