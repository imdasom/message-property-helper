package com.konai.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static InputStream getInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static List<String> readLines(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<>();
        String line = null;
        while((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
