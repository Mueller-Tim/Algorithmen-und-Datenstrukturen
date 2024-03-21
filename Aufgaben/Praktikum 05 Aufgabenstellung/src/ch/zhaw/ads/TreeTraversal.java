package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {
    private final TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    public void inorder(Visitor<T> visitor) {
        inOrderRecursive(root, visitor);
    }
    private void inOrderRecursive(TreeNode<T> node, Visitor<T> visitor){
        if(node != null){
            inOrderRecursive(node.left, visitor);
            visitor.visit(node.getValue());
            inOrderRecursive(node.right, visitor);

        }
    }

    public void preorder(Visitor<T> visitor) {
        preorderRecursive(root, visitor);
    }

    private void preorderRecursive(TreeNode<T> node, Visitor<T> visitor){
        if(node != null){
            visitor.visit(node.getValue());
            preorderRecursive(node.left, visitor);
            preorderRecursive(node.right, visitor);
        }
    }

    public void postorder(Visitor<T> visitor) {
        postorderRecursive(root, visitor);
    }

    private void postorderRecursive(TreeNode<T> node, Visitor<T> visitor){
        if(node != null){
            postorderRecursive(node.left, visitor);
            postorderRecursive(node.right, visitor);
            visitor.visit(node.getValue());
        }
    }

    @Override
    public void levelorder(Visitor<T> vistor) {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        if(root != null){
            queue.offer(root);
        }
        while(!queue.isEmpty()){
            TreeNode<T> node = queue.poll();
            vistor.visit(node.getValue());
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }

    }

    @Override
    public void interval(T min, T max, Visitor<T> vistor) {
        intervalRecursive(root, min, max, vistor);
    }

    private void intervalRecursive(TreeNode<T> node, T min, T max, Visitor<T> visitor){
        if(node != null){
            if(min.compareTo(node.getValue()) > 0){
                intervalRecursive(node.right, min, max, visitor);
            } else if(max.compareTo(node.getValue()) < 0){
                intervalRecursive(node.left, min, max, visitor);
            } else{
                visitor.visit(node.getValue());
                intervalRecursive(node.left, min, max, visitor);
                intervalRecursive(node.right, min, max, visitor);
            }
        }
    }
}
