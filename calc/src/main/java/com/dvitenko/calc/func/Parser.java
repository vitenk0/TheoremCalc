package com.dvitenko.calc.func;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.dvitenko.calc.func.AST.*;

class MathParser {

    // Tokenize the expression into numbers, variables, operators, and parentheses
    public static List<String> tokenize(String expression) {
        // Recognize numbers, variables, and operators
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+\\.\\d+|\\d+|[a-zA-Z]+|[-+*/()]").matcher(expression);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    // Recursive Descent Parser
    static class Parser {
        private List<String> tokens;
        private int currentTokenIndex;

        public Parser(List<String> tokens) {
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

        // Parse expressions with addition and subtraction
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

        // Parse terms with multiplication and division
        private Node parseTerm() {
            Node node = parseFactor();
            while (getCurrentToken() != null && (getCurrentToken().equals("*") || getCurrentToken().equals("/"))) {
                String op = getCurrentToken();
                consumeToken();
                Node rightNode = parseFactor();
                node = new Node(op, Type.OPERATOR, node, rightNode);
            }
            return node;
        }

        // Parse factors (numbers, variables, and expressions inside parentheses)
        private Node parseFactor() {
            String token = getCurrentToken();
            if (token == null) return null;

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
                return new AST.Node(token, Type.VARIABLE);
            }
            return null;
        }
    }

    public static Node evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        Parser parser = new Parser(tokens);
        return parser.parseExpression();
    }

    public static void main(String[] args) {
        String expression = "3 + 5 * (x - 8) / y";
        Node result = evaluate(expression);
        System.out.println(result.toString());
    }
}
