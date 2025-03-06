package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
    public Optional<User> getUsersById(Long id)
    {
        return userRepository.findById(id);
    }
    public User saveUser(User user)
    {
        return userRepository.save(user);
    }
    public User updateUser(Long id, User user)
    {
        if(userRepository.existsById(id)){
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }
    public void deleteUser(Long id)
    { 
        userRepository.deleteById(id);
    }
}
