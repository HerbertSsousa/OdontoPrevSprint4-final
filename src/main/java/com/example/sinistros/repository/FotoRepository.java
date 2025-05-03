package com.example.sinistros.repository;

import com.example.sinistros.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
}
