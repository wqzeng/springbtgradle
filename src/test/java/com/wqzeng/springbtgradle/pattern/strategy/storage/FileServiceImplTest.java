package com.wqzeng.springbtgradle.pattern.strategy.storage;

import com.wqzeng.springbtgradle.StaticPowerMockitoTestBase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.web.multipart.MultipartFile;

public class FileServiceImplTest extends StaticPowerMockitoTestBase {
    @InjectMocks
    private FileServiceImpl fileService;
    @Mock
    private IStorageService iStorageService;

    public void init() {
        PowerMockito.when(iStorageService.upload(Mockito.any(MultipartFile.class))).thenReturn(Mockito.any());
    }

    @Test
    public void save() {
        fileService.save(null);
    }
}
