package com.project.HotelServer.services.auth;

import com.project.HotelServer.dto.SignupRequest;
import com.project.HotelServer.dto.UserDto;
import com.project.HotelServer.entity.User;
import com.project.HotelServer.enums.UserRole;
import com.project.HotelServer.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        if(adminAccount.isEmpty()){
            User admin = new User();
            admin.setEmail("admin@test.com");
            admin.setName("admin");
            admin.setUserRole(UserRole.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(admin);
            System.out.println("Admin account created Successfully");
        }else {
            System.out.println("Admin account already exists.");
        }
    }

    public UserDto createUser(SignupRequest signupRequest){
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User Account present with email "+signupRequest.getEmail());
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);

        return createdUser.getUserDto();
    }
}
