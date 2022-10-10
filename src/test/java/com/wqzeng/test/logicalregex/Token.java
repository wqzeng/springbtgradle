package com.wqzeng.test.logicalregex;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private TokenTypeEnum tokenType;
    private int start;
    private String data;

    public Token(TokenTypeEnum tokenType, int start, String data) {
        this.tokenType = tokenType;
        this.start = start;
        this.data = data;
    }
    @Override
    public String toString() {
        return tokenType + "[" + data + "]";
    }
}
