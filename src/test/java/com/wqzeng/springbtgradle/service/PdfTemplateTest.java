package com.wqzeng.springbtgradle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfTemplateTest {
    @Autowired
    private PdfTemplate pdfTemplate;
    @Test
    public void exportPDF() {
        pdfTemplate.exportPDF();
    }
}