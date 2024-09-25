package dao;

import models.Funcionario;
import config.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    public void criarFuncionario(Funcionario fucionario) throws SQLException{
        String sql = "INSERT INTO funcionario (nome, documento, telefone, email, senha, cargo, admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, fucionario.getNome());
                stmt.setString(2, fucionario.getDocumento());
                stmt.setString(3, fucionario.getTelefone());
                stmt.setString(4, fucionario.getEmail());
                stmt.setString(5, fucionario.getSenha());
                stmt.setString(6, fucionario.getCargo());
                stmt.setBoolean(7, fucionario.isAdmin());
                stmt.executeUpdate();
             }
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> fucionarios = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Funcionario funcionario = new Funcionario(rs.getString("nome"), 
                                                    rs.getString("documento"), 
                                                    rs.getString("telefone"), 
                                                    rs.getString("email"),
                                                    rs.getString("senha"),
                                                    rs.getString("cargo"),
                                                    rs.getBoolean("admin"));
                                               
                    fucionarios.add(funcionario);
                }

                return fucionarios;
            }
    }

    
}