package com.joysistvi.recordingapp;



import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.repository.ArtistRepo;
import com.joysistvi.recordingapp.repository.ArtistRepoImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.cliview.ArtistView;

import java.util.Scanner;

// Manual testing entry point — focused on Artist only for now.
// Song / Album / Playlist / User wiring removed temporarily until we're
// done verifying Artist works end-to-end.
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        // ----- Artist feature wiring -----
        ArtistRepo artistRepository = new ArtistRepoImpl(dbConnection);
        ArtistService artistService = new ArtistServiceImpl(artistRepository);
        ArtistController artistController = new ArtistController(artistService);
        ArtistView artistView = new ArtistView(artistController, scanner);

        // Straight into Artist Management — no main menu needed yet
        artistView.run();

        scanner.close();
    }
}


