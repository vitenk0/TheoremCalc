package com.dvitenko.calc.func;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.dvitenko.calc.func.AST.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeSimplifier {   
    public static Node simplify(Node node){
        node = collapse(node);  // Collapse tree
        node = evaluate(node);  // Evaluate tree
        return convertNums(node);  // Convert numbers to desired format
    }

    // Recursively collapses the tree by simplifying branches
    private static Node collapse(Node node) {
        if (node == null) return null;

        // Recursively collapse each child
        for (int i = 0; i < node.children.size(); i++) {
            node.children.set(i, collapse(node.children.get(i)));
        }

        // Collapse all addition branches into one
        if(node.value.equals("+")){
            List<Node> newChildren = new ArrayList<>();
            if(node.children.get(0).value.equals("+")) {
                for(Node grandChild: node.children.get(0).children) {
                    newChildren.add(grandChild);
                }
            } else {
                newChildren.add(node.children.get(0));
            }
            if(node.children.get(1).value.equals("+")) {
                for(Node grandChild: node.children.get(1).children) {
                    newChildren.add(grandChild);
                }
            } else {
                newChildren.add(node.children.get(1));
            }
            node.children = newChildren;
        }   

        return node;
    }

    private static Node evaluate(Node node){
        // Recursively evaluate each child
        for (int i = 0; i < node.children.size(); i++) {
            node.children.set(i, evaluate(node.children.get(i)));
        }

        // Simplify addition expressions, like 2 + x + 3 -> x + 5
        if (node.value.equals("+")) {
            Map<String, Double> vars = new HashMap<>();  // Map to store variables and their coefficients
            double constantSum = 0; 
        
            // Process each child node
            for (Iterator<Node> it = node.children.iterator(); it.hasNext(); ) {
                Node child = it.next();
                if (child.type == Type.NUMBER) {
                    constantSum += Double.parseDouble(child.value);  // Sum up constants
                    it.remove(); 
                } else if (child.type == Type.VARIABLE) {
                    vars.put(child.value, vars.getOrDefault(child.value, 0.0) + 1.0);  // Add 1 to coefficient if variable exists
                    it.remove(); 
                }
            }

            if(vars.isEmpty()) {
                node.type = Type.NUMBER;
                node.value = String.valueOf(constantSum);
            } else {
                // Add the variables with their coefficients back to the children
                for (Map.Entry<String, Double> entry : vars.entrySet()) {
                    double coefficient = entry.getValue();
                    String variable = entry.getKey();
            
                    if (coefficient == 1.0) {
                        node.children.add(new Node(variable, Type.VARIABLE));
                    } else {
                        Node mulNode = new Node("*", Type.OPERATOR);
                        mulNode.children.add(new Node(String.valueOf(coefficient), Type.NUMBER));
                        mulNode.children.add(new Node(variable, Type.VARIABLE));
                        node.children.add(mulNode);
                    }
                }
                
                if (constantSum != 0) {
                    node.children.add(new Node(String.valueOf(constantSum), Type.NUMBER));
                }
            }
        }

        // Evaluate functions, like sqrt(4) -> 2
        if (node.children.size() == 1 && node.type == Type.FUNCTION && node.children.get(0).type == Type.NUMBER) {
            node.value = String.valueOf(calculateFunc(node.value, node.children.get(0).value));
            node.type = Type.NUMBER;
            node.children.clear();  // Clear children after evaluation
        }

        // Evaluate operations, like 2 + 2 -> 4
        else if (node.children.size() == 2 && node.type == Type.OPERATOR 
                && node.children.get(0).type == Type.NUMBER 
                && node.children.get(1).type == Type.NUMBER) {
            node.value = String.valueOf(calculateOp(node.value, node.children.get(0).value, node.children.get(1).value));
            node.type = Type.NUMBER;
            node.children.clear();  // Clear children after evaluation
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
        if(func.charAt(0) == '-') {
            func = func.substring(1);
        }
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

        // Recursively convert numbers in children
        for (int i = 0; i < node.children.size(); i++) {
            node.children.set(i, convertNums(node.children.get(i)));
        }

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
