package com.konai.common.util;

import org.junit.Test;

import java.io.*;
import java.util.List;

public class FileUtilsTest {

    @Test
    public void getInputStream() throws FileNotFoundException {
        InputStream inputStream = FileUtils.getInputStream(new File(".\\test\\resources\\html\\productView.html"));
    }

    @Test
    public void readLines() throws IOException {
        InputStream inputStream = new FileInputStream(new File(".\\test\\resources\\html\\productView.html"));
        List<String> lines = FileUtils.readLines(inputStream);
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
