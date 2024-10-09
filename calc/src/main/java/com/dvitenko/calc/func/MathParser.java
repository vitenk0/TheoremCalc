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
        List<Node> children = new ArrayList<>();
        children.add(node); // Add the first term

        while (getCurrentToken() != null && (getCurrentToken().equals("+") || getCurrentToken().equals("-"))) {
            String op = getCurrentToken();
            consumeToken();
            Node rightNode = parseTerm();
            Node opNode = new Node(op, Type.OPERATOR);
            opNode.addChild(node);    // Add previous node as the first child
            opNode.addChild(rightNode); // Add new term as the second child
            node = opNode;           // Set the operator node as the current node
        }

        return node;
    }

    // Parse multiplication and division
    private Node parseTerm() {
        Node node = parseFactor();
        List<Node> children = new ArrayList<>();
        children.add(node); // Add the first factor

        while (getCurrentToken() != null && (getCurrentToken().equals("*") || getCurrentToken().equals("/"))) {
            String op = getCurrentToken();
            consumeToken();
            Node rightNode = parseFactor(); // Parse next factor (not exponentiation)
            Node opNode = new Node(op, Type.OPERATOR);
            opNode.addChild(node);    // Add previous node as the first child
            opNode.addChild(rightNode); // Add new factor as the second child
            node = opNode;           // Set the operator node as the current node
        }

        return node;
    }

    // Parse exponentiation
    private Node parseFactor() {
        Node node = parseBase();
        while (getCurrentToken() != null && getCurrentToken().equals("^")) {
            String op = getCurrentToken();
            consumeToken();
            Node rightNode = parseBase(); // Parse the right side of exponentiation
            Node opNode = new Node(op, Type.OPERATOR);
            opNode.addChild(node);    // Add previous node as the first child
            opNode.addChild(rightNode); // Add new base as the second child
            node = opNode;           // Set the operator node as the current node
        }
        return node;
    }

    // Parse bases (numbers, variables, and expressions inside parentheses or functions)
    private Node parseBase() {
        String token = getCurrentToken();
        if (token == null) 
            return null;

        if (token.equals("-")) {
            consumeToken(); // Consume the '-'
            token = getCurrentToken();

            // If the next token is '(', handle it as a negative expression
            if (token != null && token.equals("(")) {
                consumeToken(); // consume '('
                Node node = parseExpression(); // Parse the expression inside parentheses
                consumeToken(); // consume ')'

                // Create a new node for the negative expression
                Node negativeNode = new Node("-", Type.UNARY);
                negativeNode.addChild(node); // Add the parsed expression as a child
                return negativeNode; // Return the negative expression node
            } 
            
            // If a valid node follows, treat it as a negative expression or number
            else if (token != null && (token.matches("\\d+") || token.matches("\\d+\\.\\d+") || token.matches("[a-zA-Z]+"))) {
                Node node = parseBase();
                node.value = "-" + node.value;
                return node;
            } else {
                return new Node("-1", Type.NUMBER); // Default to -1 if no valid token follows
            }
        }

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
        } else if (token.matches("[a-zA-Z]+")) {  // Variables or functions
            if (token.equals("sin") || token.equals("cos") || token.equals("tan") || token.equals("sqrt")) {
                consumeToken(); // consume function name
                consumeToken(); // consume '(' after function name
                Node argNode = parseExpression();
                consumeToken(); // consume ')'
                Node funcNode = new Node(token, Type.FUNCTION);
                funcNode.addChild(argNode); // Add argument to the function node
                return funcNode;
            } else {
                consumeToken(); // consume variable
                return new Node(token, Type.VARIABLE);
            }
        }
        return null;
    }

    public Node evaluate() {
        return parseExpression();
    }
}
