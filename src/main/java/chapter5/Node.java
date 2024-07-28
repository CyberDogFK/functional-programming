package chapter5;

public class Node {
    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node(Node node) {
        this.value = node.value;
        this.left = null;
        this.right = null;
    }

    public int getValue() {
        return value;
    }

    public Node left() {
        return this.left;
    }

    public Node addLeft(int value) {
        Node node = new Node(value);
        this.left = node;
        return node;
    }

    public Node addLeft(Node node) {
        this.left = node;
        return this;
    }

    public Node right() {
        return this.right;
    }

    public Node addRight(int value) {
        Node node = new Node(value);
        this.right = node;
        return node;
    }

    public Node addRight(Node node) {
        this.right = node;
        return this;
    }

    public static void main(String[] args) {
        Node root = new Node(12);
        root.addLeft(8).addRight(9);
        root.addRight(18).addLeft(14).addRight(17);

        postOrder(root);
        System.out.println();

        System.out.println(pow(9, 5));

        System.out.println();
        preOrder(root);

        Node treeCopy = copyTree(root);
    }

    public static void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left());
            postOrder(node.right());
            System.out.print(node.getValue() + " ");
        }
    }

    public static void preOrder(Node node) {
        if (node == null) {
            return;
        } else {
            System.out.print(node.getValue() + " ");
            preOrder(node.left());
            preOrder(node.right());
        }
    }

    public static Node copyTree(Node node) {
        if (node == null) {
            return null;
        } else {
            return (new Node(node)
                    .addLeft(copyTree(node.left())))
                    .addRight(copyTree(node.right()));
        }
    }

    public static void inOrder(Node node) {
        if (node == null) {
            return;
        } else {
            inOrder(node.left());
            System.out.print(node.getValue() + " ");
            inOrder(node);
        }
    }

    private static long pow(long base, long exponent) {
        if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return base;
        } else {
            long intermediate = pow(base, exponent / 2);
            if (exponent % 2 == 0) {
                return intermediate * intermediate;
            } else {
                return intermediate * intermediate * base;
            }
        }
    }
}
