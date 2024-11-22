package org.example;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://ru.wikipedia.org/wiki/%D0%90%D0%92%D0%9B-%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%BE">https://ru.wikipedia.org/wiki/%D0%90%D0%92%D0%9B-%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%BE</a>
 * <a href="https://habr.com/ru/articles/150732/">https://habr.com/ru/articles/150732/</a>
 * <a href="https://github.com/surajsubramanian/AVL-Trees/tree/master">https://github.com/surajsubramanian/AVL-Trees/tree/master</a>
 * @author Адельсон-Вельский Георгий Максимович и Ландис Евгений Михайлович
 */
public class AVLTree {
    private Node root;

    public boolean add(int key) {
        if (findNode(root, key) == null) {
            root = bstInsert(root, key);
            return true;
        }
        return false;
    }

    public void update(int x) {
        Node nodeToUpdate = findNode(root, x);
        Node leftNode = findNode(root, x - 1);
        Node rightNode = findNode(root, x + 1);
        if(nodeToUpdate.letter.equals("L")) {
            nodeToUpdate.letter = "R";
        } else {
            nodeToUpdate.letter = "L";
        }
        if(leftNode != null && !leftNode.letter.equals(nodeToUpdate.letter)) {
            nodeToUpdate.lengthPrefix = leftNode.lengthPrefix + 1;
        } else {
            nodeToUpdate.lengthPrefix = 0;
        }
        if(rightNode != null && !rightNode.letter.equals(nodeToUpdate.letter)) {
            nodeToUpdate.lengthSuffix = rightNode.lengthSuffix + 1;
        } else {
            nodeToUpdate.lengthSuffix = 0;
        }

        if(leftNode != null && !leftNode.letter.equals(nodeToUpdate.letter)) {
            leftNode.lengthSuffix = nodeToUpdate.lengthSuffix + 1;
        }

        if(rightNode != null && !rightNode.letter.equals(nodeToUpdate.letter)) {
            rightNode.lengthPrefix = nodeToUpdate.lengthPrefix + 1;
        }

        //todo обновить значение в интервале
        System.out.println();
    }

    public int getMaxLength() {
        return getMaxLength(root);
    }

    private int getMaxLength(Node root) {
        if(root == null) {
            return 0;
        }
        return Math.max(
                root.lengthSuffix + root.lengthPrefix + 1, Math.max(getMaxLength(root.left), getMaxLength(root.right))
        );
    }

    public boolean contains(int key) {
        return findNode(root, key) != null;
    }


    public boolean remove(int key) {
        if (findNode(root, key) != null) {
            root = remove(root, key);
            return true;
        }
        return false;
    }

    public List<Integer> inOrder(){
        List<Integer> arrayList = new ArrayList<>();
        inOrder(root, arrayList);
        return arrayList;
    }

    public void clear(){
        root = null;
    }


    private int Height(Node key) {
        if (key == null)
            return 0;

        else
            return key.height;
    }


    private int Balance(Node key) {
        if (key == null)
            return 0;

        else
            return (Height(key.right) - Height(key.left));
    }


    private void updateHeight(Node key) {
        int l = Height(key.left);
        int r = Height(key.right);

        key.height = Math.max(l, r) + 1;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node balanceTree(Node root) {
        updateHeight(root);

        int balance = Balance(root);

        if (balance > 1) //R
        {
            if (Balance(root.right) < 0)//RL
            {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            } else //RR
                return rotateLeft(root);
        }

        if (balance < -1)//L
        {
            if (Balance(root.left) > 0)//LR
            {
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            } else//LL
                return rotateRight(root);
        }

        return root;
    }


    private Node bstInsert(Node root, int key) {
        // Performs normal BST insertion
        if (root == null)
            return new Node(key);

        else if (key < root.value)
            root.left = bstInsert(root.left, key);

        else
            root.right = bstInsert(root.right, key);

        // Balances the tree after BST Insertion
        return balanceTree(root);
    }

    private Node  successor(Node root) {
        if (root.left != null)
            return successor(root.left);

        else
            return root;
    }


    private Node remove(Node root, int key) {
        // Performs standard BST Deletion
        if (root == null)
            return root;

        else if (key < root.value)
            root.left = remove(root.left, key);

        else if (key > root.value)
            root.right = remove(root.right, key);

        else {
            if (root.right == null)
                root = root.left;

            else if (root.left == null)
                root = root.right;

            else {
                Node temp = successor(root.right);
                root.value = temp.value;
                root.right = remove(root.right, root.value);
            }
        }

        if (root == null)
            return root;

        else
            // Balances the tree after deletion
            return balanceTree(root);
    }

    private Node findNode(Node root, int key) {
        if (root == null || key == root.value)
            return root;

        if (key < root.value)
            return findNode(root.left, key);

        else
            return findNode(root.right, key);
    }



    private void inOrder(Node cur, List<Integer> res) {
        if (cur == null) {
            return;
        }

        if (cur.left != null)
            inOrder(cur.left, res);
        res.add(cur.value);
        if (cur.right != null)
            inOrder(cur.right, res);

    }

    @Getter
    static class Node {
        int value;
        String letter;
        int lengthSuffix;
        int lengthPrefix;
        String firstLetter;
        String lastLetter;
        int height;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.height = 1;
            this.letter = "L";
            this.lengthSuffix = 0;
            this.lengthPrefix = 0;
            this.firstLetter = "L";
            this.lastLetter = "L";
        }

        public String getLabel() {
            return String.format("%d (%d)", this.value, this.height);
        }
    }

}