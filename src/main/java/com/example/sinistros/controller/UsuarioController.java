package com.example.sinistros.controller;

import com.example.sinistros.dto.UsuarioDTO;
import com.example.sinistros.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // REST - API JSON
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<RepresentationModel<UsuarioDTO>> buscarUsuarioPorId(@PathVariable Integer id) {
        Optional<UsuarioDTO> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(this::criarUsuarioComLinks)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    private RepresentationModel<UsuarioDTO> criarUsuarioComLinks(UsuarioDTO usuarioDTO) {
        RepresentationModel<UsuarioDTO> resource = new RepresentationModel<>(usuarioDTO.getLinks());
        resource.add(Link.of("/usuarios/" + usuarioDTO.getIdUser(), "self"));
        resource.add(Link.of("/usuarios", "listar"));
        resource.add(Link.of("/usuarios/" + usuarioDTO.getIdUser(), "atualizar"));
        resource.add(Link.of("/usuarios/" + usuarioDTO.getIdUser(), "deletar"));
        return resource;
    }

    // ✅ EXIBE O FORMULÁRIO HTML - sem conflito
    @GetMapping("/novo")
    public String exibirFormularioNovoUsuario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "form"; // o nome do seu HTML
    }

    // ✅ SALVA USUÁRIO SEM REDIRECIONAR, MOSTRANDO MENSAGEM
    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
            if (usuarioDTO.getIdUser() != null) {
                usuarioService.atualizarUsuario(usuarioDTO.getIdUser(), usuarioDTO);
                model.addAttribute("mensagem", "Usuário atualizado com sucesso!");
                model.addAttribute("tipoMensagem", "sucesso");
            } else {
                usuarioService.criarUsuario(usuarioDTO);
                model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");
                model.addAttribute("tipoMensagem", "sucesso");
            }
        } catch (Exception e) {
            model.addAttribute("mensagem", "Não foi possível cadastrar o usuário.");
            model.addAttribute("tipoMensagem", "erro");
        }

        return "form"; // permanece na mesma página exibindo mensagem
    }
}
