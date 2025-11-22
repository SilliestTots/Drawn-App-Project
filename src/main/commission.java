
package main;

import config.config;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class commission {

    private final config cfg;

    public commission(config cfg) {
        this.cfg = cfg;
    }

    /* 1) Create a new commission (buyer creates request) */
    public boolean createCommission(int buyerID, String description, Double budget, String deadline) {
        String sql = "INSERT INTO commission (buyerID, description, budget, deadline, status) VALUES (?, ?, ?, ?, 'Open')";
        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, buyerID);
            pstmt.setString(2, description);

            if (budget != null) {
                pstmt.setDouble(3, budget);
            } else {
                pstmt.setNull(3, Types.REAL);
            }

            pstmt.setString(4, deadline);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating commission: " + e.getMessage());
            return false;
        }
    }

    /* 2) View all open commissions (artists browse these) */
    public List<commission_record> viewOpenCommissions() {
        String sql = "SELECT commissionID, buyerID, description, budget, deadline, status FROM commission WHERE status = 'Open' ORDER BY commissionID DESC";
        List<commission_record> list = new ArrayList<>();

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                commission_record c = new commission_record(
                        rs.getInt("commissionID"),
                        rs.getInt("buyerID"),
                        rs.getString("description"),
                        rs.getDouble("budget"),
                        rs.getString("deadline"),
                        rs.getString("status")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching open commissions: " + e.getMessage());
        }
        return list;
    }

    /* 3) View commissions for a specific buyer */
    public List<commission_record> viewUserCommissions(int buyerID) {
        String sql = "SELECT commissionID, buyerID, description, budget, deadline, status FROM commission WHERE buyerID = ? ORDER BY commissionID DESC";
        List<commission_record> list = new ArrayList<>();

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, buyerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    commission_record c = new commission_record(
                            rs.getInt("commissionID"),
                            rs.getInt("buyerID"),
                            rs.getString("description"),
                            rs.getDouble("budget"),
                            rs.getString("deadline"),
                            rs.getString("status")
                    );
                    list.add(c);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user commissions: " + e.getMessage());
        }
        return list;
    }

    /* 4) Get specific commission details */
    public commission_record getCommissionDetails(int commissionID) {
        String sql = "SELECT commissionID, buyerID, description, budget, deadline, status FROM commission WHERE commissionID = ?";

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commissionID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new commission_record(
                            rs.getInt("commissionID"),
                            rs.getInt("buyerID"),
                            rs.getString("description"),
                            rs.getDouble("budget"),
                            rs.getString("deadline"),
                            rs.getString("status")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching commission details: " + e.getMessage());
        }

        return null;
    }

    /* 5) Update commission status (Open -> In Progress -> Closed) */
    public boolean updateCommissionStatus(int commissionID, String status) {
        String sql = "UPDATE commission SET status = ? WHERE commissionID = ?";

        try (Connection conn = cfg.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, commissionID);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating commission status: " + e.getMessage());
            return false;
        }
    }

    /* Inner record class (lowercase name too) */
    public static class commission_record {

        public final int commissionID;
        public final int buyerID;
        public final String description;
        public final Double budget;
        public final String deadline;
        public final String status;

        public commission_record(int commissionID, int buyerID, String description, Double budget, String deadline, String status) {
            this.commissionID = commissionID;
            this.buyerID = buyerID;
            this.description = description;
            this.budget = (budget != 0.0 ? budget : null);
            this.deadline = deadline;
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format(
                "ID:%d | Buyer:%d | %s | budget: %s | due: %s | %s",
                commissionID, buyerID, description,
                (budget == null ? "N/A" : budget.toString()),
                deadline, status
            );
        }
    }
}
