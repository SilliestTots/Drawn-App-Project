package main;

import config.config;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class artwork {
    private final config cfg;

    public artwork(config cfg) {
        this.cfg = cfg;
    }

    /* 1) Add new artwork (artist delivers output for a commission) */
    public boolean addArtwork(int commissionID, int artistID, String filePath, String description) {
        String sql = "INSERT INTO Artwork (commissionID, artistID, filePath, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commissionID);
            pstmt.setInt(2, artistID);
            pstmt.setString(3, filePath);
            pstmt.setString(4, description);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding artwork: " + e.getMessage());
            return false;
        }
    }

    /* 2) Get all artworks by a specific artist */
    public List<ArtworkRecord> getArtworksByArtist(int artistID) {
        String sql = "SELECT artworkID, commissionID, artistID, filePath, description " +
                     "FROM Artwork WHERE artistID = ? ORDER BY artworkID DESC";

        List<ArtworkRecord> list = new ArrayList<>();

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, artistID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ArtworkRecord(
                        rs.getInt("artworkID"),
                        rs.getInt("commissionID"),
                        rs.getInt("artistID"),
                        rs.getString("filePath"),
                        rs.getString("description")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching artworks: " + e.getMessage());
        }

        return list;
    }

    /* 3) Get artworks linked to a specific commission */
    public List<ArtworkRecord> getArtworksByCommission(int commissionID) {
        String sql = "SELECT artworkID, commissionID, artistID, filePath, description " +
                     "FROM Artwork WHERE commissionID = ?";

        List<ArtworkRecord> list = new ArrayList<>();

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commissionID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ArtworkRecord(
                        rs.getInt("artworkID"),
                        rs.getInt("commissionID"),
                        rs.getInt("artistID"),
                        rs.getString("filePath"),
                        rs.getString("description")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching commission artworks: " + e.getMessage());
        }

        return list;
    }

    /* 4) Delete artwork (in case artist removes a post or admin moderates) */
    public boolean deleteArtwork(int artworkID) {
        String sql = "DELETE FROM Artwork WHERE artworkID = ?";

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, artworkID);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting artwork: " + e.getMessage());
            return false;
        }
    }

    /* Record Holder */
    public static class ArtworkRecord {
        public final int artworkID;
        public final int commissionID;
        public final int artistID;
        public final String filePath;
        public final String description;

        public ArtworkRecord(int artworkID, int commissionID, int artistID, String filePath, String description) {
            this.artworkID = artworkID;
            this.commissionID = commissionID;
            this.artistID = artistID;
            this.filePath = filePath;
            this.description = description;
        }

        @Override
        public String toString() {
            return String.format("Artwork %d | Commission %d | Artist %d | %s | %s",
                    artworkID, commissionID, artistID, filePath, 
                    (description == null ? "" : description));
        }
    }
}
