package com.example.sinistros.controller;

import com.example.sinistros.dto.FuncionarioDTO;
import com.example.sinistros.model.Funcionario;
import com.example.sinistros.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<EntityModel<Funcionario>> listar() {
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        return funcionarios.stream()
                .map(funcionario -> EntityModel.of(funcionario,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FuncionarioController.class).buscarPorId(funcionario.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FuncionarioController.class).listar()).withRel("funcionarios")))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Funcionario>> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Funcionario> resource = EntityModel.of(funcionario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FuncionarioController.class).buscarPorId(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FuncionarioController.class).listar()).withRel("funcionarios"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<Funcionario> criar(@RequestBody FuncionarioDTO dto) {
        Funcionario funcionario = funcionarioService.salvar(dto);
        return ResponseEntity.ok(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        funcionarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

// Controller Web (HTML) separado para lidar com as páginas

@Controller
@RequestMapping("/funcionarios")
class FuncionarioWebController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/lista")
    public String listarFuncionarios(Model model, @ModelAttribute("mensagem") String mensagem,
                                     @ModelAttribute("tipoMensagem") String tipoMensagem) {
        List<Funcionario> funcionarios = funcionarioService.listarTodos();
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("mensagem", mensagem);
        model.addAttribute("tipoMensagem", tipoMensagem);
        return "lista-funcionarios";
    }

    @PostMapping("/salvar")
    public String salvarFuncionario(@ModelAttribute FuncionarioDTO funcionarioDTO, RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.salvar(funcionarioDTO);
            redirectAttributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Falha no cadastro de novo funcionário.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/funcionarios/lista";
    }

    @GetMapping("/novo")
    public String novoFuncionarioForm(Model model) {
        model.addAttribute("funcionarioDTO", new FuncionarioDTO());
        return "form-funcionario";
    }
}
