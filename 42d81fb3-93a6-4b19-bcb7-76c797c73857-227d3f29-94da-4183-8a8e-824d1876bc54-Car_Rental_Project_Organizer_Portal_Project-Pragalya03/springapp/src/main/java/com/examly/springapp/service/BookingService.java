package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(long id){
        return bookingRepository.findById(id);
    }
    public Booking saveBooking(Booking booking)
    {
        return bookingRepository.save(booking);
    }
    public Booking updateBooking(Long id, Booking booking)
    {
        if(bookingRepository.existsById(id))
        {
            booking.setId(id);
            return bookingRepository.save(booking);
        }
        return null;
    }
    public void deleteBooking(Long id)
    { 
        bookingRepository.deleteById(id);
    }
}
