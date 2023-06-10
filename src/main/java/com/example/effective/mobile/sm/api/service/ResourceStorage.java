package com.example.effective.mobile.sm.api.service;

import com.example.effective.mobile.sm.api.exception.SaveFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ResourceStorage {
    String saveAndGetPath(String path, MultipartFile file) throws IOException, SaveFileException;

    void deleteFile(String path) throws IOException;
}
