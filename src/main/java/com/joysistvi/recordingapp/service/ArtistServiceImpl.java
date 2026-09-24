package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.repository.ArtistRepo;

import java.util.List;

public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepo artistRepo; // Composition

    // Constructor injection
    public ArtistServiceImpl(ArtistRepo artistRepo) {
        this.artistRepo = artistRepo;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepo.getAllArtists();
    }

    @Override
    public List<Artist> searchArtist(String keyword) {
        if (keyword == null | keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of(); // instead of returning null
        }

        return artistRepo.searchArtist(keyword.trim());
    }

    @Override
    public Artist getArtistById(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return null;
        }

        Artist artist = artistRepo.getArtistById(id);
        if (artist == null) {
            System.out.println("Artist not found.");
        }
        return artist;
    }

    @Override
    public boolean createArtist(Artist artist) {
        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name is required.");
            return false;
        }

        return true;
    }

}
