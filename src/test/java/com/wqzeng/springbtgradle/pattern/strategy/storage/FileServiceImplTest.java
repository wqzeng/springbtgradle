package com.wqzeng.springbtgradle.pattern.strategy.storage;

import com.alibaba.fastjson.JSON;
import com.wqzeng.springbtgradle.StaticPowerMockitoSpringTestBase;
import com.wqzeng.springbtgradle.StaticPowerMockitoTestBase;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FileService fileServiceImpl;

    @Test
    public void save() {
        MultipartFile file=new MockMultipartFile("test file", (byte[]) null);
        FileVo result = fileServiceImpl.save(file);
        logger.info(JSON.toJSONString(result));
    }
}
