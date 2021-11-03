/*
 * Copyright (c) 2021. Pradeesh Kumar
 */

package org.pradeesh.crawler.core.storage;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.pradeesh.crawler.core.config.StorageConfiguration;
import org.pradeesh.crawler.core.error.StorageException;
import org.pradeesh.crawler.core.message.WebDocument;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * The type Min io object storage service.
 *
 * @author pradeesh.kumar
 */
@Service
@Slf4j
public class MinIoObjectStorageService implements StorageService {

    private static final String HTML_EXT = ".html";

    private final MinioClient storageClient;
    private final String bucketName;
    private final String dir;

    /**
     * Instantiates a new Min io object storage service.
     *
     * @param storageClient the storage client
     * @param storageConfig the storage config
     */
    public MinIoObjectStorageService(MinioClient storageClient, StorageConfiguration storageConfig) {
        this.storageClient = storageClient;
        this.bucketName = storageConfig.docBucket();
        this.dir = storageConfig.docPath();
    }

    /**
     * Stores the specified HTML document in the storage.
     *
     * @param htmlDoc the html doc
     * @return the object name
     *
     * @throws StorageException If any error occurres while storing the document
     */
    @Override
    public String store(WebDocument htmlDoc) {
        try {
            verifyBucket();
            String docId = createDocument(htmlDoc);
            log.info("Document has been stored. Doc id: {}", docId);
            return docId;
        } catch (StorageException e) {
            throw e;
        } catch (Exception e) {
            throw new StorageException("Unknown error occurred while verifying the bucket", e);
        }
    }

    /**
     * Creates the object in the storage
     *
     * @param htmlDoc the html doc
     * @return the object name
     *
     * @throws StorageException If any error occurres while storing the document
     */
    private String createDocument(WebDocument htmlDoc) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            log.info("Creating the document in the storage");
            var inputStream = new ByteArrayInputStream(htmlDoc.html().getBytes());
            int available = inputStream.available();
            String filename = generateFileName();
            String filepath = this.dir + filename;
            log.debug("Creating the object at path: {}", filepath);
            var putObjectArgs = PutObjectArgs.builder()
                    .object(filepath)
                    .bucket(bucketName)
                    .stream(inputStream, available, -1)
                    .build();
            ObjectWriteResponse response = storageClient.putObject(putObjectArgs);
            log.info("The object has been uploaded to the bucket: {}, response: {}", bucketName, response);
            return filename;
        } catch (ServerException | ErrorResponseException | InvalidResponseException |InternalException e) {
            throw new StorageException("Server error occurred while creating the object", e);
        } catch (InsufficientDataException e) {
            throw new StorageException("InsufficientDataException occurred while creating the object", e);
        } catch (Exception e) {
            throw new StorageException("Unknown error occurred while creating the object", e);
        }
    }

    /**
     * Checks if the bucket exists, if not, create a new bucket
     *
     * @throws StorageException If any error occurres while verifying or creating the gucket
     */
    private void verifyBucket() {
        try {
            var bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            log.debug("Checking if the bucket {} exists", bucketName);
            if (!storageClient.bucketExists(bucketExistsArgs)) {
                var makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
                log.warn("The bucket {} doesn't exists. Creating the new bucket", bucketName);
                storageClient.makeBucket(makeBucketArgs);
                log.info("New bucket has been created with the name: {}", bucketName);
            }
        } catch (ServerException | ErrorResponseException | InvalidResponseException |InternalException e) {
            throw new StorageException("Server error occurred while verifying the bucket", e);
        } catch (InsufficientDataException e) {
            throw new StorageException("InsufficientDataException occurred while verifying the bucket", e);
        } catch (Exception e) {
            throw new StorageException("Unknown error occurred while verifying the bucket", e);
        }
    }

    private String generateFileName() {
        String randomId = UUID.randomUUID().toString();
        String filename = randomId + HTML_EXT;
        log.debug("Generated filename: {}", filename);
        return filename;
    }
}
