package com.sha.rabbitdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
@Service
public class InvoiceService {

    private final String path = "/Users/sertaconurhakbilen/Desktop/InvoiceReaderFrontend/src/__mocks__/analytics.js";


    public String getExpenses() {
        try (FileInputStream fileInputStream = new FileInputStream(ResourceUtils.getFile(path))) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            System.out.println(new String(bytes));
            return new String(bytes);
            // Close the FileInputStream object.
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
