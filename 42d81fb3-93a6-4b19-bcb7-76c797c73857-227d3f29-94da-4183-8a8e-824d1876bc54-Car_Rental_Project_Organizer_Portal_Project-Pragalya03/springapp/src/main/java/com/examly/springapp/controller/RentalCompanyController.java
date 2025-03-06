package com.examly.springapp.controller;

import com.examly.springapp.model.RentalCompany;
import com.examly.springapp.service.RentalCompanyService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentalcompanies")
public class RentalCompanyController {

    @Autowired
    private RentalCompanyService rentalCompanyService;
    @GetMapping
    public List<RentalCompany> getAllRentalCompanies(){
        return rentalCompanyService.getAllRentalCompanies();
    }

    @GetMapping("/{id}")
    public RentalCompany getRentalCompanyById(@PathVariable Long id){
        return rentalCompanyService.getRentalCompanyById(id).orElse(null);
    }

    @PostMapping
    public RentalCompany createRentalCompany(@RequestBody RentalCompany rentalCompany){
        return rentalCompanyService.saveRentalCompany(rentalCompany);
    }

    @PutMapping("/{id}")
    public RentalCompany updateRentalCompany(@PathVariable Long id, @RequestBody RentalCompany rentalCompany){
        rentalCompany.setId(id);
        return rentalCompanyService.saveRentalCompany(rentalCompany);
    }

    @DeleteMapping("/{id}")
    public void deleteRentalCompany(@PathVariable Long id){
        rentalCompanyService.deleteRentalCompany(id);
    }

}
