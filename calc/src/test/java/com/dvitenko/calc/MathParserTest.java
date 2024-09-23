package com.dvitenko.calc;

import org.junit.jupiter.api.Test;

import com.dvitenko.calc.func.AST.Node;
import com.dvitenko.calc.func.MathParser;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MathParserTest {

    @Test
    public void testTokenizeSimpleExpression() {
        MathParser parser = new MathParser("3+5*2");
        List<String> tokens = parser.tokenize("3+5*2");
        assertEquals(List.of("3", "+", "5", "*", "2"), tokens);
    }

    @Test
    public void testTokenizeWithParentheses() {
        MathParser parser = new MathParser("(3+5)*2");
        List<String> tokens = parser.tokenize("(3+5)*2");
        assertEquals(List.of("(", "3", "+", "5", ")", "*", "2"), tokens);
    }

    @Test
    public void testTokenizeWithVariables() {
        MathParser parser = new MathParser("2a+3b");
        List<String> tokens = parser.tokenize("2a+3b");
        assertEquals(List.of("2", "*", "a", "+", "3", "*", "b"), tokens);
    }

    @Test
    public void testTokenizeWithExponents() {
        MathParser parser = new MathParser("2^3+5");
        Node node = parser.evaluate();
        assertEquals("+\r\n" + //
                        "├── ^\r\n" + //
                        "│   ├── 2\r\n" + //
                        "│   └── 3\r\n" + //
                        "└── 5\r\n", 
                        node.toString());
    }
}

