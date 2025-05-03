package com.example.sinistros.service;

import com.example.sinistros.dto.UsuarioDTO;
import com.example.sinistros.model.Usuario;
import com.example.sinistros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).map(this::converterParaDTO);
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        Integer nextId = jdbcTemplate.queryForObject("SELECT SEQ_USUARIOS.NEXTVAL FROM DUAL", Integer.class);
        usuario.setIdUser(nextId);
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha())); // 🔒 criptografia
        usuario.setDataCriacao(LocalDate.now());

        Usuario salvo = usuarioRepository.save(usuario);
        return converterParaDTO(salvo);
    }

    public UsuarioDTO atualizarUsuario(Integer id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // 🔒 criptografia
        usuario.setDataCriacao(LocalDate.now());
        return converterParaDTO(usuarioRepository.save(usuario));
    }

    public void deletarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUser(usuario.getIdUser());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setSenha(usuario.getSenha());
        dto.setDataCriacao(usuario.getDataCriacao());
        return dto;
    }
}
