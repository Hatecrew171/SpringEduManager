package com.alkemy.service;

import com.alkemy.entity.Estudiante;
import com.alkemy.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Verificamos si es el ADMIN manual
        if ("admin".equalsIgnoreCase(username)) {
            return User.withUsername("admin")
                    .password("admin123")
                    .roles("ADMIN")
                    .build();
        }

        // 2. Si no es admin, buscamos al ESTUDIANTE por correo en la DB
        Estudiante estudiante = estudianteRepo.findAll().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.withUsername(estudiante.getEmail())
                .password("user123")
                .roles("USER")
                .build();
    }
}