package com.upc.exportadora.security.services;

import com.upc.exportadora.security.dtos.RegisterRequestDTO;
import com.upc.exportadora.security.dtos.UserResponseDTO;
import com.upc.exportadora.security.entities.User;
import com.upc.exportadora.security.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registrar(RegisterRequestDTO request) {

        // Validar que el usuario y la contraseña sean obligatorios
        if (request.getUsername() == null
                || request.getUsername().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuario y la contraseña son obligatorios"
            );
        }

        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El usuario '" + request.getUsername()
                            + "' ya se encuentra registrado"
            );
        }

        // Validar que el rol sea BROKER o CONTADOR
        String role = request.getRole();

        if (role == null
                || (!role.equalsIgnoreCase("BROKER")
                && !role.equalsIgnoreCase("CONTADOR"))) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El rol debe ser BROKER o CONTADOR"
            );
        }

        // Crear el usuario
        User user = new User();
        user.setUsername(request.getUsername());

        // Hashear la contraseña con BCrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Guardar el rol permitido
        user.setRole("ROLE_" + role.toUpperCase());

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Devolver los datos del usuario, sin su contraseña
        return new UserResponseDTO(
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}
