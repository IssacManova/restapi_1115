package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.examly.springapp.model.RentalCompany;
@Repository
public interface RentalCompanyRepository extends JpaRepository<RentalCompany, Long> {
    Page<RentalCompany> findByLocationIgnoreCase(String location, Pageable pageable);
}
