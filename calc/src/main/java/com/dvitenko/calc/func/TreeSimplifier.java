package com.dvitenko.calc.func;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.dvitenko.calc.func.AST.*;

public class TreeSimplifier {   
    public static Node simplify(Node node){
        node = evaluate(node);  // Evaluate constants
        return convertNums(node);  // Convert numbers to desired format
    }

    // Recursively evaluates and simplifies constant expressions
    private static Node evaluate(Node node) {
        if (node == null) return null;

        node.left = evaluate(node.left);
        node.right = evaluate(node.right);

        // Evaluate functions, like sqrt(4) -> 2
        if (node.left != null) {
            if (node.type == Type.FUNCTION && node.left.type == Type.NUMBER) {
                node.value = String.valueOf(calculateFunc(node.value, node.left.value));
                node.type = Type.NUMBER;
                node.left = null;  // Clear left node after evaluation
            }
            // Evaluate operations, like 2 + 2 -> 4
            else if (node.type == Type.OPERATOR && node.left.type == Type.NUMBER && node.right.type == Type.NUMBER) {
                node.value = String.valueOf(calculateOp(node.value, node.left.value, node.right.value));
                node.type = Type.NUMBER;
                node.left = null;  // Clear left node after evaluation
                node.right = null; // Clear right node after evaluation
            }
        }

        return node;
    }

    // Calculate operations, such as +, -, *, /, and ^
    private static double calculateOp(String op, String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case "^": return Math.pow(a, b);
        }
        throw new IllegalArgumentException("Unknown operator " + op);
    }

    // Calculate functions such as sin, cos, tan, sqrt
    private static double calculateFunc(String func, String arg) {
        double a = Double.parseDouble(arg);
        switch (func) {
            case "sin": return Math.sin(a);
            case "cos": return Math.cos(a);
            case "tan": return Math.tan(a);
            case "sqrt": return Math.sqrt(a);
        }
        throw new IllegalArgumentException("Unknown function " + func);
    }

    // Converts numbers to remove trailing zeros or round to three decimal places
    private static Node convertNums(Node node) {
        if (node == null) return null;

        node.left = convertNums(node.left);
        node.right = convertNums(node.right);

        if(node.type == Type.NUMBER){
            double val = Double.parseDouble(node.value);
            if(val == Math.floor(val)) {
                node.value = String.valueOf((int) val);  // Remove trailing zeros
            } else {
                BigDecimal bd = new BigDecimal(val);
                bd = bd.setScale(3, RoundingMode.HALF_UP);  // Round to 3 decimal places
                node.value = String.valueOf(bd.doubleValue());
            }
        }

        return node;
    }
}
