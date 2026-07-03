package com.dietplanner.dao;

import com.dietplanner.config.DatabaseConnection;
import com.dietplanner.model.Makanan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MakananDAO {

    public boolean tambahMakanan(Makanan makanan) {

        String sql = """
                INSERT INTO makanan
                (nama,kategori,kalori,
                protein,karbohidrat,
                lemak,alergen)
                VALUES (?,?,?,?,?,?,?)
                """;

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, makanan.getNama());
            ps.setString(2, makanan.getKategori());
            ps.setInt(3, makanan.getKalori());
            ps.setDouble(4, makanan.getProtein());
            ps.setDouble(5, makanan.getKarbohidrat());
            ps.setDouble(6, makanan.getLemak());
            ps.setString(7, makanan.getAlergen());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    public List<Makanan> getSemuaMakanan() {

    List<Makanan> daftar = new ArrayList<>();

    String sql = "SELECT * FROM makanan";

    try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            Makanan m = new Makanan();

            m.setId(rs.getInt("id"));
            m.setNama(rs.getString("nama"));
            m.setKategori(rs.getString("kategori"));
            m.setKalori(rs.getInt("kalori"));
            m.setProtein(rs.getDouble("protein"));
            m.setKarbohidrat(rs.getDouble("karbohidrat"));
            m.setLemak(rs.getDouble("lemak"));
            m.setAlergen(rs.getString("alergen"));

            daftar.add(m);

        }

    } catch (SQLException e) {

        e.printStackTrace();

    }

    return daftar;

}

public boolean hapusMakanan(int id){

    String sql="DELETE FROM makanan WHERE id=?";

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

public boolean updateMakanan(Makanan makanan){

    String sql="""
            UPDATE makanan
            SET nama=?,
                kategori=?,
                kalori=?,
                protein=?,
                karbohidrat=?,
                lemak=?,
                alergen=?
            WHERE id=?
            """;

    try(
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)
    ){

        ps.setString(1,makanan.getNama());
        ps.setString(2,makanan.getKategori());
        ps.setInt(3,makanan.getKalori());
        ps.setDouble(4,makanan.getProtein());
        ps.setDouble(5,makanan.getKarbohidrat());
        ps.setDouble(6,makanan.getLemak());
        ps.setString(7,makanan.getAlergen());
        ps.setInt(8,makanan.getId());

        ps.executeUpdate();

        return true;

    }catch(SQLException e){

        e.printStackTrace();

        return false;

    }

}

}