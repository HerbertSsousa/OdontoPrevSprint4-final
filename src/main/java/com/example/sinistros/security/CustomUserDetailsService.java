package com.example.sinistros.security;

import com.example.sinistros.model.Usuario;
import com.example.sinistros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCpf(cpf);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return new CustomUserDetails(usuario);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado: " + cpf);
        }
    }
}