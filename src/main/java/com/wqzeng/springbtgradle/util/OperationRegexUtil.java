package com.wqzeng.springbtgradle.util;

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
    /**
     * 正则表达式
     * 按()分组，分成8组对应分词器类型TokenTypeEnum，分别是:空格，and，or，运算符(=，!=，>=，<=，in，not in)，左括号，右括号，变量标识，引号值
     * 原始的表达式："(\\s+)|(AND)|(OR)|(=)|(\\()|(\\))|(\\w+)|\'([^\']+)\'"
     * ?i表示忽略大小写,直到遇到(?-i)或结束字符串为止.
     * 其中:\\b表示单词边界
     * ?!表示非
     * []表示字符集合，其中\\w表示字母或数字或下划线，\\u4E00-\\u9FA5表示汉字，\\u0400-\\u04FF表示西里尔字母,-表示连接符
     */
//    private static final String REGEX="(\\s+)|(?i)\\b(and)\\b|\\b(or)\\b|(=|!=|>=|<=|>|<|\\bin\\b|\\bnot in\\b)|(\\()|(\\))|(\\w+\\s?\\+*-?\\.?/?\\|?(?!\\bin\\b|\\bnot in\\b|>|<|>=|<=|=)\\w+)|\'([^\']+)\'";
    private static final String REGEX="(\\s+)|(?i)\\b(and)\\b|\\b(or)\\b|(=|!=|>=|<=|>|<|\\bin\\b|\\bnot in\\b)|(\\()|(\\))|([\\w\\u4E00-\\u9FA5\\u0400-\\u04FF-]+\\s?\\+*\\.?/?\\|?(?!\\bin\\b|\\bnot in\\b|>|<|>=|<=|=)[\\w\\u4E00-\\u9FA5\\u0400-\\u04FF]+)|\'([^\']+)\'";
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
            String group0 = matcher.group(0);
            System.out.println("group0:"+group0);
            System.out.println("groupCount:"+matcher.groupCount());
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
