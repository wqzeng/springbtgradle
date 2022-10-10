package com.wqzeng.test.logicalregex;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class EqualsExpr implements Expr {
    private String identifier,equals,literal;

    public EqualsExpr(TokenStream stream) throws ParseException {
        Token token = stream.consumeIf(TokenTypeEnum.IDENTIFIER);
        if(token != null) {
            this.identifier = token.getData();
            this.equals = stream.consume(TokenTypeEnum.EQUALS).getData();
            this.literal = stream.consume(TokenTypeEnum.LITERAL).getData();
        } else {
            this.literal = stream.consume(TokenTypeEnum.LITERAL).getData();
            this.equals = stream.consume(TokenTypeEnum.EQUALS).getData();
            this.identifier = stream.consume(TokenTypeEnum.IDENTIFIER).getData();
        }
    }
    @Override
    public boolean evaluate(Map<String, String> data) {
        boolean result=true;
        if ("in".equalsIgnoreCase(equals) ||"not in".equalsIgnoreCase(equals)) {
            String[] paramValues = literal.split(",");
            List<String> valueList= Lists.newArrayList(paramValues);
            result = !"in".equalsIgnoreCase(equals); //in时默认false，not in时默认true
            if(valueList.contains(data.get(identifier))){
                result = "in".equalsIgnoreCase(equals); //in时true，not in时false
            }
        } else if(equals.equals("=")){
            result = literal.equals(data.get(identifier));
        } else if(equals.equals("!=")){
            result = !literal.equals(data.get(identifier));
        } else{
            BigDecimal decimalValue = convert2Decimal(data.get(identifier));
            BigDecimal decimalParamValue = convert2Decimal(literal);
            if ((equals.equals(">") && decimalValue.compareTo(decimalParamValue) <= 0)
                    || (equals.equals("<") && decimalValue.compareTo(decimalParamValue) >= 0)
                    || (equals.equals(">=") && decimalValue.compareTo(decimalParamValue) < 0)
                    || (equals.equals("<=") && decimalValue.compareTo(decimalParamValue) > 0)) {
                result = false;
            }
        }
        return result;
    }

    private BigDecimal convert2Decimal(String value) {
        BigDecimal bigDecimal = new BigDecimal("0");
        try {
            if(value.indexOf("%")>=0){
                String decimal = value.substring(0, value.indexOf("%"));
                bigDecimal = new BigDecimal(decimal);
                bigDecimal=bigDecimal.divide(new BigDecimal("100"), 6,BigDecimal.ROUND_HALF_UP);
            }else{
                bigDecimal = new BigDecimal(value);
            }
        } catch (Exception e) {
        }

        return bigDecimal;
    }
}
