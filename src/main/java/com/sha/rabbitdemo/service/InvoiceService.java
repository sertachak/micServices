package com.sha.rabbitdemo.service;

import com.sha.rabbitdemo.exception.CustomException;
import com.sha.rabbitdemo.enums.TutException;
import com.sha.rabbitdemo.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Service
public class InvoiceService {

    @Value("${invoice.file.path}")
    private String path;

    public String getExpenses() {
        try (FileInputStream fileInputStream = new FileInputStream(ResourceUtils.getFile(path))) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            System.out.println(new String(bytes));
            throw new IOException("File not found");
            //return new String(bytes);
            // Close the FileInputStream object.


        } catch (IOException e) {
            throw new ApiException(TutException.FILE_NOT_FOUND);
        }
    }
}
