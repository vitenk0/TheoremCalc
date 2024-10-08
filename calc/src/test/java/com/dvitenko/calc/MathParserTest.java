package com.dvitenko.calc;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import com.dvitenko.calc.func.AST.Node;
import com.dvitenko.calc.func.MathParser;
import com.dvitenko.calc.func.Tokenizer;
import com.dvitenko.calc.func.TreeSimplifier;

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

    @Test
    public void testGetCompleteLatex4() {
        String exp = "x-2(-4)";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        assertEquals("\\(x - 2\\cdot-4\\)", node.getCompleteLatex());
    }

    @Test
    public void testSimplify() {
        String exp = "2+3*4";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = TreeSimplifier.simplify(parser.evaluate());
        assertEquals("\\(14\\)", node.getCompleteLatex());
    }

    @Test
    public void testNegative() {
        String exp = "-2+x";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = TreeSimplifier.simplify(parser.evaluate());
        assertEquals("\\(-2 + x\\)", node.getCompleteLatex());
    }
  
    @Test
    public void testDoubleNeg() {
        String exp = "x-2(-2)";
        Tokenizer tokenizer = new Tokenizer(exp);
        List<String> tokens = tokenizer.tokenize();
        MathParser parser = new MathParser(tokens);
        Node node = parser.evaluate();
        TreeSimplifier.simplify(node);
        assertEquals("\\(x + 4\\)", node.getCompleteLatex());
    }
}

