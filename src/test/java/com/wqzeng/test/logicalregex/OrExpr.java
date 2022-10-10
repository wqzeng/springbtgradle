package com.wqzeng.test.logicalregex;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrExpr implements Expr {
    private final List<Expr> children = new ArrayList<>();
    public OrExpr(TokenStream stream) throws ParseException {
        do {
            children.add(new AndExpr(stream));
        } while(stream.consumeIf(TokenTypeEnum.OR) != null);
    }
    @Override
    public boolean evaluate(Map<String, String> data) {
        for(Expr child : children) {
            if(child.evaluate(data))
                return true;
        }
        return false;
    }
}
