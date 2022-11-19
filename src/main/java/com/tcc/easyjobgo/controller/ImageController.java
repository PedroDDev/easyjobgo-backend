package com.tcc.easyjobgo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "easyjobgo/v1/image")
public class ImageController {

    private static final String IMG_PATH = System.getProperty("user.dir")+File.separator+"img"+File.separator;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/{userCpf}/{fileName}")
    @ResponseBody
    public ResponseEntity<String> returnImage(@PathVariable("userCpf") String userCpf, @PathVariable("fileName") String fileName) throws IOException {
        if(fileName != null && fileName.trim().length() > 0){
            File file = new File(IMG_PATH+userCpf+File.separator+fileName);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return new ResponseEntity<String>(Base64.getEncoder().encodeToString(fileContent), HttpStatus.OK);
        }
        return new ResponseEntity<String>("File not found.", HttpStatus.NOT_FOUND);
        
    }

}
