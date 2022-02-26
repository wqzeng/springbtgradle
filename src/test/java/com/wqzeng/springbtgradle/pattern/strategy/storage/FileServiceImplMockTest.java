package com.wqzeng.springbtgradle.pattern.strategy.storage;

import com.alibaba.fastjson.JSON;
import com.wqzeng.springbtgradle.StaticPowerMockitoSpringTestBase;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@PrepareForTest({NumberUtils.class})
public class FileServiceImplMockTest extends StaticPowerMockitoSpringTestBase {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @InjectMocks
    private FileServiceImpl fileService;
    @Mock
    private IStorageService<String,FileVo> fastDsfStorageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(NumberUtils.class);
    }

    @Test
    public void save() {
        FileVo fileVo=new FileVo("testFile",111L);
        PowerMockito.when(fastDsfStorageService.upload(Mockito.any(MultipartFile.class))).thenReturn(fileVo);
        FileVo result = fileService.save(null);
        logger.info(JSON.toJSONString(result));
    }
}
