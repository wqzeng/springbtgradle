package com.wqzeng.springbtgradle.util;

import com.sun.org.apache.regexp.internal.RE;
import com.wqzeng.springbtgradle.util.logicalregex.Expr;
import com.wqzeng.springbtgradle.util.logicalregex.OrExpr;
import com.wqzeng.springbtgradle.util.logicalregex.Token;
import com.wqzeng.springbtgradle.util.logicalregex.TokenStream;
import com.wqzeng.springbtgradle.util.logicalregex.TokenTypeEnum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationRegexUtil {
    private static final String REGEX="(\\s+)|(?i)\\b(and)\\b|\\b(or)\\b|(=|!=|>=|<=|>|<|\\bin\\b|\\bnot in\\b)|(\\()|(\\))|(\\w+\\s?\\+*-?\\.?/?\\|?(?!\\bin\\b|\\bnot in\\b|>|<|>=|<=|=)\\w+)|\'([^\']+)\'";
    private static final Pattern TOKENS = Pattern.compile(REGEX);

    public static boolean isMatch(String input) {
        return Pattern.matches(REGEX,input);
    }
    public static Expr parse(TokenStream stream) throws ParseException {
        OrExpr expr = new OrExpr(stream);
        stream.consume(TokenTypeEnum.EOF); // ensure that we parsed the whole input
        return expr;
    }

    /**
     * 分词器
     * @param input
     * @return
     * @throws ParseException
     */
    public static TokenStream tokenize(String input) throws ParseException {
        List<Token> inputData = new ArrayList<>();
        Matcher matcher = TOKENS.matcher(input);
        int offset = 0;
        TokenTypeEnum[] types = TokenTypeEnum.values();
        while (offset != input.length()) {
            boolean find=matcher.find();
            int start = matcher.start();
            if (!find || start != offset) {
                throw new ParseException("Unexpected token at " + start, offset);
            }
            for (int i = 0; i < types.length; i++) {
                String data = matcher.group(i + 1);
                if (data != null) {
                    if (types[i] != TokenTypeEnum.WHITESPACE)
                        inputData.add(new Token(types[i],offset,data));
                    break;
                }
            }
            offset = matcher.end();
        }
        inputData.add(new Token(TokenTypeEnum.EOF,input.length(),""));
        return new TokenStream(inputData);
    }
}
