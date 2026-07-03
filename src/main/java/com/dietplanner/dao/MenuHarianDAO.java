package com.dietplanner.dao;

import com.dietplanner.config.DatabaseConnection;
import com.dietplanner.model.MenuHarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuHarianDAO {

    public boolean simpan(MenuHarian menu) {

        String sql = """
                INSERT INTO menu_harian
                (user_id,tanggal,
                sarapan,makan_siang,
                makan_malam,total_kalori)
                VALUES (?,?,?,?,?,?)
                """;

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, menu.getUserId());
            ps.setString(2, menu.getTanggal());
            ps.setString(3, menu.getSarapan());
            ps.setString(4, menu.getMakanSiang());
            ps.setString(5, menu.getMakanMalam());
            ps.setInt(6, menu.getTotalKalori());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    public List<MenuHarian> getSemuaRiwayat() {

        List<MenuHarian> daftar = new ArrayList<>();

        String sql = "SELECT * FROM menu_harian ORDER BY tanggal DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                daftar.add(mapRow(rs));
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return daftar;

    }

    public List<MenuHarian> getRiwayatByUser(int userId) {

        List<MenuHarian> daftar = new ArrayList<>();

        String sql = "SELECT * FROM menu_harian WHERE user_id = ? ORDER BY tanggal DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }

    private MenuHarian mapRow(ResultSet rs) throws SQLException {

        MenuHarian m = new MenuHarian();

        m.setId(rs.getInt("id"));
        m.setUserId(rs.getInt("user_id"));
        m.setTanggal(rs.getString("tanggal"));
        m.setSarapan(rs.getString("sarapan"));
        m.setMakanSiang(rs.getString("makan_siang"));
        m.setMakanMalam(rs.getString("makan_malam"));
        m.setTotalKalori(rs.getInt("total_kalori"));

        return m;
    }

}
