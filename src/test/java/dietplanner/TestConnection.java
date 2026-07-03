package dietplanner;

import java.sql.Connection;

import com.dietplanner.config.DatabaseConnection;

public class TestConnection {

    public static void main(String[] args) {

        Connection conn = DatabaseConnection.getConnection();

        if(conn != null){

            System.out.println("Berhasil terkoneksi");

        }else{

            System.out.println("Koneksi gagal");

        }

    }

}