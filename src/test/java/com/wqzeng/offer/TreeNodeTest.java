package com.wqzeng.offer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.tree.TreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNodeTest {

    private List<Integer> largestValues(TreeNode root) {
        int current=0;
        int next;
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
            current=1;
        }
        List<Integer> result = new LinkedList<>();
        int max=Integer.MIN_VALUE;
        while (!queue.isEmpty()) {

        }
        return result;
    }
}
