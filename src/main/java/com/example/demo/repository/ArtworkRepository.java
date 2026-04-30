package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Artwork;

import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    // ✅ Filter by status
    List<Artwork> findByStatus(String status);
}