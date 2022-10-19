package com.wqzeng.test;

import com.wqzeng.springbtgradle.util.logicalregex.Expr;
import com.wqzeng.springbtgradle.util.OperationRegexUtil;
import com.wqzeng.springbtgradle.util.logicalregex.TokenStream;
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
        data.put("And4in","N");
        data.put("Mutant inFrequency","20%");
        String input = "And4in not in 'Y,N' and (Acct2 = 'N' Or Acct3 = 'Y')or Mutant inFrequency >='20%'";
//        String input = "Acct1 in 'Y,N' AND Mutant Frequency ='20%'";
        TokenStream tokenStream= OperationRegexUtil.tokenize(input);
        Expr expr = OperationRegexUtil.parse(tokenStream);
        boolean evaluate = expr.evaluate(data);
        Assert.assertTrue(evaluate);
    }

    @Test
    public void isMatchTest() {
        String input="Acct1 in 'Y,N' and (Acct2 = 'N' Or Acct3 = 'Y') or Mutant notFrequency >='20%'";
        boolean match = OperationRegexUtil.isMatch(input);
        Assert.assertTrue(match);
    }
}