package com.tcc.easyjobgo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.easyjobgo.factory.ServiceFactory;
import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.factory.WorkerDayFactory;
import com.tcc.easyjobgo.model.Services;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.repository.IServicesRepository;
import com.tcc.easyjobgo.repository.IUserRepository;
import com.tcc.easyjobgo.repository.IWorkerDayRepository;
import com.tcc.easyjobgo.util.email.BuildEmailMessage;
import com.tcc.easyjobgo.util.email.EmailService;

@RestController
@RequestMapping(path = "easyjobgo/v1/services")
public class ServiceController {

    IServicesRepository serviceRepository = ServiceFactory.createServicesService();
    IUserRepository userRepository = UserFactory.createUserService();
    IWorkerDayRepository workerDayRepository = WorkerDayFactory.createWorkerDayService();

    @Autowired
    EmailService emailSenderService;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value="/worker", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<Services>> getWorkerServices(@RequestParam("worker") UUID workerId) {
        List<Services> result = serviceRepository.findAllByWorker(workerId);
        if(result.size() > 0) return new ResponseEntity<List<Services>>(result, HttpStatus.OK);
        return new ResponseEntity<List<Services>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value="/client", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<Services>> getClientServices(@RequestParam("client") UUID clientId) {
        List<Services> result = serviceRepository.findAllByClient(clientId);
        if(result.size() > 0) return new ResponseEntity<List<Services>>(result, HttpStatus.OK);
        return new ResponseEntity<List<Services>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value="/registration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> saveService(@RequestParam("service") String service){

        Services savedService = null;

        User client = null;
        User worker = null;
        
        try {

            ObjectMapper om = new ObjectMapper();
            Services responseService = om.readValue(service, Services.class);

            responseService.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            responseService.setExpiresDate(Timestamp.valueOf(LocalDateTime.now().plusHours(24)));

            savedService = serviceRepository.save(responseService);

            client = userRepository.findById(savedService.getServiceClient());
            worker = userRepository.findById(savedService.getServiceWorker());

            emailSenderService.send(client.getUsername(), "Service Order Created", new BuildEmailMessage().clientServiceCreateEmail(client.getFirstName(), worker.getFirstName()));
            emailSenderService.send(worker.getUsername(), "Service Order Created", new BuildEmailMessage().workerServiceCreateEmail(client.getFirstName(), worker.getFirstName()));

        } catch (Exception e) {
            if(savedService != null) serviceRepository.delete(savedService.getId());
            
            return new ResponseEntity<String>("Error While Try to Register Service. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Confirmation Token: " + savedService.getId().toString(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value="/worker-confirm", consumes = {"application/json"})
    public ResponseEntity<String> workerConfirmService(@RequestBody Services service){

        User client = null;
        User worker = null;
        
        try {

            serviceRepository.update(service);

            client = userRepository.findById(service.getServiceClient());
            worker = userRepository.findById(service.getServiceWorker());

            emailSenderService.send(client.getUsername(), "Service Order Confirmed", new BuildEmailMessage().clientServiceCreateEmail(client.getFirstName(), worker.getFirstName()));
            emailSenderService.send(worker.getUsername(), "Service Order Confirmed", new BuildEmailMessage().workerServiceCreateEmail(client.getFirstName(), worker.getFirstName()));

            // workerDayRepository.updateStatus(service.getDayServiceWorker(), 3);

        } catch (Exception e) {
            
            return new ResponseEntity<String>("Error While Try to Confirm Service. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Job Confirmed" + service.getId().toString(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value="/start", consumes = {"application/json"})
    public ResponseEntity<String> startService(@RequestBody Services service){

        User client = null;
        User worker = null;
        
        try {

            service.setStartDate(Timestamp.valueOf(LocalDateTime.now()));

            serviceRepository.update(service);

            client = userRepository.findById(service.getServiceClient());
            worker = userRepository.findById(service.getServiceWorker());

            emailSenderService.send(client.getUsername(), "Service Order Started", new BuildEmailMessage().clientServiceCreateEmail(client.getFirstName(), worker.getFirstName()));
            emailSenderService.send(worker.getUsername(), "Service Order Started", new BuildEmailMessage().workerServiceCreateEmail(client.getFirstName(), worker.getFirstName()));

        } catch (Exception e) {
            
            return new ResponseEntity<String>("Error While Try to Confirm Service. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Job Confirmed" + service.getId().toString(), HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value="/end", consumes = {"application/json"})
    public ResponseEntity<String> workerEndService(@RequestBody Services service){

        User client = null;
        User worker = null;
        
        try {

            service.setEndDate(Timestamp.valueOf(LocalDateTime.now()));
            service.setEndConfirmationWorker(true);

            serviceRepository.update(service);

            client = userRepository.findById(service.getServiceClient());
            worker = userRepository.findById(service.getServiceWorker());

            emailSenderService.send(client.getUsername(), "Service Order Finished", new BuildEmailMessage().clientServiceCreateEmail(client.getFirstName(), worker.getFirstName()));
            emailSenderService.send(worker.getUsername(), "Service Order Finished", new BuildEmailMessage().workerServiceCreateEmail(client.getFirstName(), worker.getFirstName()));

        } catch (Exception e) {
            
            return new ResponseEntity<String>("Error While Try to Confirm Service. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Job Confirmed" + service.getId().toString(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value="/endconfirmation", consumes = {"application/json"})
    public ResponseEntity<String> clientEndConfirmationService(@RequestBody Services service){

        Services savedService = null;
        
        try {

            service.setEndConfirmationClient(true);
            String token = UUID.randomUUID().toString().substring(0, 4);
            service.setEndToken(token);

            savedService = serviceRepository.update(service);

        } catch (Exception e) {
            
            return new ResponseEntity<String>("Error While Try to Confirm Service. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(savedService.getEndToken(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/finish", consumes = {"application/json"})
    public ResponseEntity<String> finishService(@RequestParam String token){

        try {

            Services serviceExists = serviceRepository.findByToken(token);
            if(serviceExists == null) return new ResponseEntity<String>("Token Não Existente. Por favor, tente novamente!", HttpStatus.NOT_FOUND);

            workerDayRepository.updateStatus(serviceExists.getDayServiceWorker(), 1);

        } catch (Exception e) {
            
            return new ResponseEntity<String>("Error While Try to Confirm Service. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Service Done!", HttpStatus.OK);
    }

}
