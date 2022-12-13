package com.tcc.easyjobgo.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@RestController
@RequestMapping(path = "easyjobgo/v1/file")
public class FileController {

    @Autowired
    AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="read", produces = {"image/jpg", "image/jpeg", "image/png", "image/bmp"})
    public ResponseEntity<String> readFile(@RequestParam("file") String fileName) throws IOException{
        
        try{
            S3Object file = s3Client.getObject(bucketName, fileName);

            if(file != null){
                S3ObjectInputStream fileContent = file.getObjectContent();
                byte[] fileBytes = IOUtils.toByteArray(fileContent);
    
                return new ResponseEntity<String>(Base64.getEncoder().encodeToString(fileBytes), HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("Imagem nao encontrada!",HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
           return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
