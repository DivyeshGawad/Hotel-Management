package com.project.HotelServer.services.auth;

import com.project.HotelServer.dto.SignupRequest;
import com.project.HotelServer.dto.UserDto;

public interface AuthService {

    public UserDto createUser(SignupRequest signupRequest);
}
