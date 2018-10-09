package com.konai.common.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public static ResourceBundle getResourceBundle(String location, String bundleName, Locale locale) throws MalformedURLException {
        File file = new File(location);
        URL[] urls = { file.toURI().toURL() };
        ClassLoader loader = new URLClassLoader(urls);
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, loader);
        return bundle;
    }
}
