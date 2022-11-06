package com.tcc.easyjobgo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.easyjobgo.factory.ServiceCategoryFactory;
import com.tcc.easyjobgo.model.ServiceCategory;
import com.tcc.easyjobgo.repository.IServiceCategoryRepository;

@RestController
@RequestMapping(path = "easyjobgo/v1/servicecategory")
public class ServiceCategoryController {
    
    IServiceCategoryRepository serviceCategoryRepository = ServiceCategoryFactory.createServiceCategoryService();

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/all")
    public ResponseEntity<List<ServiceCategory>> getAll() {
        List<ServiceCategory> result = serviceCategoryRepository.findAll();
        if(result.size() > 0) return new ResponseEntity<List<ServiceCategory>>(result, HttpStatus.OK);
        return new ResponseEntity<List<ServiceCategory>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/{id}")
    public ResponseEntity<ServiceCategory> getById(@PathVariable("id") int id) {
        ServiceCategory result = serviceCategoryRepository.findById(id);
        if(result != null) return new ResponseEntity<ServiceCategory>(result, HttpStatus.OK);
        return new ResponseEntity<ServiceCategory>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/description/{description}")
    public ResponseEntity<List<ServiceCategory>> getByDesc(@PathVariable("description") String desc) {
        
        desc = desc.toUpperCase();

        List<ServiceCategory> result = serviceCategoryRepository.findByDesc(desc);
        if(result.size() > 0) return new ResponseEntity<List<ServiceCategory>>(result, HttpStatus.OK);
        return new ResponseEntity<List<ServiceCategory>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value="/registration", consumes = {"application/json"})
    public ResponseEntity<String> saveServiceCategory(@RequestBody ServiceCategory sc){
        List<ServiceCategory> serviceCategoryExists = serviceCategoryRepository.findByDesc(sc.getDescription());
        if(serviceCategoryExists.size() > 0) return new ResponseEntity<String>("Categoria de Serviço já está cadastrada no BD!", HttpStatus.CONFLICT);
        
        ServiceCategory savedServiceCategory = serviceCategoryRepository.saveServiceCategory(sc);

        return new ResponseEntity<String>("A categoria " + savedServiceCategory.getDescription() + " foi cadastrada no BD", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value="/alteration", consumes = {"application/json"})
    public ResponseEntity<ServiceCategory> updateUser(@RequestBody ServiceCategory sc){
        ServiceCategory serviceCategoryExists = serviceCategoryRepository.findById(sc.getId());
        if(serviceCategoryExists != null) return new ResponseEntity<ServiceCategory>(serviceCategoryRepository.updateServiceCategory(sc), HttpStatus.OK);
        return new ResponseEntity<ServiceCategory>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value="/delete", consumes = {"application/json"})
    public ResponseEntity<String> deleteUser(@RequestBody ServiceCategory sc){
        ServiceCategory serviceCategoryExists = serviceCategoryRepository.findById(sc.getId());
        if(serviceCategoryExists != null) return new ResponseEntity<String>(serviceCategoryRepository.deleteServiceCategory(sc.getId()), HttpStatus.OK);
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    } 
}
