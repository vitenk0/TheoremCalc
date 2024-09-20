package com.dvitenko.calc.func;

public class AST {
    enum Type {
        NUMBER, 
        VARIABLE, 
        OPERATOR
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
        }
    }
}
