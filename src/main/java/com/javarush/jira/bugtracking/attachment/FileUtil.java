package com.javarush.jira.bugtracking.attachment;

import com.javarush.jira.common.error.IllegalRequestDataException;
import com.javarush.jira.common.error.NotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@UtilityClass
public class FileUtil {
    private static final Path BASE_PATH = Paths.get("./attachments").toAbsolutePath().normalize();

    static {
        try {
            Files.createDirectories(BASE_PATH); // Ensure base directory exists
        } catch (IOException e) {
            throw new RuntimeException("Failed to create base directory", e);
        }
    }


    public static void upload(MultipartFile multipartFile, String directoryName, String fileName) {
        try {
            Path directoryPath = BASE_PATH.resolve(directoryName).normalize();
            Files.createDirectories(directoryPath); // Ensure directory exists

            Path filePath = directoryPath.resolve(sanitizeFileName(fileName));
            Files.write(filePath, multipartFile.getBytes(), StandardOpenOption.CREATE_NEW);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + fileName, e);
        }
    }


    public static Resource download(String filePath) {
        try {
            Path resolvedPath = BASE_PATH.resolve(filePath).normalize();
            if (!Files.exists(resolvedPath) || !Files.isReadable(resolvedPath)) {
                throw new RuntimeException("File not found or not readable: " + filePath);
            }
            return new UrlResource(resolvedPath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed file URL: " + filePath, e);
        }
    }


    public static void delete(String filePath) {
        try {
            Path resolvedPath = BASE_PATH.resolve(filePath).normalize();
            Files.deleteIfExists(resolvedPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + filePath, e);
        }
    }

    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
    }

    public static Path getPath(String directoryName) {
        return BASE_PATH.resolve(directoryName.toLowerCase()).normalize();
    }

}
