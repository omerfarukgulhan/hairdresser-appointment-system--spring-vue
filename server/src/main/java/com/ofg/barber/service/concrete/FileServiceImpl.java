package com.ofg.barber.service.concrete;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

import com.ofg.barber.config.AppProperties;
import com.ofg.barber.exception.file.FileServiceException;
import com.ofg.barber.service.abstact.FileService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final String DEFAULT_EXTENSION = ".jpg";

    private final AppProperties appProperties;
    private final Tika tika;

    @Autowired
    public FileServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.tika = new Tika();
    }

    public String saveFile(MultipartFile file) {
        String filename = generateFilename(file.getOriginalFilename());
        Path path = getProfileImagePath(filename);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            logger.error("Failed to save file: {}", filename, e);
            throw new FileServiceException("save");
        }
    }

    public String saveBase64StringAsFile(String image) {
        String filename = UUID.randomUUID().toString();
        Path path = getProfileImagePath(filename);

        try (OutputStream outputStream = Files.newOutputStream(path)) {
            outputStream.write(decodedImage(image));
            return filename;
        } catch (IOException e) {
            logger.error("Failed to save base64 image: {}", filename, e);
            throw new FileServiceException("save64");
        }
    }

    public String detectType(String value) {
        return tika.detect(decodedImage(value));
    }

    private byte[] decodedImage(String encodedImage) {
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

    public void deleteProfileImage(String image) {
        if (image == null) {
            throw new FileServiceException("image null");
        }
        if ("default.png".equals(image)) {
            logger.info("Not deleting the default profile image: {}", image);
            return;
        }

        Path path = getProfileImagePath(image);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            logger.error("Failed to delete profile image: {}", image, e);
            throw new FileServiceException("delete");
        }
    }

    private Path getProfileImagePath(String filename) {
        return Paths.get(appProperties.getStorage().getRoot(), appProperties.getStorage().getProfile(), filename);
    }

    private String generateFilename(String originalFilename) {
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : DEFAULT_EXTENSION;
        return UUID.randomUUID() + extension;
    }
}
