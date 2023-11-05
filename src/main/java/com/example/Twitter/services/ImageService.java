package com.example.Twitter.services;

import com.example.Twitter.exceptions.UnableToResolvePhotoException;
import com.example.Twitter.exceptions.UnableToSavePhotoException;
import com.example.Twitter.models.Image;
import com.example.Twitter.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    private static final File DIRECTORY = new File("/home/chicmic/Desktop/Java/Twitter/img");
    private static final String URL = "localhost:8000/images/";

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(MultipartFile file, String prefix) throws UnableToSavePhotoException {
        try {
            //CONTENT TYPE FROM THE REQUEST LOOKS SOMETHING LIKE THIS img/jpeg
            String extension = '.' + file.getContentType().split("/")[1];
            File img = File.createTempFile(prefix, extension, DIRECTORY);

            file.transferTo(img);
            String imageURL = URL + img.getName();
            Image i = new Image(img.getName(), file.getContentType(), img.getPath(), imageURL);
            Image saved = imageRepository.save(i);
            return saved;
        }catch (IOException e){
            throw new UnableToSavePhotoException();
        }
    }

    public byte[] downloadImage(String filename) throws UnableToResolvePhotoException {
        try{
           Image image = imageRepository.findByImageName(filename).get();
           String filePath = image.getImagePath();
           byte[] imageBytes = Files.readAllBytes(new File(filePath).toPath());
           return  imageBytes;
        }catch(IOException e){
            throw new UnableToResolvePhotoException();
        }
    }

    public String getImageType(String fileName){
        Image image = imageRepository.findByImageName(fileName).get();
        return image.getImageType();
    }

}
