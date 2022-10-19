package com.wqzeng.springbtgradle.util.logicalregex;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AndExpr implements Expr {
    private List<Expr> children = new ArrayList<>();
    public AndExpr(TokenStream stream) throws ParseException {
        do {
            children.add(new SubExpr(stream));
        } while(stream.consumeIf(TokenTypeEnum.AND) != null);
    }
    @Override
    public boolean evaluate(Map<String, String> data) {
        for(Expr child : children) {
            if(!child.evaluate(data))
                return false;
        }
        return true;
    }
}
