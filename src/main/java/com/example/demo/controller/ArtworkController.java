package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.entity.Artwork;
import com.example.demo.repository.ArtworkRepository;

@RestController
@RequestMapping("/api/artworks")
@CrossOrigin("*")
public class ArtworkController {

    @Autowired
    private ArtworkRepository artworkRepository;

    // ✅ GET ALL (SORTED)
    @GetMapping
    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // ✅ GET ONLY APPROVED (FOR VISITOR)
    @GetMapping("/approved")
    public List<Artwork> getApprovedArtworks() {
        return artworkRepository.findByStatus("Approved");
    }

    // ✅ GET ONLY PENDING (FOR CURATOR)
    @GetMapping("/pending")
    public List<Artwork> getPendingArtworks() {
        return artworkRepository.findByStatus("Pending");
    }

    // ✅ ADD ARTWORK (DEFAULT PENDING)
    @PostMapping
    public Artwork addArtwork(@RequestBody Artwork artwork) {
        artwork.setStatus("Pending"); // always pending initially
        return artworkRepository.save(artwork);
    }

    // ✅ DELETE ARTWORK (ADMIN)
    @DeleteMapping("/{id}")
    public String deleteArtwork(@PathVariable Long id) {
        artworkRepository.deleteById(id);
        return "Deleted successfully";
    }

    // ✅ UPDATE STATUS (CURATOR APPROVE / REJECT)
    @PutMapping("/{id}/status")
    public Artwork updateStatus(@PathVariable Long id, @RequestBody Artwork updatedArtwork) {

        Artwork artwork = artworkRepository.findById(id).orElse(null);

        if (artwork != null) {
            artwork.setStatus(updatedArtwork.getStatus());
            return artworkRepository.save(artwork);
        }

        return null;
    }

    // ✅ UPDATE FULL ARTWORK (EDIT FEATURE)
    @PutMapping("/{id}")
    public Artwork updateArtwork(@PathVariable Long id, @RequestBody Artwork updatedArtwork) {

        Artwork artwork = artworkRepository.findById(id).orElse(null);

        if (artwork != null) {
            artwork.setTitle(updatedArtwork.getTitle());
            artwork.setArtist(updatedArtwork.getArtist());
            artwork.setImage(updatedArtwork.getImage());
            artwork.setDescription(updatedArtwork.getDescription());
            artwork.setPrice(updatedArtwork.getPrice());
            artwork.setCategory(updatedArtwork.getCategory());
            artwork.setYear(updatedArtwork.getYear());

            return artworkRepository.save(artwork);
        }

        return null;
    }
}