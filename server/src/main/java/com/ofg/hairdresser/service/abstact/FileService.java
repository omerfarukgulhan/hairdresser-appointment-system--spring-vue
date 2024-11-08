package com.ofg.hairdresser.service.abstact;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(String path,MultipartFile file);

    String saveBase64StringAsFile(String path,String image);

    String detectType(String value);

    void deleteProfileImage(String path,String image);
}
