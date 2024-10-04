package com.dvitenko.calc;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import com.dvitenko.calc.func.AST.Node;
import com.dvitenko.calc.func.MathParser;
import com.dvitenko.calc.func.Tokenizer;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MathParserTest {

    @Test
    public void testGetCompleteLatex() {
        String exp = "2+3x";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        assertEquals("\\(2 + 3x\\)", node.getCompleteLatex());
    }

    @Test
    public void testGetCompleteLatex2() {
        String exp = "(2x+3.7)^(4/5-g)";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        assertEquals("\\(\\left(2x + 3.7\\right)^{\\left(\\frac{4}{5} - g\\right)}\\)", node.getCompleteLatex());
    }

    @Test
    public void testGetCompleteLatex3() {
        String exp = "sin(x)+tan(5x)";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        assertEquals("\\(\\sin\\left(x\\right) + \\tan\\left(5x\\right)\\)", node.getCompleteLatex());
    }
}

