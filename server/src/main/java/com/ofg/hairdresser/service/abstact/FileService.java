package com.ofg.hairdresser.service.abstact;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(MultipartFile file);

    String saveBase64StringAsFile(String image);

    String detectType(String value);

    void deleteProfileImage(String image);
}
