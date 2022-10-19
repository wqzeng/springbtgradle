package com.wqzeng.springbtgradle.util.logicalregex;

import java.util.Map;

public interface Expr {
    boolean evaluate(Map<String,String> data);
}
