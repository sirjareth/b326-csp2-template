package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.model.Artist;

import java.util.List;
import java.util.Scanner;

public class ArtistView {

    private final ArtistController artistController;
    private final Scanner scanner;

    public ArtistView(ArtistController artistController, Scanner scanner) {
        this.artistController = artistController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;

        do {
            printMenu();

            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllArtists();
                case 2 -> searchArtist();
                case 3 -> addArtists();
                case 4 -> updateArtist();

                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }


    private void printMenu() {
        System.out.println("\n----- Artist Management -----");
        System.out.println("1. View All Artists");
        System.out.println("2. Search Artist");
        System.out.println("3. Add Artist");
        System.out.println("4. Update Artist");
        System.out.println("5. Archive Artist");
        System.out.println("6. Restore Artist");
        System.out.println("7. Delete Artist");
        System.out.println("8. View All Archived Artists");
        System.out.println("0. Back");
    }

    public int promptChoice() {
        System.out.print("Choice: ");
        return readInt();
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (RuntimeException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private void viewAllArtists() {
        System.out.println("\n----- View All Artists -----");
        List<Artist> artists = artistController.handleViewAllArtists();
        printArtists(artists);
    }

    private void searchArtist() {
        System.out.println("\n----- Search Artists -----");
        System.out.print("Enter name: ");
        String keyword = scanner.nextLine();
        List<Artist> artists = artistController.searchArtist(keyword);
        printArtists(artists);
    }

    private void addArtists() {
        System.out.println("\n----- Add Artists -----");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        Artist artist = new Artist(name);

        boolean isSuccess = artistController.handleCreateArtist(artist);
        System.out.println(isSuccess ? "Artist added successfully." : "Failed to add artist.");

        if (isSuccess) {
            System.out.println();
            viewAllArtists(); // read-after-write
        }
    }

    public void updateArtist() {
        System.out.println("\n----- Update Artists -----");

        // Show all artists first so the admin can see which ID to pick
        viewAllArtists();

        System.out.print("Arists ID to update: ");
        int id = readInt();

        Artist current = artistController.handleGetArtistById(id);

        if (current == null) {
            System.out.println("No artist found with ID " + id + ". Please check the ID and try again.");
            return;
        }

        System.out.println("New Name [ " + current.getName() + "] (press Enter to keep the current): ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            name = current.getName();
        }

        Artist artist = new Artist(id, name);

        boolean isSuccess = artistController.handleCreateArtist(artist);
        System.out.println(isSuccess ? "Artist updated successfully." : "Failed to update artist.");

        if (isSuccess) {
            System.out.println();
            viewAllArtists(); // read-after-write / refresh-after-mutation
        }
    }


    public void printArtists(List<Artist> artists) {
        if (artists.isEmpty()) {
            System.out.println("No artists found.");
            return;
        }

        String border = "+" + "-".repeat(6) + "+"  + "-".repeat(27) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Name");
        System.out.println(border);

        for (Artist artist : artists) {
            System.out.printf("| %-4s | %-25s |%n", artist.getId(), artist.getName());
        }

        System.out.println(border);
    }

}
