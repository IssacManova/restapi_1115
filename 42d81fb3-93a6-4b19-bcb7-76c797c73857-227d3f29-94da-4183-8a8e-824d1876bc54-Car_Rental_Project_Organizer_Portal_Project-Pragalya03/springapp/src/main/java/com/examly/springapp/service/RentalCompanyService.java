package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.RentalCompany;
import com.examly.springapp.repository.RentalCompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class RentalCompanyService {
    @Autowired
    private RentalCompanyRepository rentalCompanyRepository;
    public List<RentalCompany> getAllRentalCompanies()
    {
        return rentalCompanyRepository.findAll();
    }
    public Optional<RentalCompany> getRentalCompanyById(Long id){
        return rentalCompanyRepository.findById(id);
    }
    public RentalCompany saveRentalCompany(RentalCompany rentalCompany)
    {
        return rentalCompanyRepository.save(rentalCompany);
    }
    public void deleteRentalCompany(Long id)
    { 
        rentalCompanyRepository.deleteById(id);
    }

    public Page<RentalCompany> getRentalCompaniesByLocation(String location, PageRequest pageRequest){
        return rentalCompanyRepository.findByLocationIgnoreCase(location, pageRequest);
    }

    public Page<RentalCompany> getRentalCompanies(PageRequest pageRequest){
        return rentalCompanyRepository.findAll(pageRequest);
    }
}
