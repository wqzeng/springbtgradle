package com.wqzeng.test;

import com.alibaba.fastjson.JSONObject;
import com.wqzeng.springbtgradle.bean.ExportDataParams;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class JsonTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Test
    public void testObject() {
        Set<String> methods = new HashSet<>();
        String jsonStr="{\"mul_cat_bsn\":\"10\",\"bsn\":\"9,19,20,38,365\",\"cat\":\"1,18,29,30,31,32,100,101,104,368\",\"cat3_bsn\":\"99\",\"cat3_tag\":\"12,22,23,24,25\",\"cat_tag$19\":\"58,21\",\"normal$100$100$5$100$100\":\"28,37,52,213,279\",\"notcalc\":\"11\",\"normal$50$5$0$100$100\":\"390\",\"normal$100$100$50%50%100\":\"14\",\"cat_threshold$0$5$5\":\"389,421,429\",\"normal$99$49$5$0\":\"442\"}";
        JSONObject jsonObject=JSONObject.parseObject(jsonStr);
        for (String key : jsonObject.keySet()) {
            methods.add(key);
            String source=jsonObject.getString(key);
        }
        Assert.assertEquals(methods.size(),13);
    }

    @Test
    public void testObject2Json() {
        Set<Integer> type = new HashSet<>();
        type.add(2);
        type.add(1);
        type.add(3);

        ExportDataParams params=new ExportDataParams();
        params.setBasePath("theme_retrieval_data");
        params.setDataTypes(type);

        String jsonString=JSONObject.toJSONString(params);
        logger.info(jsonString);

    }
}
