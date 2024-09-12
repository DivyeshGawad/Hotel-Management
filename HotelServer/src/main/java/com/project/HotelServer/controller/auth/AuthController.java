package com.project.HotelServer.controller.auth;

import com.project.HotelServer.dto.AuthenticationRequest;
import com.project.HotelServer.dto.AuthenticationResponse;
import com.project.HotelServer.dto.SignupRequest;
import com.project.HotelServer.dto.UserDto;
import com.project.HotelServer.entity.User;
import com.project.HotelServer.repository.UserRepository;
import com.project.HotelServer.services.auth.AuthService;
import com.project.HotelServer.services.jwt.UserService;
import com.project.HotelServer.util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try{
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch (EntityExistsException entityExistsException){
            return new ResponseEntity<>("User Already Exists.", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception exception){
            return new ResponseEntity<>("User not created, come again later!",HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//
//            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
//            Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
//
//            if (optionalUser.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//            }
//
//            String jwt = jwtUtil.generateToken(userDetails);
//            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//            authenticationResponse.setJwt(jwt);
//            authenticationResponse.setUserId(optionalUser.get().getId());
//            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
//
//            return ResponseEntity.ok(authenticationResponse);
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }

        return authenticationResponse;
    }
}
