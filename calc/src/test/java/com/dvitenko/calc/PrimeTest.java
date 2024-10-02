package com.dvitenko.calc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.dvitenko.calc.func.Prime;

public class PrimeTest {
    @Test
    public void testPrime() {
        Prime prime = new Prime(1,5);
        assertEquals(new ArrayList<>(Arrays.asList(2,3,5)), prime.sieveOfAtkin());
    }

    @Test
    public void testPrimeEmpty() {
        Prime prime = new Prime(1,1);
        assertEquals(new ArrayList<>(Arrays.asList()), prime.sieveOfAtkin());
    }
}
