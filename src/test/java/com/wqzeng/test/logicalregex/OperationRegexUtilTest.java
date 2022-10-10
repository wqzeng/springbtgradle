package com.wqzeng.test.logicalregex;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class OperationRegexUtilTest {


    @Test
    public void parse() throws ParseException {
        Map<String,String> data = new HashMap<>();
        data.put("Acct1","Y");
        data.put("Acct2","N");
        data.put("Acct3","Y");
        data.put("Acct4","N");
        data.put("Mutant Frequency","20%");
        String input = "Acct1 in 'Y,N' And (Acct2 = 'N' Or Acct3 = 'Y') and Mutant Frequency >='20%'";
//        String input = "Acct1 in 'Y,N' AND Mutant Frequency ='20%'";
        TokenStream tokenStream=OperationRegexUtil.tokenize(input);
        Expr expr = OperationRegexUtil.parse(tokenStream);
        boolean evaluate = expr.evaluate(data);
        Assert.assertTrue(evaluate);

    }
}