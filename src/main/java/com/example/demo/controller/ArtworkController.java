package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.entity.Artwork;
import com.example.demo.repository.ArtworkRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkRepository repo;

    // GET all artworks
    @GetMapping
    public List<Artwork> getAllArtworks() {
        return repo.findAll();
    }

    // POST new artwork
    @PostMapping
    public Artwork addArtwork(@RequestBody Artwork art) {
        return repo.save(art);
    }

    // DELETE artwork
    @DeleteMapping("/{id}")
    public void deleteArtwork(@PathVariable int id) {
        repo.deleteById(id);
    }
}