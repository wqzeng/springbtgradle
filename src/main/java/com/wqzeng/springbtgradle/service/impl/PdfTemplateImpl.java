package com.wqzeng.springbtgradle.service.impl;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.wqzeng.springbtgradle.service.PdfTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfTemplateImpl implements PdfTemplate {
    private final String TEMPLATE_PATH="/template/生物安全柜使用及维护记录.pdf";

    public void exportPDF() {
        String path=new ClassPathResource(TEMPLATE_PATH).getPath();
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        PdfReader reader;
        try {
            bos = new ByteArrayOutputStream();
            reader = new PdfReader(path);
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

        } catch (IOException e) {

        } catch (DocumentException e) {

        }
    }
}
