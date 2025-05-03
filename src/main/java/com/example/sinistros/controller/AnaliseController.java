package com.example.sinistros.controller;

import com.example.sinistros.messaging.RabbitMQProducerService;
import com.example.sinistros.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/analises")
public class AnaliseController {

    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    private OpenAIService openAIService;

    @GetMapping
    public String mostrarAnalises() {
        return "analises";
    }

    @PostMapping("/enviar")
    public String enviarParaRabbit(@RequestParam("idFoto") String idFoto) {
        rabbitMQProducerService.enviarAnalise(idFoto);
        return "redirect:/analises";
    }

    @PostMapping("/ia")
    public String analisarComIA(@RequestParam("texto") String texto, Model model) {
        String resposta = openAIService.analisarTexto(texto);
        model.addAttribute("respostaIA", resposta);
        return "analises";
    }
}
