package org.example;

public class SegmentTree {
    Node root;

    private static class Node {
        int data;
        int startInterval;
        int endInterval;
        String firstLetter;
        String lastLetter;
        int lengthLongestSuffix;
        int lengthLongestPrefix;
        Node left;
        Node right;

        public Node(int startInterval, int endInterval) {
            this.startInterval = startInterval;
            this.endInterval = endInterval;
            this.firstLetter = "L";
            this.lastLetter = "L";
            this.lengthLongestSuffix = 1;
            this.lengthLongestPrefix = 1;
            this.data = 1;
        }
    }

    public SegmentTree(int n) {
        // create a tree using this array
        this.root = constructTree(1, n);
    }

    private Node constructTree(int start, int end) {
        if(start == end) {
            // leaf node
            return new Node(start, end);
        }

        // create new node with index you are at
        Node node = new Node(start, end);

        int mid = (start + end) / 2;

        node.left = this.constructTree(start, mid);
        node.right = this.constructTree(mid + 1, end);

        return node;
    }


    // update
    public int update(int index) {
        return update(this.root, index).data;
    }


    private Node update(Node node, int index) {
        if(index >= node.startInterval && index <= node.endInterval) {
            if(index == node.startInterval && index == node.endInterval) {
                if(node.firstLetter.equals("L")) {
                    node.firstLetter = "R";
                    node.lastLetter = "R";
                } else {
                    node.firstLetter = "L";
                    node.lastLetter = "L";
                }
                return node;
            } else {
                Node leftNode = update(node.left, index);
                Node rightNode = update(node.right, index);

                node.firstLetter = leftNode.firstLetter;
                node.lastLetter = rightNode.lastLetter;
                if(!leftNode.lastLetter.equals(rightNode.firstLetter)) {
                    node.data = leftNode.lengthLongestSuffix + rightNode.lengthLongestPrefix;
                    node.lengthLongestPrefix = leftNode.lengthLongestPrefix + rightNode.lengthLongestPrefix;
                    node.lengthLongestSuffix = rightNode.lengthLongestSuffix + leftNode.lengthLongestSuffix;
                } else {
                    node.data = Math.max(leftNode.data, rightNode.data);
                    node.lengthLongestSuffix = rightNode.lengthLongestSuffix;
                    node.lengthLongestPrefix = leftNode.lengthLongestPrefix;
                }
                return node;
            }
        }

        return node;
    }
}
