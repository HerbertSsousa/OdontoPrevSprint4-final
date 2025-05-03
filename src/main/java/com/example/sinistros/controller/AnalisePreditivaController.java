//package com.example.sinistros.controller;
//
//import com.example.sinistros.model.AnalisePreditiva;
//import com.example.sinistros.service.AnalisePreditivaService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/analises")
//public class AnalisePreditivaController {
//
//    @Autowired
//    private AnalisePreditivaService analisePreditivaService;
//
//    @GetMapping
//    public String exibirAnalises(Model model) {
//        List<AnalisePreditiva> analises = analisePreditivaService.listarTodas();
//        model.addAttribute("analises", analises);
//        return "analises"; // analises.html (Thymeleaf)
//    }
//}
