package com.example.effective.mobile.sm.api.utils;

import java.io.File;


public class FileNameUtils {
    private FileNameUtils(){

    }

    public static String generateUniqueFileName(String directory, String originalFileName) {
        File file = new File(directory, originalFileName);
        String baseName = getBaseName(originalFileName);
        String extension = getExtension(originalFileName);

        int count = 1;
        while (file.exists()) {
            String uniqueFileName = baseName + "_" + count + "." + extension;
            file = new File(directory, uniqueFileName);
            count++;
        }

        return file.getName();
    }

    private static String getBaseName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    private static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}
