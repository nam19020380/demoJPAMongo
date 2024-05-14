package com.example.demo.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface ImageService {
    public String uploadImage(MultipartFile multipartFile) throws IOException;
    public ByteArrayResource getImage (String address) throws IOException;

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException;

    public void deleteImage(String address) throws IOException;
}