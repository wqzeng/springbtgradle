package com.wqzeng.springbtgradle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(PowerMockRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore( {"javax.management.*", "javax.net.ssl.*"})////为了解决使用powermock后，提示classloader错误
public class StaticPowerMockitoSpringTestBase {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void test() {
        logger.info("StaticPowerMockitoSpringTestBase test");
    }
}
