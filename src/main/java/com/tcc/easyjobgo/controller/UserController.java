package com.tcc.easyjobgo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.easyjobgo.factory.TokenFactory;
import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.factory.WorkerDayFactory;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.model.WorkerDay;
import com.tcc.easyjobgo.repository.ITokenRepository;
import com.tcc.easyjobgo.repository.IUserRepository;
import com.tcc.easyjobgo.repository.IWorkerDayRepository;
import com.tcc.easyjobgo.util.email.BuildEmailMessage;
import com.tcc.easyjobgo.util.email.EmailService;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "easyjobgo/v1/user")
public class UserController {
    
    IUserRepository userRepository = UserFactory.createUserService();
    ITokenRepository tokenRepository = TokenFactory.createTokenService();
    IWorkerDayRepository workerDayRepository = WorkerDayFactory.createWorkerDayService();

    private static final String IMG_PATH = System.getProperty("user.dir")+File.separator;
    private static final String CONFIRMATION_LINK = "https://easyjobgoapp.herokuapp.com/easyjobgo/v1/registration/confirm?token=";
    // private static final String IMAGE_BASE_URL = "https://easyjobgoapp.herokuapp.com/easyjobgo/v1/file/read?file=";

    @Autowired
    AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    EmailService emailSenderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> result = userRepository.findAll();
        if(result.size() > 0) return new ResponseEntity<List<User>>(result, HttpStatus.OK);
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/workers/{username}")
    public ResponseEntity<List<User>> getAllWorkers(@PathVariable("username") String username) {
        List<User> result = userRepository.findAllWorkers(username);
        if(result.size() > 0) return new ResponseEntity<List<User>>(result, HttpStatus.OK);
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/workers/{servicecategory}/{username}")
    public ResponseEntity<List<User>> getAllWorkersByServiceCat(@PathVariable("servicecategory") Integer serviceCat, @PathVariable("username") String username) {
        List<User> result = userRepository.findAllWorkersByService(serviceCat, username);
        if(result.size() > 0) return new ResponseEntity<List<User>>(result, HttpStatus.OK);
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/workers/search/{description}/{username}")
    public ResponseEntity<List<User>> getByDesc(@PathVariable("username") String username, @PathVariable("description") String description) {
        List<User> result = userRepository.findByDesc(username, description);
        if(result.size() > 0) return new ResponseEntity<List<User>>(result, HttpStatus.OK);
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User result = userRepository.findByUsername(username);
        if(result != null) return new ResponseEntity<User>(result, HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/registration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> saveUser(@RequestParam("user") String user, @RequestParam("days") String days){

        com.tcc.easyjobgo.model.Token confirmationToken = null;
        User savedUser = null;
        
        try {
            ObjectMapper om = new ObjectMapper();
            User responseUser = om.readValue(user, User.class);
            List<WorkerDay> responseDays = Arrays.asList(om.readValue(days, WorkerDay[].class));

            User usernameExists = userRepository.findByUsername(responseUser.getUsername());
            if(usernameExists != null) return new ResponseEntity<String>("Email já está cadastrado no Sistema!", HttpStatus.CONFLICT);
            
            User cpfExists = userRepository.findByCpf(responseUser.getCpf());
            if(cpfExists != null) return new ResponseEntity<String>("Cpf já está cadastrado no Sistema!", HttpStatus.CONFLICT);

            savedUser = userRepository.saveUser(responseUser);

            if(savedUser.isProvideService()){
                if(days != null){
                    for (WorkerDay day : responseDays) {
                        
                        day.setWorkerId(savedUser.getId());
                        workerDayRepository.save(day);
                    }
                }
            }

            String token = UUID.randomUUID().toString();
            confirmationToken = new com.tcc.easyjobgo.model.Token(token, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), savedUser.getId());

            String link = CONFIRMATION_LINK + token;
            emailSenderService.send(savedUser.getUsername(), "Confirm your email", new BuildEmailMessage().confirmationEmail(savedUser.getFirstName(), link));

            tokenRepository.saveToken(confirmationToken);
        } catch (Exception e) {
            if(confirmationToken != null) tokenRepository.deleteToken(confirmationToken.getId());
            if(savedUser != null) userRepository.deleteUser(savedUser.getId());
            
            return new ResponseEntity<String>("Error While Try to Register User. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Confirmation Token: " + confirmationToken.getToken(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value="/alteration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> updateUser(@RequestParam("user") String user){
        try {
            ObjectMapper om = new ObjectMapper();
            User responseUser = om.readValue(user, User.class);
        
            User userExists = userRepository.findById(responseUser.getId());
            if(userExists != null) return new ResponseEntity<User>(userRepository.updateUser(responseUser), HttpStatus.OK);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value="/image/alteration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateUserProfile(@RequestParam("file") MultipartFile profileImg, @RequestParam("id") UUID id){
        try {
            User userExists = userRepository.findById(id);
            if(userExists != null){
                if(!profileImg.isEmpty()){
                    File requestFile = new File(IMG_PATH+profileImg.getOriginalFilename());
                    FileOutputStream os = new FileOutputStream(requestFile);
                    os.write(profileImg.getBytes());
                    os.close();

                    String fileName = userExists.getCpf()+"_perfil_"+profileImg.getOriginalFilename();
                    
                    s3Client.putObject(new PutObjectRequest(bucketName, fileName, requestFile));
                    
                    requestFile.delete();

                    S3Object file = s3Client.getObject(bucketName, fileName);

                    if(file != null){
                        S3ObjectInputStream fileContent = file.getObjectContent();
                        byte[] fileBytes = IOUtils.toByteArray(fileContent);
                        String fileBase64 = Base64.getEncoder().encodeToString(fileBytes);
                        userRepository.updateUserProfileImage(fileBase64, id);

                        return new ResponseEntity<String>(Base64.getEncoder().encodeToString(fileBytes), HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<String>("Error ao salvar imagem!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value="/delete", consumes = {"application/json"})
    public ResponseEntity<String> deleteUser(@RequestBody User user){
        User userExists = userRepository.findById(user.getId());
        if(userExists != null) return new ResponseEntity<String>(userRepository.deleteUser(user.getId()), HttpStatus.OK);
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    } 
}
