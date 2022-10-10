package com.wqzeng.test.logicalregex;

import java.text.ParseException;
import java.util.Map;

/**
 * EqualsExpr或括号中更复杂的东西(如果我们看到括号)
 */
public class SubExpr implements Expr {
    private Expr child;
    public SubExpr(TokenStream stream) throws ParseException {
        if(stream.consumeIf(TokenTypeEnum.LEFT_PAREN) != null) {
            child = new OrExpr(stream);
            stream.consume(TokenTypeEnum.RIGHT_PAREN);
        } else {
            child = new EqualsExpr(stream);
        }
    }
    @Override
    public boolean evaluate(Map<String, String> data) {
        return child.evaluate(data);
    }
}
