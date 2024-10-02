package com.dvitenko.calc.func;

import java.util.*;
import com.dvitenko.calc.func.AST.*;

public class MathParser {
    private List<String> tokens;
    private int currentTokenIndex;

    public MathParser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    private String getCurrentToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        }
        return null;
    }

    private void consumeToken() {
        currentTokenIndex++;
    }

    // Parse addition and subtraction
    private Node parseExpression() {
        Node node = parseTerm();
        while (getCurrentToken() != null && (getCurrentToken().equals("+") || getCurrentToken().equals("-"))) {
            String op = getCurrentToken();
            consumeToken();
            Node rightNode = parseTerm();
            node = new Node(op, Type.OPERATOR, node, rightNode);
        }
        return node;
    }

    // Parse multiplication and division
    private Node parseTerm() {
        Node node = parseExponentiation();
        while (getCurrentToken() != null && (getCurrentToken().equals("*") || getCurrentToken().equals("/"))) {
            String op = getCurrentToken();
            consumeToken();
            Node rightNode = parseExponentiation();
            node = new Node(op, Type.OPERATOR, node, rightNode);
        }
        return node;
    }

    // Parse exponents
    private Node parseExponentiation() {
        Node node = parseFactor();
        while (getCurrentToken() != null && getCurrentToken().equals("^")) {
            String op = getCurrentToken();
            consumeToken();
            Node rightNode = parseExponentiation();
            node = new Node(op, Type.OPERATOR, node, rightNode);
        }
        return node;
    }

    // Parse factors (numbers, variables, and expressions inside parentheses)
    private Node parseFactor() {
        String token = getCurrentToken();
        if (token == null) 
            return null;
        if (token.equals("(")) {
            consumeToken(); // consume '('
            Node node = parseExpression();
            consumeToken(); // consume ')'
            return node;
        } else if (token.matches("\\d+")) {  // Integer numbers
            consumeToken();
            return new Node(token, Type.NUMBER);
        } else if (token.matches("\\d+\\.\\d+")) {  // Floating point numbers
            consumeToken();
            return new Node(token, Type.NUMBER);
        } else if (token.matches("[a-zA-Z]+")) {  // Variables
            consumeToken();
            return new Node(token, Type.VARIABLE);
        }
        return null;
    }



    public Node evaluate() {
        return parseExpression();
    }
}
