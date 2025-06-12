package org.example;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DatabaseManager {
    private final String url = "jdbc:mysql://localhost:3306/scrum_escape";
    private final String user = "root";
    private final String password = "root";


// ALS WAT FOUT GAAT MET DATABASE CHECK FF HIERZO GIRLYPOPS YAAAAAAAAAA
    public boolean registreerSpeler(String gebruikersnaam, String wachtwoord) {
        String query = "INSERT INTO speler (gebruikersnaam, wachtwoord, status, huidige_kamer_nummer) VALUES (?, ?, 'Nieuw', 1)";
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gebruikersnaam);
            stmt.setString(2, wachtwoord);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Gebruikersnaam bestaat al of databasefout: " + e.getMessage());
            return false;
        }
    }

    public boolean controleerLogin(String gebruikersnaam, String wachtwoord) {
        String query = "SELECT * FROM speler WHERE gebruikersnaam = ? AND wachtwoord = ?";
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gebruikersnaam);
            stmt.setString(2, wachtwoord);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateSpelerStatus(String gebruikersnaam, String status) throws SQLException {
        String sql = "UPDATE speler SET status = ? WHERE gebruikersnaam = ?";
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, gebruikersnaam);
            ps.executeUpdate();
        }
    }

    public void updateHuidigeKamer(String gebruikersnaam, int kamerNummer) throws SQLException {
        String sql = "UPDATE speler SET huidige_kamer_nummer = ? WHERE gebruikersnaam = ?";
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kamerNummer);
            ps.setString(2, gebruikersnaam);
            ps.executeUpdate();
        }
    }

    public void voegVoltooideKamerToe(String gebruikersnaam, int kamerNummer) throws SQLException {
        // checken of kamertje af is dan update into database guys yayy!!!
        if (!isKamerVoltooid(gebruikersnaam, kamerNummer)) {
            String sql = "INSERT INTO kamer_voortgang (speler_id, kamer_nummer, voltooid) VALUES (?, ?, 1)";
            try (Connection conn = getConnection(url, user, password);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, gebruikersnaam);
                ps.setInt(2, kamerNummer);
                ps.executeUpdate();
            }

            // update de huidige kamer naar de volgende
            updateHuidigeKamer(gebruikersnaam, kamerNummer + 1);
        }
    }

    public boolean isKamerVoltooid(String gebruikersnaam, int kamerNummer) throws SQLException {
        String sql = "SELECT 1 FROM kamer_voortgang WHERE speler_id = ? AND kamer_nummer = ? AND voltooid = 1";
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gebruikersnaam);
            ps.setInt(2, kamerNummer);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public int getHuidigeKamer(String gebruikersnaam) throws SQLException {
        String sql = "SELECT huidige_kamer_nummer FROM speler WHERE gebruikersnaam = ?";
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gebruikersnaam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("huidige_kamer_nummer");
            }
            return 1; // standardvalue if sum goes wrongg want huidigkamer = altijd 1e kamer
        }
    }
    public void updateMunten(String gebruikersnaam, int nieuweWaarde) throws SQLException {
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("UPDATE speler SET munten = ? WHERE gebruikersnaam = ?")) {
            stmt.setInt(1, nieuweWaarde);
            stmt.setString(2, gebruikersnaam);
            stmt.executeUpdate();
        }
    }

    public int getMunten(String gebruikersnaam) throws SQLException {
        try (Connection conn = getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT munten FROM speler WHERE gebruikersnaam = ?")) {
            stmt.setString(1, gebruikersnaam);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("munten");
            }
            return 0;
        }
    }

}