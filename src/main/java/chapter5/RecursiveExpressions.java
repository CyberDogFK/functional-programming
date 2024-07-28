package chapter5;

import java.util.function.Consumer;

public class RecursiveExpressions {
    public static void main(String[] args) {
        Consumer<Node> inorder;
//        inorder = (Node node) -> {
//            if (node == null) {
//                return;
//            } else {
//                inorder.accept(node.left());
//                System.out.println(node.getValue() + " ");
//                inorder.accept(node.right());
//            }
//        };
        Node root = new Node(12);
        root.addLeft(8).addRight(9);
        root.addRight(18).addLeft(14).addRight(17);
//        inorder.accept(root);
    }

    public int arrayTotalHelper(int numbers[], int index) {
        if (index >= 0 && index <= numbers.length - 1) {
            return arrayTotal(numbers, index);
        } else {
            throw new RuntimeException("");
        }
    }

    public int arrayTotal(int[] numbers, int index) {
        if (index == 0) {
            return numbers[0];
        } else {
            return numbers[index] + arrayTotal(numbers, index - 1);
        }
    }
}
