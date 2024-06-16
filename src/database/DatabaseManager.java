package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {

    private String url;
    private String user;
    private String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void insertData(List<String> data) throws SQLException {
        String insertSQL = "INSERT INTO projeto_a3 (ordenacao) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            for (String value : data) {
                stmt.setString(1, value);
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("Dados carregados com sucesso no banco de dados.");
        } catch (SQLException e) {
            throw new SQLException("Erro ao carregar dados no banco de dados: " + e.getMessage(), e);
        }
    }
}
