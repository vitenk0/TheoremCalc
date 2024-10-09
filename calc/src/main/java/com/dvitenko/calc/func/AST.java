package com.dvitenko.calc.func;

import java.util.ArrayList;
import java.util.List;

public class AST {
    enum Type {
        NUMBER, 
        VARIABLE, 
        OPERATOR,
        FUNCTION,
        UNARY
    }

    public static class Node {
        String value;
        Type type;
        List<Node> children;

        public Node(String value, Type type) {
            this.value = value;
            this.type = type;
            this.children = new ArrayList<>();
        }

        public void addChild(Node child) {
            this.children.add(child);
        }

        public String toString() {
            StringBuilder buffer = new StringBuilder(50);
            print(buffer, "", "");
            return buffer.toString();
        }

        private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
            buffer.append(prefix);
            buffer.append(value);
            buffer.append('\n');
            for (int i = 0; i < children.size(); i++) {
                Node child = children.get(i);
                if (i < children.size() - 1) {
                    child.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
                } else {
                    child.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
                }
            }
        }

        private int getPrecedence(String op) {
            switch (op) {
                case "+":
                case "-":
                    return 1;
                case "*":
                case "/":
                    return 2;
                case "^":
                    return 3;
                default:
                    return 0;
            }
        }

        public String toLatex() {
            if (this.type == Type.NUMBER || this.type == Type.VARIABLE) {
                return value;
            } else if (this.type == Type.OPERATOR) {
                StringBuilder latex = new StringBuilder();

                // Handle addition with multiple children
                if (value.equals("+")) {
                    for (int i = 0; i < children.size(); i++) {
                        String childLatex = children.get(i).toLatex();
                        
                        // Apply parentheses if the child is an operator with lower precedence or a negative number
                        boolean needParen = children.get(i).type == Type.OPERATOR && 
                            getPrecedence(children.get(i).value) < getPrecedence(value) ||
                            (children.get(i).type == Type.NUMBER && children.get(i).value.charAt(0) == '-');
                        
                        if (needParen) {
                            childLatex = "\\left(" + childLatex + "\\right)";
                        }
                        
                        // Append the child LaTeX to the result
                        if (i > 0) {
                            latex.append(" + ");
                        }
                        latex.append(childLatex);
                    }
                    return latex.toString();
                }
                
                String leftLatex = children.size() > 0 ? children.get(0).toLatex() : "";
                String rightLatex = children.size() > 1 ? children.get(1).toLatex() : "";

                boolean needLeftParen = children.size() > 0 && 
                    (children.get(0).type == Type.OPERATOR && getPrecedence(children.get(0).value) < getPrecedence(value));
                boolean needRightParen = children.size() > 1 && 
                    (children.get(1).type == Type.OPERATOR && getPrecedence(children.get(1).value) <= getPrecedence(value));

                    if (needLeftParen) {
                        leftLatex = "\\left(" + leftLatex + "\\right)";
                    }
                    if (needRightParen || (children.size() > 1 && children.get(1).type == Type.NUMBER && children.get(1).value.charAt(0) == '-')) {
                        rightLatex = "\\left(" + rightLatex + "\\right)";
                    }

                switch (value) {
                    case "+":
                        return leftLatex + " + " + rightLatex;
                    case "-":
                        // Check if the right child exists and is a negative number
                        if (children.size() > 1 && children.get(1).type == Type.NUMBER && children.get(1).value.charAt(0) == '-') {
                            // If the right is a negative number, convert to a positive sign
                            return leftLatex + " + " + children.get(1).value.substring(1); // Combine as +
                        }
                        return leftLatex + " - " + rightLatex; // Normal subtraction
                    case "*":
                        return leftLatex + rightLatex; 
                    case "/":
                        return "\\frac{" + leftLatex + "}{" + rightLatex + "}";
                    case "^":
                        return leftLatex + "^{" + rightLatex + "}";
                    default:
                        return value;
                }
            } else if(this.type == Type.UNARY) {
                String childLatex = children.size() > 0 ? children.get(0).toLatex() : "";
                return value + "(" + childLatex + ")";
            } else if (this.type == Type.FUNCTION) {
                StringBuilder argsLatex = new StringBuilder();
                for (int i = 0; i < children.size(); i++) {
                    if (i > 0) {
                        argsLatex.append(", ");
                    }
                    argsLatex.append(children.get(i).toLatex());
                }
                String out = "";
                if(value.charAt(0) == '-') {
                    value = value.substring(1);
                    out = "-" + out;
                }
                if (value.equals("sqrt")) {
                    out = out + "\\sqrt{" + argsLatex + "}";
                } else {
                    out = out + "\\" + value + "\\left(" + argsLatex + "\\right)";
                }
                return out;
            }
            return "";
        }

        public String getCompleteLatex() {
            return "\\(" + this.toLatex() + "\\)";
        }
    }
}
