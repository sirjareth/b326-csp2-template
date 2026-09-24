package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepoImpl implements ArtistRepo {

    private final DbConnection dbConnection;

    public ArtistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Database Access Logic
    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        // try-with-resources for auto-closing database connections
        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            // Extract and display data rows
            while (result.next()) {
                artists.add(new Artist
                        (result.getInt("id"),
                                result.getString("name"))
                );
            }

        } catch (SQLException e) {
            System.err.println("Get All Artists: " + e.getMessage());

        }

        return artists; // null

    }

    @Override
    public boolean createArtist(Artist artist) {

        // parameterized query
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep =  conn.prepareStatement(query);

            // set wild card values
            prep.setString(1, artist.getName());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateArtist(Artist artist) {
        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, artist.getName());
            prep.setInt(2, artist.getId());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }
        return false;
    }

    public Artist getArtistById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                return new Artist(res.getInt("id"), res.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Read Artist By Id: " + e.getMessage());
        }
        return null;
    }
}
