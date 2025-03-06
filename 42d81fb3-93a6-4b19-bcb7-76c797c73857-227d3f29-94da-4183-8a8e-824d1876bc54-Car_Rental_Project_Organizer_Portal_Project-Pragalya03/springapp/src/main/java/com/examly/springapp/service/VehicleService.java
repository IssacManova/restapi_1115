package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Vehicle;
import com.examly.springapp.repository.VehicleRepository;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }
    public Optional<Vehicle> getVehicleById(Long id){
        return vehicleRepository.findById(id);
    }
    public Vehicle saveVehicle(Vehicle vehicle)
    {
        return vehicleRepository.save(vehicle);
    }
    public Vehicle updateVehicle(Long id, Vehicle vehicle)
    {
        if(vehicleRepository.existsById(id))
        {
            vehicle.setId(id);
            return vehicleRepository.save(vehicle);
        }
        return null;
    }
    public void deleteVehicle(Long id)
    { 
        vehicleRepository.deleteById(id);
    }
}
