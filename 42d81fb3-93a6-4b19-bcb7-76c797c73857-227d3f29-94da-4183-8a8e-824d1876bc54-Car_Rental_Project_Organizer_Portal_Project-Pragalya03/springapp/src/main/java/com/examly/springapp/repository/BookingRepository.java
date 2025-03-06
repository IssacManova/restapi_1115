package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Booking;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
