package com.wqzeng.test.logicalregex;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationRegexUtil {
    private static final String REGEX="(\\s+)|(?i)(and)|(or)|(=|!=|>=|<=|>|<|(?i)in|not in)|(\\()|(\\))|(\\w+\\s?\\+*-?\\.?/?\\|?(?!(?i)in|not in|>|<|>=|<=|=)\\w+)|\'([^\']+)\'";
    private static final Pattern TOKENS = Pattern.compile(REGEX);
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
            if (!matcher.find() || matcher.start() != offset) {
                throw new ParseException("Unexpected token at " + offset,offset);
            }
            for (int i = 0; i < types.length; i++) {
                if (matcher.group(i + 1) != null) {
                    if (types[i] != TokenTypeEnum.WHITESPACE)
                        inputData.add(new Token(types[i],offset,matcher.group(i + 1)));
                    break;
                }
            }
            offset = matcher.end();
        }
        inputData.add(new Token(TokenTypeEnum.EOF,input.length(),""));
        return new TokenStream(inputData);
    }
}
