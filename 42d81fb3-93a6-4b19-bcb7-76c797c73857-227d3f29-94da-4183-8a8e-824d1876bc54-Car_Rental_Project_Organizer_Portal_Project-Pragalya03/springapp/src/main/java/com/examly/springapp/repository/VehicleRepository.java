package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
}
