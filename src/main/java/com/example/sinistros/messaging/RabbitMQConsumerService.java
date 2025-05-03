package com.example.sinistros.messaging;

import com.example.sinistros.model.AnalisePreditiva;
import com.example.sinistros.model.Foto;
import com.example.sinistros.repository.AnalisePreditivaRepository;
import com.example.sinistros.repository.FotoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RabbitMQConsumerService {

    private final AnalisePreditivaRepository analiseRepo;
    private final FotoRepository fotoRepo;

    public RabbitMQConsumerService(AnalisePreditivaRepository analiseRepo, FotoRepository fotoRepo) {
        this.analiseRepo = analiseRepo;
        this.fotoRepo = fotoRepo;
    }

    @RabbitListener(queues = "analise-queue")
    public void processarAnalise(String idFotoString) {
        try {
            Long idFoto = Long.parseLong(idFotoString);
            Foto foto = fotoRepo.findById(idFoto).orElse(null);

            if (foto == null) {
                System.out.println("⚠️ Foto não encontrada com ID: " + idFoto);
                return;
            }

            AnalisePreditiva analise = new AnalisePreditiva();
            analise.setFoto(foto);
            analise.setResultadoAnalise(Math.random() > 0.5 ? "Apto" : "Inapto");
            analise.setFrequenciaSinistros((int) (Math.random() * 10));
            analise.setDataAnalise(LocalDate.now());

            analiseRepo.save(analise);
            System.out.println("✅ Análise preditiva salva com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("❌ ID da foto inválido: " + idFotoString);
        }
    }
}
