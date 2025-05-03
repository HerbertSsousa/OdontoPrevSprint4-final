package com.example.sinistros.controller;

import com.example.sinistros.dto.UsuarioDTO;
import com.example.sinistros.dto.FuncionarioDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class homeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }


//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/usuarios/form")
    public String exibirFormularioUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO()); //
        return "form";
    }

    @GetMapping("/funcionarios/form")
    public String exibirFormularioFuncionario(Model model) {
        model.addAttribute("funcionario", new FuncionarioDTO()); //
        return "funcionario";
    }

}
