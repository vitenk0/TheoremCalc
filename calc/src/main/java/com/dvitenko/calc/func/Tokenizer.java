package com.dvitenko.calc.func;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tokenizer {
    private String expression;
    public boolean isValid = true;
    public String message = null;

    @JsonCreator
    public Tokenizer(@JsonProperty("exp") String expression) {
        this.expression = expression.replaceAll("\\s+", "");
    }

    // Tokenize the expression into numbers, variables, operators, and parentheses
    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+\\.\\d+|\\d+|sin|cos|tan|sqrt|[a-zA-Z]+|[-+*/()^]").matcher(this.expression);
        
        int openCount = 0;
        int closeCount = 0;

        // Count parentheses in the expression
        for (char c : this.expression.toCharArray()) {
            if (c == '(') {
                openCount++;
            } else if (c == ')') {
                closeCount++;
            }
        }

        // Validate parentheses count
        if (openCount != closeCount) {
            this.isValid = false;
            this.message = "Mismatched Parentheses";
            return tokens;
        }

        int lastMatchEnd = 0;
        while (matcher.find()) {
            if (matcher.start() != lastMatchEnd) {
                this.isValid = false;
                this.message = this.expression.substring(lastMatchEnd, matcher.start());
                return tokens;
            }
            else {
                tokens.add(matcher.group());
            }
            lastMatchEnd = matcher.end();
        }
        if (lastMatchEnd != expression.length()) {
            this.isValid = false;
            this.message = this.expression.substring(lastMatchEnd);
            return tokens;
        }

        // Check for empty parentheses
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("(") && (i + 1 < tokens.size() && tokens.get(i + 1).equals(")"))) {
                this.isValid = false;
                this.message = "Empty parentheses";
                return tokens;
            }
        }

        // Check for unwrapped functions
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).matches("sin|cos|tan|sqrt") && (i + 1 >= tokens.size() || !tokens.get(i + 1).equals("("))) {
                this.isValid = false;
                this.message = "Function \"" + tokens.get(i) + "\" must be followed by parentheses";
                return tokens;
            }
        }

        List<String> processedTokens = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            processedTokens.add(tokens.get(i));

            // If a number or variable is followed by an argument or '(', insert '*', except for functions
            if (i < tokens.size() - 1 && (tokens.get(i).matches("\\d+\\.?\\d*") || tokens.get(i).matches("[a-zA-Z]+") || tokens.get(i).equals(")")) 
                && (tokens.get(i + 1).matches("[a-zA-Z]+") || tokens.get(i + 1).equals("("))) {
                // Check if the current token is a function
                if (!(tokens.get(i).matches("sin|cos|tan|sqrt") && (i + 1 < tokens.size() && tokens.get(i + 1).equals("(")))) {
                    processedTokens.add("*");
                }
            }
        }

        return processedTokens;
    }
}
