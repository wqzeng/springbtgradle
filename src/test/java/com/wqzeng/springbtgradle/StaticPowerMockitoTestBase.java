package com.wqzeng.springbtgradle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(PowerMockRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@PowerMockIgnore( {"javax.management.*", "javax.net.ssl.*"})////为了解决使用powermock后，提示classloader错误
public class StaticPowerMockitoTestBase {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void test() {
        logger.info("StaticPowerMockitoTestBase");
    }
}
