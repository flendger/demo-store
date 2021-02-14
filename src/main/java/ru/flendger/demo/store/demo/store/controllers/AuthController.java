package ru.flendger.demo.store.demo.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.flendger.demo.store.demo.store.dto.JwtRequest;
import ru.flendger.demo.store.demo.store.dto.JwtResponse;
import ru.flendger.demo.store.demo.store.dto.RegRequest;
import ru.flendger.demo.store.demo.store.dto.UserDto;
import ru.flendger.demo.store.demo.store.exeptions.ErrorMessage;
import ru.flendger.demo.store.demo.store.model.User;
import ru.flendger.demo.store.demo.store.services.UserService;
import ru.flendger.demo.store.demo.store.utils.JwtTokenUtil;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Неверное имя пользователя или пароль"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> regUser(@RequestBody RegRequest regRequest, Principal principal) {
        if (principal != null) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "You must logout before registration"), HttpStatus.BAD_REQUEST);
        }

        if (regRequest.getUsername().isBlank() || regRequest.getPassword().isBlank() || regRequest.getEmail().isBlank()) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Username, password or email is blank"), HttpStatus.BAD_REQUEST);
        }

        if (userService.checkNewUser(regRequest.getUsername(), regRequest.getEmail())) {
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Username or Email already exists"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(regRequest.getUsername(), regRequest.getPassword(), regRequest.getEmail());
        return ResponseEntity.ok(new UserDto(userService.createUser(user)));
    }
}
