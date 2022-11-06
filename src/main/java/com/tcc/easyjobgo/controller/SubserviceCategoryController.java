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

import com.tcc.easyjobgo.factory.SubserviceCategoryFactory;
import com.tcc.easyjobgo.model.SubserviceCategory;
import com.tcc.easyjobgo.repository.ISubserviceCategoryRepository;

@RestController
@RequestMapping(path = "easyjobgo/v1/subservicecategory")
public class SubserviceCategoryController {
    
    ISubserviceCategoryRepository subserviceCategoryRepository = SubserviceCategoryFactory.createSubserviceCategoryService();

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/all")
    public ResponseEntity<List<SubserviceCategory>> getAll() {
        List<SubserviceCategory> result = subserviceCategoryRepository.findAll();
        if(result.size() > 0) return new ResponseEntity<List<SubserviceCategory>>(result, HttpStatus.OK);
        return new ResponseEntity<List<SubserviceCategory>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/{id}")
    public ResponseEntity<SubserviceCategory> getById(@PathVariable("id") int id) {
        SubserviceCategory result = subserviceCategoryRepository.findById(id);
        if(result != null) return new ResponseEntity<SubserviceCategory>(result, HttpStatus.OK);
        return new ResponseEntity<SubserviceCategory>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/description/{description}")
    public ResponseEntity<List<SubserviceCategory>> getByDesc(@PathVariable("description") String desc) {
        
        desc = desc.toUpperCase();

        List<SubserviceCategory> result = subserviceCategoryRepository.findByDesc(desc);
        if(result.size() > 0) return new ResponseEntity<List<SubserviceCategory>>(result, HttpStatus.OK);
        return new ResponseEntity<List<SubserviceCategory>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value="/registration", consumes = {"application/json"})
    public ResponseEntity<String> saveServiceCategory(@RequestBody SubserviceCategory sc){
        List<SubserviceCategory> serviceCategoryExists = subserviceCategoryRepository.findByDesc(sc.getDescription());
        if(serviceCategoryExists.size() > 0) return new ResponseEntity<String>("Categoria de Serviço já está cadastrada no BD!", HttpStatus.CONFLICT);
        
        SubserviceCategory savedServiceCategory = subserviceCategoryRepository.saveSubserviceCategory(sc);

        return new ResponseEntity<String>("A categoria " + savedServiceCategory.getDescription() + " foi cadastrada no BD", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value="/alteration", consumes = {"application/json"})
    public ResponseEntity<SubserviceCategory> updateUser(@RequestBody SubserviceCategory sc){
        SubserviceCategory serviceCategoryExists = subserviceCategoryRepository.findById(sc.getId());
        if(serviceCategoryExists != null) return new ResponseEntity<SubserviceCategory>(subserviceCategoryRepository.updateSubserviceCategory(sc), HttpStatus.OK);
        return new ResponseEntity<SubserviceCategory>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value="/delete", consumes = {"application/json"})
    public ResponseEntity<String> deleteUser(@RequestBody SubserviceCategory sc){
        SubserviceCategory serviceCategoryExists = subserviceCategoryRepository.findById(sc.getId());
        if(serviceCategoryExists != null) return new ResponseEntity<String>(subserviceCategoryRepository.deleteSubserviceCategory(sc.getId()), HttpStatus.OK);
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    } 

}
