package com.dietplanner.dao;

import com.dietplanner.config.DatabaseConnection;
import com.dietplanner.model.Progress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgressDAO {

    public boolean tambah(Progress p) {

        String sql = "INSERT INTO progress (user_id, tanggal, berat_badan) VALUES (?,?,?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getTanggal());
            ps.setDouble(3, p.getBeratBadan());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Progress> getByUser(int userId) {

        List<Progress> daftar = new ArrayList<>();
        String sql = "SELECT * FROM progress WHERE user_id = ? ORDER BY tanggal ASC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Progress p = new Progress();
                    p.setId(rs.getInt("id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setTanggal(rs.getString("tanggal"));
                    p.setBeratBadan(rs.getDouble("berat_badan"));
                    daftar.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }
}
