package com.wqzeng.springbtgradle.structures.algorithm;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class BSearchTreeTest {

    @Test
    public void insert() {
        BSearchTree<Integer> bst=new BSearchTree<>();
        Assert.assertEquals(true,bst.isEmpty());
        bst.insert(10);
        bst.insert(5);
        Assert.assertEquals(false,bst.isEmpty());
        bst.insert(6);
        bst.insert(4);
        bst.insert(7);
        bst.insert(8);
        bst.insert(12);
        List<Integer> allElement=bst.inOrderTraversal();
        List<Integer> traversalByStack=bst.inOrderTraversalByStack();
        Assert.assertEquals(7,allElement.size());
        log.info(JSONObject.toJSONString(allElement));
        log.info(JSONObject.toJSONString(traversalByStack));
    }
}
