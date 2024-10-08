package com.dvitenko.calc.func;

public class AST {
    enum Type {
        NUMBER, 
        VARIABLE, 
        OPERATOR,
        FUNCTION
    }

    public static class Node {
        String value;
        Node left;
        Node right;
        Type type;

        public Node(String value, Type type) {
            this.value = value;
            this.type = type;
            this.left = null;
            this.right = null;
        }

        public Node(String value, Type type, Node left, Node right) {
            this.value = value;
            this.type = type;
            this.left = left;
            this.right = right;
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
            if(this.type == Type.OPERATOR) {
                this.left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
                this.right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
            if(this.type == Type.FUNCTION) {
                this.left.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
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
            }
            else if (this.type == Type.OPERATOR) {
                String leftLatex = left != null ? left.toLatex() : "";
                String rightLatex = right != null ? right.toLatex() : "";

                boolean needLeftParen = left != null && 
                    (left.type == Type.OPERATOR && getPrecedence(left.value) < getPrecedence(value));
                boolean needRightParen = right != null && 
                    (right.type == Type.OPERATOR && getPrecedence(right.value) <= getPrecedence(value));

                if (needLeftParen) {
                    leftLatex = "\\left(" + leftLatex + "\\right)";
                }
                if (needRightParen) {
                    rightLatex = "\\left(" + rightLatex + "\\right)";
                }

                switch (value) {
                    case "+":
                    case "-":
                        if (right.type == Type.NUMBER && right.value.charAt(0) == '-') {
                            // If the right is a negative number, convert to a positive sign
                            return leftLatex + " + " + rightLatex.substring(1); // Combine as +
                        }
                        return leftLatex + " - " + rightLatex; // Normal subtraction
                    case "*":
                        return leftLatex + "\\cdot" + rightLatex; 
                    case "/":
                        return "\\frac{" + leftLatex + "}{" + rightLatex + "}";
                    case "^":
                        return leftLatex + "^{" + rightLatex + "}";
                    default:
                        return value;
                }
            }
            else if (this.type == Type.FUNCTION) {
                if(value.equals("sqrt")) {
                    return "\\sqrt{" + left.toLatex() + "}";
                } else {
                return "\\" + value + "\\left(" + left.toLatex() + "\\right)";
                }
            }
            return "";
        }

        public String getCompleteLatex() {
            return "\\(" + this.toLatex() + "\\)";
        }
    }
}
