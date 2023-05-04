package com.demo.app.service;

import com.demo.app.enums.ExceptionMessage;
import com.demo.app.exception.NullPhotoException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.database.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseStorageService {

    private Storage storage;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    @Value("${firebase.json.config}")
    private String jsonConfig;

    @PostConstruct
    public void init() throws IOException {
        File resource = new File(jsonConfig);
        InputStream inputStream = new FileInputStream(resource);
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        this.storage = storageOptions.getService();
    }

    public String uploadFile(MultipartFile file) throws IOException {

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new NullPhotoException(ExceptionMessage.NULL_PHOTO.getExceptionMessage());
        }

        String fileName = ("rental_" + UUID.randomUUID());

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        String photoUrl = storage.signUrl(blobInfo, 365, TimeUnit.DAYS).toString();
        storage.create(blobInfo, file.getBytes());
        return photoUrl;
    }

}
