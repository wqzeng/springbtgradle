package com.wqzeng.springbtgradle.structures.algorithm;

import com.google.common.collect.Lists;
import com.wqzeng.springbtgradle.structures.BTreeNode;

import java.util.List;
import java.util.Stack;

/**
 * 二叉搜索树
 * @param <T>
 */
public class BSearchTree<T extends Comparable> {
    private BTreeNode<T> root;

    public void makeEmpty() {
        this.root=null;
    }

    public boolean isEmpty() {
        return this.root==null;
    }

    public void insert(T element) {
        root=insert(element, root);
    }

    /**
     * 中序遍历
     * @return 所有节点值
     */
    public List<T> inOrderTraversal() {
        List<T> result=Lists.newArrayList();
        result.addAll(inOrderTraversal(root));
        return result;
    }
    /**
     * 用栈中序遍历
     * @return
     */
    public List<T> inOrderTraversalByStack() {
        List<T> result= Lists.newArrayList();
        if (root == null) {
            return result;
        }
        Stack<BTreeNode> treeNodes=new Stack<>();
        BTreeNode<T> cur=root;
        while (cur != null || !treeNodes.isEmpty()) {
            while (cur != null) {
                treeNodes.push(cur);
                cur = cur.left;
            }
            cur = treeNodes.pop();
            result.add(cur.element);
            cur = cur.right;
        }
        return result;
    }

    /**
     * 用栈前序遍历
     * @return
     */
    public List<T> preOrderTraversalByStack() {
        List<T> result= Lists.newArrayList();
        if (root == null) {
            return result;
        }
        Stack<BTreeNode> treeNodes=new Stack<>();
        BTreeNode<T> cur=root;
        while (cur != null || !treeNodes.isEmpty()) {
            while (cur != null) {
                treeNodes.push(cur);
                cur = cur.left;
            }
            cur = treeNodes.pop();
            result.add(cur.element);
            cur = cur.right;
        }
        return result;
    }
    private BTreeNode<T> insert(T element, BTreeNode<T> parent) {
        if (parent == null) {
            parent = new BTreeNode<>(element);
            return parent;
        }
        if (element.compareTo(parent.element) <= 0) {
            parent.left = insert(element,parent.left);
        }else{
            parent.right = insert(element,parent.right);
        }
        return parent;
    }

    /**
     * 用递归中序遍历
     * @param parent
     * @return
     */
    private List<T> inOrderTraversal(BTreeNode<T> parent) {
        List<T> result= Lists.newArrayList();
        if (parent == null) {
            return result;
        }
        result.addAll(inOrderTraversal(parent.left));
        result.add(parent.element);
        result.addAll(inOrderTraversal(parent.right));
        return result;
    }
    /**
     * 用递归前序遍历
     * @param parent
     * @return
     */
    private List<T> preOrderTraversal(BTreeNode<T> parent) {
        List<T> result= Lists.newArrayList();
        if (parent == null) {
            return result;
        }
        result.add(parent.element);
        result.addAll(preOrderTraversal(parent.left));
        result.addAll(preOrderTraversal(parent.right));
        return result;
    }

    /**
     * 用递归后序遍历
     * @param parent
     * @return
     */
    private List<T> postOrderTraversal(BTreeNode<T> parent) {
        List<T> result= Lists.newArrayList();
        if (parent == null) {
            return result;
        }
        result.addAll(postOrderTraversal(parent.left));
        result.addAll(postOrderTraversal(parent.right));
        result.add(parent.element);
        return result;
    }
}
