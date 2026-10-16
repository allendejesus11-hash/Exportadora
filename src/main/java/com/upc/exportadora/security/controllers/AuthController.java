package com.upc.exportadora.security.controllers;

import com.upc.exportadora.security.dtos.AuthRequestDTO;
import com.upc.exportadora.security.dtos.RegisterRequestDTO;
import com.upc.exportadora.security.dtos.UserResponseDTO;
import com.upc.exportadora.security.services.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.upc.exportadora.security.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;
	private final UserRegistrationService userRegistrationService;

	public AuthController(AuthenticationManager authenticationManager,
						  UserDetailsService userDetailsService, JwtUtil jwtUtil,
						  UserRegistrationService userRegistrationService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.userRegistrationService = userRegistrationService;
	}

    // Registro público: crea usuario con contraseña hasheada con BCrypt
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = userRegistrationService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

	// Login: devuelve el token JWT
	@PostMapping("/authenticate")
	public Map<String, String> authenticate(@RequestBody AuthRequestDTO request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		return Map.of("jwt", jwtUtil.generateToken(userDetails));
	}
}
