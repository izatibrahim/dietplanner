package com.dietplanner.dao;

import com.dietplanner.config.DatabaseConnection;
import com.dietplanner.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean tambahUser(User user) {

        String sql = """
                INSERT INTO user
                (nama, umur, jenis_kelamin, tinggi_badan,
                berat_badan, target, alergi, target_kalori,
                username, password)
                VALUES (?,?,?,?,?,?,?,?,?,?)
                """;

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, user.getNama());
            ps.setInt(2, user.getUmur());
            ps.setString(3, user.getJenisKelamin());
            ps.setDouble(4, user.getTinggiBadan());
            ps.setDouble(5, user.getBeratBadan());
            ps.setString(6, user.getTarget());
            ps.setString(7, user.getAlergi());
            ps.setInt(8, user.getTargetKalori());
            ps.setString(9, user.getUsername());
            ps.setString(10, user.getPassword());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    public List<User> getSemuaUser() {

        List<User> daftar = new ArrayList<>();

        String sql = "SELECT * FROM user";

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

    public User cariById(int id) {

        String sql = "SELECT * FROM user WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User cariByUsername(String username) {

        String sql = "SELECT * FROM user WHERE username = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User mapRow(ResultSet rs) throws SQLException {

        User u = new User();

        u.setId(rs.getInt("id"));
        u.setNama(rs.getString("nama"));
        u.setUmur(rs.getInt("umur"));
        u.setJenisKelamin(rs.getString("jenis_kelamin"));
        u.setTinggiBadan(rs.getDouble("tinggi_badan"));
        u.setBeratBadan(rs.getDouble("berat_badan"));
        u.setTarget(rs.getString("target"));
        u.setAlergi(rs.getString("alergi"));
        u.setTargetKalori(rs.getInt("target_kalori"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));

        return u;
    }

    public boolean hapusUser(int id){

        String sql="DELETE FROM user WHERE id=?";

        try(
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ps.executeUpdate();

            return true;

        }catch(SQLException e){

            e.printStackTrace();

            return false;

        }

    }

    public boolean updateUser(User user){

        String sql="""
                UPDATE user
                SET nama=?,
                    umur=?,
                    jenis_kelamin=?,
                    tinggi_badan=?,
                    berat_badan=?,
                    target=?,
                    alergi=?,
                    target_kalori=?
                WHERE id=?
                """;

        try(
                Connection conn=DatabaseConnection.getConnection();
                PreparedStatement ps=conn.prepareStatement(sql)
        ){

            ps.setString(1,user.getNama());
            ps.setInt(2,user.getUmur());
            ps.setString(3,user.getJenisKelamin());
            ps.setDouble(4,user.getTinggiBadan());
            ps.setDouble(5,user.getBeratBadan());
            ps.setString(6,user.getTarget());
            ps.setString(7,user.getAlergi());
            ps.setInt(8,user.getTargetKalori());
            ps.setInt(9,user.getId());

            ps.executeUpdate();

            return true;

        }catch(SQLException e){

            e.printStackTrace();

            return false;

        }

    }

}
