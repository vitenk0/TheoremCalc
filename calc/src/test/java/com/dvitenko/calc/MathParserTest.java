package com.dvitenko.calc;

import org.junit.jupiter.api.Test;

import com.dvitenko.calc.func.AST.Node;
import com.dvitenko.calc.func.MathParser;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MathParserTest {
    // Test getCompleteLatex() method
    @Test
    public void testGetCompleteLatex() {
        List<String> tokens = List.of("2", "+", "3", "*", "x");
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        assertEquals("\\(2 + 3x\\)", node.getCompleteLatex());
    }

    @Test
    public void testGetCompleteLatex2() {
        List<String> tokens = List.of("(", "2", "x", "+", "3.7", ")", "^", "4", "/", "5", "-", "g");
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        assertEquals("", node.getCompleteLatex());
    }
}

