package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.enums.ImageType;
import com.example.effective.mobile.sm.api.exception.SaveFileException;
import com.example.effective.mobile.sm.api.service.ResourceStorage;
import com.example.effective.mobile.sm.api.utils.FileNameUtils;
import com.example.effective.mobile.sm.api.utils.MediaTypeUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service("resourceStorageImpl")
public class ResourceStorageImpl implements ResourceStorage {
    @Override
    public String saveAndGetPath(String path, MultipartFile file) throws IOException, SaveFileException {
        ImageType imageType = MediaTypeUtils.getImageType(file);
        if(imageType != null){
            String fileName = FileNameUtils.generateUniqueFileName(path,file.getOriginalFilename());
            String filePath = path + fileName;
            Path targetPath = Path.of(filePath);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        }
        throw new SaveFileException("Указан неверный формат изображения.");
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new IOException("Не удалось удалить файл: " + filePath, e);
        }
    }
}
