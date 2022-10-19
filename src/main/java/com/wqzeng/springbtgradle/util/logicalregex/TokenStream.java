package com.wqzeng.springbtgradle.util.logicalregex;

import java.text.ParseException;
import java.util.List;

public class TokenStream {
    private List<Token> dataList;
    private int offset = 0;

    public TokenStream(List<Token> dataList) {
        this.dataList = dataList;
    }
    public Token consume(TokenTypeEnum type) throws ParseException {
        Token token = dataList.get(offset++);
        if (token.getTokenType()!= type) {
            throw new ParseException("Unexpected token at " + token.getStart()
                    + ": " + token + " (was looking for " + type + ")",
                    token.getStart());
        }
        return token;
    }

    // consume token of given type (return null and don't advance if type differs)
    public Token consumeIf(TokenTypeEnum type) {
        Token token = dataList.get(offset);
        if (token.getTokenType()== type) {
            offset++;
            return token;
        }
        return null;
    }

    @Override
    public String toString() {
        return dataList.toString();
    }
}
